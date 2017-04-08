package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherPostEngage;
import com.yanxiu.ce.core.basic.entity.TeacherPostEngageQuery;

/**
 * 岗位聘任管理
 * @author tangmin
 * @date 2016-12-13 17:55:25
 */
public interface TeacherPostEngageService extends BaseService<TeacherPostEngage, TeacherPostEngageQuery>{
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
	Boolean checkNameExit(TeacherPostEngage entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherPostEngage entity);

}
