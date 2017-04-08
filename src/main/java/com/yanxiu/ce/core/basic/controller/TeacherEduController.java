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
import com.yanxiu.ce.core.basic.entity.Course;
import com.yanxiu.ce.core.basic.entity.CourseQuery;
import com.yanxiu.ce.core.basic.entity.Grade;
import com.yanxiu.ce.core.basic.entity.GradeQuery;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.basic.entity.TeacherEdu;
import com.yanxiu.ce.core.basic.entity.TeacherEduQuery;
import com.yanxiu.ce.core.basic.service.CourseService;
import com.yanxiu.ce.core.basic.service.GradeService;
import com.yanxiu.ce.core.basic.service.StageService;
import com.yanxiu.ce.core.basic.service.TeacherEduService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 教学和学历管理
 * @author tangmin
 * @date 2016-04-05 14:01:34
 */
@Controller
@RequestMapping("/core/basic/teacher/edu")
public class TeacherEduController {
	
	@Autowired
	private TeacherEduService teacherEduService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private GradeService gradeService;
	
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherEduList")
	@RequiresPermissions("TeacherEdu:read")
	public String list(TeacherEduQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<TeacherEdu> page = teacherEduService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/teacher/edu/teacherEduList";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherEduForm/{tid}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherEdu:update")
	public String teacherEduForm(@PathVariable Long tid,Model model){
		TeacherEdu entity = teacherEduService.selectByTid(tid);
		
		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
		List<Stage> stages = stageService.selectStages();
		List<DictItem> jobStatuss= dictCatlogService.getSelectItems("JOB_STATUS");
		
		List<Course> courses = null;
		CourseQuery csQuery = new CourseQuery();
		if(entity!=null&&entity.getStage()!=null){
			csQuery.setStageId(entity.getStage().toString());
			courses = courseService.selectList(csQuery );
		}
		
		List<Grade> grades = null;
		GradeQuery gdQuery = new GradeQuery();
		if(entity!=null&&entity.getStage()!=null){
			gdQuery.setStageId(entity.getStage().toString());
			grades = gradeService.selectList(gdQuery );
		}
		
		model.addAttribute("degrees", degrees);
		model.addAttribute("stages", stages);
		model.addAttribute("courses", courses);
		model.addAttribute("grades", grades);
		model.addAttribute("jobStatuss", jobStatuss);
		
		model.addAttribute("tid", tid);
		model.addAttribute("entity", entity);
		return "core/basic/teacher/edu/teacherEduForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherEdu",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherEdu:update","TeacherEdu:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存教学和学历")
	public String saveTeacherEdu(TeacherEdu entity){
		AjaxCallback ok = AjaxCallback.OK(teacherEduService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
}
