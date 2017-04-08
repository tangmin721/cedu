package com.yanxiu.ce.core.train.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.ProjectQuota;
import com.yanxiu.ce.core.train.entity.ProjectQuotaQuery;

/**
 * 名额分配管理
 * @author tangmin
 * @date 2016-04-21 09:51:46
 */
public interface ProjectQuotaService extends BaseService<ProjectQuota, ProjectQuotaQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq(Long pid);
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(ProjectQuota entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(ProjectQuota entity);
	
	/**
	 * 根据项目id获取已分配名额人数
	 */
	Integer countedPid(Long pid);
	
	/**
	 * 据pid，省市县校 获取记录条数 (判断记录是否已经存在)
	 * @param pid
	 * @param province
	 * @param city
	 * @param town
	 * @param school
	 * @return
	 */
	Integer countPpcts(Long pid,
			Integer province,Integer city,
			Integer town,Long school);
	
	/**
	 * 据pid，省市县校   获取分配的名额
	 * @param pid
	 * @param province
	 * @param city
	 * @param town
	 * @param school
	 * @return
	 */
	Integer numSelectPpcts(Long pid,
			Integer province,Integer city,
			Integer town,Long school);
	
	

}
