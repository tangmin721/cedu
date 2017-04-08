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
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.entity.DictCatlog;
import com.yanxiu.ce.system.entity.DictCatlogQuery;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

@Controller
@RequestMapping("/system/dict/catlog")
public class DictCatlogController {
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("dictCatlogList")
	@RequiresPermissions("DictCatlog:read")
	public String list(DictCatlogQuery query,Model model){
		query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxx", "asc"));
		Pagination<DictCatlog> page = dictCatlogService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/dict/catlog/dictCatlogList";
	}
	
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("dictCatlogForm")
	@RequiresPermissions("DictCatlog:read")
	public String dictCatlogForm(Model model){
		DictCatlog entity = new DictCatlog();
		entity.setSeq(dictCatlogService.selectMaxSeq()+1);
		model.addAttribute("entity", entity);
		return "system/dict/catlog/dictCatlogForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="dictCatlogForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("DictCatlog:read")
	public String dictCatlogForm(@PathVariable Long id,Model model){
		DictCatlog entity = dictCatlogService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/dict/catlog/dictCatlogForm";
	}
	
	/**
	 * 保存方法(多权限任选一or 必须具有所有权限and)
	 * @return
	 */
	@RequestMapping(value="saveDictCatlog",method = RequestMethod.POST)
	@RequiresPermissions(value={"DictCatlog:update","DictCatlog:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存字典目录")
	public String saveDictCatlog(DictCatlog entity){
		AjaxCallback ok = AjaxCallback.OK(dictCatlogService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteDictCatlogByIds",method = RequestMethod.POST)
	@RequiresPermissions("DictCatlog:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除字典目录")
	public String deleteDictCatlogByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		AjaxCallback ok = AjaxCallback.OK(dictCatlogService.deleteCatlogByIds(idList));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 根据catlog的code获取item下拉框
	 */
	@RequestMapping("getSelectItems")
	@RequiresPermissions("DictCatlog:read")
	@ResponseBody
	public String getSelectItems(@RequestParam(value="code",required=true) String code){
		List<DictItem> selectItems = dictCatlogService.getSelectItems(code);
		return JSON.toJSONString(selectItems);
	}
	
	/**
	 * 同步缓存服务
	 * @param code
	 * @return
	 */
	@RequestMapping("syncSelectItems")
	@RequiresPermissions("DictCatlog:read")
	@ResponseBody
	public String syncSelectItems(@RequestParam(value="code",required=true) String code){
//		dictCatlogService.syncSelectItems(code);
		AjaxCallback ok = AjaxCallback.OK("同步成功！");
		return JSON.toJSONString(ok);
	}
	

}
