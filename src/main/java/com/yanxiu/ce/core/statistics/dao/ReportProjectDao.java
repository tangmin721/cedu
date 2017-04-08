package com.yanxiu.ce.core.statistics.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;

/**
 * 项目统计
 * @author tangmin
 * @date 2016年8月18日
 */
@MybatisDao
public interface ReportProjectDao {
	
	/**
	 * 根据不同条件  groupBy
	 */
	List<NameValue> groupByProperty(@Param("property")String property,@Param("tableName")String tableName,
			@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,@Param("school")Long school,
			@Param("schoolYear")Long schoolYear);

}
