package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 师德信息管理
 * @author tangmin
 * @date 2016-12-22 17:08:27
 */
public class TeacherMoralityQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private Date assessDate;
	private Boolean assessDateLike = false;
	
	private Long assessResult;
	private Boolean assessResultLike = false;
	
	private String unitName;
	private Boolean unitNameLike = false;
	
	private Long honorLevel;
	private Boolean honorLevelLike = false;
	
	private Long honorName;
	private Boolean honorNameLike = false;
	
	private Date honorStartDate;
	private Boolean honorStartDateLike = false;
	
	private String honorDesc;
	private Boolean honorDescLike = false;
	
	private String honorAwardUnit;
	private Boolean honorAwardUnitLike = false;
	
	private Long punishType;
	private Boolean punishTypeLike = false;
	
	private Long punishReason;
	private Boolean punishReasonLike = false;
	
	private Date punishStartDate;
	private Boolean punishStartDateLike = false;
	
	private String punishDec;
	private Boolean punishDecLike = false;
	
	private String punishUnit;
	private Boolean punishUnitLike = false;
	
	private Date punishDate;
	private Boolean punishDateLike = false;
	
	private Date punishCancelDate;
	private Boolean punishCancelDateLike = false;
	
	private String punishCancelReason;
	private Boolean punishCancelReasonLike = false;
	
	
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
	public Date getAssessDate() {
		return assessDate;
	}
	public void setAssessDate(Date assessDate) {
		this.assessDate = assessDate;
	}
	public Boolean getAssessDateLike() {
		return assessDateLike;
	}
	public void setAssessDateLike(Boolean assessDateLike) {
		this.assessDateLike = assessDateLike;
	}
	public Long getAssessResult() {
		return assessResult;
	}
	public void setAssessResult(Long assessResult) {
		this.assessResult = assessResult;
	}
	public Boolean getAssessResultLike() {
		return assessResultLike;
	}
	public void setAssessResultLike(Boolean assessResultLike) {
		this.assessResultLike = assessResultLike;
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
	public Long getHonorLevel() {
		return honorLevel;
	}
	public void setHonorLevel(Long honorLevel) {
		this.honorLevel = honorLevel;
	}
	public Boolean getHonorLevelLike() {
		return honorLevelLike;
	}
	public void setHonorLevelLike(Boolean honorLevelLike) {
		this.honorLevelLike = honorLevelLike;
	}
	public Long getHonorName() {
		return honorName;
	}
	public void setHonorName(Long honorName) {
		this.honorName = honorName;
	}
	public Boolean getHonorNameLike() {
		return honorNameLike;
	}
	public void setHonorNameLike(Boolean honorNameLike) {
		this.honorNameLike = honorNameLike;
	}
	public Date getHonorStartDate() {
		return honorStartDate;
	}
	public void setHonorStartDate(Date honorStartDate) {
		this.honorStartDate = honorStartDate;
	}
	public Boolean getHonorStartDateLike() {
		return honorStartDateLike;
	}
	public void setHonorStartDateLike(Boolean honorStartDateLike) {
		this.honorStartDateLike = honorStartDateLike;
	}
	public String getHonorDesc() {
		return honorDesc;
	}
	public void setHonorDesc(String honorDesc) {
		this.honorDesc = honorDesc;
	}
	public Boolean getHonorDescLike() {
		return honorDescLike;
	}
	public void setHonorDescLike(Boolean honorDescLike) {
		this.honorDescLike = honorDescLike;
	}
	public String getHonorAwardUnit() {
		return honorAwardUnit;
	}
	public void setHonorAwardUnit(String honorAwardUnit) {
		this.honorAwardUnit = honorAwardUnit;
	}
	public Boolean getHonorAwardUnitLike() {
		return honorAwardUnitLike;
	}
	public void setHonorAwardUnitLike(Boolean honorAwardUnitLike) {
		this.honorAwardUnitLike = honorAwardUnitLike;
	}
	public Long getPunishType() {
		return punishType;
	}
	public void setPunishType(Long punishType) {
		this.punishType = punishType;
	}
	public Boolean getPunishTypeLike() {
		return punishTypeLike;
	}
	public void setPunishTypeLike(Boolean punishTypeLike) {
		this.punishTypeLike = punishTypeLike;
	}
	public Long getPunishReason() {
		return punishReason;
	}
	public void setPunishReason(Long punishReason) {
		this.punishReason = punishReason;
	}
	public Boolean getPunishReasonLike() {
		return punishReasonLike;
	}
	public void setPunishReasonLike(Boolean punishReasonLike) {
		this.punishReasonLike = punishReasonLike;
	}
	public Date getPunishStartDate() {
		return punishStartDate;
	}
	public void setPunishStartDate(Date punishStartDate) {
		this.punishStartDate = punishStartDate;
	}
	public Boolean getPunishStartDateLike() {
		return punishStartDateLike;
	}
	public void setPunishStartDateLike(Boolean punishStartDateLike) {
		this.punishStartDateLike = punishStartDateLike;
	}
	public String getPunishDec() {
		return punishDec;
	}
	public void setPunishDec(String punishDec) {
		this.punishDec = punishDec;
	}
	public Boolean getPunishDecLike() {
		return punishDecLike;
	}
	public void setPunishDecLike(Boolean punishDecLike) {
		this.punishDecLike = punishDecLike;
	}
	public String getPunishUnit() {
		return punishUnit;
	}
	public void setPunishUnit(String punishUnit) {
		this.punishUnit = punishUnit;
	}
	public Boolean getPunishUnitLike() {
		return punishUnitLike;
	}
	public void setPunishUnitLike(Boolean punishUnitLike) {
		this.punishUnitLike = punishUnitLike;
	}
	public Date getPunishDate() {
		return punishDate;
	}
	public void setPunishDate(Date punishDate) {
		this.punishDate = punishDate;
	}
	public Boolean getPunishDateLike() {
		return punishDateLike;
	}
	public void setPunishDateLike(Boolean punishDateLike) {
		this.punishDateLike = punishDateLike;
	}
	public Date getPunishCancelDate() {
		return punishCancelDate;
	}
	public void setPunishCancelDate(Date punishCancelDate) {
		this.punishCancelDate = punishCancelDate;
	}
	public Boolean getPunishCancelDateLike() {
		return punishCancelDateLike;
	}
	public void setPunishCancelDateLike(Boolean punishCancelDateLike) {
		this.punishCancelDateLike = punishCancelDateLike;
	}
	public String getPunishCancelReason() {
		return punishCancelReason;
	}
	public void setPunishCancelReason(String punishCancelReason) {
		this.punishCancelReason = punishCancelReason;
	}
	public Boolean getPunishCancelReasonLike() {
		return punishCancelReasonLike;
	}
	public void setPunishCancelReasonLike(Boolean punishCancelReasonLike) {
		this.punishCancelReasonLike = punishCancelReasonLike;
	}
	
	
}