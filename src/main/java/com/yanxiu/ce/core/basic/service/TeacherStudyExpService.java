package com.yanxiu.ce.core.basic.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherStudyExp;
import com.yanxiu.ce.core.basic.entity.TeacherStudyExpQuery;

/**
 * 学习经历管理
 * @author tangmin
 * @date 2016-04-03 11:47:53
 */
public interface TeacherStudyExpService extends BaseService<TeacherStudyExp, TeacherStudyExpQuery>{
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
	Boolean checkNameExit(TeacherStudyExp entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherStudyExp entity);
	
	/**
	 * 全部保存
	 */
	String saveStudyExps(List<TeacherStudyExp>  exps);
	
	/**
	 * 根据tid删除TeacherStudyExp
	 */
	public Long deleteByTid(Long tid);
	
	/**
	 * 最高学历记录标志更新
	 */
	public List<TeacherStudyExp> getStudyExpDgreeMax(List<TeacherStudyExp> teacherStudyExpList);
}
