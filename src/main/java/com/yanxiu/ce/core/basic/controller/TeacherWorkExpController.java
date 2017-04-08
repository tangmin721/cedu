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
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.core.basic.entity.TeacherWorkExp;
import com.yanxiu.ce.core.basic.entity.TeacherWorkExpQuery;
import com.yanxiu.ce.core.basic.service.TeacherWorkExpService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 工作经历管理
 * @author tangmin
 * @date 2016-04-05 15:50:01
 */
@Controller
@RequestMapping("/core/basic/teacher/workExp")
public class TeacherWorkExpController {
	
	@Autowired
	private TeacherWorkExpService teacherWorkExpService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherWorkExpList/{tid}")
	@RequiresPermissions("TeacherWorkExp:read")
	public String list(@PathVariable Long tid, TeacherWorkExpQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<TeacherWorkExp> page = teacherWorkExpService.selectListPagination(query);
		
		List<DictItem> deptTypes= dictCatlogService.getSelectItems("DEPT_TYPE");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("deptTypes", deptTypes);
		return "core/basic/teacher/workExp/teacherWorkExpList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherWorkExpForm")
	@RequiresPermissions("TeacherWorkExp:create")
	public String teacherWorkExpFormAdd(@RequestParam(value="tid", required=true) Long tid, Model model){
		TeacherWorkExp entity = new TeacherWorkExp();
		entity.setSeq(teacherWorkExpService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		List<DictItem> deptTypes= dictCatlogService.getSelectTreeItems("DEPT_TYPE");
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("deptTypes", JSON.toJSONString(deptTypes));
		return "core/basic/teacher/workExp/teacherWorkExpForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherWorkExpForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherWorkExp:update")
	public String teacherWorkExpFormEdit(@PathVariable Long id,Model model){
		TeacherWorkExp entity = teacherWorkExpService.selectById(id);
		
		List<DictItem> deptTypes= dictCatlogService.getSelectTreeItems("DEPT_TYPE");
		String deptTypeName = "";
		if(entity.getDeptType() != null && !("").equals(entity.getDeptType())){
			for(DictItem dict : deptTypes){
				if(dict.getId().longValue() == Long.parseLong(entity.getDeptType())){
					dict.setChecked(true);
				}
			}
			
			deptTypeName = dictItemService.selectById(Long.parseLong(entity.getDeptType())).getName();
		}
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("deptTypeName", deptTypeName);
		model.addAttribute("deptTypes", JSON.toJSONString(deptTypes));
		return "core/basic/teacher/workExp/teacherWorkExpForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
//	@RequestMapping(value="teacherWorkExpForm/{tid}")
//	@RequiresPermissions("TeacherWorkExp:update")
//	public String teacherWorkExpForm(@PathVariable Long tid,Model model){
//		
//		List<DictItem> deptTypes= dictCatlogService.getSelectTreeItems("DEPT_TYPE");
//		
//		TeacherWorkExpQuery query = new TeacherWorkExpQuery();
//		query.setTid(tid.toString());
//		query.setTidLike(false);
//		query.setOrderField("seq");
//		List<TeacherWorkExp> workExps = teacherWorkExpService.selectList(query);
//		
//		for(TeacherWorkExp workExp : workExps){
//			if(workExp.getDeptType() != null && !("").equals(workExp.getDeptType())){
//				String deptTypeName = dictItemService.selectById(Long.parseLong(workExp.getDeptType())).getName();
//				workExp.setDeptTypeName(deptTypeName);
//			}
//		}
//		
//		model.addAttribute("tid", tid);
//		model.addAttribute("deptTypes", JSON.toJSONString(deptTypes));
//		model.addAttribute("workExps", workExps);
//		
//		return "core/basic/teacher/workExp/teacherWorkExpForm";
//	}
	
//	/**
//	 * 单条 保存方法
//	 * @return
//	 */
//	@RequestMapping(value="saveTeacherWorkExp",method = RequestMethod.POST)
//	@RequiresPermissions(value={"TeacherWorkExp:update","TeacherWorkExp:create"},logical=Logical.AND)
//	@ResponseBody
//	@SystemControllerLog(description = "保存工作经历")
//	public String saveTeacherWorkExp(HttpServletRequest request)throws ParseException{
//		String keypre = "workExp[0]";
//		TeacherWorkExp exp = new TeacherWorkExp();
//		confWorkExpPro(request, keypre, exp);
//		AjaxCallback ok = AjaxCallback.OK(teacherWorkExpService.saveOrUpdate(exp));
//		return JSON.toJSONString(ok);
//	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherWorkExp",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherWorkExp:update","TeacherWorkExp:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherWorkExp(TeacherWorkExp entity){
		AjaxCallback ok = AjaxCallback.OK(teacherWorkExpService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 全部 保存方法
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="saveTeacherWorkExps",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherWorkExp:update","TeacherWorkExp:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存工作经历")
	public String saveTeacherWorkExps(HttpServletRequest request) throws ParseException{
		List<TeacherWorkExp> workExps = Lists.newArrayList();
		Enumeration<String> keys = request.getParameterNames();
		Set<String> parmKeys = Sets.newHashSet(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    String[] split = k.split("\\.");
	    	parmKeys.add(split[0]);
		} 
		for(String keypre:parmKeys){
			TeacherWorkExp exp = new TeacherWorkExp();
			confWorkExpPro(request, keypre, exp);
			workExps.add(exp);
		}
		AjaxCallback ok = AjaxCallback.OK(teacherWorkExpService.saveWorkExps(workExps));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * set属性
	 * @param request
	 * @param keypre
	 * @param exp
	 * @throws ParseException
	 */
	private void confWorkExpPro(HttpServletRequest request, String keypre,
			TeacherWorkExp exp) throws ParseException {
		String parmId = request.getParameter(keypre+".id");
		if(StringUtils.isNoneBlank(parmId)){
			exp.setId(Long.parseLong(request.getParameter(keypre+".id")));
		}
		exp.setTid(Long.parseLong(request.getParameter(keypre+".tid")));
		exp.setSeq(Integer.parseInt(request.getParameter(keypre+".seq")));
		exp.setStartDate(DateUtils.SHORT_DATE_DASH_FORMAT.parse(request.getParameter(keypre+".startDate")));
		exp.setEndDate(DateUtils.SHORT_DATE_DASH_FORMAT.parse(request.getParameter(keypre+".endDate")));
		exp.setDuty(request.getParameter(keypre+".duty"));
		exp.setDept(request.getParameter(keypre+".dept"));
		exp.setVoucher(request.getParameter(keypre+".voucher"));
		exp.setMobile(request.getParameter(keypre+".mobile"));
		exp.setMemo(request.getParameter(keypre+".memo"));
		exp.setDeptType(request.getParameter(keypre+".deptType"));
	}
	

	/**
	 * 删除s
	 * @param
	 * @return
	 */
	@RequestMapping("deleteById/{id}")
	@RequiresPermissions("TeacherWorkExp:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除工作经历")
	public String deleteById(@PathVariable Long id){
		teacherWorkExpService.deleteById(id);
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherWorkExpByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherWorkExp:delete")
	@ResponseBody
	public String deleteTeacherWorkExpByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherWorkExpService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
}
