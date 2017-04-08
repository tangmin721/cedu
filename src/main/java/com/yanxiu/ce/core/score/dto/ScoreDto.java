package com.yanxiu.ce.core.score.dto;

import java.util.List;

/**
 * 封装  返回给研修网的获取学分的接口
 * @author tangmin
 * @date 2016年8月11日
 */
public class ScoreDto {
	
	/**
	 * 已获总学分
	 */
	private Integer totalScore;
	
	/**
	 * 我的在陪项目
	 */
	private List<MyProjectDto> myTraings;
	
	/**
	 * 我
	 */
	private List<MyProjectDto> myTraneds;
	
	

}
