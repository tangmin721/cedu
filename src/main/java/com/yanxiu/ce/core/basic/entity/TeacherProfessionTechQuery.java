package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 专业技术职务聘任管理
 * @author tangmin
 * @date 2016-12-16 11:34:36
 */
public class TeacherProfessionTechQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private Long professionDuty;
	private Boolean professionDutyLike = false;
	
	private Date startDate;
	private Boolean startDateLike = false;
	
	private Date endDate;
	private Boolean endDateLike = false;
	
	private String unitName;
	private Boolean unitNameLike = true;
	
	
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
	public Long getProfessionDuty() {
		return professionDuty;
	}
	public void setProfessionDuty(Long professionDuty) {
		this.professionDuty = professionDuty;
	}
	public Boolean getProfessionDutyLike() {
		return professionDutyLike;
	}
	public void setProfessionDutyLike(Boolean professionDutyLike) {
		this.professionDutyLike = professionDutyLike;
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