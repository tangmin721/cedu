package com.yanxiu.ce.core.train.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.ProjectReportConfig;
import com.yanxiu.ce.core.train.entity.ProjectReportConfigQuery;

/**
 * 报名配置管理
 * @author tangmin
 * @date 2016-04-21 16:10:32
 */
public interface ProjectReportConfigService extends BaseService<ProjectReportConfig, ProjectReportConfigQuery>{
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
	Boolean checkNameExit(ProjectReportConfig entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	ProjectReportConfig saveOrUpdate(ProjectReportConfig entity);
	
	/**
	 * 根据pid查询  1：1
	 * @param id
	 * @return
	 */
	ProjectReportConfig selectByPid(Long pid);
	
	/**
	 * 根据pid获取项目配置的总人数
	 * @param pid
	 * @return
	 */
	Integer countPidNum(Long pid);

}
