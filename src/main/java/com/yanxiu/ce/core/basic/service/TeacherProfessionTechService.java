package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherProfessionTech;
import com.yanxiu.ce.core.basic.entity.TeacherProfessionTechQuery;

/**
 * 专业技术职务聘任管理
 * @author tangmin
 * @date 2016-12-16 11:34:37
 */
public interface TeacherProfessionTechService extends BaseService<TeacherProfessionTech, TeacherProfessionTechQuery>{
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
	Boolean checkNameExit(TeacherProfessionTech entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherProfessionTech entity);

}
