package com.yanxiu.ce.core.statistics.service;

public interface ReportService {
	/**
	 * 后台定时统计调度的方法
	 * @return
	 */
	String process();
	
	
	/**
	 * 生成测试数据
	 * @param startNumber
	 * @param totalNumber
	 */
	public void addTeachers (Integer startNum,Integer totalNum);
}
