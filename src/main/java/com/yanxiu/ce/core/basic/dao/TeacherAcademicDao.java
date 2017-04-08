package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherAcademic;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicQuery;

/**
 * 学术论文管理
 * @author tangmin
 * @date 2016-04-19 11:38:55
 */
@MybatisDao
public interface TeacherAcademicDao extends BaseDao<TeacherAcademic, TeacherAcademicQuery>{
	
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
