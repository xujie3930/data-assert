/*micro-oauth2-api项目*/
/*更新explainInfo字段长度*/
use daas;
CREATE TABLE `master_data` (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主数据类别id',
                               `name` varchar(255) DEFAULT '' COMMENT '主数据类别描述',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='主数据类别表';

INSERT INTO `daas`.`master_data` (`id`, `name`) VALUES ('1', '法人基础数据');
INSERT INTO `daas`.`master_data` (`id`, `name`) VALUES ('2', '自然人基础数据');
