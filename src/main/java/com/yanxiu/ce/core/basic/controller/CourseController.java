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
import com.yanxiu.ce.core.basic.entity.Course;
import com.yanxiu.ce.core.basic.entity.CourseQuery;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.basic.service.CourseService;
import com.yanxiu.ce.core.basic.service.StageService;

/**
 * 学科管理
 * @author tangmin
 * @date 2016-04-01 12:06:26
 */
@Controller
@RequestMapping("/core/basic/conf/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StageService stageService;
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("courseList")
	@RequiresPermissions("Course:read")
	public String list(CourseQuery query,Model model){
		query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<Course> page = courseService.selectListPagination(query);
		
		//学科下拉框
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("stages", stages);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/conf/course/courseList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("courseForm")
	@RequiresPermissions("Course:create")
	public String courseForm(Model model){
		Course entity = new Course();
		entity.setSeq(courseService.selectMaxSeq()+1);
		//学科下拉框
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("stages", stages);
		model.addAttribute("entity", entity);
		return "core/basic/conf/course/courseForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="courseForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Course:update")
	public String courseForm(@PathVariable Long id,Model model){
		Course entity = courseService.selectById(id);
		//学科下拉框
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("stages", stages);
		model.addAttribute("entity", entity);
		return "core/basic/conf/course/courseForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveCourse",method = RequestMethod.POST)
	@RequiresPermissions(value={"Course:update","Course:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存学科")
	public String saveCourse(Course entity){
		AjaxCallback ok = AjaxCallback.OK(courseService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteCourseByIds",method = RequestMethod.POST)
	@RequiresPermissions("Course:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除学科")
	public String deleteCourseByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		courseService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * stage下拉框
	 */
	@RequestMapping("courses/{stage}")
	@ResponseBody
	public String courses(@PathVariable Long stage){
		CourseQuery query = new CourseQuery();
		query.setFields("id,name");
		query.setStageIdLike(false);
		query.setStageId(stage.toString());
		query.setOrderField("seq");
		return JSON.toJSONString(courseService.selectList(query));
	}

}
