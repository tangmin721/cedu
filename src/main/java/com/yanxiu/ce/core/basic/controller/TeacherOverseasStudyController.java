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
import com.yanxiu.ce.core.basic.entity.TeacherOverseasStudy;
import com.yanxiu.ce.core.basic.entity.TeacherOverseasStudyQuery;
import com.yanxiu.ce.core.basic.service.TeacherOverseasStudyService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 海外研修(访学)管理
 * @author tangmin
 * @date 2016-12-28 12:31:42
 */
@Controller
@RequestMapping("/core/basic/teacher/overseasStudy")
public class TeacherOverseasStudyController extends BaseController{
	
	@Autowired
	private TeacherOverseasStudyService teacherOverseasStudyService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherOverseasStudyList/{tid}")
	@RequiresPermissions("TeacherOverseasStudy:read")
	public String list(@PathVariable Long tid, TeacherOverseasStudyQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		query.setTid(tid);
		Pagination<TeacherOverseasStudy> page = teacherOverseasStudyService.selectListPagination(query);
		
		//是否有海外研修（访学）经历
		List<DictItem> overseasStudys = dictCatlogService.getSelectItems("OVERSEAS_STUDY_FLAG");
		//国家(地区)
		List<DictItem> nations = dictCatlogService.getSelectItems("COUNTRY_TYPE");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("overseasStudys", overseasStudys);
		model.addAttribute("nations", nations);
		return "core/basic/teacher/overseasStudy/teacherOverseasStudyList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherOverseasStudyForm")
	@RequiresPermissions("TeacherOverseasStudy:create")
	public String teacherOverseasStudyFormAdd(@RequestParam(value="tid", required=true) Long tid, Model model){
		TeacherOverseasStudy entity = new TeacherOverseasStudy();
		entity.setSeq(teacherOverseasStudyService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		//是否有海外研修（访学）经历
		List<DictItem> overseasStudys = dictCatlogService.getSelectItems("OVERSEAS_STUDY_FLAG");
		//国家(地区)
		List<DictItem> nations = dictCatlogService.getSelectItems("COUNTRY_TYPE");
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("overseasStudys", overseasStudys);
		model.addAttribute("nations", nations);
		return "core/basic/teacher/overseasStudy/teacherOverseasStudyForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherOverseasStudyForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherOverseasStudy:update")
	public String teacherOverseasStudyFormEdit(@PathVariable Long id,Model model){
		TeacherOverseasStudy entity = teacherOverseasStudyService.selectById(id);
		
		//是否有海外研修（访学）经历
		List<DictItem> overseasStudys = dictCatlogService.getSelectItems("OVERSEAS_STUDY_FLAG");
		//国家(地区)
		List<DictItem> nations = dictCatlogService.getSelectItems("COUNTRY_TYPE");
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("overseasStudys", overseasStudys);
		model.addAttribute("nations", nations);
		return "core/basic/teacher/overseasStudy/teacherOverseasStudyForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherOverseasStudy",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherOverseasStudy:update","TeacherOverseasStudy:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherOverseasStudy(TeacherOverseasStudy entity){
		AjaxCallback ok = AjaxCallback.OK(teacherOverseasStudyService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherOverseasStudyByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherOverseasStudy:delete")
	@ResponseBody
	public String deleteTeacherOverseasStudyByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherOverseasStudyService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
