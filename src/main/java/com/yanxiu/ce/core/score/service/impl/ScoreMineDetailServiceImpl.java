package com.yanxiu.ce.core.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.score.dao.ScoreMineDetailDao;
import com.yanxiu.ce.core.score.entity.ScoreMineDetail;
import com.yanxiu.ce.core.score.entity.ScoreMineDetailQuery;
import com.yanxiu.ce.core.score.service.ScoreMineDetailService;

/**
 * 培训电子档案管理
 * @author tangmin
 * @date 2017-01-12 13:34:33
 */
@Service("scoreMineDetailService")
public class ScoreMineDetailServiceImpl extends BaseServiceImpl<ScoreMineDetail, ScoreMineDetailQuery> implements ScoreMineDetailService{
	@Autowired
	private ScoreMineDetailDao dao;

	@Override
	protected BaseDao<ScoreMineDetail, ScoreMineDetailQuery> dao() {
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
	public Boolean checkNameExit(ScoreMineDetail entity) {
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
	public String saveOrUpdate(ScoreMineDetail entity) {
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

	@Override
	public void deleteBySid(Long sid) {
		this.dao.deleteBySid(sid);
	}

}