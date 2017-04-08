package com.yanxiu.ce.core.train.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.TrainOrg;
import com.yanxiu.ce.core.train.entity.TrainOrgQuery;

/**
 * 培训机构管理
 * @author tangmin
 * @date 2016-04-11 17:38:15
 */
@MybatisDao
public interface TrainOrgDao extends BaseDao<TrainOrg, TrainOrgQuery>{
	
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
	 * 查找name为@name，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("id")Long id);
	
	/**
	 * 根据orgNo查询
	 * @param orgNo
	 * @return
	 */
	TrainOrg selectByOrgNo(@Param("orgNo") String orgNo);
}
