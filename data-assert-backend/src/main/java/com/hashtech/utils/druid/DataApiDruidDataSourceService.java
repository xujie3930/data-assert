package com.hashtech.utils.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.hashtech.utils.factory.DatasourceFactory;
import com.hashtech.utils.factory.DatasourceSync;
import com.hashtech.utils.factory.common.DatasourceTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Project：icreditstudio-dataapi-service
 * Package：com.jinninghui.datasphere.icreditstudio.dataapi.druid
 * ClassName: DataApiDruidDataSourceHelper
 * Description:  DataApiDruidDataSourceHelper类
 * Date: 2022/5/11 12:24
 *
 * @author liyanhui
 */
public final class DataApiDruidDataSourceService {

    /**
     * key : jdbc url
     * value: datasource instance
     */
    public Map<String, DataApiDruidDataSource> map = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(DataApiDruidDataSourceService.class);
    static private DataApiDruidDataSourceService instance;

    private DataApiDruidDataSourceService() {
    }

    static synchronized public DataApiDruidDataSourceService getInstance() {
        if (instance == null) {
            instance = new DataApiDruidDataSourceService();
        }
        return instance;
    }


    /**
     * 用配置项代替
     *
     * @param url
     * @param type
     * @param userName
     * @param password
     * @return
     * @throws SQLException
     */
    DataApiDruidDataSource initDataSource(String url, Integer type, String userName, String password) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("driver", DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver());
        DatasourceSync dataFactory = DatasourceFactory.getDatasource(type);
        String uri = dataFactory.geturi(url);
        prop.setProperty("url", uri);
        prop.setProperty("connectionProperties", "useUnicode=true;characterEncoding=UTF8");
        prop.setProperty("username", userName);
        prop.setProperty("password", password);
        // 设置数据连接池初始化连接池数量
        prop.setProperty("initialSize", "3");
        // 最大连接数
        prop.setProperty("maxActive", "50");
        prop.setProperty("minIdle", "3");
        // 连接最大等待时间60秒
        prop.setProperty("maxWait", "60000");
        prop.setProperty("filters", "stat");
        prop.setProperty("timeBetweenEvictionRunsMillis", "35000");
        prop.setProperty("minEvictableIdleTimeMillis", "30000");
        prop.setProperty("testWhileIdle", "true");
        prop.setProperty("testOnBorrow", "false");
        prop.setProperty("testOnReturn", "false");
        prop.setProperty("poolPreparedStatements", "false");
        prop.setProperty("maxPoolPreparedStatementPerConnectionSize", "200");
        prop.setProperty("removeAbandoned", "true");
        try {
            DruidDataSource druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
            DataApiDruidDataSource dataApiDruidDataSource = new DataApiDruidDataSource(druidDataSource);
            Date now = new Date();
            dataApiDruidDataSource.setCreateDate(now);
            dataApiDruidDataSource.setLastUseDate(now);
            map.put(url, dataApiDruidDataSource);
            return dataApiDruidDataSource;
        } catch (Exception e) {
            logger.error("初始化创建数据源{}连接池失败！", url, e);
            throw e;
        }
    }

    public DruidPooledConnection getOrCreateConnection(String url, Integer type, String userName, String password) throws Exception {
        DataApiDruidDataSource source;
        synchronized (this) {
            if (!map.containsKey(url)) {
                source = initDataSource(url, type, userName, password);
                return (DruidPooledConnection) source.getDruidDataSource().getPooledConnection();
            }
        }
        source = map.get(url);
        source.setLastUseDate(new Date());
        logger.debug("当前数据库连接池的量为：" + source.getDruidDataSource().getActiveConnections().size() + "---" + source.getDruidDataSource().getActiveCount() + "---" + source.getDruidDataSource().getCloseCount());
        return (DruidPooledConnection) source.getDruidDataSource().getPooledConnection();
    }

    public DruidPooledConnection getOrCreateConnectionWithoutUsername(String url, Integer type) throws Exception {
        DataApiDruidDataSource source;
        synchronized (this) {
            if (!map.containsKey(url)) {
                String userName = DatasourceSync.getUsername(url);
                String password = DatasourceSync.getPassword(url);
                source = initDataSource(url, type, userName, password);
                return (DruidPooledConnection) source.getDruidDataSource().getPooledConnection();
            }
        }
        source = map.get(url);
        source.setLastUseDate(new Date());
        logger.debug("当前数据库连接池的量为：" + source.getDruidDataSource().getActiveConnections().size() + "---" + source.getDruidDataSource().getActiveCount() + "---" + source.getDruidDataSource().getCloseCount());
        return (DruidPooledConnection) source.getDruidDataSource().getPooledConnection();
    }

    //定义release方法，释放资源
    public static void release(ResultSet resultSet, Statement statement, Connection connection){
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
