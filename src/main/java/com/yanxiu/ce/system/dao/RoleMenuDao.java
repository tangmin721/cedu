package com.yanxiu.ce.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Menu;
import com.yanxiu.ce.system.entity.RoleMenu;

/**
 * 角色-菜单 dao
 * @author tangmin
 * @date 2016年3月24日
 */
@MybatisDao
public interface RoleMenuDao {
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	Long insertBatch(List<RoleMenu> list);
	
	/**
	 * 根据roleId批量删除
	 * @param userId
	 * @return
	 */
	Long deleteByRoleId(@Param("roleId")Long roleId);
	
	/**
	 * 根据menuId批量删除
	 * @param roleId
	 * @return
	 */
	Long deleteByMenuId(@Param("menuId")Long menuId);
	
	/**
	 * 根据roleId获取Menu list，显示激活的菜单，用于权限树
	 */
	List<Menu> selectMenusByRoleId(@Param("roleId")Long roleId);
	
	
	/**
	 * 根据roleId获取Menu list  只显示shower=1的菜单，用于用户登录时，目录树的加载
	 */
	List<Menu> selectShowerMenusByRoleId(@Param("roleId")Long roleId);
	
	

}
