package com.yanxiu.ce.common.core.dto;

/**
 * @Description: BJUI ajax 返回回调信息
 * @Table: TODO
 * @author: tangm
 * @date: 2016年2月19日 
 * @version: 1.0
 */
public class AjaxCallback {
	
	public Object data;
	
	public final static int OK = 200;
	public final static int ERROR = 300;
	public final static int TIMEOUT = 301;
	public final static int CHECK_FAILD = 350;
	
	/**
	 * 必选。状态码(ok = 200, error = 300, timeout = 301)，可以在BJUI.init时配置三个参数的默认值。
	 */
	private int statusCode;
	
	/**
	 * 可选。信息内容。
	 */
	private String message;
	
	/**
	 * 可选。待刷新navtab id，多个id以英文逗号分隔开，当前的navtab id不需要填写，填写后可能会导致当前navtab重复刷新。
	 */
	private String tabid;
	
	/**
	 * 可选。待刷新dialog id，多个id以英文逗号分隔开，请不要填写当前的dialog id，要控制刷新当前dialog，请设置dialog中表单的reload参数。
	 */
	private String dialogid;
	
	/**
	 * 可选。待刷新div id，多个id以英文逗号分隔开，请不要填写当前的div id，要控制刷新当前div，请设置该div中表单的reload参数。
	 */
	private String divid;
	
	/**
	 * 可选。是否关闭当前窗口(navtab或dialog)。
	 */
	private Boolean closeCurrent;
	
	/**
	 * 可选。跳转到某个url。
	 */
	private String forward;
	
	/**
	 * 可选。跳转url前的确认提示信息。
	 */
	private String forwardConfirm;
	
	/**
	 * 不能通过new创建没有status的对象
	 */
	private AjaxCallback(){
		
	}
	
	/**
	 * 返回Ajax操作成功的回调信息
	 * @param msg
	 * @return 操作成功
	 */
	public static AjaxCallback OK(String msg){
		AjaxCallback acb = new AjaxCallback();
		acb.setMessage(msg);
		acb.setStatusCode(OK);
		return acb;
	}
	
	/**
	 * 返回Ajax操作失败的回调信息
	 * @param msg
	 * @return 操作失败
	 */
	public static AjaxCallback ERROR(String msg){
		AjaxCallback acb = new AjaxCallback();
		acb.setMessage(msg);
		acb.setStatusCode(ERROR);
		return acb;
	}
	
	/**
	 * 返回Ajax操作超时的回调信息
	 * @param msg
	 * @return 操作超时
	 */
	public static AjaxCallback TIMEOUT(String msg){
		AjaxCallback acb = new AjaxCallback();
		acb.setMessage(msg);
		acb.setStatusCode(TIMEOUT);
		return acb;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTabid() {
		return tabid;
	}

	public void setTabid(String tabid) {
		this.tabid = tabid;
	}

	public String getDialogid() {
		return dialogid;
	}

	public void setDialogid(String dialogid) {
		this.dialogid = dialogid;
	}

	public String getDivid() {
		return divid;
	}

	public void setDivid(String divid) {
		this.divid = divid;
	}

	public Boolean getCloseCurrent() {
		return closeCurrent;
	}

	public void setCloseCurrent(Boolean closeCurrent) {
		this.closeCurrent = closeCurrent;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getForwardConfirm() {
		return forwardConfirm;
	}

	public void setForwardConfirm(String forwardConfirm) {
		this.forwardConfirm = forwardConfirm;
	}
	
}
