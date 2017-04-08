package com.yanxiu.ce.system.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.dto.DictItemNodeDto;
import com.yanxiu.ce.system.entity.DictCatlog;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.DictItemQuery;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.zookeeper.server.ZooKeeperServer.ok;

@Controller
@RequestMapping("/system/dict/item")
public class DictItemController {
	
	@Autowired
	private DictCatlogService dictCatlogService;
	@Autowired
	private DictItemService dictItemService;

	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("dictItemList")
	@RequiresPermissions("DictItem:read")
	public String list(DictItemQuery query,Model model){
		query.setOrderField("seq");//默认是按id排

		String catLogId = query.getCatlogId();
		Boolean isTree = false;
		if(StringUtils.isNotBlank(catLogId)){
			DictCatlog dictCatlog = dictCatlogService.selectById(Long.valueOf(query.getCatlogId()));
			if (dictCatlog != null) {
				isTree = dictCatlog.getIsTree();

			}
		}

		//如果是树，加载所有纪录
		if (isTree) {
			query.setPageSize(1000l);
		}
		Pagination<DictItem> page = dictItemService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("catlogId", catLogId);
		if (isTree) {
			return "system/dict/item/dictItemTreeList";
		}else {
			return "system/dict/item/dictItemList";
		}
	}

	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("dictItemForm")
	@RequiresPermissions("DictItem:read")
	public String dictItemFormCatlog(@RequestParam(value="catlogId",required=false) Long catlogId, Model model){
		DictItem entity = new DictItem();
		entity.setCatlogId(catlogId);
		entity.setSeq(dictItemService.selectMaxSeq(catlogId)+1);
		model.addAttribute("entity", entity);
		return "system/dict/item/dictItemForm";
	}

	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("dictItemTreeForm")
	@RequiresPermissions("DictItem:read")
	public String dictItemTreeFormCatlog(@RequestParam(value="catlogId",required=false) Long catlogId, Model model){
		DictItem entity = new DictItem();
		entity.setCatlogId(catlogId);
		entity.setSeq(dictItemService.selectMaxSeq(catlogId)+1);
		model.addAttribute("entity", entity);
		model.addAttribute("catlogId", catlogId);
		return "system/dict/item/dictItemTreeForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="dictItemForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("DictItem:read")
	public String dictItemForm(@PathVariable Long id,Model model){
		DictItem entity = dictItemService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/dict/item/dictItemForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveDictItem",method = RequestMethod.POST)
	@RequiresPermissions(value={"DictItem:update","DictItem:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存字典项")
	public String saveDictItem(DictItem entity){
		AjaxCallback ok = AjaxCallback.OK(dictItemService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteDictItemByIds",method = RequestMethod.POST)
	@RequiresPermissions("DictItem:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除字典项")
	public String deleteDictItemByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		dictItemService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}



	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveDictItemTree/{catlogId}",method = RequestMethod.POST)
	@RequiresPermissions(value={"DictItem:update","DictItem:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存字典项")
	public String saveDictItemTree(String itemsJson,@PathVariable Long catlogId){
		System.out.println(itemsJson);
		List<DictItemNodeDto> dictItemNodeDtos = JSON.parseArray(itemsJson, DictItemNodeDto.class);
		dictItemService.saveDictItemTree(catlogId,dictItemNodeDtos);
		return JSON.toJSONString(ok);
	}

}
