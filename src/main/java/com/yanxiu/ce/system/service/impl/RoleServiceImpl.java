package com.yanxiu.ce.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.BizException;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.system.dao.RoleDao;
import com.yanxiu.ce.system.dto.TreeCheckedIdDto;
import com.yanxiu.ce.system.entity.Menu;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.RoleMenu;
import com.yanxiu.ce.system.entity.RolePerm;
import com.yanxiu.ce.system.entity.RoleQuery;
import com.yanxiu.ce.system.service.MenuService;
import com.yanxiu.ce.system.service.RoleMenuService;
import com.yanxiu.ce.system.service.RolePermService;
import com.yanxiu.ce.system.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleQuery> implements RoleService{
	@Autowired
	private RoleDao dao;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	@Autowired
	private RolePermService rolePermService;

	@Override
	protected BaseDao<Role, RoleQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq() {
		Integer selectMaxSeq = this.dao.selectMaxSeq();
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkModifyCodeExit(Role entity) {
		Long count = this.dao.selectCheckCodeExit(entity.getCode(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(Role entity) {
		if(!checkModifyCodeExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"编码已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"编码已经存在，修改失败");
			}
		}
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		return msg;
	}
	
	/**
	 * 保存角色-菜单-权限信息
	 */
	@Override
	@Transactional
	public String saveRoleMenuPerm(Long roleId,List<TreeCheckedIdDto> dtos) {
		List<Long> menuIds = Lists.newArrayList();
		List<Long> permIds = Lists.newArrayList();
		for(TreeCheckedIdDto dto:dtos){
			if(dto.getPerm()!=null&&dto.getPerm()){
				permIds.add(dto.getId());
			}else{
				menuIds.add(dto.getId());
			}
		}
		
		if(roleId==null){
			throw new BizException("roleId is null");
		}
		
		//保存  roleMenu
		List<RoleMenu> roleMenus = Lists.newArrayList();
		for(Long menuId:menuIds){
			RoleMenu rm = new RoleMenu();
			rm.setRoleId(roleId);
			rm.setMenuId(menuId);
			roleMenus.add(rm);
		}
		//先清空
		roleMenuService.deleteByRoleId(roleId);
		//再保存
		roleMenuService.insertBatch(roleMenus);
		
		//保存  rolePerm
		List<RolePerm> rolePerms = Lists.newArrayList();
		for(Long permId:permIds){
			RolePerm rp = new RolePerm();
			rp.setRoleId(roleId);
			rp.setPermId(permId);
			rolePerms.add(rp);
		}
		//先清空
		rolePermService.deleteByRoleId(roleId);
		//再保存
		rolePermService.insertBatch(rolePerms);
		
		//同步缓存
		menuService.syncGetPermTreeGlobal();
		menuService.syncLoadTreeNodes();
		
		return "保存成功！";
		
	}

	/**
	 * 根据roleId获取所有被选中的TreeCheckedIdDto
	 */
	@Override
	public List<TreeCheckedIdDto> getTreeCheckedIds(Long roleId) {
		List<TreeCheckedIdDto> dtos = Lists.newArrayList();
		
		List<Menu> menus = roleMenuService.selectMenusByRoleId(roleId);
		for(Menu menu:menus){
			TreeCheckedIdDto menuIdDto = new TreeCheckedIdDto();
			menuIdDto.setId(menu.getId());
			dtos.add(menuIdDto);
		}
		
		List<Permission> perms = rolePermService.selectPermsByRoleId(roleId);
		for(Permission perm:perms){
			TreeCheckedIdDto permIdDto = new TreeCheckedIdDto();
			permIdDto.setId(perm.getId());
			permIdDto.setPerm(true);
			dtos.add(permIdDto);
		}
		
		return dtos;
	}

}