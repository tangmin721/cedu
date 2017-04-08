package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicExchange;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicExchangeQuery;

/**
 * 学术交流管理
 * @author tangmin
 * @date 2016-08-24 11:23:09
 */
public interface TeacherAcademicExchangeService extends BaseService<TeacherAcademicExchange, TeacherAcademicExchangeQuery>{
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
	Boolean checkNameExit(TeacherAcademicExchange entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherAcademicExchange entity);

}
