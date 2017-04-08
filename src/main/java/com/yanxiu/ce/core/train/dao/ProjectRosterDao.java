package com.yanxiu.ce.core.train.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.core.train.entity.ProjectRosterQuery;

/**
 * 报名业务管理
 * @author tangmin
 * @date 2016-04-28 09:53:26
 */
@MybatisDao
public interface ProjectRosterDao extends BaseDao<ProjectRoster, ProjectRosterQuery>{
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
	 * 根据 ID修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	public Long updateStatus(@Param("id")Long id,@Param("status")Integer status);
	
	/**
	 * 上报，更新上报时间
	 * @param entity
	 * @return
	 */
	public Long updateReport(ProjectRoster entity);
	
	/**
	 * 审批，更新审核信息
	 */
	public Long updateCheck(ProjectRoster entity);
	
	/**
	 * 获取申请单人数
	 * @param applyId
	 * @return
	 */
	Long countTeacherNum(@Param("rosterId")Long applyId);
	
	/**
	 * 一个项目，根据以下查询条件是唯一的(相当于selectById)
	 * @return
	 */
	ProjectRoster selectOneRoster(@Param("pid")Long pid,
			@Param("province")Integer province,@Param("city")Integer city,
			@Param("town")Integer town,@Param("school")Long school);
}
