package com.yanxiu.ce.core.statistics.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 汇总时间管理
 * @author tangmin
 * @date 2017-02-14 11:27:41
 */
public class SummaryTimeRecordQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String moduleName;
	private Boolean moduleNameLike = false;
	
	private String summaryTime;
	private Boolean summaryTimeLike = false;
	

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Boolean getModuleNameLike() {
		return moduleNameLike;
	}
	public void setModuleNameLike(Boolean moduleNameLike) {
		this.moduleNameLike = moduleNameLike;
	}
	public String getSummaryTime() {
		return summaryTime;
	}
	public void setSummaryTime(String summaryTime) {
		this.summaryTime = summaryTime;
	}
	public Boolean getSummaryTimeLike() {
		return summaryTimeLike;
	}
	public void setSummaryTimeLike(Boolean summaryTimeLike) {
		this.summaryTimeLike = summaryTimeLike;
	}
	
}