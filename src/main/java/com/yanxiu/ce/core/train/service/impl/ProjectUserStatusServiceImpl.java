package com.yanxiu.ce.core.train.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.train.dao.ProjectUserStatusDao;
import com.yanxiu.ce.core.train.entity.ProjectUserStatus;
import com.yanxiu.ce.core.train.entity.ProjectUserStatusQuery;
import com.yanxiu.ce.core.train.service.ProjectUserStatusService;

/**
 * 项目用户状态管理
 * @author tangmin
 * @date 2016-06-23 12:34:37
 */
@Service("projectUserStatusService")
public class ProjectUserStatusServiceImpl extends BaseServiceImpl<ProjectUserStatus, ProjectUserStatusQuery> implements ProjectUserStatusService{
	@Autowired
	private ProjectUserStatusDao dao;

	@Override
	protected BaseDao<ProjectUserStatus, ProjectUserStatusQuery> dao() {
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
	public Boolean checkNameExit(ProjectUserStatus entity) {
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
	public String saveOrUpdate(ProjectUserStatus entity) {
		if(!checkNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
			}
		}
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		return msg;
	}

	@Override
	public ProjectUserStatus selectOneStatus(Long pid, Integer province,
			Integer city, Integer town, Long school,Integer userType) {
		return this.dao.selectOneStatus(pid, province, city, town, school,userType);
	}

}