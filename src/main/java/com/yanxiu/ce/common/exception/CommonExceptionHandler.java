package com.yanxiu.ce.common.exception;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.StoppedSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.validate.ValidatorBean;

/**
 * @Description: 统一异常处理
 * @author: tangm
 * @date: 2016年2月20日
 * @version: 1.0
 */
@ControllerAdvice
public class CommonExceptionHandler {
	
	@ExceptionHandler({ RuntimeException.class })
	public ModelAndView handlerRtExp(Exception ex) {
		ModelAndView mv = new ModelAndView("error/ajax-error");
		AjaxCallback acb = AjaxCallback.ERROR("<blockquote class='point'><h4>后台业务异常错误信息</h4></blockquote>"+ex.getMessage());//上线的时候屏蔽错误细节
		mv.addObject("exception", JSON.toJSONString(acb));
		return mv;
	}

	/**
	 * 自定义的业务异常父类
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ BizException.class })
	public ModelAndView handlerBizExp(Exception ex) {
		ModelAndView mv = new ModelAndView("error/ajax-error");
		AjaxCallback acb = AjaxCallback.ERROR("<blockquote class='point'><h4>后台业务异常错误信息</h4></blockquote>"+ex.getMessage());
		mv.addObject("exception", JSON.toJSONString(acb));
		return mv;
	}

	/**
	 * 子类自定义异常     精确匹配，异常优先级最高
	 * @param ex
	 * @param response 
	 * @return
	 * @throws IOException 
	 */
	@ExceptionHandler({ ValidateException.class })
	public ModelAndView handlerValidateExp(HttpServletRequest request,HttpServletResponse response,Exception ex) throws IOException {
		ModelAndView mv = new ModelAndView("error/ajax-error");
		
		String msg = "<blockquote class='point'><h4>后台校验错误信息</h4></blockquote><table cellspacing='10'><tr><th>字段</th><th>原因</th></tr>";
		List<ValidatorBean> vbs = JSON.parseArray(ex.getMessage(), ValidatorBean.class);
		for(ValidatorBean vb:vbs){
			msg =msg +"<tr><td>" + vb.getFiled()+":</td><td>"+vb.getErrorMsg()+"</td></tr>";
		}
		msg +="</table>";
		AjaxCallback acb = AjaxCallback.ERROR(msg);
		mv.addObject("exception", JSON.toJSONString(acb));
		return mv;
	}
	
	/**
	 * 子类自定义异常     用户名名称不匹配   精确匹配，异常优先级最高
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ UsernameNotMatchPasswordException.class })
	public ModelAndView handlerUserPasswordNotMatchExp(Exception ex) {
		ModelAndView mv = new ModelAndView("error/ajax-error");
		AjaxCallback acb = AjaxCallback.ERROR("<blockquote class='point'><h4>后台业务异常错误信息</h4></blockquote>"+ex.getMessage());
		mv.addObject("exception", JSON.toJSONString(acb));
		return mv;
	}
	
	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ ValidateOtherException.class })
	public ModelAndView handlerValidateOtherExp(Exception ex) {
		ModelAndView mv = new ModelAndView("error/ajax-error");
		AjaxCallback acb = AjaxCallback.ERROR("<blockquote class='point'><h4>后台校验异常错误信息</h4></blockquote>"+ex.getMessage());
		mv.addObject("exception", JSON.toJSONString(acb));
		return mv;
	}
	
	//org.apache.shiro.session.UnknownSessionException
	//org.apache.shiro.session.StoppedSessionException
	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ UnknownSessionException.class,StoppedSessionException.class })
	public ModelAndView handlerSessionExp(Exception ex) {
		ModelAndView mv = new ModelAndView("login");
//		AjaxCallback acb = AjaxCallback.ERROR("<blockquote class='point'><h4>后台Session异常错误信息</h4></blockquote>"+ex.getMessage());
		mv.addObject("error", JSON.toJSONString("会话已过期，请重新登录！"));
		return mv;
	}
}
