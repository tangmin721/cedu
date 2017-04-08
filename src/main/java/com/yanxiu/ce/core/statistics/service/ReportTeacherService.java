package com.yanxiu.ce.core.statistics.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportTeacher;
import com.yanxiu.ce.core.statistics.entity.ReportTeacherQuery;

import java.util.List;

/**
 * 消息管理
 * @author tangmin
 * @date 2016-08-16 11:56:03
 */
public interface ReportTeacherService extends BaseService<ReportTeacher, ReportTeacherQuery>{
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
	Boolean checkNameExit(ReportTeacher entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(ReportTeacher entity);

	/**
	 * 定时调度  教师统计总任务
	 * @return
	 */
	String processTeacherTotal();
	
	String processTeacher(Integer tchtype);
	/**0 教师人数  统计  （根据类型统计 0教师，1校长）*/
	String processTeacherNum(Integer tchtype);
	/**1 学历（学段、学科、职称、行政职务、骨干类型）  统计**/
	String processProperty(Integer tchtype);
	
	/**2 根据年龄统计  统计**/
	String processAge(Integer tchtype);
	
	/**3 根据教龄统计  统计**/
	String processJoinday(Integer tchtype);
	
	/**
	 * 清空表
	 */
	void truncate();
	
	/**
	 * 根据type去重查询  比如city town  school
	 * @param type
	 * @return
	 */
	List<String> distinctByType(String type,Integer province,Integer city,Integer town,Long school,Integer tchtype);
	
	/**
	 * 根据省，统计省市县的人数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupByCity(Integer province,Integer tchtype);
	
	/**
	 * 根据市，统计各区县的人数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupByTown(Integer province,Integer city,Integer tchtype);
	
	/**
	 * 根据区县，统计各校的人数分布的情况
	 * @param province
	 * @return
	 */
	List<NameValue> groupBySchool(Integer province,Integer city,Integer town,Integer tchtype);
	
	/**
	 * 本校数据
	 * @param school
	 * @return
	 */
	List<NameValue> countSchool(Long school,Integer tchtype); 
	
	/**
	 * 根据不同条件  groupBy
	 */
	List<NameValue> groupByProperty(String property,String tableName,
			Integer province,Integer city,Integer town,Long school,Integer tchtype);
	 
	
	/**
	 * 根据年龄统计
	 */
	List<NameValue> groupByAge(Integer province,Integer city,Integer town,
			Long school,Integer tchtype);
	
	/**
	 * 根据教龄统计
	 */
	List<NameValue> groupByJoinday(Integer province,Integer city,Integer town,
			Long school,Integer tchtype);



	/**
	 * 统计证书获取人数
	 * @param uppct    如果pct是city ，则uppct就是province
	 * @param uppctValue   420000
	 * @param pct   province city  town
	 * @param credentType
	 * @return
	 */
	List<NameValue> groupCredentByPct(String uppct, Integer uppctValue, String pct, String credentType, Integer tchtype);
	List<NameValue> groupCredentBySchool(Integer town,String credentType,Integer tchtype);
	List<NameValue> countCredentBySchool(Long school,String credentType,Integer tchtype);
}
