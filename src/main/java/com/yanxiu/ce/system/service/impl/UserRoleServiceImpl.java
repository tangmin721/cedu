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
import com.yanxiu.ce.system.dao.UserRoleDao;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserRole;
import com.yanxiu.ce.system.service.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService{
	
	@Autowired
	private UserRoleDao dao;

	@Override
	@Transactional
	public Long insertBatch(List<UserRole> list) {
		for(UserRole entity:list){
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
	public Long deleteByUserId(Long userId) {
		return this.dao.deleteByUserId(userId);
	}

	@Override
	@Transactional
	public Long deleteByRoleId(Long roleId) {
		return this.dao.deleteByRoleId(roleId);
	}

	@Override
	public List<Role> selectRolesByUserId(Long userId) {
		return this.dao.selectRolesByUserId(userId);
	}

	@Override
	public List<User> selectUsersByRoleId(Long roleId) {
		return this.dao.selectUsersByRoleId(roleId);
	}

	@Override
	public List<Role> selectNotRolesByUserId(Long userId) {
		return this.dao.selectNotRolesByUserId(userId);
	}

	@Override
	public List<User> selectNotUsersByRoleId(Long roleId) {
		return this.dao.selectNotUsersByRoleId(roleId);
	}

	
	@Override
	@Transactional
	public String saveUserRole(Long userId, List<Long> roleIds) {
		if(userId==null){
			throw new BizException("UserId is null");
		}
		List<UserRole> userRoles = Lists.newArrayList();
		for(Long roldId:roleIds){
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roldId);
			userRoles.add(ur);
		}
		//先清空
		this.deleteByUserId(userId);
		//再保存
		this.insertBatch(userRoles);
		return "保存成功！";
	}

}
