package com.yanxiu.ce.core.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.core.statistics.service.ReportService;

@Controller
@RequestMapping("/core/statistics/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping("process")
	@ResponseBody
	public String process(){
		AjaxCallback ok = AjaxCallback.OK(reportService.process());
		return JSON.toJSONString(ok);
	}
	
	@RequestMapping("createTeacherForm")
	public String createTeacherForm(){
		return "core/statistics/report/createTeacherForm";
	}
	
	@RequestMapping("makeTeachers")
	@ResponseBody
	public String makeTeachers(@RequestParam("startNum") Integer startNum,@RequestParam("totalNum") Integer totalNum){
		this.reportService.addTeachers(startNum, totalNum);
		AjaxCallback ok = AjaxCallback.OK("开始后台执行");
		return JSON.toJSONString(ok);
	}
	
}
