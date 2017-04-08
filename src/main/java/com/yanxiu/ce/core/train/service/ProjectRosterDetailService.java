package com.yanxiu.ce.core.train.service;

import java.util.List;
import java.util.Map;

import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetailQuery;
import com.yanxiu.ce.system.entity.User;

/**
 * 报名名单详单管理
 * @author tangmin
 * @date 2016-04-20 16:00:38
 */
public interface ProjectRosterDetailService extends BaseService<ProjectRosterDetail, ProjectRosterDetailQuery>{
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
	Boolean checkNameExit(ProjectRosterDetail entity);
	
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	public Boolean checkTidApplyidExit(ProjectRosterDetail entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	Map<String,Object> saveTeachers(List<Long> tidList, Long applyId,User currentUser);
	
	/**
	 * 根据pid获取参培人明细
	 * @param query
	 * @param pid
	 * @return
	 */
	public List<ProjectRosterDetail> selectTrainTeachersPage(ProjectRosterDetailQuery query);
	/**
	 * 总条数
	 * @param query
	 * @return
	 */
	Long selectTrainTeachersTotal(ProjectRosterDetailQuery query);
	/**
	 * 参培人明细页面分页
	 * @param query
	 * @return 返回分页对象
	 */
	Pagination<ProjectRosterDetail> selectTrainTeachersPagination(ProjectRosterDetailQuery query);

}
