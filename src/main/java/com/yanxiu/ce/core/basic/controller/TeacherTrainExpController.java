package com.yanxiu.ce.core.basic.controller;

import java.text.ParseException;
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
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExp;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExpQuery;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.basic.service.TeacherTrainExpService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 培训情况管理
 * @author tangmin
 * @date 2016-05-23 17:30:42
 */
@Controller
@RequestMapping("/core/basic/teacher/trainExp")
public class TeacherTrainExpController extends BaseController{
	
	@Autowired
	private TeacherTrainExpService teacherTrainExpService;
	
	@Autowired
	private DictCatlogService dictCatlogService;

	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	protected SchoolService schoolService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherTrainExpList/{tid}")
	@RequiresPermissions("TeacherTrainExp:read")
	public String list(@PathVariable Long tid,TeacherTrainExpQuery query, Model model){

		query.setTid(tid.toString());
		query.setTidLike(false);
		query.setOrderField("seq");
		List<TeacherTrainExp> trainExps = teacherTrainExpService.selectList(query);
		//Pagination<TeacherTrainExp> page = teacherTrainExpService.selectListPagination(query);
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		//培训年度
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
				
		//培训方式
		List<DictItem> trainModes = dictCatlogService.getSelectItems("TRAIN_MODE");

		
		//查询培训审核通过记录
//		List<TeacherTrainExp> teacherTrainTmpList = new ArrayList<TeacherTrainExp>();
//		ScoreQuery scoreQuery = new ScoreQuery();
//		scoreQuery.setTid(tid.toString());
//		scoreQuery.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
//		List<Score> scoreList = scoreService.selectList(scoreQuery);
//		for(Score score : scoreList){
//			Long pid = score.getPid();
//			if(pid != null){
//				TeacherTrainExp teacherTrainTmp =  new TeacherTrainExp();
//				Project project = projectService.selectById(pid);
//				teacherTrainTmp.setEndDate(project.getEndDate());
//				teacherTrainTmp.setProjectName(project.getName());
//				teacherTrainTmp.setStartDate(project.getStartDate());
//				teacherTrainTmp.setTid(tid);
//				teacherTrainTmp.setTrainType(project.getTrainType());
//				teacherTrainTmp.setMemo(project.getMemo());
//				teacherTrainTmp.setFlag(true);
//				teacherTrainTmpList.add(teacherTrainTmp);
//			}
//		}
//		
//		trainExps.addAll(teacherTrainTmpList);
		
		model.addAttribute("query", query);
		model.addAttribute("page", trainExps);
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("tid", tid);
		model.addAttribute("years", years);
		model.addAttribute("trainModes", trainModes);
		return "core/basic/teacher/trainExp/teacherTrainExpList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherTrainExpForm")
	@RequiresPermissions("TeacherTrainExp:create")
	public String teacherTrainAddForm(@RequestParam(value="tid",required=true) Long tid,Model model){
		TeacherTrainExp entity = new TeacherTrainExp();
		entity.setSeq(teacherTrainExpService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		//培训类别
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		
		//培训年度
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		//培训方式
		List<DictItem> trainModes = dictCatlogService.getSelectItems("TRAIN_MODE");

		//获取教师所属学校
		Teacher teacher = teacherService.selectById(entity.getTid());
		School school = schoolService.selectById(teacher.getSchool());
		int schoolType = school.getSchoolType();
		
		model.addAttribute("entity", entity);
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("tid", tid);
		model.addAttribute("years", years);
		model.addAttribute("trainModes", trainModes);
		model.addAttribute("schoolType", schoolType);
		return "core/basic/teacher/trainExp/teacherTrainExpForm";
	}
	
//	/**
//	 * 进入编辑form表单页面
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value="teacherTrainExpForm/{tid}")
//	@RequiresPermissions("TeacherTrainExp:update")
//	public String teacherTrainExpForm(@PathVariable Long tid,Model model){
//		
//		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_LEVEL");
//		
//		TeacherTrainExpQuery query = new TeacherTrainExpQuery();
//		query.setTid(tid.toString());
//		query.setTidLike(false);
//		query.setOrderField("seq");
//		List<TeacherTrainExp> trainExps = teacherTrainExpService.selectList(query);
//		
//		model.addAttribute("tid", tid);
//		model.addAttribute("trainTypes", trainTypes);
//		model.addAttribute("trainExps", trainExps);
//		
//		return "core/basic/teacher/trainExp/teacherTrainExpForm";
//	}
	
	/**
	 * 进入编辑form表单页面
	 * @return
	 */
	@RequestMapping("teacherTrainExpForm/{id}")
	@RequiresPermissions("TeacherTrainExp:update")
	public String teacherTrainExpForm(@PathVariable Long id,Model model){
		TeacherTrainExp entity = teacherTrainExpService.selectById(id);
		
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		//培训年度
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
				
		//培训方式
		List<DictItem> trainModes = dictCatlogService.getSelectItems("TRAIN_MODE");

		//获取教师所属学校
		Teacher teacher = teacherService.selectById(entity.getTid());
		School school = schoolService.selectById(teacher.getSchool());
		int schoolType = school.getSchoolType();
		
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("years", years);
		model.addAttribute("trainModes", trainModes);
		model.addAttribute("schoolType", schoolType);
		return "core/basic/teacher/trainExp/teacherTrainExpForm";
	}
	
	/**
	 * 单条 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherTrainExp",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherTrainExp:update","TeacherTrainExp:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存培训经历")
	public String saveTeacherTrainExp(TeacherTrainExp entity)throws ParseException{
		AjaxCallback ok = AjaxCallback.OK(teacherTrainExpService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 全部 保存方法
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="saveTeacherTrainExps",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherTrainExp:update","TeacherTrainExp:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存培训经历")
	public String saveTeacherTrainExps(HttpServletRequest request) throws ParseException{
		List<TeacherTrainExp> trainExps = Lists.newArrayList();
		Enumeration<String> keys = request.getParameterNames();
		Set<String> parmKeys = Sets.newHashSet(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    String[] split = k.split("\\.");
	    	parmKeys.add(split[0]);
		} 
		for(String keypre:parmKeys){
			TeacherTrainExp exp = new TeacherTrainExp();
			confTrainExpPro(request, keypre, exp);
			trainExps.add(exp);
		}
		AjaxCallback ok = AjaxCallback.OK(teacherTrainExpService.saveTrainExps(trainExps));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * set属性
	 * @param request
	 * @param keypre
	 * @param exp
	 * @throws ParseException
	 */
	private void confTrainExpPro(HttpServletRequest request, String keypre,
			TeacherTrainExp exp) throws ParseException {
		String parmId = request.getParameter(keypre+".id");
		if(StringUtils.isNoneBlank(parmId)){
			exp.setId(Long.parseLong(request.getParameter(keypre+".id")));
		}
		exp.setTid(Long.parseLong(request.getParameter(keypre+".tid")));
		exp.setSeq(Integer.parseInt(request.getParameter(keypre+".seq")));
//		exp.setStartDate(DateUtils.SHORT_DATE_DASH_FORMAT.parse(request.getParameter(keypre+".startDate")));
//		exp.setEndDate(DateUtils.SHORT_DATE_DASH_FORMAT.parse(request.getParameter(keypre+".endDate")));
		exp.setTrainType(Long.parseLong(request.getParameter(keypre+".trainType")));
		exp.setProjectName(request.getParameter(keypre+".projectName")); 
		exp.setMemo(request.getParameter(keypre+".memo"));
	}
	

	/**
	 * 删除
	 * @param
	 * @return
	 */
	@RequestMapping("deleteById/{id}")
	@RequiresPermissions("TeacherTrainExp:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除培训经历")
	public String deleteById(@PathVariable Long id){
		teacherTrainExpService.deleteById(id);
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 删除
	 * @param
	 * @return
	 */
	@RequestMapping("deleteTeacherTrainExpByIds")
	@ResponseBody
	@SystemControllerLog(description = "删除培训经历")
	public String deleteById(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherTrainExpService.deleteByIds(idList);
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}
	

}
