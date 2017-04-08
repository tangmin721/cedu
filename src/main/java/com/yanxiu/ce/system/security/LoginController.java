package com.yanxiu.ce.system.security;

import com.yanxiu.ce.common.exception.BizException;
import com.yanxiu.ce.common.yanxiuapi.PassportHelper;
import com.yanxiu.ce.system.entity.Config;
import com.yanxiu.ce.system.entity.MenuTreeNode;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.service.ConfigService;
import com.yanxiu.ce.system.service.MenuService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 系统登录
 * @author tangm
 */
@Controller
public class LoginController {
	
	protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private ConfigService configService;
	
	@Value(value="${teachehubei.appKey}")
	private String APP_KEY;
	
	
	/**
	 * 系统登录首页
	 * @return
	 */
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(Model model){
		Collection<MenuTreeNode> menus = menuService.loadTreeNodes(1l,ShiroUtils.getCurrentUser().getId());//根节点的id为1
		String sysName = "";
		Config config = configService.selectByTheKey("SYS_NAME");
		if(config!=null){
			sysName = config.getTheValue();
		}
		model.addAttribute("menus", menus);
		model.addAttribute("sysName", sysName);
		return "index";
	}
	
	/**
	 * 系统登录 首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		String sysName = "";
		Config config = configService.selectByTheKey("SYS_NAME");
		if(config!=null){
			sysName = config.getTheValue();
		}
		model.addAttribute("sysName", sysName);
		model.addAttribute("APP_KEY", APP_KEY);
		
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			return "login";
		} else {
			return "redirect:/index";
		}
	}
	
	
	
	/**
	 * 登录认证
	 * @param loginName
	 * @param loginPwd
	 * @param rememberMe
	 * @param captcha
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String authentication(
			@RequestParam("loginName") String loginName,
			@RequestParam("loginPwd") String loginPwd,
			@RequestParam(value = "rememberMe", required = false) Boolean rememberMe,
			@RequestParam(value = "captcha", required = false) String captcha,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			this.authorize(loginName, loginPwd,(rememberMe != null ? rememberMe : false), captcha);
			model.addAttribute("APP_KEY", APP_KEY);
		} catch (BizException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("loginName",
					loginName);
			model.addAttribute("APP_KEY", APP_KEY);
			return "login";
		}
		return "redirect:/index";
	}


	/**
	 * 认证方法
	 * @param loginName
	 * @param loginPwd
	 * @param rememberMe
	 * @param captcha
	 * @throws BizException
	 */
	private void authorize(String loginName, String loginPwd,
			boolean rememberMe, String captcha) throws BizException {
		try {
			userService.authenticate(loginName, loginPwd, rememberMe, captcha);
		} catch (UnknownAccountException ex) {
			throw new BizException("用户名或密码不正确!");
		} catch (IncorrectCredentialsException ice) {
			throw new BizException("用户名或密码不正确!");
		} catch (LockedAccountException lae) {
			throw new BizException("账户已被禁用!");
		} catch (AuthenticationException ae) {
			throw new BizException("认证错误!");
		}
	}
	
	
	/**
	 * 研修单点登录
	 * @return
	 */
	@RequestMapping(value="sso/{type}",method=RequestMethod.GET)
	public String sso(@PathVariable Integer type,HttpServletRequest request, HttpServletResponse response,
			Model model){
		try{
			String passport = PassportHelper.getPassport((HttpServletRequest)request);
			logger.info("==>sso passport:"+passport+"想要登录继教系统！");
			
			//passport = "4201050000000002";//服务器已获取
			if(StringUtils.isBlank(passport)){
				throw new BizException("");
			}
			
			User user = userService.selectByLoginName(passport);
			if(user!=null && user.getLoginName().endsWith(passport)){
				userService.authenticateSSO(passport);
				logger.info("==>sso passport:"+passport+"登录继教系统成功！");
				model.addAttribute("APP_KEY", APP_KEY);
				return "redirect:/index";
			}else{
				throw new BizException("找不到对应用户！请联系管理员！");
			}
		}catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("APP_KEY", APP_KEY);
			return "login";
		}
	}
}
