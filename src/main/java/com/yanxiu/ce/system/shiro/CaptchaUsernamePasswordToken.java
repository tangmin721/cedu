package com.yanxiu.ce.system.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * 验证码token
 * @author tangmin
 * @date 2016年3月10日
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = -3178260335127476542L;

	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken() {
		super();
	}

	public CaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}
}
