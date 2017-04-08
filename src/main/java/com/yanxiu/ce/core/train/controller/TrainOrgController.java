package com.yanxiu.ce.core.train.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.entity.TrainOrg;
import com.yanxiu.ce.core.train.entity.TrainOrgQuery;
import com.yanxiu.ce.core.train.enums.OrgEnum;
import com.yanxiu.ce.core.train.enums.RegisterEnum;
import com.yanxiu.ce.core.train.service.TrainOrgService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 培训机构管理
 * 
 * @author tangmin
 * @date 2016-04-11 17:38:15
 */
@Controller
@RequestMapping("/core/train/org/base")
public class TrainOrgController extends BasePctsController<TrainOrgQuery>{

	@Autowired
	private TrainOrgService trainOrgService;

	@Autowired
	private DictCatlogService dictCatlogService;

	/**
	 * 进入list页面
	 * 
	 * @return
	 */
	@RequestMapping("trainOrgList")
	@RequiresPermissions("TrainOrg:read")
	public String list(TrainOrgQuery query, Model model) {
		// query.setOrderField("seq");//默认是按id排
		// 可同时多列排序 query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		
		this.configPstsQuery(query);
		//if(StringUtils.isBlank(query.getUserType())){
		//	query.setUserType(ShiroUtils.getCurrentUser().getType().toString());
		//}
		
		Pagination<TrainOrg> page = trainOrgService.selectListPagination(query);

		List<DictItem> orgTypes = dictCatlogService
				.getSelectItems("TRAIN_ORG_TYPE");
		List<DictItem> orgLevels = dictCatlogService
				.getSelectItems("TRAIN_ORG_LEVEL");
		List<?> orgEnums = OrgEnum.toList();

		model.addAttribute("orgTypes", orgTypes);
		model.addAttribute("orgLevels", orgLevels);
		model.addAttribute("orgEnums", orgEnums);

		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/train/org/base/trainOrgList";
	}

	/**
	 * 配置项目培训单位的选择list页面
	 * @return
	 */
	@RequestMapping("trainOrgSelectList/{pid}")
	@RequiresPermissions("TrainOrg:read")
	public String selectList(@PathVariable Long pid,TrainOrgQuery query, Model model) {
		// query.setOrderField("seq");//默认是按id排
		// 可同时多列排序 query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<TrainOrg> page = trainOrgService.selectListPagination(query);

		List<DictItem> orgTypes = dictCatlogService
				.getSelectItems("TRAIN_ORG_TYPE");
		List<DictItem> orgLevels = dictCatlogService
				.getSelectItems("TRAIN_ORG_LEVEL");
		List<?> orgEnums = OrgEnum.toList();
		model.addAttribute("orgTypes", orgTypes);
		model.addAttribute("orgLevels", orgLevels);
		model.addAttribute("orgEnums", orgEnums);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("pid", pid);
		return "core/train/org/base/trainOrgSelectList";
	}

	/**
	 * 进入新增form表单页面
	 * 
	 * @return
	 */
	@RequestMapping("trainOrgForm")
	@RequiresPermissions("TrainOrg:create")
	public String trainOrgForm(Model model) {
		TrainOrg entity = new TrainOrg();

		List<DictItem> orgTypes = dictCatlogService
				.getSelectItems("TRAIN_ORG_TYPE");
		List<DictItem> orgLevels = dictCatlogService
				.getSelectItems("TRAIN_ORG_LEVEL");

		model.addAttribute("entity", entity);
		model.addAttribute("orgTypes", orgTypes);
		model.addAttribute("orgLevels", orgLevels);
		return "core/train/org/base/trainOrgForm";
	}

	/**
	 * 进入编辑form表单页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "trainOrgForm/{id}", method = RequestMethod.POST)
	@RequiresPermissions("TrainOrg:update")
	public String trainOrgForm(@PathVariable Long id, Model model) {
		TrainOrg entity = trainOrgService.selectById(id);
		List<DictItem> orgTypes = dictCatlogService
				.getSelectItems("TRAIN_ORG_TYPE");
		List<DictItem> orgLevels = dictCatlogService
				.getSelectItems("TRAIN_ORG_LEVEL");

		model.addAttribute("orgTypes", orgTypes);
		model.addAttribute("orgLevels", orgLevels);
		model.addAttribute("entity", entity);
		return "core/train/org/base/trainOrgForm";
	}

	/**
	 * 进入view form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "trainOrgView/{id}")
	@RequiresPermissions("TrainOrg:read")
	public String trainOrgView(@PathVariable Long id, Model model) {
		TrainOrg entity = trainOrgService.selectById(id);
		List<DictItem> orgTypes = dictCatlogService
				.getSelectItems("TRAIN_ORG_TYPE");
		List<DictItem> orgLevels = dictCatlogService
				.getSelectItems("TRAIN_ORG_LEVEL");

		model.addAttribute("orgTypes", orgTypes);
		model.addAttribute("orgLevels", orgLevels);
		model.addAttribute("entity", entity);
		return "core/train/org/base/trainOrgView";
	}

	/**
	 * 保存方法
	 * 
	 * @return
	 */
	@RequestMapping(value = "saveTrainOrg", method = RequestMethod.POST)
	@RequiresPermissions(value = { "TrainOrg:update", "TrainOrg:create" }, logical = Logical.AND)
	@ResponseBody
	public String saveTrainOrg(TrainOrg entity) {
		User user = ShiroUtils.getCurrentUser();
		entity.setUserType(user.getType());
		entity.setProvince(user.getProvince());
		entity.setCity(user.getCity());
		entity.setTown(user.getTown());
		entity.setSchool(user.getSchool());
		
		AjaxCallback ok = AjaxCallback.OK(trainOrgService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "deleteTrainOrgByIds", method = RequestMethod.POST)
	@RequiresPermissions("TrainOrg:delete")
	@ResponseBody
	public String deleteTrainOrgByIds(
			@RequestParam(value = "ids", required = true) String ids) {
		List<Long> idList = Lists.newArrayList();

		String[] split = ids.split(",");
		for (String strId : split) {
			idList.add(Long.parseLong(strId));
		}
		trainOrgService.deleteByIds(idList);

		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 培训机构选择下拉框
	 */
	@RequestMapping("trainOrgs")
	@ResponseBody
	public String trainOrgs(){
		return JSON.toJSONString(trainOrgService.selectTrainOrgs());
	}
	

}
