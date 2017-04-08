package com.yanxiu.ce.core.basic.service;

import java.util.Map;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.Grade;
import com.yanxiu.ce.core.basic.entity.GradeQuery;

/**
 * 年级管理
 * @author tangmin
 * @date 2016-04-01 12:05:00
 */
public interface GradeService extends BaseService<Grade, GradeQuery>{
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
	Boolean checkNameExit(Grade entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Grade entity);
	
	/**
	 * 根据stageId删除
	 * @param stageId
	 * @return
	 */
	Long deleteByStageId(Long stageId);
	
	/**
	 * 学科,Id  Name  Map  用于Excel导入判断
	 * @return
	 */
	Map<String,Long> gradeMap(Long stageId);
	
	/**
	 * 同步redis缓存
	 */
	public Long syncRedisGradeMap(Long stageId);

}
