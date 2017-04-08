package com.yanxiu.ce.core.train.controller;

import java.util.List;

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
import com.yanxiu.ce.core.train.entity.ProjectTrainOrg;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrgQuery;
import com.yanxiu.ce.core.train.enums.OrgEnum;
import com.yanxiu.ce.core.train.service.ProjectTrainOrgService;

/**
 * 项目培训机构管理
 * @author tangmin
 * @date 2016-05-10 15:22:40
 */
@Controller
@RequestMapping("/core/train/project/trainorg")
public class ProjectTrainOrgController extends BaseController{
	
	@Autowired
	private ProjectTrainOrgService projectTrainOrgService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("projectTrainOrgList/{pid}")
	@RequiresPermissions("Project:conf")
	public String list(@PathVariable Long pid,ProjectTrainOrgQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setPid(pid.toString());
		Pagination<ProjectTrainOrg> page = projectTrainOrgService.selectListPagination(query);
		List<?> orgEnums = OrgEnum.toList();
		model.addAttribute("orgEnums",orgEnums);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("pid", pid);
		return "core/train/project/trainorg/projectTrainOrgList";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProjectTrainOrg",method = RequestMethod.POST)
	@RequiresPermissions("Project:conf")
	@ResponseBody
	@SystemControllerLog(description = "保存培训机构")
	public String saveProjectTrainOrg(@RequestParam(value="ids",required=true) String ids,@RequestParam(value="pid",required=true) Long pid){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectTrainOrgService.saveTrainOrgs(idList,pid);
		AjaxCallback ok = AjaxCallback.OK("添加成功");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProjectTrainOrgByIds",method = RequestMethod.POST)
	@RequiresPermissions("Project:conf")
	@ResponseBody
	@SystemControllerLog(description = "删除培训机构")
	public String deleteProjectTrainOrgByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectTrainOrgService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
