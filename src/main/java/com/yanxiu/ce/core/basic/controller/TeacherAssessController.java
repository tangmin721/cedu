package com.yanxiu.ce.core.basic.controller;

import java.text.ParseException;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.google.common.collect.Sets;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherAssess;
import com.yanxiu.ce.core.basic.entity.TeacherAssessQuery;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.TeacherAssessService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 考核情况管理
 * @author tangmin
 * @date 2016-05-23 14:46:57
 */
@Controller
@RequestMapping("/core/basic/teacher/assess")
public class TeacherAssessController extends BaseController{
	
	@Autowired
	private TeacherAssessService teacherAssessService;
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	protected SchoolService schoolService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherAssessList/{tid}")
	@RequiresPermissions("TeacherAssess:read")
	public String list(@PathVariable Long tid, TeacherAssessQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setTid(tid.toString());
		Pagination<TeacherAssess> page = teacherAssessService.selectListPagination(query);
		
		List<DictItem> cyears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> checkResults= dictCatlogService.getSelectItems("CHECK_RESULT");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("cyears", cyears);
		model.addAttribute("checkResults", checkResults);
		return "core/basic/teacher/assess/teacherAssessList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @param tid
	 * @return
	 */
	@RequestMapping(value="teacherAssessForm")
	@RequiresPermissions("TeacherAssess:update")
	public String teacherAssessFormAdd(@RequestParam(value="tid",required=true) Long tid, Model model){
		TeacherAssess entity = new TeacherAssess();
		entity.setSeq(teacherAssessService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		List<DictItem> cyears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> checkResults= dictCatlogService.getSelectItems("CHECK_RESULT");

		//获取教师所属学校
		Teacher teacher = teacherService.selectById(tid);
		School school = schoolService.selectById(teacher.getSchool());
		String defSchool = school.getName();
		int schoolType = school.getSchoolType();
		
		model.addAttribute("tid", tid);
		model.addAttribute("entity", entity);
		model.addAttribute("cyears", cyears);
		model.addAttribute("checkResults", checkResults);
		model.addAttribute("defSchool", defSchool);
		model.addAttribute("schoolType", schoolType);
		return "core/basic/teacher/assess/teacherAssessForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherAssessForm/{id}")
	@RequiresPermissions("TeacherAssess:update")
	public String teacherAssessFormEdit(@PathVariable Long id,Model model){
		
		TeacherAssess entity = teacherAssessService.selectById(id);
		
		List<DictItem> cyears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> checkResults= dictCatlogService.getSelectItems("CHECK_RESULT");
		
		//获取教师所属学校
		Teacher teacher = teacherService.selectById(entity.getTid());
		School school = schoolService.selectById(teacher.getSchool());
		int schoolType = school.getSchoolType();
				

		model.addAttribute("tid", entity.getTid());
		model.addAttribute("entity", entity);
		model.addAttribute("cyears", cyears);
		model.addAttribute("checkResults", checkResults);
		model.addAttribute("schoolType", schoolType);
		return "core/basic/teacher/assess/teacherAssessForm";
	}
	
	/**
	 * 单条 保存方法
	 * @return
	 */
//	@RequestMapping(value="saveTeacherAssess",method = RequestMethod.POST)
//	@RequiresPermissions(value={"TeacherAssess:update","TeacherAssess:create"},logical=Logical.AND)
//	@ResponseBody
//	public String saveTeacherAssess(HttpServletRequest request)throws ParseException{
//		String keypre = "assess[0]";
//		TeacherAssess exp = new TeacherAssess();
//		confAssessPro(request, keypre, exp);
//		AjaxCallback ok = AjaxCallback.OK(teacherAssessService.saveOrUpdate(exp));
//		return JSON.toJSONString(ok);
//	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherAssess",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherAssess:update","TeacherAssess:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherAssess(TeacherAssess entity){
		AjaxCallback ok = AjaxCallback.OK(teacherAssessService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 全部 保存方法
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="saveTeacherAssesses",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherAssess:update","TeacherAssess:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存考核情况")
	public String saveTeacherAssesss(HttpServletRequest request) throws ParseException{
		List<TeacherAssess> assesss = Lists.newArrayList();
		Enumeration<String> keys = request.getParameterNames();
		Set<String> parmKeys = Sets.newHashSet(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    String[] split = k.split("\\.");
	    	parmKeys.add(split[0]);
		} 
		for(String keypre:parmKeys){
			TeacherAssess exp = new TeacherAssess();
			confAssessPro(request, keypre, exp);
			assesss.add(exp);
		}
		AjaxCallback ok = AjaxCallback.OK(teacherAssessService.saveAssesses(assesss));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * set属性
	 * @param request
	 * @param keypre
	 * @param exp
	 * @throws ParseException
	 */
	private void confAssessPro(HttpServletRequest request, String keypre,
			TeacherAssess exp) throws ParseException {
		String parmId = request.getParameter(keypre+".id");
		if(StringUtils.isNoneBlank(parmId)){
			exp.setId(Long.parseLong(request.getParameter(keypre+".id")));
		}
		exp.setTid(Long.parseLong(request.getParameter(keypre+".tid")));
		exp.setSeq(Integer.parseInt(request.getParameter(keypre+".seq")));
		exp.setCyear(Long.parseLong(request.getParameter(keypre+".cyear")));
		exp.setCheckResult(Long.parseLong(request.getParameter(keypre+".checkResult")));
		exp.setMemo(request.getParameter(keypre+".memo"));
		exp.setAssessUnit(request.getParameter(keypre+".assessUnit"));
	}
	

	/**
	 * 删除
	 * @param
	 * @return
	 */
	@RequestMapping("deleteById/{id}")
	@RequiresPermissions("TeacherAssess:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除考核情况")
	public String deleteById(@PathVariable Long id){
		teacherAssessService.deleteById(id);
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherAssessByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherAssess:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除考核情况")
	public String deleteTeacherAssessByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherAssessService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}

}
