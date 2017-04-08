package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherPostEngage;
import com.yanxiu.ce.core.basic.entity.TeacherPostEngageQuery;

/**
 * 岗位聘任管理
 * @author tangmin
 * @date 2016-12-13 17:55:25
 */
@MybatisDao
public interface TeacherPostEngageDao extends BaseDao<TeacherPostEngage, TeacherPostEngageQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq(Long tid);
	
	/**
	 * 查找name为@name，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("id")Long id);
	
}
