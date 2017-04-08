package com.yanxiu.ce.core.basic.service;

import java.util.List;
import java.util.Map;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.basic.entity.StageQuery;

/**
 * 学段管理
 * @author tangmin
 * @date 2016-04-01 11:06:21
 */
public interface StageService extends BaseService<Stage, StageQuery>{
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
	Boolean checkNameExit(Stage entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Stage entity);
	
	/**
	 * 学段下拉框
	 * @return
	 */
	List<Stage> selectStages();

	/**
	 * 根据ids 彻底删除  删除同时删除课程和年级的
	 * @param id
	 * @return  返回删除的条数
	 */
	Long deleteByIdsAndCg(List<Long> ids);
	
	/**
	 * 学段,Id  Name  Map  用于Excel导入判断
	 * @return
	 */
	Map<String,Long> stageMap();
	
	/**
	 * 同步redis缓存
	 */
	public Long syncRedisStageMap();
}
