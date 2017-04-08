package com.yanxiu.ce.core.score.controller;


import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.Course;
import com.yanxiu.ce.core.basic.entity.CourseQuery;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.basic.enums.TeacherStatusEnum;
import com.yanxiu.ce.core.basic.service.CourseService;
import com.yanxiu.ce.core.basic.service.GradeService;
import com.yanxiu.ce.core.basic.service.StageService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.entity.ScoreTeacher;
import com.yanxiu.ce.core.score.entity.ScoreTeacherQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.enums.ScoreTypeEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.core.score.service.ScoreTeacherService;
import com.yanxiu.ce.core.statistics.entity.SummaryTimeRecord;
import com.yanxiu.ce.core.statistics.enums.SummaryTimeWayEnum;
import com.yanxiu.ce.core.statistics.service.ReportScoreService;
import com.yanxiu.ce.core.statistics.service.SummaryTimeRecordService;
import com.yanxiu.ce.system.entity.Config;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.ConfigService;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/core/score/scoreTeacher")
public class ScoreTeacherController extends BasePctsController<ScoreTeacherQuery>{
	
	@Autowired
	private ScoreTeacherService scoreTeacherService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private DictItemService dictItemService;
	
	@Autowired
	private ReportScoreService reportScoreService;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private TeacherService teacherService;
	
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private SummaryTimeRecordService summaryTimeRecordService;
	
	/**
	 * 区县待审列表(合并)
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions("ScoreTeacher:read")
	public String townJoinCheckScoreList(ScoreTeacherQuery query,Model model){
		query = configPstsQuery(query);//配置省市区县查询条件
		Pagination<ScoreTeacher> page = scoreTeacherService.selectListPagination(query);
		
		List<?> teacherStatus = TeacherStatusEnum.toList();
		List<DictItem> nations= dictCatlogService.getSelectItems("NATION");
		List<DictItem> qualifys= dictCatlogService.getSelectItems("QUALIFY_TYPE");
		List<Stage> stages = stageService.selectStages();
		List<DictItem> jobStatuss= dictCatlogService.getSelectItems("JOB_STATUS");
		List<DictItem> titles= dictCatlogService.getSelectItems("TITLE_TYPE");
		List<DictItem> dutys= dictCatlogService.getSelectItems("DUTY_TYPE");
		List<DictItem> genders = dictCatlogService.getSelectItems("GENDER");
		
		List<Course> courseQuerys = null;
//		CourseQuery cQuery = new CourseQuery();
//		if(query!=null&&query.getStage()!=null){
//			cQuery.setStageId(query.getStage().toString());
//			cQuery.setStageIdLike(false);
//			courseQuerys = courseService.selectList(cQuery );
//		}
//		
		//获取上次记录汇总时间
		String summaryTime = summaryTimeRecordService.getSummaryTimeByName(SummaryTimeWayEnum.scoreSummaryType.getValue());
		model.addAttribute("summaryTime", summaryTime);
		
		model.addAttribute("teacherStatus", teacherStatus);
		model.addAttribute("nations", nations);
		model.addAttribute("qualifys", qualifys);
		model.addAttribute("stages", stages);
		model.addAttribute("jobStatuss", jobStatuss);
		model.addAttribute("titles", titles);
		model.addAttribute("courseQuerys", courseQuerys);
		model.addAttribute("dutys", dutys);
		model.addAttribute("genders", genders);
		
		CourseQuery csQuery = new CourseQuery();
		List<Course> courses = courseService.selectList(csQuery );
		model.addAttribute("courses", courses);
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("type", 0);
		
		return "core/score/teacher/scoreTeacherList";
	}
	
	
	/**
	 * 调用此方法 执行统计每个教师的总学分，保存再teacher表里
	 * @return
	 */
	@RequestMapping("processTotalScore")
	@ResponseBody
	public String process(){
		//汇总时间记录
		SummaryTimeRecord entity = new SummaryTimeRecord();
		entity.setModuleName(SummaryTimeWayEnum.scoreSummaryType.getValue());
		entity.setSummaryTime(new Date());
		summaryTimeRecordService.saveOrUpdate(entity);
		
		reportScoreService.jobTotalScore();
		AjaxCallback ok = AjaxCallback.OK("执行完成！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 进入myScoreList页面
	 * @return
	 */
	@RequestMapping("teacherScore/{tid}")
	@RequiresPermissions("ScoreTeacher:read")
	public String myScoreList(@PathVariable Long tid,
			ScoreQuery query,Model model){
		model.addAttribute("tid", tid);

		model.addAttribute("tname",teacherService.selectById(tid).getName());

		query.setTid(tid.toString());
		query.setStatus(Integer.toString(ScoreStatusEnum.CHECK_END.getValue()));
		Pagination<Score> page = scoreService.selectListPagination(query);
		model.addAttribute("query", query);
		
		//转换学分start
		Config cRait = configService.selectByTheKey("C_RAIT");
		Config ucRait = configService.selectByTheKey("UC_RAIT");
		
		String[] splitCRait = cRait.getTheValue().split(":");
		String[] splitUCRait = ucRait.getTheValue().split(":");
		Integer totalScore = 0;
		Double totalScoreNum = 0.0;
		//总的学时学分
		for(Score score:scoreService.selectList(query)){
			Double sc = score.getScore().doubleValue();
			Double scNum = 0.0;
			if(score.getType()==0){
				scNum = sc * Double.valueOf(splitCRait[1])/Double.valueOf(splitCRait[0]);
			}else{
				scNum = sc * Double.valueOf(splitUCRait[1])/Double.valueOf(splitUCRait[0]);
			}
			BigDecimal   b   =   new   BigDecimal(scNum);  
			Double   result   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			totalScoreNum += result;
			totalScore += score.getScore();
			//score.setScoreNum(result.toString());
		}
		//本页的记录，单条的学分
		for(Score score:page.getList()){
			Double sc = score.getScore().doubleValue();
			Double scNum = 0.0;
			if(score.getType()==0){
				scNum = sc * Double.valueOf(splitCRait[1])/Double.valueOf(splitCRait[0]);
			}else{
				scNum = sc * Double.valueOf(splitUCRait[1])/Double.valueOf(splitUCRait[0]);
			}
			BigDecimal   b   =   new   BigDecimal(scNum);  
			Double   result   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			score.setScoreNum(result.toString());
		}
		//转换学分end
		
		model.addAttribute("page", page);
		
		List<?> scoreStatus = ScoreStatusEnum.toList();
		model.addAttribute("scoreStatus", scoreStatus);
		
		List<?> scoreTypes = ScoreTypeEnum.toList();
		model.addAttribute("scoreTypes", scoreTypes);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		//同步本教师的总学时
		reportScoreService.syncTeacherScore(tid,splitCRait,splitUCRait);
		
		//总学时
		model.addAttribute("totalScore", totalScore);
		
		//总学分
		model.addAttribute("totalScoreNum", totalScoreNum);
		
		return "core/score/teacher/teacherScore";
	}
}
