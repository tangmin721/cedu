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
import com.yanxiu.ce.system.dao.RolePermDao;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.RolePerm;
import com.yanxiu.ce.system.service.MenuService;
import com.yanxiu.ce.system.service.RolePermService;

@Service("rolePermService")
public class RolePermServiceImpl implements RolePermService{
	
	@Autowired
	private RolePermDao dao;
	
	@Autowired
	private MenuService menuService;

	@Override
	@Transactional
	public Long insertBatch(List<RolePerm> list) {
		for(RolePerm entity:list){
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
	public Long deleteByPermId(Long permId) {
		return this.dao.deleteByPermId(permId);
	}

	@Override
	public List<Permission> selectPermsByRoleId(Long roleId) {
		return this.dao.selectPermsByRoleId(roleId);
	}

	@Override
	@Transactional
	public String saveRolePerm(Long roleId, List<Long> permIds) {
		if(roleId==null){
			throw new BizException("roleId is null");
		}
		List<RolePerm> rolePerms = Lists.newArrayList();
		for(Long permId:permIds){
			RolePerm rm = new RolePerm();
			rm.setRoleId(roleId);
			rm.setPermId(permId);
			rolePerms.add(rm);
		}
		//先清空
		this.deleteByRoleId(roleId);
		//再保存
		this.insertBatch(rolePerms);
		
		//同步缓存
		menuService.syncGetPermTreeGlobal();
		menuService.syncLoadTreeNodes();
		
		return "保存成功！";
	}
	
}
