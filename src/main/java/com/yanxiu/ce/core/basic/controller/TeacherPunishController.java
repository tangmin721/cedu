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
import com.yanxiu.ce.core.basic.entity.TeacherPunish;
import com.yanxiu.ce.core.basic.entity.TeacherPunishQuery;
import com.yanxiu.ce.core.basic.service.TeacherPunishService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 处分情况管理
 * @author tangmin
 * @date 2016-05-23 15:41:24
 */
@Controller
@RequestMapping("/core/basic/teacher/punish")
public class TeacherPunishController extends BaseController{
	
	@Autowired
	private TeacherPunishService teacherPunishService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherPunishList")
	@RequiresPermissions("TeacherPunish:read")
	public String list(TeacherPunishQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<TeacherPunish> page = teacherPunishService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/teacher/punish/teacherPunishList";
	}
	
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherPunishForm/{tid}")
	@RequiresPermissions("TeacherPunish:update")
	public String teacherPunishForm(@PathVariable Long tid,Model model){
		
		List<DictItem> levels= dictCatlogService.getSelectItems("PUNISH_LEVEL");
		
		TeacherPunishQuery query = new TeacherPunishQuery();
		query.setTid(tid.toString());
		query.setTidLike(false);
		query.setOrderField("seq");
		List<TeacherPunish> punishs = teacherPunishService.selectList(query);
		
		model.addAttribute("tid", tid);
		model.addAttribute("levels", levels);
		model.addAttribute("punishs", punishs);
		
		return "core/basic/teacher/punish/teacherPunishForm";
	}
	
	/**
	 * 单条 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherPunish",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherPunish:update","TeacherPunish:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherPunish(HttpServletRequest request)throws ParseException{
		String keypre = "punish[0]";
		TeacherPunish exp = new TeacherPunish();
		confPunishPro(request, keypre, exp);
		AjaxCallback ok = AjaxCallback.OK(teacherPunishService.saveOrUpdate(exp));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 全部 保存方法
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="saveTeacherPunishs",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherPunish:update","TeacherPunish:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存处分")
	public String saveTeacherPunishs(HttpServletRequest request) throws ParseException{
		List<TeacherPunish> punishs = Lists.newArrayList();
		Enumeration<String> keys = request.getParameterNames();
		Set<String> parmKeys = Sets.newHashSet(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    String[] split = k.split("\\.");
	    	parmKeys.add(split[0]);
		} 
		for(String keypre:parmKeys){
			TeacherPunish exp = new TeacherPunish();
			confPunishPro(request, keypre, exp);
			punishs.add(exp);
		}
		AjaxCallback ok = AjaxCallback.OK(teacherPunishService.savePunishs(punishs));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * set属性
	 * @param request
	 * @param keypre
	 * @param exp
	 * @throws ParseException
	 */
	private void confPunishPro(HttpServletRequest request, String keypre,
			TeacherPunish exp) throws ParseException {
		String parmId = request.getParameter(keypre+".id");
		if(StringUtils.isNoneBlank(parmId)){
			exp.setId(Long.parseLong(request.getParameter(keypre+".id")));
		}
		exp.setTid(Long.parseLong(request.getParameter(keypre+".tid")));
		exp.setSeq(Integer.parseInt(request.getParameter(keypre+".seq")));
		exp.setTdate(DateUtils.SHORT_DATE_DASH_FORMAT.parse(request.getParameter(keypre+".tdate")));
		exp.setLevel(Long.parseLong(request.getParameter(keypre+".level")));
		exp.setDept(request.getParameter(keypre+".dept"));
		exp.setMemo(request.getParameter(keypre+".memo"));
	}
	

	/**
	 * 删除
	 * @param
	 * @return
	 */
	@RequestMapping("deleteById/{id}")
	@RequiresPermissions("TeacherPunish:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除处分")
	public String deleteById(@PathVariable Long id){
		teacherPunishService.deleteById(id);
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}

}
