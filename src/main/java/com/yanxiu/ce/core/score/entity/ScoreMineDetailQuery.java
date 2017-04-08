package com.yanxiu.ce.core.score.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 培训电子档案管理
 * @author tangmin
 * @date 2017-01-12 13:34:33
 */
public class ScoreMineDetailQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String sid;
	private Boolean sidLike = true;
	
	private String courseName;
	private Boolean courseNameLike = true;
	
	private String hour;
	private Boolean hourLike = true;
	
	private String experter;
	private Boolean experterLike = true;
	

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Boolean getSidLike() {
		return sidLike;
	}
	public void setSidLike(Boolean sidLike) {
		this.sidLike = sidLike;
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
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public Boolean getHourLike() {
		return hourLike;
	}
	public void setHourLike(Boolean hourLike) {
		this.hourLike = hourLike;
	}
	public String getExperter() {
		return experter;
	}
	public void setExperter(String experter) {
		this.experter = experter;
	}
	public Boolean getExperterLike() {
		return experterLike;
	}
	public void setExperterLike(Boolean experterLike) {
		this.experterLike = experterLike;
	}
	
}