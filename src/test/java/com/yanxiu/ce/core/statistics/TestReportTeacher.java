package com.yanxiu.ce.core.statistics;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportTeacher;
import com.yanxiu.ce.core.statistics.enums.ReportTeacherWayEnum;
import com.yanxiu.ce.core.statistics.service.ReportTeacherService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

/**
 * 教师 信息 统计 测试
 * @author tangmin
 * @date 2016年8月17日
 */
public class TestReportTeacher extends SpringJunitTest {
	
	@Value("${SYS.PROVINCE}")
	private Integer SYS_PROVINCE;
	
	@Autowired
	private ReportTeacherService reportTeacherService;
	
	
	
	/**
	 * 人数统计
	 */ 
	@Test
	@Rollback(false)
	public void processTeacher(){
		reportTeacherService.processTeacherTotal();
		
		
	}
	
	/**
	 * 根据属性（学历、学段、学科、职称、骨干类型等）人数统计
	 */
	@Test
	@Rollback(false)
	public void processProperty(){
		reportTeacherService.processProperty(0);
		
	}
	
	

	//清除数据
	@Test
	@Rollback(false)
	public void truncate(){
		reportTeacherService.truncate();
	}
	
	//统计省下各市人数分布
	@Test
	public void distinctByType(){
		List<String> result = reportTeacherService.distinctByType("city",SYS_PROVINCE,null,null,null,0);
		System.out.println(result);
	}
	
	//统计省下各市人数分布
	@Test
	@Rollback(false)
	public void groupByCity(){
		List<NameValue> nameValues = reportTeacherService.groupByCity(SYS_PROVINCE,0);
		
		ReportTeacher reportTeacher = new ReportTeacher();
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		for(NameValue nv:nameValues){
			nvs.add(nv);
			titils.add(nv.getName());
		}
		
		reportTeacher.setWay(ReportTeacherWayEnum.NUMBERS.getValue());
		
		reportTeacher.setProvince(SYS_PROVINCE);
		reportTeacher.setCity(0);
		reportTeacher.setTown(0);
		reportTeacher.setSchool(0l);
		
		reportTeacher.setTitle(JSONObject.toJSONString(titils));
		reportTeacher.setData(JSONObject.toJSONString(nvs));
		
		reportTeacherService.saveOrUpdate(reportTeacher);
	}
	
	//统计市下各 区县 人数分布
	@Test
	@Rollback(false)
	public void groupByTwon(){
		
		List<String> citys = reportTeacherService.distinctByType("city",SYS_PROVINCE,null,null,null,0);
		
		for(String cityStr:citys){
			Integer city = Integer.parseInt(cityStr);
			List<NameValue> nameValues = reportTeacherService.groupByTown(SYS_PROVINCE, city,0);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.NUMBERS.getValue());
			
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(0);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			reportTeacherService.saveOrUpdate(reportTeacher);
			
		}
		
	}
	
	//统计 区县 下各校 人数分布
	@Test
	@Rollback(false)
	public void groupBySchool(){
		Integer city = 420100;
		List<String> towns = reportTeacherService.distinctByType("town",SYS_PROVINCE,city,null,null,0);
		
		for(String townStr:towns){
			Integer town = Integer.parseInt(townStr);
			List<NameValue> nameValues = reportTeacherService.groupBySchool(SYS_PROVINCE, city,town,0);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.NUMBERS.getValue());
			
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			reportTeacherService.saveOrUpdate(reportTeacher);
			
		}
		
	}
	
	//统计 区县 下各校 人数分布
	@Test
	@Rollback(false)
	public void countSchool(){
			Long school = 1l;
		
			List<NameValue> nameValues = reportTeacherService.countSchool(school,0);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.NUMBERS.getValue());
			
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(420100);
			reportTeacher.setTown(420105);
			reportTeacher.setSchool(school);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			reportTeacherService.saveOrUpdate(reportTeacher);
			
		
	}
	
	
	

	@Test
	public void groupByAge(){
		List<NameValue> groupByAge = reportTeacherService.groupByAge(SYS_PROVINCE, null, null, null, 0);
		System.out.println(groupByAge);
	}

	
	@Test
	public void groupByJoinday(){
		List<NameValue> groupByJoinday = reportTeacherService.groupByJoinday(SYS_PROVINCE, null, null, null, 0);
		System.out.println(groupByJoinday);
	}
}
