package com.yanxiu.ce.core.statistics.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportScore;
import com.yanxiu.ce.core.statistics.entity.ReportScoreQuery;

/**
 * 学时统计管理
 * @author tangmin
 * @date 2016-08-22 12:22:10
 */
public interface ReportScoreService{
	
	/**
	 * 1、分批查询 未同步的tid
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
	 * 4、更新flag
	 */
	public void updateSyncFlag(@Param("tid")Long tid);
	
	/**
	 * 统计更新学分的任务
	 */
	public void jobTotalScore();

	/**
	 * 更新当前教师的学分总计
	 * @param tid
	 * @return
	 */				 
	public Integer syncTeacherScore(Long tid,String[] splitCRait,String[] splitUCRait);
	
	/**
	 * 根据不同条件  groupBy
	 */
	List<NameValue> groupByProperty(String iname,String property,String joinOnName,String tableName,
			Integer province,Integer city,Integer town,Long school,
			String startDate,String endDate,
			Integer startScore,Integer endScore);
}
