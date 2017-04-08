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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.Grade;
import com.yanxiu.ce.core.basic.entity.GradeQuery;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.basic.service.GradeService;
import com.yanxiu.ce.core.basic.service.StageService;

/**
 * 年级管理
 * @author tangmin
 * @date 2016-04-01 12:05:00
 */
@Controller
@RequestMapping("/core/basic/conf/grade")
public class GradeController {
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private StageService stageService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("gradeList")
	@RequiresPermissions("Grade:read")
	public String list(GradeQuery query,Model model){
		query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<Grade> page = gradeService.selectListPagination(query);
		
		//学科下拉框
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("stages", stages);
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/conf/grade/gradeList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("gradeForm")
	@RequiresPermissions("Grade:create")
	public String gradeForm(Model model){
		Grade entity = new Grade();
		entity.setSeq(gradeService.selectMaxSeq()+1);
		//学科下拉框
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("stages", stages);
		model.addAttribute("entity", entity);
		return "core/basic/conf/grade/gradeForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="gradeForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Grade:update")
	public String gradeForm(@PathVariable Long id,Model model){
		Grade entity = gradeService.selectById(id);
		//学科下拉框
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("stages", stages);
		model.addAttribute("entity", entity);
		return "core/basic/conf/grade/gradeForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveGrade",method = RequestMethod.POST)
	@RequiresPermissions(value={"Grade:update","Grade:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存年级")
	public String saveGrade(Grade entity){
		AjaxCallback ok = AjaxCallback.OK(gradeService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteGradeByIds",method = RequestMethod.POST)
	@RequiresPermissions("Grade:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除年级")
	public String deleteGradeByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		gradeService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * grade下拉框
	 */
	@RequestMapping("grades/{stage}")
	@ResponseBody
	public String grades(@PathVariable Long stage){
		GradeQuery query = new GradeQuery();
		query.setFields("id,name");
		query.setStageIdLike(false);
		query.setStageId(stage.toString());
		query.setOrderField("seq");
		return JSON.toJSONString(gradeService.selectList(query));
	}

}
