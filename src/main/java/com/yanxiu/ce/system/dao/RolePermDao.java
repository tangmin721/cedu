package com.yanxiu.ce.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.RolePerm;

/**
 * 角色-权限 dao
 * @author tangmin
 * @date 2016年3月24日
 */
@MybatisDao
public interface RolePermDao {
	
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
	Long deleteByRoleId(@Param("roleId")Long roleId);
	
	/**
	 * 根据permId批量删除
	 * @param roleId
	 * @return
	 */
	Long deleteByPermId(@Param("permId")Long permId);
	
	/**
	 * 根据roleId获取list Perm
	 */
	List<Permission> selectPermsByRoleId(@Param("roleId")Long roleId);
	

}
