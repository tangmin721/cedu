package com.yanxiu.ce.core.train.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.common.utils.excel.ExcelFileGenerator;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.service.CourseService;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.StageService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.train.entity.*;
import com.yanxiu.ce.core.train.service.ProjectPersonService;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.core.train.service.ProjectTrainOrgService;
import com.yanxiu.ce.core.train.service.TrainOrgService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.enums.UserTypeEnum;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.CityService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.TownService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 参培人名单管理
 * @author tangmin
 * @date 2016-10-24 16:32:04
 */
@Controller
@RequestMapping("/core/train/project/person")
public class ProjectPersonController extends BasePctsController<ProjectPersonQuery> {
	
	@Autowired
	private ProjectPersonService projectPersonService;

    @Autowired
    private TrainOrgService trainOrgService;

    @Autowired
    private ProjectService projectService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private ProjectTrainOrgService projectTrainOrgService;

	@Autowired
	protected CityService cityService;

	@Autowired
	protected TownService townService;

	@Autowired
	protected SchoolService schoolService;

    @Autowired
    private JedisClient jedisClient;

	@Autowired
	private CourseService courseService;

	@Autowired
	private StageService stageService;

	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("projectPersonList/{pid}")
	@RequiresPermissions("ProjectPerson:read")
	public String list(@PathVariable Long pid, ProjectPersonQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query = configUnPstsQuery(query);//配置省市区县查询条件
        query.setPid(pid.toString());

        User currentUser = ShiroUtils.getCurrentUser();
        if(currentUser.getType()== UserTypeEnum.TRAINORG.getValue()){
            query.setOid(trainOrgService.selectByOrgNo(currentUser.getLoginName()).getId().toString());
        }
		Pagination<ProjectPerson> page = projectPersonService.selectListPagination(query);

		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("pid",pid);

		//获取培训机构选择下拉框
		ProjectTrainOrgQuery orgQuery = new ProjectTrainOrgQuery();
		orgQuery .setPid(pid.toString());
		List<ProjectTrainOrg> projectOrgList = projectTrainOrgService.selectList(orgQuery);//获取项目分配的所有机构集合
		List<Map<String,String>> projectOrgMaps = Lists.newArrayList();
		for (ProjectTrainOrg pto:projectOrgList){
			if(pto.getOrgid()!=null && trainOrgService.selectById(pto.getOrgid())!=null){
				Map<String,String> map = Maps.newHashMap();
				map.put("id",pto.getOrgid().toString());
				map.put("name",trainOrgService.selectById(pto.getOrgid()).getName());
				projectOrgMaps.add(map);
			}
		}
		model.addAttribute("projectOrgMaps",projectOrgMaps);

        //获取项目状态
        Project project = projectService.selectById(pid);

        Integer projectStatus = project.getStatus();
        model.addAttribute("projectStatus", projectStatus);

        Integer scoreCreateType = project.getScoreCreateType();
        model.addAttribute("scoreCreateType", scoreCreateType);
		return "core/train/project/person/projectPersonList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("projectPersonForm")
	@RequiresPermissions("ProjectPerson:create")
	public String projectPersonForm(Model model){
		ProjectPerson entity = new ProjectPerson();
		model.addAttribute("entity", entity);
		return "core/train/project/person/projectPersonForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="projectPersonForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("ProjectPerson:update")
	public String projectPersonForm(@PathVariable Long id,Model model){
		ProjectPerson entity = projectPersonService.selectById(id);
		Teacher teacher = teacherService.selectById(entity.getTid());
		Project project = projectService.selectById(entity.getPid());
		model.addAttribute("entity", entity);
		model.addAttribute("provinceName", provinceService.getNameByNo(teacher.getProvince()));
		model.addAttribute("cityName", cityService.getNameByNo(teacher.getCity()));
		model.addAttribute("townName", townService.getNameByNo(teacher.getTown()));
		model.addAttribute("schoolName", schoolService.selectById(teacher.getSchool()).getName());
		model.addAttribute("name",teacher.getName());
		model.addAttribute("tno",teacher.getTno());
		model.addAttribute("idCard",teacher.getIdCard());
		/*model.addAttribute("stageName",stageService.selectById(teacher.getStage()).getName());
		model.addAttribute("courseName",courseService.selectById(teacher.getCourse()).getName());*/
		model.addAttribute("project", project);
		return "core/train/project/person/projectPersonForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProjectPerson",method = RequestMethod.POST)
	@RequiresPermissions(value={"ProjectPerson:update","ProjectPerson:create"},logical=Logical.AND)
	@ResponseBody
	public String saveProjectPerson(ProjectPerson entity){
		AjaxCallback ok = AjaxCallback.OK(projectPersonService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProjectPersonByIds",method = RequestMethod.POST)
	@RequiresPermissions("ProjectPerson:delete")
	@ResponseBody
	public String deleteProjectPersonByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		projectPersonService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}


	/**
	 * 进入导入向导的dialog
	 * @return
	 */
	@RequestMapping("personImport/{pid}")
    @RequiresPermissions("ProjectPerson:import")
	public String teacherImport(@PathVariable Long pid, Model model){
		model.addAttribute("pid", pid);

	//	model.addAttribute("oid",oid);
		return "core/train/project/person/personImport";
	}

	/**
	 * 验证excel
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkExcel/{pid}", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
    @RequiresPermissions("ProjectPerson:import")
	public String checkExcel(@PathVariable Long pid,@RequestParam(value="file",required=false) MultipartFile file) {
		logger.info("checkExcel::");
		String msg = "";
		AjaxCallback ok = AjaxCallback.OK(msg);
		List<String> errorList = projectPersonService.checkExcel(file,pid);
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
	 * @throws Exception
	 */
	@RequestMapping(value = "importExcel/{pid}", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "批量导入")
	public String importExcel(@PathVariable Long pid,@RequestParam(value="file",required=false) MultipartFile file) {
		logger.info("importExcel::");

		User currentUser = ShiroUtils.getCurrentUser();
		TrainOrg trainOrg = trainOrgService.selectByOrgNo(currentUser.getLoginName());
		Long oid = null;
		if(trainOrg!=null){
			oid = trainOrg.getId();
		}
		AjaxCallback ok = AjaxCallback.OK("成功导入【"+projectPersonService.importExcel(file,pid,oid)+"】条信息！");
		// Thread.sleep(5000);模拟延时

		return JSON.toJSONString(ok);
	}

	/**
	 * 进入确认生成认证学时的dialog
	 * @return
	 */
	@RequestMapping("personCreateScorePage/{pid}")
	@RequiresPermissions("ProjectPerson:makeScore")
	public String personCreateScorePage(@PathVariable Long pid, Model model){
		model.addAttribute("pid", pid);

		return "core/train/project/person/personCreateScorePage";
	}

    /**
     * 开始执行生成学时操作
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("startMakeScore/{pid}")
    @RequiresPermissions("ProjectPerson:makeScore")
    @ResponseBody
    public String startMakeScore(@PathVariable Long pid, Model model){
        AjaxCallback ok = AjaxCallback.OK("start...");
        Map<String,String> map = projectPersonService.startMakeScore(pid);
        ok.setData(map);

        return JSON.toJSONString(ok);
    }

    /**
     * 执行生成学时操作线程
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("makeScore/{pid}/{redisKey}")
    @RequiresPermissions("ProjectPerson:makeScore")
    @ResponseBody
    public void makeScore(@PathVariable Long pid,@PathVariable String redisKey, Model model){
       projectPersonService.makeScore(pid,redisKey);
    }

    /**
     * 取消学时，删除本项目所有认证的学时
     * @param pid
     * @param redisKey
     * @param model
     */
    @RequestMapping("cancelMakeScore/{pid}")
    @RequiresPermissions("ProjectPerson:makeScore")
    @ResponseBody
    public String cancelMakeScore(@PathVariable Long pid,@RequestParam(value="redisKey",required=false)String redisKey, Model model){
        AjaxCallback ok = AjaxCallback.OK("start...");
        projectPersonService.cancelMakeScore(pid,redisKey);
        return JSON.toJSONString(ok);
    }

    /**
     * 获取rediskey得到当前执行到那一条了
     * @param model
     * @return
     */
    @RequestMapping("getCurrentNum/{redisKey}")
    @ResponseBody
    public String getCurrentNum(@PathVariable String redisKey, Model model){
        AjaxCallback ok = AjaxCallback.OK("get currentNum");
        Map<String,Integer> map = Maps.newHashMap();
        map.put("currentNum", Integer.valueOf(jedisClient.get(redisKey)));
        ok.setData(map);
        return JSON.toJSONString(ok);
    }


	/**
	 * 导出全部
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportAll/{pid}")
	public void exportAll(@PathVariable Long pid,HttpServletResponse response) throws Exception{
		//excel的标题数据（只有一条）
		List<String> fieldName = projectPersonService.getExcelFieldName();
		//excel的内容数据（多条）
		ProjectPersonQuery query = new ProjectPersonQuery();
		query = configUnPstsQuery(query);//配置省市区县查询条件
		query.setPid(pid.toString());

		User currentUser = ShiroUtils.getCurrentUser();
		if(currentUser.getType()== UserTypeEnum.TRAINORG.getValue()){
			query.setOid(trainOrgService.selectByOrgNo(currentUser.getLoginName()).getId().toString());
		}
		List<List<String>> fieldDatas = projectPersonService.getExcelDatasByQuery(query);
		ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas,"参培人员成绩单");
	}

	/**
	 * 根据条件导出
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportSearch/{pid}")
	public void exportSearch(@PathVariable Long pid,ProjectPersonQuery query, HttpServletResponse response) throws Exception{
		//excel的标题数据（只有一条）
		List<String> fieldName = projectPersonService.getExcelFieldName();
		//excel的内容数据（多条）
		query = configUnPstsQuery(query);//配置省市区县查询条件
		query.setPid(pid.toString());

		User currentUser = ShiroUtils.getCurrentUser();
		if(currentUser.getType()== UserTypeEnum.TRAINORG.getValue()){
			query.setOid(trainOrgService.selectByOrgNo(currentUser.getLoginName()).getId().toString());
		}

		//url docode
		query.setName(java.net.URLDecoder.decode(query.getName(), "UTF-8"));
		query.setTno(java.net.URLDecoder.decode(query.getTno(), "UTF-8"));
		query.setIdCard(java.net.URLDecoder.decode(query.getIdCard(), "UTF-8"));

		List<List<String>> fieldDatas = projectPersonService.getExcelDatasByQuery(query);
		ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas,"参评人员成绩单");
	}

}
