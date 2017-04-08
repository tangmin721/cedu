package com.yanxiu.ce.core.statistics.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.statistics.entity.SummaryTimeRecord;
import com.yanxiu.ce.core.statistics.entity.SummaryTimeRecordQuery;

/**
 * 汇总时间管理
 * @author tangmin
 * @date 2017-02-14 11:27:42
 */
@MybatisDao
public interface SummaryTimeRecordDao extends BaseDao<SummaryTimeRecord, SummaryTimeRecordQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 查找name为@name，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("moduleName")String moduleName,@Param("id")Long id);
	
	/**
	 * 根据模块名称获取上次数据汇总时间
	 */
	public String getSummaryTimeByName(@Param("moduleName")String moduleName);
	
	/**
	 * 根据模块名称更新数据汇总时间
	 */
	public Long updateByName(SummaryTimeRecord entity);
}
