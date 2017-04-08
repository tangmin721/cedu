package com.yanxiu.ce.common.yanxiuapi;


public class PPCookie {

	private int version;
	private long createTime;
	private long expireTime;
	private PPCookieInfo info;
	private String rsa;	
		
	public static PPCookie parsePPCookie(String cookieString) {
		if (cookieString==null) return null;
		
		String[] values = cookieString.split("\\|");
		if (values == null || values.length != 5) return null;
		try {
			int version = Integer.parseInt(values[0]);
			long create = Long.parseLong(values[1]);
			long expire = Long.parseLong(values[2]);
			PPCookieInfo ppInfo = PPCookieInfo.parsePPCookieInfo(values[3]);
			String rsa = values[4];
			
			PPCookie pp = new PPCookie(version, create, expire, ppInfo, rsa);	
			return pp;
		} catch (Exception e) {
			return null;
		}
	}
		
	public PPCookie(int version, long createTime, long expireTime, PPCookieInfo info,
			String rsa) {
		super();
		this.createTime = createTime;
		this.expireTime = expireTime;
		this.info = info;
		this.rsa = rsa;
		this.version = version;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public PPCookieInfo getInfo() {
		return info;
	}

	public void setInfo(PPCookieInfo info) {
		this.info = info;
	}

	public String getRsa() {
		return rsa;
	}

	public void setRsa(String rsa) {
		this.rsa = rsa;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ppcookie = "1|1348802538|0|cHA6MzU6MTg2MDEyMDc4NzUlNDBtb2JpbGUlMmV5YW54aXUlMmVjb218YXBwTWFwOjE0OjEwMDF8MTAwMnwxMDA1fHVzZXJpZDo3OjY2ODY5NTJ8bmlja25hbWU6NzpzZGZkZmZmfA==|HotQ131Sj97K42T8FGLLMj0CCZwYDyA8v4kJdtT0OE4JPgPHYoGR7c5+tM/cJkyes3PgKMFHb6Q+MT1SArE1TInYJxVx7n5mRK2P5vn8+0CeKkXfxlCA1XkMzEDDbM4BOLVg2YAsk/NL3p+RFAPrVFKpxCV+OZ0AgABz7Rw5DMAmpoH7wEnsCJQhOV4CB8RvCe4AzGKzjaurqXTHGH9xR7ebAnXIopDW/11HYtOnV1Ys07rZvzvSTniuklDjAYD4cCL0qPWa/VrJsLRG0UYnLIdxBeA8X8Y9RIwtg9hU7WBWJA+Mhx8TI93FGkxWfTJPNiedN4j7tCY5u+qpGpUIWQ==";
		ppcookie = "1|1351687216|0|cHA6MjM6WFkxMTk5NDAlNDB5YW54aXUlMmVjb218YXBwTWFwOjE5OjEwMDF8MTAwMnwxMDA1fDEwMDN8dXNlcmlkOjc6ODg2MjgxNHxuaWNrbmFtZToyNjrvvKLvvLkldTRlMDAldTc1MWYldTRlMDAldTRlMTZ8|lJ46cStfMMR84WL8UOJ0oVcsxSDRbpA6Xy0S85ovQhk4+YzgF/mjmxpmssopeFGJZZNTy6EkGVlKoHnJ1ifuS/IJ5eD0/hVILjMIq9b8/zfk6fdIb9bCbro6a1BWI/oAiNwCSBOK2oxGAT33EX+UIq402SqnCUcjfLEytsdabF5Ldtyegeq+GpX9aKE9SKzzhnDgU7SlynoFeEiFBgZPlIfpce/aTQDTL2utSjqGtj/CsaFQatdsfzrPAa3QOQY98wSOKDJnZxhnUeOll5Cp4UDZQG2z7KO/M7xmvHXbR5tQRSLie4379iL4yyz9ycZwHS/BHVh4W1ps2VfIzCB5Lg==";

		PPCookie ppc = PPCookie.parsePPCookie(ppcookie);
		System.out.println(ppc);
	}

}
