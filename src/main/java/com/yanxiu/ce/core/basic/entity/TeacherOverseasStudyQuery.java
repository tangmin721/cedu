package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 海外研修(访学)管理
 * @author tangmin
 * @date 2016-12-28 12:31:41
 */
public class TeacherOverseasStudyQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private Long isOverseasExp;
	private Boolean isOverseasExpLike = false;
	
	private Date startDate;
	private Boolean startDateLike = false;
	
	private Date endDate;
	private Boolean endDateLike = false;
	
	private Long nation;
	private Boolean nationLike = false;
	
	private String orgName;
	private Boolean orgNameLike = false;
	
	private String projectName;
	private Boolean projectNameLike = false;
	
	private String unitName;
	private Boolean unitNameLike = false;
	
	
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
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Boolean getSeqLike() {
		return seqLike;
	}
	public void setSeqLike(Boolean seqLike) {
		this.seqLike = seqLike;
	}
	public Long getIsOverseasExp() {
		return isOverseasExp;
	}
	public void setIsOverseasExp(Long isOverseasExp) {
		this.isOverseasExp = isOverseasExp;
	}
	public Boolean getIsOverseasExpLike() {
		return isOverseasExpLike;
	}
	public void setIsOverseasExpLike(Boolean isOverseasExpLike) {
		this.isOverseasExpLike = isOverseasExpLike;
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
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Boolean getEndDateLike() {
		return endDateLike;
	}
	public void setEndDateLike(Boolean endDateLike) {
		this.endDateLike = endDateLike;
	}
	public Long getNation() {
		return nation;
	}
	public void setNation(Long nation) {
		this.nation = nation;
	}
	public Boolean getNationLike() {
		return nationLike;
	}
	public void setNationLike(Boolean nationLike) {
		this.nationLike = nationLike;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Boolean getOrgNameLike() {
		return orgNameLike;
	}
	public void setOrgNameLike(Boolean orgNameLike) {
		this.orgNameLike = orgNameLike;
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
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Boolean getUnitNameLike() {
		return unitNameLike;
	}
	public void setUnitNameLike(Boolean unitNameLike) {
		this.unitNameLike = unitNameLike;
	}
	
	
}