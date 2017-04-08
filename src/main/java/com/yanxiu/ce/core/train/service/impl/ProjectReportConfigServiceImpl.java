package com.yanxiu.ce.core.train.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.train.dao.ProjectReportConfigDao;
import com.yanxiu.ce.core.train.entity.ProjectReportConfig;
import com.yanxiu.ce.core.train.entity.ProjectReportConfigQuery;
import com.yanxiu.ce.core.train.service.ProjectReportConfigService;

/**
 * 报名配置管理
 * @author tangmin
 * @date 2016-04-21 16:10:32
 */
@Service("projectReportConfigService")
public class ProjectReportConfigServiceImpl extends BaseServiceImpl<ProjectReportConfig, ProjectReportConfigQuery> implements ProjectReportConfigService{
	@Autowired
	private ProjectReportConfigDao dao;

	@Override
	protected BaseDao<ProjectReportConfig, ProjectReportConfigQuery> dao() {
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
	public Boolean checkNameExit(ProjectReportConfig entity) {
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
	public ProjectReportConfig saveOrUpdate(ProjectReportConfig entity) {
		if(!checkNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
			}
		}
		ProjectReportConfig rc = null;
		if(entity.getId()==null){
			Long id = this.insert(entity);
			rc = this.selectById(id);
		}else {
			this.update(entity);
			rc = this.selectById(entity.getId());
		}
		return rc;
	}

	/**
	 * 根据pid查询实体类
	 */
	@Override
	public ProjectReportConfig selectByPid(Long pid) {
		return this.dao.selectByPid(pid);
	}

	@Override
	public Integer countPidNum(Long pid) {
		return this.dao.countPidNum(pid);
	}

}