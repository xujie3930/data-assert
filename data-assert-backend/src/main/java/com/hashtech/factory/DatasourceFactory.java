package com.hashtech.factory;

import com.hashtech.common.DatasourceTypeEnum;
import com.hashtech.factory.impl.MysqlDatasource;
import com.hashtech.factory.impl.OracleDatasource;
import com.hashtech.factory.impl.PostgreDatasource;

/**
 * @author xujie
 * @description 数据源工厂
 * @create 2021-08-25 15:32
 **/
public class DatasourceFactory {

    public static DatasourceSync getDatasource(Integer type) {
        DatasourceTypeEnum datasourceTypeEnum = DatasourceTypeEnum.findDatasourceTypeByType(type);
        switch (datasourceTypeEnum) {
            case MYSQL:
                return new MysqlDatasource();
            case ORACLE:
                return new OracleDatasource();
            case  POSTGRESQL:
                return new PostgreDatasource();
            default:
                return new MysqlDatasource();
        }
    }
}
