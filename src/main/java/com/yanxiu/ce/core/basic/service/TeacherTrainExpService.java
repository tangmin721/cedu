package com.yanxiu.ce.core.basic.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExp;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExpQuery;
import com.yanxiu.ce.core.basic.entity.TeacherWorkExp;

/**
 * 培训情况管理
 * @author tangmin
 * @date 2016-05-23 17:30:41
 */
public interface TeacherTrainExpService extends BaseService<TeacherTrainExp, TeacherTrainExpQuery>{
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
	Boolean checkNameExit(TeacherTrainExp entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherTrainExp entity);
	
	/**
	 * 全部保存
	 */
	String saveTrainExps(List<TeacherTrainExp>  exps);
	
	/**
	 * 根据tid删除TeacherTrainExp
	 */
	public Long deleteByTid(Long tid);
	
	/**
	 * 根据pid删除TeacherTrainExp
	 */
	public Long deleteByPid(Long pid);
	

}
