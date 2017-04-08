package com.yanxiu.ce.core.train.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.core.train.dao.ProjectRosterDetailDao;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetailQuery;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrg;
import com.yanxiu.ce.core.train.service.ProjectQuotaService;
import com.yanxiu.ce.core.train.service.ProjectRosterDetailService;
import com.yanxiu.ce.core.train.service.ProjectRosterService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;

/**
 * 报名名单详单管理
 * @author tangmin
 * @date 2016-04-20 16:00:38
 */
@Service("projectRosterDetailService")
public class ProjectRosterDetailServiceImpl extends BaseServiceImpl<ProjectRosterDetail, ProjectRosterDetailQuery> implements ProjectRosterDetailService{
	@Autowired
	private ProjectRosterDetailDao dao;
	
	@Autowired
	private ProjectRosterService projectRosterService;
	
	@Autowired
	private ProjectQuotaService projectQuotaService;

	@Override
	protected BaseDao<ProjectRosterDetail, ProjectRosterDetailQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq() {
		Integer selectMaxSeq = this.dao.selectMaxSeq();
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(ProjectRosterDetail entity) {
//		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
//		if(count>0){
//			return false;
//		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public Map<String,Object> saveTeachers(List<Long> tidList, Long applyId,User currentUser) {
		Map<String,Object> result = Maps.newHashMap();
		Long count = 0l;
		for(Long tid:tidList){
			ProjectRosterDetail entity = new ProjectRosterDetail();
			entity.setTid(tid);
			entity.setRosterId(applyId);
			//判断是否存在
			if(checkTidApplyidExit(entity)){
				this.dao.insert(entity);
			}
			count++;
		}
		Long countTeacherNum = projectRosterService.countTeacherNum(applyId);//已选择的人数
		ProjectRoster roster = projectRosterService.selectById(applyId);
		Integer numSelectPpcts = projectQuotaService.numSelectPpcts(roster.getPid(), currentUser.getProvince(), currentUser.getCity(),
				currentUser.getTown(), currentUser.getSchool());//上级分配的人数
		result.put("numSelectPpcts", numSelectPpcts);
		result.put("countTeacherNum", countTeacherNum);
		return result;
	}

	@Override
	public Boolean checkTidApplyidExit(ProjectRosterDetail entity) {
		Long count = this.dao.selectCheckTidApplyidExit(entity.getTid(), entity.getRosterId());
		if(count>0){
			return false;
		}
		return true;
	}

	/**
	 * 根据pid获取参培人明细
	 * @param query
	 * @return
	 */
	@Override
	public List<ProjectRosterDetail> selectTrainTeachersPage(
			ProjectRosterDetailQuery query) {
		return this.dao.selectTrainTeachersPage(query);
	}

	/**
	 * 参培人明细总条数
	 * @param query
	 * @return
	 */
	@Override
	public Long selectTrainTeachersTotal(ProjectRosterDetailQuery query) {
		return this.dao.selectTrainTeachersTotal(query);
	}
	
	/**
	 * 参培人分页
	 */
	@Override
	public Pagination<ProjectRosterDetail> selectTrainTeachersPagination(ProjectRosterDetailQuery query){
		Pagination<ProjectRosterDetail> pagination = new Pagination<ProjectRosterDetail>(query.getPageCurrent(),query.getPageSize(),this.dao.selectTrainTeachersTotal(query));
		pagination.setList(this.dao.selectTrainTeachersPage(query));
		return pagination;
	}
	

}