package com.yanxiu.ce.core.train.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetailQuery;
import com.yanxiu.ce.core.train.service.ProjectQuotaService;
import com.yanxiu.ce.core.train.service.ProjectRosterDetailService;
import com.yanxiu.ce.core.train.service.ProjectRosterService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;

/**
 * 报名名单详单管理
 * @author tangmin
 * @date 2016-04-20 16:00:38
 */
@Controller
@RequestMapping("/core/train/project/roster/detail")
public class ProjectRosterDetailController extends BaseController{
	
	@Autowired
	private ProjectRosterDetailService projectRosterDetailService;
	
	@Autowired
	private ProjectRosterService projectRosterService;
	
	@Autowired
	private ProjectQuotaService projectQuotaService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("projectRosterDetailList/{applyId}")
	@RequiresPermissions("ProjectRosterDetail:read")
	public String list(@PathVariable Long applyId,ProjectRosterDetailQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setRosterId(applyId.toString());
		query.setRosterIdLike(false);
		Pagination<ProjectRosterDetail> page = projectRosterDetailService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("applyId", applyId);
		return "core/train/project/roster/detail/projectRosterDetailList";
	}
	
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("projectRosterDetailForm")
	@RequiresPermissions("ProjectRosterDetail:create")
	public String projectRosterDetailForm(Model model){
		ProjectRosterDetail entity = new ProjectRosterDetail();
	//	entity.setSeq(projectRosterDetailService.selectMaxSeq()+1);
		model.addAttribute("entity", entity);
		return "core/train/project/roster/detail/projectRosterDetailForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="projectRosterDetailForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("ProjectRosterDetail:update")
	public String projectRosterDetailForm(@PathVariable Long id,Model model){
		ProjectRosterDetail entity = projectRosterDetailService.selectById(id);
		model.addAttribute("entity", entity);
		return "core/train/project/roster/detail/projectRosterDetailForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProjectRosterDetail",method = RequestMethod.POST)
	@RequiresPermissions(value={"ProjectRosterDetail:update","ProjectRosterDetail:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存申请单人员名单")
	public String saveProjectRosterDetail(@RequestParam(value="ids",required=true) String ids,@RequestParam(value="applyId",required=true) Long applyId){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		User user = ShiroUtils.getCurrentUser();
		Map<String, Object> result = projectRosterDetailService.saveTeachers(idList,applyId,user);
		AjaxCallback ok = AjaxCallback.OK("添加成功！");
		ok.setData("名额【"+result.get("numSelectPpcts")+"】，已添加【"+result.get("countTeacherNum")+"】人");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProjectRosterDetailByIds",method = RequestMethod.POST)
	@RequiresPermissions("ProjectRosterDetail:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除申请单人员名单")
	public String deleteProjectRosterDetailByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectRosterDetailService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	
	/*****************************************************/
	
	/**
	 * 参培人明细list
	 * @return
	 */
	@RequestMapping("trainTeachers")
	@RequiresPermissions("ProjectRosterDetail:read")
	public String trainTeachers(ProjectRosterDetailQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<ProjectRosterDetail> page = projectRosterDetailService.selectTrainTeachersPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/train/project/roster/detail/trainTeachers";
	}
	
	
	/**
	 * 参培人明细list   有学分授予按钮，权限
	 * @return
	 */
	@RequestMapping("trainScores")
	@RequiresPermissions("Project:score")
	public String trainScores(ProjectRosterDetailQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<ProjectRosterDetail> page = projectRosterDetailService.selectTrainTeachersPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("pid", query.getPid());
		return "core/train/project/roster/detail/trainScores";
	}
	
	

}
