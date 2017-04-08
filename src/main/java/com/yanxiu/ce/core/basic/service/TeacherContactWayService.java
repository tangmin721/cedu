package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherContactWay;
import com.yanxiu.ce.core.basic.entity.TeacherContactWayQuery;

/**
 * 联系方式管理
 * @author tangmin
 * @date 2016-12-29 17:58:06
 */
public interface TeacherContactWayService extends BaseService<TeacherContactWay, TeacherContactWayQuery>{
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
	Boolean checkNameExit(TeacherContactWay entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherContactWay entity);

}
