package com.yanxiu.ce.core.score.entity;

import com.yanxiu.ce.core.basic.entity.Teacher;

/**
 * teacher汇总
 * @author tangmin
 * @date 2016年8月31日
 */
public class ScoreTeacher extends Teacher{
	private static final long serialVersionUID = 1L;
	
	private Integer totalScore;
	
	private Double totalScoreNum;

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Double getTotalScoreNum() {
		return totalScoreNum;
	}

	public void setTotalScoreNum(Double totalScoreNum) {
		this.totalScoreNum = totalScoreNum;
	}
	
}
