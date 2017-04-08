package com.yanxiu.ce.common.core.controller;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.system.dto.PctsQueryDto;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.CityService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.TownService;

/**
 * 包含 省市区 学校的 过滤条件的查询类
 * @author tangmin
 * @date 2016-03-25 11:17:37
 */
public class BasePctsController<Q extends BasePctsQuery> extends BaseController{
	
	@Autowired
	protected ProvinceService provinceService;
	
	@Autowired
	protected CityService cityService;
	
	@Autowired
	protected TownService townService;
	
	@Autowired
	protected SchoolService schoolService;
	/**
	 * 根据当前用户，配置省市县校 查询条件
	 * @return
	 */
	protected Q configPstsQuery(Q query) {
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
		}else{
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
	
	/**
	 * 不用判断当前用户省市县权限的查询(项目管理)
	 * @param query
	 * @return
	 */
	protected Q configUnPstsQuery(Q query) {
		PctsQueryDto pcts = new PctsQueryDto();
		
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
	
	/**
	 * 当前用户省市县权限的查询(培训机构管理)  能看上级，不看下级
	 * @param query
	 * @return
	 */
	protected Q configOrgPstsQuery(Q query) {
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
	
	
	
	/**
	 * 根据当前用户，配置省市县校 查询条件  报表统计，精确匹配
	 * @return
	 */
	protected Q configReportPstsQuery(Q query) {
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
			query.setSchool(0l);
		}else{
			if(query.getSchool()==0){
				query.setSchool(pcts.getSchool());
			}
		}
		
		if(query.getTown()==null){
			query.setTown(0);
		}else{
			if(query.getTown()==0){
				query.setTown(pcts.getTown());
			}
		}
		
		if(query.getCity()==null){
			query.setCity(0);
		}else{
			if(query.getCity()==0){
				query.setCity(pcts.getCity());
			}
		}
		
		if(query.getProvince()==null){
			query.setProvince(0);
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
