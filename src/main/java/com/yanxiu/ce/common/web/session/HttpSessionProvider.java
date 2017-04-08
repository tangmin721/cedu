package com.yanxiu.ce.common.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 本地Session，上线的时候替换为redis session
 * @author lx
 *
 */
public class HttpSessionProvider implements SessionProvider{

	public void setAttribute(HttpServletRequest request,HttpServletResponse response, String name,
			Serializable value) {
		HttpSession session = request.getSession();//true    Cookie JSESSIONID
		session.setAttribute(name, value);
	}

	public Serializable getAttribute(HttpServletRequest request,HttpServletResponse response, String name) {
		HttpSession session = request.getSession(false);
		if(session != null){
			return (Serializable) session.getAttribute(name);
		}
		return null;
	}

	public void logout(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
		}
		
	}

	public String getSessionId(HttpServletRequest request,HttpServletResponse response) {
		//request.getRequestedSessionId();  //Http://localhost:8080/html/sfsf.shtml?JESSIONID=ewrqwrq234123412
		return request.getSession().getId();
	}

}
