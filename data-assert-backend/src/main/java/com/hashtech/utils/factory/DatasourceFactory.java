package com.hashtech.utils.factory;

import com.hashtech.utils.factory.common.DatasourceTypeEnum;
import com.hashtech.utils.factory.impl.MysqlDatasource;
import com.hashtech.utils.factory.impl.OracleDatasource;
import com.hashtech.utils.factory.impl.PostgreDatasource;

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
