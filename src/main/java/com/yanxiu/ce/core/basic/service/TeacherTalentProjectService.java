package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherTalentProject;
import com.yanxiu.ce.core.basic.entity.TeacherTalentProjectQuery;

/**
 * 入选人才项目管理
 * @author tangmin
 * @date 2016-12-27 16:21:49
 */
public interface TeacherTalentProjectService extends BaseService<TeacherTalentProject, TeacherTalentProjectQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq(Long tid);
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(TeacherTalentProject entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherTalentProject entity);

}
