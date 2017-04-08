package com.yanxiu.ce.system.service;

import java.util.List;

import com.yanxiu.ce.system.entity.Menu;
import com.yanxiu.ce.system.entity.RoleMenu;

/**
 * @author tangmin
 * @date 2016年3月18日
 */
public interface RoleMenuService {

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
	Long deleteByRoleId(Long roleId);
	
	/**
	 * 根据menuId批量删除
	 * @param roleId
	 * @return
	 */
	Long deleteByMenuId(Long menuId);
	
	/**
	 * 根据roleId获取Menu list，显示激活的菜单，用于权限树
	 */
	List<Menu> selectMenusByRoleId(Long roleId);
	
	/**
	 * 根据roleId获取Menu list  只显示shower=1的菜单，用于用户登录时，目录树的加载
	 */
	List<Menu> selectShowerMenusByRoleId(Long roleId);
	
	/**
	 * 根据roleid,List(menuIds)保存关系
	 */
	String saveRoleMenu(Long roleId,List<Long> menuIds);
}
