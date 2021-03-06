package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherCredent;
import com.yanxiu.ce.core.basic.entity.TeacherCredentQuery;

/**
 * 证书管理
 * @author tangmin
 * @date 2016-04-12 15:26:41
 */
@MybatisDao
public interface TeacherCredentDao extends BaseDao<TeacherCredent, TeacherCredentQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq(@Param("tid")Long tid);
	
	/**
	 * 查找name为@name，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("id")Long id);
	
}
