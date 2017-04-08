package com.yanxiu.ce.core.score.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.score.entity.ScoreMineDetail;
import com.yanxiu.ce.core.score.entity.ScoreMineDetailQuery;

/**
 * 培训电子档案管理
 * @author tangmin
 * @date 2017-01-12 13:34:33
 */
public interface ScoreMineDetailService extends BaseService<ScoreMineDetail, ScoreMineDetailQuery>{
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
	Boolean checkNameExit(ScoreMineDetail entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(ScoreMineDetail entity);

	/**
	 * 根据sid删除
	 * @param sid
	 */
	void deleteBySid(Long sid);
}
