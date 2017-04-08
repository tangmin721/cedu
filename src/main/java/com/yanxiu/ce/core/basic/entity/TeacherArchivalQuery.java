package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 学籍档案管理
 * @author tangmin
 * @date 2016-04-01 18:10:28
 */
public class TeacherArchivalQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String personType;
	private Boolean personTypeLike = true;
	
	private String duty;
	private Boolean dutyLike = true;
	
	private String title;
	private Boolean titleLike = true;
	
	private String qualify;
	private Boolean qualifyLike = true;
	
	private String boneType;
	private Boolean boneTypeLike = true;
	
	private String jobLevel;
	private Boolean jobLevelLike = true;
	
	private String politic;
	private Boolean politicLike = true;
	
	private String joinDay;
	private Boolean joinDayLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	

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
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public Boolean getPersonTypeLike() {
		return personTypeLike;
	}
	public void setPersonTypeLike(Boolean personTypeLike) {
		this.personTypeLike = personTypeLike;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public Boolean getDutyLike() {
		return dutyLike;
	}
	public void setDutyLike(Boolean dutyLike) {
		this.dutyLike = dutyLike;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(Boolean titleLike) {
		this.titleLike = titleLike;
	}
	public String getQualify() {
		return qualify;
	}
	public void setQualify(String qualify) {
		this.qualify = qualify;
	}
	public Boolean getQualifyLike() {
		return qualifyLike;
	}
	public void setQualifyLike(Boolean qualifyLike) {
		this.qualifyLike = qualifyLike;
	}
	public String getBoneType() {
		return boneType;
	}
	public void setBoneType(String boneType) {
		this.boneType = boneType;
	}
	public Boolean getBoneTypeLike() {
		return boneTypeLike;
	}
	public void setBoneTypeLike(Boolean boneTypeLike) {
		this.boneTypeLike = boneTypeLike;
	}
	public String getJobLevel() {
		return jobLevel;
	}
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	public Boolean getJobLevelLike() {
		return jobLevelLike;
	}
	public void setJobLevelLike(Boolean jobLevelLike) {
		this.jobLevelLike = jobLevelLike;
	}
	public String getPolitic() {
		return politic;
	}
	public void setPolitic(String politic) {
		this.politic = politic;
	}
	public Boolean getPoliticLike() {
		return politicLike;
	}
	public void setPoliticLike(Boolean politicLike) {
		this.politicLike = politicLike;
	}
	public String getJoinDay() {
		return joinDay;
	}
	public void setJoinDay(String joinDay) {
		this.joinDay = joinDay;
	}
	public Boolean getJoinDayLike() {
		return joinDayLike;
	}
	public void setJoinDayLike(Boolean joinDayLike) {
		this.joinDayLike = joinDayLike;
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