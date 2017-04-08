package com.yanxiu.ce.system.controller;

import java.util.List;

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
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.PermissionQuery;
import com.yanxiu.ce.system.service.PermissionService;

/**
 * 权限管理
 * @author tangmin
 * @date 2016-03-23 16:53:39
 */
@Controller
@RequestMapping("/system/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("permissionList/{menuId}")
	public String list(@PathVariable Long menuId,PermissionQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<Permission> page = permissionService.selectListPagination(query);
		query.setMenuId(menuId.toString());
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("menuId", menuId);
		return "system/permission/permissionList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("permissionForm")
	public String permissionForm(@RequestParam(value="menuId",required=true) Long menuId,Model model){
		Permission entity = new Permission();
		entity.setMenuId(menuId);
		model.addAttribute("entity", entity);
		return "system/permission/permissionForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="permissionForm/{id}",method = RequestMethod.POST)
	public String permissionForm(@RequestParam(value="menuId",required=true) Long menuId,@PathVariable Long id,Model model){
		Permission entity = permissionService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/permission/permissionForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="savePermission",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "保存权限")
	public String savePermission(Permission entity){
		AjaxCallback ok = AjaxCallback.OK(permissionService.saveOrUpdate(entity));
		//ok.setDialogid("menu_permission_form_dialog");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletePermissionByIds",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除权限")
	public String deletePermissionByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		permissionService.deletePermByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
