package com.yanxiu.ce.system.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.UserLog;
import com.yanxiu.ce.system.entity.UserLogQuery;

/**
 * 用户操作日志管理
 * @author tangmin
 * @date 2016-07-13 11:57:28
 */
public interface UserLogService extends BaseService<UserLog, UserLogQuery>{
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
	Boolean checkNameExit(UserLog entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(UserLog entity);

}
