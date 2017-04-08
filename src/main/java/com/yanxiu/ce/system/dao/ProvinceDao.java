package com.yanxiu.ce.system.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.ProvinceQuery;

/**
 * 省管理
 * @author tangmin
 * @date 2016-03-08 17:13:15
 */
@MybatisDao
public interface ProvinceDao extends BaseDao<Province, ProvinceQuery>{
	
	String selectNameByNo(@Param("provinceNo") Integer provinceNo);
}
