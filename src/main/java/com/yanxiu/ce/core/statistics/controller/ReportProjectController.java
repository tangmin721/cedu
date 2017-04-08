package com.yanxiu.ce.core.statistics.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportProjectQuery;
import com.yanxiu.ce.core.statistics.enums.ReportProjectWayEnum;
import com.yanxiu.ce.core.statistics.service.ReportProjectService;
import com.yanxiu.ce.system.dto.PctsQueryDto;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.CityService;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.TownService;


/**
 * 项目统计
 * @author tangmin
 * @date 2016年8月18日
 */
@Controller
@RequestMapping("/core/statistics/project")
public class ReportProjectController{
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private TownService townService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private ReportProjectService reportProjectService;
	
	@RequestMapping("main")
	public String main(ReportProjectQuery query,Model model){
		if(StringUtils.isBlank(query.getWay())){
			query.setWay(ReportProjectWayEnum.trainType.getValue()+"");
		}
		this.configPstsQuery(query);
		
		List<?> ways = ReportProjectWayEnum.toList();
		model.addAttribute("ways", ways);
		model.addAttribute("query", query);
		
		String wayName = "";//副标题
		if(StringUtils.isNotBlank(query.getWay())){
			wayName = ReportProjectWayEnum.getDesc(Integer.parseInt(query.getWay()));
		}else{
			wayName = ReportProjectWayEnum.trainType.getDesc();
		}
		model.addAttribute("wayName",wayName);
		
		
		List<NameValue> results = reportProjectService.groupByProperty(ReportProjectWayEnum.getName(Integer.parseInt(query.getWay())), "sys_dict_item",
				query.getProvince(), query.getCity(), query.getTown(), query.getSchool(), query.getSchoolYear());
		
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		//总数
		Integer total = 0;
		for(NameValue nv:results){
			nvs.add(nv);
			titils.add(nv.getName());
			total +=  Integer.parseInt(nv.getValue());
		}
		
		List<DictItem> schoolYears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("schoolYears", schoolYears);
		
		model.addAttribute("title",JSON.toJSONString(titils));
		model.addAttribute("data",JSON.toJSONString(nvs));
		model.addAttribute("total", total);
		
		
		return "core/statistics/project/main";
	}
	
	/**
	 * 根据当前用户，配置省市县校 查询条件
	 * @return
	 */
	private ReportProjectQuery configPstsQuery(ReportProjectQuery query) {
		Subject subject = ShiroUtils.getSubject();
		User currentUser = ShiroUtils.getCurrentUser();
		
		PctsQueryDto pcts = new PctsQueryDto();
		
		if(subject.hasRole("SCHOOL_ADMIN")) {  
			pcts.setSchool(currentUser.getSchool());
			pcts.setTown(currentUser.getTown());
			pcts.setCity(currentUser.getCity());
			pcts.setProvince(currentUser.getProvince());
		} else if (subject.hasRole("TOWN_ADMIN")){ 
			pcts.setSchool(0l);
			pcts.setTown(currentUser.getTown());
			pcts.setCity(currentUser.getCity());
			pcts.setProvince(currentUser.getProvince());
			
		} else if (subject.hasRole("CITY_ADMIN")){ 
			pcts.setSchool(0l);
			pcts.setTown(0);
			pcts.setCity(currentUser.getCity());
			pcts.setProvince(currentUser.getProvince());
		} else if (subject.hasRole("PROVINCE_ADMIN")){ 
			pcts.setSchool(0l);
			pcts.setTown(0);
			pcts.setCity(0);
			pcts.setProvince(currentUser.getProvince());
		} else if (subject.hasRole("SYS_ADMIN")){ 
			pcts.setSchool(0l);
			pcts.setTown(0);
			pcts.setCity(0);
			pcts.setProvince(0);
		} else if (subject.hasRole("SUP_ADMIN")){ 
			pcts.setSchool(0l);
			pcts.setTown(0);
			pcts.setCity(0);
			pcts.setProvince(0);
		}
		
		
		if(query.getSchool()==null){
			query.setSchool(pcts.getSchool());
		}else{
			if(query.getSchool()==0){
				query.setSchool(pcts.getSchool());
			}
		}
		
		if(query.getTown()==null){
			query.setTown(pcts.getTown());
		}else{
			if(query.getTown()==0){
				query.setTown(pcts.getTown());
			}
		}
		
		if(query.getCity()==null){
			query.setCity(pcts.getCity());
		}else{
			if(query.getCity()==0){
				query.setCity(pcts.getCity());
			}
		}
		
		if(query.getProvince()==null){
			query.setProvince(pcts.getProvince());
		}else{
			if(query.getProvince()==0){
				query.setProvince(pcts.getProvince());
			}
		}
		
		//用于返回查询后 选中
		query.getQueryProvinces().addAll(provinceService.provinces());
		query.getQueryCitys().addAll(cityService.citys(query.getProvince()));
		query.getQueryTowns().addAll(townService.towns(query.getCity()));
		query.getQuerySchools().addAll(schoolService.schools(query.getTown()));
		
		return query;
	}
	
}
