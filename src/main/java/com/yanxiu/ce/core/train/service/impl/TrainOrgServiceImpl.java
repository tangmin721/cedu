package com.yanxiu.ce.core.train.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.utils.AppStringUtils;
import com.yanxiu.ce.core.train.dao.TrainOrgDao;
import com.yanxiu.ce.core.train.entity.TrainOrg;
import com.yanxiu.ce.core.train.entity.TrainOrgQuery;
import com.yanxiu.ce.core.train.service.TrainOrgService;
import com.yanxiu.ce.system.service.UserService;

/**
 * 培训机构管理
 * @author tangmin
 * @date 2016-04-11 17:38:15
 */
@Service("trainOrgService")
public class TrainOrgServiceImpl extends BaseServiceImpl<TrainOrg, TrainOrgQuery> implements TrainOrgService{
	@Autowired
	private TrainOrgDao dao;
	
	@Autowired
	private UserService userService;

	@Override
	protected BaseDao<TrainOrg, TrainOrgQuery> dao() {
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
	 * 覆写删除方法，删除培训机构同时删除账户
	 */
	@Override
	@Transactional
	public Long deleteByIds(List<Long> ids) {
		Long count = 0l;
		for(Long id:ids){
			TrainOrg org = this.selectById(id);
			if(org!=null && org.getOrgNo()!=null){
				userService.deleteByLoginName(org.getOrgNo().toString());
			}
			count++;
			
			this.deleteById(id);
		}
		
		return count;
	}
	
	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(TrainOrg entity) {
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
	public String saveOrUpdate(TrainOrg entity) {
		if(!checkNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
			}
		}
		String msg = "";
		if(entity.getId()==null){
			//机构编号
			String orgNo = "ORG"+""+AppStringUtils.addZero(6,this.dao.nextSequenceVal());       // ;
			entity.setOrgNo(orgNo);
			this.insert(entity);
			msg = "添加成功！";
		}else {
			TrainOrg old = this.selectById(entity.getId());
			entity.setOrgNo(old.getOrgNo());
			this.update(entity);
				msg = "编辑成功！";
			
				
		}
		
		//生成机构账户
		userService.createTrainOrgUser(entity);
		return msg;
	}

	/**
	 * 培训机构下拉框
	 */
	@Override
	public List<TrainOrg> selectTrainOrgs() {
		TrainOrgQuery query = new TrainOrgQuery();
		query.setFields("id,name");
		return this.selectList(query);
	}

	@Override
	public TrainOrg selectByOrgNo(String orgNo) {
		return this.dao.selectByOrgNo(orgNo);
	}
}