package com.yanxiu.ce.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.train.entity.TrainOrg;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserQuery;

/**
 * 用户管理
 * @author tangmin
 * @date 2016-03-08 11:27:47
 */
public interface UserService extends BaseService<User, UserQuery>{
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean selectCheckLoginNameExit(User entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(User entity);
	
	/**
	 * 更新密码（旧密码，新密码）
	 * @param entity
	 * @param newPwd
	 * @return
	 */
	String updatePwd(User entity,String newPwd);
	
	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	String resetPwd(Long id);
	
	/**
	 * 通过loginName获取用户
	 * @param loginName
	 * @return
	 */
	User selectByLoginName(String loginName);

	/**
	 * 密码认证方法
	 * @param loginName
	 * @param loginPwd
	 * @param rememberMe
	 * @param captcha
	 */
	User authenticate(String loginName, String loginPwd, boolean rememberMe,
			String captcha);
	
	/**
	 * sso认证方法
	 * @param loginName
	 * @param loginPwd
	 * @param rememberMe
	 * @param captcha
	 * @return
	 */
	User authenticateSSO(String passport);

	/**
	 * 根据UserId获取
	 * @return
	 */
	List<Role> getRoles();

	/**
	 * 判断是否超级管理员.
	 */
	boolean isSupervisor(Long id);
	
	/**
	 * type  0教师，1校长
	 * @param type
	 * @return
	 */
	User createTeacherUser(Teacher teacher);
	
	/**
	 * 创建学校用户
	 * @param type
	 * @return
	 */
	User createSchoolUser(School school);
	
	/**
	 * 创建培训机构管理员
	 */
	User createTrainOrgUser(TrainOrg trainOrg);
	
	/**
	 * 根据登录名删除用户
	 * @param loginName
	 */
	void deleteByLoginName(String loginName);
	
	Long updateUid(String loginName,Long uid);

	/**
	 * 用户信息导出表头信息
	 */
	List<String> getExcelFieldName();
	
	/**
	 * 用户信息导出内容
	 */
	List<List<String>> getExcelDatasByQuery(UserQuery query);
	
	/**
	 * 删除用户信息，数据库标志，数据不删除
	 */
	Long removeByIds(List<Long> ids);
	
	/**
	 * 根据登录名删除用户，数据库标记，数据不删除
	 */
	Long removeByLoginName(String loginName);
}
