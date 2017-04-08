package com.yanxiu.ce.core.solr.dao;

import java.util.List;

import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.solr.dto.TeacherSolr;

/**
 * solr
 * @author tangmin
 * @date 2016年5月27日
 */
@MybatisDao
public interface TeacherSolrDao{
	
	/**
	 * 获取所有教师资源，用于初次导入solr索引库
	 * @return
	 */
	public List<TeacherSolr> getAllTeacherSolr();
	

}
