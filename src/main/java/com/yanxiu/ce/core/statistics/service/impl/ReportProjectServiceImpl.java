package com.yanxiu.ce.core.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanxiu.ce.core.statistics.dao.ReportProjectDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.service.ReportProjectService;

@Service("reportProjectService")
public class ReportProjectServiceImpl implements ReportProjectService{
	
	@Autowired
	private ReportProjectDao dao;

	@Override
	public List<NameValue> groupByProperty(String property, String tableName,
			Integer province, Integer city, Integer town, Long school,
			Long schoolYear) {
		return this.dao.groupByProperty(property, tableName, province, city, town, school, schoolYear);
	}

}
