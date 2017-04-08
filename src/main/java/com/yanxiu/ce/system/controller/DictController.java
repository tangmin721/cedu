package com.yanxiu.ce.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 字典页面集成
 * @author tangmin
 * @date 2016年3月4日
 */
@Controller
@RequestMapping("/system/dict")
public class DictController {
	
	@Autowired
	DictCatlogService dictCatlogService;
	
	@RequiresPermissions("DictCatlog:read")
	@RequestMapping("dict")
	public String list(){
		return "system/dict/dict";
	}

	/**
	 * 清除缓存
	 * @return
	 */
	@RequestMapping("removeDictCache")
	@ResponseBody
	public String removeDictCache(){
		dictCatlogService.removeAllCache();;
		AjaxCallback ok = AjaxCallback.OK("清除缓存成功！");
		return JSON.toJSONString(ok);
	}
}
