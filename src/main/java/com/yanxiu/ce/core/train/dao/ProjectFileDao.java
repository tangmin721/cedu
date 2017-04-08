package com.yanxiu.ce.core.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.ProjectFile;
import com.yanxiu.ce.core.train.entity.ProjectFileQuery;

/**
 * 项目资料文件管理
 * @author tangmin
 * @date 2016-07-26 10:07:34
 */
@MybatisDao
public interface ProjectFileDao extends BaseDao<ProjectFile, ProjectFileQuery>{
	
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
	 * 根据
	 * @param pid
	 * @param orgId
	 * @param type
	 * @return
	 */
	public List<ProjectFile> selectByPidOrgType(@Param("pid")Long pid,@Param("orgId")Long orgId,@Param("type")Integer type);
	
}
