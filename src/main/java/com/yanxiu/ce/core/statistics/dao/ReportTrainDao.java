package com.yanxiu.ce.core.statistics.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportScore;
import com.yanxiu.ce.core.statistics.entity.ReportScoreQuery;

/**
 * 已陪 培训 统计
 * @author tangmin
 * @date 2016年8月25日
 */
@MybatisDao
public interface ReportTrainDao{
	
	/**
	 * 根据不同条件  groupBy 
	 */
	List<NameValue> groupByProperty(@Param("iname")String iname,@Param("property")String property,@Param("tableName")String tableName,
			@Param("joinOnName")String joinOnName,
			@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,@Param("school")Long school,
			@Param("startDate")String startDate,@Param("endDate")String endDate,
			@Param("startScore")Integer startScore,@Param("endScore")Integer endScore);
	
	
}
