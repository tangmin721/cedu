package com.yanxiu.ce.core.train.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.ProjectReportConfig;
import com.yanxiu.ce.core.train.entity.ProjectReportConfigQuery;

/**
 * 报名配置管理
 * @author tangmin
 * @date 2016-04-21 16:10:32
 */
@MybatisDao
public interface ProjectReportConfigDao extends BaseDao<ProjectReportConfig, ProjectReportConfigQuery>{
	
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
	 * 根据pid查找1:1关联的实体
	 * @param tid
	 * @return
	 */
	public ProjectReportConfig selectByPid(@Param("pid")Long pid);
	
	
	/**
	 * 根据pid获取项目配置的总人数
	 * @param pid
	 * @return
	 */
	Integer countPidNum(@Param("pid") Long pid);
}
