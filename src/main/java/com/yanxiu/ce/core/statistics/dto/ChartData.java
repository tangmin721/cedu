package com.yanxiu.ce.core.statistics.dto;

import java.util.List;

/**
 * Echart3.0  data包装类
 * @author tangmin
 * @date 2016年4月25日
 */
public class ChartData {
	/**
	 * 
	 */
	List<String> categories;
	
	/**
	 * 
	 */
	List<Integer> data;

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}
	
}
