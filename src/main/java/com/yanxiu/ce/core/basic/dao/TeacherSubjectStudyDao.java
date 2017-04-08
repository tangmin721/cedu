package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherSubjectStudy;
import com.yanxiu.ce.core.basic.entity.TeacherSubjectStudyQuery;

/**
 * 学术交流管理
 * @author tangmin
 * @date 2016-08-24 11:24:29
 */
@MybatisDao
public interface TeacherSubjectStudyDao extends BaseDao<TeacherSubjectStudy, TeacherSubjectStudyQuery>{
	
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
