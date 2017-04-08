package com.yanxiu.ce.core.train.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrg;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrgQuery;

/**
 * 项目培训机构管理
 * @author tangmin
 * @date 2016-05-10 15:22:39
 */
public interface ProjectTrainOrgService extends BaseService<ProjectTrainOrg, ProjectTrainOrgQuery>{
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
	Boolean checkNameExit(ProjectTrainOrg entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	Long saveTrainOrgs(List<Long> tidList, Long applyId);
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkPidOrgidExit(ProjectTrainOrg entity);
	

}
