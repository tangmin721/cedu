package com.yanxiu.ce.system.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.dto.TreeCheckedIdDto;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.RoleQuery;
import com.yanxiu.ce.system.service.MenuService;
import com.yanxiu.ce.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理、权限设置
 * @author tangmin
 * @date 2016年3月24日
 */
@Controller
@RequestMapping("/system/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("roleList")
	public String list(RoleQuery query,Model model){
		query.setOrderField("seq");
		Pagination<Role> page = roleService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/role/roleList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("roleForm")
	public String roleForm(Model model){
		Role entity = new Role();
		entity.setSeq(roleService.selectMaxSeq()+1);
		model.addAttribute("entity", entity);
		return "system/role/roleForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="roleForm/{id}",method = RequestMethod.POST)
	public String roleForm(@PathVariable Long id,Model model){
		Role entity = roleService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/role/roleForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveRole",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "保存角色")
	public String saveRole(Role entity){
		if(entity.getActive()==null){
			entity.setActive(false);
		}
		AjaxCallback ok = AjaxCallback.OK(roleService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteRoleByIds",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除角色")
	public String deleteRoleByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		roleService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 进入角色权限分配的form
	 */
	@RequestMapping("rolePermTree/{id}")
	public String rolePermTree(@PathVariable Long id,Model model){
		model.addAttribute("ids", JSON.toJSONString(roleService.getTreeCheckedIds(id)).replaceAll("\"", "\'"));
		model.addAttribute("roleId",id);
		model.addAttribute("permTree", JSON.toJSONString(menuService.getPermTreeGlobal(id)));
		return  "system/role/rolePermTree";
	}

	/**
	 * 保存角色权限
	 * @return
	 */
	@RequestMapping(value="saveRolePerm",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "保存角色权限")
	public String saveRolePerm(@RequestParam(value="roleId",required=true) Long roleId,@RequestParam(value="ids",required=false) String ids){
		System.out.println(":::::ids:"+ids);
		List<TreeCheckedIdDto> checkedIds =  JSON.parseArray(ids,TreeCheckedIdDto.class);
		AjaxCallback ok = AjaxCallback.OK(roleService.saveRoleMenuPerm(roleId, checkedIds));
		System.out.println(":::::listids:"+JSON.toJSONString(checkedIds));
		return JSON.toJSONString(ok);
	}
}
