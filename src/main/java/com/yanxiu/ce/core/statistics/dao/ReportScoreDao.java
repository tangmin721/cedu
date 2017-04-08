package com.yanxiu.ce.core.statistics.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportScore;
import com.yanxiu.ce.core.statistics.entity.ReportScoreQuery;

/**
 * 学时统计管理
 * @author tangmin
 * @date 2016-08-22 12:22:10
 */
@MybatisDao
public interface ReportScoreDao{
	
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
	 * 未同步的tid总数
	 * @param size
	 * @return
	 */
	public Integer countUnsyncTids();
	
	/**
	 * 分批查询 未同步的tid
	 */
	public List<Long> selectUnsyncTids(@Param("size")Integer size);
	
	
	/**
	 * #2统计 tid的总学分
	 * @return
	 */
	public Integer sumScoreByTid(@Param("tid")Long tid);
	
	/**
	 *#3 更新 t_teacher 的总学分
	*/
	public void updateTotalScore(@Param("totalScore")Integer totalScore,@Param("tid")Long tid);
	public void updateTotalScoreNum(@Param("totalScoreNum")Double totalScoreNum,@Param("tid")Long tid);
	
	/**
	 * 3、4在一个事务
	 */
	public void updateSyncFlag(@Param("tid")Long tid);
	
	
	/**
	 * 根据不同条件  groupBy 
	 */
	List<NameValue> groupByProperty(@Param("iname")String iname,@Param("property")String property,@Param("tableName")String tableName,
			@Param("joinOnName")String joinOnName,
			@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,@Param("school")Long school,
			@Param("startDate")String startDate,@Param("endDate")String endDate,
			@Param("startScore")Integer startScore,@Param("endScore")Integer endScore);
	
	
}
