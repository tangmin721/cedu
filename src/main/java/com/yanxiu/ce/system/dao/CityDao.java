package com.yanxiu.ce.system.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.CityQuery;

/**
 * 市管理
 * @author tangmin
 * @date 2016-03-08 17:13:48
 */
@MybatisDao
public interface CityDao extends BaseDao<City, CityQuery>{
	
	String selectNameByNo(@Param("cityNo") Integer cityNo);
	
}
