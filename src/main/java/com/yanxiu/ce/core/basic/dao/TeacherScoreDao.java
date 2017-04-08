package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherScore;
import com.yanxiu.ce.core.basic.entity.TeacherScoreQuery;

/**
 * 学时学分管理
 * @author tangmin
 * @date 2016-05-11 15:11:39
 */
@MybatisDao
public interface TeacherScoreDao extends BaseDao<TeacherScore, TeacherScoreQuery>{
	
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
	
}
