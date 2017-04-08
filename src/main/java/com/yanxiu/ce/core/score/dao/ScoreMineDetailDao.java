package com.yanxiu.ce.core.score.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.score.entity.ScoreMineDetail;
import com.yanxiu.ce.core.score.entity.ScoreMineDetailQuery;

/**
 * 培训电子档案管理
 * @author tangmin
 * @date 2017-01-12 13:34:33
 */
@MybatisDao
public interface ScoreMineDetailDao extends BaseDao<ScoreMineDetail, ScoreMineDetailQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 查找name为@name，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("id")Long id);
	
	
	Long deleteBySid(@Param("sid") Long sid);
	
}
