package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherArchival;
import com.yanxiu.ce.core.basic.entity.TeacherArchivalQuery;

/**
 * 学籍档案管理
 * @author tangmin
 * @date 2016-04-01 18:10:28
 */
@MybatisDao
public interface TeacherArchivalDao extends BaseDao<TeacherArchival, TeacherArchivalQuery>{
	
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
	 * 根据tid获取1对1的实体
	 * @param name
	 * @param id
	 * @return
	 */
	public TeacherArchival selectByTid(@Param("tid")Long tid);
	
	/**
	 * deleteByTid
	 */
	public Long deleteByTid(@Param("tid")Long tid);
	
}
