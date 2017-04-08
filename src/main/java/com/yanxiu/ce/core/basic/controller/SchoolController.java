package com.yanxiu.ce.core.basic.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.utils.JsonUtils;
import com.yanxiu.ce.common.utils.excel.ExcelFileGenerator;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.SchoolQuery;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.basic.enums.SchoolStatusEnum;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.Town;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.DictCatlogService;

/**
 * 学校管理
 * @author tangmin
 * @date 2016-03-22 19:04:55
 */
@Controller
@RequestMapping("/core/basic/school")
public class SchoolController extends BasePctsController<SchoolQuery>{
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("schoolList")
	public String list(SchoolQuery query,Model model){
		query = configPstsQuery(query);//配置省市区县查询条件
		
		//下拉框
		List<DictItem> categorys = dictCatlogService.getSelectItems("SCHOOL_CATEGORY");
		List<DictItem> types = dictCatlogService.getSelectItems("SCHOOL_TYPE");
		List<?> schoolStatus = SchoolStatusEnum.toList();
		List<?> schoolTypes = TeacherTypeEnum.toList();
		
		Pagination<School> page = schoolService.selectListPagination(query);
		
		model.addAttribute("categorys", categorys);
		model.addAttribute("types", types);
		model.addAttribute("schoolStatus", schoolStatus);
		model.addAttribute("schoolTypes", schoolTypes);
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/basic/school/schoolList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("schoolForm")
	public String schoolForm(Model model){
		
		
		SchoolQuery query = new SchoolQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		
		//下拉框
		List<DictItem> categorys = dictCatlogService.getSelectItems("SCHOOL_CATEGORY");
		List<DictItem> types = dictCatlogService.getSelectItems("SCHOOL_TYPE");
		List<?> schoolStatus = SchoolStatusEnum.toList();
		List<?> schoolTypes = TeacherTypeEnum.toList();
		
		model.addAttribute("categorys", categorys);
		model.addAttribute("types", types);
		model.addAttribute("schoolStatus", schoolStatus);
		model.addAttribute("schoolTypes", schoolTypes);
		
		model.addAttribute("provinces", query.getQueryProvinces());
		model.addAttribute("citys", query.getQueryCitys());
		model.addAttribute("towns", query.getQueryTowns());
		//为了跟修改的form统一
		School entity = new School();
		entity.setProvince(query.getProvince());
		entity.setCity(query.getCity());
		entity.setTown(query.getTown());
		model.addAttribute("entity", entity);
		
		return "core/basic/school/schoolForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="schoolForm/{id}",method = RequestMethod.POST)
	public String schoolForm(@PathVariable Long id,Model model){
		School entity = schoolService.selectById(id);
		
		//下拉框
		List<DictItem> categorys = dictCatlogService.getSelectItems("SCHOOL_CATEGORY");
		List<DictItem> types = dictCatlogService.getSelectItems("SCHOOL_TYPE");
		List<?> schoolStatus = SchoolStatusEnum.toList();
		List<?> schoolTypes = TeacherTypeEnum.toList();
		
		model.addAttribute("categorys", categorys);
		model.addAttribute("types", types);
		model.addAttribute("schoolStatus", schoolStatus);
		model.addAttribute("schoolTypes", schoolTypes);
		
		//编辑页面，选中下拉框
		List<Province> provinces = provinceService.provinces();
		List<City> citys = cityService.citys(entity.getProvince());
		List<Town> towns = townService.towns(entity.getCity());
		
		model.addAttribute("provinces", provinces);
		model.addAttribute("citys", citys);
		model.addAttribute("towns", towns);
		
		model.addAttribute("entity", entity);
		return "core/basic/school/schoolForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveSchool",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "保存学校信息")
	public String saveSchool(School entity){
		AjaxCallback ok = AjaxCallback.OK(schoolService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteSchoolByIds",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除学校信息")
	public String deleteSchoolByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		List<String> errorList = Lists.newArrayList();
		
		
		String msg = "";
		AjaxCallback ok = AjaxCallback.OK(msg);
		
		String[] split = ids.split(",");
		for(String strId:split){
			//判断学校下是否还有教师
			TeacherQuery queryTeacher = new TeacherQuery();
			queryTeacher.setSchool(Long.parseLong(strId));
			Long count = teacherService.selectListTotal(queryTeacher);
			if(count != 0){
				String schoolName = schoolService.selectById(Long.parseLong(strId)).getName();
				errorList.add("学校【"+schoolName+"】下还有教师，请先调动教师后再删除学校！");
			}
		}
		
		if(errorList.size() > 0){
			msg = "学校下还有教师信息，请先调动教师后再删除学校！";
			ok.setMessage(msg);
			ok.setStatusCode(AjaxCallback.CHECK_FAILD);
		}else{
			msg = "当前学校下已无教师信息，可以删除！";
			ok.setMessage(msg);
			ok.setStatusCode(AjaxCallback.OK);
			
			for(String strId:split){
				idList.add(Long.parseLong(strId));	
			}
			schoolService.deleteByIds(idList);
		}
		ok.setData(errorList);
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 根据town获取学校的下拉框列表
	 * @param town
	 * @return
	 */
	@RequestMapping("schools")
	@ResponseBody
	public String towns(@RequestParam(value="town",required=true) Integer town){
		if(town==null){
			town = 0;
		}
		List<School> schools = schoolService.schools(town);
		//做一个字段名的转换，匹配 townNo-->value  townName-->label
		List<Map<String, Object>> records = Lists.newArrayList();
		
		Map<String, Object> defaultCity = Maps.newHashMap();
		defaultCity.put("value", "");
		defaultCity.put("label", "--学校--");
		records.add(defaultCity);
		
		for(School school:schools){
			Map<String, Object> m = JsonUtils.beanToMap(town);
			m.put("value", school.getId());
			m.put("label", school.getName());
			
			m.remove("id");
			m.remove("name");
			m.remove("createTime");
			m.remove("deleted");
			m.remove("version");
			
			records.add(m);
		}
		return JSON.toJSONString(records);
	}
	
	
	/**
	 * 进入导出向导的dialog
	 * @return
	 */
	@RequestMapping("schoolImport")
	public String schoolImport(Model model){
		
		return "core/basic/school/schoolImport";
	}

	
	/**
     * 导出全部
     * @return
     * @throws Exception
     */
    @RequestMapping("exportAll")
    public void exportAll(HttpServletResponse response) throws Exception{
        //excel的标题数据（只有一条）
        List<String> fieldName = schoolService.getExcelFieldName();
        //excel的内容数据（多条）
        SchoolQuery query = new SchoolQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
        List<List<String>> fieldDatas = schoolService.getExcelDatasByQuery(query);
        ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas,"学校信息表");
    }
    
    /**
     * 根据条件导出
     * @return
     * @throws Exception
     */
    @RequestMapping("exportSearch")
    public void exportSearch(SchoolQuery query,HttpServletResponse response) throws Exception{
        //excel的标题数据（只有一条）
        List<String> fieldName = schoolService.getExcelFieldName();
        //excel的内容数据（多条）
		query = configPstsQuery(query);//配置省市区县查询条件
		
		//url docode
		query.setName(java.net.URLDecoder.decode(query.getName(), "UTF-8"));
		query.setSchoolNo(java.net.URLDecoder.decode(query.getSchoolNo(), "UTF-8"));
		query.setAddress(java.net.URLDecoder.decode(query.getAddress(), "UTF-8"));
		query.setPostCode(java.net.URLDecoder.decode(query.getPostCode(), "UTF-8"));
		query.setMaster(java.net.URLDecoder.decode(query.getMaster(), "UTF-8"));
		query.setTel(java.net.URLDecoder.decode(query.getTel(), "UTF-8"));
		query.setDirector(java.net.URLDecoder.decode(query.getDirector(), "UTF-8"));
		query.setIdCard(java.net.URLDecoder.decode(query.getIdCard(), "UTF-8"));
		query.setTheTel(java.net.URLDecoder.decode(query.getTheTel(), "UTF-8"));
		query.setPhone(java.net.URLDecoder.decode(query.getPhone(), "UTF-8"));
		query.setEmail(java.net.URLDecoder.decode(query.getEmail(), "UTF-8"));
		
        List<List<String>> fieldDatas = schoolService.getExcelDatasByQuery(query);
        ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas,"学校信息表");
    }
    
    /**
	 * 验证excel
	 * 
	 * @param token
	 * @param file
	 * @return
     * @throws Exception 
	 */
	@RequestMapping(value = "checkExcel", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkExcel(@RequestParam(value="file",required=false) MultipartFile file) {
		logger.info("importExcel::");
		
		String msg = "";
		AjaxCallback ok = AjaxCallback.OK(msg);
		
		SchoolQuery query = new SchoolQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		
		List<String> errorList = schoolService.checkExcel(file,query);
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
	 * @param token
	 * @param file
	 * @return
     * @throws Exception 
	 */
	@RequestMapping(value = "importExcel", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "批量导入")
	public String importExcel(@RequestParam(value="file",required=false) MultipartFile file) {
		logger.info("importExcel::");
		
		SchoolQuery query = new SchoolQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		AjaxCallback ok = AjaxCallback.OK("成功导入【"+schoolService.importExcel(file,query)+"】条学校信息！");
	   // Thread.sleep(5000);模拟延时
		
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 进入学校个人中心  表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="schoolProfile")
	public String schoolProfile(Model model){
		User use = ShiroUtils.getCurrentUser();
		
		School entity = schoolService.selectById(use.getSchool());
		
		//下拉框
		List<DictItem> categorys = dictCatlogService.getSelectItems("SCHOOL_CATEGORY");
		List<DictItem> types = dictCatlogService.getSelectItems("SCHOOL_TYPE");
		List<?> schoolStatus = SchoolStatusEnum.toList();
		List<?> schoolTypes = TeacherTypeEnum.toList();
		
		model.addAttribute("categorys", categorys);
		model.addAttribute("types", types);
		model.addAttribute("schoolStatus", schoolStatus);
		model.addAttribute("schoolTypes", schoolTypes);
		
		//编辑页面，选中下拉框
		List<Province> provinces = provinceService.provinces();
		List<City> citys = cityService.citys(entity.getProvince());
		List<Town> towns = townService.towns(entity.getCity());
		
		model.addAttribute("provinces", provinces);
		model.addAttribute("citys", citys);
		model.addAttribute("towns", towns);
		
		model.addAttribute("entity", entity);
		return "core/basic/school/schoolEdit";
	}
	
	/**
	 * 进入学校个人中心 编辑 表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="schoolProfileEdit/{schoolId}")
	public String schoolProfile(@PathVariable Long schoolId, Model model){
		
		School entity = schoolService.selectById(schoolId);
		
		//下拉框
		List<DictItem> categorys = dictCatlogService.getSelectItems("SCHOOL_CATEGORY");
		List<DictItem> types = dictCatlogService.getSelectItems("SCHOOL_TYPE");
		List<?> schoolStatus = SchoolStatusEnum.toList();
		List<?> schoolTypes = TeacherTypeEnum.toList();
		
		model.addAttribute("categorys", categorys);
		model.addAttribute("types", types);
		model.addAttribute("schoolStatus", schoolStatus);
		model.addAttribute("schoolTypes", schoolTypes);
		
		//编辑页面，选中下拉框
		List<Province> provinces = provinceService.provinces();
		List<City> citys = cityService.citys(entity.getProvince());
		List<Town> towns = townService.towns(entity.getCity());
		
		model.addAttribute("provinces", provinces);
		model.addAttribute("citys", citys);
		model.addAttribute("towns", towns);
		
		model.addAttribute("entity", entity);
		return "core/basic/school/schoolEdit";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveSchoolProfile",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "保存本校信息")
	public String saveSchoolProfile(School entity){
		User use = ShiroUtils.getCurrentUser();
		entity.setProvince(use.getProvince());
		entity.setCity(use.getCity());
		entity.setTown(use.getTown());
		AjaxCallback ok = AjaxCallback.OK(schoolService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 进入学校详细信息展示页面
	 * 
	 * 
	 */
	@RequestMapping("schoolViewForm/{id}")
	public String schoolViewForm(@PathVariable Long id, Model model) {
	
		School entity = schoolService.selectById(id);

		// 下拉框
		List<DictItem> categorys = dictCatlogService.getSelectItems("SCHOOL_CATEGORY");
		List<DictItem> types = dictCatlogService.getSelectItems("SCHOOL_TYPE");
		List<?> schoolStatus = SchoolStatusEnum.toList();
		List<?> schoolTypes = TeacherTypeEnum.toList();

		model.addAttribute("categorys", categorys);
		model.addAttribute("types", types);
		model.addAttribute("schoolStatus", schoolStatus);
		model.addAttribute("schoolTypes", schoolTypes);
		
		List<Province> provinces = provinceService.provinces();
		List<City> citys = cityService.citys(entity.getProvince());
		List<Town> towns = townService.towns(entity.getCity());

		model.addAttribute("provinces", provinces);
		model.addAttribute("citys", citys);
		model.addAttribute("towns", towns);

		model.addAttribute("entity", entity);

		return "core/basic/school/schoolViewForm";
	}
	
	/**
	 * 获取学校类型
	 * @return
	 */
	@RequestMapping(value="getSchoolType/{schoolId}",method = RequestMethod.POST)
	@ResponseBody
	public String getSchoolType(@PathVariable Long schoolId){
		Integer schoolType = 0;
		School school = schoolService.selectById(schoolId);
		if(school!=null){
			schoolType = school.getSchoolType();
		}
		AjaxCallback ok = AjaxCallback.OK(schoolType.toString());
		return JSON.toJSONString(ok);
	}
	
}
