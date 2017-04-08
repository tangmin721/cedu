package com.yanxiu.ce.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.system.dao.PermissionDao;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.PermissionQuery;
import com.yanxiu.ce.system.service.PermissionService;
import com.yanxiu.ce.system.service.RolePermService;

/**
 * 权限管理
 * @author tangmin
 * @date 2016-03-23 16:53:39
 */
@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<Permission, PermissionQuery> implements PermissionService{
	@Autowired
	private PermissionDao dao;
	
	@Autowired
	private RolePermService rolePermService;

	@Override
	protected BaseDao<Permission, PermissionQuery> dao() {
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
	public Boolean checkModifyCodeExit(Permission entity) {
		Long count = this.dao.selectCheckCodeExit(entity.getMenuId(),entity.getClzName(),entity.getCode(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	public String saveOrUpdate(Permission entity) {
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
	 * 批量删除（同时需要删除 role与perm的对应关系）
	 */
	@Override
	@Transactional
	public Long deletePermByIds(List<Long> ids) {
		//先删除rolePerm对应关系
		for(Long permId:ids){
			rolePermService.deleteByPermId(permId);
		}
		
		//再删除
		return this.deleteByIds(ids);
	}

}