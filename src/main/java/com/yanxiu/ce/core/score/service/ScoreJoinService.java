package com.yanxiu.ce.core.score.service;

import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.score.entity.ScoreJoin;
import com.yanxiu.ce.core.score.entity.ScoreJoinQuery;

/**
 * 学时申报管理
 * @author tangmin
 * @date 2016-08-04 13:48:34
 */
public interface ScoreJoinService extends BaseService<ScoreJoin, ScoreJoinQuery>{
	
	/**
	 * 页面分页
	 * @param query
	 * @return 返回分页对象
	 */
	Pagination<ScoreJoin> selectTownListPagination(ScoreJoinQuery query);
	
	/**
	 * 页面分页
	 * @param query
	 * @return 返回分页对象
	 */
	Pagination<ScoreJoin> selectProvinceListPagination(ScoreJoinQuery query);

}
