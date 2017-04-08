package com.yanxiu.ce.core.train.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.train.dao.ProjectTrainOrgDao;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrg;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrgQuery;
import com.yanxiu.ce.core.train.service.ProjectTrainOrgService;

/**
 * 项目培训机构管理
 * @author tangmin
 * @date 2016-05-10 15:22:40
 */
@Service("projectTrainOrgService")
public class ProjectTrainOrgServiceImpl extends BaseServiceImpl<ProjectTrainOrg, ProjectTrainOrgQuery> implements ProjectTrainOrgService{
	@Autowired
	private ProjectTrainOrgDao dao;

	@Override
	protected BaseDao<ProjectTrainOrg, ProjectTrainOrgQuery> dao() {
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
	public Boolean checkNameExit(ProjectTrainOrg entity) {
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
	public Long saveTrainOrgs(List<Long> orgidList, Long pid) {
		Long count = 0l;
		for(Long orgid:orgidList){
			ProjectTrainOrg entity = new ProjectTrainOrg();
			entity.setPid(pid);
			entity.setOrgid(orgid);
			//判断是否存在
			if(!checkPidOrgidExit(entity)){
				this.dao.insert(entity);
			}
			count++;
		}
		return count;
	}

	/**
	 * 检查是否存在
	 */
	@Override
	public Boolean checkPidOrgidExit(ProjectTrainOrg entity) {
		Long count = this.dao.selectCheckPidOrgidExit(entity.getPid(), entity.getOrgid());
		if(count>0){
			return true;
		}
		return false;
	}

}