package com.yanxiu.ce.core.statistics.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 数据汇总时间记录
 * @author guowenyi
 * @date 2017年2月14日
 */
public class SummaryTimeRecord extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2135666491058792888L;

	/**
	 * 模块名称
	 */
	private String moduleName;
	
	/**
	 * 汇总时间
	 */
	private Date summaryTime;
	

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Date getSummaryTime() {
		return summaryTime;
	}

	public void setSummaryTime(Date summaryTime) {
		this.summaryTime = summaryTime;
	}
	
}
