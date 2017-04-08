package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherCredent;
import com.yanxiu.ce.core.basic.entity.TeacherCredentQuery;

/**
 * 证书管理
 * @author tangmin
 * @date 2016-04-12 15:26:41
 */
public interface TeacherCredentService extends BaseService<TeacherCredent, TeacherCredentQuery>{
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
	Boolean checkNameExit(TeacherCredent entity);
	
	/**
	 * 保存或添加，包括附件
	 * @return
	 */
	String saveOrUpdate(Long userId,TeacherCredent entity,String attIds);
	
}
