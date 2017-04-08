package com.yanxiu.ce.core.statistics.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 教学信息统计
 * @author tangmin
 * @date 2016年8月15日
 */
public class ReportTeacher extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Integer province;
	private Integer city;
	private Integer town;
	private Long school;
	
	/**
	 * 教师类型 0，教师，1校长
	 */
	private Integer tchtype;
	/**
	 * 查询方式  ReportTeacherWayEnum
	 */
	private Integer way;
	
	/**
	 * ['省市1','省市2']  标题
	 */
	private String title;
	/**
	 * json格式的data   通过定时任务生成65535
	 */
	private String data;
	

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

	public Integer getWay() {
		return way;
	}

	public void setWay(Integer way) {
		this.way = way;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTchtype() {
		return tchtype;
	}

	public void setTchtype(Integer tchtype) {
		this.tchtype = tchtype;
	}
	
}
