package com.yanxiu.ce.core.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.core.statistics.dao.ReportSchoolDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportSchool;
import com.yanxiu.ce.core.statistics.entity.ReportSchoolQuery;
import com.yanxiu.ce.core.statistics.enums.ReportSchoolWayEnum;
import com.yanxiu.ce.core.statistics.service.ReportSchoolService;

/**
 * 学校统计管理
 * @author tangmin
 * @date 2016-08-18 12:00:34
 */
@Service("reportSchoolService")
public class ReportSchoolServiceImpl extends BaseServiceImpl<ReportSchool, ReportSchoolQuery> implements ReportSchoolService{
	@Autowired
	private ReportSchoolDao dao;
	
	@Value("${SYS.PROVINCE}")
	private Integer SYS_PROVINCE;

	@Override
	protected BaseDao<ReportSchool, ReportSchoolQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq() {
		Integer selectMaxSeq = this.dao.selectMaxSeq();
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(ReportSchool entity) {
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(ReportSchool entity) {
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		return msg;
	}

	@Override
	public String processTotal() {
		// 清空此表
		this.truncate();
		this.processNum();
		this.processProperty();
		return "执行成功";
	}

	/**
	 * 根据学校数量统计
	 * @return
	 */
	@Override
	public String processNum() {
		
		//1  先统计省的总体
		groupByCityProcess();
		//2 执行 市、区县 的统计
		groupByTownProcess();
		
		return "执行成功";
	}
	
	/**
	 * 0.学校数量统计 统计本省  各市
	 */
	private void groupByCityProcess() {
		List<NameValue> nameValues = this.groupByCity(SYS_PROVINCE);
		
		ReportSchool reportSchool = new ReportSchool();
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		for(NameValue nv:nameValues){
			nvs.add(nv);
			titils.add(nv.getName());
		}
		
		reportSchool.setWay(ReportSchoolWayEnum.NUMBERS.getValue());
		reportSchool.setProvince(SYS_PROVINCE);
		reportSchool.setCity(0);
		reportSchool.setTown(0);
		
		reportSchool.setTitle(JSONObject.toJSONString(titils));
		reportSchool.setData(JSONObject.toJSONString(nvs));
		
		this.saveOrUpdate(reportSchool);
	}
	
	/**
	 * 0.学校数统计 统计本市  各区县（下层调用 各区下的各学校统计）
	 */
	private void groupByTownProcess() {
		List<String> citys = this.distinctByType("city",SYS_PROVINCE,null);
		
		for(String cityStr:citys){
			Integer city = Integer.parseInt(cityStr);
			List<NameValue> nameValues = this.groupByTown(SYS_PROVINCE, city);
			ReportSchool reportSchool = new ReportSchool();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportSchool.setWay(ReportSchoolWayEnum.NUMBERS.getValue());
			reportSchool.setProvince(SYS_PROVINCE);
			reportSchool.setCity(city);
			reportSchool.setTown(0);
			
			reportSchool.setTitle(JSONObject.toJSONString(titils));
			reportSchool.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportSchool);
			
			//统计本区县 下的学校个数
			groupBySchoolProcess(city);
		}
	}
	
	/**
	 * 0.统计学校个数
	 * @param city
	 * @param town
	 */
	private void groupBySchoolProcess(Integer city) {
		List<String> towns = this.distinctByType("town", SYS_PROVINCE, city);
		for(String townStr:towns){
			Integer town = Integer.parseInt(townStr);
			
			List<NameValue> nameValues = this.countTownSchool(SYS_PROVINCE, city, town);
			ReportSchool reportTeacher = new ReportSchool();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportSchoolWayEnum.NUMBERS.getValue());
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
		}
	}

	/**
	 * 根据类型统计
	 * @return
	 */
	@Override
	public String processProperty() {
		//1、统计 学校类型
		this.provinceProperty("category", "sys_dict_item",ReportSchoolWayEnum.CATEGORY.getValue());
		
		//2、统计 学校类别
		this.provinceProperty("type", "sys_dict_item",ReportSchoolWayEnum.TYPE.getValue());
		
		return "执行成功";
	}

	/**
	 * 省级
	 * @param string
	 * @param string2
	 * @param value
	 */
	private void provinceProperty(String property, String tableName,Integer way) {
		List<NameValue> nameValues = this.groupByProperty(property, tableName, SYS_PROVINCE, null, null);
		ReportSchool reportSchool = new ReportSchool();
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		for(NameValue nv:nameValues){
			nvs.add(nv);
			titils.add(nv.getName());
		}
		
		reportSchool.setWay(way);
		reportSchool.setProvince(SYS_PROVINCE);
		reportSchool.setCity(0);
		reportSchool.setTown(0);
		
		reportSchool.setTitle(JSONObject.toJSONString(titils));
		reportSchool.setData(JSONObject.toJSONString(nvs));
		
		this.saveOrUpdate(reportSchool);
		
		this.cityProperty(property,tableName,way);
		
	}

	private void cityProperty(String property, String tableName, Integer way) {
		List<String> citys = this.distinctByType("city",SYS_PROVINCE,null);
		for(String cityStr:citys){
			Integer city = Integer.parseInt(cityStr);
			List<NameValue> nameValues = this.groupByProperty(property, tableName, SYS_PROVINCE, city, null);
			ReportSchool reportSchool = new ReportSchool();
			
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportSchool.setWay(way);
			reportSchool.setProvince(SYS_PROVINCE);
			reportSchool.setCity(city);
			reportSchool.setTown(0);
			
			reportSchool.setTitle(JSONObject.toJSONString(titils));
			reportSchool.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportSchool);
			
			this.townProperty(property, tableName, way, city);
		}
		
	}

	private void townProperty(String property, String tableName, Integer way,
			Integer city) {
		List<String> towns = this.distinctByType("town",SYS_PROVINCE,city);
		
		for(String townStr:towns){
			Integer town = Integer.parseInt(townStr);
			List<NameValue> nameValues =  this.groupByProperty(property, tableName, SYS_PROVINCE, city, town);
			ReportSchool reportSchool = new ReportSchool();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportSchool.setWay(way);
			reportSchool.setProvince(SYS_PROVINCE);
			reportSchool.setCity(city);
			reportSchool.setTown(town);
			
			reportSchool.setTitle(JSONObject.toJSONString(titils));
			reportSchool.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportSchool);
		}
		
	}

	@Override
	public void truncate() {
		this.dao.truncate();
	}

	@Override
	public List<String> distinctByType(String type, Integer province,
			Integer city) {
		return this.dao.distinctByType(type, province, city);
	}

	@Override
	public List<NameValue> groupByCity(Integer province) {
		return this.dao.groupByCity(province);
	}

	@Override
	public List<NameValue> groupByTown(Integer province, Integer city) {
		return this.dao.groupByTown(province, city);
	}

	@Override
	public List<NameValue> groupByProperty(String property, String tableName,
			Integer province, Integer city, Integer town) {
		return this.dao.groupByProperty(property, tableName, province, city, town);
	}

	@Override
	public List<NameValue> countTownSchool(Integer province, Integer city,
			Integer town) {
		return this.dao.countTownSchool(province, city, town);
	}

}