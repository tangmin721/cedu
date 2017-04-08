package com.yanxiu.ce.core.basic.controller;

import java.util.ArrayList;
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
import com.yanxiu.ce.core.basic.entity.TeacherAcademicExchange;
import com.yanxiu.ce.core.basic.entity.TeacherSubjectStudy;
import com.yanxiu.ce.core.basic.entity.TeacherSubjectStudyQuery;
import com.yanxiu.ce.core.basic.service.TeacherSubjectStudyService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 学术交流管理
 * @author tangmin
 * @date 2016-08-24 11:24:29
 */
@Controller
@RequestMapping("/core/basic/teacher/subjectStudy")
public class TeacherSubjectStudyController extends BaseController{
	
	@Autowired
	private TeacherSubjectStudyService teacherSubjectStudyService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private ScoreService scoreService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherSubjectStudyList/{tid}")
	@RequiresPermissions("TeacherSubjectStudy:read")
	public String list(@PathVariable Long tid, TeacherSubjectStudyQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setTid(tid.toString());
		query.setTidLike(false);
		List<TeacherSubjectStudy> page = teacherSubjectStudyService.selectList(query);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> levels = dictCatlogService.getSelectItems("SCORE_LEVEL");
		List<DictItem> roleTypes = dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		
		List<TeacherSubjectStudy> subjectStudyListTmp = new ArrayList<TeacherSubjectStudy>();
		//查询课题研究流审核通过记录
		ScoreQuery scoreQuery = new ScoreQuery();
		scoreQuery.setTid(tid.toString());
		scoreQuery.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
		List<Score> scoreList = scoreService.selectList(scoreQuery);
		for(Score score : scoreList){
			if (score.getLevel() != null && score.getYear() != null && score.getScoreRoleType() != null) {
				TeacherSubjectStudy subjectStudyTmp = new TeacherSubjectStudy();
				subjectStudyTmp.setLevel(score.getLevel());
				subjectStudyTmp.setYear(score.getYear());
				subjectStudyTmp.setName(score.getName());
				subjectStudyTmp.setMemo(score.getMemo());
				subjectStudyTmp.setRoleType(Integer.parseInt(score.getScoreRoleType().toString()));
				subjectStudyTmp.setTid(tid);
				subjectStudyTmp.setFlag(true);
				subjectStudyListTmp.add(subjectStudyTmp);
			}
		}
		page.addAll(subjectStudyListTmp);
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("levels", levels);
		model.addAttribute("years", years);
		model.addAttribute("roleTypes", roleTypes);
		
		return "core/basic/teacher/subjectStudy/teacherSubjectStudyList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherSubjectStudyForm")
	@RequiresPermissions("TeacherSubjectStudy:create")
	public String teacherSubjectStudyAddForm(@RequestParam(value="tid",required=true) Long tid, Model model){
		TeacherSubjectStudy entity = new TeacherSubjectStudy();
		entity.setSeq(teacherSubjectStudyService.selectMaxSeq(tid)+1);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> levels = dictCatlogService.getSelectItems("SCORE_LEVEL");
		List<DictItem> roleTypes = dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		
		entity.setTid(tid);
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("levels", levels);
		model.addAttribute("years", years);
		model.addAttribute("roleTypes", roleTypes);
		
		return "core/basic/teacher/subjectStudy/teacherSubjectStudyForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherSubjectStudyForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherSubjectStudy:update")
	public String teacherSubjectStudyEditForm(@PathVariable Long id,Model model){
		TeacherSubjectStudy entity = teacherSubjectStudyService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> levels = dictCatlogService.getSelectItems("SCORE_LEVEL");
		List<DictItem> roleTypes = dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("levels", levels);
		model.addAttribute("years", years);
		model.addAttribute("roleTypes", roleTypes);
		
		return "core/basic/teacher/subjectStudy/teacherSubjectStudyForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherSubjectStudy",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherSubjectStudy:update","TeacherSubjectStudy:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherSubjectStudy(TeacherSubjectStudy entity){
		AjaxCallback ok = AjaxCallback.OK(teacherSubjectStudyService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherSubjectStudyByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherSubjectStudy:delete")
	@ResponseBody
	public String deleteTeacherSubjectStudyByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherSubjectStudyService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
