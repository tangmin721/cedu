package com.yanxiu.ce.system.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.dto.TreeCheckedIdDto;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.RoleQuery;

public interface RoleService extends BaseService<Role, RoleQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param dictCatlog
	 * @return
	 */
	Boolean checkModifyCodeExit(Role entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Role entity);
	
	/**
	 * 保存角色 -菜单-权限 信息
	 */
	String saveRoleMenuPerm(Long roleId,List<TreeCheckedIdDto> dtos);
	
	/**
	 * 根据roleId获取所有被选中的TreeCheckedIdDto
	 */
	List<TreeCheckedIdDto> getTreeCheckedIds(Long roleId);
}
