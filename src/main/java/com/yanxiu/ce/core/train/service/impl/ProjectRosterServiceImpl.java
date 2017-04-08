package com.yanxiu.ce.core.train.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.utils.AppStringUtils;
import com.yanxiu.ce.core.train.dao.ProjectRosterDao;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.core.train.entity.ProjectRosterQuery;
import com.yanxiu.ce.core.train.entity.ProjectUserStatus;
import com.yanxiu.ce.core.train.enums.RegisterStatusEnum;
import com.yanxiu.ce.core.train.service.ProjectQuotaService;
import com.yanxiu.ce.core.train.service.ProjectRosterService;
import com.yanxiu.ce.core.train.service.ProjectUserStatusService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.service.UserService;

/**
 * 报名业务管理
 * @author tangmin
 * @date 2016-04-28 09:53:26
 */
@Service("projectRosterService")
public class ProjectRosterServiceImpl extends BaseServiceImpl<ProjectRoster, ProjectRosterQuery> implements ProjectRosterService{
	@Autowired
	private ProjectRosterDao dao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectQuotaService projectQuotaService;
	
	@Autowired
	private ProjectUserStatusService statusService;

	@Override
	protected BaseDao<ProjectRoster, ProjectRosterQuery> dao() {
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
	public Boolean checkNameExit(ProjectRoster entity) {
//		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
//		if(count>0){
//			return false;
//		}
		return true;
	}
	
	/**
	 * 保存申请单
	 */
	@Override
	@Transactional
	public ProjectRoster saveApply(ProjectRoster entity) {
		ProjectRoster result = null;
		
		User user = userService.selectById(entity.getApplyUserId());
		ProjectRoster roster = selectOneRoster(entity.getPid(), user.getProvince(), 
				user.getCity(), user.getTown(), user.getSchool());
		if(roster==null){
			Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);//获取年份
			String rosterNo = "APPLY-"+year+""+AppStringUtils.addZero(8,this.dao.nextSequenceVal());
			entity.setRosterNo(rosterNo);
			entity.setStatus(RegisterStatusEnum.UNREPORT.getValue());//默认未上报
			this.insert(entity);
			
			ProjectUserStatus pus = statusService.selectOneStatus(entity.getPid(), user.getProvince(), 
				user.getCity(), user.getTown(), user.getSchool(),user.getType());
			if(pus == null){
				pus = new ProjectUserStatus();
			}
			pus.setPid(entity.getPid());
			pus.setProvince(user.getProvince());
			pus.setCity(user.getCity());
			pus.setTown(user.getTown());
			pus.setSchool(user.getSchool());
		//	pus.setStatus(ProjectMenuEnum.JOINED.getValue());
			pus.setUserType(user.getType());
			pus.setLoginName(user.getLoginName());
			statusService.saveOrUpdate(pus );
			result = entity;
		}else {
			roster.setMemo(entity.getMemo());
			this.update(roster);
			result = roster;
		}
		
		return result;
	}
	
	/**
	 * 修改状态为上报，上报申请单
	 */
	@Override
	public Long saveReport(Long id) {
		ProjectRoster entity  = this.selectById(id);
		entity.setReportDate(new Date());
		entity.setStatus(RegisterStatusEnum.REPORT.getValue());//已上报
		return this.dao.updateReport(entity);
	}
	
	/**
	 * 修改状态
	 */
	@Override
	public Long updateStatus(Long id, Integer status) {
		return this.dao.updateStatus(id, status);
	}

	/**
	 * 审批form提交保存
	 * @param id
	 * @param status
	 * @param checkDesc
	 * @return
	 */
	@Override
	public Long auditSave(Long id, Integer status, String checkDesc,Long CheckUserId) {
		ProjectRoster entity = this.selectById(id);
		entity.setCheckDesc(checkDesc);
		entity.setCheckDate(new Date());
		entity.setCheckUserId(CheckUserId);
		entity.setStatus(status);
		return saveCheck(entity);
	}

	@Override
	public Long saveCheck(ProjectRoster entity) {
		return this.dao.updateCheck(entity);
	}

	@Override
	public Long countTeacherNum(Long applyId) {
		return this.dao.countTeacherNum(applyId);
	}

	@Override
	public ProjectRoster selectOneRoster(Long pid, Integer province,
			Integer city, Integer town, Long school) {
		return this.dao.selectOneRoster(pid, province, city, town, school);
	}

	@Override
	@Transactional
	public void joinOrNot(Long pid, User user,Integer status) {
		ProjectUserStatus pus = statusService.selectOneStatus(pid, user.getProvince(), 
			user.getCity(), user.getTown(), user.getSchool(),user.getType());
		if(pus == null){
			pus = new ProjectUserStatus();
		}
		pus.setPid(pid);
		pus.setProvince(user.getProvince());
		pus.setCity(user.getCity());
		pus.setTown(user.getTown());
		pus.setSchool(user.getSchool());
	//	pus.setStatus(ProjectMenuEnum.UN_JOIN.getValue());
		pus.setStatus(status);
		pus.setUserType(user.getType());
		pus.setLoginName(user.getLoginName());
		statusService.saveOrUpdate(pus );
	}

	@Override
	public Map<String, Object> checkReport(Long applyId, User user) {
		Map<String, Object> resultMap = Maps.newHashMap();
		ProjectRoster roster = this.selectById(applyId);
		Long countTeacherNum = this.countTeacherNum(applyId);//本申请单教师人数
		Integer numSelectPpcts = projectQuotaService.numSelectPpcts(roster.getPid(), user.getProvince(), user.getCity(),
				user.getTown(), user.getSchool());//上级分配的人数
		
		Boolean resultBl = true;
		if(countTeacherNum<1){
			resultBl = false;
			resultMap.put("result", resultBl);
			resultMap.put("msg", "申请单上报人数不能为【0】");
			return resultMap;
		}
		
		if(countTeacherNum>numSelectPpcts){
			resultBl = false;
			resultMap.put("result", resultBl);
			resultMap.put("msg", "申请单的人数【"+countTeacherNum+"】不能大于"+"分配的名额：【"+numSelectPpcts+"】。");
			return resultMap;
		}
		
		if(countTeacherNum<numSelectPpcts){
			resultBl = false;
			resultMap.put("result", resultBl);
			resultMap.put("msg", "申请单的人数【"+countTeacherNum+"】不能小于"+"分配的名额：【"+numSelectPpcts+"】。");
			return resultMap;
		}
		resultMap.put("result", resultBl);
		resultMap.put("msg", "校验成功！");
		return resultMap;
	}

}