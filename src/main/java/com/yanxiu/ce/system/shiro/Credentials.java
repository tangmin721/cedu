package com.yanxiu.ce.system.shiro;

/**
 * 认证后产生的证书
 * @author tangmin
 * @date 2016年3月10日
 */
public class Credentials {

	//是否授予
	private boolean award;
	//可用用户数量
	private int userCount;
	//失败信息
	private String failureMessage;
	//不可用标识
	private boolean enable = true;
	
	public static Credentials disable(){
		Credentials credentials = new Credentials();
		credentials.award = true;
		credentials.enable = false;
		return credentials;
	}
	
	public static Credentials failure(String msg){
		Credentials credentials = new Credentials();
		credentials.award = false;
		credentials.enable = true;
		return credentials;
	}
	
	public static Credentials award(int userCount){
		Credentials credentials = new Credentials();
		credentials.award = true;
		credentials.enable = true;
		credentials.userCount = userCount;
		return credentials;
	}
	
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public boolean isAward() {
		return award;
	}

	public void setAward(boolean award) {
		this.award = award;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
