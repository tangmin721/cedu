package com.yanxiu.ce.core.basic.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherAcademic;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicQuery;

/**
 * 学术论文管理
 * @author tangmin
 * @date 2016-04-19 11:38:55
 */
public interface TeacherAcademicService extends BaseService<TeacherAcademic, TeacherAcademicQuery>{
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
	Boolean checkNameExit(TeacherAcademic entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherAcademic entity);
	
	/**
	 * 全部保存
	 */
	String saveAcademics(List<TeacherAcademic>  exps);
	
	/**
	 * 根据tid删除TeacherAcademic
	 */
	public Long deleteByTid(Long tid);

}
