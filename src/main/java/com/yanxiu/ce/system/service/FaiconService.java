package com.yanxiu.ce.system.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.Faicon;
import com.yanxiu.ce.system.entity.FaiconQuery;

public interface FaiconService extends BaseService<Faicon, FaiconQuery>{
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkModifyNameExit(Faicon entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Faicon entity);

}
