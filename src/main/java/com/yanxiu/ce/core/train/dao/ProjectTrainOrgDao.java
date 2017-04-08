package com.yanxiu.ce.core.train.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrg;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrgQuery;

/**
 * 项目培训机构管理
 * @author tangmin
 * @date 2016-05-10 15:22:39
 */
@MybatisDao
public interface ProjectTrainOrgDao extends BaseDao<ProjectTrainOrg, ProjectTrainOrgQuery>{
	
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
	 * 查找
	 * @param pid
	 * @param orgid
	 * @return
	 */
	public Long selectCheckPidOrgidExit(@Param("pid")Long pid,@Param("orgid")Long orgid);
	
	
}
