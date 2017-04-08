package com.yanxiu.ce.system.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.RoleQuery;

@MybatisDao
public interface RoleDao extends BaseDao<Role, RoleQuery>{
	
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
	public Long selectCheckCodeExit(@Param("code")String code,@Param("id")Long id);
	
}
