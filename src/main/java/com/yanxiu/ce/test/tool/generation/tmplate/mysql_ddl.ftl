/*
注意：为了不侵入实体类的代码，本ddl语句仅供创建字段，
为了数据库最多性能，请建表后调整各字段的：类型，长度，注释 等，需要加索引的列也加上
*/

CREATE TABLE `${entity.tableName}` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
<#list entity.myfieldList as e>  `${e.columnName}` varchar(50) NOT NULL COMMENT '@TODO注释',
</#list>	
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;