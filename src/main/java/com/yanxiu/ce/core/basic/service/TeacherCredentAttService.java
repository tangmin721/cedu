package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherCredentAtt;
import com.yanxiu.ce.core.basic.entity.TeacherCredentAttQuery;

/**
 * 附件管理
 * @author tangmin
 * @date 2016-04-13 11:03:13
 */
public interface TeacherCredentAttService extends BaseService<TeacherCredentAtt, TeacherCredentAttQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(TeacherCredentAtt entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherCredentAtt entity);

}
