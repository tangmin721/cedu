package com.yanxiu.ce.core.basic.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherAssess;
import com.yanxiu.ce.core.basic.entity.TeacherAssessQuery;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExp;

/**
 * 考核情况管理
 * @author tangmin
 * @date 2016-05-23 14:46:57
 */
public interface TeacherAssessService extends BaseService<TeacherAssess, TeacherAssessQuery>{
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
	Boolean checkNameExit(TeacherAssess entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherAssess entity);

	/**
	 * 全部保存
	 */
	String saveAssesses(List<TeacherAssess>  exps);
	
	/**
	 * 根据tid删除TeacherTrainExp
	 */
	public Long deleteByTid(Long tid);
}
