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
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.TeacherQualification;
import com.yanxiu.ce.core.basic.entity.TeacherQualificationQuery;
import com.yanxiu.ce.core.basic.service.TeacherQualificationService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 教师资格管理
 * @author tangmin
 * @date 2016-12-20 18:22:10
 */
@Controller
@RequestMapping("/core/basic/teacher/teacherQualification")
public class TeacherQualificationController extends BaseController{
	
	@Autowired
	private TeacherQualificationService teacherQualificationService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherQualificationList/{tid}")
	@RequiresPermissions("TeacherQualification:read")
	public String list(@PathVariable Long tid, TeacherQualificationQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		query.setTid(tid);
		Pagination<TeacherQualification> page = teacherQualificationService.selectListPagination(query);
		
		//教师资格类型信息查询 TCH_QUALIFY_TYPE
		List<DictItem> qualifys = dictCatlogService.getSelectItems("TCH_QUALIFY_TYPE");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("qualifys", qualifys);
		return "core/basic/teacher/teacherQualification/teacherQualificationList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherQualificationForm")
	@RequiresPermissions("TeacherQualification:create")
	public String teacherQualificationFormAdd(@RequestParam(value="tid",required=true) Long tid, Model model){
		TeacherQualification entity = new TeacherQualification();
		entity.setSeq(teacherQualificationService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		//教师资格类型信息查询 TCH_QUALIFY_TYPE
		List<DictItem> qualifys = dictCatlogService.getSelectItems("TCH_QUALIFY_TYPE");
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("qualifys", qualifys);
		return "core/basic/teacher/teacherQualification/teacherQualificationForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherQualificationForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherQualification:update")
	public String teacherQualificationFormEdit(@PathVariable Long id,Model model){
		TeacherQualification entity = teacherQualificationService.selectById(id);
		
		//教师资格类型信息查询 TCH_QUALIFY_TYPE
		List<DictItem> qualifys = dictCatlogService.getSelectItems("TCH_QUALIFY_TYPE");
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("qualifys", qualifys);
		return "core/basic/teacher/teacherQualification/teacherQualificationForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherQualification",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherQualification:update","TeacherQualification:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherQualification(TeacherQualification entity){
		AjaxCallback ok = AjaxCallback.OK(teacherQualificationService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherQualificationByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherQualification:delete")
	@ResponseBody
	public String deleteTeacherQualificationByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherQualificationService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
