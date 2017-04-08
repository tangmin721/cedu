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
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.entity.Menu;
import com.yanxiu.ce.system.entity.MenuQuery;
import com.yanxiu.ce.system.service.MenuService;

/**
 * 菜单管理
 * @author tangmin
 * @date 2016年3月4日
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController{
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 菜单集成页面
	 * @return
	 */
	@RequestMapping("menu")
	public String menu(){
		return "system/menu/menu";
	}
	
	/**
	 * 左侧tree
	 * @return
	 */
	@RequestMapping("tree")
	public String tree(Model model){
		MenuQuery query = new MenuQuery();
		query.setOrderField("seq");
		query.setCreateTree(true);
		model.addAttribute("menuTree", menuService.selectList(query));
		return "system/menu/tree";
	}
	
	/**
	 * treelookup
	 * @return
	 */
	@RequestMapping("treelookup")
	public String treelookup(Model model){
		MenuQuery query = new MenuQuery();
		query.setOrderField("seq");
		query.setCreateTree(true);
		model.addAttribute("menuTreelookup", menuService.selectList(query));
		return "system/menu/treelookup";
	}
	
	/**
	 * treeFormLookup
	 * @param model
	 * @return
	 */
	@RequestMapping("treeFormLookup")
	public String treeFormLookup(Model model){
		MenuQuery query = new MenuQuery();
		query.setOrderField("seq");
		query.setCreateTree(true);
		model.addAttribute("menuTreelookup", menuService.selectList(query));
		return "system/menu/treeFormLookup";
	}
	
	
	/**
	 * 菜单列表
	 * @return
	 */
	@RequestMapping("menuList")
	public String list(MenuQuery query,Model model){
		query.setOrderField("seq");
		Pagination<Menu> page = menuService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/menu/menuList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("menuForm")
	public String menuForm2(@RequestParam(value="pid",required=false) Long pid,Model model){
		Menu entity = new Menu();
		entity.setPid(pid);
		entity.setSeq(menuService.selectMaxSeq(pid)+1);
		
		String pname = "根节点";
		if(entity!=null&&entity.getPid()!=0){
			pname = menuService.selectById(entity.getPid()).getName();
		}
		
		model.addAttribute("pname", pname);
		model.addAttribute("entity", entity);
		return "system/menu/menuForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="menuForm/{id}",method = RequestMethod.POST)
	public String menuForm(@PathVariable Long id,Model model){
		Menu entity = menuService.selectById(id);
		String pname = menuService.selectById(entity.getPid()).getName();
		model.addAttribute("entity", entity);
		model.addAttribute("pname", pname);
		return "system/menu/menuForm";
	}
	
	/**
	 * 进入编辑treeForm表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="menuTreeForm",method = RequestMethod.POST)
	public String menuTreeForm(@RequestParam(value="id",required=false) Long id,Model model){
		Menu entity = menuService.selectById(id);
		String pname = "";
		if(entity!=null&&entity.getPid()!=0){
			pname = menuService.selectById(entity.getPid()).getName();
		}
		model.addAttribute("entity", entity);
		model.addAttribute("pname", pname);
		return "system/menu/menuTreeForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveMenu",method = RequestMethod.POST)
	@ResponseBody
	public String saveMenu(Menu entity){
		AjaxCallback ok = AjaxCallback.OK(menuService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteMenuByIds",method = RequestMethod.POST)
	@ResponseBody
	public String deleteMenuByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		menuService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 清除缓存
	 * @return
	 */
	@RequestMapping("removeMenuCache")
	@ResponseBody
	public String removeMenuCache(){
		menuService.removeMenuCache();
		AjaxCallback ok = AjaxCallback.OK("清除缓存成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 获取权限树
	 * @return
	 */
	@RequestMapping("getPermTreeGlobal")
	@ResponseBody
	public String getPermTreeGlobal(){
		AjaxCallback ok = AjaxCallback.OK("获取目录树成功！");
		ok.setData(menuService.getPermTreeGlobal(1l));
		return JSON.toJSONString(ok);
	}
}
