package com.yanxiu.ce.core.statistics.dto;

/**
 * 堆叠条形图  （学段、学科统计）
 * @author tangmin
 * @date 2016年8月26日
 */
public class StackBar {
	
	/**
	 * x轴  学段
	 */
	private String xname;
	
	/**
	 * y轴   学科
	 */
	private String yname;

	
	/**
	 * 学科的value
	 */
	private Integer value;


	public String getXname() {
		return xname;
	}


	public void setXname(String xname) {
		this.xname = xname;
	}


	public String getYname() {
		return yname;
	}


	public void setYname(String yname) {
		this.yname = yname;
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}
	
	
	
}
