package com.yanxiu.ce.core.score.dao;

import java.util.List;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.score.entity.ScoreTeacher;
import com.yanxiu.ce.core.score.entity.ScoreTeacherQuery;

/**
 * 教师学时汇总
 * @author tangmin
 * @date 2016年8月31日
 */
@MybatisDao
public interface ScoreTeacherDao extends BaseDao<ScoreTeacher, ScoreTeacherQuery>{
	
	/**
	 * 分页
	 * @param query
	 * @return
	 */
	List<ScoreTeacher> selectListPage(ScoreTeacherQuery query);
	
	/**
	 * group by teacher 总条数
	 * @param query
	 * @return
	 */
	Long selectListTotal(ScoreTeacherQuery query);
	
}
