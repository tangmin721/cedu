package com.yanxiu.ce.common.yanxiuapi;

import javax.servlet.http.HttpServletRequest;

public class PassportHelper {
	public static final String PASSPORT_HEADER_NAME = "X-YanxiuPassport-Passport";
	public static final String APP_MAP_HEADER_NAME = "X-YanxiuPassport-AppMap";
	
	public static String  getPassport(HttpServletRequest request) {
		if (isInDevStage()) {
			return getPassportFromCookie(request);
		} else {
			return getPassportFromHeader(request);
		}
	}
	
	public static boolean isInDevStage() {
		return "dev".equals(System.getProperty("stage"));
	}
	
	public static String  getAppMap(HttpServletRequest request) {
		if (request==null) return null;
		return request.getHeader(APP_MAP_HEADER_NAME);
	}
	
	public static String  getPassportFromHeader(HttpServletRequest request) {
		if (request==null) return null;
		String passport = request.getHeader(PASSPORT_HEADER_NAME);
		if (passport!=null) passport = EscapUnescape.unescape(passport);
//		try {
//			if (passport!=null) passport = URLDecoder.decode(passport, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return passport;
	}
	
	public static String getPassportFromCookie(HttpServletRequest request) {
		PPCookie ppCookie = getPPCookie(request);
		if (ppCookie!=null) return ppCookie.getInfo().getPassport();
		return null;
	}

	public static PPCookie getPPCookie(HttpServletRequest request) {
		String passportCookieStr = CookieUtil.getCookieValue("passport", request); 
		if (passportCookieStr!=null) {
			return PPCookie.parsePPCookie(passportCookieStr);
		}
		return null;
	}
}