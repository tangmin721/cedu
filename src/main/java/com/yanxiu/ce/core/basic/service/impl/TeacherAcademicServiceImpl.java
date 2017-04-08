package com.yanxiu.ce.core.basic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.basic.dao.TeacherAcademicDao;
import com.yanxiu.ce.core.basic.entity.TeacherAcademic;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicQuery;
import com.yanxiu.ce.core.basic.service.TeacherAcademicService;

/**
 * 学术论文管理
 * @author tangmin
 * @date 2016-04-19 11:38:55
 */
@Service("teacherAcademicService")
public class TeacherAcademicServiceImpl extends BaseServiceImpl<TeacherAcademic, TeacherAcademicQuery> implements TeacherAcademicService{
	@Autowired
	private TeacherAcademicDao dao;

	@Override
	protected BaseDao<TeacherAcademic, TeacherAcademicQuery> dao() {
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
	public Boolean checkNameExit(TeacherAcademic entity) {
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
	public String saveOrUpdate(TeacherAcademic entity) {
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
	 * 保存集合
	 */
	@Override
	@Transactional
	public String saveAcademics(List<TeacherAcademic> exps) {
		if(exps.size()>0){
			//第一步，根据tid删除exps
			this.deleteByTid(exps.get(0).getTid());
			//第二步：保存
			this.insertBatch(exps);
		}
		return "保存成功";
	}

	@Override
	@Transactional
	public Long deleteByTid(Long tid) {
		return this.dao.deleteByTid(tid);
	}

}