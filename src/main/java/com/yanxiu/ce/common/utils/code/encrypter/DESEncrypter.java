/**
 * Create on 2012-2-2 下午3:25:05
 * 
 * Project: com.crsc.recorder.core
 * File: DESEncrypter.java
 *
 * Copyright 2010 HuaChen , Inc. All rights reserved. 
 */
package com.yanxiu.ce.common.utils.code.encrypter;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密
 * @author tangmin
 * @date 2016年3月10日
 */
public class DESEncrypter implements Encrypter {

	@Override
	public byte[] encrypt(byte[] byteIn,String... args) throws EncryptException{
		byte[] encryptedData = null;
		if(args == null || args.length == 0){
			throw new EncryptException("No key for des encrypt.");
		}
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(args[0].getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 正式执行加密操作
			encryptedData = cipher.doFinal(byteIn);
		} catch (Exception e) {
			throw new EncryptException(e);
		}
		return encryptedData;
	}

	@Override
	public byte[] decrypt(byte[] byteIn,String... args) throws EncryptException{
		byte[] decryptedData = null;
		if(args == null || args.length == 0){
			throw new EncryptException("No key for des decrypt.");
		}
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(args[0].getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 正式执行解密操作
			decryptedData = cipher.doFinal(byteIn);
		}catch (Exception e) {
			throw new EncryptException(e);
		}
		return decryptedData;
	}
}
