package com.yanxiu.ce.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.exception.BizException;
import com.yanxiu.ce.common.utils.excel.ExcelFileGenerator;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.SchoolQuery;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.train.entity.TrainOrg;
import com.yanxiu.ce.core.train.service.TrainOrgService;
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.entity.Town;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserQuery;
import com.yanxiu.ce.system.enums.UserStatusEnum;
import com.yanxiu.ce.system.enums.UserTypeCityEnum;
import com.yanxiu.ce.system.enums.UserTypeEnum;
import com.yanxiu.ce.system.enums.UserTypeProvinceEnum;
import com.yanxiu.ce.system.enums.UserTypeSchoolEnum;
import com.yanxiu.ce.system.enums.UserTypeSysEnum;
import com.yanxiu.ce.system.enums.UserTypeTownEnum;
import com.yanxiu.ce.system.enums.UserTypeTrainEnum;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.UserRoleService;
import com.yanxiu.ce.system.service.UserService;

/**
 * 用户管理
 * @author tangmin
 * @date 2016-03-08 11:27:47
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BasePctsController<UserQuery>{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private TrainOrgService trainOrgService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("userList")
	@RequiresPermissions("User:read")
	@SystemControllerLog(description = "查询用户")   
	public String list(UserQuery query,Model model){
		query = configPstsQuery(query);//配置省市区县查询条件
		
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setFields("u.id,u.userno,u.loginname,u.realname,u.mobileno,u.email,u.unit,u.status,u.type,p.provincename,c.cityname,t.townname,s.name as schoolname");
		
		Pagination<User> page = userService.selectListPagination(query);
		
		User currentUser = ShiroUtils.getCurrentUser();
		List<?> userTypes = confUserTypeList(currentUser);
		List<?> userStatus = UserStatusEnum.toList();
		List<TrainOrg> trainOrgs = trainOrgService.selectTrainOrgs();
		
		model.addAttribute("userTypes", userTypes);
		model.addAttribute("userStatus", userStatus);
		model.addAttribute("trainOrgs", trainOrgs);
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "system/user/userList";
	}

	
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("userAddForm")
	@RequiresPermissions("User:create")
	public String userForm(Model model){
		User entity = new User();
		
		User currentUser = ShiroUtils.getCurrentUser();
		List<?> userTypes = confUserTypeList(currentUser);
		
		UserQuery query = new UserQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		
		
		List<?> userStatus = UserStatusEnum.toList();
		List<TrainOrg> trainOrgs = trainOrgService.selectTrainOrgs();
		model.addAttribute("userTypes", userTypes);
		model.addAttribute("userStatus", userStatus);
		model.addAttribute("trainOrgs", trainOrgs);
		model.addAttribute("entity", entity);
		
		model.addAttribute("query", query);
		return "system/user/userAddForm";
	}


	/**
	 * 配置用户能创建的用户类型下拉框
	 * @param userTypes
	 * @param currentUser
	 * @return
	 */
	private List<?> confUserTypeList(User currentUser) {
		if(currentUser.getType()==UserTypeEnum.SUP_ADMIN.getValue() || currentUser.getType()==UserTypeEnum.SYS_ADMIN.getValue()){
			return UserTypeSysEnum.toList();
		}
		if(currentUser.getType()==UserTypeEnum.PROVINCE_ADMIN.getValue()){
			return UserTypeProvinceEnum.toList();
		}
		if(currentUser.getType()==UserTypeEnum.CITY_ADMIN.getValue()){
			return UserTypeCityEnum.toList();
		}
		if(currentUser.getType()==UserTypeEnum.TOWN_ADMIN.getValue()){
			return UserTypeTownEnum.toList();
		}
		if(currentUser.getType()==UserTypeEnum.SCHOOL_ADMIN.getValue()){
			return UserTypeSchoolEnum.toList();
		}
		if(currentUser.getType()==UserTypeEnum.TRAINORG.getValue()){
			return UserTypeTrainEnum.toList();
		}
		return null;
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="userUpdateForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("User:update")
	public String userUpdateForm(@PathVariable Long id,Model model){
		User entity = userService.selectById(id);
		
		User currentUser = ShiroUtils.getCurrentUser();
		List<?> userTypes = confUserTypeList(currentUser);
		
		List<?> userStatus = UserStatusEnum.toList();
		List<TrainOrg> trainOrgs = trainOrgService.selectTrainOrgs();
		model.addAttribute("userTypes", userTypes);
		model.addAttribute("userStatus", userStatus);
		model.addAttribute("trainOrgs", trainOrgs);
		model.addAttribute("entity", entity);
		
		//编辑页面，选中下拉框
		List<Province> provinces = provinceService.provinces();
		List<City> citys = cityService.citys(entity.getProvince());
		List<Town> towns = townService.towns(entity.getCity());
		List<School> schools = schoolService.schools(entity.getTown());
		
		model.addAttribute("provinces", provinces);
		model.addAttribute("citys", citys);
		model.addAttribute("towns", towns);
		model.addAttribute("schools", schools);
		
		return "system/user/userUpdateForm";
	}
	
	/**
	 * 进入首页，查看我的资料页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="userProfileForm")
	public String userProfileForm(Model model){
		User entity = ShiroUtils.getCurrentUser();
		
		List<?> userTypes = UserTypeSysEnum.toList();
		
		List<?> userStatus = UserStatusEnum.toList();
		List<TrainOrg> trainOrgs = trainOrgService.selectTrainOrgs();
		model.addAttribute("userTypes", userTypes);
		model.addAttribute("userStatus", userStatus);
		model.addAttribute("trainOrgs", trainOrgs);
		model.addAttribute("entity", userService.selectById(entity.getId()));
		
		//编辑页面，选中下拉框
		List<Province> provinces = provinceService.provinces();
		List<City> citys = cityService.citys(entity.getProvince());
		List<Town> towns = townService.towns(entity.getCity());
		List<School> schools = schoolService.schools(entity.getTown());
		
		model.addAttribute("provinces", provinces);
		model.addAttribute("citys", citys);
		model.addAttribute("towns", towns);
		model.addAttribute("schools", schools);
		
		return "system/user/userProfileForm";
	}
	
	
	
	/**
	 * 进入修改密码form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="userPwdForm",method = RequestMethod.POST)
	public String userPwdForm(){
		return "system/user/userPwdForm";
	}
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveUser",method = RequestMethod.POST)
	@RequiresPermissions(value={"User:update","User:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "增加or修改用户")
	public String saveUser(User entity,@RequestParam(value="newPwd1",required=false) String newPwd1,
			@RequestParam(value="newPwd2",required=false) String newPwd2){
		if(entity.getId()==null){
			if(!newPwd1.equals(newPwd2)){
				throw new BizException("确认的两次密码不匹配");
			}
			entity.setPlainPassword(newPwd1);
		}
		if(entity.getProvince()==null){
			entity.setProvince(0);
		}
		if(entity.getCity()==null){
			entity.setCity(0);
		}
		if(entity.getTown()==null){
			entity.setTown(0);
		}
		if(entity.getSchool()==null){
			entity.setSchool(0l);
		}
		AjaxCallback ok = AjaxCallback.OK(userService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 修改密码保存
	 * @param loginPwd
	 * @param newPwd1
	 * @param newPwd2
	 * @return
	 */
	@RequestMapping(value="updatePwd",method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "修改密码")
	public String updatePwd(@RequestParam(value="loginPwd",required=true) String loginPwd,
			@RequestParam(value="newPwd1",required=true) String newPwd1,
			@RequestParam(value="newPwd2",required=true) String newPwd2){
		if(!newPwd1.equals(newPwd2)){
			throw new BizException("确认的两次密码不匹配");
		}
		User user = ShiroUtils.getCurrentUser();
		user.setPlainPassword(loginPwd);
		AjaxCallback ok = AjaxCallback.OK(userService.updatePwd(user, newPwd1));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="resetPwd/{id}",method = RequestMethod.POST)
	@ResponseBody
	public String resetPwd(@PathVariable Long id){
		AjaxCallback ok = AjaxCallback.OK(userService.resetPwd(id));
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteUserByIds",method = RequestMethod.POST)
	@RequiresPermissions("User:delete")
	@ResponseBody
	public String deleteUserByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		userService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	
	
	/**
	 * 进入用户-角色分配页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="userRoleManage/{id}",method = RequestMethod.POST)
	public String userRoleManage(@PathVariable Long id,Model model){
		//已分配的角色
		List<Role> hadRoles = userRoleService.selectRolesByUserId(id);
		
		//未分配角色
		List<Role> unHadRoles = userRoleService.selectNotRolesByUserId(id);
		model.addAttribute("hadRoles", hadRoles);
		model.addAttribute("unHadRoles", unHadRoles);
		model.addAttribute("userId", id);//返回用户id
		return "system/user/userRoleManage";
	}

	/**
	 * 保存用户-角色关系
	 * @param request
	 * @return
	 */
	@RequestMapping(value="saveUserRole",method = RequestMethod.POST)
	@ResponseBody
	public String saveUser(HttpServletRequest request,@RequestParam(value="userId",required=true) Long userId){
		String[] roleStrIds = request.getParameterValues("from[]");
		List<Long> roleIds = Lists.newArrayList();
		if(roleStrIds!=null){
			for(String roleStrId:roleStrIds){
				roleIds.add(Long.parseLong(roleStrId));
			}
		}
		AjaxCallback ok = AjaxCallback.OK(userRoleService.saveUserRole(userId,roleIds));
		return JSON.toJSONString(ok);
	}
	
	/**
     * 导出全部
     * @return
     * @throws Exception
     */
    @RequestMapping("exportAll")
    @SystemControllerLog(description = "导出全部")
    public void exportAll(HttpServletResponse response) throws Exception{
        //excel的标题数据（只有一条）
    	List<String> fieldName = null;
    	String excelName = "";
    	
		fieldName = userService.getExcelFieldName();
		excelName = "用户信息表";

        //excel的内容数据（多条）
		UserQuery query = new UserQuery();
		query = configPstsQuery(query);//配置省市区县查询条件
		
		List<List<String>> fieldDatas = userService.getExcelDatasByQuery(query);
		ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas,excelName);
    }
    
    /**
     * 根据条件导出
     * @return
     * @throws Exception
     */
    @RequestMapping("exportSearch")
    @SystemControllerLog(description = "根据条件导出")
    public void exportSearch(UserQuery query,HttpServletResponse response) throws Exception{
        //excel的标题数据（只有一条）
    	List<String> fieldName = null;
    	String excelName = "";
    	
    	fieldName = userService.getExcelFieldName();
		excelName = "用户信息表";
    	
        //excel的内容数据（多条）
		query = configPstsQuery(query);//配置省市区县查询条件
		
		//url docode
		query.setRealName(java.net.URLDecoder.decode(query.getRealName(), "UTF-8"));
		query.setRealNameLike(true);
		//用户类型
		query.setType(java.net.URLDecoder.decode(query.getType(), "UTF-8"));
		query.setTypeLike(false);
		//单位	
		query.setUnit(java.net.URLDecoder.decode(query.getUnit(), "UTF-8"));
		query.setUnitLike(true);
		//登录名
		query.setLoginName(java.net.URLDecoder.decode(query.getLoginName(), "UTF-8"));
		query.setLoginNameLike(false);
		//状态
		query.setStatus(java.net.URLDecoder.decode(query.getStatus(), "UTF-8"));
		query.setStatusLike(false);
		
        List<List<String>> fieldDatas = userService.getExcelDatasByQuery(query);
        ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas,excelName);
    }
	
}
