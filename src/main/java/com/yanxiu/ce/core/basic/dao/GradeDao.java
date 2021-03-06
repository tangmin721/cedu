package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.Grade;
import com.yanxiu.ce.core.basic.entity.GradeQuery;

/**
 * 年级管理
 * @author tangmin
 * @date 2016-04-01 12:05:00
 */
@MybatisDao
public interface GradeDao extends BaseDao<Grade, GradeQuery>{
	
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
	public Long selectCheckNameExit(@Param("name")String name,@Param("stageId")Long stageId,@Param("id")Long id);
	
	/**
	 * 根据stageId删除
	 * @param stageId
	 * @return
	 */
	Long deleteByStageId(@Param("stageId")Long stageId);
	
}
