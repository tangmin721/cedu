package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherCommunRotate;
import com.yanxiu.ce.core.basic.entity.TeacherCommunRotateQuery;

/**
 * 交流轮岗管理
 * @author tangmin
 * @date 2016-12-29 10:35:05
 */
public interface TeacherCommunRotateService extends BaseService<TeacherCommunRotate, TeacherCommunRotateQuery>{
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
	Boolean checkNameExit(TeacherCommunRotate entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherCommunRotate entity);

}
