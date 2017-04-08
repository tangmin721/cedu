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
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.core.basic.entity.TeacherAcademic;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicQuery;
import com.yanxiu.ce.core.basic.service.TeacherAcademicService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 学术论文管理
 * @author tangmin
 * @date 2016-04-19 11:38:55
 */
@Controller
@RequestMapping("/core/basic/teacher/academic")
public class TeacherAcademicController extends BaseController{
	
	@Autowired
	private TeacherAcademicService teacherAcademicService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private ScoreService scoreService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherAcademicList/{tid}")
	@RequiresPermissions("TeacherAcademic:read")
	public String list(@PathVariable Long tid,TeacherAcademicQuery query,Model model){
		query.setOrderField("seq");//默认是按id排
		query.setTid(tid.toString());
		query.setTidLike(false);
		List<TeacherAcademic> page = teacherAcademicService.selectList(query);
		List<DictItem> roleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		//查询培训审核通过记录
		List<TeacherAcademic> teacherAcademicTmpList = new ArrayList<TeacherAcademic>();
		ScoreQuery scoreQuery = new ScoreQuery();
		scoreQuery.setTid(tid.toString());
		scoreQuery.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
		List<Score> scoreList = scoreService.selectList(scoreQuery);
		for (Score score : scoreList) {
			if (score.getPnum() != null && score.getScoreRoleType() != null && score.getZsize() != null) {
				TeacherAcademic teacherAcademicTmp = new TeacherAcademic();
				teacherAcademicTmp.setFlag(true);
				teacherAcademicTmp.setMemo(score.getMemo());
				teacherAcademicTmp.setName(score.getName());
				teacherAcademicTmp.setPnum(Long.parseLong(score.getPnum().toString()));
				teacherAcademicTmp.setPublishDate(score.getYear());
				teacherAcademicTmp.setPublishSource(score.getCsource());
				teacherAcademicTmp.setRoleType(score.getScoreRoleType().toString());
				teacherAcademicTmp.setTid(tid);
				teacherAcademicTmp.setZsize(Long.parseLong(score.getZsize().toString()));
				teacherAcademicTmpList.add(teacherAcademicTmp);
			}
		}
		page.addAll(teacherAcademicTmpList);

		model.addAttribute("roleTypes", roleTypes);
		model.addAttribute("query", query);
		model.addAttribute("years", years);
		model.addAttribute("page", page);
		model.addAttribute("tid",tid);
		return "core/basic/teacher/academic/teacherAcademicList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherAcademicForm")
	@RequiresPermissions("TeacherAcademic:create")
	public String teacherAcademicAddForm(@RequestParam(value="tid",required=true) Long tid,Model model){
		TeacherAcademic entity = new TeacherAcademic();
		entity.setSeq(teacherAcademicService.selectMaxSeq(tid)+1);
		List<DictItem> roleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		entity.setTid(tid);
		model.addAttribute("entity", entity);
		model.addAttribute("roleTypes", roleTypes);
		model.addAttribute("years", years);
		model.addAttribute("tid", tid);
		return "core/basic/teacher/academic/teacherAcademicForm";
	}
//	/**
//	 * 进入编辑form表单页面
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value="teacherAcademicForm/{tid}")
//	@RequiresPermissions("TeacherAcademic:update")
//	public String teacherAcademicForm(@PathVariable Long tid,Model model){
//		TeacherAcademicQuery query = new TeacherAcademicQuery();
//		query.setTid(tid.toString());
//		query.setTidLike(false);
//		query.setOrderField("seq");
//		
//		List<TeacherAcademic> academics = teacherAcademicService.selectList(query);
//		model.addAttribute("tid", tid);
//		model.addAttribute("academics", academics);
//		return "core/basic/teacher/academic/teacherAcademicForm";
//	}
	
	/**
	 * 进入编辑form表单页面
	 * @return
	 */
	@RequestMapping("teacherAcademicForm/{id}")
	@RequiresPermissions("TeacherAcademic:update")
	public String teacherStudyExpForm(@PathVariable Long id,Model model){
		TeacherAcademic entity = teacherAcademicService.selectById(id);
		
		List<DictItem> roleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		model.addAttribute("roleTypes", roleTypes);
		model.addAttribute("entity", entity);
		model.addAttribute("years", years);
		model.addAttribute("tid", entity.getTid());
		return "core/basic/teacher/academic/teacherAcademicForm";
	}
	
	
//	/**
//	 * 单条 保存方法
//	 * @return
//	 */
//	@RequestMapping(value="saveTeacherAcademic",method = RequestMethod.POST)
//	@RequiresPermissions(value={"TeacherAcademic:update","TeacherAcademic:create"},logical=Logical.AND)
//	@ResponseBody
//	@SystemControllerLog(description = "保存学术论文")
//	public String saveTeacherAcademic(HttpServletRequest request)throws ParseException{
//		String keypre = "academic[0]";
//		TeacherAcademic exp = new TeacherAcademic();
//		confAcademicPro(request, keypre, exp);
//		AjaxCallback ok = AjaxCallback.OK(teacherAcademicService.saveOrUpdate(exp));
//		return JSON.toJSONString(ok);
//	}
	
	/**
	 * 保存方法
	 * @return
	 * @throws IOException  
	 */
	@RequestMapping(value="saveTeacherAcademic",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherAcademic:update","TeacherAcademic:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存论文专著信息")
	public String saveTeacherStudyExp(TeacherAcademic entity) throws IOException{
		AjaxCallback ok = AjaxCallback.OK(teacherAcademicService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 全部 保存方法
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="saveTeacherAcademics",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherAcademic:update","TeacherAcademic:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存学术论文")
	public String saveTeacherAcademics(HttpServletRequest request) throws ParseException{
		List<TeacherAcademic> academics = Lists.newArrayList();
		Enumeration<String> keys = request.getParameterNames();
		Set<String> parmKeys = Sets.newHashSet(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    String[] split = k.split("\\.");
	    	parmKeys.add(split[0]);
		} 
		for(String keypre:parmKeys){
			TeacherAcademic exp = new TeacherAcademic();
			confAcademicPro(request, keypre, exp);
			academics.add(exp);
		}
		AjaxCallback ok = AjaxCallback.OK(teacherAcademicService.saveAcademics(academics));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * set属性
	 * @param request
	 * @param keypre
	 * @param exp
	 * @throws ParseException
	 */
	private void confAcademicPro(HttpServletRequest request, String keypre,
			TeacherAcademic exp) throws ParseException {
		String parmId = request.getParameter(keypre+".id");
		if(StringUtils.isNoneBlank(parmId)){
			exp.setId(Long.parseLong(request.getParameter(keypre+".id")));
		}
		exp.setTid(Long.parseLong(request.getParameter(keypre+".tid")));
		exp.setSeq(Integer.parseInt(request.getParameter(keypre+".seq")));
		exp.setPublishDate(Long.parseLong(request.getParameter(keypre+".publishDate")));
		exp.setName(request.getParameter(keypre+".name"));
		exp.setPnum(Long.parseLong(request.getParameter(keypre+".pnum")));
		exp.setZsize(Long.parseLong(request.getParameter(keypre+".zsize")));
		exp.setPublishSource(request.getParameter(keypre+".publishSource"));
		exp.setMemo(request.getParameter(keypre+".memo"));
	}
	

	/**
	 * 删除
	 * @param
	 * @return
	 */
	@RequestMapping("deleteById/{id}")
	@RequiresPermissions("TeacherAcademic:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除学术论文")
	public String deleteById(@PathVariable Long id){
		teacherAcademicService.deleteById(id);
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherAcademicByIds",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除论文/专著信息")
	public String deleteTeacherAcademicByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherAcademicService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	

}
