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
import com.yanxiu.ce.core.train.entity.ProjectReportConfig;
import com.yanxiu.ce.core.train.entity.ProjectReportConfigQuery;
import com.yanxiu.ce.core.train.enums.RegisterEnum;
import com.yanxiu.ce.core.train.service.ProjectReportConfigService;

/**
 * 报名配置管理
 * @author tangmin
 * @date 2016-04-21 16:10:32
 */
@Controller
@RequestMapping("/core/train/project/reportConfig")
public class ProjectReportConfigController extends BaseController{
	
	@Autowired
	private ProjectReportConfigService projectReportConfigService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("projectReportConfigList")
	@RequiresPermissions("ProjectReportConfig:read")
	public String list(ProjectReportConfigQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<ProjectReportConfig> page = projectReportConfigService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/train/project/reportConfig/projectReportConfigList";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="projectReportConfigForm/{pid}",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherArchival:update","TeacherArchival:create"},logical=Logical.AND)
	public String projectReportConfigForm(@PathVariable Long pid,Model model){
		ProjectReportConfig entity = projectReportConfigService.selectByPid(pid);
		List<?> registerTypes = RegisterEnum.toList();
		model.addAttribute("registerTypes", registerTypes);
		model.addAttribute("entity", entity);
		model.addAttribute("pid", pid);
		return "core/train/project/reportConfig/projectReportConfigForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProjectReportConfig",method = RequestMethod.POST)
	@RequiresPermissions(value={"ProjectReportConfig:update","ProjectReportConfig:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存报名配置")
	public String saveProjectReportConfig(ProjectReportConfig entity){
		ProjectReportConfig rc = projectReportConfigService.saveOrUpdate(entity);
		AjaxCallback ok = AjaxCallback.OK("保存成功");
		ok.setData(rc);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProjectReportConfigByIds",method = RequestMethod.POST)
	@RequiresPermissions("ProjectReportConfig:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除报名配置")
	public String deleteProjectReportConfigByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectReportConfigService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
