package com.yanxiu.ce.core.score.service;

import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.score.entity.ScoreTeacher;
import com.yanxiu.ce.core.score.entity.ScoreTeacherQuery;

/**
 * 学时申报管理
 * @author tangmin
 * @date 2016-08-04 13:48:34
 */
public interface ScoreTeacherService extends BaseService<ScoreTeacher, ScoreTeacherQuery>{
	
	/**
	 * 页面分页
	 * @param query
	 * @return 返回分页对象
	 */
	Pagination<ScoreTeacher> selectPagination(ScoreTeacherQuery query);

}
