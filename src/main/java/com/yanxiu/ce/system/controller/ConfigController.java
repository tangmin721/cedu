package com.yanxiu.ce.system.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.entity.Config;
import com.yanxiu.ce.system.entity.ConfigQuery;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.ConfigService;

/**
 * 系统配置管理
 * @author tangmin
 * @date 2016-04-12 17:40:16
 */
@Controller
@RequestMapping("/system/config")
public class ConfigController extends BaseController{
	
	@Autowired
	private ConfigService configService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("configList")
	@RequiresPermissions("Config:read")
	public String list(ConfigQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<Config> page = configService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/config/configList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("configForm")
	@RequiresPermissions("Config:create")
	public String configForm(Model model){
		Config entity = new Config();
		model.addAttribute("entity", entity);
		return "system/config/configForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="configForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Config:update")
	public String configForm(@PathVariable Long id,Model model){
		Config entity = configService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/config/configForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveConfig",method = RequestMethod.POST)
	@RequiresPermissions(value={"Config:update","Config:create"},logical=Logical.AND)
	@ResponseBody
	public String saveConfig(Config entity){
		User user = ShiroUtils.getCurrentUser();
		entity.setUserId(user.getId());
		
		AjaxCallback ok = AjaxCallback.OK(configService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteConfigByIds",method = RequestMethod.POST)
	@RequiresPermissions("Config:delete")
	@ResponseBody
	public String deleteConfigByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		configService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
