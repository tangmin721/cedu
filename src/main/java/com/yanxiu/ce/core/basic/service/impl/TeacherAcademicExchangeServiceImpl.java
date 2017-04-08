package com.yanxiu.ce.core.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.basic.dao.TeacherAcademicExchangeDao;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicExchange;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicExchangeQuery;
import com.yanxiu.ce.core.basic.service.TeacherAcademicExchangeService;

/**
 * 学术交流管理
 * @author tangmin
 * @date 2016-08-24 11:23:09
 */
@Service("teacherAcademicExchangeService")
public class TeacherAcademicExchangeServiceImpl extends BaseServiceImpl<TeacherAcademicExchange, TeacherAcademicExchangeQuery> implements TeacherAcademicExchangeService{
	@Autowired
	private TeacherAcademicExchangeDao dao;

	@Override
	protected BaseDao<TeacherAcademicExchange, TeacherAcademicExchangeQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq(Long tid) {
		Integer selectMaxSeq = this.dao.selectMaxSeq(tid);
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(TeacherAcademicExchange entity) {
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
	public String saveOrUpdate(TeacherAcademicExchange entity) {
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