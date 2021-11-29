package com.hashtech.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author xujie
 * @description 获取配置文件的数据库信息
 * @create 2021-10-28 19:39
 **/
public class DatabaseProperty {
    InputStream input = null;
    Properties p = new Properties();

    private static DatabaseProperty instance = null;

    public static DatabaseProperty getInstance(){
        if (instance != null){
            return instance;
        }
        instance = new DatabaseProperty();
        instance.loadPoperty();
        return instance;
    }

    public void loadPoperty() {
        try {
            input = this.getClass().getClassLoader()
                    .getResourceAsStream("database.properties");
            p.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getUsername(){
        return p.getProperty("datasource.username");
    }

    public String getPassword(){
        return p.getProperty("datasource.password");
    }

    public String getUrl(){
        return p.getProperty("datasource.url");
    }

    public static void main(String[] args) {
        DatabaseProperty instance = getInstance();
        String url = instance.getUrl();
        System.out.println(url);
    }
}
