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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.core.basic.entity.TeacherAward;
import com.yanxiu.ce.core.basic.entity.TeacherAwardQuery;
import com.yanxiu.ce.core.basic.service.TeacherAwardService;

/**
 * 获奖情况管理
 * @author tangmin
 * @date 2016-04-19 11:44:11
 */
@Controller
@RequestMapping("/core/basic/teacher/award")
public class TeacherAwardController extends BaseController{
	
	@Autowired
	private TeacherAwardService teacherAwardService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherAwardList")
	@RequiresPermissions("TeacherAward:read")
	public String list(TeacherAwardQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<TeacherAward> page = teacherAwardService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/teacher/award/teacherAwardList";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping("teacherAwardForm/{tid}")
	@RequiresPermissions("TeacherAward:update")
	public String teacherAwardForm(@PathVariable Long tid,Model model){
		TeacherAwardQuery query = new TeacherAwardQuery();
		query.setTid(tid.toString());
		query.setTidLike(false);
		query.setOrderField("seq");
		
		List<TeacherAward> awards = teacherAwardService.selectList(query);
		model.addAttribute("tid", tid);
		model.addAttribute("awards", awards);
		return "core/basic/teacher/award/teacherAwardForm";
	}
	
	
	/**
	 * 单条 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherAward",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherAward:update","TeacherAward:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存获奖情况")
	public String saveTeacherAward(HttpServletRequest request)throws ParseException{
		String keypre = "award[0]";
		TeacherAward exp = new TeacherAward();
		confAwardPro(request, keypre, exp);
		AjaxCallback ok = AjaxCallback.OK(teacherAwardService.saveOrUpdate(exp));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 全部 保存方法
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="saveTeacherAwards",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherAward:update","TeacherAward:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存获奖情况")
	public String saveTeacherAwards(HttpServletRequest request) throws ParseException{
		List<TeacherAward> awards = Lists.newArrayList();
		Enumeration<String> keys = request.getParameterNames();
		Set<String> parmKeys = Sets.newHashSet(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    String[] split = k.split("\\.");
	    	parmKeys.add(split[0]);
		} 
		for(String keypre:parmKeys){
			TeacherAward exp = new TeacherAward();
			confAwardPro(request, keypre, exp);
			awards.add(exp);
		}
		AjaxCallback ok = AjaxCallback.OK(teacherAwardService.saveAwards(awards));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * set属性
	 * @param request
	 * @param keypre
	 * @param exp
	 * @throws ParseException
	 */
	private void confAwardPro(HttpServletRequest request, String keypre,
			TeacherAward exp) throws ParseException {
		String parmId = request.getParameter(keypre+".id");
		if(StringUtils.isNoneBlank(parmId)){
			exp.setId(Long.parseLong(request.getParameter(keypre+".id")));
		}
		exp.setTid(Long.parseLong(request.getParameter(keypre+".tid")));
		exp.setSeq(Integer.parseInt(request.getParameter(keypre+".seq")));
		exp.setTakeDate(DateUtils.SHORT_DATE_DASH_FORMAT.parse(request.getParameter(keypre+".takeDate")));
		exp.setName(request.getParameter(keypre+".name"));
		exp.setDept(request.getParameter(keypre+".dept"));
		exp.setMemo(request.getParameter(keypre+".memo"));
	}
	
	/**
	 * 删除
	 * @param
	 * @return
	 */
	@RequestMapping("deleteById/{id}")
	@RequiresPermissions("TeacherAward:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除获奖情况")
	public String deleteById(@PathVariable Long id){
		teacherAwardService.deleteById(id);
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}
	
}
