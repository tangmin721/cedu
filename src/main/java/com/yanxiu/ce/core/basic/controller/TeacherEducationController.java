package com.yanxiu.ce.core.basic.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.yanxiu.ce.core.basic.entity.TeacherEducation;
import com.yanxiu.ce.core.basic.entity.TeacherEducationQuery;
import com.yanxiu.ce.core.basic.service.TeacherEducationService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 教育教学管理
 * @author tangmin
 * @date 2016-12-23 16:30:32
 */
@Controller
@RequestMapping("/core/basic/teacher/teacherEducation")
public class TeacherEducationController extends BaseController{
	
	@Autowired
	private TeacherEducationService teacherEducationService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherEducationList/{tid}/{tfs_type}")
	@RequiresPermissions("TeacherEducation:read")
	public String list(@PathVariable Long tid, @PathVariable Integer tfs_type, TeacherEducationQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		query.setTid(tid);
		Pagination<TeacherEducation> page = teacherEducationService.selectListPagination(query);
		
		//学期
		List<DictItem> terms = dictCatlogService.getSelectItems("TERM_TYPE");
				
		//任课状况
		List<DictItem> tchStatuss = dictCatlogService.getSelectItems("TCH_STATUS");
				
		//任教课程
		for (TeacherEducation tchEdu : page.getList()) {
			//任教学段
			List<Long> ids = new ArrayList<Long>();
			String segmentStr = tchEdu.getTchSegment();
			if (segmentStr != null && !("").equals(segmentStr)) {
				String[] segmentArr = segmentStr.split(",");
				for (int i = 0; i < segmentArr.length; i++) {
					ids.add(Long.valueOf(segmentArr[i]));
				}
				String segmentNmaes = "";
				List<DictItem> dicts = dictItemService.selectByIds(ids);
				for (DictItem item : dicts) {
					segmentNmaes += item.getName() + ",";
				}
				tchEdu.setTchSegment(segmentNmaes.substring(0,segmentNmaes.lastIndexOf(",")));
			}else{
				tchEdu.setTchSegment("");
			}
			
			//任教课程
			String courseTypeStr = null;
			if(tfs_type.intValue() == 0){
				courseTypeStr = tchEdu.getTchCourseType();
			}else if(tfs_type.intValue() == 2){
				courseTypeStr = tchEdu.getSpecialTchCourseType();
			}
			
			String tchCourseTypeName = "";
			List<Long> courseIds = new ArrayList<Long>();
			if (courseTypeStr != null && !("").equals(courseTypeStr)) {
				// 单选的情况，多选还需要split
				String[] courseTypeArr = courseTypeStr.split(",");
				for (int i = 0; i < courseTypeArr.length; i++) {
					courseIds.add(Long.valueOf(courseTypeArr[i]));
				}
				List<DictItem> items = dictItemService.selectByIds(courseIds);
				for (DictItem item : items) {
					tchCourseTypeName += item.getName() + ",";
				}
				tchCourseTypeName = tchCourseTypeName.substring(0,tchCourseTypeName.lastIndexOf(","));
				tchEdu.setTchCourseType(tchCourseTypeName);
			} else {
				tchEdu.setTchCourseType(tchCourseTypeName);
			}
		}
				 
		//任课课程类别
		List<DictItem> secondaryCourseTypes = dictCatlogService.getSelectItems("SECONDARY_COURSE_TYPE");
				
		//任课学科类别
		List<DictItem> secondarySubjectTypes = dictCatlogService.getSelectItems("SECONDARY_SUBJECT_TYPE");
				
		//承担的其他工作
		List<DictItem> otherJobTypes = dictCatlogService.getSelectItems("OTHER_JOB_TYPE");
				
		//兼任工作
		List<DictItem> partTimeJobs = dictCatlogService.getSelectItems("PART_TIME_JOB");
		
		//学年
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("terms", terms);
		model.addAttribute("tchStatuss", tchStatuss);
		model.addAttribute("secondaryCourseTypes", secondaryCourseTypes);
		model.addAttribute("secondarySubjectTypes", secondarySubjectTypes);
		model.addAttribute("otherJobTypes", otherJobTypes);
		model.addAttribute("partTimeJobs", partTimeJobs);
		model.addAttribute("years", years);
		model.addAttribute("tfs_type", tfs_type);
		if(tfs_type.intValue() == 3){//幼儿园无需填写此项内容
			return "core/basic/teacher/teacherPrompt";
		}else{
			return "core/basic/teacher/teacherEducation/teacherEducationList";
		}
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherEducationForm")
	@RequiresPermissions("TeacherEducation:create")
	public String teacherEducationFormAdd(@RequestParam(value="tid", required=true) Long tid, @RequestParam(value="tfs_type", required=true) Integer tfs_type, Model model){
		TeacherEducation entity = new TeacherEducation();
		entity.setSeq(teacherEducationService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		//学期
		List<DictItem> terms = dictCatlogService.getSelectItems("TERM_TYPE");
		
		//任教学段
		List<DictItem> tchSegments = dictCatlogService.getSelectItems("TCH_SEGMENT_TYPE");
		
		//任课状况
		List<DictItem> tchStatuss = dictCatlogService.getSelectItems("TCH_STATUS");
		
		//任教课程
		List<DictItem> tchCourseTypes = null;
		List<DictItem> specialTchCourseTypes = null;
		if(tfs_type.intValue() == 0){
			tchCourseTypes = dictCatlogService.getSelectTreeItems("TCH_COURSE_TYPE");
		}else if(tfs_type.intValue() == 2){
			specialTchCourseTypes = dictCatlogService.getSelectItems("TCH_SPECIAL_COURSE_TYPE");
		}
		//任课课程类别
		List<DictItem> secondaryCourseTypes = dictCatlogService.getSelectItems("SECONDARY_COURSE_TYPE");
		
		//任课学科类别
		List<DictItem> secondarySubjectTypes = dictCatlogService.getSelectTreeItems("SECONDARY_SUBJECT_TYPE");
		
		//承担的其他工作
		List<DictItem> otherJobTypes = dictCatlogService.getSelectItems("OTHER_JOB_TYPE");
		
		//兼任工作
		List<DictItem> partTimeJobs = dictCatlogService.getSelectItems("PART_TIME_JOB");
		
		//学年
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy");
		Date time = new Date();
		String currentYear = sdf.format(time);
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("terms", terms);
		model.addAttribute("tchSegments", tchSegments);
		model.addAttribute("tchStatuss", tchStatuss);
		model.addAttribute("tchCourseTypes", JSON.toJSONString(tchCourseTypes));
		model.addAttribute("secondaryCourseTypes", secondaryCourseTypes);
		model.addAttribute("secondarySubjectTypes", JSON.toJSONString(secondarySubjectTypes));
		model.addAttribute("otherJobTypes", otherJobTypes);
		model.addAttribute("partTimeJobs", partTimeJobs);
		model.addAttribute("years", years);
		model.addAttribute("tfs_type", tfs_type);
		model.addAttribute("specialTchCourseTypes", specialTchCourseTypes);
		model.addAttribute("currentYear", currentYear);
		return "core/basic/teacher/teacherEducation/teacherEducationForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherEducationForm/{id}/{tfs_type}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherEducation:update")
	public String teacherEducationFormEdit(@PathVariable Long id, @PathVariable Integer tfs_type,Model model){
		TeacherEducation entity = teacherEducationService.selectById(id);
		
		//学期
		List<DictItem> terms = dictCatlogService.getSelectItems("TERM_TYPE");
				
		//任教学段
		String segmentStr = entity.getTchSegment();
		List<DictItem> tchSegments = dictCatlogService.getSelectItems("TCH_SEGMENT_TYPE");
		if (segmentStr != null && !("").equals(segmentStr)) {
			String[] segmentArr = segmentStr.split(",");
			for (int i = 0; i < segmentArr.length; i++) {
				for (DictItem dict : tchSegments) {
					if (Long.valueOf(segmentArr[i]).longValue() == dict.getId().longValue()) {
						dict.setChecked(true);
					}
				}
			}
		}
		
		//任课状况
		List<DictItem> tchStatuss = dictCatlogService.getSelectItems("TCH_STATUS");
				
		//任教课程
		String tchCourseTypeName = "";
		List<DictItem> tchCourseTypes = null;
		List<DictItem> specialTchCourseTypes = null;
		if(tfs_type.intValue() == 0){
			tchCourseTypes = dictCatlogService.getSelectTreeItems("TCH_COURSE_TYPE");
			String courseTypeStr = entity.getTchCourseType();
			List<Long> courseIds = new ArrayList<Long>();
			if (courseTypeStr != null && !("").equals(courseTypeStr)) {
				for (DictItem ent : tchCourseTypes) {
					// 单选的情况，多选还需要split
					String[] courseTypeArr = courseTypeStr.split(",");
					for (int i = 0; i < courseTypeArr.length; i++) {
						if (ent.getId().longValue() == Long.valueOf(
								courseTypeArr[i]).longValue()) {
							courseIds.add(Long.valueOf(courseTypeArr[i]));
							ent.setChecked(true);
						}
					}
				}
				List<DictItem> items = dictItemService.selectByIds(courseIds);
				for (DictItem item : items) {
					tchCourseTypeName += item.getName() + ",";
				}
				tchCourseTypeName = tchCourseTypeName.substring(0,tchCourseTypeName.lastIndexOf(","));
			}
		}else if(tfs_type.intValue() == 2){
			specialTchCourseTypes = dictCatlogService.getSelectItems("TCH_SPECIAL_COURSE_TYPE");
		}
				 
		//任课课程类别
		List<DictItem> secondaryCourseTypes = dictCatlogService.getSelectItems("SECONDARY_COURSE_TYPE");
				
		//任课学科类别
		String secondarySubjectTypeName = "";
		List<DictItem> secondarySubjectTypes = dictCatlogService.getSelectTreeItems("SECONDARY_SUBJECT_TYPE");
		if(entity.getSecondarySubjectType() != null && entity.getSecondarySubjectType().longValue() != 0){
			for (DictItem ent : secondarySubjectTypes) {
				//单选的情况，多选还需要split
				if (ent.getId().longValue() == entity.getSecondarySubjectType().longValue()){
					ent.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getSecondarySubjectType());
			if (item != null) {
				secondarySubjectTypeName = item.getName();
			}
		}
		
		//承担的其他工作
		List<DictItem> otherJobTypes = dictCatlogService.getSelectItems("OTHER_JOB_TYPE");
		
		//兼任工作
		List<DictItem> partTimeJobs = dictCatlogService.getSelectItems("PART_TIME_JOB");
		
		//学年
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
				
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("terms", terms);
		model.addAttribute("tchSegments", tchSegments);
		model.addAttribute("tchStatuss", tchStatuss);
		model.addAttribute("tchCourseTypes", JSON.toJSONString(tchCourseTypes));
		model.addAttribute("secondaryCourseTypes", secondaryCourseTypes);
		model.addAttribute("secondarySubjectTypes", JSON.toJSONString(secondarySubjectTypes));
		model.addAttribute("otherJobTypes", otherJobTypes);
		model.addAttribute("partTimeJobs", partTimeJobs);
		model.addAttribute("secondarySubjectTypeName", secondarySubjectTypeName);
		model.addAttribute("tchCourseTypeName", tchCourseTypeName);
		model.addAttribute("years", years);
		model.addAttribute("tfs_type", tfs_type);
		model.addAttribute("specialTchCourseTypes", specialTchCourseTypes);
		return "core/basic/teacher/teacherEducation/teacherEducationForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherEducation",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherEducation:update","TeacherEducation:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherEducation(TeacherEducation entity){
		AjaxCallback ok = AjaxCallback.OK(teacherEducationService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherEducationByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherEducation:delete")
	@ResponseBody
	public String deleteTeacherEducationByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherEducationService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
