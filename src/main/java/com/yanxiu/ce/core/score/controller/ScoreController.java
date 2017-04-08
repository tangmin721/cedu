package com.yanxiu.ce.core.score.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.yanxiuapi.PassportHelper;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherAssess;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.score.dto.CheckScoreDto;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreMineDetail;
import com.yanxiu.ce.core.score.entity.ScoreMineDetailQuery;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.enums.ScoreTypeEnum;
import com.yanxiu.ce.core.score.service.ScoreMineDetailService;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.core.statistics.service.ReportScoreService;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.entity.ProjectQuery;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.system.entity.Config;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.enums.UserTypeEnum;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.ConfigService;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 学时申报管理
 * @author tangmin
 * @date 2016-08-01 12:18:52
 */
@Controller
@RequestMapping("/core/score")
public class ScoreController extends BasePctsController<ScoreQuery>{
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ReportScoreService reportScoreService;
	
	@Autowired
	private DictItemService dictItemService;
	
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
    private ScoreMineDetailService scoreMineDetailService;
	
	
	/**
	 * 进入myScoreList页面
	 * @return
	 */
	@RequestMapping("myScoreList")
	@RequiresPermissions("Score:myScoreList")
	public String myScoreList(ScoreQuery query,Model model){
		
		Teacher teacher = getCurrentTeacher();
		model.addAttribute("tid", teacher.getId());
		
		query.setTid(teacher.getId().toString());
		Pagination<Score> page = scoreService.selectListPagination(query);
		traScoreNum(page);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		
		List<?> scoreStatus = ScoreStatusEnum.toList();
		model.addAttribute("scoreStatus", scoreStatus);
		
		List<?> scoreTypes = ScoreTypeEnum.toList();
		model.addAttribute("scoreTypes", scoreTypes);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		return "core/score/myScoreList";
	}

	//学时转换为学分
	private void traScoreNum(Pagination<Score> page) {
		Config cRait = configService.selectByTheKey("C_RAIT");
		Config ucRait = configService.selectByTheKey("UC_RAIT");
		
		String[] splitCRait = cRait.getTheValue().split(":");
		String[] splitUCRait = ucRait.getTheValue().split(":");
		
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
	}
	
	/**
	 * 进入myScoreList页面
	 * @return
	 */
	@RequestMapping("myCheckedScoreList")
	@RequiresPermissions("Score:myScoreList")
	public String myCheckedScoreList(ScoreQuery query,Model model){
		
		Teacher teacher = getCurrentTeacher();
		model.addAttribute("tid", teacher.getId());
		
		query.setStatus(ScoreStatusEnum.CHECK_END.getValue()+"");
		query.setTid(teacher.getId().toString());
		Pagination<Score> page = scoreService.selectListPagination(query);
		traScoreNum(page);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		
		List<?> scoreStatus = ScoreStatusEnum.toList();
		model.addAttribute("scoreStatus", scoreStatus);
		
		List<?> scoreTypes = ScoreTypeEnum.toList();
		model.addAttribute("scoreTypes", scoreTypes);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		
		
		
		return "core/score/myCheckedScoreList";
	}

	/**
	 * 根据登陆教师用户获取teacher实体
	 * @return
	 */
	private Teacher getCurrentTeacher() {
		User currentUser = ShiroUtils.getCurrentUser();
		TeacherQuery tchQuery = new TeacherQuery();
		tchQuery.setIdCard(currentUser.getLoginName());//需求已修改为以身份证登录
		tchQuery.setIdCardLike(false);
		List<Teacher> teachers = teacherService.selectList(tchQuery);
		Teacher teacher = teachers.get(0);
		return teacher;
	}
	
	/**
	 * 认证学时查询
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("queryScoreList")
	@RequiresPermissions("Score:read")
	public String queryScoreList(ScoreQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query = configPstsQuery(query);//配置省市区县查询条件
		User currentUser = ShiroUtils.getCurrentUser();
		query.setSelectJoinFlag(true);
		
		List<Map<String, String>> scoreStatus = Lists.newArrayList();
		
		
		if(currentUser.getType()==UserTypeEnum.PROVINCE_ADMIN.getValue()){
			query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_CHECKING.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_UNPASS.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.CHECK_END.getValue());
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.PROVINCE_CHECKING));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.PROVINCE_UNPASS));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.CHECK_END));
		}else if(currentUser.getType()==UserTypeEnum.TOWN_ADMIN.getValue()){
			query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_CHECKING.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_UNPASS.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.CHECK_END.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.TOWN_CHECKING.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.TOWN_UNPASS.getValue());
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.PROVINCE_CHECKING));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.PROVINCE_UNPASS));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.CHECK_END));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.TOWN_CHECKING));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.TOWN_UNPASS));
		}else if(currentUser.getType()==UserTypeEnum.SCHOOL_ADMIN.getValue()){
			query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_CHECKING.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_UNPASS.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.CHECK_END.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.TOWN_CHECKING.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.TOWN_UNPASS.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.SCHOOL_CHECKING.getValue());
			query.getJoinStatus().add(ScoreStatusEnum.SCHOOL_UNPASS.getValue());
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.PROVINCE_CHECKING));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.PROVINCE_UNPASS));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.CHECK_END));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.TOWN_CHECKING));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.TOWN_UNPASS));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.SCHOOL_CHECKING));
			scoreStatus.add(addSatuseEnum(ScoreStatusEnum.SCHOOL_UNPASS));
		}
		
		Pagination<Score> page = scoreService.selectListPagination(query);
		traScoreNum(page);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		
		
		model.addAttribute("scoreStatus", scoreStatus);
		
		List<?> scoreTypes = ScoreTypeEnum.toList();
		model.addAttribute("scoreTypes", scoreTypes);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		
		
		return "core/score/queryScoreList";
	}
	
	
	private Map<String,String> addSatuseEnum(ScoreStatusEnum status){
		Map<String,String> map = Maps.newHashMap();
		map.put("value", status.getValue()+"");
		map.put("desc", status.getDesc());
		return map;
		
	}
	
	/**
	 * type = 0 待审，1 已审
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("checkScoreList/{pagetype}")
	@RequiresPermissions("Score:check")
	public String list(@PathVariable Integer pagetype,ScoreQuery query,
			@RequestParam(value="pcity",required=false) Integer pcity,
			@RequestParam(value="ptown",required=false) Integer ptown,
			@RequestParam(value="pschool",required=false) Long pschool,
			Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query = configPstsQuery(query);//配置省市区县查询条件
		
		User currentUser = ShiroUtils.getCurrentUser();
		
		if(pagetype==0){  //待审
			Integer theStatus = 0;//根据管理员确定 待审状态是几
			theStatus = getTheStatusByCurrentAdmin(currentUser, theStatus);
			query.setStatus(theStatus.toString());
		}else{  //1  已审
//			
//			SCHOOL_CHECKING("校级待审", 10), 
//			SCHOOL_UNPASS("校级不通过",11),
//			TOWN_CHECKING("区县待审", 20), 
//			TOWN_UNPASS("区县不通过",21),
//			PROVINCE_CHECKING("省级待审", 30), 
//			PROVINCE_UNPASS("省级不通过",31),
//			CHECK_END("学时认证完成", 40);
//			
			query.setSelectJoinFlag(true);
			if(currentUser.getType()==UserTypeEnum.PROVINCE_ADMIN.getValue()){
				query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_CHECKING.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_UNPASS.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.CHECK_END.getValue());
			}else if(currentUser.getType()==UserTypeEnum.TOWN_ADMIN.getValue()){
				query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_CHECKING.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_UNPASS.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.CHECK_END.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.TOWN_CHECKING.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.TOWN_UNPASS.getValue());
			}else if(currentUser.getType()==UserTypeEnum.SCHOOL_ADMIN.getValue()){
				query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_CHECKING.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.PROVINCE_UNPASS.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.CHECK_END.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.TOWN_CHECKING.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.TOWN_UNPASS.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.SCHOOL_CHECKING.getValue());
				query.getJoinStatus().add(ScoreStatusEnum.SCHOOL_UNPASS.getValue());
			}
		}
		
		//配置查询条件
		if(pcity!=null){
			query.setCity(pcity);
		}
		if(ptown!=null){
			query.setTown(ptown);
			query = configPstsQuery(query);//配置省市区县查询条件（再次执行，是为了回显town选中）
		}
		if(pschool!=null){
			query.setSchool(pschool);
		}
		
		Pagination<Score> page = scoreService.selectListPagination(query);
		traScoreNum(page);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		
		
		List<?> scoreStatus = ScoreStatusEnum.toList();
		model.addAttribute("scoreStatus", scoreStatus);
		
		List<?> scoreTypes = ScoreTypeEnum.toList();
		model.addAttribute("scoreTypes", scoreTypes);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		model.addAttribute("pagetype", pagetype);
		
		return "core/score/checkScoreList";
	}

	/**
	 * 根据当前用户，获取需要查询的学分待审状态
	 * @param currentUser
	 * @param theStatus
	 * @return
	 */
	private Integer getTheStatusByCurrentAdmin(User currentUser, Integer theStatus) {
		if(currentUser.getType()== UserTypeEnum.PROVINCE_ADMIN.getValue()){
            theStatus = ScoreStatusEnum.PROVINCE_CHECKING.getValue();
        }else if(currentUser.getType()==UserTypeEnum.TOWN_ADMIN.getValue()){
            theStatus = ScoreStatusEnum.TOWN_CHECKING.getValue();
        }else if(currentUser.getType()==UserTypeEnum.SCHOOL_ADMIN.getValue()){
            theStatus = ScoreStatusEnum.SCHOOL_CHECKING.getValue();
        }
		return theStatus;
	}


	/**********************************1 start**********************************************/
	/**
	 * 进入新增form1表单页面
	 * @return
	 */
	@RequestMapping("scoreForm1")
	@RequiresPermissions("Score:create")
	public String scoreForm1(Model model){
		Teacher teacher = getCurrentTeacher();
		Score entity = new Score();
		entity.setTid(teacher.getId());
		model.addAttribute("entity", entity);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);

		String xiaoItemId = "";

		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		for(DictItem item:trainLevels){
			if(item.getName().indexOf("校")!=-1){
				xiaoItemId = item.getId().toString();
				trainLevels.remove(item);
				break;
			}
		}
		model.addAttribute("trainLevels", trainLevels);
		
		ProjectQuery query = new ProjectQuery();
		query.setFields("id,name");
		query.setNotEqTrainLevel(xiaoItemId);
		List<Project> projects = projectService.selectList(query);
		model.addAttribute("projects", projects);
		
		return "core/score/scoreForm1";
	}


	
	/**
	 * 进入编辑form1表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreForm1/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Score:update")
	public String scoreForm1(@PathVariable Long id, Model model, @RequestParam(value = "adminUpdate",required = false)Boolean adminUpdate){
		Score entity = scoreService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);

		String xiaoItemId = "";
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		for(DictItem item:trainLevels){
			if(item.getName().indexOf("校")!=-1){
				xiaoItemId = item.getId().toString();
				trainLevels.remove(item);
				break;
			}
		}
		model.addAttribute("trainLevels", trainLevels);
		
		ProjectQuery query = new ProjectQuery();
		query.setFields("id,name");
		query.setNotEqTrainLevel(xiaoItemId);
		List<Project> projects = projectService.selectList(query);
		model.addAttribute("projects", projects);

		model.addAttribute("entity", entity);
		if(adminUpdate!=null&&adminUpdate){
			return "core/score/scoreAdminForm1";
		}
		return "core/score/scoreForm1";
	}
	
	/**
	 * 进入form1详细信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreView1/{id}",method = RequestMethod.POST)
	public String scoreView1(@PathVariable Long id,Model model){
		Score entity = scoreService.selectById(id);
		
		entity.setYearName(dictItemService.selectById(entity.getYear()).getName());
		
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		model.addAttribute("trainLevels", trainLevels);
		
		Project project = projectService.selectById(entity.getPid());
		model.addAttribute("projectName", project.getName());
		
		model.addAttribute("entity", entity);
		return "core/score/scoreView1";
	}

	/**
	 * ajax验证学分
	 *
	 * @param entity
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "checkScore")
	@ResponseBody
	public String checkScore(Score entity) throws ParseException {
		HashMap<String, String> result = Maps.newHashMap();
		CheckScoreDto dto = scoreService.checkScore(entity);
		AjaxCallback ok = AjaxCallback.OK("验证学分是否可用");
		if (dto.getResult()) {
			result.put("ok", dto.getMsg());
		} else {
			result.put("error", dto.getMsg());
		}
		ok.setData(result);
		return JSON.toJSONString(ok);
	}

	/**************************************1 end******************************************/
	
	/**********************************2 start**********************************************/
	/**
	 * 进入新增form2表单页面
	 * @return
	 */
	@RequestMapping("scoreForm2")
	@RequiresPermissions("Score:create")
	public String scoreForm2(Model model){
		Teacher teacher = getCurrentTeacher();
		Score entity = new Score();
		entity.setTid(teacher.getId());
		model.addAttribute("entity", entity);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
		model.addAttribute("degrees", degrees);
		
		return "core/score/scoreForm2";
	}
	
	/**
	 * 进入编辑form2表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreForm2/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Score:update")
	public String scoreForm2(@PathVariable Long id,Model model, @RequestParam(value = "adminUpdate",required = false)Boolean adminUpdate){
		Score entity = scoreService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
		model.addAttribute("degrees", degrees);
		
		model.addAttribute("entity", entity);
		if(adminUpdate!=null&&adminUpdate){
			return "core/score/scoreAdminForm2";
		}
		return "core/score/scoreForm2";
	}
	
	/**
	 * 进入form2详细信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreView2/{id}",method = RequestMethod.POST)
	public String scoreView2(@PathVariable Long id,Model model){
		Score entity = scoreService.selectById(id);
		
		entity.setYearName(dictItemService.selectById(entity.getYear()).getName());
		
		List<DictItem> degrees= dictCatlogService.getSelectItems("DEGREE");
		model.addAttribute("degrees", degrees);
		
		model.addAttribute("entity", entity);
		return "core/score/scoreView2";
	}
	
	/**************************************2 end******************************************/
	
	
	/**********************************3 start**********************************************/
	/**
	 * 进入新增form3表单页面
	 * @return
	 */
	@RequestMapping("scoreForm3")
	@RequiresPermissions("Score:create")
	public String scoreForm3(Model model){
		Teacher teacher = getCurrentTeacher();
		Score entity = new Score();
		entity.setTid(teacher.getId());
		entity.setLevel(187l);//学校级
		model.addAttribute("entity", entity);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		model.addAttribute("trainLevels", trainLevels);
		
		ProjectQuery query = new ProjectQuery();
		query.setFields("id,name");
		query.setTrainLevel("187");//学校级
		User currentUser = ShiroUtils.getCurrentUser();
		query.setSchool(currentUser.getSchool());
		List<Project> projects = projectService.selectList(query);
		model.addAttribute("projects", projects);
		
		return "core/score/scoreForm3";
	}
	
	/**
	 * 进入编辑form3表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreForm3/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Score:update")
	public String scoreForm3(@PathVariable Long id,Model model, @RequestParam(value = "adminUpdate",required = false)Boolean adminUpdate){
		Score entity = scoreService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		model.addAttribute("trainLevels", trainLevels);
		
		ProjectQuery query = new ProjectQuery();
		query.setFields("id,name");
		query.setTrainLevel("187");//学校级
		User currentUser = ShiroUtils.getCurrentUser();
		query.setSchool(currentUser.getSchool());
		List<Project> projects = projectService.selectList(query);
		model.addAttribute("projects", projects);
		
		model.addAttribute("entity", entity);
		if(adminUpdate!=null&&adminUpdate){
			return "core/score/scoreAdminForm3";
		}
		return "core/score/scoreForm3";
	}
	
	/**
	 * 进入form3详细信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreView3/{id}",method = RequestMethod.POST)
	public String scoreView3(@PathVariable Long id,Model model){
		Score entity = scoreService.selectById(id);
		
		entity.setYearName(dictItemService.selectById(entity.getYear()).getName());
		
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		model.addAttribute("trainLevels", trainLevels);
		
		Project project = projectService.selectById(entity.getPid());
		model.addAttribute("projectName", project.getName());
		
		model.addAttribute("entity", entity);
		return "core/score/scoreView3";
	}
	
	/**************************************3 end******************************************/
	
	/**********************************4 start**********************************************/
	/**
	 * 进入新增form4表单页面
	 * @return
	 */
	@RequestMapping("scoreForm4")
	@RequiresPermissions("Score:create")
	public String scoreForm4(Model model){
		Teacher teacher = getCurrentTeacher();
		Score entity = new Score();
		entity.setTid(teacher.getId());
		model.addAttribute("entity", entity);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> scoreRoleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		model.addAttribute("scoreRoleTypes", scoreRoleTypes);
		
		return "core/score/scoreForm4";
	}
	
	/**
	 * 进入编辑form4表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreForm4/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Score:update")
	public String scoreForm4(@PathVariable Long id,Model model, @RequestParam(value = "adminUpdate",required = false)Boolean adminUpdate){
		Score entity = scoreService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> scoreRoleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		model.addAttribute("scoreRoleTypes", scoreRoleTypes);
		
		model.addAttribute("entity", entity);
		if(adminUpdate!=null&&adminUpdate){
			return "core/score/scoreAdminForm4";
		}
		return "core/score/scoreForm4";
	}
	
	/**
	 * 进入form4详细信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreView4/{id}",method = RequestMethod.POST)
	public String scoreView4(@PathVariable Long id,Model model){
		Score entity = scoreService.selectById(id);
		
		entity.setYearName(dictItemService.selectById(entity.getYear()).getName());
		
		List<DictItem> scoreRoleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		model.addAttribute("scoreRoleTypes", scoreRoleTypes);

		model.addAttribute("entity", entity);
		return "core/score/scoreView4";
	}
	
	/**************************************4 end******************************************/
	
	/**********************************5 start**********************************************/
	/**
	 * 进入新增form5表单页面
	 * @return
	 */
	@RequestMapping("scoreForm5")
	@RequiresPermissions("Score:create")
	public String scoreForm5(Model model){
		Teacher teacher = getCurrentTeacher();
		Score entity = new Score();
		entity.setTid(teacher.getId());
		model.addAttribute("entity", entity);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> levels= dictCatlogService.getSelectItems("SCORE_LEVEL");
		model.addAttribute("levels", levels);
		
		return "core/score/scoreForm5";
	}
	
	/**
	 * 进入编辑form5表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreForm5/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Score:update")
	public String scoreForm5(@PathVariable Long id,Model model, @RequestParam(value = "adminUpdate",required = false)Boolean adminUpdate){
		Score entity = scoreService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> levels= dictCatlogService.getSelectItems("SCORE_LEVEL");
		model.addAttribute("levels", levels);
		
		model.addAttribute("entity", entity);
		if(adminUpdate!=null&&adminUpdate){
			return "core/score/scoreAdminForm5";
		}
		return "core/score/scoreForm5";
	}
	
	/**
	 * 进入form5详细信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreView5/{id}",method = RequestMethod.POST)
	public String scoreView5(@PathVariable Long id,Model model){
		Score entity = scoreService.selectById(id);
		
		entity.setYearName(dictItemService.selectById(entity.getYear()).getName());
		
		List<DictItem> levels= dictCatlogService.getSelectItems("SCORE_LEVEL");
		model.addAttribute("levels", levels);
		
		model.addAttribute("entity", entity);
		return "core/score/scoreView5";
	}
	
	/**************************************5 end******************************************/
	
	/**********************************6 start**********************************************/
	/**
	 * 进入新增form6表单页面
	 * @return
	 */
	@RequestMapping("scoreForm6")
	@RequiresPermissions("Score:create")
	public String scoreForm6(Model model){
		Teacher teacher = getCurrentTeacher();
		Score entity = new Score();
		entity.setTid(teacher.getId());
		model.addAttribute("entity", entity);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> levels= dictCatlogService.getSelectItems("SCORE_LEVEL");
		model.addAttribute("levels", levels);
		
		List<DictItem> scoreRoleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		model.addAttribute("scoreRoleTypes", scoreRoleTypes);
		
		return "core/score/scoreForm6";
	}
	
	/**
	 * 进入编辑form6表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreForm6/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Score:update")
	public String scoreForm6(@PathVariable Long id,Model model, @RequestParam(value = "adminUpdate",required = false)Boolean adminUpdate){
		Score entity = scoreService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> levels= dictCatlogService.getSelectItems("SCORE_LEVEL");
		model.addAttribute("levels", levels);
		
		List<DictItem> scoreRoleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		model.addAttribute("scoreRoleTypes", scoreRoleTypes);
		
		model.addAttribute("entity", entity);
		if(adminUpdate!=null&&adminUpdate){
			return "core/score/scoreAdminForm6";
		}
		return "core/score/scoreForm6";
	}
	
	/**
	 * 进入form6详细信息页面
	 * 
	 */
	@RequestMapping(value="scoreView6/{id}",method = RequestMethod.POST)
	public String scoreView6(@PathVariable Long id,Model model){
		Score entity = scoreService.selectById(id);
		
		entity.setYearName(dictItemService.selectById(entity.getYear()).getName());
		
		List<DictItem> levels= dictCatlogService.getSelectItems("SCORE_LEVEL");
		model.addAttribute("levels", levels);
		
		List<DictItem> scoreRoleTypes= dictCatlogService.getSelectItems("SCORE_ROLE_TYPE");
		model.addAttribute("scoreRoleTypes", scoreRoleTypes);
		
		model.addAttribute("entity", entity);
		return "core/score/scoreView6";
	}
	
	/**************************************6 end******************************************/
	
	/**********************************7 start**********************************************/
	/**
	 * 进入新增form7表单页面
	 * @return
	 */
	@RequestMapping("scoreForm7")
	@RequiresPermissions("Score:create")
	public String scoreForm7(Model model){
		Teacher teacher = getCurrentTeacher();
		Score entity = new Score();
		entity.setTid(teacher.getId());
		model.addAttribute("entity", entity);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		return "core/score/scoreForm7";
	}
	
	/**
	 * 进入编辑form7表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreForm7/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Score:update")
	public String scoreForm7(@PathVariable Long id,Model model, @RequestParam(value = "adminUpdate",required = false)Boolean adminUpdate){
		Score entity = scoreService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		model.addAttribute("entity", entity);
		if(adminUpdate!=null&&adminUpdate){
			return "core/score/scoreAdminForm7";
		}
		return "core/score/scoreForm7";
	}
	
	/**
	 * 进入form7详细信息页面
	 * 
	 */
	@RequestMapping(value="scoreView7/{id}",method = RequestMethod.POST)
	public String scoreView7(@PathVariable Long id,Model model){
		Score entity = scoreService.selectById(id);
		
		entity.setYearName(dictItemService.selectById(entity.getYear()).getName());
		
		model.addAttribute("entity", entity);
		return "core/score/scoreView7";
	}
	
	/**************************************7 end******************************************/
	
	/**********************************8 start**********************************************/
	/**
	 * 进入新增form8表单页面
	 * @return
	 */
	@RequestMapping("scoreForm8")
	@RequiresPermissions("Score:create")
	public String scoreForm8(Model model){
		Teacher teacher = getCurrentTeacher();
		Score entity = new Score();
		entity.setTid(teacher.getId());
		entity.setLevel(187l);//学校级
		model.addAttribute("entity", entity);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		model.addAttribute("trainLevels", trainLevels);
		
		ProjectQuery query = new ProjectQuery();
		query.setFields("id,name");
		//query.setTrainLevel("187");//学校级
		//User currentUser = ShiroUtils.getCurrentUser();
		//query.setSchool(currentUser.getSchool());
		List<Project> projects = projectService.selectList(query);
		model.addAttribute("projects", projects);
		
		return "core/score/scoreForm8";
	}
	
	/**
	 * 进入编辑form8表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreForm8/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Score:update")
	public String scoreForm8(@PathVariable Long id,Model model, @RequestParam(value = "adminUpdate",required = false)Boolean adminUpdate){
		Score entity = scoreService.selectById(id);
		
		List<DictItem> years= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		model.addAttribute("years", years);
		
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		model.addAttribute("trainLevels", trainLevels);
		
		ProjectQuery query = new ProjectQuery();
		query.setFields("id,name");
		//query.setTrainLevel("187");//学校级
		//User currentUser = ShiroUtils.getCurrentUser();
		//query.setSchool(currentUser.getSchool());
		List<Project> projects = projectService.selectList(query);
		model.addAttribute("projects", projects);
		
		model.addAttribute("entity", entity);
		
		
		ScoreMineDetailQuery mineQuery = new ScoreMineDetailQuery();
		mineQuery.setSid(entity.getId().toString());
		mineQuery.setSidLike(false);
		model.addAttribute("sdetails", scoreMineDetailService.selectList(mineQuery ));
		if(adminUpdate!=null&&adminUpdate){
			return "core/score/scoreAdminForm8";
		}
		return "core/score/scoreForm8";
	}
	
	/**
	 * 进入form8详细信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreView8/{id}",method = RequestMethod.POST)
	public String scoreView8(@PathVariable Long id,Model model){
		Score entity = scoreService.selectById(id);
		
		entity.setYearName(dictItemService.selectById(entity.getYear()).getName());
		
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		model.addAttribute("trainLevels", trainLevels);
		
		Project project = projectService.selectById(entity.getPid());
		model.addAttribute("projectName", project.getName());
		
		model.addAttribute("entity", entity);
		
		ScoreMineDetailQuery mineQuery = new ScoreMineDetailQuery();
		mineQuery.setSid(entity.getId().toString());
		mineQuery.setSidLike(false);
		model.addAttribute("sdetails", scoreMineDetailService.selectList(mineQuery ));
		
		return "core/score/scoreView8";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveScore8",method = RequestMethod.POST)
	@RequiresPermissions(value={"Score:update","Score:create"},logical=Logical.AND)
	@ResponseBody
	public String saveScore8(Score entity,HttpServletRequest request)throws ParseException{
		List<ScoreMineDetail> details = Lists.newArrayList();
		Enumeration<String> keys = request.getParameterNames();
		Set<String> parmKeys = Sets.newHashSet(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    if(k.indexOf(".")!=-1){
		    	String[] split = k.split("\\.");
		    	parmKeys.add(split[0]);
		    }
		} 
		for(String keypre:parmKeys){
			ScoreMineDetail detail = new ScoreMineDetail();
			confPro(request, keypre, detail);
			details.add(detail);
		}
		AjaxCallback ok = AjaxCallback.OK(scoreService.saveOrUpdate(entity,details));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * set属性
	 * @param request
	 * @param keypre
	 * @param exp
	 * @throws ParseException
	 */
	private void confPro(HttpServletRequest request, String keypre,
			ScoreMineDetail detail) throws ParseException {
		String parmId = request.getParameter(keypre+".id");
		if(StringUtils.isNoneBlank(parmId)){
			detail.setId(Long.parseLong(request.getParameter(keypre+".id")));
		}
		detail.setCourseName(request.getParameter(keypre+".courseName"));
		detail.setHour(Integer.valueOf(request.getParameter(keypre+".hour")));
		detail.setExperter(request.getParameter(keypre+".experter"));
	}
	/**************************************8 end******************************************/
	
	
	/**
	 * 单条 审核方法      status =0 不通过  1 通过
	 * @return
	 */
	@RequestMapping(value="saveStatus",method = RequestMethod.POST)
	@RequiresPermissions("Score:check")
	@ResponseBody
	public String saveStatus(@RequestParam(value="id",required=true) Long id,
							 @RequestParam(value="checkDesc",required=false) String checkDesc,
							 @RequestParam(value="status",required=true) Integer status){
		Integer theStatus = getStatus(status);
		AjaxCallback ok = AjaxCallback.OK(scoreService.saveStatus(id, checkDesc, theStatus));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 进入审批不通过，输入不通过意见的form页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="scoreUnPassForm/{id}")
	@RequiresPermissions("Score:check")
	public String scoreUnPassForm(@PathVariable Long id,Model model){
		model.addAttribute("id", id);
		Score score = scoreService.selectById(id);
		model.addAttribute("checkDesc", score.getCheckDesc());
		return "core/score/scoreUnPassForm";
	}
	
	
	/**
	 * 批量审核
	 * @param
	 * @return
	 */
	@RequestMapping(value="checkMultiByIds",method = RequestMethod.POST)
	@RequiresPermissions("Score:check")
	@ResponseBody
	public String checkMultiByIds(@RequestParam(value="ids",required=true) String ids,
			 @RequestParam(value="checkDesc",required=false) String checkDesc,
			 @RequestParam(value="status",required=true) Integer status){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		Integer theStatus = getStatus(status);
		scoreService.checkMultiByIds(idList, checkDesc, theStatus);
		AjaxCallback ok = AjaxCallback.OK("批量审核选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 进入批量审批不通过，输入不通过意见的form页面
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequestMapping(value="scoreUnPassesForm")
	@RequiresPermissions("Score:check")
	public String scoreUnPassesForm(@RequestParam(value="ids",required=true) String ids,Model model){
		model.addAttribute("ids", ids);
		return "core/score/scoreUnPassesForm";
	}

	/**
	 * 通过不同的角色转换审核通过与不通过的状态   1为通过，0为不通过
	 * @param status
	 * @return
	 */
	private Integer getStatus(Integer status) {
		User currentUser = ShiroUtils.getCurrentUser();
		Integer theStatus = 0;
		if(status==1){
			if(currentUser.getType()==UserTypeEnum.PROVINCE_ADMIN.getValue()){
				theStatus = ScoreStatusEnum.CHECK_END.getValue();
			}else if(currentUser.getType()==UserTypeEnum.TOWN_ADMIN.getValue()){
				theStatus = ScoreStatusEnum.PROVINCE_CHECKING.getValue();
			}else if(currentUser.getType()==UserTypeEnum.SCHOOL_ADMIN.getValue()){
				theStatus = ScoreStatusEnum.TOWN_CHECKING.getValue();
			}
		}else{
			if(currentUser.getType()==UserTypeEnum.PROVINCE_ADMIN.getValue()){
				theStatus = ScoreStatusEnum.PROVINCE_UNPASS.getValue();
			}else if(currentUser.getType()==UserTypeEnum.TOWN_ADMIN.getValue()){
				theStatus = ScoreStatusEnum.TOWN_UNPASS.getValue();
			}else if(currentUser.getType()==UserTypeEnum.SCHOOL_ADMIN.getValue()){
				theStatus = ScoreStatusEnum.SCHOOL_UNPASS.getValue();
			}
		}
		return theStatus;
	}
	
	/**
	 * 获取可以当前用户  待审的状态
	 * @return
	 */
	private Integer getCurrentStatus() {
		User currentUser = ShiroUtils.getCurrentUser();
		Integer theStatus = 0;
		theStatus = getTheStatusByCurrentAdmin(currentUser, theStatus);
		return theStatus;
	}
	
	
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveScore",method = RequestMethod.POST)
	@RequiresPermissions(value={"Score:update","Score:create"},logical=Logical.AND)
	@ResponseBody
	public String saveScore(Score entity){
		AjaxCallback ok = AjaxCallback.OK(scoreService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}

	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="adminUpdateScore",method = RequestMethod.POST)
	@RequiresPermissions("Score:check")
	@ResponseBody
	public String adminUpdateScore(Score entity){
		AjaxCallback ok = AjaxCallback.OK(scoreService.adminUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteScoreByIds",method = RequestMethod.POST)
	@RequiresPermissions("Score:create")
	@ResponseBody
	public String deleteScoreByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		scoreService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 单条删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteScoreById/{id}", method=RequestMethod.POST)
	@RequiresPermissions("Score:delete")
	@ResponseBody
	public String deleteScoreById(@PathVariable Long id){
		
		scoreService.deleteScore(id);
		
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 一键通过
	 */
	@RequestMapping(value="oneKeyPass",method = RequestMethod.POST)
	@RequiresPermissions("Score:check")
	@ResponseBody
	public String oneKeyPass(@RequestParam(value="eqProvince",required=false) Integer eqProvince,
			@RequestParam(value="eqCity",required=false) Integer eqCity,
			@RequestParam(value="eqTown",required=false) Integer eqTown,
			@RequestParam(value="eqSchool",required=false) Long eqSchool
			){
		User currentUser = ShiroUtils.getCurrentUser();
		
		Integer theStatus = getStatus(1);//1为通过
		
		Integer province = eqProvince!=null?eqProvince:currentUser.getProvince();
		Integer city =  eqCity!=null?eqCity:currentUser.getCity();
		Integer town =  eqTown!=null?eqTown:currentUser.getTown();
		Long school =  eqSchool!=null?eqSchool:currentUser.getSchool();
		
		Integer currentStatus = getCurrentStatus();
		scoreService.checkOneKey("", theStatus, province, city, town, school,currentStatus);
		
		AjaxCallback ok = AjaxCallback.OK("一键通过操作成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * **********************研修网接口  start************************
	 */

	/**
	 * 获取我的学时
	 * @return
	 */
	@RequestMapping(value="getMyTotalScore")
	@ResponseBody
	public String getMyTotalScore(HttpServletRequest request, HttpServletResponse response,
			Model model){
		Integer myscore = 0;
		String passport = PassportHelper.getPassport((HttpServletRequest)request);
	//	passport="4201050000000002";
		if(StringUtils.isNotBlank(passport)){
			Teacher teacher = teacherService.selectByTno(passport);
			if(teacher!=null){
				myscore = reportScoreService.sumScoreByTid(teacher.getId());
			}
		}
		
		AjaxCallback ok = AjaxCallback.OK("success");
		ok.setData(myscore);
		ok.setCloseCurrent(null);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * **********************研修网接口  end************************
	 */
}
