package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 用户操作
 * @author tangmin
 * @table sys_user_oplog
 * @date 2016年3月8日
 */
public class UserLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/** 用户编号 **/
	private String userNo;
	/** 操作员登录名 **/
	private String loginName;
	/** 操作状态(1:成功,0:失败) **/
	private Integer operateStatus;
	/** IP地址 **/
	private String ip;
	
	/**
	 * 日志描述（取注解的描述）
	 */
	private String  description;
	/** 操作内容 **/
	private String content;

	/**
	 * 操作类型：引用UserLogTypeEnum
	 */
	private Integer operType; 

	/**
	 * 操作类型：引用UserLogTypeEnum 
	 */
	public Integer getOperType() {
		return operType;
	}

	/**
	 * 操作类型：引用UserLogTypeEnum 
	 */
	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	/**
	 * 用户编号
	 * @return
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * 用户编号
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/**
	 * 操作员登录名
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 操作员登录名
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * IP地址
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * IP地址
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 操作内容
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 操作内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 操作状态(1:成功,0:失败)
	 * @return
	 */
	public Integer getOperateStatus() {
		return operateStatus;
	}

	/**
	 * 操作状态(1:成功,0:失败)
	 * @param operateStatus
	 */
	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
