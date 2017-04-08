package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherQualification;
import com.yanxiu.ce.core.basic.entity.TeacherQualificationQuery;

/**
 * 教师资格管理
 * @author tangmin
 * @date 2016-12-20 18:22:10
 */
@MybatisDao
public interface TeacherQualificationDao extends BaseDao<TeacherQualification, TeacherQualificationQuery>{
	
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
