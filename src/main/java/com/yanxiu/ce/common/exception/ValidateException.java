package com.yanxiu.ce.common.exception;

/**
 * @Description: hibernate validate异常 8001
 * @author: tangm
 * @date: 2016年2月18日 
 * @version: 1.0
 */
public class ValidateException extends BizException{

	private static final long serialVersionUID = 5687159283526389692L;

	/**
	 *  Insert 校验失败
	 */
	public static final int INSERT_FAILD =  80010001;
	/**
	 * Update 校验失败
	 */
	public static final int UPDATE_FAILD =  80010002;
	
	/**
	 * Delete 校验失败
	 */
	public static final int DELETE_FAILD =  80010003;
	
	/**
	 * Select 校验失败
	 */
	public static final int SELECT_FAILD =  80010004;
	
	
	public ValidateException() {
	}

	public ValidateException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public ValidateException(int code, String msg) {
		super(code, msg);
	}
	
	/**
	 * 实例化异常
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	@Override
	public ValidateException newInstance(String msgFormat, Object... args) {
		return new ValidateException(this.code, msgFormat, args);
	}
}
