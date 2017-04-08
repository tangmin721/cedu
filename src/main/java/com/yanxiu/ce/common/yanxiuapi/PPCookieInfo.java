package com.yanxiu.ce.common.yanxiuapi;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PPCookieInfo {
	
	private static final Logger log = LoggerFactory.getLogger(PPCookieInfo.class);;
	
	private String passport;
	private String appMap;
	private long userId;
	private String nickName;
	

	public static PPCookieInfo parsePPCookieInfo(String info) {
		if (info==null) return null;
		
		try {
			info = new String(Base64.decodeBase64(info), "UTF-8");
//			System.out.println(info);
			String[] values = info.split(":");
			if (values==null) return null;
			
			HashMap<String,String> map = new HashMap<String, String>();
			parseInfo(info, map);
			
			String passport = EscapUnescape.unescape((String)map.get("pp"));
			String appMap = (String)map.get("appMap");
			long userid = Long.parseLong((String)map.get("userid"));
			String nickname = (String)map.get("nickname");
			return new PPCookieInfo(passport, appMap, userid, nickname);
		} catch (UnsupportedEncodingException e) {
			log.error("parese pp cookie info fail:", e);
		}
		return null;
	}
	
	private static void parseInfo(String ppinfo, HashMap<String, String> map) {
		if (map == null || ppinfo == null) return;
		
		int idx1 = ppinfo.indexOf(":");
		if (idx1==-1) return;
		String key = ppinfo.substring(0, idx1);
//		System.out.println(key);
		
		idx1++;
		int idx2 = ppinfo.indexOf(":", idx1);
//		System.out.println(idx1+","+idx2);
		String strLen = ppinfo.substring(idx1, idx2);
//		System.out.println(strLen);
		int length = Integer.parseInt(strLen);	
		
		idx2++;
		int idx3 = idx2+length;
		String value = ppinfo.substring(idx2,idx3);
//		System.out.println(value);
		map.put(key, value);
		
		idx3++;
		parseInfo(ppinfo.substring(idx3),map);
	}

	public PPCookieInfo(String passport, String appMap, long userId,
			String nickName) {
		super();
		this.passport = passport;
		this.appMap = appMap;
		this.userId = userId;
		this.nickName = nickName;
	}

	public PPCookieInfo() {
		super();
	}

	public String getPassport() {
		return passport;
	}


	public void setPassport(String passport) {
		this.passport = passport;
	}


	public String getAppMap() {
		return appMap;
	}


	public void setAppMap(String appMap) {
		this.appMap = appMap;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String info = "cHA6MzU6MTg2MDEyMDc4NzUlNDBtb2JpbGUlMmV5YW54aXUlMmVjb218YXBwTWFwOjE0OjEwMDF8MTAwMnwxMDA1fHVzZXJpZDo3OjY2ODY5NTJ8bmlja25hbWU6NzpzZGZkZmZmfA==";
		PPCookieInfo ppinfo = PPCookieInfo.parsePPCookieInfo(info);
		System.out.println(ppinfo);
		
		info = "cHA6MjM6WFkxMTk5NDAlNDB5YW54aXUlMmVjb218YXBwTWFwOjE5OjEwMDF8MTAwMnwxMDA1fDEwMDN8dXNlcmlkOjc6ODg2MjgxNHxuaWNrbmFtZToyNjrvvKLvvLkldTRlMDAldTc1MWYldTRlMDAldTRlMTZ8";
		ppinfo = PPCookieInfo.parsePPCookieInfo(info);
		System.out.println(ppinfo);
	}

}
