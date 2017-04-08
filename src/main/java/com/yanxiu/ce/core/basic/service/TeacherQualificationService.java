package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherQualification;
import com.yanxiu.ce.core.basic.entity.TeacherQualificationQuery;

/**
 * 教师资格管理
 * @author tangmin
 * @date 2016-12-20 18:22:10
 */
public interface TeacherQualificationService extends BaseService<TeacherQualification, TeacherQualificationQuery>{
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
	Boolean checkNameExit(TeacherQualification entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherQualification entity);

}
