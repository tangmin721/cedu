package com.yanxiu.ce.core.score.dao;

import java.util.List;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.score.entity.ScoreJoin;
import com.yanxiu.ce.core.score.entity.ScoreJoinQuery;

/**
 * 学时申报管理
 * @author tangmin
 * @date 2016-08-04 13:48:34
 */
@MybatisDao
public interface ScoreJoinDao extends BaseDao<ScoreJoin, ScoreJoinQuery>{
	
	/**
	 * 区县级 Limit Page查询
	 * @param query
	 * @return
	 */
	List<ScoreJoin> selectTownListPage(ScoreJoinQuery query);
	
	/**
	 * 区县级 总条数
	 * @param query
	 * @return
	 */
	Long selectTownListTotal(ScoreJoinQuery query);
	
	
	/**
	 * 省级 Limit Page查询
	 * @param query
	 * @return
	 */
	List<ScoreJoin> selectProvinceListPage(ScoreJoinQuery query);
	
	/**
	 * 省级 总条数
	 * @param query
	 * @return
	 */
	Long selectProvinceListTotal(ScoreJoinQuery query);
}
