package com.yanxiu.ce.core.basic.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.TeacherArchival;
import com.yanxiu.ce.core.basic.entity.TeacherArchivalQuery;
import com.yanxiu.ce.core.basic.service.TeacherArchivalService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 学籍档案管理
 * @author tangmin
 * @date 2016-04-01 18:10:28
 */
@Controller
@RequestMapping("/core/basic/teacher/archival")
public class TeacherArchivalController {
	
	@Autowired
	private TeacherArchivalService teacherArchivalService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherArchivalList")
	@RequiresPermissions("TeacherArchival:read")
	public String list(TeacherArchivalQuery query,Model model){
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<TeacherArchival> page = teacherArchivalService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/teacher/archival/teacherArchivalList";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherArchivalForm/{tid}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherArchival:update")
	public String teacherArchivalForm(@PathVariable Long tid,Model model){
		TeacherArchival entity = teacherArchivalService.selectByTid(tid);
		
		List<DictItem> personTypes= dictCatlogService.getSelectItems("PERSON_TYPE");
		List<DictItem> dutys= dictCatlogService.getSelectItems("DUTY_TYPE");
		List<DictItem> titles= dictCatlogService.getSelectItems("TITLE_TYPE");
		List<DictItem> qualifys= dictCatlogService.getSelectItems("QUALIFY_TYPE");
		List<DictItem> boneTypes= dictCatlogService.getSelectItems("BONE_TYPE");
		List<DictItem> jobLevels= dictCatlogService.getSelectItems("JOB_LEVEL");
		List<DictItem> politics= dictCatlogService.getSelectItems("POLITIC_TYPE");
		model.addAttribute("personTypes", personTypes);
		model.addAttribute("dutys", dutys);
		model.addAttribute("titles", titles);
		model.addAttribute("qualifys", qualifys);
		model.addAttribute("boneTypes", boneTypes);
		model.addAttribute("jobLevels", jobLevels);
		model.addAttribute("politics", politics);
		
		model.addAttribute("tid", tid);
		model.addAttribute("entity", entity);
		return "core/basic/teacher/archival/teacherArchivalForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherArchival",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherArchival:update","TeacherArchival:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存学籍档案")
	public String saveTeacherArchival(TeacherArchival entity){
		AjaxCallback ok = AjaxCallback.OK(teacherArchivalService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	

}
