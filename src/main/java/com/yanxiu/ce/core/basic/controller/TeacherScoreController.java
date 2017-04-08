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
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherScore;
import com.yanxiu.ce.core.basic.entity.TeacherScoreQuery;
import com.yanxiu.ce.core.basic.service.TeacherScoreService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.enums.ScoreTypeEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 学时学分管理
 * @author tangmin
 * @date 2016-05-11 15:11:39
 */
@Controller
@RequestMapping("/core/basic/teacher/score")
public class TeacherScoreController extends BaseController{
	
	@Autowired
	private TeacherScoreService teacherScoreService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherScoreList")
	@RequiresPermissions("TeacherScore:read")
	public String list(@RequestParam(value="tid",required=true) Long tid,ScoreQuery query,Model model){		
		query.setTid(tid.toString());
		query.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
		Pagination<Score> page = scoreService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
			
		List<?> scoreStatus = ScoreStatusEnum.toList();
		model.addAttribute("scoreStatus", scoreStatus);
			
		List<?> scoreTypes = ScoreTypeEnum.toList();
		model.addAttribute("scoreTypes", scoreTypes);
			
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		return "core/basic/teacher/score/teacherScoreList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherScoreForm")
	@RequiresPermissions("TeacherScore:create")
	public String teacherScoreForm(@RequestParam(value="tid",required=true) Long tid,@RequestParam(value="pid",required=true) Long pid,Model model){
		TeacherScore entity = new TeacherScore();
		
		//一个项目一个老师只能是一次学分获取
		TeacherScoreQuery query = new TeacherScoreQuery();
	    query.setPid(pid.toString());
	    query.setPidLike(false);
	    query.setTid(tid.toString());
	    query.setTidLike(false);
	    List<TeacherScore> scoreList = teacherScoreService.selectList(query);
	    if(scoreList.size()>0){
	    	entity = scoreList.get(0);
	    }
		
		entity.setPid(pid);
		entity.setTid(tid);
		
		//设置默认自项目的学时学分
		Project project = projectService.selectById(pid);
//		entity.setScore(project.getScore());
		entity.setHour(project.getHour());
		
		model.addAttribute("entity", entity);
		return "core/basic/teacher/score/teacherScoreForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherScoreForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherScore:update")
	public String teacherScoreForm(@PathVariable Long id,Model model){
		TeacherScore entity = teacherScoreService.selectById(id);
		model.addAttribute("entity", entity);
		return "core/basic/teacher/score/teacherScoreForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherScore",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherScore:update","TeacherScore:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存学分")
	public String saveTeacherScore(TeacherScore entity){
		AjaxCallback ok = AjaxCallback.OK(teacherScoreService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherScoreByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherScore:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除学分")
	public String deleteTeacherScoreByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherScoreService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
