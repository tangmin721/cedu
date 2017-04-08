package com.yanxiu.ce.core.train.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.ProjectOperator;
import com.yanxiu.ce.core.train.entity.ProjectOperatorQuery;

/**
 * 项目执行人管理
 * @author tangmin
 * @date 2016-04-19 14:04:52
 */
public interface ProjectOperatorService extends BaseService<ProjectOperator, ProjectOperatorQuery>{
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
	Boolean checkNameExit(ProjectOperator entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(ProjectOperator entity);

}
