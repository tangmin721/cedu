package com.yanxiu.ce.test.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.test.entity.TestTable;
import com.yanxiu.ce.test.entity.TestTableQuery;

/**
 * 接口层
 * @author tangm
 */
public interface TestTableService extends BaseService<TestTable, TestTableQuery>{
	TestTable testAop();
	
	TestTable testTra();
}
