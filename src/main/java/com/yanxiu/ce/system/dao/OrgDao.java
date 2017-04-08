package com.yanxiu.ce.system.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Org;
import com.yanxiu.ce.system.entity.OrgQuery;

/**
 * 组织机构管理
 * @author tangmin
 * @date 2016-03-17 14:21:50
 */
@MybatisDao
public interface OrgDao extends BaseDao<Org, OrgQuery>{
	
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
