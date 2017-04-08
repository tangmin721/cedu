package com.yanxiu.ce.core.statistics.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 学校统计管理
 * @author tangmin
 * @date 2016-08-18 12:00:34
 */
public class ReportSchoolQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	private String way;

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}
	
}