package com.yanxiu.ce.system.controller;

import java.util.List;

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
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.ProvinceQuery;
import com.yanxiu.ce.system.service.ProvinceService;

/**
 * 省管理
 * @author tangmin
 * @date 2016-03-08 17:32:22
 */
@Controller
@RequestMapping("/system/area/province")
public class ProvinceController {
	
	@Autowired
	private ProvinceService provinceService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("provinceList")
	public String list(ProvinceQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setFields("provinceno,provincename,seq");
		Pagination<Province> page = provinceService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/area/province/provinceList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("provinceForm")
	public String provinceForm(Model model){
		return "system/area/province/provinceForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="provinceForm/{id}",method = RequestMethod.POST)
	public String provinceForm(@PathVariable Long id,Model model){
		Province entity = provinceService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/area/province/provinceForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProvince",method = RequestMethod.POST)
	@ResponseBody
	public String saveProvince(Province entity){
		AjaxCallback ok = AjaxCallback.OK(provinceService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProvinceByIds",method = RequestMethod.POST)
	@ResponseBody
	public String deleteProvinceByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		provinceService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 获取所有省下拉框列表
	 * @return
	 */
	@RequestMapping("provinces")
	@ResponseBody
	public String provinces(){
		return JSON.toJSONString(provinceService.provinces());
	}

}
