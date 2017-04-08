package com.yanxiu.ce.core.basic.controller;

import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.TeacherMovePojo;
import com.yanxiu.ce.core.basic.entity.TeacherMovePojoQuery;
import com.yanxiu.ce.core.basic.service.TeacherMovePojoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 12:18:18
 */
@Controller
@RequestMapping("/core/basic/teacher/move")
public class TeacherMovePojoController extends BasePctsController<TeacherMovePojoQuery>{
	
	@Autowired
	private TeacherMovePojoService teacherMovePojoService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions("TeacherMovePojo:read")
	public String list(TeacherMovePojoQuery query, Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query = configPstsQuery(query);//配置省市区县查询条件
		Pagination<TeacherMovePojo> page = teacherMovePojoService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/teacher/move/pojoList";
	}
	
}
