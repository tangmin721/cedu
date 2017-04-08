package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExp;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExpQuery;

/**
 * 培训情况管理
 * @author tangmin
 * @date 2016-05-23 17:30:41
 */
@MybatisDao
public interface TeacherTrainExpDao extends BaseDao<TeacherTrainExp, TeacherTrainExpQuery>{
	
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

	/**
	 * deleteByPid
	 */
	public Long deleteByPid(@Param("pid")Long pid);
	
}
