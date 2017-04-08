package com.yanxiu.ce.core.score.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;

/**
 * teacher汇总
 * @author tangmin
 * @date 2016年8月31日
 */
public class ScoreTeacherQuery extends TeacherQuery{
	private static final long serialVersionUID = 1L;
	
	private Integer startScore;
	private Integer endScore;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date startCheckDay;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date endCheckDay;
	
	public Integer getStartScore() {
		return startScore;
	}
	public void setStartScore(Integer startScore) {
		this.startScore = startScore;
	}
	public Integer getEndScore() {
		return endScore;
	}
	public void setEndScore(Integer endScore) {
		this.endScore = endScore;
	}
	public Date getStartCheckDay() {
		return startCheckDay;
	}
	public void setStartCheckDay(Date startCheckDay) {
		this.startCheckDay = startCheckDay;
	}
	public Date getEndCheckDay() {
		return endCheckDay;
	}
	public void setEndCheckDay(Date endCheckDay) {
		this.endCheckDay = endCheckDay;
	}
	
}
