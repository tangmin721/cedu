package com.yanxiu.ce.core.statistics.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportSchool;
import com.yanxiu.ce.core.statistics.entity.ReportSchoolQuery;

/**
 * 学校统计管理
 * @author tangmin
 * @date 2016-08-18 12:00:34
 */
public interface ReportSchoolService extends BaseService<ReportSchool, ReportSchoolQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(ReportSchool entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(ReportSchool entity);
	
	/**
	 * 定时调度  学校统计总任务
	 * @return
	 */
	String processTotal();
	
	/**0  数  统计  （根据类型统计 0教师，1校长）*/
	String processNum();
	/**1 类型（学校类型、学校类别）  统计**/
	String processProperty();
	
	
	/**
	 * 清空表
	 */
	void truncate();
	
	/**
	 * 根据type去重查询  比如city town  school
	 * @param type
	 * @return
	 */
	List<String> distinctByType(String type,Integer province,Integer city);
	
	/**
	 * 根据省，统计省市县的学校数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupByCity(Integer province);
	
	/**
	 * 根据市，统计各区县的学校数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupByTown(Integer province,Integer city);
	
	/**
	 * 本区县数据
	 * @param school
	 * @return
	 */
	List<NameValue> countTownSchool(Integer province,Integer city,Integer town); 
	
	/**
	 * 根据不同条件  groupBy
	 */
	List<NameValue> groupByProperty(String property,String tableName,
			Integer province,Integer city,Integer town);

}
