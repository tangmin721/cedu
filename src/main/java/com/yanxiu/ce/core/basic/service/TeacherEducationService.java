package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherEducation;
import com.yanxiu.ce.core.basic.entity.TeacherEducationQuery;

/**
 * 教育教学管理
 * @author tangmin
 * @date 2016-12-23 16:30:32
 */
public interface TeacherEducationService extends BaseService<TeacherEducation, TeacherEducationQuery>{
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
	Boolean checkNameExit(TeacherEducation entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherEducation entity);

}
