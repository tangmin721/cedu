package com.yanxiu.ce.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserQuery;

/**
 * 用户管理
 * @author tangmin
 * @date 2016-03-08 11:27:47
 */
@MybatisDao
public interface UserDao extends BaseDao<User, UserQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 查找code为@code，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckLoginNameExit(@Param("loginName")String code,@Param("id")Long id);
	
	/**
	 * 更新密码
	 * @param entity
	 * @return
	 */
	Long updatePwd(User entity);
	
	/**
	 * 根据loginName删除用户
	 * @param loginName
	 * @return
	 */
	Long deleteByLoginName(@Param("loginName")String loginName);
	
	
	/**
	 * 根据loginName更新uid  (研修远程注册)
	 */
	Long updateUid(@Param("loginName")String loginName,@Param("uid")Long uid);
	
	/**
	 * 导出excel表格数据
	 */
	List<User> selectExcelList(UserQuery query);
	
	/**
	 * 删除用户信息，数据库标志，数据不删除
	 */
	Long removeByIds(List<Long> ids);
	
	/**
	 * 删除用户信息，数据库标志，数据不删除
	 */
	Long removeByLoginName(@Param("loginName")String loginName);
}
