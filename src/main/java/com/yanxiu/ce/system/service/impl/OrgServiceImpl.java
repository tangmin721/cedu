package com.yanxiu.ce.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.system.dao.OrgDao;
import com.yanxiu.ce.system.entity.Org;
import com.yanxiu.ce.system.entity.OrgQuery;
import com.yanxiu.ce.system.service.OrgService;

/**
 * 组织机构管理
 * @author tangmin
 * @date 2016-03-17 14:21:50
 */
@Service("orgService")
public class OrgServiceImpl extends BaseServiceImpl<Org, OrgQuery> implements OrgService{
	@Autowired
	private OrgDao dao;

	@Override
	protected BaseDao<Org, OrgQuery> dao() {
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
	public Boolean checkModifyCodeExit(Org entity) {
		Long count = this.dao.selectCheckCodeExit(entity.getCode(), entity.getId());
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
	public String saveOrUpdate(Org entity) {
		if(!checkModifyCodeExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"编码已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"编码已经存在，修改失败");
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