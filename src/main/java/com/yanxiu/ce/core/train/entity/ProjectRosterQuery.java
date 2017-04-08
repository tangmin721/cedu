package com.yanxiu.ce.core.train.entity;

import java.util.List;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 报名业务管理
 * @author tangmin
 * @date 2016-04-28 10:50:24
 */
public class ProjectRosterQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String pid;
	
	private String rosterNo;
	private Boolean rosterNoLike = true;
	
	private String reportDate;
	private Boolean reportDateLike = true;
	
	private String applyUserId;
	private Boolean applyUserIdLike = true;
	
	private List<Long> statusIds;
	
	private String status;
	
	private String memo;
	private Boolean memoLike = true;
	
	private String checkUserId;
	private Boolean checkUserIdLike = true;
	
	private String checkDate;
	private Boolean checkDateLike = true;
	
	private String checkDesc;
	private Boolean checkDescLike = true;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getRosterNo() {
		return rosterNo;
	}
	public void setRosterNo(String rosterNo) {
		this.rosterNo = rosterNo;
	}
	public Boolean getRosterNoLike() {
		return rosterNoLike;
	}
	public void setRosterNoLike(Boolean rosterNoLike) {
		this.rosterNoLike = rosterNoLike;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public Boolean getReportDateLike() {
		return reportDateLike;
	}
	public void setReportDateLike(Boolean reportDateLike) {
		this.reportDateLike = reportDateLike;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public Boolean getApplyUserIdLike() {
		return applyUserIdLike;
	}
	public void setApplyUserIdLike(Boolean applyUserIdLike) {
		this.applyUserIdLike = applyUserIdLike;
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
	public String getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	public Boolean getCheckUserIdLike() {
		return checkUserIdLike;
	}
	public void setCheckUserIdLike(Boolean checkUserIdLike) {
		this.checkUserIdLike = checkUserIdLike;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public Boolean getCheckDateLike() {
		return checkDateLike;
	}
	public void setCheckDateLike(Boolean checkDateLike) {
		this.checkDateLike = checkDateLike;
	}
	public String getCheckDesc() {
		return checkDesc;
	}
	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}
	public Boolean getCheckDescLike() {
		return checkDescLike;
	}
	public void setCheckDescLike(Boolean checkDescLike) {
		this.checkDescLike = checkDescLike;
	}
	public List<Long> getStatusIds() {
		return statusIds;
	}
	public void setStatusIds(List<Long> statusIds) {
		this.statusIds = statusIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}