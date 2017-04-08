package com.yanxiu.ce.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.exception.BizException;
import com.yanxiu.ce.common.exception.ValidateException;
import com.yanxiu.ce.common.validate.BeanValidator;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.ValidatorResult;
import com.yanxiu.ce.system.dao.RoleMenuDao;
import com.yanxiu.ce.system.entity.Menu;
import com.yanxiu.ce.system.entity.RoleMenu;
import com.yanxiu.ce.system.service.MenuService;
import com.yanxiu.ce.system.service.RoleMenuService;

@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService{
	
	@Autowired
	private RoleMenuDao dao;
	
	@Autowired
	private MenuService menuService;

	@Override
	@Transactional
	public Long insertBatch(List<RoleMenu> list) {
		for(RoleMenu entity:list){
			ValidatorResult validateResult = BeanValidator.validateResult(entity, Insert.class);
			if(!validateResult.getFlag()){
				throw new ValidateException(ValidateException.INSERT_FAILD, JSON.toJSONString(validateResult.getErrorObjs()));
			}
		}
		Long result = 0l;
		if(list.size()>0){
			result = this.dao.insertBatch(list);
		}
		return result;
	}

	@Override
	@Transactional
	public Long deleteByRoleId(Long roleId) {
		return this.dao.deleteByRoleId(roleId);
	}

	@Override
	@Transactional
	public Long deleteByMenuId(Long menuId) {
		return this.dao.deleteByMenuId(menuId);
	}

	@Override
	public List<Menu> selectMenusByRoleId(Long roleId) {
		return this.dao.selectMenusByRoleId(roleId);
	}
	
	@Override
	public List<Menu> selectShowerMenusByRoleId(Long roleId) {
		return this.dao.selectShowerMenusByRoleId(roleId);
	}
	
	@Override
	@Transactional
	public String saveRoleMenu(Long roleId, List<Long> menuIds) {
		if(roleId==null){
			throw new BizException("roleId is null");
		}
		List<RoleMenu> roleMenus = Lists.newArrayList();
		for(Long menuId:menuIds){
			RoleMenu rm = new RoleMenu();
			rm.setRoleId(roleId);
			rm.setMenuId(menuId);
			roleMenus.add(rm);
		}
		//先清空
		this.deleteByRoleId(roleId);
		//再保存
		this.insertBatch(roleMenus);
		
		//同步缓存
		menuService.syncGetPermTreeGlobal();
		menuService.syncLoadTreeNodes();
		
		return "保存成功！";
	}

	

}
