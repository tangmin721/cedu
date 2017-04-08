package com.yanxiu.ce.system.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Config;
import com.yanxiu.ce.system.entity.ConfigQuery;

/**
 * 系统配置管理
 * @author tangmin
 * @date 2016-04-12 17:40:16
 */
@MybatisDao
public interface ConfigDao extends BaseDao<Config, ConfigQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 查找name为@name，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("id")Long id);
	
	/**
	 * 根据key查找
	 * @param theKey
	 * @return
	 */
	public Config selectByTheKey(@Param("theKey")String theKey);
	
	
	
}
