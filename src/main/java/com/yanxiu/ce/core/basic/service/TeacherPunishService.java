package com.yanxiu.ce.core.basic.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherPunish;
import com.yanxiu.ce.core.basic.entity.TeacherPunishQuery;

/**
 * 处分情况管理
 * @author tangmin
 * @date 2016-05-23 15:41:24
 */
public interface TeacherPunishService extends BaseService<TeacherPunish, TeacherPunishQuery>{
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
	Boolean checkNameExit(TeacherPunish entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherPunish entity);

	/**
	 * 全部保存
	 */
	String savePunishs(List<TeacherPunish>  exps);
	
	/**
	 * 根据tid删除TeacherPunish
	 */
	public Long deleteByTid(Long tid);
}
