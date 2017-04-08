package com.yanxiu.ce.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.system.dao.FaiconDao;
import com.yanxiu.ce.system.entity.Faicon;
import com.yanxiu.ce.system.entity.FaiconQuery;
import com.yanxiu.ce.system.service.FaiconService;

@Service("faiconService")
public class FaiconServiceImpl extends BaseServiceImpl<Faicon, FaiconQuery> implements FaiconService{
	@Autowired
	private FaiconDao dao;

	@Override
	protected BaseDao<Faicon, FaiconQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 校验entity是否可修改（name是否存在）
	 */
	@Override
	public Boolean checkModifyNameExit(Faicon entity) {
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
	public String saveOrUpdate(Faicon entity) {
		if(!checkModifyNameExit(entity)){
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