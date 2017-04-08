package com.yanxiu.ce.core.statistics.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 消息管理
 * @author tangmin
 * @date 2016-08-16 11:56:03
 */
public class ReportTeacherQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	private String way;
	
	private String tchtype;
	
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getTchtype() {
		return tchtype;
	}
	public void setTchtype(String tchtype) {
		this.tchtype = tchtype;
	}
	
}