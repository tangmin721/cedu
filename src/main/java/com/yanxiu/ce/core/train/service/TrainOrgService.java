package com.yanxiu.ce.core.train.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.train.entity.TrainOrg;
import com.yanxiu.ce.core.train.entity.TrainOrgQuery;

/**
 * 培训机构管理
 * @author tangmin
 * @date 2016-04-11 17:38:15
 */
public interface TrainOrgService extends BaseService<TrainOrg, TrainOrgQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(TrainOrg entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TrainOrg entity);
	
	
	/**
	 * 培训机构下拉框
	 * @return
	 */
	List<TrainOrg> selectTrainOrgs();
	
	
	/**
	 * 根据loginName = orgNo 查询实体
	 */

	TrainOrg selectByOrgNo(String orgNo);
}
