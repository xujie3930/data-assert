package com.hashtech.common;


import java.util.Objects;

public enum DatasourceTypeEnum {
    //数据源分类（1-关系型，2-半结构化，3-NoSql,4-本地文件，5-区块链）
    //这里不同的数据源，只根据type做唯一区分,不同数据源的type不能相同
    MYSQL(1, 1, "mysql", "com.mysql.jdbc.Driver"),
    ORACLE(1, 2, "oracle", "oracle.jdbc.driver.OracleDriver"),
    POSTGRESQL(1, 3, "postgresql", "org.postgresql.Driver"),
    SQLSERVER(1, 4, "sqlServer", "om.microsoft.sqlserver.jdbc.SQLServerDriver"),
    MONGODB(2, 5, "mongoDB", ""),
    DEFAULT(1, 1, "mysql", "com.mysql.jdbc.Driver");
    private final Integer category;
    private final Integer type;
    private final String desc;
    private final String driver;

    DatasourceTypeEnum(Integer category, Integer type, String desc, String driver) {
        this.category = category;
        this.type = type;
        this.desc = desc;
        this.driver = driver;
    }

    public static DatasourceTypeEnum findDatasourceTypeByType(Integer type) {
        DatasourceTypeEnum result = DatasourceTypeEnum.DEFAULT;
        if (Objects.nonNull(type)) {
            for (DatasourceTypeEnum value : DatasourceTypeEnum.values()) {
                if (value.type.equals(type)) {
                    result = value;
                    break;
                }
            }
        }
        return result;
    }

    public Integer getCategory() {
        return category;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getDriver() {
        return driver;
    }
}
