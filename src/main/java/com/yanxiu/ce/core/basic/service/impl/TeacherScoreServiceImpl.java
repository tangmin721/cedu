package com.yanxiu.ce.core.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.basic.dao.TeacherScoreDao;
import com.yanxiu.ce.core.basic.entity.TeacherScore;
import com.yanxiu.ce.core.basic.entity.TeacherScoreQuery;
import com.yanxiu.ce.core.basic.service.TeacherScoreService;

/**
 * 学时学分管理
 * @author tangmin
 * @date 2016-05-11 15:11:39
 */
@Service("teacherScoreService")
public class TeacherScoreServiceImpl extends BaseServiceImpl<TeacherScore, TeacherScoreQuery> implements TeacherScoreService{
	@Autowired
	private TeacherScoreDao dao;

	@Override
	protected BaseDao<TeacherScore, TeacherScoreQuery> dao() {
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
	public Boolean checkNameExit(TeacherScore entity) {
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
	public String saveOrUpdate(TeacherScore entity) {
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

}