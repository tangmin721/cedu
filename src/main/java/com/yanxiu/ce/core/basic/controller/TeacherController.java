package com.yanxiu.ce.core.basic.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.core.basic.dto.TeacherSaveDto;
import com.yanxiu.ce.core.basic.entity.*;
import com.yanxiu.ce.core.basic.enums.TeacherStatusEnum;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.basic.service.*;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.system.entity.*;
import com.yanxiu.ce.system.enums.UserTypeEnum;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师管理
 * @author tangmin
 * @date 2016-03-30 18:03:20
 */
@Controller
@RequestMapping("/core/basic/teacher/base")
public class TeacherController extends BasePctsController<TeacherQuery>{
	
	@Autowired
	private TeacherService teacherService;

	@Value(value = "${FR_URL}")
	private String FR_URL;

	@Value("${JERSEY_FILE_SYS_URL_DOWNLOAD}")
	private String JERSEY_FILE_SYS_URL_DOWNLOAD;

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
	private TeacherStudyExpService teacherStudyExpService;
	
	@Autowired
	private TeacherWorkExpService teacherWorkExpService;
	
	@Autowired
	private TeacherTrainExpService teacherTrainExpService;
	
	@Autowired
	private TeacherAwardService teacherAwardService;
	
	@Autowired
	private TeacherAcademicService teacherAcademicService;
	
	@Autowired
	private TeacherScoreService teacherScoreService;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private TeacherAssessService teacherAssessService;
	
	@Autowired
	private TeacherCredentService teacherCredentService;
	
	@Autowired
	private TeacherPunishService teacherPunishService;
	
	@Autowired
	private TeacherAcademicExchangeService teacherAcademicExchangeService;
	
	@Autowired
	private TeacherSubjectStudyService teacherSubjectStudyService;
	
	@Autowired
	protected ProvinceService provinceService;
	
	@Autowired
	protected CityService cityService;
	
	@Autowired
	protected TownService townService;
	
	@Autowired
	protected SchoolService schoolService;
	
	@Autowired
	private TeacherExcelFileGenerator teacherExcelFileGenerator;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeacherPostEngageService teacherPostEngageService;
	
	@Autowired
	private TeacherProfessionTechService professionTechnicalService;
	
	
	/**
	 * 新增 form向导
	 * @return
	 */
	@RequestMapping("wizardForm")
	public String wizardForm(@RequestParam(value="type",required=false) String type,Model model){
		model.addAttribute("type", type);
		
		Integer schoolType = getCurrentUserSchoolType();
		model.addAttribute("schoolType", schoolType);
		
		return "core/basic/teacher/wizardForm";
	}

	/**
	 * 获取当前登录用户的schoolType
	 * @return
	 */
	private Integer getCurrentUserSchoolType() {
		//新增的时候，默认启用中小学教师的模板
		Integer schoolType = TeacherTypeEnum.MIDDLE_SCHOOL.getValue();
		User currentUser = ShiroUtils.getCurrentUser();
		int userType = currentUser.getType();
		String loginName = currentUser.getLoginName();
		if(userType == UserTypeEnum.SCHOOL_ADMIN.getValue()){
			SchoolQuery query = new SchoolQuery();
			query.setSchoolNo(loginName);
			query.setSchoolNoLike(false);
			List<School> schools = schoolService.selectList(query);
			if(schools !=null && schools.size()>0){
				schoolType = schools.get(0).getSchoolType();
			}
		}
		return schoolType;
	}
	
	/**
	 * 修改 form向导
	 * @return
	 */
	@RequestMapping("wizardForm/{id}")
	public String wizardForm(@PathVariable Long id,@RequestParam(value="type",required=false) String type,Model model){
		model.addAttribute("tid", id);
		model.addAttribute("type", type);
		
		//编辑的时候，默认启用中小学教师的模板
		
		Integer schoolType = 0;
		School school = schoolService.selectById(teacherService.selectById(id).getSchool());
		if(school!=null){
			schoolType = school.getSchoolType();
		}
		model.addAttribute("schoolType", schoolType);
		
		return "core/basic/teacher/wizardForm";
	}
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherList/{type}")
	public String list(@PathVariable String type,TeacherQuery query,Model model){
		query = configPstsQuery(query);//配置省市区县查询条件
		query.setType(type);
		query.setFields("o.id,o.name,o.birthday,p.provincename,c.cityname,t.townname,o.tno,s.name as schoolname,i.name as genderName");
		Pagination<Teacher> page = teacherService.selectListPagination(query);
		
		List<?> teacherStatus = TeacherStatusEnum.toList();
		List<DictItem> nations= dictCatlogService.getSelectItems("NATION");
		List<DictItem> qualifys= dictCatlogService.getSelectItems("QUALIFY_TYPE");
		List<Stage> stages = stageService.selectStages();
		List<DictItem> jobStatuss= dictCatlogService.getSelectItems("JOB_STATUS");
		List<DictItem> titles= dictCatlogService.getSelectItems("TITLE_TYPE");
		List<DictItem> dutys= dictCatlogService.getSelectItems("DUTY_TYPE");
		
		List<Course> courseQuerys = null;
		CourseQuery cQuery = new CourseQuery();
//		if(query!=null&&StringUtils.isNotBlank(query.getStage())){
//			cQuery.setStageId(query.getStage().toString());
//			cQuery.setStageIdLike(false);
//			cQuery.setFields("id,name");
//			courseQuerys = courseService.selectList(cQuery );
//		}
//		
		model.addAttribute("teacherStatus", teacherStatus);
		model.addAttribute("nations", nations);
		model.addAttribute("qualifys", qualifys);
		model.addAttribute("stages", stages);
		model.addAttribute("jobStatuss", jobStatuss);
		model.addAttribute("titles", titles);
		model.addAttribute("courseQuerys", courseQuerys);
		model.addAttribute("dutys", dutys);
		
		List<Course> courses = null;
		CourseQuery csQuery = new CourseQuery();
		csQuery.setFields("id,name");
		courses = courseService.selectList(csQuery );
		model.addAttribute("courses", courses);
		
		model.addAttribute("query", query);
		model.addAttribute("type", type);
		model.addAttribute("page", page);
		return "core/basic/teacher/base/teacherList";
	}
	
	
	/**
	 * 进入教师/校长个人用户页面
	 * @return
	 */
	@RequestMapping("teacherOwner")
	public String teacherOwner(Model model){
		
		User currentUser = ShiroUtils.getCurrentUser();
		Teacher teacher = teacherService.selectByTno(currentUser.getLoginName());
		if(teacher!=null){
			//基本信息查询
//		Teacher entity = teacherService.selectById(id);
//		teacherViewInfo(id, type, model, entity);
			Teacher entity = teacherService.selectViewInfo(teacher.getId());

			//照片url
			String tchImgurl = "";
			if(StringUtils.isNotBlank(entity.getPath())){
				tchImgurl = JERSEY_FILE_SYS_URL_DOWNLOAD + entity.getPath();
			}
			model.addAttribute("tchImgurl",tchImgurl);
			model.addAttribute("entity", entity);
			model.addAttribute("type", 0);
		}


		return "core/basic/teacher/base/teacherView";
	}
	
	/**
	 * 进入报名选择教师的list页面
	 * @return
	 */
	@RequestMapping("teacherSelectList/{type}")
	public String selectList(@PathVariable String type,TeacherQuery query,Model model){
		query = configPstsQuery(query);//配置省市区县查询条件
		query.setType(type);
		Pagination<Teacher> page = teacherService.selectListPagination(query);
		
		List<?> teacherStatus = TeacherStatusEnum.toList();
		List<DictItem> nations= dictCatlogService.getSelectItems("NATION");
		
		
		model.addAttribute("teacherStatus", teacherStatus);
		model.addAttribute("nations", nations);
		
		model.addAttribute("query", query);
		model.addAttribute("type", type);
		model.addAttribute("page", page);
		return "core/basic/teacher/base/teacherSelectList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherForm")
	public String teacherForm(@RequestParam(value="type",required=false) String type,Model model){
		Teacher entity = new Teacher();
		TeacherQuery query = new TeacherQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		
		List<DictItem> genders= dictCatlogService.getSelectItems("GENDER");
		model.addAttribute("genders", genders);
		
		List<DictItem> nationalitys= dictCatlogService.getSelectItems("COUNTRY_TYPE");
		model.addAttribute("nationalitys", nationalitys);
		
		List<DictItem> paperTypes= dictCatlogService.getSelectItems("PAPER_TYPE");
		model.addAttribute("paperTypes", paperTypes);
		
		List<DictItem> nations= dictCatlogService.getSelectItems("NATION");
		model.addAttribute("nations", nations);
		
		List<DictItem> politicTypes= dictCatlogService.getSelectItems("POLITIC_TYPE");
		model.addAttribute("politicTypes", politicTypes);
		
		List<DictItem> healths= dictCatlogService.getSelectItems("HEALTH_CONDITION");
		model.addAttribute("healths", healths);
		
		List<DictItem> isFlags= dictCatlogService.getSelectItems("IS_FLAG");
		model.addAttribute("isFlags", isFlags);
		
		List<DictItem> usePersonTypes= dictCatlogService.getSelectItems("USE_PERSON_TYPE");
		model.addAttribute("usePersonTypes", usePersonTypes);
		
		List<DictItem> signContracts= dictCatlogService.getSelectItems("SIGN_CONTRACT");
		model.addAttribute("signContracts", signContracts);
		
		List<DictItem> computerAbilitys= dictCatlogService.getSelectItems("COMPUTER_ABILITY");
		model.addAttribute("computerAbilitys", computerAbilitys);
		
		List<DictItem> personStatuss= dictCatlogService.getSelectItems("PERSON_STATUS");
		model.addAttribute("personStatuss", personStatuss);
		
		List<DictItem> workDateTimers= dictCatlogService.getSelectItems("WORK_DATE_TIMER");
		model.addAttribute("workDateTimers", workDateTimers);
		
		List<DictItem> isHealths= dictCatlogService.getSelectItems("IS_HEALTH_FLAG");
		model.addAttribute("isHealths", isHealths);
		
		List<DictItem> isMains= dictCatlogService.getSelectItems("IS_MAIN_FLAG");
		model.addAttribute("isMains", isMains);
		
		//tree dict-->
		List<DictItem> marrys= dictCatlogService.getSelectTreeItems("MARRY");
		model.addAttribute("marrys", JSON.toJSONString(marrys));
		
		List<DictItem> workerFrom1s= dictCatlogService.getSelectTreeItems("WORKER_FROM_1");
		model.addAttribute("workerFrom1s", JSON.toJSONString(workerFrom1s));
		
		List<DictItem> workerFrom2s= dictCatlogService.getSelectTreeItems("WORKER_FROM_2");
		model.addAttribute("workerFrom2s", JSON.toJSONString(workerFrom2s));
		
		List<DictItem> workerType1s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_1");
        model.addAttribute("workerType1s", JSON.toJSONString(workerType1s));
        
        List<DictItem> workerType2s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_2");
        model.addAttribute("workerType2s", JSON.toJSONString(workerType2s));
        
        List<DictItem> workerType3s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_3");
        model.addAttribute("workerType3s", JSON.toJSONString(workerType3s));
        
        List<DictItem> workerType4s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_4");
        model.addAttribute("workerType4s", JSON.toJSONString(workerType4s));
        
        List<DictItem> isJoinBases= dictCatlogService.getSelectTreeItems("IS_JOIN_BASE");
        model.addAttribute("isJoinBases", JSON.toJSONString(isJoinBases));
        //<--tree dict
        
		model.addAttribute("provinces", query.getQueryProvinces());
		model.addAttribute("citys", query.getQueryCitys());
		model.addAttribute("towns", query.getQueryTowns());
		model.addAttribute("schools", query.getQuerySchools());
		//为了跟修改的form统一
		entity.setProvince(query.getProvince());
		entity.setCity(query.getCity());
		entity.setTown(query.getTown());
		entity.setSchool(query.getSchool());
//		if(query.getCourse() == null || "".equals(query.getCourse())){
//			query.setCourse("0");
//		}
//		if(query.getGrade() == null || "".equals(query.getGrade())){
//			query.setGrade("0");
//		}
	/*	entity.setCourse(Long.parseLong(query.getCourse()));
		entity.setGrade(Long.parseLong(query.getGrade()));*/
		
		model.addAttribute("entity", entity);
		model.addAttribute("type", type);
		
		return "core/basic/teacher/base/teacherForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherForm/{id}",method = RequestMethod.POST)
	public String teacherForm(@PathVariable Long id,Model model){
		Teacher entity = teacherService.selectById(id);
		
		//下拉框
		List<DictItem> genders= dictCatlogService.getSelectItems("GENDER");
		model.addAttribute("genders", genders);
		
		List<DictItem> nationalitys= dictCatlogService.getSelectItems("COUNTRY_TYPE");
		model.addAttribute("nationalitys", nationalitys);
		
		List<DictItem> paperTypes= dictCatlogService.getSelectItems("PAPER_TYPE");
		model.addAttribute("paperTypes", paperTypes);
		
		List<DictItem> nations= dictCatlogService.getSelectItems("NATION");
		model.addAttribute("nations", nations);
		
		List<DictItem> politicTypes= dictCatlogService.getSelectItems("POLITIC_TYPE");
		model.addAttribute("politicTypes", politicTypes);
		String fundStr = entity.getPoliticType();
		
		if(fundStr != null && !("").equals(fundStr)){
		String[] fundArr = fundStr.split(",");

		for (int i = 0; i < fundArr.length; i++) {
			for (DictItem dict : politicTypes) {
				if(Long.valueOf(fundArr[i]).longValue() == dict.getId().longValue()){
					dict.setChecked(true);
				}
			}
		}
		}
		
		List<DictItem> healths= dictCatlogService.getSelectItems("HEALTH_CONDITION");
		model.addAttribute("healths", healths);
		
		List<DictItem> isFlags= dictCatlogService.getSelectItems("IS_FLAG");
		model.addAttribute("isFlags", isFlags);
		
		List<DictItem> usePersonTypes= dictCatlogService.getSelectItems("USE_PERSON_TYPE");
		model.addAttribute("usePersonTypes", usePersonTypes);
		
		List<DictItem> signContracts= dictCatlogService.getSelectItems("SIGN_CONTRACT");
		model.addAttribute("signContracts", signContracts);
		
		List<DictItem> computerAbilitys= dictCatlogService.getSelectItems("COMPUTER_ABILITY");
		model.addAttribute("computerAbilitys", computerAbilitys);
		
		List<DictItem> personStatuss= dictCatlogService.getSelectItems("PERSON_STATUS");
		model.addAttribute("personStatuss", personStatuss);
		
		List<DictItem> workDateTimers= dictCatlogService.getSelectItems("WORK_DATE_TIMER");
		model.addAttribute("workDateTimers", workDateTimers);
		
		List<DictItem> isHealths= dictCatlogService.getSelectItems("IS_HEALTH_FLAG");
		model.addAttribute("isHealths", isHealths);
		
		List<DictItem> isMains= dictCatlogService.getSelectItems("IS_MAIN_FLAG");
		model.addAttribute("isMains", isMains);
		
		//tree dict
		//tree dict start ----->
		
		List<DictItem> marrys= dictCatlogService.getSelectTreeItems("MARRY");
		String marryName = "";
		if(entity.getMarry()!=null){
			for (DictItem marry : marrys) {
				//单选的情况，多选还需要split
				if (marry.getId().longValue() == entity.getMarry().longValue()){
					marry.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getMarry());
			if (item != null) {
				marryName = item.getName();
			}
		}
		model.addAttribute("marryName", marryName);
		model.addAttribute("marrys", JSON.toJSONString(marrys));
		
		//tree dict end  <--------
		
		 //tree dict-->
		List<DictItem> workerFrom1s= dictCatlogService.getSelectTreeItems("WORKER_FROM_1");
		String workerFrom1Name = "";
		if(entity.getWorkerFrom1()!=null){
			for (DictItem item : workerFrom1s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerFrom1().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerFrom1());
			if (item != null) {
				workerFrom1Name = item.getName();
			}
		}
		model.addAttribute("workerFrom1Name", workerFrom1Name);
		model.addAttribute("workerFrom1s", JSON.toJSONString(workerFrom1s));
		
		List<DictItem> workerFrom2s= dictCatlogService.getSelectTreeItems("WORKER_FROM_2");
		String workerFrom2Name = "";
		if(entity.getWorkerFrom2()!=null){
			for (DictItem item : workerFrom2s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerFrom2().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerFrom2());
			if (item != null) {
				workerFrom2Name = item.getName();
			}
		}
		model.addAttribute("workerFrom2Name", workerFrom2Name);
		model.addAttribute("workerFrom2s", JSON.toJSONString(workerFrom2s));
		
		List<DictItem> workerType1s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_1");
        String workerType1Name = "";
		if(entity.getWorkerType1()!=null){
			for (DictItem item : workerType1s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerType1().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerType1());
			if (item != null) {
				workerType1Name = item.getName();
			}
		}
		model.addAttribute("workerType1Name", workerType1Name);
		model.addAttribute("workerType1s", JSON.toJSONString(workerType1s));
        
		
        List<DictItem> workerType2s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_2");
        String workerType2Name = "";
		if(entity.getWorkerType2()!=null){
			for (DictItem item : workerType2s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerType2().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerType2());
			if (item != null) {
				workerType2Name = item.getName();
			}
		}
		model.addAttribute("workerType2Name", workerType2Name);
		model.addAttribute("workerType2s", JSON.toJSONString(workerType2s));
        
        
        List<DictItem> workerType3s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_3");
        String workerType3Name = "";
		if(entity.getWorkerType3()!=null){
			for (DictItem item : workerType3s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerType3().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerType3());
			if (item != null) {
				workerType3Name = item.getName();
			}
		}
		model.addAttribute("workerType3Name", workerType3Name);
		 model.addAttribute("workerType3s", JSON.toJSONString(workerType3s));
        
        
        List<DictItem> workerType4s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_4");
        String workerType4Name = "";
		if(entity.getWorkerType4()!=null){
			for (DictItem item : workerType4s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerType4().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerType4());
			if (item != null) {
				workerType4Name = item.getName();
			}
		}
		model.addAttribute("workerType4Name", workerType4Name);
		model.addAttribute("workerType4s", JSON.toJSONString(workerType4s));
        
        
        List<DictItem> isJoinBases= dictCatlogService.getSelectTreeItems("IS_JOIN_BASE");
        String isJoinBaseName = "";
		if(entity.getIsJoinBase()!=null){
			for (DictItem item : workerType4s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getIsJoinBase().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getIsJoinBase());
			if (item != null) {
				isJoinBaseName = item.getName();
			}
		}
		model.addAttribute("isJoinBaseName", isJoinBaseName);
        model.addAttribute("isJoinBases", JSON.toJSONString(isJoinBases));
        //<--tree dict
		
		//照片url
		String tchImgurl = "";
		if(StringUtils.isNotBlank(entity.getPath())){
			tchImgurl = JERSEY_FILE_SYS_URL_DOWNLOAD + entity.getPath();
		}
		model.addAttribute("tchImgurl",tchImgurl);
		
		
		//编辑页面，选中下拉框
		List<Province> provinces = provinceService.provinces();
		List<City> citys = cityService.citys(entity.getProvince());
		List<Town> towns = townService.towns(entity.getCity());
		List<School> schools = schoolService.schools(entity.getTown().intValue());
		
		model.addAttribute("provinces", provinces);
		model.addAttribute("citys", citys);
		model.addAttribute("towns", towns);
		model.addAttribute("schools", schools);
		
		model.addAttribute("entity", entity);
		model.addAttribute("type", entity.getType());
		
		
		return "core/basic/teacher/base/teacherForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacher",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "保存教师信息")
	public String saveTeacher(Teacher entity){
		TeacherSaveDto resultDto = teacherService.saveOrUpdate(entity);
		AjaxCallback ok = AjaxCallback.OK(resultDto.getMsg());
		ok.setData(resultDto);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherByIds",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除教师信息")
	public String deleteTeacherByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		//teacherService.deleteByIds(idList);
		teacherService.removeByIds(idList);
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 查看教师信息详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("teacherView/{id}")
	public String teacherView(@PathVariable Long id,@RequestParam(value="type",required=true) String type, Model model){
		//基本信息查询
//		Teacher entity = teacherService.selectById(id);
//		teacherViewInfo(id, type, model, entity);
		Teacher entity = teacherService.selectViewInfo(id);
		
		//获取学习经历信息
		TeacherStudyExpQuery studyExpQuery = new TeacherStudyExpQuery();
		TeacherStudyExp studyExpEntity = new TeacherStudyExp();
		studyExpQuery.setTid(id.toString());
		studyExpQuery.setTidLike(false);
		List<TeacherStudyExp> studyExpList = teacherStudyExpService.selectList(studyExpQuery);
		for(TeacherStudyExp studyExp : studyExpList){
			if(studyExp.getDegree() != null && studyExp.getDegree().longValue() != 0){
				DictItem  degreeDict = dictItemService.selectById(studyExp.getDegree());
				if(studyExpEntity.getDegree() != null){ 
					if(studyExpEntity.getDegree().longValue() >0 && studyExpEntity.getDegree().longValue() > Long.parseLong(degreeDict.getCode())){
						studyExpEntity.setDegreeName(degreeDict.getName());
						studyExpEntity.setUniv(studyExp.getUniv());
						studyExpEntity.setDegree(Long.parseLong(degreeDict.getCode()));
					}else if(studyExpEntity.getDegree().longValue() == 0 && studyExpEntity.getDegree().longValue() < Long.parseLong(degreeDict.getCode())){
						studyExpEntity.setDegreeName(degreeDict.getName());
						studyExpEntity.setUniv(studyExp.getUniv());
						studyExpEntity.setDegree(Long.parseLong(degreeDict.getCode()));
					}
				}else {
					studyExpEntity.setDegreeName(degreeDict.getName());
					studyExpEntity.setUniv(studyExp.getUniv());
					studyExpEntity.setDegree(Long.parseLong(degreeDict.getCode()));
				}
			}
			if(studyExp.getAcademicDegree() != null && studyExp.getAcademicDegree().longValue() != 0){
				DictItem  acaDegreeDict = dictItemService.selectById(studyExp.getAcademicDegree());
				DictItem  acaDegreeNameDict = dictItemService.selectById(studyExp.getAcademicDegreeName());
				
				if(studyExpEntity.getAcademicDegree() != null){
					if(studyExpEntity.getAcademicDegree().longValue() >0 && studyExpEntity.getAcademicDegree().longValue() > Long.parseLong(acaDegreeDict.getCode())){
						studyExpEntity.setAcademicDegreeN(acaDegreeDict.getName());
						studyExpEntity.setAcademicDegreeNameN(acaDegreeNameDict == null ? "" : acaDegreeNameDict.getName());
						studyExpEntity.setAcademicUnit(studyExp.getAcademicUnit());
						studyExpEntity.setAcademicDegree(Long.parseLong(acaDegreeDict.getCode()));
					}else if(studyExpEntity.getAcademicDegree().longValue() == 0 && studyExpEntity.getAcademicDegree().longValue() < Long.parseLong(acaDegreeDict.getCode())){
						studyExpEntity.setAcademicDegreeN(acaDegreeDict.getName());
						studyExpEntity.setAcademicDegreeNameN(acaDegreeNameDict == null ? "" : acaDegreeNameDict.getName());
						studyExpEntity.setAcademicUnit(studyExp.getAcademicUnit());
						studyExpEntity.setAcademicDegree(Long.parseLong(acaDegreeDict.getCode()));
					}
				}else {
					studyExpEntity.setAcademicDegreeN(acaDegreeDict.getName());
					studyExpEntity.setAcademicDegreeNameN(acaDegreeNameDict == null ? "" : acaDegreeNameDict.getName());
					studyExpEntity.setAcademicUnit(studyExp.getAcademicUnit());
					studyExpEntity.setAcademicDegree(Long.parseLong(acaDegreeDict.getCode()));
				}
			}
		}
		
		//获取岗位信息
		TeacherPostEngageQuery postEngageQuery = new TeacherPostEngageQuery();
		TeacherPostEngage postEngageEntity = new TeacherPostEngage();
		postEngageQuery.setTid(id);
		postEngageQuery.setTidLike(false);
		List<TeacherPostEngage> postEngageList = teacherPostEngageService.selectList(postEngageQuery);
		for(TeacherPostEngage postEngage : postEngageList){
			String  postTypeName = dictItemService.selectById(postEngage.getPostType().longValue()).getName();
			String  postLevelName = dictItemService.selectById(postEngage.getPostLevel().longValue()).getName();
			if(postEngageEntity.getStartDate() != null ){
				if(DateUtils.compareDate(postEngage.getStartDate(), postEngageEntity.getStartDate(), Calendar.DATE) > 0){
					postEngageEntity.setPostTypeName(postTypeName);
					postEngageEntity.setPostLevelName(postLevelName);
					postEngageEntity.setStartDate(postEngage.getStartDate());
				}
			}else{
				postEngageEntity.setPostTypeName(postTypeName);
				postEngageEntity.setPostLevelName(postLevelName);
				postEngageEntity.setStartDate(postEngage.getStartDate());
			}
		}
		
		//专业技术聘任职务信息查询 
		TeacherProfessionTechQuery professionTechQuery = new TeacherProfessionTechQuery();
		TeacherProfessionTech professEntity = new TeacherProfessionTech();
		professionTechQuery.setTid(id);
		professionTechQuery.setTidLike(false);
		List<TeacherProfessionTech> professList = professionTechnicalService.selectList(professionTechQuery);
		for(TeacherProfessionTech profess : professList){
			String professName = dictItemService.selectById(profess.getProfessionDuty().longValue()).getName();
			if(profess.getStartDate() != null){
				if(professEntity.getStartDate() != null ){
					if(DateUtils.compareDate(profess.getStartDate(), professEntity.getStartDate(), Calendar.DATE) > 0){
						professEntity.setProfessionDutyName(professName);
						professEntity.setStartDate(profess.getStartDate());
					}
				}else{
					professEntity.setProfessionDutyName(professName);
					professEntity.setStartDate(profess.getStartDate());
				}
			}
		}
		
		//照片url
		String tchImgurl = "";
		if(StringUtils.isNotBlank(entity.getPath())){
			tchImgurl = JERSEY_FILE_SYS_URL_DOWNLOAD + entity.getPath();
		}
		model.addAttribute("tchImgurl",tchImgurl);
		model.addAttribute("entity", entity);
		model.addAttribute("type", type);
		model.addAttribute("studyExpEntity", studyExpEntity);
		model.addAttribute("postEngageEntity", postEngageEntity);
		model.addAttribute("professEntity", professEntity);
		return "core/basic/teacher/base/teacherView";
	}

	/**
	 * 教师信息详情
	 * @param id
	 * @param type
	 * @param model
	 * @param entity
	 */
	private void teacherViewInfo(Long id, String type, Model model,
			Teacher entity) {
		//获取省市县学校名称
		ProvinceQuery provinceQuery = new ProvinceQuery();
		provinceQuery.setProvinceNo(entity.getProvince().toString());
		provinceQuery.setProvinceNoLike(false);
		List<Province> provinceList = provinceService.selectList(provinceQuery);
		entity.setProvinceName(provinceList.get(0).getProvinceName());
		
		CityQuery cityQuery = new CityQuery();
		cityQuery.setCityNo(entity.getCity().toString());
		cityQuery.setCityNoLike(false);
		List<City> cityList = cityService.selectList(cityQuery);
		entity.setCityName(cityList.get(0).getCityName());
		
		TownQuery townQuery = new TownQuery();
		townQuery.setTownNo(entity.getTown().toString());
		townQuery.setTownNoLike(false);
		List<Town> townList = townService.selectList(townQuery);
		entity.setTownName(townList.get(0).getTownName());
		
		School school = schoolService.selectById(entity.getSchool());
		entity.setSchoolName(school.getName());
		if(entity.getNation() != null && dictItemService.selectById(entity.getNation())!=null){
			entity.setNationName(dictItemService.selectById(entity.getNation()).getName());
		}
		
		//照片url
		String tchImgurl = "";
		if(StringUtils.isNotBlank(entity.getPath())){
			tchImgurl = JERSEY_FILE_SYS_URL_DOWNLOAD + entity.getPath();
		}
		model.addAttribute("tchImgurl",tchImgurl);
		
		if(entity.getGender() != null && dictItemService.selectById(entity.getGender())!=null){
			model.addAttribute("genderName",dictItemService.selectById(entity.getGender()).getName());
		}
		
		if(entity.getNationality() != null && dictItemService.selectById(entity.getNationality())!=null){
			model.addAttribute("nationalityName",dictItemService.selectById(entity.getNationality()).getName());
		}
		
		if(entity.getPaperType() != null && dictItemService.selectById(entity.getPaperType())!=null){
			model.addAttribute("paperTypeName",dictItemService.selectById(entity.getPaperType()).getName());
		}
		
		if(entity.getNation() != null && dictItemService.selectById(entity.getNation())!=null){
			model.addAttribute("nationName",dictItemService.selectById(entity.getNation()).getName());
		}
		
		if(entity.getPoliticType() != null){
			List<Long> ids = new ArrayList<Long>();
			String fundStr = entity.getPoliticType();
			String[] fundArr = fundStr.split(",");
			for(int i = 0; i < fundArr.length; i++){
				ids.add(Long.valueOf(fundArr[i]));
			}
			String fund = "";
			List<DictItem> dicts = dictItemService.selectByIds(ids);
			for(DictItem item : dicts){
				fund += item.getName() + ",";
			}		
			model.addAttribute("politicTypeName",fund.substring(0, fund.lastIndexOf(",")));
		}
		
		if(entity.getHealth() != null && dictItemService.selectById(entity.getHealth())!=null){
			model.addAttribute("healthName",dictItemService.selectById(entity.getHealth()).getName());
		}
		
		if(entity.getAtSchool() != null && dictItemService.selectById(entity.getAtSchool())!=null){
			model.addAttribute("atSchoolName",dictItemService.selectById(entity.getAtSchool()).getName());
		}
		
		if(entity.getUsePersonType() != null && dictItemService.selectById(entity.getUsePersonType())!=null){
			model.addAttribute("usePersonTypeName",dictItemService.selectById(entity.getUsePersonType()).getName());
		}
		
		if(entity.getSignContract() != null && dictItemService.selectById(entity.getSignContract())!=null){
			model.addAttribute("signContractName",dictItemService.selectById(entity.getSignContract()).getName());
		}
		
		if(entity.getIsQrz() != null && dictItemService.selectById(entity.getIsQrz())!=null){
			model.addAttribute("isQrzName",dictItemService.selectById(entity.getIsQrz()).getName());
		}
		
		if(entity.getIsTjpx() != null && dictItemService.selectById(entity.getIsTjpx())!=null){
			model.addAttribute("isTjpxName",dictItemService.selectById(entity.getIsTjpx()).getName());
		}
		
		if(entity.getIsTszs() != null && dictItemService.selectById(entity.getIsTszs())!=null){
			model.addAttribute("isTszsName",dictItemService.selectById(entity.getIsTszs()).getName());
		}
		
		if(entity.getComputerAbility() != null && dictItemService.selectById(entity.getComputerAbility())!=null){
			model.addAttribute("computerAbilityName",dictItemService.selectById(entity.getComputerAbility()).getName());
		}
		
		if(entity.getIsMian() != null && dictItemService.selectById(entity.getIsMian())!=null){
			model.addAttribute("isMianName",dictItemService.selectById(entity.getIsMian()).getName());
		}
		
		if(entity.getIsTj() != null && dictItemService.selectById(entity.getIsTj())!=null){
			model.addAttribute("isTjName",dictItemService.selectById(entity.getIsTj()).getName());
		}
		
		if(entity.getIsTownUpBone() != null && dictItemService.selectById(entity.getIsTownUpBone())!=null){
			model.addAttribute("isTownUpBoneName",dictItemService.selectById(entity.getIsTownUpBone()).getName());
		}
		
		if(entity.getPersonStatus() != null && dictItemService.selectById(entity.getPersonStatus())!=null){
			model.addAttribute("personStatusName",dictItemService.selectById(entity.getPersonStatus()).getName());
		}
		
		if(entity.getIsDoubleTch() != null && dictItemService.selectById(entity.getIsDoubleTch())!=null){
			model.addAttribute("isDoubleTchName",dictItemService.selectById(entity.getIsDoubleTch()).getName());
		}
		
		if(entity.getIsProfessCard() != null && dictItemService.selectById(entity.getIsProfessCard())!=null){
			model.addAttribute("isProfessCardName",dictItemService.selectById(entity.getIsProfessCard()).getName());
		}
		
		
		if(entity.getWorkDateTimer() != null && dictItemService.selectById(entity.getWorkDateTimer())!=null){
			model.addAttribute("workDateTimerName",dictItemService.selectById(entity.getWorkDateTimer()).getName());
		}
		
		if(entity.getIsPreTch() != null && dictItemService.selectById(entity.getIsPreTch())!=null){
			model.addAttribute("isPreTchName",dictItemService.selectById(entity.getIsPreTch()).getName());
		}
		
		if(entity.getIsPreTrain() != null && dictItemService.selectById(entity.getIsPreTrain())!=null){
			model.addAttribute("isPreTrainName",dictItemService.selectById(entity.getIsPreTrain()).getName());
		}
		
		//tree dict
		//tree dict start ----->
		
		List<DictItem> marrys= dictCatlogService.getSelectTreeItems("MARRY");
		String marryName = "";
		if(entity.getMarry()!=null){
			for (DictItem marry : marrys) {
				//单选的情况，多选还需要split
				if (marry.getId().longValue() == entity.getMarry().longValue()){
					marry.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getMarry());
			if (item != null) {
				marryName = item.getName();
			}
		}
		model.addAttribute("marryName", marryName);
		model.addAttribute("marrys", JSON.toJSONString(marrys));
		//tree dict end  <--------
		
		 //tree dict-->
		List<DictItem> workerFrom1s= dictCatlogService.getSelectTreeItems("WORKER_FROM_1");
		String workerFrom1Name = "";
		if(entity.getWorkerFrom1()!=null){
			for (DictItem item : workerFrom1s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerFrom1().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerFrom1());
			if (item != null) {
				workerFrom1Name = item.getName();
			}
		}
		model.addAttribute("workerFrom1Name", workerFrom1Name);
		model.addAttribute("workerFrom1s", JSON.toJSONString(workerFrom1s));
		
		List<DictItem> workerFrom2s= dictCatlogService.getSelectTreeItems("WORKER_FROM_2");
		String workerFrom2Name = "";
		if(entity.getWorkerFrom2()!=null){
			for (DictItem item : workerFrom2s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerFrom2().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerFrom2());
			if (item != null) {
				workerFrom2Name = item.getName();
			}
		}
		model.addAttribute("workerFrom2Name", workerFrom2Name);
		model.addAttribute("workerFrom2s", JSON.toJSONString(workerFrom2s));
		
		List<DictItem> workerType1s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_1");
        String workerType1Name = "";
		if(entity.getWorkerType1()!=null){
			for (DictItem item : workerType1s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerType1().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerType1());
			if (item != null) {
				workerType1Name = item.getName();
			}
		}
		model.addAttribute("workerType1Name", workerType1Name);
		model.addAttribute("workerType1s", JSON.toJSONString(workerType1s));
        
		
        List<DictItem> workerType2s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_2");
        String workerType2Name = "";
		if(entity.getWorkerType2()!=null){
			for (DictItem item : workerType2s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerType2().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerType2());
			if (item != null) {
				workerType2Name = item.getName();
			}
		}
		model.addAttribute("workerType2Name", workerType2Name);
		model.addAttribute("workerType2s", JSON.toJSONString(workerType2s));
        
        
        List<DictItem> workerType3s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_3");
        String workerType3Name = "";
		if(entity.getWorkerType3()!=null){
			for (DictItem item : workerType3s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerType3().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerType3());
			if (item != null) {
				workerType3Name = item.getName();
			}
		}
		model.addAttribute("workerType3Name", workerType3Name);
		 model.addAttribute("workerType3s", JSON.toJSONString(workerType3s));
        
        
        List<DictItem> workerType4s= dictCatlogService.getSelectTreeItems("WORKER_TYPE_4");
        String workerType4Name = "";
		if(entity.getWorkerType4()!=null){
			for (DictItem item : workerType4s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getWorkerType4().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getWorkerType4());
			if (item != null) {
				workerType4Name = item.getName();
			}
		}
		model.addAttribute("workerType4Name", workerType4Name);
		model.addAttribute("workerType4s", JSON.toJSONString(workerType4s));
        
        
        List<DictItem> isJoinBases= dictCatlogService.getSelectTreeItems("IS_JOIN_BASE");
        String isJoinBaseName = "";
		if(entity.getIsJoinBase()!=null){
			for (DictItem item : workerType4s) {
				//单选的情况，多选还需要split
				if (item.getId().longValue() == entity.getIsJoinBase().longValue()){
					item.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getIsJoinBase());
			if (item != null) {
				isJoinBaseName = item.getName();
			}
		}
		model.addAttribute("isJoinBaseName", isJoinBaseName);
        model.addAttribute("isJoinBases", JSON.toJSONString(isJoinBases));
        //<--tree dict
		
		
		
		
		
		
		
		
		
		//查询培训审核通过记录
		ScoreQuery scoreQuery = new ScoreQuery();
		scoreQuery.setTid(id.toString());
		scoreQuery.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
		List<Score> scoreList = scoreService.selectList(scoreQuery);
		
		//学习经历查询
		TeacherStudyExpQuery queryStudy = new TeacherStudyExpQuery();
		queryStudy.setTid(id.toString());
		queryStudy.setTidLike(false);
		List<TeacherStudyExp> studyExp = teacherStudyExpService.selectList(queryStudy);
		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
		
		List<TeacherStudyExp> studyExpListTmp = new ArrayList<TeacherStudyExp>();
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
				studyExpListTmp.add(studyExpTmp);
			}
		}
		studyExp.addAll(studyExpListTmp);
		//最高学历标志设置
		List<TeacherStudyExp> studyExps = teacherStudyExpService.getStudyExpDgreeMax(studyExp);
		
		//工作经历查询
		TeacherWorkExpQuery queryWork = new TeacherWorkExpQuery();
		queryWork.setTid(id.toString());
		queryWork.setTidLike(false);
		queryWork.setOrderField("seq");
		List<TeacherWorkExp> workExps = teacherWorkExpService.selectList(queryWork);
		//List<DictItem> dutys= dictCatlogService.getSelectItems("DUTY_TYPE");
		
		//培训经历查询
		TeacherTrainExpQuery queryTran = new TeacherTrainExpQuery();
		queryTran.setTid(id.toString());
		queryTran.setTidLike(false);
		queryTran.setOrderField("seq");
		List<TeacherTrainExp> trainExps = teacherTrainExpService.selectList(queryTran);
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_TYPE");
		
		//查询培训审核通过记录
		List<TeacherTrainExp> teacherTrainTmpList = new ArrayList<TeacherTrainExp>();
		for (Score score : scoreList) {
			Long pid = score.getPid();
			if (pid != null) {
				TeacherTrainExp teacherTrainTmp = new TeacherTrainExp();
				Project project = projectService.selectById(pid);
				teacherTrainTmp.setEndDate(project.getEndDate());
				teacherTrainTmp.setProjectName(project.getName());
				teacherTrainTmp.setStartDate(project.getStartDate());
				teacherTrainTmp.setTid(id);
				teacherTrainTmp.setTrainType(project.getTrainType());
				teacherTrainTmp.setMemo(project.getMemo());
				teacherTrainTmp.setFlag(true);
				teacherTrainTmpList.add(teacherTrainTmp);
			}
		}
		trainExps.addAll(teacherTrainTmpList);
		
		//学术交流信息查询
		TeacherAcademicExchangeQuery queryAcademicExch = new TeacherAcademicExchangeQuery();
		queryAcademicExch.setTid(id.toString());
		queryAcademicExch.setTidLike(false);
		List<TeacherAcademicExchange> academicExchs = teacherAcademicExchangeService.selectList(queryAcademicExch);
		
		List<TeacherAcademicExchange> academicExchangeListTmp = new ArrayList<TeacherAcademicExchange>();
		for(Score score : scoreList){
			if (score.getLevel() != null && score.getYear() != null && score.getPnum() != null) {
				TeacherAcademicExchange academicExchangeTmp = new TeacherAcademicExchange();
				academicExchangeTmp.setLevel(score.getLevel());
				academicExchangeTmp.setYear(score.getYear());
				academicExchangeTmp.setExchName(score.getName());
				academicExchangeTmp.setMemo(score.getMemo());
				academicExchangeTmp.setHours(score.getPnum());
				academicExchangeTmp.setTid(id);
				academicExchangeTmp.setFlag(true);
				academicExchangeListTmp.add(academicExchangeTmp);
			}
		}
		academicExchs.addAll(academicExchangeListTmp);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> scoreLevels = dictCatlogService.getSelectItems("SCORE_LEVEL");
		
		//课题研究信息查询
		TeacherSubjectStudyQuery querySubjectStudy = new TeacherSubjectStudyQuery();
		querySubjectStudy.setTid(id.toString());
		querySubjectStudy.setTidLike(false);
		List<TeacherSubjectStudy> subjectStudys = teacherSubjectStudyService.selectList(querySubjectStudy);	
		List<DictItem> roleTypes = dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		
		//查询课题研究流审核通过记录
		List<TeacherSubjectStudy> subjectStudyListTmp = new ArrayList<TeacherSubjectStudy>();
		for(Score score : scoreList){
			if (score.getLevel() != null && score.getYear() != null && score.getScoreRoleType() != null) {
				TeacherSubjectStudy subjectStudyTmp = new TeacherSubjectStudy();
				subjectStudyTmp.setLevel(score.getLevel());
				subjectStudyTmp.setYear(score.getYear());
				subjectStudyTmp.setName(score.getName());
				subjectStudyTmp.setMemo(score.getMemo());
				subjectStudyTmp.setRoleType(Integer.parseInt(score.getScoreRoleType().toString()));
				subjectStudyTmp.setTid(id);
				subjectStudyTmp.setFlag(true);
				subjectStudyListTmp.add(subjectStudyTmp);
			}
		}
		subjectStudys.addAll(subjectStudyListTmp);
			
		//获奖情况查询
		TeacherAwardQuery queryAward = new TeacherAwardQuery();
		queryAward.setTid(id.toString());
		queryAward.setTidLike(false);
		List<TeacherAward> awards = teacherAwardService.selectList(queryAward);

		//论文/专著
		TeacherAcademicQuery queryAcademic = new TeacherAcademicQuery();
		queryAcademic.setTid(id.toString());
		queryAcademic.setTidLike(false);
		List<TeacherAcademic> academics = teacherAcademicService.selectList(queryAcademic);
		
		List<TeacherAcademic> teacherAcademicTmpList = new ArrayList<TeacherAcademic>();
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
				teacherAcademicTmp.setTid(id);
				teacherAcademicTmp.setZsize(Long.parseLong(score.getZsize().toString()));
				teacherAcademicTmpList.add(teacherAcademicTmp);
			}
		}
		academics.addAll(teacherAcademicTmpList);
		
		//学时学分
		ScoreQuery queryScore = new ScoreQuery();
		queryScore.setTid(id.toString());
		queryScore.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
		List<Score> scores = scoreService.selectList(queryScore);
		List<DictItem> scoreYears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		//考核情况
		TeacherAssessQuery queryAssess = new TeacherAssessQuery();
		queryAssess.setTid(id.toString());
		queryAssess.setTidLike(false);
		List<TeacherAssess> assess = teacherAssessService.selectList(queryAssess);
		List<DictItem> cyears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> checkResults= dictCatlogService.getSelectItems("CHECK_RESULT");

		//证书管理
		TeacherCredentQuery queryCredent = new TeacherCredentQuery();
		queryCredent.setOrderField("seq");//默认是按id排
		queryCredent.setTid(id.toString());
		queryCredent.setTidLike(false);
		List<TeacherCredent> credents = teacherCredentService.selectList(queryCredent);
		List<DictItem> credentsTypes= dictCatlogService.getSelectItems("CREDENT_TYPE");
		
		//处分情况
		TeacherPunishQuery queryPunish = new TeacherPunishQuery();
		queryPunish.setTid(id.toString());
		queryPunish.setTidLike(false);
		List<TeacherPunish> punishs = teacherPunishService.selectList(queryPunish);
		List<DictItem> levels= dictCatlogService.getSelectItems("PUNISH_LEVEL");
			
		model.addAttribute("entity", entity);
		model.addAttribute("studyExps", studyExps);
		model.addAttribute("degrees", degrees);
		model.addAttribute("workExps", workExps);
		//model.addAttribute("dutys", dutys);
		model.addAttribute("trainExps", trainExps);
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("awards", awards);
		model.addAttribute("academics", academics);
		model.addAttribute("roleTypes", roleTypes);
		model.addAttribute("scores", scores);
		model.addAttribute("scoreYears", scoreYears);
		model.addAttribute("assess", assess);
		model.addAttribute("cyears", cyears);
		model.addAttribute("checkResults", checkResults);
		model.addAttribute("credents", credents);
		model.addAttribute("credentsTypes", credentsTypes);
		model.addAttribute("punishs", punishs);
		model.addAttribute("levels", levels);
		model.addAttribute("academicExchs", academicExchs);
		model.addAttribute("years", years);
		model.addAttribute("scoreLevels", scoreLevels);
		model.addAttribute("subjectStudys", subjectStudys);
		model.addAttribute("type", type);
	}

	
	/***********************************excel导入导出**********************************************/
	/**
	 * 进入导出向导的dialog
	 * @return
	 */
	@RequestMapping("teacherImport/{type}")
	public String teacherImport(@PathVariable String type, Model model){
		Integer schoolType = -1; 
		User currentUser = ShiroUtils.getCurrentUser();
		int userType = currentUser.getType();
		String loginName = currentUser.getLoginName();
		if(userType == UserTypeEnum.SCHOOL_ADMIN.getValue()){
			User user = userService.selectByLoginName(loginName);
			Long schoolId = user.getSchool();
			
			School school = schoolService.selectById(schoolId);
			if(school != null){
				schoolType = school.getSchoolType();
			}
		}
		model.addAttribute("schoolType", schoolType);
		model.addAttribute("type", type);
		return "core/basic/teacher/base/teacherImport";
	}
	
	/**
	 * 验证excel
	 * 
	 * @param file
	 * @return
     * @throws Exception 
	 */
	@RequestMapping(value = "checkExcel/{type}", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkExcel(@RequestParam(value="file",required=false) MultipartFile file, @PathVariable String type, @RequestParam(value="schoolType",required=true) String schoolType) {
		logger.info("importExcel::");
		
		String msg = "";
		AjaxCallback ok = AjaxCallback.OK(msg);
		
		TeacherQuery query = new TeacherQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		query.setType(type);
		query.setSchoolType(schoolType);
		
		int schoolTypeInt = Integer.parseInt(schoolType); 
		
		List<String> errorList = Lists.newArrayList();
		if (schoolTypeInt == TeacherTypeEnum.MIDDLE_SCHOOL.getValue()
				|| schoolTypeInt == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()
				|| schoolTypeInt == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()
				|| schoolTypeInt == TeacherTypeEnum.KINDERGARTEN.getValue()) {
			
			errorList = teacherService.checkExcel(file, query);
		} else {
			errorList.add("温馨提示：只有【学校管理员】有批量导入教师信息功能！");
		}
		if(errorList.size()>0){
			msg = "验证失败！请对比错误信息修改Excel文件数据！";
			ok.setMessage(msg);
			ok.setStatusCode(AjaxCallback.CHECK_FAILD);
		}else{
			msg = "验证成功！";
			ok.setMessage(msg);
		}
		
		ok.setData(errorList);
	   // Thread.sleep(5000);模拟延时
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 导入excel
	 * 
	 * @param file
	 * @return
	 * @throws ParseException 
     * @throws Exception 
	 */
	@RequestMapping(value = "importExcel/{type}", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "批量导入教师信息")
	public String importExcel(@PathVariable String type,@RequestParam(value="file",required=false) MultipartFile file, @RequestParam(value="schoolType",required=true) String schoolType) throws ParseException {
		logger.info("importExcel::schoolType="+schoolType);
		
		TeacherQuery query = new TeacherQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		query.setType(type);
		query.setSchoolType(schoolType);
		AjaxCallback ok = AjaxCallback.OK("成功导入【"+teacherService.importExcel(file,query)+"】条信息！");
	   // Thread.sleep(5000);模拟延时
		
		return JSON.toJSONString(ok);
	}
	
	/**
     * 导出全部
     * @return
     * @throws Exception
     */
    @RequestMapping("exportAll/{type}")
    @SystemControllerLog(description = "导出全部")
    public void exportAll(@PathVariable String type,HttpServletResponse response) throws Exception{
        //excel的标题数据（只有一条）
    	List<String> fieldName = null;
    	String excelName = "";
    	
    	Integer schoolType = -1; 
		User currentUser = ShiroUtils.getCurrentUser();
		int userType = currentUser.getType();
		String loginName = currentUser.getLoginName();
		if(userType == UserTypeEnum.SCHOOL_ADMIN.getValue()){
			User user = userService.selectByLoginName(loginName);
			Long schoolId = user.getSchool();
			
			School school = schoolService.selectById(schoolId);
			if(school != null){
				schoolType = school.getSchoolType();
			}
		}
    	
//    	if(type.equals("0")){
		fieldName = teacherService.getExcelFieldName(schoolType);
		excelName = "教师信息表";
//    	}else{
//    		fieldName = teacherService.getMasterExcelFieldName();
//    		excelName = "校长信息表";
//    	}
        //excel的内容数据（多条）
        TeacherQuery query = new TeacherQuery();
        query.setType(type);
		query = configPstsQuery(query);//配置省市区县查询条件
		query.setSchoolType(schoolType.toString());
		
		List<Map<String, String>> sercherNameList = new ArrayList<Map<String, String>>();
		Map<String, String> sercherNameMap = new HashMap<String, String>();
		sercherNameMap.put("姓名",query.getName());
		if(query.getNation() != null && !("").equals(query.getNation())){
			sercherNameMap.put("民族",dictItemService.selectById(Long.parseLong(query.getNation())).getName());
		}else{
			sercherNameMap.put("民族", "");
		}
		sercherNameMap.put("身份证",query.getIdCard());
		sercherNameMap.put("标识码",query.getTno());
		
		teacherExcelFileGenerator.excelExportDownload(response, fieldName, query, excelName, sercherNameMap);
		
//        List<List<String>> fieldDatas = teacherService.getExcelDatasByQuery(query);
//        ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas,excelName);
    }
    
    /**
     * 根据条件导出
     * @return
     * @throws Exception
     */
    @RequestMapping("exportSearch/{type}")
    @SystemControllerLog(description = "根据条件导出")
    public void exportSearch(@PathVariable String type,TeacherQuery query,HttpServletResponse response) throws Exception{
        //excel的标题数据（只有一条）
    	List<String> fieldName = null;
    	String excelName = "";
    	
    	Integer schoolType = -1; 
		User currentUser = ShiroUtils.getCurrentUser();
		int userType = currentUser.getType();
		String loginName = currentUser.getLoginName();
		if(userType == UserTypeEnum.SCHOOL_ADMIN.getValue()){
			User user = userService.selectByLoginName(loginName);
			Long schoolId = user.getSchool();
			
			School school = schoolService.selectById(schoolId);
			if(school != null){
				schoolType = school.getSchoolType();
			}
		}
    	
//    	if(type.equals("0")){
		fieldName = teacherService.getExcelFieldName(schoolType);
		excelName = "教师信息表";
//    	}else{
//    		fieldName = teacherService.getMasterExcelFieldName();
//    		excelName = "校长信息表";
//    	}
    	
		List<Map<String, String>> sercherNameList = new ArrayList<Map<String, String>>();
		Map<String, String> sercherNameMap = new HashMap<String, String>();
        //excel的内容数据（多条）
		query = configPstsQuery(query);//配置省市区县查询条件
		
		//url docode
		query.setType(type);
		query.setSchoolType(schoolType.toString());
		query.setName(java.net.URLDecoder.decode(query.getName(), "UTF-8"));
		sercherNameMap.put("姓名",query.getName());
//		if(query.getSex() != null){
//			query.setSex(java.net.URLDecoder.decode(query.getSex(), "UTF-8"));
//		}
//		if(query.getStartBirthday() != null){
//			query.setStartBirthday(query.getStartBirthday());
//		}
//		if(query.getEndBirthday() != null){
//			query.setEndBirthday(query.getEndBirthday());
//		}
		query.setNation(java.net.URLDecoder.decode(query.getNation(), "UTF-8"));
		if(query.getNation() != null && !("").equals(query.getNation())){
			sercherNameMap.put("民族",dictItemService.selectById(Long.parseLong(query.getNation())).getName());
		}else{
			sercherNameMap.put("民族", "");
		}
//		query.setTel(java.net.URLDecoder.decode(query.getTel(), "UTF-8"));
		query.setIdCard(java.net.URLDecoder.decode(query.getIdCard(), "UTF-8"));
		sercherNameMap.put("身份证",query.getIdCard());
//		query.setMobile(java.net.URLDecoder.decode(query.getMobile(), "UTF-8"));
//		query.setEmail(java.net.URLDecoder.decode(query.getEmail(), "UTF-8"));
		query.setTno(java.net.URLDecoder.decode(query.getTno(), "UTF-8"));
		sercherNameMap.put("标识码",query.getTno());
//		query.setQualify(java.net.URLDecoder.decode(query.getQualify(), "UTF-8"));
//		query.setUniv(java.net.URLDecoder.decode(query.getUniv(), "UTF-8"));
//		query.setMajor(java.net.URLDecoder.decode(query.getMajor(), "UTF-8"));
//		query.setStage(java.net.URLDecoder.decode(query.getStage(), "UTF-8"));
//		query.setJobStatus(java.net.URLDecoder.decode(query.getJobStatus(), "UTF-8"));
		
//        List<List<String>> fieldDatas = teacherService.getExcelDatasByQuery(query);
//        ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas,excelName);
		teacherExcelFileGenerator.excelExportDownload(response, fieldName, query, excelName, sercherNameMap);
    }
    
}
