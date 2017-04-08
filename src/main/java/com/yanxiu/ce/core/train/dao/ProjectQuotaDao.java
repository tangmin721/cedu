package com.yanxiu.ce.core.train.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.ProjectQuota;
import com.yanxiu.ce.core.train.entity.ProjectQuotaQuery;

/**
 * 名额分配管理
 * @author tangmin
 * @date 2016-04-21 09:51:46
 */
@MybatisDao
public interface ProjectQuotaDao extends BaseDao<ProjectQuota, ProjectQuotaQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq(@Param("pid")Long pid);
	
	/**
	 * 查找name为@name，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("id")Long id);
	
	/**
	 * 根据项目id获取已分配名额人数
	 */
	Integer countedPid(@Param("pid")Long pid);
	
	/**
	 * 据pid，省市县校 获取记录条数 (判断记录是否已经存在)
	 * @param pid
	 * @param province
	 * @param city
	 * @param town
	 * @param school
	 * @return
	 */
	Integer countPpcts(@Param("pid")Long pid,
			@Param("province")Integer province,@Param("city")Integer city,
			@Param("town")Integer town,@Param("school")Long school);
	
	/**
	 * 据pid，省市县校   获取分配的名额
	 * @param pid
	 * @param province
	 * @param city
	 * @param town
	 * @param school
	 * @return
	 */
	Integer numSelectPpcts(@Param("pid")Long pid,
			@Param("province")Integer province,@Param("city")Integer city,
			@Param("town")Integer town,@Param("school")Long school);
}
