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
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.entity.Faicon;
import com.yanxiu.ce.system.entity.FaiconQuery;
import com.yanxiu.ce.system.service.FaiconService;

/**
 * 图标管理
 * @author tangmin
 * @date 2016年3月4日
 */
@Controller
@RequestMapping("/system/faicon")
public class FaiconController {
	
	@Autowired
	private FaiconService faiconService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("faiconList")
	public String list(FaiconQuery query,Model model){
		Pagination<Faicon> page = faiconService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/faicon/faiconList";
	}
	
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("faiconForm")
	public String faiconForm(Model model){
		return "system/faicon/faiconForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="faiconForm/{id}",method = RequestMethod.POST)
	public String faiconForm(@PathVariable Long id,Model model){
		Faicon entity = faiconService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/faicon/faiconForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveFaicon",method = RequestMethod.POST)
	@ResponseBody
	public String saveFaicon(Faicon entity){
		AjaxCallback ok = AjaxCallback.OK(faiconService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteFaiconByIds",method = RequestMethod.POST)
	@ResponseBody
	public String deleteFaiconByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		faiconService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
