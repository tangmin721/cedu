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
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.CityQuery;
import com.yanxiu.ce.system.service.CityService;

/**
 * 市管理
 * @author tangmin
 * @date 2016-03-08 17:32:57
 */
@Controller
@RequestMapping("/system/area/city")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("cityList")
	public String list(CityQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setFields("provinceno,cityno,cityname,seq");
		Pagination<City> page = cityService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/area/city/cityList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("cityForm")
	public String cityForm(Model model){
		return "system/area/city/cityForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cityForm/{id}",method = RequestMethod.POST)
	public String cityForm(@PathVariable Long id,Model model){
		City entity = cityService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/area/city/cityForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveCity",method = RequestMethod.POST)
	@ResponseBody
	public String saveCity(City entity){
		AjaxCallback ok = AjaxCallback.OK(cityService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteCityByIds",method = RequestMethod.POST)
	@ResponseBody
	public String deleteCityByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		cityService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 根据provinceNo获取市的下拉框列表
	 * @param provinceNo
	 * @return
	 */
	@RequestMapping("citys")
	@ResponseBody
	public String citys(@RequestParam(value="province",required=true) Integer provinceNo){
		if(provinceNo==null){
			provinceNo = 0;
		}
		List<City> citys = cityService.citys(provinceNo);
		
		//做一个字段名的转换，匹配 cityNo-->value  cityName-->lable
		List<Map<String, Object>> records = Lists.newArrayList();
		
		Map<String, Object> defaultCity = Maps.newHashMap();
		defaultCity.put("value", "");
		defaultCity.put("label", "--城市--");
		records.add(defaultCity);
		for(City city:citys){
			Map<String, Object> m = JsonUtils.beanToMap(city);
			m.put("value", city.getCityNo());
			m.put("label", city.getCityName());
			
			m.remove("cityName");
			m.remove("cityNo");
			m.remove("createTime");
			m.remove("deleted");
			m.remove("version");
			
			records.add(m);
		}
		return JSON.toJSONString(records);
	}
	

}
