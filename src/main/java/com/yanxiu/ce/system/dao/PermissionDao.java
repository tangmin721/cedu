package com.yanxiu.ce.system.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.PermissionQuery;

/**
 * 权限管理
 * @author tangmin
 * @date 2016-03-23 16:53:39
 */
@MybatisDao
public interface PermissionDao extends BaseDao<Permission, PermissionQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 查找code为@code，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckCodeExit(@Param("menuId")Long menuId,@Param("clzName")String clzName,@Param("code")String code,@Param("id")Long id);
	
}
