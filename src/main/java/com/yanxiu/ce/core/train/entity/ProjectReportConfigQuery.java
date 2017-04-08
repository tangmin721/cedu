package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 报名配置管理
 * @author tangmin
 * @date 2016-04-21 16:10:32
 */
public class ProjectReportConfigQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String pid;
	private Boolean pidLike = true;
	
	private String trainCount;
	private Boolean trainCountLike = true;
	
	private String registerType;
	private Boolean registerTypeLike = true;
	
	private String startDate;
	private Boolean startDateLike = true;
	
	private String endDate;
	private Boolean endDateLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Boolean getPidLike() {
		return pidLike;
	}
	public void setPidLike(Boolean pidLike) {
		this.pidLike = pidLike;
	}
	public String getTrainCount() {
		return trainCount;
	}
	public void setTrainCount(String trainCount) {
		this.trainCount = trainCount;
	}
	public Boolean getTrainCountLike() {
		return trainCountLike;
	}
	public void setTrainCountLike(Boolean trainCountLike) {
		this.trainCountLike = trainCountLike;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public Boolean getRegisterTypeLike() {
		return registerTypeLike;
	}
	public void setRegisterTypeLike(Boolean registerTypeLike) {
		this.registerTypeLike = registerTypeLike;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Boolean getStartDateLike() {
		return startDateLike;
	}
	public void setStartDateLike(Boolean startDateLike) {
		this.startDateLike = startDateLike;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Boolean getEndDateLike() {
		return endDateLike;
	}
	public void setEndDateLike(Boolean endDateLike) {
		this.endDateLike = endDateLike;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getMemoLike() {
		return memoLike;
	}
	public void setMemoLike(Boolean memoLike) {
		this.memoLike = memoLike;
	}
	
}