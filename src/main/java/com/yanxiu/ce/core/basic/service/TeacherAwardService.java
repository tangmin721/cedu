package com.yanxiu.ce.core.basic.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherAward;
import com.yanxiu.ce.core.basic.entity.TeacherAwardQuery;

/**
 * 获奖情况管理
 * @author tangmin
 * @date 2016-04-19 11:44:11
 */
public interface TeacherAwardService extends BaseService<TeacherAward, TeacherAwardQuery>{
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
	Boolean checkNameExit(TeacherAward entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherAward entity);
	
	/**
	 * 全部保存
	 */
	String saveAwards(List<TeacherAward>  exps);
	
	/**
	 * 根据tid删除TeacherAward
	 */
	public Long deleteByTid(Long tid);

}
