
package com.yanxiu.ce.common.utils.code.encrypter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密器
 * @author tangmin
 * @date 2016年3月10日
 */
public class MD5Encrypter implements Encrypter{

	@Override
	public byte[] encrypt(byte[] in,String... keys) throws EncryptException{
		byte[] result = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(in);
			result = digest.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException(e);
		}
		return result;
	}

	@Override
	public byte[] decrypt(byte[] in,String... keys)throws EncryptException {
		throw new EncryptException("Unsupport decrypt method");
	}

	
}
