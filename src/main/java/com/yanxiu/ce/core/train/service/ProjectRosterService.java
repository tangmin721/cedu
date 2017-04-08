package com.yanxiu.ce.core.train.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.core.train.entity.ProjectRosterQuery;
import com.yanxiu.ce.system.entity.User;

/**
 * 报名业务管理
 * @author tangmin
 * @date 2016-04-28 09:53:26
 */
public interface ProjectRosterService extends BaseService<ProjectRoster, ProjectRosterQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(ProjectRoster entity);
	
	/**
	 * 保存apply
	 * @return
	 */
	ProjectRoster saveApply(ProjectRoster entity);
	
	/**
	 * 上报，更新上报时间
	 * @return
	 */
	Long saveReport(Long id);
	
	/**
	 * 保存check
	 * @return
	 */
	Long saveCheck(ProjectRoster entity);
	
	/**
	 * 根据 ID修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	public Long updateStatus(Long id,Integer status);
	
	
	/**
	 * 审批form提交保存
	 * @param id
	 * @param status
	 * @param checkDesc
	 * @return
	 */
	public Long auditSave(Long id,Integer status,String checkDesc,Long checkUserId);
	
	/**
	 * 申请单下教师的人数
	 * @param applyId
	 * @return
	 */
	public Long countTeacherNum(Long applyId);
	
	/**
	 * 一个项目，根据以下查询条件是唯一的(相当于selectById)
	 * @param pid
	 * @param province
	 * @param city
	 * @param town
	 * @param school
	 * @return
	 */
	public ProjectRoster selectOneRoster(@Param("pid")Long pid,
			@Param("province")Integer province,@Param("city")Integer city,
			@Param("town")Integer town,@Param("school")Long school);

	/**
	 * 参加or不参加
	 * @param pid
	 * @param currentUser
	 */
	void joinOrNot(Long pid, User currentUser,Integer status);
	
	/**
	 * 上报校验
	 * @param applyId
	 * @param currentUser
	 * @return
	 */
	public Map<String,Object> checkReport(Long applyId,User currentUser);

}
