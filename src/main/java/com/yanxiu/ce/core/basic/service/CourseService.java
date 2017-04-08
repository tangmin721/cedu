package com.yanxiu.ce.core.basic.service;

import java.util.Map;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.Course;
import com.yanxiu.ce.core.basic.entity.CourseQuery;

/**
 * 学科管理
 * @author tangmin
 * @date 2016-04-01 12:06:26
 */
public interface CourseService extends BaseService<Course, CourseQuery>{
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
	Boolean checkNameExit(Course entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Course entity);
	
	/**
	 * 根据stageId删除
	 * @param stageId
	 * @return
	 */
	Long deleteByStageId(Long stageId);
	
	/**
	 * 学科,Id  Name  Map  用于Excel导入判断
	 * @return
	 */
	Map<String,Long> courseMap(Long stageId);
	
	/**
	 * 同步redis缓存
	 */
	public Long syncRedisCourseMap(Long stageId);
}
