package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 教学和学历管理
 * @author tangmin
 * @date 2016-04-05 14:26:29
 */
public class TeacherEduQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String degree;
	private Boolean degreeLike = true;
	
	private String major;
	private Boolean majorLike = true;
	
	private String univ;
	private Boolean univLike = true;
	
	private String teachedDay;
	private Boolean teachedDayLike = true;
	
	private String workedDay;
	private Boolean workedDayLike = true;
	
	private String stage;
	private Boolean stageLike = true;
	
	private String course;
	private Boolean courseLike = true;
	
	private String grade;
	private Boolean gradeLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Boolean getTidLike() {
		return tidLike;
	}
	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Boolean getDegreeLike() {
		return degreeLike;
	}
	public void setDegreeLike(Boolean degreeLike) {
		this.degreeLike = degreeLike;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Boolean getMajorLike() {
		return majorLike;
	}
	public void setMajorLike(Boolean majorLike) {
		this.majorLike = majorLike;
	}
	public String getUniv() {
		return univ;
	}
	public void setUniv(String univ) {
		this.univ = univ;
	}
	public Boolean getUnivLike() {
		return univLike;
	}
	public void setUnivLike(Boolean univLike) {
		this.univLike = univLike;
	}
	public String getTeachedDay() {
		return teachedDay;
	}
	public void setTeachedDay(String teachedDay) {
		this.teachedDay = teachedDay;
	}
	public Boolean getTeachedDayLike() {
		return teachedDayLike;
	}
	public void setTeachedDayLike(Boolean teachedDayLike) {
		this.teachedDayLike = teachedDayLike;
	}
	public String getWorkedDay() {
		return workedDay;
	}
	public void setWorkedDay(String workedDay) {
		this.workedDay = workedDay;
	}
	public Boolean getWorkedDayLike() {
		return workedDayLike;
	}
	public void setWorkedDayLike(Boolean workedDayLike) {
		this.workedDayLike = workedDayLike;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Boolean getStageLike() {
		return stageLike;
	}
	public void setStageLike(Boolean stageLike) {
		this.stageLike = stageLike;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public Boolean getCourseLike() {
		return courseLike;
	}
	public void setCourseLike(Boolean courseLike) {
		this.courseLike = courseLike;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Boolean getGradeLike() {
		return gradeLike;
	}
	public void setGradeLike(Boolean gradeLike) {
		this.gradeLike = gradeLike;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getMemoLike() {
		return memoLike;
	}
	public void setMemoLike(Boolean memoLike) {
		this.memoLike = memoLike;
	}
	
}