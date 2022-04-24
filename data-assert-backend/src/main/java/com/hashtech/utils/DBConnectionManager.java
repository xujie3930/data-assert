package com.hashtech.utils;

/**
 * @author xujie
 * @description 动态创建数据源连接池
 * @create 2022-01-28 17:33
 **/

import cn.hutool.core.util.StrUtil;
import com.hashtech.common.DatasourceTypeEnum;
import com.hashtech.utils.sm4.SM4Utils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

@Slf4j
public class DBConnectionManager {
    private static final String SEPARATOR = "|";
    private static final String SPLIT_URL_FLAG = "?";
    private static final String SQL_CHARACTER = "useSSL=false&useUnicode=true&characterEncoding=utf8";
    static private DBConnectionManager instance;
    static private int clients;
    private final Vector drivers = new Vector();
    private final int defaultConn = 50;
    private Hashtable pools = new Hashtable();

    private DBConnectionManager() {
    }

    static synchronized public DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        clients++;
        return instance;
    }

    static String getDrvierByType(Integer type) {
        String driver = DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver();
        return driver;
    }

    /**
     * 获取用户名
     *
     * @param uri
     * @return
     */
    static String getUsername(String uri) {
        //根据uri获取username
        String temp = uri.substring(uri.indexOf("username=") + "username=".length());
        String username = temp.substring(0, temp.indexOf(SEPARATOR));
        return username;
    }

    /**
     * 获取密码
     *
     * @param uri
     * @return
     */
    static String getPassword(String uri) {
        //根据uri获取password
        String temp = uri.substring(uri.indexOf("password=") + "password=".length());
        String password;
        if (!temp.endsWith(SEPARATOR)) {
            password = temp;
        } else {
            password = temp.substring(0, temp.indexOf(SEPARATOR));
        }
        SM4Utils sm4 = new SM4Utils();
        return sm4.decryptData_ECB(password);
    }

    /**
     * 根据uri获取jdbc连接
     *
     * @param uri
     * @return
     */
    static String getUri(String uri) {
        //根据uri获取jdbc连接
        if (uri.contains(SPLIT_URL_FLAG)) {//url包含？ -- jdbc:mysql://192.168.0.193:3306/data_source?username=root
            return String.valueOf(new StringBuffer(uri.substring(0, uri.indexOf(SPLIT_URL_FLAG))).append(SPLIT_URL_FLAG).append(SQL_CHARACTER));
        } else if (uri.contains(SEPARATOR)) {//url不包含？但包含|  -- jdbc:mysql://192.168.0.193:3306/data_source|username=root
            return String.valueOf(new StringBuffer(uri.substring(0, uri.indexOf(SEPARATOR))).append(SPLIT_URL_FLAG).append(SQL_CHARACTER));
        } else {//url不包含？和| -- jdbc:mysql://192.168.0.193:3306/daas
            return String.valueOf(new StringBuffer(uri).append(SPLIT_URL_FLAG).append(SQL_CHARACTER));
        }
    }

    /**
     * 取得数据库名称
     *
     * @param uri
     * @return
     */
    static String getDatabaseName(String uri) {
        String s = StrUtil.subBefore(uri, "?", false);
        return StrUtil.subAfter(s, "/", true);
    }

    public Hashtable getPools() {
        return pools;
    }

    public String createPools(String uri, Integer type) {
        //数据库连接信息
        //jdbc:mysql://192.168.0.193:3306/t02?useSSL=false&useUnicode=true&characterEncoding=utf8
        // |username=root|password=92Z2cSfUPcbIJxiedl07og==
        String url = getUri(uri);
        String username = getUsername(uri);
        String password = getPassword(uri);
        String poolName = uri;
        String drvier = getDrvierByType(type);
        DBConnectionPool pool = new DBConnectionPool(poolName, drvier, url, username, password, defaultConn);
        pools.put(poolName, pool);
        return poolName;
    }

    public void freeConnection(String uri, Connection conn) {
        if (conn == null) {
            return;
        }
        DBConnectionPool pool = (DBConnectionPool) pools.get(uri);
        if (pool != null) {
            pool.freeConnection(conn);
        }
    }

    /**
     * @param uri
     * @param type
     * @return
     */
    public Connection getConnection(String uri, Integer type) {
        DBConnectionPool pool = (DBConnectionPool) pools.get(uri);
        if (pool == null) {
            createPools(uri, type);
            pool = (DBConnectionPool) pools.get(uri);
        }
        return pool.getConnection();
    }

    public synchronized void release() {
        if (--clients != 0) {
            return;
        }

        Enumeration allPools = pools.elements();
        while (allPools.hasMoreElements()) {
            DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
            pool.release();
        }

        Enumeration allDrivers = drivers.elements();
        while (allDrivers.hasMoreElements()) {
            Driver driver = (Driver) allDrivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
            }
        }
    }

    public class DBConnectionPool {
        private final Vector freeConnections = new Vector();
        private final int maxConn;
        private final String name;
        private final String driver;
        private final String password;
        private final String URL;
        private final String user;
        private int checkedOut;

        public DBConnectionPool(String name, String driver, String URL, String user, String password, int maxConn) {
            this.name = name;
            this.driver = driver;
            this.URL = URL;
            this.user = user;
            this.password = password;
            if (maxConn <= 1) {
                throw new IllegalArgumentException("maxConn must > 1");
            }
            this.maxConn = maxConn;
        }

        public synchronized void freeConnection(Connection con) {
            freeConnections.add(con);
            checkedOut--;
            notifyAll();
        }

        public synchronized Connection getConnection() {
            Connection con = null;
            if (freeConnections.size() > 0) {
                con = (Connection) freeConnections.firstElement();
                freeConnections.removeElementAt(0);
                try {
                    if (con.isClosed()) {
                        con = getConnection();
                    }
                } catch (SQLException e) {
                    con = getConnection();
                }
            } else if (maxConn == 0 || checkedOut < maxConn) {
                con = newConnection();
            }
            if (con != null) {
                checkedOut++;
            }
            return con;
        }

        public synchronized void release() {
            Enumeration allConnections = freeConnections.elements();
            while (allConnections.hasMoreElements()) {
                Connection con = (Connection) allConnections.nextElement();
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
            freeConnections.removeAllElements();
        }

        private Connection newConnection() {
            Connection con = null;
            try {
                Properties props =new Properties();
                Class.forName(driver);
                props.setProperty("remarks", "true"); //设置可以获取remarks信息
                props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
                if (user != null) {
                    props.setProperty("user", user);
                    props.setProperty("password", password);
                }
                con = DriverManager.getConnection(URL, props);
                if (URL.contains("schema=")){
                    String schema = getSchema(URL);
                    con.setSchema(schema);
                }
            } catch (Exception e) {
                return null;
            }
            return con;
        }

        /**
         * 获取表空间
         * @param uri
         * @return
         */
        public String getSchema(String uri) {
            if (!uri.contains("schema=")){
                return null;
            }
            //根据uri获取username
            int index = uri.indexOf("schema=") + "schema=".length();
            String temp = uri.substring(index);
            if (!uri.substring(index).contains(SEPARATOR)) {
                return temp;
            } else {
                return temp.substring(0, temp.indexOf(SEPARATOR));
            }
        }

        /**
         * 根据uri获取jdbc连接
         *
         * @param uri
         * @return
         */
        private String geturi(String uri) {
            //根据uri获取jdbc连接
            //jdbc:postgresql://127.0.0.1:5432/数据库名
            return uri.substring(0, uri.indexOf(SEPARATOR));
        }
    }
}
