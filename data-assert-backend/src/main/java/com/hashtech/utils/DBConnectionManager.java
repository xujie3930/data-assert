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
import java.util.Vector;

@Slf4j
public class DBConnectionManager {
    private static final String SEPARATOR = "|";
    static private DBConnectionManager instance;
    static private int clients;
    private final Vector drivers = new Vector();
    private Hashtable pools = new Hashtable();
    private final int defaultConn = 5;

    public Hashtable getPools() {
        return pools;
    }

    private DBConnectionManager() {
    }

    static synchronized public DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        clients++;
        return instance;
    }

    public String createPools(String uri, Integer type) {
        //数据库连接信息
        //jdbc:mysql://192.168.0.193:3306/t02?useSSL=false&useUnicode=true&characterEncoding=utf8
        // |username=root|password=92Z2cSfUPcbIJxiedl07og==
        String databaseName = getDatabaseName(uri);
        String url = geturi(uri);
        String username = getUsername(uri);
        String password = getPassword(uri);
        String poolName = uri;
        String drvier = getDrvierByType(type);
        DBConnectionPool pool = new DBConnectionPool(poolName, drvier, url, username, password, defaultConn);
        pools.put(poolName, pool);
        return poolName;
    }

    static String getDrvierByType(Integer type) {
        String driver = DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver();
        return driver;
    }


    public void freeConnection(String uri, Connection conn) {
        if (conn == null){
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
            getConnection(uri, type);
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
                if (user == null) {
                    con = DriverManager.getConnection(URL);
                } else {
                    con = DriverManager.getConnection(URL, user, password);
                }
            } catch (SQLException e) {
                return null;
            }
            return con;
        }
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
     * @param uri
     * @return
     */
    static String geturi(String uri) {
        //根据uri获取jdbc连接
        return uri.substring(0, uri.indexOf(SEPARATOR));
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
}
