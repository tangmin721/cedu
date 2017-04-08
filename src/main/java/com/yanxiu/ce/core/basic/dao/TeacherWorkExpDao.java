package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherWorkExp;
import com.yanxiu.ce.core.basic.entity.TeacherWorkExpQuery;

/**
 * 工作经历管理
 * @author tangmin
 * @date 2016-04-05 15:50:01
 */
@MybatisDao
public interface TeacherWorkExpDao extends BaseDao<TeacherWorkExp, TeacherWorkExpQuery>{
	
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
	
	/**
	 * deleteByTid
	 */
	public Long deleteByTid(@Param("tid")Long tid);
}
