package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherPay;
import com.yanxiu.ce.core.basic.entity.TeacherPayQuery;

/**
 * 基本待遇管理
 * @author tangmin
 * @date 2016-12-19 11:26:56
 */
public interface TeacherPayService extends BaseService<TeacherPay, TeacherPayQuery>{
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
	Boolean checkNameExit(TeacherPay entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherPay entity);

}
