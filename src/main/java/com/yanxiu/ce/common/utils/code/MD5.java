package com.yanxiu.ce.common.utils.code;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yanxiu.ce.common.utils.code.encrypter.Digests;

/**
 * MD5加密
 * @author tangmin
 * @date 2016年8月8日
 */
public class MD5 {

	private static final Logger logger = LoggerFactory.getLogger(MD5.class);

	public static String encode(String input) {
		logger.debug(input);
		String ret = null;
		try {
			ret = Encodes.encodeHex(Digests.md5(input.getBytes("UTF-8"), null,
					1));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void main(String[] args) {
		String str = "123456";
		System.out.println(encode(str));
	}
}
