package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherArchival;
import com.yanxiu.ce.core.basic.entity.TeacherArchivalQuery;

/**
 * 学籍档案管理
 * @author tangmin
 * @date 2016-04-01 18:10:28
 */
public interface TeacherArchivalService extends BaseService<TeacherArchival, TeacherArchivalQuery>{
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
	Boolean checkNameExit(TeacherArchival entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherArchival entity);
	
	
	/**
	 * 根据TID查找记录.
	 * @param id
	 * @return 返回T
	 */
	TeacherArchival selectByTid(Long id);

	/**
	 * excel导入的保存方法，不用再校验了，前面已经校验完成了
	 */
	Long saveImpTeacherArchival(TeacherArchival entity);

	/**
	 * 根据tid删除TeacherAcademic
	 */
	public Long deleteByTid(Long tid);
}
