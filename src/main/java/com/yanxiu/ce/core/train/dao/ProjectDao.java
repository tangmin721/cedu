package com.yanxiu.ce.core.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.entity.ProjectQuery;

/**
 * 培训项目管理
 * @author tangmin
 * @date 2016-04-11 14:46:02
 */
@MybatisDao
public interface ProjectDao extends BaseDao<Project, ProjectQuery>{
	
	/**
	 * 获取sequence
	 */
	Long nextSequenceVal();
	
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
	 * 根据项目 ID修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	public Long updateStatus(@Param("id")Long id,@Param("status")Integer status);
	
	/**
	 * 根据项目 ID修改状态,审批意见
	 * @param id
	 * @param status
	 * @return
	 */
	public Long updateCstatus(@Param("id")Long id,@Param("cstatus")Integer cstatus,@Param("coption")String coption);
	
	

	/**
	 * 根据currentUser获取与我相关的pid集合    我未参加的项目status=99 我参加的项目status=0  我不参加的项目=1
	 */
	public List<Long> getJoinPids(@Param("province")Integer province,@Param("city")Integer city,
			@Param("town")Integer town,@Param("school")Long school,
			@Param("type")Integer type,@Param("loginName")String loginName,
			@Param("status")Integer status);
	
	
	/**
	 * 根据loginName获取pid  组织机构
	 * @param loginName
	 * @return
	 */
	public List<Long> getOrgJoinPids(@Param("loginName")String loginName);
	
	
	/**
	 * 根据pno获取
	 * @param loginName
	 * @return
	 */
	public Project selectByPno(@Param("pno")String pno);
	
	
}
