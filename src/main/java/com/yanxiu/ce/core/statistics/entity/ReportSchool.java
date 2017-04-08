package com.yanxiu.ce.core.statistics.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 学校信息统计
 * @author tangmin
 * @date 2016年8月18日
 */
public class ReportSchool extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Integer province;
	private Integer city;
	private Integer town;
	
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

}
