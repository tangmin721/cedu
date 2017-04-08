package com.yanxiu.ce.core.basic.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.google.common.collect.Sets;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.core.basic.entity.TeacherCredent;
import com.yanxiu.ce.core.basic.entity.TeacherStudyExp;
import com.yanxiu.ce.core.basic.entity.TeacherStudyExpQuery;
import com.yanxiu.ce.core.basic.service.TeacherStudyExpService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 学习经历管理
 * @author tangmin
 * @date 2016-04-03 11:47:53
 */
@Controller
@RequestMapping("/core/basic/teacher/studyExp")
public class TeacherStudyExpController {
	
	@Autowired
	private TeacherStudyExpService teacherStudyExpService;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private DictItemService dictItemService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherStudyExpList/{tid}")
	@RequiresPermissions("TeacherStudyExp:read")
	public String list(@PathVariable Long tid,TeacherStudyExpQuery query,Model model){
		query.setOrderField("seq");//默认是按id排
		query.setTid(tid.toString());
		query.setTidLike(false);
		//Pagination<TeacherStudyExp> page = teacherStudyExpService.selectListPagination(query);
		List<TeacherStudyExp> page = teacherStudyExpService.selectList(query);
				
		/*List<TeacherStudyExp> studyExpListTmp = new ArrayList<TeacherStudyExp>();
		//查询学历提升审核通过记录
		ScoreQuery scoreQuery = new ScoreQuery();
		scoreQuery.setTid(tid.toString());
		scoreQuery.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
		List<Score> scoreList = scoreService.selectList(scoreQuery);
		for(Score score : scoreList){
			if (score.getDegree() != null) {
				TeacherStudyExp studyExpTmp = new TeacherStudyExp();
				studyExpTmp.setDegree(score.getDegree());
				studyExpTmp.setEndDate(score.getEndDate());
				studyExpTmp.setStartDate(score.getStartDate());
				studyExpTmp.setMajor(score.getMajor());
				studyExpTmp.setMemo(score.getMemo());
				studyExpTmp.setTid(score.getTid());
				studyExpTmp.setUniv(score.getUniv());
				studyExpTmp.setDegreeFlag("否");
				studyExpTmp.setFlag(true);
				studyExpListTmp.add(studyExpTmp);
			}
		}
		page.addAll(studyExpListTmp);*/
		
		//最高学历标志设置
		//List<TeacherStudyExp> studyExpList = teacherStudyExpService.getStudyExpDgreeMax(page);
		
		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
		//获得学历的国家（地区）信息
		List<DictItem> degreeCountryTypes = dictCatlogService.getSelectItems("COUNTRY_TYPE");
		//是否师范类专业
		List<DictItem> isNormalColleges = dictCatlogService.getSelectItems("IS_FLAG");
		//学位层次信息
		List<DictItem> academicDegrees = dictCatlogService.getSelectItems("ACADEMIC_DEGREE_TYPE");
		//学位名称信息
		List<DictItem> academicDegreeNames = dictCatlogService.getSelectItems("ACADEMIC_DEGREE_NAME");
		//获得学位的国家（地区）信息
		List<DictItem> academicCountryTypes = degreeCountryTypes;
		//学习方式信息
		List<DictItem> studyModes = dictCatlogService.getSelectItems("STUDY_MODE");
		//在学单位类别信息你
		List<DictItem> studyUnitTypes = dictCatlogService.getSelectItems("STUDY_UNIT_TYPE");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("degrees", degrees);
		model.addAttribute("degreeCountryTypes", degreeCountryTypes);
		model.addAttribute("isNormalColleges", isNormalColleges);
		model.addAttribute("academicDegrees", academicDegrees);
		model.addAttribute("academicDegreeNames", academicDegreeNames);
		model.addAttribute("academicCountryTypes", academicCountryTypes);
		model.addAttribute("studyModes", studyModes);
		model.addAttribute("studyUnitTypes", studyUnitTypes);
		return "core/basic/teacher/studyExp/teacherStudyExpList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherStudyExpForm")
	@RequiresPermissions("TeacherStudyExp:create")
	public String teacherCredentAddForm(@RequestParam(value="tid",required=true) Long tid,Model model){
		TeacherStudyExp entity = new TeacherStudyExp();
		entity.setSeq(teacherStudyExpService.selectMaxSeq(tid)+1);
		//List<DictItem> degreeTypes= dictCatlogService.getSelectItems("DEGREE_TYPE");
		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
		//获得学历的国家（地区）信息
		List<DictItem> degreeCountryTypes = dictCatlogService.getSelectItems("COUNTRY_TYPE");
		//是否师范类专业
		List<DictItem> isNormalColleges = dictCatlogService.getSelectItems("IS_FLAG");
		//学位层次信息
		List<DictItem> academicDegrees = dictCatlogService.getSelectItems("ACADEMIC_DEGREE_TYPE");
		//学位名称信息
		List<DictItem> academicDegreeNames = dictCatlogService.getSelectTreeItems("ACADEMIC_DEGREE_NAME");
		//获得学位的国家（地区）信息
		List<DictItem> academicCountryTypes = degreeCountryTypes;
		//学习方式信息
		List<DictItem> studyModes = dictCatlogService.getSelectItems("STUDY_MODE");
		//在学单位类别信息你
		List<DictItem> studyUnitTypes = dictCatlogService.getSelectTreeItems("STUDY_UNIT_TYPE");
		
		
		entity.setTid(tid);
		
		model.addAttribute("entity", entity);
		model.addAttribute("degrees", degrees);
		model.addAttribute("tid", tid);
		model.addAttribute("degreeCountryTypes", degreeCountryTypes);
		model.addAttribute("isNormalColleges", isNormalColleges);
		model.addAttribute("academicDegrees", academicDegrees);
		model.addAttribute("academicDegreeNames", JSON.toJSONString(academicDegreeNames));
		model.addAttribute("academicCountryTypes", academicCountryTypes);
		model.addAttribute("studyModes", studyModes);
		model.addAttribute("studyUnitTypes", JSON.toJSONString(studyUnitTypes));
		return "core/basic/teacher/studyExp/teacherStudyExpForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @return
	 */
//	@RequestMapping("teacherStudyExpForm/{tid}")
//	@RequiresPermissions("TeacherStudyExp:update")
//	public String teacherStudyExpForm(@PathVariable Long tid,Model model){
//		
//		List<DictItem> degreeTypes= dictCatlogService.getSelectItems("DEGREE_TYPE");
//		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
//		
//		TeacherStudyExpQuery query = new TeacherStudyExpQuery();
//		query.setTid(tid.toString());
//		query.setTidLike(false);
//		query.setOrderField("seq");
//		List<TeacherStudyExp> studyExps = teacherStudyExpService.selectList(query);
//		
//		model.addAttribute("tid", tid);
//		model.addAttribute("degreeTypes", degreeTypes);
//		model.addAttribute("degrees", degrees);
//		model.addAttribute("studyExps", studyExps);
//		return "core/basic/teacher/studyExp/teacherStudyExpForm";
//	}
	
	/**
	 * 进入编辑form表单页面
	 * @return
	 */
	@RequestMapping("teacherStudyExpForm/{id}")
	@RequiresPermissions("TeacherStudyExp:update")
	public String teacherStudyExpForm(@PathVariable Long id,Model model){
		TeacherStudyExp entity = teacherStudyExpService.selectById(id);
		
		//List<DictItem> degreeTypes= dictCatlogService.getSelectItems("DEGREE_TYPE");
		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
		//获得学历的国家（地区）信息
		List<DictItem> degreeCountryTypes = dictCatlogService.getSelectItems("COUNTRY_TYPE");
		//是否师范类专业
		List<DictItem> isNormalColleges = dictCatlogService.getSelectItems("IS_FLAG");
		//学位层次信息
		List<DictItem> academicDegrees = dictCatlogService.getSelectItems("ACADEMIC_DEGREE_TYPE");
		//学位名称信息
		List<DictItem> academicDegreeNames = dictCatlogService.getSelectTreeItems("ACADEMIC_DEGREE_NAME");
		String academicDegreeName = "";
		if(entity.getAcademicDegreeName() != null && entity.getAcademicDegreeName().longValue() != 0){
			for(DictItem dict : academicDegreeNames){
				if(entity.getAcademicDegreeName().longValue() == dict.getId().longValue()){
					dict.setChecked(true);
				}
			}
			academicDegreeName = dictItemService.selectById(entity.getAcademicDegreeName()).getName();
		}
		
		//获得学位的国家（地区）信息
		List<DictItem> academicCountryTypes = degreeCountryTypes;
		//学习方式信息
		List<DictItem> studyModes = dictCatlogService.getSelectItems("STUDY_MODE");
		//在学单位类别信息你
		List<DictItem> studyUnitTypes = dictCatlogService.getSelectTreeItems("STUDY_UNIT_TYPE");
		String studyUnitTypeName = "";
		if(entity.getStudyUnitType() != null && entity.getStudyUnitType().longValue() != 0){
			for(DictItem dict : studyUnitTypes){
				if(entity.getStudyUnitType().longValue() == dict.getId().longValue()){
					dict.setChecked(true);
				}
			}
			studyUnitTypeName = dictItemService.selectById(entity.getStudyUnitType()).getName();
		}
				
//		model.addAttribute("degreeTypes", degreeTypes);
		model.addAttribute("degrees", degrees);
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("degreeCountryTypes", degreeCountryTypes);
		model.addAttribute("isNormalColleges", isNormalColleges);
		model.addAttribute("academicDegrees", academicDegrees);
		model.addAttribute("academicDegreeNames", JSON.toJSONString(academicDegreeNames));
		model.addAttribute("academicCountryTypes", academicCountryTypes);
		model.addAttribute("studyModes", studyModes);
		model.addAttribute("studyUnitTypes", JSON.toJSONString(studyUnitTypes));
		model.addAttribute("academicDegreeName", academicDegreeName);
		model.addAttribute("studyUnitTypeName", studyUnitTypeName);
		return "core/basic/teacher/studyExp/teacherStudyExpForm";
	}
	
	
	/**
	 * 行内编辑 保存方法
	 * @return
	 * @throws ParseException 
	 */
//	@RequestMapping(value="saveTeacherStudyExp",method = RequestMethod.POST)
//	@RequiresPermissions(value={"TeacherStudyExp:update","TeacherStudyExp:create"},logical=Logical.AND)
//	@ResponseBody
//	@SystemControllerLog(description = "保存学习经历")
//	public String saveTeacherStudyExp(HttpServletRequest request) throws ParseException{
//			String keypre = "studyExp[0]";
//			TeacherStudyExp exp = new TeacherStudyExp();
//			confStudyExpPro(request, keypre, exp);
//		AjaxCallback ok = AjaxCallback.OK(teacherStudyExpService.saveOrUpdate(exp));
//		return JSON.toJSONString(ok);
//	}
	
	/**
	 * 保存方法
	 * @return
	 * @throws IOException  
	 */
	@RequestMapping(value="saveTeacherStudyExp",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherStudyExp:update","TeacherStudyExp:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存学习经历")
	public String saveTeacherStudyExp(TeacherStudyExp entity) throws IOException{
		AjaxCallback ok = AjaxCallback.OK(teacherStudyExpService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}

	/**
	 * 全部 保存方法
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="saveTeacherStudyExps",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherStudyExp:update","TeacherStudyExp:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存学习经历")
	public String saveTeacherStudyExps(HttpServletRequest request) throws ParseException{
		List<TeacherStudyExp> studyExps = Lists.newArrayList();
		Enumeration<String> keys = request.getParameterNames();
		Set<String> parmKeys = Sets.newHashSet(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    String[] split = k.split("\\.");
	    	parmKeys.add(split[0]);
		} 
		for(String keypre:parmKeys){
			TeacherStudyExp exp = new TeacherStudyExp();
			confStudyExpPro(request, keypre, exp);
			studyExps.add(exp);
		}
		AjaxCallback ok = AjaxCallback.OK(teacherStudyExpService.saveStudyExps(studyExps));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * set属性
	 * @param request
	 * @param keypre
	 * @param exp
	 * @throws ParseException
	 */
	private void confStudyExpPro(HttpServletRequest request, String keypre,
			TeacherStudyExp exp) throws ParseException {
		String parmId = request.getParameter(keypre+".id");
		if(StringUtils.isNoneBlank(parmId)){
			exp.setId(Long.parseLong(request.getParameter(keypre+".id")));
		}
		exp.setTid(Long.parseLong(request.getParameter(keypre+".tid")));
		exp.setSeq(Integer.parseInt(request.getParameter(keypre+".seq")));
		exp.setStartDate(DateUtils.SHORT_DATE_DASH_FORMAT.parse(request.getParameter(keypre+".startDate")));
		exp.setEndDate(DateUtils.SHORT_DATE_DASH_FORMAT.parse(request.getParameter(keypre+".endDate")));
		exp.setUniv(request.getParameter(keypre+".univ"));
		exp.setDegreeType(Long.parseLong(request.getParameter(keypre+".degreeType")));
		exp.setDegree(Long.parseLong(request.getParameter(keypre+".degree")));
		exp.setMajor(request.getParameter(keypre+".major"));
		exp.setMemo(request.getParameter(keypre+".memo"));
		exp.setDegreeFlag("否");
	}
	
	
	/**
	 * 删除
	 * @param
	 * @return
	 */
	@RequestMapping("deleteById/{id}")
	@RequiresPermissions("TeacherStudyExp:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除学习经历")
	public String deleteById(@PathVariable Long id){
		teacherStudyExpService.deleteById(id);
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherStudyExpByIds",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除学习经历信息")
	public String deleteTeacherStudyExpByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherStudyExpService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	

}
