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
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherMorality;
import com.yanxiu.ce.core.basic.entity.TeacherMoralityQuery;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.TeacherMoralityService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 师德信息管理
 * @author tangmin
 * @date 2016-12-22 17:08:27
 */
@Controller
@RequestMapping("/core/basic/teacher/teacherMorality")
public class TeacherMoralityController extends BaseController{
	
	@Autowired
	private TeacherMoralityService teacherMoralityService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	protected SchoolService schoolService;
	
	/**
	 * 进入list页面+
	 * @return
	 */
	@RequestMapping("teacherMoralityList/{tid}")
	@RequiresPermissions("TeacherMorality:read")
	public String list(@PathVariable Long tid, TeacherMoralityQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		query.setTid(tid);
		Pagination<TeacherMorality> page = teacherMoralityService.selectListPagination(query);
		
		//师德考核结论信息查询
		List<DictItem> assessResults = dictCatlogService.getSelectItems("ASSESS_RESULT");
				
		//荣誉级别信息查询
		List<DictItem> honorLevels = dictCatlogService.getSelectItems("HONOR_LEVEL");	
		
		//获得荣誉称号信息查询
		List<DictItem> honorNames = dictCatlogService.getSelectItems("HONOR_NAME");
				
		//处分类别信息查询
		List<DictItem> punishTypes = dictCatlogService.getSelectItems("PUNISH_TYPE");
		
		//处分原因信息查询
		List<DictItem> punishReasons = dictCatlogService.getSelectItems("PUNISH_REASON");
				
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("assessResults", assessResults);
		model.addAttribute("honorLevels", honorLevels);
		model.addAttribute("honorNames", honorNames);
		model.addAttribute("punishTypes", punishTypes);
		model.addAttribute("punishReasons", punishReasons);
		return "core/basic/teacher/teacherMorality/teacherMoralityList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherMoralityForm")
	@RequiresPermissions("TeacherMorality:create")
	public String teacherMoralityFormAdd(@RequestParam(value="tid",required=true) Long tid, Model model){
		TeacherMorality entity = new TeacherMorality();
		entity.setSeq(teacherMoralityService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		//师德考核结论信息查询
		List<DictItem> assessResults = dictCatlogService.getSelectItems("ASSESS_RESULT");
						
		//荣誉级别信息查询
		List<DictItem> honorLevels = dictCatlogService.getSelectItems("HONOR_LEVEL");	
				
		//获得荣誉称号信息查询
		List<DictItem> honorNames = dictCatlogService.getSelectItems("HONOR_NAME");
						
		//处分类别信息查询
		List<DictItem> punishTypes = dictCatlogService.getSelectItems("PUNISH_TYPE");
				
		//处分原因信息查询
		List<DictItem> punishReasons = dictCatlogService.getSelectItems("PUNISH_REASON");
		
		//获取教师所属学校
		Teacher teacher = teacherService.selectById(entity.getTid());
		School school = schoolService.selectById(teacher.getSchool());
		int schoolType = school.getSchoolType();
				
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("assessResults", assessResults);
		model.addAttribute("honorLevels", honorLevels);
		model.addAttribute("honorNames", honorNames);
		model.addAttribute("punishTypes", punishTypes);
		model.addAttribute("punishReasons", punishReasons);
		model.addAttribute("schoolType", schoolType);
		return "core/basic/teacher/teacherMorality/teacherMoralityForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherMoralityForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherMorality:update")
	public String teacherMoralityFormEdit(@PathVariable Long id,Model model){
		TeacherMorality entity = teacherMoralityService.selectById(id);
		
		//师德考核结论信息查询
		List<DictItem> assessResults = dictCatlogService.getSelectItems("ASSESS_RESULT");
						
		//荣誉级别信息查询
		List<DictItem> honorLevels = dictCatlogService.getSelectItems("HONOR_LEVEL");	
				
		//获得荣誉称号信息查询
		List<DictItem> honorNames = dictCatlogService.getSelectItems("HONOR_NAME");
						
		//处分类别信息查询
		List<DictItem> punishTypes = dictCatlogService.getSelectItems("PUNISH_TYPE");
				
		//处分原因信息查询
		List<DictItem> punishReasons = dictCatlogService.getSelectItems("PUNISH_REASON");
		
		//获取教师所属学校
		Teacher teacher = teacherService.selectById(entity.getTid());
		School school = schoolService.selectById(teacher.getSchool());
		int schoolType = school.getSchoolType();		
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("assessResults", assessResults);
		model.addAttribute("honorLevels", honorLevels);
		model.addAttribute("honorNames", honorNames);
		model.addAttribute("punishTypes", punishTypes);
		model.addAttribute("punishReasons", punishReasons);
		model.addAttribute("schoolType", schoolType);
		return "core/basic/teacher/teacherMorality/teacherMoralityForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherMorality",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherMorality:update","TeacherMorality:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherMorality(TeacherMorality entity){
		AjaxCallback ok = AjaxCallback.OK(teacherMoralityService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherMoralityByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherMorality:delete")
	@ResponseBody
	public String deleteTeacherMoralityByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherMoralityService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
