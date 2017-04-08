package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherSubjectStudy;
import com.yanxiu.ce.core.basic.entity.TeacherSubjectStudyQuery;

/**
 * 学术交流管理
 * @author tangmin
 * @date 2016-08-24 11:24:29
 */
public interface TeacherSubjectStudyService extends BaseService<TeacherSubjectStudy, TeacherSubjectStudyQuery>{
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
	Boolean checkNameExit(TeacherSubjectStudy entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherSubjectStudy entity);

}
