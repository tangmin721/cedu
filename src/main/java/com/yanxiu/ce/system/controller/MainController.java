package com.yanxiu.ce.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 我的主页
 * @author tangm
 */
@Controller
public class MainController {
	
	/**
	 * 我的主页
	 * @return
	 */
	@RequestMapping("main")
	public String main(){
		return "system/main";
	}
	
	/**
	 * 建设中
	 * @return
	 */
	@RequestMapping("building")
	public String building(){
		return "system/building";
	}
	
	/**
	 * 没有权限
	 * @return
	 */
	@RequestMapping("refuse")
	public String refuse(){
		return "system/refuse";
	}

	/**
	 * 建设中
	 * @return
	 */
	@RequestMapping("druidindex")
	public String druidindex(){
		return "system/druid";
	}

}
