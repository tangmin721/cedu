package com.yanxiu.ce.core.train.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetailQuery;
import com.yanxiu.ce.core.train.entity.ProjectRosterQuery;
import com.yanxiu.ce.core.train.service.ProjectQuotaService;
import com.yanxiu.ce.core.train.service.ProjectRosterDetailService;
import com.yanxiu.ce.core.train.service.ProjectRosterService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;

/**
 * 报名业务管理
 * @author tangmin
 * @date 2016-04-28 09:53:26
 */
@Controller
@RequestMapping("/core/train/project/roster")
public class ProjectRosterController extends BaseController{
	
	@Autowired
	private ProjectRosterService projectRosterService;
	
	@Autowired
	private ProjectQuotaService projectQuotaService;
	
	@Value(value = "${FR_URL}")
	private String FR_URL;
	
	@Autowired
	private ProjectRosterDetailService projectRosterDetailService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("projectRosterList")
	@RequiresPermissions("ProjectRoster:read")
	public String list(ProjectRosterQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<ProjectRoster> page = projectRosterService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/train/project/roster/projectRosterList";
	}
	
	/**
	 * 进入form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="projectRosterForm/{pid}",method = RequestMethod.POST)
	@RequiresPermissions("ProjectRoster:update")
	public String projectRosterForm(@PathVariable Long pid,@RequestParam(value="id",required=false) Long id,Model model){
		ProjectRoster entity = null;
		if(id!=null){
			entity  = projectRosterService.selectById(id);
		}
		model.addAttribute("entity", entity);
		model.addAttribute("pid", pid);
		return "core/train/project/roster/projectRosterForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProjectRoster",method = RequestMethod.POST)
	@RequiresPermissions("Project:apply")
	@ResponseBody
	@SystemControllerLog(description = "保存申请单主单")
	public String saveProjectRoster(ProjectRoster entity){
		
		User currentUser = ShiroUtils.getCurrentUser();
		entity.setApplyUserId(currentUser.getId());
		String msg = "保存成功";
		AjaxCallback ok = AjaxCallback.OK(msg);
		ok.setData(projectRosterService.saveApply(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 上报
	 * @return
	 */
	@RequestMapping(value="reportRoster/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Project:apply")
	@ResponseBody
	@SystemControllerLog(description = "上报申请单")
	public String reportRoster(@PathVariable Long id){
		String msg = "上报成功";
		projectRosterService.saveReport(id);
		AjaxCallback ok = AjaxCallback.OK(msg);
		ok.setData(this.projectRosterService.selectById(id));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProjectRosterByIds",method = RequestMethod.POST)
	@RequiresPermissions("Project:apply")
	@ResponseBody
	@SystemControllerLog(description = "删除申请单")
	public String deleteProjectRosterByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectRosterService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/******************************************分界线********************************************************************/
	/**
	 * 进入报名申请的管理页面
	 * @return
	 */
	@RequestMapping("applyList/{pid}")
	@RequiresPermissions("Project:apply")
	public String projectReportApply(@PathVariable Long pid,ProjectRosterQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setPid(pid.toString());
		Pagination<ProjectRoster> page = projectRosterService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("pid", pid);
		return "core/train/project/base/applyList";
	}
	
	/**
	 * 进入报名申请管理tabs
	 * @return
	 */
	@RequestMapping("applyForm/{pid}")
	@RequiresPermissions("Project:apply")
	public String projectReportApply(@PathVariable Long pid,Model model){
		User currentUser = ShiroUtils.getCurrentUser();
		ProjectRoster entity = projectRosterService.selectOneRoster(pid, currentUser.getProvince(), 
				currentUser.getCity(), currentUser.getTown(), currentUser.getSchool());
	
		if(entity!=null){
			model.addAttribute("entity", entity);
			model.addAttribute("id", entity.getId());
			model.addAttribute("status", entity.getStatus());
		}else{
			model.addAttribute("id", null);
			model.addAttribute("status", 0);
		}
		
		Integer quotaNum = projectQuotaService.numSelectPpcts(pid, currentUser.getProvince(), currentUser.getCity(),
				currentUser.getTown(), currentUser.getSchool());//上级分配的人数
		
		model.addAttribute("pid", pid);
		model.addAttribute("quotaNum", quotaNum);
		
		return "core/train/project/base/applyForm";
	}
	
	/**
	 * 参不参加报名
	 * @return
	 */
	@RequestMapping("joinOrNot/{pid}/{status}")
	@RequiresPermissions("Project:apply")
	@ResponseBody
	@SystemControllerLog(description = "变更是否参加报名状态")
	public String joinOrNot(@PathVariable Long pid,@PathVariable Integer status,Model model){
		User currentUser = ShiroUtils.getCurrentUser();
		projectRosterService.joinOrNot(pid,currentUser,status);
		AjaxCallback ok = AjaxCallback.OK("保存成功");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 校验申请人名单人数
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping("checkCount/{applyId}")
	@ResponseBody
	public String checkCount(@PathVariable Long applyId,Model model){
		User currentUser = ShiroUtils.getCurrentUser();
		Map<String, Object> checkReport = projectRosterService.checkReport(applyId,currentUser);
		AjaxCallback ok = AjaxCallback.OK("上报成功！");
		ok.setData(checkReport);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="applyFormEdit/{id}")
	@RequiresPermissions("Project:apply")
	public String applyFormEdit(@PathVariable Long id,Model model){
		ProjectRoster entity = projectRosterService.selectById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("pid", entity.getPid());
		model.addAttribute("id", id);
		return "core/train/project/base/applyForm";
	}
	
	
	/**
	 * 进入报名审批的管理页面
	 * @return
	 */
	@RequestMapping("checkList/{pid}")
	@RequiresPermissions("Project:check")
	public String projectReportCheck(@PathVariable Long pid,ProjectRosterQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setPid(pid.toString());
		List<Long> statusIds = Lists.newArrayList(1l,2l,3l,4l);
		query.setStatusIds(statusIds);
		Pagination<ProjectRoster> page = projectRosterService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("pid", pid);
		return "core/train/project/base/checkList";
	}
	
	/**
	 * 进入报名审批管理tabs
	 * @param id
	 * @return
	 */
	@RequestMapping(value="checkForm/{id}")
	@RequiresPermissions("Project:check")
	public String checkForm(@PathVariable Long id,ProjectRosterDetailQuery query,Model model){
		ProjectRoster entity = projectRosterService.selectById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("pid", entity.getPid());
		model.addAttribute("id", id);
		
		query.setRosterId(id.toString());
		query.setRosterIdLike(false);
		Pagination<ProjectRosterDetail> page = projectRosterDetailService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("applyId", id);
		
		return "core/train/project/base/checkForm";
	}
	
	/**
	 * 进入报名审批管理div
	 * @param id
	 * @return
	 */
	@RequestMapping(value="checkAudit/{id}")
	@RequiresPermissions("Project:check")
	public String checkAudit(@PathVariable Long id,ProjectRosterDetailQuery query,Model model){
		ProjectRoster entity = projectRosterService.selectById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("pid", entity.getPid());
		model.addAttribute("id", id);
		 
		query.setRosterId(id.toString());
		query.setRosterIdLike(false);
		Pagination<ProjectRosterDetail> page = projectRosterDetailService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("applyId", id);
		model.addAttribute("status", entity.getStatus());
		return "core/train/project/base/checkAudit";
	}
	
	/**
	 * 进入报名审批管理 查看申请信息列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="checkView/{id}")
	@RequiresPermissions("Project:check")
	public String checkView(@PathVariable Long id,ProjectRosterDetailQuery query,Model model){
		ProjectRoster entity = projectRosterService.selectById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("pid", entity.getPid());
		model.addAttribute("id", id);
		
		query.setRosterId(id.toString());
		query.setRosterIdLike(false);
		Pagination<ProjectRosterDetail> page = projectRosterDetailService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("applyId", id);
		
		return "core/train/project/base/checkView";
	}

	/**
	 * 审批form提交
	 * @return
	 */
	@RequestMapping(value="auditSave",method = RequestMethod.POST)
	@RequiresPermissions("Project:apply")
	@ResponseBody
	@SystemControllerLog(description = "审批申请单")
	public String auditSave(@RequestParam(value="id",required=false) Long id,
			@RequestParam(value="status",required=false) Integer status,@RequestParam(value="checkDesc",required=false) String checkDesc){
		Long checkUserId = ShiroUtils.getCurrentUser().getId();
		Long updateStatus = projectRosterService.auditSave(id, status,checkDesc,checkUserId);
		String msg = "操作成功";
		if(updateStatus!=1){
			msg = "操作失败";
		}
		AjaxCallback ok = AjaxCallback.OK(msg);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 变更状态
	 * @return
	 */
	@RequestMapping(value="updateStatus",method = RequestMethod.POST)
	@RequiresPermissions("Project:apply")
	@ResponseBody
	@SystemControllerLog(description = "变更申请单状态")
	public String updateStatus(@RequestParam(value="id",required=false) Long id,
			@RequestParam(value="status",required=false) Integer status){
		Long updateStatus = projectRosterService.updateStatus(id, status);
		String msg = "操作成功";
		if(updateStatus!=1){
			msg = "操作失败";
		}
		AjaxCallback ok = AjaxCallback.OK(msg);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 报表查看报名申请单
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("applyViewReport/{id}")
	public String teacherView(@PathVariable Long id,Model model){
		model.addAttribute("id", id);
		model.addAttribute("fr_url", FR_URL);
		return "core/train/project/base/applyViewReport";
	}
	
	/**
	 * 报表查看各单位人员上报情况
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("checkStaticsFr/{pid}")
	public String checkStaticsFr(@PathVariable Long pid,Model model){
		model.addAttribute("pid", pid);
		model.addAttribute("fr_url", FR_URL);
		return "core/train/project/base/checkStaticsFr";
	}
}
