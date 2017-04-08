package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 培训情况管理
 * @author tangmin
 * @date 2016-05-23 17:30:41
 */
public class TeacherTrainExpQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String pid;
	private Boolean pidLike = false;
	
	private String status;
	private Boolean statusLike = false;
	
	private String startDate;
	private Boolean startDateLike = true;
	
	private String endDate;
	private Boolean endDateLike = true;
	
	private String trainType;
	private Boolean trainTypeLike = true;
	
	private String projectName;
	private Boolean projectNameLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	
	private String seq;
	private Boolean seqLike = true;
	

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Boolean getTidLike() {
		return tidLike;
	}
	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
	}
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getStatusLike() {
		return statusLike;
	}
	public void setStatusLike(Boolean statusLike) {
		this.statusLike = statusLike;
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
	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public Boolean getTrainTypeLike() {
		return trainTypeLike;
	}
	public void setTrainTypeLike(Boolean trainTypeLike) {
		this.trainTypeLike = trainTypeLike;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Boolean getProjectNameLike() {
		return projectNameLike;
	}
	public void setProjectNameLike(Boolean projectNameLike) {
		this.projectNameLike = projectNameLike;
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public Boolean getSeqLike() {
		return seqLike;
	}
	public void setSeqLike(Boolean seqLike) {
		this.seqLike = seqLike;
	}
	
}