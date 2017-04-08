package com.yanxiu.ce.core.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.basic.dao.TeacherQualificationDao;
import com.yanxiu.ce.core.basic.entity.TeacherQualification;
import com.yanxiu.ce.core.basic.entity.TeacherQualificationQuery;
import com.yanxiu.ce.core.basic.service.TeacherQualificationService;

/**
 * 教师资格管理
 * @author tangmin
 * @date 2016-12-20 18:22:10
 */
@Service("teacherQualificationService")
public class TeacherQualificationServiceImpl extends BaseServiceImpl<TeacherQualification, TeacherQualificationQuery> implements TeacherQualificationService{
	@Autowired
	private TeacherQualificationDao dao;

	@Override
	protected BaseDao<TeacherQualification, TeacherQualificationQuery> dao() {
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
	public Boolean checkNameExit(TeacherQualification entity) {
		Long count = this.dao.selectCheckNameExit(null, entity.getId());
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
	public String saveOrUpdate(TeacherQualification entity) {
//		if(!checkNameExit(entity)){
//			if(entity.getId()==null){
//				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
//			}else {
//				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
//			}
//		}
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