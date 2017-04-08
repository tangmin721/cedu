package com.yanxiu.ce.core.statistics.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.entity.BasePctsQuery;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.Town;

/**
 * 项目统计查询条件
 * @author tangmin
 * @date 2016年8月18日
 */
public class ReportProjectQuery implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer province=0;
	private Integer city=0;
	private Integer town=0;
	private Long school=0l;
	
	private Long schoolYear;
	private String way;
	
	List<Province> queryProvinces = Lists.newArrayList();
	List<City> queryCitys = Lists.newArrayList();
	List<Town> queryTowns = Lists.newArrayList();
	List<School> querySchools= Lists.newArrayList();
	
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getTown() {
		return town;
	}
	public void setTown(Integer town) {
		this.town = town;
	}
	public Long getSchool() {
		return school;
	}
	public void setSchool(Long school) {
		this.school = school;
	}
	public List<Province> getQueryProvinces() {
		return queryProvinces;
	}
	public void setQueryProvinces(List<Province> queryProvinces) {
		this.queryProvinces = queryProvinces;
	}
	public List<City> getQueryCitys() {
		return queryCitys;
	}
	public void setQueryCitys(List<City> queryCitys) {
		this.queryCitys = queryCitys;
	}
	public List<Town> getQueryTowns() {
		return queryTowns;
	}
	public void setQueryTowns(List<Town> queryTowns) {
		this.queryTowns = queryTowns;
	}
	public List<School> getQuerySchools() {
		return querySchools;
	}
	public void setQuerySchools(List<School> querySchools) {
		this.querySchools = querySchools;
	}
	public Long getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	
}
