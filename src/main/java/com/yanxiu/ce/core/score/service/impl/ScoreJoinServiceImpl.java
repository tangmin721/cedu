package com.yanxiu.ce.core.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.core.score.dao.ScoreJoinDao;
import com.yanxiu.ce.core.score.entity.ScoreJoin;
import com.yanxiu.ce.core.score.entity.ScoreJoinQuery;
import com.yanxiu.ce.core.score.service.ScoreJoinService;

/**
 * 学时申报管理
 * @author tangmin
 * @date 2016-08-04 13:48:34
 */
@Service("scoreJoinService")
public class ScoreJoinServiceImpl extends BaseServiceImpl<ScoreJoin, ScoreJoinQuery> implements ScoreJoinService{
	@Autowired
	private ScoreJoinDao dao;

	@Override
	protected BaseDao<ScoreJoin, ScoreJoinQuery> dao() {
		return this.dao;
	}

	@Override
	public Pagination<ScoreJoin> selectTownListPagination(ScoreJoinQuery query) {
		Long total = 0l;
		Long selectTotal = this.dao.selectTownListTotal(query);
		if(selectTotal!=null){
			total = selectTotal;
		}
		 
		Pagination<ScoreJoin> pagination = new Pagination<ScoreJoin>(query.getPageCurrent(),query.getPageSize(),total);
		pagination.setList(this.dao.selectTownListPage(query));
		return pagination;
	}

	@Override
	public Pagination<ScoreJoin> selectProvinceListPagination(
			ScoreJoinQuery query) {
		
		Long total = 0l;
		Long selectTotal = this.dao.selectProvinceListTotal(query);
		if(selectTotal!=null){
			total = selectTotal;
		}
		
		Pagination<ScoreJoin> pagination = new Pagination<ScoreJoin>(query.getPageCurrent(),query.getPageSize(),total);
		pagination.setList(this.dao.selectProvinceListPage(query));
		return pagination;
	}
	

}