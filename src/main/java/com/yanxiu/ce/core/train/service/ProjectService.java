package com.yanxiu.ce.core.train.service;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.entity.ProjectQuery;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.system.entity.User;

/**
 * 培训项目管理
 * @author tangmin
 * @date 2016-04-11 14:46:02
 */
public interface ProjectService extends BaseService<Project, ProjectQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 根据pno获取
	 * @return
	 */
	Project selectByPno(String pno);
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(Project entity);
	
	/**
	 * 保存或添加  ,带操作类型
	 * @return
	 */
	String saveOrUpdate(Project entity);
	
	
	/**
	 * 根据项目 ID修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	public Long updateStatus(Long id,Integer status);
	
	
	/**
	 * 根据currentUser获取与我相关的pid集合   1 我未参加的项目
	 */
	public List<Long> getJoinPids(User currentUser,Integer myProjectStatus);
	
	
	/**
	 * 培训机构的项目列表 根据loginName获取与我相关的pid集合 
	 */
	public List<Long> getOrgJoinPids(String loginName);

	/**
	 * 区县审核
	 * @param pid
	 * @param pass
	 * @param coption
	 */
	void saveTownCheckOption(Long pid, Integer pass, String coption);
	

}
