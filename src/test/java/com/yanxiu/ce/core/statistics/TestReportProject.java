package com.yanxiu.ce.core.statistics;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.service.ReportProjectService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestReportProject extends SpringJunitTest {
	
	@Autowired
	private ReportProjectService reportProjectService;
	
	@Test
	public void test(){
		List<NameValue> nvs = reportProjectService.groupByProperty("trainlevel", "sys_dict_item", 420000, null, null, null, null);
		System.out.println(nvs);
	}

}
