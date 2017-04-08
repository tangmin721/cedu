package com.yanxiu.ce.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description:  业务异常基类，所有业务异常都必须继承于此异常
 *  定义异常时，需要先确定异常所属模块。例如：添加系统用户报错 可以定义为 [10010001] 前四位数为系统模块编号，后4位为错误代码 ,唯一 <br>
 *  并在CommonExceptionHandler.java类中设置异常的处理
 *  system异常 1001 <br>
 *  business异常 2001 <br>
 *  hibernate validate异常 8001 <br>
 * @author: tangm
 * @date: 2016年2月17日 
 * @version: 1.0
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="BizException 系统定义异常父类，包括基本的数据库操作异常！")
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -5875371379845226068L;

	/**
	 * 数据库操作,insert返回0
	 */
	public static final BizException DB_INSERT_RESULT_0 = new BizException(90040001, "数据库操作,insert返回0");

	/**
	 * 数据库操作,update返回0
	 */
	public static final BizException DB_UPDATE_RESULT_0 = new BizException(90040002, "数据库操作,update返回0");

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final BizException DB_SELECTONE_IS_NULL = new BizException(90040003, "数据库操作,selectOne返回null");

	/**
	 * 数据库操作,list返回null
	 */
	public static final BizException DB_LIST_IS_NULL = new BizException(90040004, "数据库操作,list返回null");

	/**
	 * Token 验证不通过
	 */
	public static final BizException TOKEN_IS_ILLICIT = new BizException(90040005, "Token 验证非法");
	/**
	 * 会话超时　获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
	 */
	public static final BizException SESSION_IS_OUT_TIME = new BizException(90040006, "会话超时");

	/**
	 * 获取序列出错
	 */
	public static final BizException DB_GET_SEQ_NEXT_VALUE_ERROR = new BizException(90040007, "获取序列出错");

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public BizException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BizException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public BizException newInstance(String msgFormat, Object... args) {
		return new BizException(this.code, msgFormat, args);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message) {
		super(message);
	}
}
