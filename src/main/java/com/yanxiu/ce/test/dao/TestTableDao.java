package com.yanxiu.ce.test.dao;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.test.entity.TestTable;
import com.yanxiu.ce.test.entity.TestTableQuery;

@MybatisDao
public interface TestTableDao extends BaseDao<TestTable, TestTableQuery>{
}
