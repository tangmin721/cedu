package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherOverseasStudy;
import com.yanxiu.ce.core.basic.entity.TeacherOverseasStudyQuery;

/**
 * 海外研修(访学)管理
 * @author tangmin
 * @date 2016-12-28 12:31:42
 */
public interface TeacherOverseasStudyService extends BaseService<TeacherOverseasStudy, TeacherOverseasStudyQuery>{
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
	Boolean checkNameExit(TeacherOverseasStudy entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherOverseasStudy entity);

}
