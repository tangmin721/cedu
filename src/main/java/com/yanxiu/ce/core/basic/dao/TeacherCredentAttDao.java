package com.yanxiu.ce.core.basic.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherCredentAtt;
import com.yanxiu.ce.core.basic.entity.TeacherCredentAttQuery;

/**
 * 附件管理
 * @author tangmin
 * @date 2016-04-13 11:03:13
 */
@MybatisDao
public interface TeacherCredentAttDao extends BaseDao<TeacherCredentAtt, TeacherCredentAttQuery>{
	
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
	
}
