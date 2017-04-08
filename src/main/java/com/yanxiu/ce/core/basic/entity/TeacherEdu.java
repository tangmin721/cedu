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
 * 3、教学 和 学历
 * @author tangmin
 * @date 2016年3月30日
 */
public class TeacherEdu extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 最高学历   字典：DEGREE
	 */
	private Long degree;
	
	/**
	 * 所学专业
	 */
	private String major;
	
	/**
	 * 毕业院校
	 */
	private String univ;
	
	
	/**
	 * 从教时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="从教时间只能为过去的时间",groups={Insert.class,Update.class})
	private Date teachedDay;
	
	/**
	 * 任职时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="任职时间只能为过去的时间",groups={Insert.class,Update.class})
	private Date workedDay;
	
	/**
	 * 学段    Stage类
	 */
	private Long stage;
	
	/**
	 * 学科 Course类
	 */
	private Long course;
	
	/**
	 * 年级  Grade类
	 */
	private Long grade;
	
	/**
	 * 在岗状态  字典 JOB_STATUS
	 **/
	private Long jobStatus;
	
	/**
	 * 备注
	 * @return
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getDegree() {
		return degree;
	}

	public void setDegree(Long degree) {
		this.degree = degree;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getUniv() {
		return univ;
	}

	public void setUniv(String univ) {
		this.univ = univ;
	}

	public Date getTeachedDay() {
		return teachedDay;
	}

	public void setTeachedDay(Date teachedDay) {
		this.teachedDay = teachedDay;
	}

	public Date getWorkedDay() {
		return workedDay;
	}

	public void setWorkedDay(Date workedDay) {
		this.workedDay = workedDay;
	}

	public Long getStage() {
		return stage;
	}

	public void setStage(Long stage) {
		this.stage = stage;
	}

	public Long getCourse() {
		return course;
	}

	public void setCourse(Long course) {
		this.course = course;
	}

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Long jobStatus) {
		this.jobStatus = jobStatus;
	}

}
