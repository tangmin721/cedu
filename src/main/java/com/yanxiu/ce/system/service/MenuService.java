package com.yanxiu.ce.system.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.dto.MenuPermTreeDto;
import com.yanxiu.ce.system.entity.Menu;
import com.yanxiu.ce.system.entity.MenuQuery;
import com.yanxiu.ce.system.entity.MenuTreeNode;

public interface MenuService extends BaseService<Menu, MenuQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq(Long pid);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Menu entity);
	
	/**
	 * 根据pid，获取一层节点集合
	 */
	List<MenuTreeNode> selectByPid(Long pid);
	
	/**
	 * 根据pid，获取所有子节点
	 */
	List<MenuTreeNode> selectAllByPid(Long pid,Set<Long> menuIds);
	
	/**
	 * 根据节点id,递归获取所有子节点集合
	 */
	Collection<MenuTreeNode> loadTreeNodes(Long id,Long userId);
	
	/**
	 * 清除缓存
	 */
	void removeMenuCache();
	
	/**
	 * 获取权限树
	 * @return
	 */
	MenuPermTreeDto getPermTreeGlobal(Long roleId);

	/**
	 * 同步缓存syncGetPermTreeGlobal
	 * @param roleId
	 * @return
	 */
	void syncGetPermTreeGlobal();

	/**
	 * 同步缓存   清除所有loadTreeNodest通配的keys
	 * @param userId
	 * @return
	 */
	void syncLoadTreeNodes();
	
}
