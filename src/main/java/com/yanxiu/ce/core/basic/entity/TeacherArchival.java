package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 2、教师档案
 * @author tangmin
 * @date 2016年3月30日
 */
public class TeacherArchival extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 人员类别：专人教师、兼任教师    字典：PERSON_TYPE
	 */
	private Long personType;
	
	/**
	 * 行政职务  行政类型 DUTY_TYPE
	 */
	private Long duty;
	
	/**
	 * 职称    字典：TITLE_TYPE
	 */
	private Long title;
	
	/**
	 * 教师资格   字典：QUALIFY_TYPE
	 */
	private Long qualify;
	
	/**
	 * 骨干类型  字典 BONE_TYPE
	 */
	private Long boneType;
	
	/**
	 * 岗位等级   字典 JOB_LEVEL
	 */
	private Long jobLevel;
	
	/**
	 * 政治面貌    字典POLITIC_TYPE
	 */
	private Long politic;
	
	/**
	 * 入党时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="入党时间只能为过去的时间",groups={Insert.class,Update.class})
	private Date joinDay;
	
	/**
	 * 备注
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getPersonType() {
		return personType;
	}

	public void setPersonType(Long personType) {
		this.personType = personType;
	}

	public Long getDuty() {
		return duty;
	}

	public void setDuty(Long duty) {
		this.duty = duty;
	}

	public Long getTitle() {
		return title;
	}

	public void setTitle(Long title) {
		this.title = title;
	}

	public Long getQualify() {
		return qualify;
	}

	public void setQualify(Long qualify) {
		this.qualify = qualify;
	}

	public Long getBoneType() {
		return boneType;
	}

	public void setBoneType(Long boneType) {
		this.boneType = boneType;
	}

	public Long getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(Long jobLevel) {
		this.jobLevel = jobLevel;
	}

	public Long getPolitic() {
		return politic;
	}

	public void setPolitic(Long politic) {
		this.politic = politic;
	}

	public Date getJoinDay() {
		return joinDay;
	}

	public void setJoinDay(Date joinDay) {
		this.joinDay = joinDay;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
