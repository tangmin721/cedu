package com.yanxiu.ce.core.basic.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherTalentProject;
import com.yanxiu.ce.core.basic.entity.TeacherTalentProjectQuery;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.basic.service.TeacherTalentProjectService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 入选人才项目管理
 * @author tangmin
 * @date 2016-12-27 16:21:49
 */
@Controller
@RequestMapping("/core/basic/teacher/teacherTalentProject")
public class TeacherTalentProjectController extends BaseController{
	
	@Autowired
	private TeacherTalentProjectService teacherTalentProjectService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	protected SchoolService schoolService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherTalentProjectList/{tid}")
	@RequiresPermissions("TeacherTalentProject:read")
	public String list(@PathVariable Long tid, TeacherTalentProjectQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		query.setTid(tid);
		Pagination<TeacherTalentProject> page = teacherTalentProjectService.selectListPagination(query);
		
		List<DictItem> projectNos = dictCatlogService.getSelectItems("TALENT_PROJECT_TYPE");
		
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		model.addAttribute("projectNos", projectNos);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("years", years);
		return "core/basic/teacher/teacherTalentProject/teacherTalentProjectList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherTalentProjectForm")
	@RequiresPermissions("TeacherTalentProject:create")
	public String teacherTalentProjectFormAdd(@RequestParam(value="tid",required=true) Long tid, Model model){
		TeacherTalentProject entity = new TeacherTalentProject();
		entity.setSeq(teacherTalentProjectService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		List<DictItem> projectNos = dictCatlogService.getSelectTreeItems("TALENT_PROJECT_TYPE");
		
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy");
		Date time = new Date();
		String currentYear = sdf.format(time);
		
		//获取教师所属学校
		Teacher teacher = teacherService.selectById(entity.getTid());
		School school = schoolService.selectById(teacher.getSchool());
		int schoolType = school.getSchoolType();
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("projectNos", JSON.toJSONString(projectNos));
		model.addAttribute("years", years);
		model.addAttribute("currentYear", currentYear);
		model.addAttribute("schoolType", schoolType);
		return "core/basic/teacher/teacherTalentProject/teacherTalentProjectForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherTalentProjectForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherTalentProject:update")
	public String teacherTalentProjectFormEdit(@PathVariable Long id,Model model){
		TeacherTalentProject entity = teacherTalentProjectService.selectById(id);
		
		//人才项目信息
		String projectName = "";
		List<DictItem> projectNos = dictCatlogService.getSelectTreeItems("TALENT_PROJECT_TYPE");
		Long projectNo = entity.getProjectNo();
		if(projectNo != null && projectNo.longValue() != 0){
			for (DictItem ent : projectNos) {
				// 单选的情况，多选还需要split
				if (ent.getId().longValue() == projectNo.longValue()) {
					ent.setChecked(true);
				}
			}
			DictItem items = dictItemService.selectById(projectNo);
			projectName = items.getName();
		}
		
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		//获取教师所属学校
		Teacher teacher = teacherService.selectById(entity.getTid());
		School school = schoolService.selectById(teacher.getSchool());
		int schoolType = school.getSchoolType();
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("projectNos", JSON.toJSONString(projectNos));
		model.addAttribute("projectName", projectName);
		model.addAttribute("years", years);
		model.addAttribute("schoolType", schoolType);
		return "core/basic/teacher/teacherTalentProject/teacherTalentProjectForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherTalentProject",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherTalentProject:update","TeacherTalentProject:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherTalentProject(TeacherTalentProject entity){
		AjaxCallback ok = AjaxCallback.OK(teacherTalentProjectService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherTalentProjectByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherTalentProject:delete")
	@ResponseBody
	public String deleteTeacherTalentProjectByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherTalentProjectService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
