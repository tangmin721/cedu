/**
 * Create on 2012-2-2 下午3:21:40
 * 
 * Project: com.crsc.recorder.core
 * File: IEncrypter.java
 *
 * Copyright 2010 HuaChen , Inc. All rights reserved. 
 */
package com.yanxiu.ce.common.utils.code.encrypter;


/**
 * @author tangmin
 * @date 2016年3月10日
 */
public interface Encrypter {
	
	public final static int DES = 1;
	
	public final static int MD5 = 2;
	
	public final static int AES = 3;
	
	/**
	 * 字节码加密
	 * 
	 * @param byteIn
	 * @return
	 */
	public byte[] encrypt(byte[] byteIn,String... args) throws EncryptException;
	
	/**
	 * 字节码解密
	 * 
	 * @param byteIn
	 * @return
	 */
	public byte[] decrypt(byte[] byteIn,String... args) throws EncryptException;
}
