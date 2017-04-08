package com.yanxiu.ce.core.statistics.service;

import java.util.List;


import com.yanxiu.ce.core.statistics.dto.NameValue;

public interface ReportProjectService {
	/**
	 * 根据不同条件  groupBy
	 */
	List<NameValue> groupByProperty(String property,String tableName,
			Integer province,Integer city,Integer town,Long school,
			Long schoolYear);
}
