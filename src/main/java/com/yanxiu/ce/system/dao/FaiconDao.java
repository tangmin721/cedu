package com.yanxiu.ce.system.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Faicon;
import com.yanxiu.ce.system.entity.FaiconQuery;

@MybatisDao
public interface FaiconDao extends BaseDao<Faicon, FaiconQuery>{
	
	/**
	 * 查找name为@name，且id不为@id的记录条数
	 * @param name
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("id")Long id);
	
}
