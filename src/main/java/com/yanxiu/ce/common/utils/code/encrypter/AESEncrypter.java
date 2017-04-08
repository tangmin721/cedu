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
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES密码器
 * @author tangmin
 * @date 2016年3月10日
 */
public class AESEncrypter implements Encrypter {

	@Override
	public byte[] encrypt(byte[] byteIn, String... args)
			throws EncryptException {
		byte[] encryptedData = null;
		if (args == null || args.length == 0) {
			throw new EncryptException("No key for des encrypt.");
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(args[0].getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			encryptedData = cipher.doFinal(byteIn);
		} catch (Exception e) {
			throw new EncryptException(e);
		}
		return encryptedData;
	}

	@Override
	public byte[] decrypt(byte[] byteIn, String... args)
			throws EncryptException {
		byte[] decryptedData = null;
		if (args == null || args.length == 0) {
			throw new EncryptException("No key for des decrypt.");
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(args[0].getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			decryptedData = cipher.doFinal(byteIn);
		} catch (Exception e) {
			throw new EncryptException(e);
		}
		return decryptedData;
	}
}
