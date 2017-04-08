package com.yanxiu.ce.system.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.PermissionQuery;

/**
 * 权限管理
 * @author tangmin
 * @date 2016-03-23 16:53:39
 */
public interface PermissionService extends BaseService<Permission, PermissionQuery>{
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
	Boolean checkModifyCodeExit(Permission entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Permission entity);
	
	
	/**
	 * 批量删除（同时需要删除 role与perm的对应关系）
	 */
	Long deletePermByIds(List<Long> ids);
}
