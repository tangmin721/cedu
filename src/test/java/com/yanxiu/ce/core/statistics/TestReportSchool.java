package com.yanxiu.ce.core.statistics;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;

import com.yanxiu.ce.core.statistics.service.ReportSchoolService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

/**
 * 学校信息统计
 * @author tangmin
 * @date 2016年8月18日
 */
public class TestReportSchool extends SpringJunitTest {
	
	@Value("${SYS.PROVINCE}")
	private Integer SYS_PROVINCE;
	
	@Autowired
	private ReportSchoolService reportSchoolService;
	
	@Test
	@Rollback(false)
	public void processSchool(){
		reportSchoolService.processTotal();
		
	}
	
	
	
	
	

}
