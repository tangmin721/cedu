package com.yanxiu.ce.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 省市县集成页面
 * @author tangmin
 * @date 2016年3月8日
 */
@Controller
@RequestMapping("/system/area")
public class AreaController {
	@RequestMapping("area")
	public String list(){
		return "system/area/area";
	}
}


