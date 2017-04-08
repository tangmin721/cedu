package com.yanxiu.ce.core.basic.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherWorkExp;
import com.yanxiu.ce.core.basic.entity.TeacherWorkExpQuery;

/**
 * 工作经历管理
 * @author tangmin
 * @date 2016-04-05 15:50:01
 */
public interface TeacherWorkExpService extends BaseService<TeacherWorkExp, TeacherWorkExpQuery>{
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
	Boolean checkNameExit(TeacherWorkExp entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherWorkExp entity);
	
	/**
	 * 全部保存
	 */
	String saveWorkExps(List<TeacherWorkExp>  exps);
	
	/**
	 * 根据tid删除TeacherWorkExp
	 */
	public Long deleteByTid(Long tid);

}
