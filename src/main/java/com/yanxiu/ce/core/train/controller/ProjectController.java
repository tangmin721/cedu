package com.yanxiu.ce.core.train.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.Course;
import com.yanxiu.ce.core.basic.entity.CourseQuery;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.basic.service.CourseService;
import com.yanxiu.ce.core.basic.service.StageService;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.entity.ProjectQuery;
import com.yanxiu.ce.core.train.enums.ProjectCstatusEnum;
import com.yanxiu.ce.core.train.enums.ProjectEnum;
import com.yanxiu.ce.core.train.enums.ProjectMenuEnum;
import com.yanxiu.ce.core.train.enums.RegisterEnum;
import com.yanxiu.ce.core.train.enums.ScoreCreateTypeEnum;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.system.entity.*;
import com.yanxiu.ce.system.enums.UserTypeEnum;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.DictCatlogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * 培训项目管理
 * @author tangmin
 * @date 2016-04-11 13:51:46
 */
@Controller
@RequestMapping("/core/train/project/base")
public class ProjectController extends BasePctsController<ProjectQuery>{
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private CourseService courseService;
	
	@Value(value = "${FR_URL}")
	private String FR_URL;
	
	
	/**
	 * 进入报名配置管理tabs
	 * @return
	 */
	@RequestMapping("projectReportConf/{pid}")
	@RequiresPermissions("Project:conf")
	public String projectReportConf(@PathVariable Long pid,Model model){
		model.addAttribute("pid", pid);
		return "core/train/project/base/projectReportConf";
	}
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("projectList")
	@RequiresPermissions("Project:read")
	public String list(ProjectQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		query.setOrderField("createtime");
		query.setOrderDirection("desc");
		this.configUnPstsQuery(query);
		Pagination<Project> page = projectService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("menuType", "99");
		
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_TYPE");
		List<DictItem> schoolYears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> trainLevels = getTheTrainLevels();
		List<DictItem> implWays= dictCatlogService.getSelectItems("IMPL_WAY");
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("trainLevels", trainLevels);
		model.addAttribute("implWays", implWays);
		model.addAttribute("stages", stages);
		
		return "core/train/project/base/projectList";
	}
	
	/**
	 * 进入 0 创建项目、1 管理我的项目、2同级项目查询 、3机构材料管理
	 * @param menuType
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("projectList/{menuType}")
	@RequiresPermissions("Project:mymenus")
	public String createList(@PathVariable Integer menuType,ProjectQuery query,Model model){
		query.setOrderField("createtime");
		query.setOrderDirection("desc");
		User currentUser = ShiroUtils.getCurrentUser();
		this.configPstsQuery(query);
		List<DictItem> trainLevels = null;
		if(ProjectMenuEnum.CREATE.getValue()==menuType){
			query.setUserId(currentUser.getId().toString());
			trainLevels = getFormTheTrainLevels();
		}else if(ProjectMenuEnum.NOT_MY.getValue()==menuType){
			query.setUserType(currentUser.getType().toString());
//			query.setUserId(currentUser.getId().toString());
//			query.setNotMy(true);//不是我，但是同级
			query.setNotEqStatus(ProjectEnum.UN_PUBLISH.getValue()+"");
			trainLevels = getFormTheTrainLevels();
		}else if(ProjectMenuEnum.DOWN.getValue()==menuType){
			query.setNotEqStatus(ProjectEnum.UN_PUBLISH.getValue()+"");
			configQueryDownAndCreate(menuType, query, currentUser);
			trainLevels = getTheTrainLevels();
		}else{//培训机构
			query.setNotEqStatus(ProjectEnum.UN_PUBLISH.getValue()+"");
			String loginName = currentUser.getLoginName();
			query.setSelectJoinFlag(true);
			List<Long> joinPids = projectService.getOrgJoinPids(loginName);
			query.setJoinPids(joinPids );
			trainLevels = dictCatlogService.getSelectItems("TRAIN_LEVEL");
		}
		
		Pagination<Project> page = projectService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("menuType", menuType);
		
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_TYPE");
		List<DictItem> schoolYears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		List<DictItem> implWays= dictCatlogService.getSelectItems("IMPL_WAY");
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("trainLevels", trainLevels);
		model.addAttribute("implWays", implWays);
		model.addAttribute("stages", stages);
		
		return "core/train/project/base/projectList";
	}
	
	/**
	 * 区县审核list
	 * @param menuType
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("townCheckList")
	@RequiresPermissions("Project:townCheck")
	public String townCheckList(ProjectQuery query,Model model){
		query.setOrderField("createtime");
		query.setOrderDirection("desc");
		User currentUser = ShiroUtils.getCurrentUser();
		this.configPstsQuery(query);
		List<DictItem> trainLevels = null;
		query.setNotEqStatus(ProjectEnum.UN_PUBLISH.getValue()+"");
		configQueryDownAndCreate(ProjectMenuEnum.DOWN.getValue(), query, currentUser);
		trainLevels = getTheTrainLevels();
		
		Pagination<Project> page = projectService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("menuType", ProjectMenuEnum.DOWN.getValue());
		
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_TYPE");
		List<DictItem> schoolYears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		List<DictItem> implWays= dictCatlogService.getSelectItems("IMPL_WAY");
		List<Stage> stages = stageService.selectStages();
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("trainLevels", trainLevels);
		model.addAttribute("implWays", implWays);
		model.addAttribute("stages", stages);
		
		model.addAttribute("isTownCheck", true);
		
		return "core/train/project/base/projectTownCheckList";
	}
	
	/**
	 * 打开，编辑区县审核form
	 * @param pid
	 * @param pass
	 * @param coption
	 * @param model
	 * @return
	 */
	@RequestMapping(value="townCheckProjectForm/{pid}")
	@RequiresPermissions("Project:townCheck")
	public String townCheckProjectForm(@PathVariable Long pid,Model model){
		Project entity = projectService.selectById(pid);
		model.addAttribute("pid", pid);
		model.addAttribute("pass", entity.getCstatus());
		model.addAttribute("coption", entity.getCoption());
		return "core/train/project/base/projectTownCheckForm";
	}
	
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTownCheckProject",method = RequestMethod.POST)
	@RequiresPermissions("Project:townCheck")
	@ResponseBody
	public String saveTownCheckOption(@RequestParam(value="pid",required=false) Long pid,
			@RequestParam(value="pass",required=false) Integer pass,
			@RequestParam(value="coption",required=false) String coption,HttpServletRequest request){
		
		AjaxCallback ok = AjaxCallback.OK("操作成功！");
		projectService.saveTownCheckOption(pid,pass,coption);
		return JSON.toJSONString(ok);
	}
	

	/**
	 * 配置查询下级项目，还有我创建的项目列表的查询条件
	 * @param menuType
	 * @param query
	 * @param currentUser
	 */
	private void configQueryDownAndCreate(Integer menuType, ProjectQuery query,
			User currentUser) {
		query.setOrderField("createtime");
		query.setOrderDirection("desc");
		if(ProjectMenuEnum.DOWN.getValue()==menuType){//4查看下级项目
//			if(currentUser.getSchool()!=0){
//				query.setSchool(currentUser.getSchool());
//				query.setTown(currentUser.getTown());
//				query.setCity(currentUser.getCity());
//				query.setProvince(currentUser.getProvince());
//			} else
			if(currentUser.getTown()!=0){
				if(query.getSchool() == 0) query.setSchool(-1l);
				query.setTown(currentUser.getTown());
				query.setCity(currentUser.getCity());
				query.setProvince(currentUser.getProvince());
			} else
			if(currentUser.getCity()!=0){
				if(query.getTown()==0) query.setTown(-1);
				query.setCity(currentUser.getCity());
				query.setProvince(currentUser.getProvince());
			} else
			if(currentUser.getProvince()!=0){
				if(query.getCity()==0) query.setCity(-1);
				query.setProvince(currentUser.getProvince());
			}
		}else{//0 我自己创建的项目
			query.setUserType(currentUser.getType().toString());
			query.setUserTypeLike(false);
		}
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("createProjectForm/{formtype}")
	@RequiresPermissions("Project:create")
	public String createProjectForm(@PathVariable String formtype,Model model){
		Project entity = new Project();
		
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_TYPE");
		List<DictItem> schoolYears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		List<DictItem> trainLevels = getFormTheTrainLevels();
				
		//面向岗位，多选
		List<DictItem> faceDeptses= dictCatlogService.getSelectItems("FACE_DEPTS");
		model.addAttribute("faceDeptses", faceDeptses);
		
		//面向学段，多选
		List<?> teacherTypeses = TeacherTypeEnum.toList();
		model.addAttribute("teacherTypeses", teacherTypeses);
		
		List<DictItem> implWays= dictCatlogService.getSelectItems("IMPL_WAY");
		List<Stage> stages = stageService.selectStages();
		
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("trainLevels", trainLevels);
		model.addAttribute("implWays", implWays);
		model.addAttribute("stages", stages);
		
		model.addAttribute("entity", entity);
		model.addAttribute("formtype", formtype);//formtype=0为navtab 1为dialog
		return "core/train/project/base/projectForm";

	}

	
	/**
	 * 查询条件 下级   不同级别
	 * @return
	 */
	private List<DictItem> getTheTrainLevels() {
		Integer userType = ShiroUtils.getCurrentUser().getType();
		//根据不同用户，过滤能创建的等级
		List<DictItem> trainLevels = Lists.newArrayList();
		List<DictItem> allLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		for(DictItem level:allLevels){
			int indexOfGuo = level.getName().indexOf("国");
			int indexOfProvince = level.getName().indexOf("省");
			int indexOfCity = level.getName().indexOf("市");
			int indexOfTown = level.getName().indexOf("县");
			int indexOfSchool = level.getName().indexOf("校");
			
			
			if(UserTypeEnum.PROVINCE_ADMIN.getValue()==userType){
				if((indexOfGuo==-1 || indexOfProvince==-1) && (indexOfCity !=-1 || indexOfTown !=-1 || indexOfSchool !=-1)){
					trainLevels.add(level);
				}
			}else if(UserTypeEnum.CITY_ADMIN.getValue()==userType){
				if((indexOfGuo==-1 || indexOfProvince==-1 || indexOfCity==-1) && (indexOfTown !=-1 || indexOfSchool !=-1)){
					trainLevels.add(level);
				}
			}else if(UserTypeEnum.TOWN_ADMIN.getValue()==userType){
				if((indexOfGuo==-1 || indexOfProvince==-1 || indexOfCity ==-1 || indexOfTown ==-1) && (indexOfSchool !=-1)){
					trainLevels.add(level);
				}
			}else if(UserTypeEnum.SCHOOL_ADMIN.getValue()==userType){
				if((indexOfGuo==-1 || indexOfProvince==-1 || indexOfCity ==-1 && indexOfTown ==-1) && indexOfSchool !=-1){
					trainLevels.add(level);
				}
			}
		}
		return trainLevels;
	}
	
	
	/**
	 * form表单不同级别      与      我创建项目 查询 不同级别
	 * @return
	 */
	private List<DictItem> getFormTheTrainLevels() {
		Integer userType = ShiroUtils.getCurrentUser().getType();
		//根据不同用户，过滤能创建的等级
		List<DictItem> trainLevels = Lists.newArrayList();
		List<DictItem> allLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		for(DictItem level:allLevels){
			int indexOfGuo = level.getName().indexOf("国");
			int indexOfProvince = level.getName().indexOf("省");
			int indexOfCity = level.getName().indexOf("市");
			int indexOfTown = level.getName().indexOf("县");
			int indexOfSchool = level.getName().indexOf("校");
			
			if(UserTypeEnum.PROVINCE_ADMIN.getValue()==userType){
				if(indexOfProvince!=-1 || indexOfGuo !=-1){
					trainLevels.add(level);
				}
			}
			
			if(UserTypeEnum.CITY_ADMIN.getValue()==userType){
				if(indexOfCity!=-1){
					trainLevels.add(level);
				}
			}
			
			if(UserTypeEnum.TOWN_ADMIN.getValue()==userType){
				if(indexOfTown!=-1){
					trainLevels.add(level);
				}
			}
			
			if(UserTypeEnum.SCHOOL_ADMIN.getValue()==userType){
				if(indexOfSchool!=-1){
					trainLevels.add(level);
				}
			}
		}
		return trainLevels;
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="projectForm/{id}")
	@RequiresPermissions("Project:update")
	public String projectForm(@PathVariable Long id,@RequestParam(value="isShow",required=false) Integer isShow,@RequestParam(value="optType",required=false) Integer optType,Model model){
		Project entity = projectService.selectById(id);
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_TYPE");
		List<DictItem> schoolYears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		List<DictItem> trainLevels = getFormTheTrainLevels();
		
		//面向岗位，多选
		List<DictItem> faceDeptses= dictCatlogService.getSelectItems("FACE_DEPTS");
		model.addAttribute("faceDeptses", faceDeptses);
		
		//面向学段,多选
		List<?> teacherTypeses = TeacherTypeEnum.toList();
		model.addAttribute("teacherTypeses", teacherTypeses);
		
		List<DictItem> implWays= dictCatlogService.getSelectItems("IMPL_WAY");
		List<Stage> stages = stageService.selectStages();
		List<?> registerTypes = RegisterEnum.toList();
		
		List<Course> courses = null;
		CourseQuery csQuery = new CourseQuery();
		if(entity!=null&&entity.getStage()!=null){
			csQuery.setStageId(entity.getStage().toString());
			courses = courseService.selectList(csQuery );
		}
		
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("trainLevels", trainLevels);
		model.addAttribute("implWays", implWays);
		model.addAttribute("stages", stages);
		model.addAttribute("courses", courses);
		model.addAttribute("registerTypes", registerTypes);
		
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
		model.addAttribute("optType", optType);
		model.addAttribute("formtype", "dialog");//formtype=0为navtab 1为dialog
		
		if(isShow!=null&&isShow==1){
			return "core/train/project/base/projectView";
		}
		return "core/train/project/base/projectForm";
	}
	
	/**
	 * 进入  查看 form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="projectViewForm/{id}")
	@RequiresPermissions("Project:read")
	public String projectViewForm(@PathVariable Long id,Model model){
		Project entity = projectService.selectById(id);
		
		List<DictItem> trainTypes= dictCatlogService.getSelectItems("TRAIN_TYPE");
		List<DictItem> schoolYears= dictCatlogService.getSelectItems("SCHOOL_YEAR");
		List<DictItem> trainLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
		List<DictItem> implWays= dictCatlogService.getSelectItems("IMPL_WAY");
		List<Stage> stages = stageService.selectStages();
		List<?> registerTypes = RegisterEnum.toList();
		
		List<Course> courses = null;
		CourseQuery csQuery = new CourseQuery();
		if(entity!=null&&entity.getStage()!=null){
			csQuery.setStageId(entity.getStage().toString());
			courses = courseService.selectList(csQuery );
		}
		
		//面向岗位，多选
		List<DictItem> faceDeptses= dictCatlogService.getSelectItems("FACE_DEPTS");
		model.addAttribute("faceDeptses", faceDeptses);
		
		//面向学段，多选
		List<?> teacherTypeses = TeacherTypeEnum.toList();
		model.addAttribute("teacherTypeses", teacherTypeses);
		
		model.addAttribute("trainTypes", trainTypes);
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("trainLevels", trainLevels);
		model.addAttribute("implWays", implWays);
		model.addAttribute("stages", stages);
		model.addAttribute("courses", courses);
		model.addAttribute("registerTypes", registerTypes);
		
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

		List<?> scoreCreateTypes = ScoreCreateTypeEnum.toList();
		model.addAttribute("scoreCreateTypes", scoreCreateTypes);
		
		return "core/train/project/base/projectViewForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProject",method = RequestMethod.POST)
	@RequiresPermissions(value={"Project:update","Project:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存项目")
	public String saveProject(Project entity,HttpServletRequest request){
		
		User user = ShiroUtils.getCurrentUser();
		entity.setUserType(user.getType());
		entity.setProvince(user.getProvince());
		entity.setCity(user.getCity());
		entity.setTown(user.getTown());
		entity.setSchool(user.getSchool());
		entity.setUserId(user.getId());
		if(UserTypeEnum.CITY_ADMIN.getValue() == user.getType() || UserTypeEnum.TOWN_ADMIN.getValue() == user.getType() || UserTypeEnum.SCHOOL_ADMIN.getValue() == user.getType()){
			Long mylevel = null;
			List<DictItem> allLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
			for(DictItem level:allLevels){
				int indexOfCity = level.getName().indexOf("市");
				int indexOfTown = level.getName().indexOf("县");
				int indexOfSchool = level.getName().indexOf("校");
				if(UserTypeEnum.CITY_ADMIN.getValue() == user.getType()){
					if(indexOfCity !=-1){
						mylevel = level.getId();
					}
				}
				if(UserTypeEnum.TOWN_ADMIN.getValue() == user.getType()){
					if(indexOfTown !=-1){
						mylevel = level.getId();
					}
				}
				if(UserTypeEnum.SCHOOL_ADMIN.getValue() == user.getType()){
					if(indexOfSchool !=-1){
						mylevel = level.getId();
					}
				}
			}
			entity.setTrainLevel(mylevel);
		}
		
		
		AjaxCallback ok = AjaxCallback.OK(projectService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 变更状态
	 * @return
	 */
	@RequestMapping(value="updateStatus",method = RequestMethod.POST)
	@RequiresPermissions(value={"Project:update","Project:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "变更项目状态")
	public String updateStatus(@RequestParam(value="id",required=false) Long id,
			@RequestParam(value="status",required=false) Integer status){
		Long updateStatus = projectService.updateStatus(id, status);
		String msg = "保存成功";
		if(updateStatus!=1){
			msg = "保存失败";
		}
		AjaxCallback ok = AjaxCallback.OK(msg);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProjectByIds",method = RequestMethod.POST)
	@RequiresPermissions("Project:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除项目")
	public String deleteProjectByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 进入配置培训结构页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="projectTrainOrgForm/{id}")
	@RequiresPermissions("Project:update")
	public String projectTrainOrgForm(@PathVariable Long id,Model model){
		return "core/train/project/base/projectTrainOrgForm";
	}

	/**
	 * 报表查看报名申请单
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("projectViewFr/{id}")
	public String teacherView(@PathVariable Long id,Model model){
		model.addAttribute("id", id);
		model.addAttribute("fr_url", FR_URL);
		return "core/train/project/base/projectViewFr";
	}
	
	
	/**
	 * 根据pno获取 项目信息
	 */
	@RequestMapping(value="getProjectByPno/{pno}",method = RequestMethod.POST)
	@ResponseBody
	public String getProjectByPno(@RequestParam(value="pno",required=true) String pno){
		Project project = projectService.selectByPno(pno);
		Map<String,Object> map = Maps.newHashMap();
		if(project!=null){
			map.put("pid", project.getId());
			map.put("pname", project.getName());
		}
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		ok.setData(map);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 获取projects下拉框
	 */
	@RequestMapping(value="getProjects",method = RequestMethod.POST)
	@ResponseBody
	public String getProjects(@RequestParam(value="pno",required=false) String pno,
			@RequestParam(value="year",required=false) String year,
			@RequestParam(value="level",required=false) String level){
		ProjectQuery query = new ProjectQuery();
		query.setFields("id,name");
		if(StringUtils.isNotBlank(pno)){
			query.setPno(pno);
			//query.setPnoLike(false);
		}
		if(StringUtils.isNotBlank(year)){
			query.setSchoolYear(year);
			query.setSchoolYearLike(false);
		}
		if(StringUtils.isNotBlank(level)){
			query.setTrainLevel(level);
			query.setTrainTypeLike(false);
		}
		return JSON.toJSONString(projectService.selectList(query ));
	}
}
