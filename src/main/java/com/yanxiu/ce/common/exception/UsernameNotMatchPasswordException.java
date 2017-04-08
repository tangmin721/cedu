package com.yanxiu.ce.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 用户密码不匹配异常  定义为403错误
 * @author tangmin
 * @date 2016年2月22日
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="用户名密码不匹配")
public class UsernameNotMatchPasswordException extends BizException {
	private static final long serialVersionUID = 4721951029607713496L;

	public UsernameNotMatchPasswordException() {
	}

	public UsernameNotMatchPasswordException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public UsernameNotMatchPasswordException(int code, String msg) {
		super(code, msg);
	}
	
	/**
	 * 实例化异常
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	@Override
	public UsernameNotMatchPasswordException newInstance(String msgFormat, Object... args) {
		return new UsernameNotMatchPasswordException(this.code, msgFormat, args);
	}

}
