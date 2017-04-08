package com.yanxiu.ce.core.train.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.utils.AppStringUtils;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExp;
import com.yanxiu.ce.core.basic.service.TeacherTrainExpService;
import com.yanxiu.ce.core.train.dao.ProjectDao;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.entity.ProjectQuery;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetailQuery;
import com.yanxiu.ce.core.train.enums.ProjectCstatusEnum;
import com.yanxiu.ce.core.train.enums.ProjectEnum;
import com.yanxiu.ce.core.train.service.ProjectRosterDetailService;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 培训项目管理
 * @author tangmin
 * @date 2016-04-11 14:46:02
 */
@Service("projectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project, ProjectQuery> implements ProjectService{
	@Autowired
	private ProjectDao dao;
	
	@Autowired
	private ProjectRosterDetailService rosterDetailService;
	
	@Autowired
	private TeacherTrainExpService teacherTrainExpService;
	
	@Autowired
	private DictCatlogService dictCatlogService;

	@Override
	protected BaseDao<Project, ProjectQuery> dao() {
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
	public Boolean checkNameExit(Project entity) {
		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 新增or修改,操作类型（其实就是需要修改到什么状态）
	 */
	@Override
	@Transactional
	public String saveOrUpdate(Project entity) {
		if(entity.getProvince()==null){
			entity.setProvince(0);
		}
		if(entity.getCity()==null){
			entity.setCity(0);
		}
		if(entity.getTown()==null){
			entity.setTown(0);
		}
		if(entity.getSchool()==null){
			entity.setSchool(0l);
		}
		if(entity.getStatus()==null){
			entity.setStatus(0);
		}
		if(!checkNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
			}
		}
		String msg = "";
		if(entity.getId()==null){
			Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);//获取年份
	      //  int m = cal.get(Calendar.MONTH);
			String pno = "I"+year+""+AppStringUtils.addZero(8,this.dao.nextSequenceVal());
			entity.setPno(pno);
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		return msg;
	}

	/**
	 * 根据项目 ID修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	@Override
	@Transactional
	public Long updateStatus(Long id, Integer status) {
		
		//添加培训记录
		if(status == ProjectEnum.TRAINING.getValue() || status == ProjectEnum.END.getValue()){
			Project project = this.selectById(id);
			
			ProjectRosterDetailQuery query = new ProjectRosterDetailQuery();
			query.setPid(id.toString());
			query.setPageSize(rosterDetailService.selectTrainTeachersTotal(query));
			List<ProjectRosterDetail> teachers = rosterDetailService.selectTrainTeachersPage(query );
			teacherTrainExpService.deleteByPid(id);//先删除
			for(ProjectRosterDetail teacher:teachers){
				Long tid = teacher.getId();
				TeacherTrainExp trainExp = new TeacherTrainExp();
				trainExp.setTid(tid);
				trainExp.setPid(id);
				trainExp.setStartDate(project.getStartDate());
				trainExp.setEndDate(project.getEndDate());
				trainExp.setTrainType(project.getTrainType());
				trainExp.setProjectName(project.getName());
				trainExp.setStatus(status);
				teacherTrainExpService.saveOrUpdate(trainExp);
			}
		}
		
		//学校改变发布状态
		schoolChangeCstatus(id, status);
		
		return this.dao.updateStatus(id, status);
	}

	/**
	 * 学校改变发布状态
	 * @param id
	 * @param status
	 */
	private void schoolChangeCstatus(Long id, Integer status) {
		//如果是校级项目，状态改为发布，则需要把项目状态改为待审批，则默认为其设置项目状态为  11-待审批
		Project project = this.selectById(id);
		List<DictItem> allLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		long levelId = 0l;
		for(DictItem level:allLevels){
			int indexOfSchool = level.getName().indexOf("校");
			if(indexOfSchool!=-1){
				levelId = level.getId();
			}
		}
		if(project.getTrainLevel().longValue()==levelId && status.intValue()==ProjectEnum.PUBLISH.getValue()){
			project.setCstatus(ProjectCstatusEnum.DEFAULT.getValue());
			this.dao.updateCstatus(id,ProjectCstatusEnum.DEFAULT.getValue(),"");
		}
		//取消发布
		if(project.getTrainLevel().longValue()==levelId && status.intValue()==ProjectEnum.UN_PUBLISH.getValue()){
			project.setCstatus(ProjectCstatusEnum.DEFAULT.getValue());
			this.dao.updateCstatus(id,null,"");
		}
	}

	@Override
	public List<Long> getJoinPids(User currentUser,Integer type) {
		List<Long> pids = this.dao.getJoinPids(currentUser.getProvince(),
				currentUser.getCity(), currentUser.getTown(), currentUser.getSchool(), currentUser.getType(),
				currentUser.getLoginName(), type);
		return pids;
	}
	
	@Override
	public List<Long> getOrgJoinPids(String loginName) {
		List<Long> pids = this.dao.getOrgJoinPids(loginName);
		return pids;
	}

	@Override
	public Project selectByPno(String pno) {
		return this.dao.selectByPno(pno);
	}

	@Override
	public void saveTownCheckOption(Long pid, Integer pass, String coption) {
		this.dao.updateCstatus(pid, pass, coption);
	}

}