package com.yanxiu.ce.core.mq.controller;

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
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.enums.TeacherStatusEnum;
import com.yanxiu.ce.core.mq.entity.RegisterMsg;
import com.yanxiu.ce.core.mq.entity.RegisterMsgQuery;
import com.yanxiu.ce.core.mq.enums.MsgStatusEnum;
import com.yanxiu.ce.core.mq.enums.MsgTypeEnum;
import com.yanxiu.ce.core.mq.service.RegisterMsgService;

/**
 * 消息管理
 * @author tangmin
 * @date 2016-08-12 14:31:04
 */
@Controller
@RequestMapping("/core/mq")
public class RegisterMsgController extends BaseController{
	
	@Autowired
	private RegisterMsgService registerMsgService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("registerMsgList")
	@RequiresPermissions("RegisterMsg:read")
	public String list(RegisterMsgQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<RegisterMsg> page = registerMsgService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		
		List<?> msgStatuses = MsgStatusEnum.toList();
		List<?> msgTypes = MsgTypeEnum.toList();
		
		model.addAttribute("msgStatuses", msgStatuses);
		model.addAttribute("msgTypes", msgTypes);
		
		return "core/mq/registerMsgList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("registerMsgForm")
	@RequiresPermissions("RegisterMsg:create")
	public String registerMsgForm(Model model){
		RegisterMsg entity = new RegisterMsg();
		model.addAttribute("entity", entity);
		return "core/mq/registerMsgForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="registerMsgForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("RegisterMsg:update")
	public String registerMsgForm(@PathVariable Long id,Model model){
		RegisterMsg entity = registerMsgService.selectById(id);
		model.addAttribute("entity", entity);
		return "core/mq/registerMsgForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveRegisterMsg",method = RequestMethod.POST)
	@RequiresPermissions(value={"RegisterMsg:update","RegisterMsg:create"},logical=Logical.AND)
	@ResponseBody
	public String saveRegisterMsg(RegisterMsg entity){
		AjaxCallback ok = AjaxCallback.OK(registerMsgService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteRegisterMsgByIds",method = RequestMethod.POST)
	@RequiresPermissions("RegisterMsg:delete")
	@ResponseBody
	public String deleteRegisterMsgByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		registerMsgService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

	/**
	 * 调用此类 去执行  执行定时任务
	 */
	@RequestMapping(value="producer",method = RequestMethod.GET)
	@ResponseBody
	public String deleteRegisterMsgByIds(){
		registerMsgService.producer();
		AjaxCallback ok = AjaxCallback.OK("调用成功");
		return JSON.toJSONString(ok);
	}
}
