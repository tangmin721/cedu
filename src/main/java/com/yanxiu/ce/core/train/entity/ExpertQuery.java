package com.yanxiu.ce.core.train.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 专家信息管理
 * @author tangmin
 * @date 2016-07-29 15:49:03
 */
public class ExpertQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Boolean nameLike = true;
	
	private String idCard;
	private Boolean idCardLike = true;
	
	private String dept;
	private Boolean deptLike = true;
	
	private String title;
	private Boolean titleLike = true;
	
	private String mobile;
	private Boolean mobileLike = true;
	
	private String email;
	private Boolean emailLike = true;
	
	private String direction;
	private Boolean directionLike = true;
	
	private String time;
	private Boolean timeLike = true;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date startTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date endTime;
	
	private String goodRate;
	private Boolean goodRateLike = true;
	
	private String courseName;
	private Boolean courseNameLike = true;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getNameLike() {
		return nameLike;
	}
	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Boolean getIdCardLike() {
		return idCardLike;
	}
	public void setIdCardLike(Boolean idCardLike) {
		this.idCardLike = idCardLike;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Boolean getDeptLike() {
		return deptLike;
	}
	public void setDeptLike(Boolean deptLike) {
		this.deptLike = deptLike;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(Boolean titleLike) {
		this.titleLike = titleLike;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Boolean getMobileLike() {
		return mobileLike;
	}
	public void setMobileLike(Boolean mobileLike) {
		this.mobileLike = mobileLike;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getEmailLike() {
		return emailLike;
	}
	public void setEmailLike(Boolean emailLike) {
		this.emailLike = emailLike;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Boolean getDirectionLike() {
		return directionLike;
	}
	public void setDirectionLike(Boolean directionLike) {
		this.directionLike = directionLike;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Boolean getTimeLike() {
		return timeLike;
	}
	public void setTimeLike(Boolean timeLike) {
		this.timeLike = timeLike;
	}
	public String getGoodRate() {
		return goodRate;
	}
	public void setGoodRate(String goodRate) {
		this.goodRate = goodRate;
	}
	public Boolean getGoodRateLike() {
		return goodRateLike;
	}
	public void setGoodRateLike(Boolean goodRateLike) {
		this.goodRateLike = goodRateLike;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Boolean getCourseNameLike() {
		return courseNameLike;
	}
	public void setCourseNameLike(Boolean courseNameLike) {
		this.courseNameLike = courseNameLike;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}