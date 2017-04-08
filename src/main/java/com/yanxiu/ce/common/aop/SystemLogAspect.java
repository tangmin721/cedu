package com.yanxiu.ce.common.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserLog;
import com.yanxiu.ce.system.enums.UserLogTypeEnum;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.UserLogService;

/**
 * 系统日志切点
 * 
 * @author tangmin
 * @date 2016年7月12日
 */
@Aspect
@Component
public class SystemLogAspect {

	@Resource
	private UserLogService userLogService;

	// 本地异常日志记录对象
	private static final Logger logger = LoggerFactory
			.getLogger(SystemLogAspect.class);

	// Service层切点
	@Pointcut("@annotation(com.yanxiu.ce.common.aop.SystemServiceLog)")
	public void serviceAspect() {
	}

	// Controller层切点
	@Pointcut("@annotation(com.yanxiu.ce.common.aop.SystemControllerLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		User user = ShiroUtils.getCurrentUser();
		// 请求的IP
		//String ip = request.getRemoteAddr();
		String ip = getClientIP(request);
		try {
			// *========控制台输出=========*//
			// System.out.println("=====前置通知开始=====");
			// System.out.println("请求方法:" +
			// (joinPoint.getTarget().getClass().getName() + "." +
			// joinPoint.getSignature().getName() + "()"));
			// System.out.println("方法描述:" +
			// getControllerMethodDescription(joinPoint));
			// System.out.println("请求人:" + user.getLoginName());
			// System.out.println("请求IP:" + ip);
			// *========数据库日志=========*//
			UserLog userLog = new UserLog();
			userLog.setLoginName(user.getLoginName());
			userLog.setIp(ip);
			userLog.setOperateStatus(1);// 1成功
			userLog.setContent((joinPoint.getTarget().getClass().getName()
					+ "." + joinPoint.getSignature().getName() + "()"));
			userLog.setDescription(getControllerMethodDescription(joinPoint));
			userLog.setOperType(UserLogTypeEnum.SYS_OPER.getValue());// 系统操作
			// 保存数据库
			userLogService.insert(userLog);

			// System.out.println("=====前置通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		User user = ShiroUtils.getCurrentUser();
		// 获取请求ip
		// String ip = request.getRemoteAddr();
		String ip = getClientIP(request);
	//	String ip = getRemoteAddrIp(request);
		// 获取用户请求方法的参数并序列化为JSON格式字符串
		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
			}
		}
		try {
			/* ========控制台输出========= */
			// System.out.println("=====异常通知开始=====");
			// System.out.println("异常代码:" + e.getClass().getName());
			// System.out.println("异常信息:" + e.getMessage());
			// System.out.println("异常方法:" +
			// (joinPoint.getTarget().getClass().getName() + "." +
			// joinPoint.getSignature().getName() + "()"));
			// System.out.println("方法描述:" +
			// getServiceMthodDescription(joinPoint));
			// System.out.println("请求登录名:" + user.getLoginName());
			// System.out.println("请求IP:" + ip);
			// System.out.println("请求参数:" + params);
			/* ==========数据库日志========= */
			UserLog userLog = new UserLog();
			userLog.setLoginName(user.getLoginName());
			userLog.setIp(ip);
			userLog.setOperateStatus(0);// 0失败
			userLog.setContent((joinPoint.getTarget().getClass().getName()
					+ "." + joinPoint.getSignature().getName() + "()"));
			userLog.setDescription(getServiceMthodDescription(joinPoint));
			userLog.setOperType(UserLogTypeEnum.SYS_OPER.getValue());
			// 保存数据库
			userLogService.saveOrUpdate(userLog);
			// System.out.println("=====异常通知结束=====");
		} catch (Exception ex) {
			// 记录本地异常日志
			logger.error("==异常通知异常==");
			logger.error("异常信息:{}", ex.getMessage());
		}
		/* ==========记录本地异常日志========== */
		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget()
				.getClass().getName()
				+ joinPoint.getSignature().getName(), e.getClass().getName(),
				e.getMessage(), params);

	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemServiceLog.class)
							.description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(
							SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	/***
	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String fromSource = "X-Real-IP";
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			fromSource = "X-Forwarded-For";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			fromSource = "Proxy-Client-IP";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			fromSource = "WL-Proxy-Client-IP";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			fromSource = "request.getRemoteAddr";
		}
		logger.info("App Client IP: "+ip+", fromSource: "+fromSource);
		return ip;
	}
}
