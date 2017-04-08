package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 岗位聘任信息
 * 
 * @author guowenyi
 * @table  t_tch_morality
 * @date 20161222
 */
public class TeacherMorality extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6259944041337145382L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;
	
	/**
	 * 师德考核时间
	 */
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="考核时间只能为过去的时间",groups={Insert.class,Update.class})
	private Date assessDate;
	
	/**
	 * 师德考核结论  对应字典表 ASSESS_RESULT
	 */
	private Long assessResult;
	
	/**
	 * 师德考核单位名称
	 */
	private  String unitName;
	
	/**
	 * 荣誉级别  对应字典表  HONOR_LEVEL
	 */
	private Long honorLevel;
	
	/**
	 * 获得荣誉称号  HONOR_NAME	
	 */
	private Long honorName;
	
	/**
	 * 荣誉发生日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="荣誉发生日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date honorStartDate;
	
	/**
	 * 荣誉记录描述
	 */
	private String honorDesc;
	
	/**
	 * 荣誉授予单位
	 */
	private String honorAwardUnit;
	
	/**
	 * 处分类别  字典表 PUNISH_TYPE
	 */
	private Long punishType;
	
	/**
	 * 处分原因 字典表 PUNISH_REASON
	 */
	private Long punishReason;
	
	/**
	 * 处分发生日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="处分发生日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date punishStartDate;
	
	/**
	 * 处分记录描述
	 */
	private String punishDec;
	
	/**
	 * 处分单位名称
	 */
	private String punishUnit;
	
	/**
	 * 处分日期
	 */
	private Date punishDate;
	
	/**
	 * 处分撤销日期
	 */
	private Date punishCancelDate;
	
	/**
	 * 处分撤销原因
	 */
	private String punishCancelReason;

	
	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Date getAssessDate() {
		return assessDate;
	}

	public void setAssessDate(Date assessDate) {
		this.assessDate = assessDate;
	}

	public Long getAssessResult() {
		return assessResult;
	}

	public void setAssessResult(Long assessResult) {
		this.assessResult = assessResult;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getHonorLevel() {
		return honorLevel;
	}

	public void setHonorLevel(Long honorLevel) {
		this.honorLevel = honorLevel;
	}

	public Long getHonorName() {
		return honorName;
	}

	public void setHonorName(Long honorName) {
		this.honorName = honorName;
	}

	public Date getHonorStartDate() {
		return honorStartDate;
	}

	public void setHonorStartDate(Date honorStartDate) {
		this.honorStartDate = honorStartDate;
	}

	public String getHonorDesc() {
		return honorDesc;
	}

	public void setHonorDesc(String honorDesc) {
		this.honorDesc = honorDesc;
	}

	public String getHonorAwardUnit() {
		return honorAwardUnit;
	}

	public void setHonorAwardUnit(String honorAwardUnit) {
		this.honorAwardUnit = honorAwardUnit;
	}

	public Long getPunishType() {
		return punishType;
	}

	public void setPunishType(Long punishType) {
		this.punishType = punishType;
	}

	public Long getPunishReason() {
		return punishReason;
	}

	public void setPunishReason(Long punishReason) {
		this.punishReason = punishReason;
	}

	public Date getPunishStartDate() {
		return punishStartDate;
	}

	public void setPunishStartDate(Date punishStartDate) {
		this.punishStartDate = punishStartDate;
	}

	public String getPunishDec() {
		return punishDec;
	}

	public void setPunishDec(String punishDec) {
		this.punishDec = punishDec;
	}

	public String getPunishUnit() {
		return punishUnit;
	}

	public void setPunishUnit(String punishUnit) {
		this.punishUnit = punishUnit;
	}

	public Date getPunishDate() {
		return punishDate;
	}

	public void setPunishDate(Date punishDate) {
		this.punishDate = punishDate;
	}

	public Date getPunishCancelDate() {
		return punishCancelDate;
	}

	public void setPunishCancelDate(Date punishCancelDate) {
		this.punishCancelDate = punishCancelDate;
	}

	public String getPunishCancelReason() {
		return punishCancelReason;
	}

	public void setPunishCancelReason(String punishCancelReason) {
		this.punishCancelReason = punishCancelReason;
	}
	
	
}
