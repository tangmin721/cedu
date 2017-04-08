package com.yanxiu.ce.core.train.dao;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.train.entity.ProjectPerson;
import com.yanxiu.ce.core.train.entity.ProjectPersonQuery;

import java.util.List;

/**
 * 参培人名单管理
 * @author tangmin
 * @date 2016-10-24 16:32:04
 */
@MybatisDao
public interface ProjectPersonDao extends BaseDao<ProjectPerson, ProjectPersonQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验
	 * @param pid
	 * @param tid
	 * @return
	 */
	 Long selectCheckPidTidExit(@Param("pid") Long pid, @Param("tid") Long tid);


	/**
	 * 根据pid，获取一共需要生成的学时记录条数
	 * @param pid
	 * @return
	 */
	Integer selectMakeScoreTotalNum(@Param("pid") Long pid);

    /**
     * 根据项目id删除学分表里的学分记录
     * @param pid
     * @return
     */
    Long deleteScoreByPid(@Param("pid") Long pid);

    /**
     * 根据教师tid，pid删除学分
     * @param tid
     * @param pid
     * @return
     */
    Long deleteScoreByTidPid(@Param("tid")Long tid,@Param("pid") Long pid);


	/**
	 * selectMakeScoreProjectPerson
	 */
	List<ProjectPerson> selectMakeScoreProjectPerson(@Param("pid") Long pid);

    /**
     * 校验某个老师的项目学时是否存在
     * @param tid
     * @param pid
     * @return
     */
    Integer selectCheckScoreExist(@Param("tid")Long tid,@Param("pid") Long pid);
	
}
