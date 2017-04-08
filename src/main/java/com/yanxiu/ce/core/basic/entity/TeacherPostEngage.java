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
 * @table  t_tch_post_engage
 * @date 20161213
 */
public class TeacherPostEngage extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8925143213174745401L;

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
	 * 岗位类别  对应字典表 POST_TYPE
	 */
	@NotNull
	private Integer postType;
	private String postTypeName;
	
	/**
	 * 岗位等级  对应字典表POST_LEVEL
	 */
	@NotNull
	private Integer postLevel;
	private String postLevelName;
	/**
	 * 聘任开始年月
	 */
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="聘任开始年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date startDate;
	
	/**
	 * 是否兼任其他岗位  0：否  1：是   字典表 PARTTIME_FLAG
	 */
	private Integer parttimeFlag;
	
	/**
	 * 兼任岗位类别  字典表 PARTTIME_POST_TYPE
	 */
	private Integer parttimeType; 
	private String parttimeTypeName;
	/**
	 * 兼任岗位等级  对应字典表POST_LEVEL
	 */
	private Integer parttimeLevel;
	private String parttimeLevelName;
	/**
	 * 校级职务  对应字典表  SCHOOL_DUTY_TYPE
	 */
	@NotNull
	private String duty;
	
	/**
	 * 任职开始年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="任职开始年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date dutyStartDate;

	public Integer getPostType() {
		return postType;
	}

	public void setPostType(Integer postType) {
		this.postType = postType;
	}

	public Integer getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(Integer postLevel) {
		this.postLevel = postLevel;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getParttimeFlag() {
		return parttimeFlag;
	}

	public void setParttimeFlag(Integer parttimeFlag) {
		this.parttimeFlag = parttimeFlag;
	}

	public Integer getParttimeType() {
		return parttimeType;
	}

	public void setParttimeType(Integer parttimeType) {
		this.parttimeType = parttimeType;
	}

	public Integer getParttimeLevel() {
		return parttimeLevel;
	}

	public void setParttimeLevel(Integer parttimeLevel) {
		this.parttimeLevel = parttimeLevel;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Date getDutyStartDate() {
		return dutyStartDate;
	}

	public void setDutyStartDate(Date dutyStartDate) {
		this.dutyStartDate = dutyStartDate;
	}

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

	public String getPostLevelName() {
		return postLevelName;
	}

	public void setPostLevelName(String postLevelName) {
		this.postLevelName = postLevelName;
	}

	public String getParttimeTypeName() {
		return parttimeTypeName;
	}

	public void setParttimeTypeName(String parttimeTypeName) {
		this.parttimeTypeName = parttimeTypeName;
	}

	public String getParttimeLevelName() {
		return parttimeLevelName;
	}

	public void setParttimeLevelName(String parttimeLevelName) {
		this.parttimeLevelName = parttimeLevelName;
	}

	public String getPostTypeName() {
		return postTypeName;
	}

	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}
	
}
