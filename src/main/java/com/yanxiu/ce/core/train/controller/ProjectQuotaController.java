package com.yanxiu.ce.core.train.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.train.entity.ProjectQuota;
import com.yanxiu.ce.core.train.entity.ProjectQuotaQuery;
import com.yanxiu.ce.core.train.service.ProjectQuotaService;
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.Town;

/**
 * 名额分配管理
 * @author tangmin
 * @date 2016-04-21 09:51:46
 */
@Controller
@RequestMapping("/core/train/project/quota")
public class ProjectQuotaController extends BasePctsController<ProjectQuotaQuery>{
	
	@Autowired
	private ProjectQuotaService projectQuotaService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("projectQuotaList/{pid}")
	@RequiresPermissions("ProjectQuota:read")
	public String list(@PathVariable Long pid,ProjectQuotaQuery query,Model model){
		query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setPid(pid.toString());
		
		Pagination<ProjectQuota> page = projectQuotaService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("pid", pid);
		return "core/train/project/quota/projectQuotaList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("projectQuotaAddForm/{pid}")
	@RequiresPermissions("ProjectQuota:create")
	public String projectQuotaAddForm(@PathVariable Long pid,Model model){
		
		ProjectQuotaQuery query = new ProjectQuotaQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		
		ProjectQuota entity = new ProjectQuota();
		entity.setSeq(projectQuotaService.selectMaxSeq(pid)+1);
		model.addAttribute("entity", entity);
		model.addAttribute("pid", pid);
		
		model.addAttribute("provinces", query.getQueryProvinces());
		model.addAttribute("citys", query.getQueryCitys());
		model.addAttribute("towns", query.getQueryTowns());
		model.addAttribute("schools", query.getQuerySchools());
		
		//为了跟修改的form统一
		entity.setProvince(query.getProvince());
		entity.setCity(query.getCity());
		entity.setTown(query.getTown());
		entity.setSchool(query.getSchool());
		model.addAttribute("entity", entity);
		
		return "core/train/project/quota/projectQuotaForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="projectQuotaForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("ProjectQuota:update")
	public String projectQuotaForm(@PathVariable Long id,Model model){
		ProjectQuota entity = projectQuotaService.selectById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("pid", entity.getPid());
		
		
		//编辑页面，选中下拉框
		List<Province> provinces = provinceService.provinces();
		List<City> citys = cityService.citys(entity.getProvince());
		List<Town> towns = townService.towns(entity.getCity());
		List<School> schools = schoolService.schools(entity.getTown().intValue());
		
		model.addAttribute("provinces", provinces);
		model.addAttribute("citys", citys);
		model.addAttribute("towns", towns);
		model.addAttribute("schools", schools);
		
		return "core/train/project/quota/projectQuotaForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProjectQuota",method = RequestMethod.POST)
	@RequiresPermissions(value={"ProjectQuota:update","ProjectQuota:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "分配项目名额")
	public String saveProjectQuota(ProjectQuota entity){
		AjaxCallback ok = AjaxCallback.OK(projectQuotaService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProjectQuotaByIds",method = RequestMethod.POST)
	@RequiresPermissions("ProjectQuota:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除项目名额")
	public String deleteProjectQuotaByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectQuotaService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	

}
