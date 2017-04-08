package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherMorality;
import com.yanxiu.ce.core.basic.entity.TeacherMoralityQuery;

/**
 * 师德信息管理
 * @author tangmin
 * @date 2016-12-22 17:08:27
 */
public interface TeacherMoralityService extends BaseService<TeacherMorality, TeacherMoralityQuery>{
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
	Boolean checkNameExit(TeacherMorality entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherMorality entity);

}
