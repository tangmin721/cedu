package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 岗位聘任管理
 * @author tangmin
 * @date 2016-12-13 17:55:25
 */
public class TeacherPostEngageQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer postType;
	private Boolean postTypeLike = false;
	
	private Integer postLevel;
	private Boolean postLevelLike = false;
	
	private Date startDate;
	private Boolean startDateLike = false;
	
	private Integer parttimeFlag;
	private Boolean parttimeFlagLike = false;
	
	private Integer parttimeType;
	private Boolean parttimeTypeLike = false;
	
	private Integer parttimeLevel;
	private Boolean parttimeLevelLike = false;
	
	private Integer duty;
	private Boolean dutyLike = false;
	
	private Date dutyStartDate;
	private Boolean dutyStartDateLike = false;
	
	private String seq;
	private Boolean seqLike = false;
	

	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public Boolean getTidLike() {
		return tidLike;
	}
	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
	}
	public Integer getPostType() {
		return postType;
	}
	public void setPostType(Integer postType) {
		this.postType = postType;
	}
	public Boolean getPostTypeLike() {
		return postTypeLike;
	}
	public void setPostTypeLike(Boolean postTypeLike) {
		this.postTypeLike = postTypeLike;
	}
	public Integer getPostLevel() {
		return postLevel;
	}
	public void setPostLevel(Integer postLevel) {
		this.postLevel = postLevel;
	}
	public Boolean getPostLevelLike() {
		return postLevelLike;
	}
	public void setPostLevelLike(Boolean postLevelLike) {
		this.postLevelLike = postLevelLike;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Boolean getStartDateLike() {
		return startDateLike;
	}
	public void setStartDateLike(Boolean startDateLike) {
		this.startDateLike = startDateLike;
	}
	public Integer getParttimeFlag() {
		return parttimeFlag;
	}
	public void setParttimeFlag(Integer parttimeFlag) {
		this.parttimeFlag = parttimeFlag;
	}
	public Boolean getParttimeFlagLike() {
		return parttimeFlagLike;
	}
	public void setParttimeFlagLike(Boolean parttimeFlagLike) {
		this.parttimeFlagLike = parttimeFlagLike;
	}
	public Integer getParttimeType() {
		return parttimeType;
	}
	public void setParttimeType(Integer parttimeType) {
		this.parttimeType = parttimeType;
	}
	public Boolean getParttimeTypeLike() {
		return parttimeTypeLike;
	}
	public void setParttimeTypeLike(Boolean parttimeTypeLike) {
		this.parttimeTypeLike = parttimeTypeLike;
	}
	public Integer getParttimeLevel() {
		return parttimeLevel;
	}
	public void setParttimeLevel(Integer parttimeLevel) {
		this.parttimeLevel = parttimeLevel;
	}
	public Boolean getParttimeLevelLike() {
		return parttimeLevelLike;
	}
	public void setParttimeLevelLike(Boolean parttimeLevelLike) {
		this.parttimeLevelLike = parttimeLevelLike;
	}
	public Integer getDuty() {
		return duty;
	}
	public void setDuty(Integer duty) {
		this.duty = duty;
	}
	public Boolean getDutyLike() {
		return dutyLike;
	}
	public void setDutyLike(Boolean dutyLike) {
		this.dutyLike = dutyLike;
	}
	public Date getDutyStartDate() {
		return dutyStartDate;
	}
	public void setDutyStartDate(Date dutyStartDate) {
		this.dutyStartDate = dutyStartDate;
	}
	public Boolean getDutyStartDateLike() {
		return dutyStartDateLike;
	}
	public void setDutyStartDateLike(Boolean dutyStartDateLike) {
		this.dutyStartDateLike = dutyStartDateLike;
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