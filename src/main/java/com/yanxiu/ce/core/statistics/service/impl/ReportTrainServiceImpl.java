package com.yanxiu.ce.core.statistics.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanxiu.ce.core.statistics.dao.ReportTrainDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.service.ReportTrainService;

/**
 * 学时统计管理
 * @author tangmin
 * @date 2016-08-22 12:22:10
 */
@Service("reportTrainService")
public class ReportTrainServiceImpl implements ReportTrainService{
	private static final String NULL = "未填";
	
	@Autowired
	private ReportTrainDao dao;
	

	@Override
	public List<NameValue> groupByProperty(String iname,String property, String tableName,String joinOnName,
			Integer province, Integer city, Integer town, Long school,
			String startDate, String endDate, Integer startScore,
			Integer endScore) {
		List<NameValue> nvs = this.dao.groupByProperty(iname,property, tableName,joinOnName, province, city, town, school, startDate, endDate, startScore, endScore);
		for(NameValue nv:nvs){
			if(StringUtils.isBlank(nv.getName())){
				nv.setName(NULL);
			}
		}
		return nvs;
	}

	
	
}