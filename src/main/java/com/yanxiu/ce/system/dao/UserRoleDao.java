package com.yanxiu.ce.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserRole;

/**
 * 用户-角色关系 DAO
 * @author tangmin
 * @date 2016年3月17日
 */

@MybatisDao
public interface UserRoleDao {
	
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
	Long deleteByUserId(@Param("userId")Long userId);
	
	/**
	 * 根据roleId批量删除
	 * @param roleId
	 * @return
	 */
	Long deleteByRoleId(@Param("roleId")Long roleId);
	
	/**
	 * 根据userId，获取用户所有角色
	 */
	List<Role> selectRolesByUserId(@Param("userId")Long userId);
	
	/**
	 * 根据roleId获取角色下所有的用户
	 */
	List<User> selectUsersByRoleId(@Param("roleId")Long roleId);
	
	/**
	 * 根据userId获取未分配给此用户的角色
	 */
	List<Role> selectNotRolesByUserId(@Param("userId")Long userId);
	
	/**
	 * 根据roleId获取未分配给此角色的用户
	 */
	List<User> selectNotUsersByRoleId(@Param("roleId")Long roleId);
	
	

}
