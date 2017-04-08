package com.yanxiu.ce.common.utils.code.encrypter;

/**
 * 加密异常
 * @author tangmin
 * @date 2016年3月10日
 */
public class EncryptException extends RuntimeException{

	private static final long serialVersionUID = -1338362813474012160L;

	public EncryptException() {
		super();
	}

	public EncryptException(Throwable cause) {
		super(cause);
	}

	public EncryptException(String message) {
		super(message);
	}

	
}
