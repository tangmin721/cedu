package com.yanxiu.ce.common.utils.code.encrypter;

/**
 * 加密器工厂
 * @author tangmin
 * @date 2016年3月10日
 */
public class EncrypterFactory {

	/**
	 * 取得加密器
	 * 
	 * @return
	 */
	public final static Encrypter getEncrypter(int algorithm) {
		Encrypter encrypter = null;
		if (algorithm == Encrypter.DES) {
			encrypter = new DESEncrypter();
		} else if (algorithm == Encrypter.MD5) {
			encrypter = new MD5Encrypter();
		}else if (algorithm == Encrypter.AES) {
			encrypter = new AESEncrypter();
		}
		return encrypter;
	}
}
