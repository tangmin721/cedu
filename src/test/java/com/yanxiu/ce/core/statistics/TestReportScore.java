package com.yanxiu.ce.core.statistics;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.yanxiu.ce.core.statistics.dao.ReportScoreDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.service.ReportScoreService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestReportScore extends SpringJunitTest {
	
	@Autowired
	private ReportScoreService reportScoreService;
	
	@Autowired
	private ReportScoreDao reportScoreDao;
	
	@Test
	@Rollback(false)
	public void test(){
		reportScoreService.jobTotalScore();
	}

	@Test
	public void test2(){
		List<NameValue> groupByProperty = reportScoreDao.groupByProperty("Name","BONETYPE","sys_dict_item","id",  null,null,null,null,"2015-09-09", "2016-08-24", 0, 72);
		System.out.println(groupByProperty);
	}
}
