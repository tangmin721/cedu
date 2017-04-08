package com.yanxiu.ce.system.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Town;
import com.yanxiu.ce.system.entity.TownQuery;

/**
 * 区/县管理
 * @author tangmin
 * @date 2016-03-08 17:14:40
 */
@MybatisDao
public interface TownDao extends BaseDao<Town, TownQuery>{
	String selectNameByNo(@Param("townNo") Integer townNo);
}
