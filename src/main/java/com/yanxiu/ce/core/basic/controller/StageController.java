package com.yanxiu.ce.core.basic.controller;

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
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.basic.entity.StageQuery;
import com.yanxiu.ce.core.basic.service.StageService;

/**
 * 学段管理
 * @author tangmin
 * @date 2016-04-01 11:06:21
 */
@Controller
@RequestMapping("/core/basic/conf/stage")
public class StageController {
	
	@Autowired
	private StageService stageService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("stageList")
	@RequiresPermissions("Stage:read")
	public String list(StageQuery query,Model model){
		query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<Stage> page = stageService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/conf/stage/stageList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("stageForm")
	@RequiresPermissions("Stage:create")
	public String stageForm(Model model){
		Stage entity = new Stage();
		entity.setSeq(stageService.selectMaxSeq()+1);
		model.addAttribute("entity", entity);
		return "core/basic/conf/stage/stageForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="stageForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Stage:update")
	public String stageForm(@PathVariable Long id,Model model){
		Stage entity = stageService.selectById(id);
		model.addAttribute("entity", entity);
		return "core/basic/conf/stage/stageForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveStage",method = RequestMethod.POST)
	@RequiresPermissions(value={"Stage:update","Stage:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存学段")
	public String saveStage(Stage entity){
		AjaxCallback ok = AjaxCallback.OK(stageService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteStageByIds",method = RequestMethod.POST)
	@RequiresPermissions("Stage:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除学段")
	public String deleteStageByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		
		//删除同时删除课程和年级的
		stageService.deleteByIdsAndCg(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * stage下拉框
	 */
	@RequestMapping("stages")
	@ResponseBody
	public String stages(){
		return JSON.toJSONString(stageService.selectStages());
	}
	

}
