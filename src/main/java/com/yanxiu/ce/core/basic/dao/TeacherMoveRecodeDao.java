package com.yanxiu.ce.core.basic.dao;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherMoveRecode;
import com.yanxiu.ce.core.basic.entity.TeacherMoveRecodeQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 16:50:41
 */
@MybatisDao
public interface TeacherMoveRecodeDao extends BaseDao<TeacherMoveRecode, TeacherMoveRecodeQuery>{
	
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
	public Long selectCheckNameExit(@Param("name") String name, @Param("id") Long id);

	/**
	 * Limit Page查询
	 * @param query
	 * @return
	 */
	List<TeacherMoveRecode> selectInListPage(TeacherMoveRecodeQuery query);

	/**
	 * 总条数
	 * @param query
	 * @return
	 */
	Long selectInListTotal(TeacherMoveRecodeQuery query);

	/**
	 * Limit Page查询  ALL
	 * @param query
	 * @return
	 */
	List<TeacherMoveRecode> selectAllListPage(TeacherMoveRecodeQuery query);

	/**
	 * 总条数  ALL
	 * @param query
	 * @return
	 */
	Long selectAllListTotal(TeacherMoveRecodeQuery query);
	
}
