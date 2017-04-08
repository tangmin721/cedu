package com.yanxiu.ce.system.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.Config;
import com.yanxiu.ce.system.entity.ConfigQuery;

/**
 * 系统配置管理
 * @author tangmin
 * @date 2016-04-12 17:40:16
 */
public interface ConfigService extends BaseService<Config, ConfigQuery>{
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
	Boolean checkNameExit(Config entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Config entity);
	
	/**
	 * 根据key查找
	 * @param theKey
	 * @return
	 */
	public Config selectByTheKey(String theKey);

}
