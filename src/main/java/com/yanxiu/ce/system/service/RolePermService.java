package com.yanxiu.ce.system.service;

import java.util.List;

import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.RolePerm;

/**
 * @author tangmin
 * @date 2016年3月18日
 */
public interface RolePermService {
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	Long insertBatch(List<RolePerm> list);
	
	/**
	 * 根据roleId批量删除
	 * @param userId
	 * @return
	 */
	Long deleteByRoleId(Long roleId);
	
	/**
	 * 根据permId批量删除
	 * @param roleId
	 * @return
	 */
	Long deleteByPermId(Long permId);
	
	/**
	 * 根据roleId获取list Permission
	 */
	List<Permission> selectPermsByRoleId(Long roleId);
	
	/**
	 * 根据roleid,List(permIds)保存关系
	 */
	String saveRolePerm(Long roleId,List<Long> permIds);
	
	
}
