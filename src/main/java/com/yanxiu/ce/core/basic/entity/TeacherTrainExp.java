package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 6、培训经历
 * @author tangmin
 * @date 2016年3月30日
 */
public class TeacherTrainExp extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 项目id
	 */
	private Long pid;
	
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;
	
	
	/**
	 * 项目状态
	 */
	private Integer status;
	
	/**
	 * 起始时间  已无用
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="起始日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date startDate;
	
	/**
	 * 结束时间  已无用
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="结束日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date endDate;
	
	/**
	 * 培训类别   字典：TRAIN_LEVEL
	 */
	private Long trainType;
	
	/**
	 * 培训项目名称
	 */
	@Size(max = 100,groups={Insert.class,Update.class})
	private String projectName;
	
	/**
	 * 培训年度  字典表 SCHOOL_YEAR
	 */
	private Long year;
	
	/**
	 * 培训机构名称 
	 */
	private String trainOrg;
	
	/**
	 * 培训方式  字典表  TRAIN_MODE
	 */
	private Long trainMode;
	
	/**
	 * 培训获得学时
	 */
	private Integer hours;
	
	/**
	 * 培训获得学分
	 */
	private Integer score;
	
	/**
	 * 备注  已无用
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;
	
	/**
	 * 培训审核通过标识，通过true
	 * 
	 */
	private boolean flag;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getTrainType() {
		return trainType;
	}

	public void setTrainType(Long trainType) {
		this.trainType = trainType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getTrainOrg() {
		return trainOrg;
	}

	public void setTrainOrg(String trainOrg) {
		this.trainOrg = trainOrg;
	}

	public Long getTrainMode() {
		return trainMode;
	}

	public void setTrainMode(Long trainMode) {
		this.trainMode = trainMode;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}


}
