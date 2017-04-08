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
import com.yanxiu.ce.system.entity.Org;
import com.yanxiu.ce.system.entity.OrgQuery;
import com.yanxiu.ce.system.service.OrgService;

/**
 * 组织机构管理
 * @author tangmin
 * @date 2016-03-17 14:21:50
 */
@Controller
@RequestMapping("/system/org")
public class OrgController {
	
	@Autowired
	private OrgService orgService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("orgList")
	public String list(OrgQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<Org> page = orgService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/org/orgList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("orgForm")
	public String orgForm(Model model){
		Org entity = new Org();
		entity.setSeq(orgService.selectMaxSeq()+1);
		model.addAttribute("entity", entity);
		return "system/org/orgForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="orgForm/{id}",method = RequestMethod.POST)
	public String orgForm(@PathVariable Long id,Model model){
		Org entity = orgService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/org/orgForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveOrg",method = RequestMethod.POST)
	@ResponseBody
	public String saveOrg(Org entity){
		AjaxCallback ok = AjaxCallback.OK(orgService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteOrgByIds",method = RequestMethod.POST)
	@ResponseBody
	public String deleteOrgByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		orgService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
