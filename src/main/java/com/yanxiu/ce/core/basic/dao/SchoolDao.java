package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.SchoolQuery;

/**
 * 学校管理
 * @author tangmin
 * @date 2016-03-22 19:04:55
 */
@MybatisDao
public interface SchoolDao extends BaseDao<School, SchoolQuery>{
	
	/**
	 * 获取sequence
	 */
	Long nextSequenceVal();
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 查找code为@code，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("town")Integer town,@Param("id")Long id);
	
}
