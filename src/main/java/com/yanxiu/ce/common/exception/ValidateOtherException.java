package com.yanxiu.ce.common.exception;

/**
 * @Description: hibernate other validate异常 8002
 * @author: tangm
 * @date: 2016年2月25日 
 * @version: 1.0
 */
public class ValidateOtherException extends BizException{

	private static final long serialVersionUID = 5687159283526389692L;

	/**
	 *  Insert 校验失败
	 */
	public static final int INSERT_FAILD =  80020001;
	/**
	 * Update 校验失败
	 */
	public static final int UPDATE_FAILD =  80020002;
	
	/**
	 * Delete 校验失败
	 */
	public static final int DELETE_FAILD =  80020003;
	
	/**
	 * Select 校验失败
	 */
	public static final int SELECT_FAILD =  80020004;
	
	
	public ValidateOtherException() {
	}

	public ValidateOtherException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public ValidateOtherException(int code, String msg) {
		super(code, msg);
	}
	
	/**
	 * 实例化异常
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	@Override
	public ValidateOtherException newInstance(String msgFormat, Object... args) {
		return new ValidateOtherException(this.code, msgFormat, args);
	}
}
