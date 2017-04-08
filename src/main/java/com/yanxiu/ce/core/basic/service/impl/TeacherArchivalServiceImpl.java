package com.yanxiu.ce.core.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.basic.dao.TeacherArchivalDao;
import com.yanxiu.ce.core.basic.entity.TeacherArchival;
import com.yanxiu.ce.core.basic.entity.TeacherArchivalQuery;
import com.yanxiu.ce.core.basic.service.TeacherArchivalService;

/**
 * 学籍档案管理
 * @author tangmin
 * @date 2016-04-01 18:10:28
 */
@Service("teacherArchivalService")
public class TeacherArchivalServiceImpl extends BaseServiceImpl<TeacherArchival, TeacherArchivalQuery> implements TeacherArchivalService{
	@Autowired
	private TeacherArchivalDao dao;

	@Override
	protected BaseDao<TeacherArchival, TeacherArchivalQuery> dao() {
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
	public Boolean checkNameExit(TeacherArchival entity) {
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
	public String saveOrUpdate(TeacherArchival entity) {
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
			msg = "保存成功！";
		}else {
			this.update(entity);
				msg = "保存成功！";
		}
		return msg;
	}

	/**
	 * 根据tid获取1v1实体
	 */
	@Override
	public TeacherArchival selectByTid(Long tid) {
		return this.dao.selectByTid(tid);
	}

	
	/**
	 * excel导入的保存方法，不用再校验了，前面已经校验完成了
	 */
	@Override
	@Transactional
	public Long saveImpTeacherArchival(TeacherArchival entity) {
		this.dao.insert(entity);
		return entity.getId();
	}

	@Override
	@Transactional
	public Long deleteByTid(Long tid) {
		return this.dao.deleteByTid(tid);
	}
}