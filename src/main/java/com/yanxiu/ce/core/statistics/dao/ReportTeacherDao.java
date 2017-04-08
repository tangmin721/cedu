package com.yanxiu.ce.core.statistics.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportTeacher;
import com.yanxiu.ce.core.statistics.entity.ReportTeacherQuery;

/**
 * 消息管理
 * @author tangmin
 * @date 2016-08-16 11:56:03
 */
@MybatisDao
public interface ReportTeacherDao extends BaseDao<ReportTeacher, ReportTeacherQuery>{
	
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
			@Param("city")Integer city,@Param("town")Integer town,@Param("school")Long school,
			@Param("tchtype")Integer tchtype);
	
	/**
	 * 根据省，统计省各市的人数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupByCity(@Param("province")Integer province,@Param("tchtype")Integer tchtype);
	
	/**
	 * 根据市，统计各区县的人数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupByTown(@Param("province")Integer province,@Param("city")Integer city,@Param("tchtype")Integer tchtype);
	
	/**
	 * 根据区县，统计各校的人数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupBySchool(@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,@Param("tchtype")Integer tchtype);
	
	/**
	 * 本校数据
	 * @param school
	 * @return
	 */
	List<NameValue> countSchool(@Param("school")Long school,@Param("tchtype")Integer tchtype); 
	
	/**
	 * 根据不同条件  groupBy
	 */
	List<NameValue> groupByProperty(@Param("property")String property,@Param("tableName")String tableName,
			@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,@Param("school")Long school,@Param("tchtype")Integer tchtype);
	 
	/**
	 * 根据年龄统计
	 */
	List<NameValue> groupByAge(@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,
			@Param("school")Long school,@Param("tchtype")Integer tchtype);
	
	 
	/**
	 * 根据教龄统计
	 */
	List<NameValue> groupByJoinday(@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,
				@Param("school")Long school,@Param("tchtype")Integer tchtype);



	/**
	 * 统计证书获取人数
	 * @param uppct    如果pct是city ，则uppct就是province
	 * @param uppctValue   420000
	 * @param pct   province city  town
	 * @param credentType
	 * @return
	 */
	List<NameValue> groupCredentByPct(@Param("uppct")String uppct,
									  @Param("uppctValue")Integer uppctValue,
									  @Param("pct")String pct,
									  @Param("credentType")String credentType,
									  @Param("tchtype")Integer tchtype);

	List<NameValue> groupCredentBySchool(@Param("town")Integer town,
									  @Param("credentType")String credentType,
									  @Param("tchtype")Integer tchtype);


	List<NameValue> countCredentBySchool(@Param("school")Long school,
									  @Param("credentType")String credentType,
									  @Param("tchtype")Integer tchtype);

}
