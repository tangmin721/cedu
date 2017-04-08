package com.yanxiu.ce.core.train.controller;

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
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.train.entity.ProjectOperator;
import com.yanxiu.ce.core.train.entity.ProjectOperatorQuery;
import com.yanxiu.ce.core.train.service.ProjectOperatorService;

/**
 * 项目执行人管理
 * @author tangmin
 * @date 2016-04-19 14:04:52
 */
@Controller
@RequestMapping("/core/train/project/operator")
public class ProjectOperatorController extends BaseController{
	
	@Autowired
	private ProjectOperatorService projectOperatorService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("projectOperatorList")
	@RequiresPermissions("ProjectOperator:read")
	public String list(ProjectOperatorQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<ProjectOperator> page = projectOperatorService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/train/project/operator/projectOperatorList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("projectOperatorForm")
	@RequiresPermissions("ProjectOperator:create")
	public String projectOperatorForm(Model model){
		ProjectOperator entity = new ProjectOperator();
		model.addAttribute("entity", entity);
		return "core/train/project/operator/projectOperatorForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="projectOperatorForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("ProjectOperator:update")
	public String projectOperatorForm(@PathVariable Long id,Model model){
		ProjectOperator entity = projectOperatorService.selectById(id);
		model.addAttribute("entity", entity);
		return "core/train/project/operator/projectOperatorForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProjectOperator",method = RequestMethod.POST)
	@RequiresPermissions(value={"ProjectOperator:update","ProjectOperator:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存项目执行人")
	public String saveProjectOperator(ProjectOperator entity){
		AjaxCallback ok = AjaxCallback.OK(projectOperatorService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProjectOperatorByIds",method = RequestMethod.POST)
	@RequiresPermissions("ProjectOperator:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除项目执行人")
	public String deleteProjectOperatorByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectOperatorService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
