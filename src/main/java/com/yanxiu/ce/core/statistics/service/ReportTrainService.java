package com.yanxiu.ce.core.statistics.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.core.statistics.dto.NameValue;

/**
 * 学时统计管理
 * @author tangmin
 * @date 2016-08-22 12:22:10
 */
public interface ReportTrainService{
	
	/**
	 * 根据不同条件  groupBy
	 */
	List<NameValue> groupByProperty(String iname,String property,String joinOnName,String tableName,
			Integer province,Integer city,Integer town,Long school,
			String startDate,String endDate,
			Integer startScore,Integer endScore);
}
