package com.yanxiu.ce.common.utils.code;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密
 */
public class AES {
	    /**
	     * 加密
	     * 
	     * @param content
	     *            待加密内容
	     * @param key
	     *            加密的密钥
	     * @return
	     */
	    public static String encrypt(String content, String key) {
	        try {
	            KeyGenerator kgen = KeyGenerator.getInstance("AES");

				SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
				secureRandom.setSeed(key.getBytes());

	            kgen.init(128, secureRandom);
	            SecretKey secretKey = kgen.generateKey();
	            byte[] enCodeFormat = secretKey.getEncoded();
	            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
	            Cipher cipher = Cipher.getInstance("AES");
	            byte[] byteContent = content.getBytes("utf-8");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
	            byte[] byteRresult = cipher.doFinal(byteContent);
	            StringBuffer sb = new StringBuffer();
	            for (int i = 0; i < byteRresult.length; i++) {
	                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
	                if (hex.length() == 1) {
	                    hex = '0' + hex;
	                }
	                sb.append(hex.toUpperCase());
	            }
	            return sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	    /**
	     * 解密
	     * 
	     * @param content
	     *            待解密内容
	     * @param key
	     *            解密的密钥
	     * @return
	     */
	    public static String decrypt(String content, String key) {
	        if (content.length() < 1)
	            return null;
	        byte[] byteRresult = new byte[content.length() / 2];
	        for (int i = 0; i < content.length() / 2; i++) {
	            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
	            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
	            byteRresult[i] = (byte) (high * 16 + low);
	        }
	        try {
	            KeyGenerator kgen = KeyGenerator.getInstance("AES");

				SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
				secureRandom.setSeed(key.getBytes());

	            kgen.init(128, secureRandom);
	            SecretKey secretKey = kgen.generateKey();
	            byte[] enCodeFormat = secretKey.getEncoded();
	            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
	            Cipher cipher = Cipher.getInstance("AES");
	            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
	            byte[] result = cipher.doFinal(byteRresult);
	            return new String(result);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    public static void main(String[] args) {
	       // String password = PassWordCreate.createPassWord(10);
	        String password = "123456";
	        String aesKey = "9BFA07432C73547D620FD46AF0C846E6";
	        //9BFA07432C73547D620FD46AF0C846E6
	        //1C3749E0A5E6321B181F3FE21AB9635A
	        System.out.println("密　钥：" + aesKey);
	        System.out.println("加密前：" + password);
	        // 加密
	        String encryptResult = encrypt(password, aesKey);
	        System.out.println("加密后：" + encryptResult);
	        // 解密
	        String decryptResult = decrypt(encryptResult, aesKey);
	        System.out.println("解密后：" + decryptResult);
	    }
	 
}
