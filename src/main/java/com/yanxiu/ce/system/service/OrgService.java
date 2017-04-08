package com.yanxiu.ce.system.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.Org;
import com.yanxiu.ce.system.entity.OrgQuery;

/**
 * 组织机构管理
 * @author tangmin
 * @date 2016-03-17 14:21:50
 */
public interface OrgService extends BaseService<Org, OrgQuery>{
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
	Boolean checkModifyCodeExit(Org entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Org entity);

}
