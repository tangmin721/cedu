package com.yanxiu.ce.core.train.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.ProjectUserStatus;
import com.yanxiu.ce.core.train.entity.ProjectUserStatusQuery;

/**
 * 项目用户状态管理
 * @author tangmin
 * @date 2016-06-23 12:34:37
 */
public interface ProjectUserStatusService extends BaseService<ProjectUserStatus, ProjectUserStatusQuery>{
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
	Boolean checkNameExit(ProjectUserStatus entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(ProjectUserStatus entity);

	/**
	 * 一个项目，根据以下查询条件是唯一的(相当于selectById)
	 * @param pid
	 * @param province
	 * @param city
	 * @param town
	 * @param school
	 * @return
	 */
	ProjectUserStatus selectOneStatus(Long pid,
			Integer province,Integer city,
			Integer town,Long school,Integer userType);

}
