package com.yanxiu.ce.system.controller;

import java.util.List;
import java.util.Map;

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
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.utils.JsonUtils;
import com.yanxiu.ce.system.entity.Town;
import com.yanxiu.ce.system.entity.TownQuery;
import com.yanxiu.ce.system.service.TownService;

/**
 * 区/县管理
 * @author tangmin
 * @date 2016-03-08 17:31:47
 */
@Controller
@RequestMapping("/system/area/town")
public class TownController {
	
	@Autowired
	private TownService townService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("townList")
	public String list(TownQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setFields("cityno,townno,townname,seq");
		Pagination<Town> page = townService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/area/town/townList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("townForm")
	public String townForm(Model model){
		return "system/area/town/townForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="townForm/{id}",method = RequestMethod.POST)
	public String townForm(@PathVariable Long id,Model model){
		Town entity = townService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/area/town/townForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTown",method = RequestMethod.POST)
	@ResponseBody
	public String saveTown(Town entity){
		AjaxCallback ok = AjaxCallback.OK(townService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTownByIds",method = RequestMethod.POST)
	@ResponseBody
	public String deleteTownByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		townService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 根据cityNo获取区县的下拉框列表
	 * @param cityNo
	 * @return
	 */
	@RequestMapping("towns")
	@ResponseBody
	public String towns(@RequestParam(value="city",required=true) Integer cityNo){
		if(cityNo==null){
			cityNo = 0;
		}
		List<Town> towns = townService.towns(cityNo);
		//做一个字段名的转换，匹配 townNo-->value  townName-->label
		List<Map<String, Object>> records = Lists.newArrayList();
		
		Map<String, Object> defaultCity = Maps.newHashMap();
		defaultCity.put("value", "");
		defaultCity.put("label", "--区县--");
		records.add(defaultCity);
		
		for(Town town:towns){
			Map<String, Object> m = JsonUtils.beanToMap(town);
			m.put("value", town.getTownNo());
			m.put("label", town.getTownName());
			
			m.remove("townName");
			m.remove("townNo");
			m.remove("createTime");
			m.remove("deleted");
			m.remove("version");
			
			records.add(m);
		}
		return JSON.toJSONString(records);
		
	}

}
