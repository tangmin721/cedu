package com.yanxiu.ce.test.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.test.dao.TestTableDao;
import com.yanxiu.ce.test.entity.TestTable;
import com.yanxiu.ce.test.entity.TestTableQuery;
import com.yanxiu.ce.test.service.TestTableService;


@Service("testTableService")
public class TestTableServiceImpl extends BaseServiceImpl<TestTable, TestTableQuery> implements TestTableService{

	@Autowired
	private TestTableDao dao;

	@Override
	protected BaseDao<TestTable, TestTableQuery> dao() {
		return this.dao;
	}

	@Override
	public TestTable testAop() {
		System.out.println("testAop");
		 try {
			Thread.sleep(1006);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class}) 
	public TestTable testTra() {
		
		TestTable testTable2 = new TestTable();
		testTable2.setAge(18);
		testTable2.setName("张三nv2");
		testTable2.setSex(1);
		//testTable.setBirthday(date);

		System.out.println(":::id::"+this.insert(testTable2));
		
		TestTable testTable = new TestTable();
		testTable.setAge(181);
		testTable.setName("张三nv1");
		testTable.setSex(1);
		//testTable.setBirthday(date);

		System.out.println(":::id::"+this.insert(testTable));
		return null;
	}



}
