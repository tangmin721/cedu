package com.yanxiu.ce.core.statistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.statistics.dao.SummaryTimeRecordDao;
import com.yanxiu.ce.core.statistics.entity.SummaryTimeRecord;
import com.yanxiu.ce.core.statistics.entity.SummaryTimeRecordQuery;
import com.yanxiu.ce.core.statistics.service.SummaryTimeRecordService;

/**
 * 汇总时间管理
 * @author tangmin
 * @date 2017-02-14 11:27:42
 */
@Service("summaryTimeRecordService")
public class SummaryTimeRecordServiceImpl extends BaseServiceImpl<SummaryTimeRecord, SummaryTimeRecordQuery> implements SummaryTimeRecordService{
	@Autowired
	private SummaryTimeRecordDao dao;

	@Override
	protected BaseDao<SummaryTimeRecord, SummaryTimeRecordQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq() {
		Integer selectMaxSeq = this.dao.selectMaxSeq();
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(SummaryTimeRecord entity) {
		Long count = this.dao.selectCheckNameExit(entity.getModuleName(), null);
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(SummaryTimeRecord entity) {
		String msg = "";
		
		if(!checkNameExit(entity)){
			this.dao.updateByName(entity);
			msg = "编辑成功！";
		}else{
			this.insert(entity);
			msg = "添加成功！";
		}
		
		return msg;
	}

	/**
	 * 根据模块名称获取上次数据汇总时间
	 */
	@Override
	public String getSummaryTimeByName(String moduleName){
		return this.dao.getSummaryTimeByName(moduleName);
	}
}