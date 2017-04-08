package com.yanxiu.ce.system.controller;

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
import com.yanxiu.ce.system.entity.UserLog;
import com.yanxiu.ce.system.entity.UserLogQuery;
import com.yanxiu.ce.system.service.UserLogService;

/**
 * 用户操作日志管理
 * @author tangmin
 * @date 2016-07-13 12:05:53
 */
@Controller
@RequestMapping("/system/userlog")
public class UserLogController extends BaseController{
	
	@Autowired
	private UserLogService userLogService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("userLogList")
	@RequiresPermissions("UserLog:read")
	public String list(UserLogQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		Pagination<UserLog> page = userLogService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/userlog/userLogList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("userLogForm")
	@RequiresPermissions("UserLog:create")
	public String userLogForm(Model model){
		UserLog entity = new UserLog();
	//	entity.setSeq(userLogService.selectMaxSeq()+1);
		model.addAttribute("entity", entity);
		return "system/userlog/userLogForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="userLogForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("UserLog:update")
	public String userLogForm(@PathVariable Long id,Model model){
		UserLog entity = userLogService.selectById(id);
		model.addAttribute("entity", entity);
		return "system/userlog/userLogForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveUserLog",method = RequestMethod.POST)
	@RequiresPermissions(value={"UserLog:update","UserLog:create"},logical=Logical.AND)
	@ResponseBody
	public String saveUserLog(UserLog entity){
		AjaxCallback ok = AjaxCallback.OK(userLogService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteUserLogByIds",method = RequestMethod.POST)
	@RequiresPermissions("UserLog:delete")
	@ResponseBody
	public String deleteUserLogByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		userLogService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
