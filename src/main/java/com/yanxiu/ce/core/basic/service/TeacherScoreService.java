package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherScore;
import com.yanxiu.ce.core.basic.entity.TeacherScoreQuery;

/**
 * 学时学分管理
 * @author tangmin
 * @date 2016-05-11 15:11:39
 */
public interface TeacherScoreService extends BaseService<TeacherScore, TeacherScoreQuery>{
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
	Boolean checkNameExit(TeacherScore entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherScore entity);

}
