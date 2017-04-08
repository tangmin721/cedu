package com.yanxiu.ce.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yanxiu.ce.common.core.controller.BaseController;

/**
 * service方法切面
 * @author tangmin
 * @date 2016年7月12日
 */

@Aspect
public class AspectService {
	
	protected static final Logger logger = LoggerFactory.getLogger(AspectService.class);
	/**
	  * 定义一个切入点com.aop.service下所有类，所有方法
	  */
	 @Pointcut("execution(*  com.yanxiu.ce.*.service..*.*(..))")
	 public void allMethodService(){
	 }
	 
	 /**
	  * 定义一个切入点com.aop.service下所有类，所有方法
	  */
	 @Pointcut("execution(*  com.yanxiu.ce.*.controller..*.*(..))")
	 public void allMethodController(){
	 }
	 
	/* @Before("allMethod() && args(name)")
	 public void beforeMethod(String name){
	  System.out.println("前置通知方法！"+name);
	 }
	 
	 @AfterReturning(pointcut="allMethod()",returning="name")
	 public void returnMethod(String name){
	  System.out.println("后置通知方法！name="+name);
	 }
	 
	 @AfterThrowing(pointcut="allMethod()",throwing="ex")
	 public void doExceptionMethod(Exception ex){
	 
	 }
	 @After(value = "allMethod()")
	 public void doAfter(){
//	  System.out.println("最终通知方法！");
	 }*/
	 
	 @Around(value = "allMethodService()")
	 public Object  doAroundMethodService(ProceedingJoinPoint point) throws Throwable{
	  //if(){ 判断权限
	   long start=System.currentTimeMillis();
	   //拦截的实体类
	   Object target = point.getTarget();
	   //拦截的方法名称
	   String methodName = point.getSignature().getName();
	   
	  // System.out.println(target.getClass()+"@Around：执行目标方法之前...");
	   Object result = point.proceed();
	   logger.info("【Class:"+target.getClass()+",Method: "+methodName+"执行耗时 : "+(System.currentTimeMillis()-start)/1000f+" 秒 】");
	  //}
	   
	  return result;
	 }
	 
	 @Around(value = "allMethodController()")
	 public Object  doAroundMethodController(ProceedingJoinPoint point) throws Throwable{
	  //if(){ 判断权限
	   long start=System.currentTimeMillis();
	   //拦截的实体类
	   Object target = point.getTarget();
	   //拦截的方法名称
	   String methodName = point.getSignature().getName();
	   
	  // System.out.println(target.getClass()+"@Around：执行目标方法之前...");
	   Object result = point.proceed();
	   logger.info("【Class:"+target.getClass()+",Method: "+methodName+"执行耗时 : "+(System.currentTimeMillis()-start)/1000f+" 秒】 ");
	  //}
	   
	  return result;
	 }

}
