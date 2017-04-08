package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherEdu;
import com.yanxiu.ce.core.basic.entity.TeacherEduQuery;

/**
 * 教学和学历管理
 * @author tangmin
 * @date 2016-04-05 14:01:34
 */
@MybatisDao
public interface TeacherEduDao extends BaseDao<TeacherEdu, TeacherEduQuery>{
	
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
	public TeacherEdu selectByTid(@Param("tid")Long tid);
	
}
