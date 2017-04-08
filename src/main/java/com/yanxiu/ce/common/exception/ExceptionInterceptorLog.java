package com.yanxiu.ce.common.exception;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;
import java.lang.reflect.Method;

/**
 * 异常拦截处理
 * @author tangmin
 * @date 2016年2月23日
 */
public class ExceptionInterceptorLog implements ThrowsAdvice {

	private static final Logger logger = Logger.getLogger(ExceptionInterceptorLog.class);

	/**
	 * 对未知异常的处理. <br>
	 * Method method 执行的方法 Object[] args <br>
	 * 方法参数 Object target <br>
	 * 代理的目标对象 Throwable BizException 产生的异常 <br>
	 */
	public void afterThrowing(Method method, Object[] args, Object target, BizException ex) {

		logger.info("==>ExceptionInterceptorLog.BizException");
		logger.info("==>errCode:" + ex.getCode() + " errMsg:" + ex.getMsg());
		logger.info("==>" + ex.fillInStackTrace());
	}

	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {

		logger.error("==>ExceptionInterceptorLog.Exception");

		logger.error("==>Error class: " + target.getClass().getName());
		logger.error("==>Error method: " + method.getName());

		for (int i = 0; i < args.length; i++) {
			logger.error("==>args[" + i + "]: " + args[i]);
		}

		logger.error("==>Exception class: " + ex.getClass().getName());
		logger.error("==>" + ex.fillInStackTrace());
		ex.printStackTrace();
	}

}