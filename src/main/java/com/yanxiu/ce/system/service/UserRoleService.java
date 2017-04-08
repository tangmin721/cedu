package com.yanxiu.ce.system.service;

import java.util.List;

import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserRole;

/**
 * @author tangmin
 * @date 2016年3月18日
 */
public interface UserRoleService {
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	Long insertBatch(List<UserRole> list);
	
	/**
	 * 根据userId批量删除
	 * @param userId
	 * @return
	 */
	Long deleteByUserId(Long userId);
	
	/**
	 * 根据roleId批量删除
	 * @param roleId
	 * @return
	 */
	Long deleteByRoleId(Long roleId);
	
	/**
	 * 根据userId，获取用户所有角色
	 */
	List<Role> selectRolesByUserId(Long userId);
	
	/**
	 * 根据roleId获取角色下所有的用户     （此方法在用户量大的时候请不要 用，如果要用，再分页）
	 */
	List<User> selectUsersByRoleId(Long roleId);
	
	/**
	 * 根据userId获取未分配给此用户的角色
	 */
	List<Role> selectNotRolesByUserId(Long userId);
	
	/**
	 * 根据roleId获取未分配给此角色的用户（此方法在用户量大的时候请不要 用，如果要用，再分页）
	 */
	List<User> selectNotUsersByRoleId(Long roleId);
	
	/**
	 * 根据userId,List<roleId>保存关系
	 */
	String saveUserRole(Long userId,List<Long> roleIds);
}
