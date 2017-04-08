package com.yanxiu.ce.core.train.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.core.train.entity.ProjectUserStatus;
import com.yanxiu.ce.core.train.entity.ProjectUserStatusQuery;

/**
 * 项目用户状态管理
 * @author tangmin
 * @date 2016-06-23 12:34:37
 */
@MybatisDao
public interface ProjectUserStatusDao extends BaseDao<ProjectUserStatus, ProjectUserStatusQuery>{
	
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
	 * 一个项目，根据以下查询条件是唯一的(相当于selectById)
	 * @return
	 */
	ProjectUserStatus selectOneStatus(@Param("pid")Long pid,
			@Param("province")Integer province,@Param("city")Integer city,
			@Param("town")Integer town,@Param("school")Long school,@Param("userType") Integer userType);
	
}
