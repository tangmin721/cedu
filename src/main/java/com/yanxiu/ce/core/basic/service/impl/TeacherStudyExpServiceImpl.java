package com.yanxiu.ce.core.basic.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.basic.dao.TeacherStudyExpDao;
import com.yanxiu.ce.core.basic.entity.TeacherStudyExp;
import com.yanxiu.ce.core.basic.entity.TeacherStudyExpQuery;
import com.yanxiu.ce.core.basic.service.TeacherStudyExpService;
import com.yanxiu.ce.system.dao.DictItemDao;
import com.yanxiu.ce.system.entity.DictItem;

/**
 * 学习经历管理
 * @author tangmin
 * @date 2016-04-03 11:47:53
 */
@Service("teacherStudyExpService")
public class TeacherStudyExpServiceImpl extends BaseServiceImpl<TeacherStudyExp, TeacherStudyExpQuery> implements TeacherStudyExpService{
	@Autowired
	private TeacherStudyExpDao dao;
	
	@Autowired
	private DictItemDao dictItemDao;

	@Override
	protected BaseDao<TeacherStudyExp, TeacherStudyExpQuery> dao() {
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
	public Boolean checkNameExit(TeacherStudyExp entity) {
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
	public String saveOrUpdate(TeacherStudyExp entity) {
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

	@Override
	@Transactional
	public Long deleteByTid(Long tid) {
		return this.dao.deleteByTid(tid);
	}
	
	/**
	 * 保存集合
	 */
	@Override
	@Transactional
	public String saveStudyExps(List<TeacherStudyExp> exps) {
		if(exps.size()>0){
			//第一步，根据tid删除exps
			this.deleteByTid(exps.get(0).getTid());
			//第二步：保存
			this.insertBatch(exps);
		}
		return "保存成功";
	}
	
	/**
	 * 最高学历记录标志更新
	 * @param
	 * @return
	 */
	public List<TeacherStudyExp> getStudyExpDgreeMax(List<TeacherStudyExp> teacherStudyExpList){		
		
		List<Long> ids = new ArrayList<Long>();
		Map<String,TeacherStudyExp>  teacherStudyExpMap = new HashMap<String,TeacherStudyExp>();
		List<TeacherStudyExp> teacherStudyExpListTmp = new ArrayList<TeacherStudyExp>();
		for(TeacherStudyExp teacherStudyExp :teacherStudyExpList){
			ids.add(teacherStudyExp.getDegree());
			teacherStudyExpMap.put(teacherStudyExp.getDegree().toString(), teacherStudyExp);
			teacherStudyExp.setDegreeFlag("否");
			teacherStudyExpListTmp.add(teacherStudyExp);
		}

		int seq = 0;
		long id = 0;
		if (ids != null && ids.size() != 0) {
			List<DictItem> dictItemList = dictItemDao.selectByIds(ids);
			for (DictItem dictItem : dictItemList) {
				int seqTmp = dictItem.getSeq();
				if (seqTmp >= seq) {
					seq = seqTmp;
					id = dictItem.getId();
				}
			}
		}
		
		TeacherStudyExp expTmp = teacherStudyExpMap.get(String.valueOf(id));
		if(expTmp != null){
			expTmp.setDegreeFlag("是");
		}
		
		return teacherStudyExpListTmp;
	}

}