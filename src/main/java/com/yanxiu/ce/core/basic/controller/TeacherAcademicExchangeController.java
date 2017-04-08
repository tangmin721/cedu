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
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicExchange;
import com.yanxiu.ce.core.basic.entity.TeacherAcademicExchangeQuery;
import com.yanxiu.ce.core.basic.entity.TeacherStudyExp;
import com.yanxiu.ce.core.basic.service.TeacherAcademicExchangeService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 学术交流管理
 * @author tangmin
 * @date 2016-08-24 11:23:09
 */
@Controller
@RequestMapping("/core/basic/teacher/academicExchange")
public class TeacherAcademicExchangeController extends BaseController{
	
	@Autowired
	private TeacherAcademicExchangeService teacherAcademicExchangeService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private ScoreService scoreService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherAcademicExchangeList/{tid}")
	@RequiresPermissions("TeacherAcademicExchange:read")
	public String list(@PathVariable Long tid, TeacherAcademicExchangeQuery query,Model model){
		query.setTid(tid.toString());
		query.setTidLike(false);
		List<TeacherAcademicExchange> page = teacherAcademicExchangeService.selectList(query);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> levels = dictCatlogService.getSelectItems("SCORE_LEVEL");
		
		List<TeacherAcademicExchange> academicExchangeListTmp = new ArrayList<TeacherAcademicExchange>();
		//查询学术交流审核通过记录
		ScoreQuery scoreQuery = new ScoreQuery();
		scoreQuery.setTid(tid.toString());
		scoreQuery.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
		List<Score> scoreList = scoreService.selectList(scoreQuery);
		for(Score score : scoreList){
			if (score.getLevel() != null && score.getYear() != null && score.getPnum() != null) {
				TeacherAcademicExchange academicExchangeTmp = new TeacherAcademicExchange();
				academicExchangeTmp.setLevel(score.getLevel());
				academicExchangeTmp.setYear(score.getYear());
				academicExchangeTmp.setExchName(score.getName());
				academicExchangeTmp.setMemo(score.getMemo());
				academicExchangeTmp.setHours(score.getPnum());
				academicExchangeTmp.setTid(tid);
				academicExchangeTmp.setFlag(true);
				academicExchangeListTmp.add(academicExchangeTmp);
			}
		}
		page.addAll(academicExchangeListTmp);
		
		model.addAttribute("years", years);
		model.addAttribute("levels", levels);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		return "core/basic/teacher/academicExchange/teacherAcademicExchangeList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherAcademicExchangeForm")
	@RequiresPermissions("TeacherAcademicExchange:create")
	public String teacherAcademicExchangeAddForm(@RequestParam(value="tid",required=true) Long tid, Model model){
		TeacherAcademicExchange entity = new TeacherAcademicExchange();
		entity.setSeq(teacherAcademicExchangeService.selectMaxSeq(tid)+1);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> levels = dictCatlogService.getSelectItems("SCORE_LEVEL");
		entity.setTid(tid);
		model.addAttribute("years", years);
		model.addAttribute("levels", levels);
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		
		return "core/basic/teacher/academicExchange/teacherAcademicExchangeForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherAcademicExchangeForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherAcademicExchange:update")
	public String teacherAcademicExchangeEditForm(@PathVariable Long id,Model model){
		TeacherAcademicExchange entity = teacherAcademicExchangeService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> levels = dictCatlogService.getSelectItems("SCORE_LEVEL");
		
		model.addAttribute("years", years);
		model.addAttribute("levels", levels);
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		
		return "core/basic/teacher/academicExchange/teacherAcademicExchangeForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherAcademicExchange",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherAcademicExchange:update","TeacherAcademicExchange:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherAcademicExchange(TeacherAcademicExchange entity){
		AjaxCallback ok = AjaxCallback.OK(teacherAcademicExchangeService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherAcademicExchangeByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherAcademicExchange:delete")
	@ResponseBody
	public String deleteTeacherAcademicExchangeByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherAcademicExchangeService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
