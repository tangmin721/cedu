package com.yanxiu.ce.system.service.impl;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.constant.AppConstant;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.BizException;
import com.yanxiu.ce.common.exception.ValidateException;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.utils.code.AES;
import com.yanxiu.ce.common.utils.code.MD5;
import com.yanxiu.ce.common.utils.code.PassWordCreate;
import com.yanxiu.ce.common.validate.BeanValidator;
import com.yanxiu.ce.common.validate.UpdatePwd;
import com.yanxiu.ce.common.validate.ValidatorResult;
import com.yanxiu.ce.core.basic.entity.*;
import com.yanxiu.ce.core.basic.service.CourseService;
import com.yanxiu.ce.core.basic.service.GradeService;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.StageService;
import com.yanxiu.ce.core.mq.entity.RegisterMsg;
import com.yanxiu.ce.core.mq.enums.MsgStatusEnum;
import com.yanxiu.ce.core.mq.enums.MsgTypeEnum;
import com.yanxiu.ce.core.mq.service.RegisterMsgService;
import com.yanxiu.ce.core.train.entity.TrainOrg;
import com.yanxiu.ce.system.dao.UserDao;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserQuery;
import com.yanxiu.ce.system.enums.UserStatusEnum;
import com.yanxiu.ce.system.enums.UserTypeEnum;
import com.yanxiu.ce.system.service.*;
import com.yanxiu.ce.system.shiro.ShiroRealm;
import com.yanxiu.ce.system.shiro.ShiroRealm.HashPassword;
import com.yanxiu.ce.system.shiro.ShiroUser;
import com.yanxiu.ce.system.shiro.UserData;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * @author tangmin
 * @date 2016-03-08 11:27:47
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, UserQuery> implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final String DEFAULT_PWD = "123456";//encrypt
	
	@Autowired
	private UserDao dao;

	@Autowired
	private ShiroRealm shiroRealm;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private SchoolService  schoolService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TownService townService;
	
	@Autowired
	private RegisterMsgService registerMsgService;
	
	@Value(value="${teachehubei.appKey}")
	private String APP_KEY;
	
	@Value(value="${AES_KEY}")
	private String AES_KEY;
	
	@Override
	protected BaseDao<User, UserQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 角色  ID  Map
	 */
	public final static Map<String,Long> ROLE_TYPE_ID_MAP = Maps.newHashMap();   
	static {  
		ROLE_TYPE_ID_MAP.put("SUP_ADMIN", 1l);  
		ROLE_TYPE_ID_MAP.put("SYS_ADMIN", 2l);
		ROLE_TYPE_ID_MAP.put("PROVINCE_ADMIN", 118l);
		ROLE_TYPE_ID_MAP.put("CITY_ADMIN", 119l);
		ROLE_TYPE_ID_MAP.put("TOWN_ADMIN", 120l);
		ROLE_TYPE_ID_MAP.put("SCHOOL_ADMIN",121l);
		ROLE_TYPE_ID_MAP.put("TEACHER", 122l);
		ROLE_TYPE_ID_MAP.put("MASTER", 123l);
		ROLE_TYPE_ID_MAP.put("TRAINORG", 125l);
	    
	}  
	/**
	 * 校验entity是否可修改（logName是否存在）
	 */
	@Override
	public Boolean selectCheckLoginNameExit(User entity) {
		Long count = this.dao.selectCheckLoginNameExit(entity.getLoginName(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(User entity) {
		if(!selectCheckLoginNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"登录名已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"登录名已经存在，修改失败");
			}
		}
		String msg = "";
		
		if(entity.getId()==null){
			//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
			if (shiroRealm != null) {
				entriptUserPwd(entity, entity.getPlainPassword());//密码转换
				entity.setUserNo(entity.getLoginName());//暂时没定义用户No的规则
				this.insert(entity);
				producerMsg(entity);
			}
			msg = "添加成功！";
		}else {
			User old = this.selectById(entity.getId());
			if(old.getUid()==null){
				producerMsg(entity);
			}
			this.update(entity);
				msg = "编辑成功！";
		}
		
		//分配角色
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(ROLE_TYPE_ID_MAP.get(UserTypeEnum.getName(entity.getType())));
		userRoleService.saveUserRole(entity.getId(),roleIds);
		
		return msg;
	}

	/**
	 * 保存 注册用户 消息队列
	 * @param entity
	 */
	private void producerMsg(User entity) {
		if(entity.getReflag()&&
				//只有教师or校长需要注册用户
				(entity.getType()==UserTypeEnum.TEACHER.getValue()||entity.getType()==UserTypeEnum.MASTER.getValue())){
			saveRegisterMsg(entity);//保存 注册用户 消息队列
		}
	}

	
	
	/**
	 * 注册用户，保存到 消息队列的表中（然后通过遍历表，执行注册）
	 * @param user
	 */
	private void saveRegisterMsg(User user) {
		RegisterMsg registerMsg = new RegisterMsg();
		registerMsg.setAppKey(APP_KEY);
		registerMsg.setRealname(user.getRealName());
		registerMsg.setPassport(user.getLoginName());
		registerMsg.setPassword(user.getMd5());
		registerMsg.setMobile(user.getMobileNo());
		registerMsg.setEmail(user.getEmail());
		registerMsg.setIdCard(user.getIdCard());
		
		if(user.getSchool()!=null){
			School school = schoolService.selectById(user.getSchool());
			if(school!=null){
				registerMsg.setSchoolName(school.getName());
			}
		}
		
		if(user.getStage()!=null){
			Stage stage = stageService.selectById(Long.parseLong(user.getStage()));
			if(stage!=null){
				registerMsg.setStage(stage.getName());
			}
		}
		
		if(user.getCourse()!=null){
			Course course = courseService.selectById(Long.parseLong(user.getCourse()));
			if(course!=null){
				registerMsg.setCourse(course.getName());
			}
		}
		
		if(user.getGrade()!=null){
			Grade grade = gradeService.selectById(Long.parseLong(user.getGrade()));
			if(grade!=null){
				registerMsg.setGrade(grade.getName());
			}
		}
		
		registerMsg.setProvince(provinceService.getNameByNo(user.getProvince()));
		registerMsg.setCity(cityService.getNameByNo(user.getCity()));
		registerMsg.setArea(townService.getNameByNo(user.getTown()));
		
		if(StringUtils.isNoneBlank(user.getGender())){
			if("1".equals(user.getGender())){
				registerMsg.setGender("男");
			}else if("0".equals(user.getGender())){
				registerMsg.setGender("女");
			}
		}
		
		//设置状态
		registerMsg.setType(MsgTypeEnum.REGISTER_USER.getValue());
		registerMsg.setStatus(MsgStatusEnum.UNPROCESS.getValue());
		registerMsgService.saveOrUpdate(registerMsg);
	}

	/**
	 * 明文加密
	 * @param entity
	 * @param plainPassword
	 */
	private void entriptUserPwd(User entity, String plainPassword) {
		HashPassword hashPassword = ShiroRealm.encryptPassword(plainPassword);
		entity.setSalt(hashPassword.salt);
		entity.setLoginPwd(hashPassword.password);
		//entity.setMd5(MD5.encode(plainPassword));
		entity.setMd5(AES.encrypt(plainPassword, AES_KEY));
	}

	/**
	 * 更新密码（旧密码，新密码）
	 */
	@Override
	@Transactional
	public String updatePwd(User entity,String newPwd) {
		
		if (entity == null){
			throw new RuntimeException("update entity:T is null");
		}
		//校验新旧密码是否匹配
		boolean isMatch = ShiroRealm.validatePassword(entity.getPlainPassword(), entity.getLoginPwd(), entity.getSalt());
		if (isMatch) {
			
			entity.setLastAlertPwdTime(new Date());
			entity.setIsChangedPwd(AppConstant.TRUE_INT);
			
			//hibernate validate校验
			ValidatorResult validateResult = BeanValidator.validateResult(entity, UpdatePwd.class);
			if(!validateResult.getFlag()){
				throw new ValidateException(ValidateException.UPDATE_FAILD, JSON.toJSONString(validateResult.getErrorObjs()));
			}
			entriptUserPwd(entity,newPwd);//加密
			Long result = this.dao.updatePwd(entity);
			if (result <= 0){
				throw BizException.DB_UPDATE_RESULT_0;
			}
			shiroRealm.clearCachedAuthorizationInfo(entity.getLoginName());
			return "修改密码成功！";
		}else{
			throw new IncorrectCredentialsException("旧密码输入错误！");
		}
	}

	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	@Transactional
	public String resetPwd(Long id){
		User user = this.selectById(id);
		entriptUserPwd(user,DEFAULT_PWD);//加密
		this.dao.updatePwd(user);
		return "重置密码成功！";
	}

	/**
	 * 根据loginName获取用户
	 */
	@Override
	public User selectByLoginName(String loginName) {
		User user = null;
		UserQuery query = new UserQuery();
		query.setLoginName(loginName);
		query.setLoginNameLike(false);
		List<User> users = selectList(query);
		if(users.size()==0){
			throw new BizException("找不到此用户");
		}else if(users.size()>1){
			throw new BizException("用户表异常：此登录名有多条记录");
		}else{
			user = users.get(0);
		}
		return user;
	}


	/**
	 * 认证授权
	 */
	@Override
	public User authenticate(String loginName, String loginPwd,
			boolean rememberMe, String captcha) {
		User user = null;
		try {
			// 1. 接受提交的当事人和证书：
			UsernamePasswordToken token = new UsernamePasswordToken(loginName,
					loginPwd);
			token.setRememberMe(rememberMe);
			// 2. 获取当前Subject：
			Subject subject  = SecurityUtils.getSubject();
			// 3. 登录：
			if (!subject.isAuthenticated()) {
				subject.login(token);
				Session session = subject.getSession();
				UserData userData = (ShiroUser) subject.getPrincipal();
				user = userData.getUser();
				session.setAttribute("currentUser", user);
				logger.info("User [" + user.getLoginName()
						+ "] logged in successfully.");
				
			}
		} catch (UnknownAccountException ex) {
			throw new BizException("用户名或密码不正确!");
		} catch (IncorrectCredentialsException ice) {
			throw new BizException("用户名或密码不正确!");
		} catch (LockedAccountException lae) {
			throw new BizException("账户已被锁定!");
		} catch (DisabledAccountException dex){
			throw new BizException("账户未激活!");
		} catch (AuthenticationException ae) {
			throw new BizException("认证错误!");
		}
		return user;
	}

	/**
	 * SSO认证授权
	 */
	@Override
	public User authenticateSSO(String passport) {
		User user = null;
		try {
			// 1. 接受提交的当事人和证书：
			UsernamePasswordToken token = new UsernamePasswordToken(passport,
					"$H12$212(^&)i3h6jhk674&^@*0)");
			token.setRememberMe(true);
			// 2. 获取当前Subject：
			Subject subject  = SecurityUtils.getSubject();
			// 3. 登录：
			if (!subject.isAuthenticated()) {
				subject.login(token);
				Session session = subject.getSession();
				UserData userData = (ShiroUser) subject.getPrincipal();
				user = userData.getUser();
				session.setAttribute("currentUser", user);
				logger.info("User [" + user.getLoginName()
						+ "] logged in successfully.");
				
			}
		} catch (UnknownAccountException ex) {
			throw new BizException("用户名或密码不正确!");
		} catch (IncorrectCredentialsException ice) {
			throw new BizException("用户名或密码不正确!");
		} catch (LockedAccountException lae) {
			throw new BizException("账户已被锁定!");
		} catch (DisabledAccountException dex){
			throw new BizException("账户未激活!");
		} catch (AuthenticationException ae) {
			throw new BizException("认证错误!");
		}
		return user;
	}
	
	@Override
	public List<Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 判断是否超级管理员.
	 */
	@Override
	public boolean isSupervisor(Long id) {
		return id==UserTypeEnum.SUP_ADMIN.getValue();
	}

	/**
	 * 根据生成教师，同时生成教师账号
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class}) 
	public User createTeacherUser(Teacher teacher) {
		User user = null;
		UserQuery query = new UserQuery();
		query.setLoginName(teacher.getIdCard());
		query.setLoginNameLike(false);
		List<User> users = selectList(query);
		if(users.size()==1){
			user = users.get(0);
		}else{
			user = new User();
		}
		user.setIdCard(teacher.getIdCard());
		
		user.setProvince(teacher.getProvince());
		user.setCity(teacher.getCity());
		user.setTown(teacher.getTown());
		user.setSchool(teacher.getSchool());
		
//		if(teacher.getSex()!=null){
//			user.setGender(teacher.getSex().toString());
//		}
	/*	if(teacher.getGrade()!=null){
			user.setGrade(teacher.getGrade().toString());
		}
		if(teacher.getCourse()!=null){
			user.setCourse(teacher.getCourse().toString());
		}
		
		if(teacher.getStage()!=null){
			user.setStage(teacher.getStage().toString());
		}*/
		//登录名，密码
		
		//user.setLoginName(idCard);
		user.setLoginName(teacher.getIdCard());//身份证号
		//String plainpw = idCard.substring(idCard.length()-6, idCard.length());
		//user.setPlainPassword(plainpw);
		user.setPlainPassword(PassWordCreate.createPassWord(10));
		
		user.setUserNo(teacher.getTno().toString());
		user.setRealName(teacher.getName());
		
		//RoleQuery roleQuery = new RoleQuery();
		
		
		if(teacher.getType()==0){
			//roleQuery.setCode("TEACHER");
			user.setType(UserTypeEnum.TEACHER.getValue());
		}else{
			//roleQuery.setCode("MASTER");
			user.setType(UserTypeEnum.MASTER.getValue());
		}
		
		/*user.setMobileNo(teacher.getMobile());
		user.setEmail(teacher.getEmail());*/
		user.setStatus(UserStatusEnum.ACTIVED.getValue());
		
		//保存用户
		user.setReflag(true);
		this.saveOrUpdate(user);
		
//		roleQuery.setActive("1");
//		roleQuery.setActiveLike(false);
//		roleQuery.setDeleted(0);
//		roleQuery.setCodeLike(false);
//		List<Role> roles = roleService.selectList(roleQuery);
//		for(Role role:roles){
//			roleIds.add(role.getId());
//		}
		
		
		return user;
	}

	@Override
	@Transactional
	public User createSchoolUser(School school) {
		User user = null;
		String schoolNo = school.getSchoolNo().toString();
		
		UserQuery query = new UserQuery();
		query.setLoginName(schoolNo);
		query.setLoginNameLike(false);
		List<User> users = selectList(query);
		if(users.size()==1){
			user = users.get(0);
		}else{
			user = new User();
		}
		
		user.setProvince(school.getProvince());
		user.setCity(school.getCity());
		user.setTown(school.getTown());
		user.setSchool(school.getId());
		
		//登录名，密码
		
		user.setLoginName(schoolNo);
		//String plainpw = schoolNo.substring(schoolNo.length()-6, schoolNo.length());
		//user.setPlainPassword(plainpw);
		user.setPlainPassword(PassWordCreate.createPassWord(10));
		
		user.setUserNo(school.getSchoolNo().toString());
		user.setRealName(school.getName());
		
		//RoleQuery roleQuery = new RoleQuery();
		
		user.setType(UserTypeEnum.SCHOOL_ADMIN.getValue());
		
		user.setMobileNo(school.getPhone());
		user.setEmail(school.getEmail());
		user.setStatus(UserStatusEnum.ACTIVED.getValue());
		
		//保存用户
		user.setReflag(true);
		this.saveOrUpdate(user);
		
//		roleQuery.setActive("1");
//		roleQuery.setActiveLike(false);
//		roleQuery.setDeleted(0);
//		roleQuery.setCodeLike(false);
//		List<Role> roles = roleService.selectList(roleQuery);
//		for(Role role:roles){
//			roleIds.add(role.getId());
//		}
		
		
		return user;
	}

	@Override
	@Transactional
	public User createTrainOrgUser(TrainOrg trainOrg) {
		User user = null;
		String orgNo = trainOrg.getOrgNo().toString();
		
		UserQuery query = new UserQuery();
		query.setLoginName(orgNo);
		query.setLoginNameLike(false);
		List<User> users = selectList(query);
		if(users.size()==1){
			user = users.get(0);
		}else{
			user = new User();
		}
		
		user.setProvince(trainOrg.getProvince());
		user.setCity(trainOrg.getCity());
		user.setTown(trainOrg.getTown());
		user.setSchool(trainOrg.getId());
		
		//登录名，密码
		
		user.setLoginName(orgNo);
		String plainpw = PassWordCreate.createPassWord(10);
		user.setPlainPassword(plainpw);
		
		user.setUserNo(trainOrg.getOrgNo().toString());
		user.setRealName(trainOrg.getName());
		
		//RoleQuery roleQuery = new RoleQuery();
		
		
		user.setType(UserTypeEnum.TRAINORG.getValue());
		
		user.setMobileNo(trainOrg.getPhone());
		user.setEmail(trainOrg.getEmail());
		user.setStatus(UserStatusEnum.ACTIVED.getValue());
		
		//保存用户
		user.setReflag(true);
		this.saveOrUpdate(user);
		
//		roleQuery.setActive("1");
//		roleQuery.setActiveLike(false);
//		roleQuery.setDeleted(0);
//		roleQuery.setCodeLike(false);
//		List<Role> roles = roleService.selectList(roleQuery);
//		for(Role role:roles){
//			roleIds.add(role.getId());
//		}
		
		return user;
	}

	@Override
	public void deleteByLoginName(String loginName) {
		this.dao.deleteByLoginName(loginName);
	}

	@Override
	public Long updateUid(String loginName, Long uid) {
		return this.dao.updateUid(loginName, uid);
	}

	@Override
	public List<String> getExcelFieldName(){
		List<String> fieldNameList = new ArrayList<String>();
//		fieldNameList.add("省");
		fieldNameList.add("市");
		fieldNameList.add("区县");
		fieldNameList.add("学校名称");
		fieldNameList.add("姓名");
		fieldNameList.add("登录名");
		fieldNameList.add("密码");
		return fieldNameList;
	}
	
	@Override
	public List<List<String>> getExcelDatasByQuery(UserQuery query){
		List<User> userList = this.dao.selectExcelList(query);
		return changeObjToStringList(userList);
	}
	
	/**
	 * 导出 转换为excel数据
	 * @param teachers
	 * @return
	 */
	private List<List<String>> changeObjToStringList(List<User> userList) {
		List<List<String>> result = Lists.newArrayList();
		for(User user : userList){
			List<String> ls = Lists.newArrayList();
//			ls.add(teacher.getProvinceName());
			ls.add(user.getCityName());
			ls.add(user.getTownName());
			ls.add(user.getSchoolName());
			ls.add(user.getRealName());
			ls.add(user.getLoginName());
			ls.add(AES.decrypt(user.getMd5(), AES_KEY));
			result.add(ls);
		}
		return result;
	}
	
	public Long removeByIds(List<Long> ids){
		return this.dao.removeByIds(ids);
	}
	
	public Long removeByLoginName(String loginName){
		return this.dao.removeByLoginName(loginName);
	}
}