package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 交流轮岗管理
 * @author tangmin
 * @date 2016-12-29 10:35:05
 */
public class TeacherCommunRotateQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private Long rotateType;
	private Boolean rotateTypeLike = false;
	
	private Long isTranRelation;
	private Boolean isTranRelationLike = false;
	
	private Date startDate;
	private Boolean startDateLike = false;
	
	private Date endDate;
	private Boolean endDateLike = false;
	
	private String oldUnitName;
	private Boolean oldUnitNameLike = false;
	
	private String rotateUnitName;
	private Boolean rotateUnitNameLike = false;
	
	
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
	public Long getRotateType() {
		return rotateType;
	}
	public void setRotateType(Long rotateType) {
		this.rotateType = rotateType;
	}
	public Boolean getRotateTypeLike() {
		return rotateTypeLike;
	}
	public void setRotateTypeLike(Boolean rotateTypeLike) {
		this.rotateTypeLike = rotateTypeLike;
	}
	public Long getIsTranRelation() {
		return isTranRelation;
	}
	public void setIsTranRelation(Long isTranRelation) {
		this.isTranRelation = isTranRelation;
	}
	public Boolean getIsTranRelationLike() {
		return isTranRelationLike;
	}
	public void setIsTranRelationLike(Boolean isTranRelationLike) {
		this.isTranRelationLike = isTranRelationLike;
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
	public String getOldUnitName() {
		return oldUnitName;
	}
	public void setOldUnitName(String oldUnitName) {
		this.oldUnitName = oldUnitName;
	}
	public Boolean getOldUnitNameLike() {
		return oldUnitNameLike;
	}
	public void setOldUnitNameLike(Boolean oldUnitNameLike) {
		this.oldUnitNameLike = oldUnitNameLike;
	}
	public String getRotateUnitName() {
		return rotateUnitName;
	}
	public void setRotateUnitName(String rotateUnitName) {
		this.rotateUnitName = rotateUnitName;
	}
	public Boolean getRotateUnitNameLike() {
		return rotateUnitNameLike;
	}
	public void setRotateUnitNameLike(Boolean rotateUnitNameLike) {
		this.rotateUnitNameLike = rotateUnitNameLike;
	}
	

	
}