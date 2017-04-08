package com.yanxiu.ce.core.solr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.solr.BaseSolrQuery;
import com.yanxiu.ce.common.core.solr.SolrResult;
import com.yanxiu.ce.core.solr.dto.TeacherSolr;
import com.yanxiu.ce.core.solr.service.TeacherSolrService;


@Controller
@RequestMapping("/core/solr/teacher")
public class TeacherSolrController {
	
	@Autowired
	private TeacherSolrService teacherSolrService;
	/**
	 * 导入全部teacher数据到solr索引库
	 * @return
	 */
	@RequestMapping("importAll")
	//@RequiresPermissions("TeacherSolr:conf")
	@ResponseBody
	public String importAll(){
		String msg = "导入成功！";
		try {
			teacherSolrService.importAll();
		} catch (Exception e) {
			msg = "导入失败！";
			e.printStackTrace();
		}
		AjaxCallback ok = AjaxCallback.OK(msg);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 查询服务  json版
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("search")
	//@RequiresPermissions("TeacherSolr:search")
	@ResponseBody
	public String search(BaseSolrQuery query) throws Exception{
		SolrResult<TeacherSolr> queryByStr = teacherSolrService.queryByStr(query);
		return JSON.toJSONString(queryByStr);
	}
	
	/**
	 * 查询服务  list版 list页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("searchList")
	public String list(BaseSolrQuery query,Model model) throws Exception{
		System.out.println(query.getQ());
		SolrResult<TeacherSolr> resultSolr = teacherSolrService.queryByStr(query);
		model.addAttribute("query", query);
		model.addAttribute("page", resultSolr);
		return "core/solr/teacherSolrList";
	}
	
	
}
