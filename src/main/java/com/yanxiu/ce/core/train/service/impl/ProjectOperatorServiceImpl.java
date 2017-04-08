package com.yanxiu.ce.core.train.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.train.dao.ProjectOperatorDao;
import com.yanxiu.ce.core.train.entity.ProjectOperator;
import com.yanxiu.ce.core.train.entity.ProjectOperatorQuery;
import com.yanxiu.ce.core.train.service.ProjectOperatorService;

/**
 * 项目执行人管理
 * @author tangmin
 * @date 2016-04-19 14:04:52
 */
@Service("projectOperatorService")
public class ProjectOperatorServiceImpl extends BaseServiceImpl<ProjectOperator, ProjectOperatorQuery> implements ProjectOperatorService{
	@Autowired
	private ProjectOperatorDao dao;

	@Override
	protected BaseDao<ProjectOperator, ProjectOperatorQuery> dao() {
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
	public Boolean checkNameExit(ProjectOperator entity) {
		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(ProjectOperator entity) {
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

}