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
import com.yanxiu.ce.core.statistics.entity.ReportTrainQuery;
import com.yanxiu.ce.core.statistics.enums.ReportTrainWayEnum;
import com.yanxiu.ce.core.statistics.service.ReportTrainService;
import com.yanxiu.ce.system.dto.PctsQueryDto;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.CityService;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.TownService;


/**
 * 培训人员统计
 * @author tangmin
 * @date 2016年8月25日
 */
@Controller
@RequestMapping("/core/statistics/train")
public class ReportTrainController{
	
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
	private ReportTrainService reportTrainService;
	
	@RequestMapping("main")
	public String main(ReportTrainQuery query,Model model){
		if(StringUtils.isBlank(query.getWay())){
			query.setWay(ReportTrainWayEnum.NUMBERS.getValue()+"");
		}
		this.configPstsQuery(query);
		
		if(query.getStartScore()==null){
			query.setStartScore(0);
		}
		
		if(query.getEndScore()==null){
			query.setEndScore(72);
		}
		
		List<?> ways = ReportTrainWayEnum.toList();
		model.addAttribute("ways", ways);
		model.addAttribute("query", query);
		
		String wayName = "";//副标题
		if(StringUtils.isNotBlank(query.getWay())){
			wayName = ReportTrainWayEnum.getDesc(Integer.parseInt(query.getWay()));
		}else{
			wayName = ReportTrainWayEnum.NUMBERS.getDesc();
		}
		model.addAttribute("wayName",wayName);
		
		String iname = "NAME";
		String tableName = "sys_dict_item";
		String joinOnName = "id";
		String groupByProName = ReportTrainWayEnum.getName(Integer.parseInt(query.getWay()));
		//如果是人数分布
		if(query.getWay().equals(ReportTrainWayEnum.NUMBERS.getValue()+"")){
			if(query.getProvince()==0){
				iname = "provincename";
				tableName="sys_area_province";
				joinOnName = "provinceno";
				groupByProName = "province";
			}
			if(query.getProvince()!=0){
				iname = "cityname";
				tableName="sys_area_city";
				joinOnName = "cityno";
				groupByProName = "city";
			}
			if(query.getCity()!=0){
				iname = "townname";
				tableName="sys_area_town";
				joinOnName = "townno";
				groupByProName = "town";
			}
			if(query.getTown()!=0){
				iname = "name";
				tableName="t_school";
				joinOnName = "id";
				groupByProName = "school";
			}
			if(query.getSchool()!=0){
				iname = "name";
				tableName="t_school";
				joinOnName = "id";
				groupByProName = "school";
			}
			
		}
//		else if(query.getWay().equals(ReportTrainWayEnum.STAGE.getValue()+"")){
//			iname = "name";
//			tableName="t_conf_stage";
//			groupByProName = "stage";
//		}else if(query.getWay().equals(ReportTrainWayEnum.COURSE.getValue()+"")){
//			iname = "name";
//			tableName="t_conf_course";
//			groupByProName = "course";
//		}
		List<NameValue> results = reportTrainService.groupByProperty(iname,groupByProName, tableName,joinOnName,
						query.getProvince(), query.getCity(), query.getTown(), query.getSchool(), query.getStartDate(),query.getEndDate(),query.getStartScore(),query.getEndScore());
		
		
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		//总数
		Integer total = 0;
		for(NameValue nv:results){
			nvs.add(nv);
			titils.add(nv.getName());
			total +=  Integer.parseInt(nv.getValue());
		}
		
		model.addAttribute("title",JSON.toJSONString(titils));
		model.addAttribute("data",JSON.toJSONString(nvs));
		model.addAttribute("total", total);
		
		
		return "core/statistics/train/main";
	}
	
	/**
	 * 根据当前用户，配置省市县校 查询条件
	 * @return
	 */
	private ReportTrainQuery configPstsQuery(ReportTrainQuery query) {
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
