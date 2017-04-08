package com.yanxiu.ce.core.score.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

public class ScoreMineDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * score的id
	 */
	private Long sid;
	
	/**
	 * 学习课程
	 */
	private String courseName;

	/**
	 * 课程学时
	 */
	private Integer hour;
	
	/**
	 * 课程授课专家
	 */
	private String experter;

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public String getExperter() {
		return experter;
	}

	public void setExperter(String experter) {
		this.experter = experter;
	}
	
	
}
