package com.yanxiu.ce.core.statistics.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportSchool;
import com.yanxiu.ce.core.statistics.entity.ReportSchoolQuery;

/**
 * 学校统计管理
 * @author tangmin
 * @date 2016-08-18 12:00:34
 */
@MybatisDao
public interface ReportSchoolDao extends BaseDao<ReportSchool, ReportSchoolQuery>{
	
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
	 * 清空表
	 */
	void truncate();
	
	/**
	 * 去重查询
	 * @return
	 */
	List<String> distinctByType(@Param("type")String type,@Param("province")Integer province,
			@Param("city")Integer city);
	
	/**
	 * 根据省，统计省各市的人数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupByCity(@Param("province")Integer province);
	
	/**
	 * 根据市，统计各区县的人数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupByTown(@Param("province")Integer province,@Param("city")Integer city);
	
	/**
	 * 本区县数据
	 * @param school
	 * @return
	 */
	List<NameValue> countTownSchool(@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town); 
	
	/**
	 * 根据不同条件  groupBy
	 */
	List<NameValue> groupByProperty(@Param("property")String property,@Param("tableName")String tableName,
			@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town);
	
}
