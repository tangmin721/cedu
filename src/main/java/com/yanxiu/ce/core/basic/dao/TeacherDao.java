package com.yanxiu.ce.core.basic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;

/**
 * 教师管理
 * @author tangmin
 * @date 2016-03-30 18:03:20
 */
@MybatisDao
public interface TeacherDao extends BaseDao<Teacher, TeacherQuery>{
	
	/**
	 * 根据身份证获取
	 * @param idCard
	 * @return
	 */
	Teacher selectByIdCard(String idCard);
	
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
	 * 查找idCard为@idCard，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckIdCardExit(@Param("idCard")String idCard,@Param("id")Long id);
	
	/**
	 * 根据shool批量修改教师的省市县
	 * @param province
	 * @param city
	 * @param town
	 * @param school
	 * @return
	 */
	Long updateBatchSchool(@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,@Param("school")Long school);
	
	/**
	 * excel导出记录查询
	 * 
	 * 
	 */
	List<Teacher> selectExcelList(TeacherQuery query);
	
	/**
	 * 查询教师详细信息
	 * 
	 */
	Teacher selectViewInfo(Long id);
	
	/**
	 * 教师信息删除，数据库只进行标记，不删除数据
	 */
	Long removeByIds(List<Long> ids);
	
	/**
	 * 教师信息删除，数据库只进行标记，不删除数据
	 */
	Long removeById(Long id);
	
}
