package com.yanxiu.ce.core.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.core.score.dao.ScoreTeacherDao;
import com.yanxiu.ce.core.score.entity.ScoreTeacher;
import com.yanxiu.ce.core.score.entity.ScoreTeacherQuery;
import com.yanxiu.ce.core.score.service.ScoreTeacherService;

/**
 * 
 * @author tangmin
 * @date 2016年8月31日
 */
@Service("scoreTeacherService")
public class ScoreTeacherServiceImpl extends BaseServiceImpl<ScoreTeacher, ScoreTeacherQuery> implements ScoreTeacherService{
	@Autowired
	private ScoreTeacherDao dao;

	@Override
	protected BaseDao<ScoreTeacher, ScoreTeacherQuery> dao() {
		return this.dao;
	}

	@Override
	public Pagination<ScoreTeacher> selectPagination(ScoreTeacherQuery query) {
		Long total = 0l;
		Long selectTotal = this.dao.selectListTotal(query);
		if(selectTotal!=null){
			total = selectTotal;
		}
		 
		Pagination<ScoreTeacher> pagination = new Pagination<ScoreTeacher>(query.getPageCurrent(),query.getPageSize(),total);
		pagination.setList(this.dao.selectListPage(query));
		return pagination;
	}

	

}