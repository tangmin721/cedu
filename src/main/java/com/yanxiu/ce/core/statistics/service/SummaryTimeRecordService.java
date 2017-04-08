package com.yanxiu.ce.core.statistics.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.statistics.entity.SummaryTimeRecord;
import com.yanxiu.ce.core.statistics.entity.SummaryTimeRecordQuery;

/**
 * 汇总时间管理
 * @author tangmin
 * @date 2017-02-14 11:27:42
 */
public interface SummaryTimeRecordService extends BaseService<SummaryTimeRecord, SummaryTimeRecordQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(SummaryTimeRecord entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(SummaryTimeRecord entity);
	
	/**
	 * 根据模块名称获取上次数据汇总时间
	 */
	String getSummaryTimeByName(String moduleName);
	
}
