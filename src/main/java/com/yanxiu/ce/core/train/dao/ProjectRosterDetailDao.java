package com.yanxiu.ce.core.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetailQuery;

/**
 * 报名名单详单管理
 * @author tangmin
 * @date 2016-04-20 16:00:38
 */
@MybatisDao
public interface ProjectRosterDetailDao extends BaseDao<ProjectRosterDetail, ProjectRosterDetailQuery>{
	
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
	
	public Long selectCheckTidApplyidExit(@Param("tid")Long tid,@Param("rosterid")Long applyId);
	
	
	/**
	 * 根据pid获取参培人明细
	 * @param query
	 * @param pid
	 * @return
	 */
	public List<ProjectRosterDetail> selectTrainTeachersPage(ProjectRosterDetailQuery query);
	public Long selectTrainTeachersTotal(ProjectRosterDetailQuery query);
	
}
