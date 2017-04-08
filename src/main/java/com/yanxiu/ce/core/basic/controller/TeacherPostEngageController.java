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
import com.yanxiu.ce.core.basic.entity.TeacherPay;
import com.yanxiu.ce.core.basic.entity.TeacherPostEngage;
import com.yanxiu.ce.core.basic.entity.TeacherPostEngageQuery;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.basic.service.TeacherPostEngageService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 岗位聘任管理
 * @author tangmin
 * @date 2016-12-13 17:55:25
 */
@Controller
@RequestMapping("/core/basic/teacher/postEngage")
public class TeacherPostEngageController extends BaseController{
	
	@Autowired
	private TeacherPostEngageService teacherPostEngageService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherPostEngageList/{tid}/{tfs_type}")
	@RequiresPermissions("TeacherPostEngage:read")
	public String list(@PathVariable Long tid, @PathVariable Integer tfs_type, TeacherPostEngageQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		query.setTid(tid);
		Pagination<TeacherPostEngage> page = teacherPostEngageService.selectListPagination(query);
		
		//岗位类别信息查询
		List<DictItem> postTypes = dictCatlogService.getSelectItems("POST_TYPE");
		//岗位等级信息查询
		List<DictItem> postLevels = dictCatlogService.getSelectItems("POST_LEVEL");
		//是否兼任其他岗位信息查询
		List<DictItem> parttimeFlag = dictCatlogService.getSelectItems("PARTTIME_FLAG");
		//兼任岗位信息查询
		List<DictItem> parttimePostType = dictCatlogService.getSelectItems("PARTTIME_POST_TYPE");
		//校级职务信息查询
		List<DictItem> schoolDutyTypes = null;
		if(tfs_type.intValue() == TeacherTypeEnum.KINDERGARTEN.getValue()){
			schoolDutyTypes = dictCatlogService.getSelectItems("KINDERGARTEN_DUTY_TYPE");
		}else{
			schoolDutyTypes = dictCatlogService.getSelectItems("SCHOOL_DUTY_TYPE");
		}
		for(TeacherPostEngage post : page.getList()){
			List<Long> ids = new ArrayList<Long>();
			String dutyStr = post.getDuty();
			String[] dutyArr = dutyStr.split(",");
			for(int i = 0; i < dutyArr.length; i++){
				ids.add(Long.valueOf(dutyArr[i]));
			}
			String duty = "";
			List<DictItem> dicts = dictItemService.selectByIds(ids);
			for(DictItem item : dicts){
				duty += item.getName() + ",";
			}
			post.setDuty(duty.substring(0, duty.lastIndexOf(",")));
		}
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("postTypes", postTypes);
		model.addAttribute("postLevels", postLevels);
		model.addAttribute("parttimeFlag", parttimeFlag);
		model.addAttribute("parttimePostType", parttimePostType);
		model.addAttribute("schoolDutyTypes", schoolDutyTypes);
		model.addAttribute("tfs_type", tfs_type);
		return "core/basic/teacher/postEngage/teacherPostEngageList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherPostEngageForm")
	@RequiresPermissions("TeacherPostEngage:create")
	public String teacherPostEngageFormAdd(@RequestParam(value="tid",required=true) Long tid,@RequestParam(value="tfs_type",required=true) Integer tfs_type, Model model){
		TeacherPostEngage entity = new TeacherPostEngage();
		entity.setSeq(teacherPostEngageService.selectMaxSeq(tid) + 1);
		
		// 岗位类别信息查询
		List<DictItem> postTypes = dictCatlogService.getSelectItems("POST_TYPE");
		// 岗位等级信息查询
		//List<DictItem> postLevels = dictCatlogService.getSelectItems("POST_LEVEL");
		// 是否兼任其他岗位信息查询
		List<DictItem> parttimeFlag = dictCatlogService.getSelectItems("PARTTIME_FLAG");
		// 兼任岗位信息查询
		//List<DictItem> parttimePostType = dictCatlogService.getSelectItems("PARTTIME_POST_TYPE");
		// 校级职务信息查询
		List<DictItem> schoolDutyTypes = null;
		if(tfs_type.intValue() == TeacherTypeEnum.KINDERGARTEN.getValue()){
			schoolDutyTypes = dictCatlogService.getSelectItems("KINDERGARTEN_DUTY_TYPE");
		}else{
			schoolDutyTypes = dictCatlogService.getSelectItems("SCHOOL_DUTY_TYPE");
		}

		entity.setTid(tid);
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("postTypes", postTypes);
		model.addAttribute("parttimeFlag", parttimeFlag);
		model.addAttribute("schoolDutyTypes", schoolDutyTypes);
		model.addAttribute("tfs_type", tfs_type);
		return "core/basic/teacher/postEngage/teacherPostEngageForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherPostEngageForm/{id}/{tfs_type}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherPostEngage:update")
	public String teacherPostEngageFormEdit(@PathVariable Long id, @PathVariable Integer tfs_type, Model model){
		TeacherPostEngage entity = teacherPostEngageService.selectById(id);
		
		// 岗位类别信息查询
		List<DictItem> postTypes = dictCatlogService.getSelectItems("POST_TYPE");
		// 岗位等级信息查询
		String postLevelName = dictItemService.selectById(entity.getPostLevel().longValue()).getName();
		entity.setPostLevelName(postLevelName);
		// 是否兼任其他岗位信息查询
		List<DictItem> parttimeFlag = dictCatlogService.getSelectItems("PARTTIME_FLAG");
		// 兼任岗位信息查询
		String parttimePostTypeName = null;
		if(entity.getParttimeType() != null && entity.getParttimeType() != 0){
			parttimePostTypeName = dictItemService.selectById(entity.getParttimeType().longValue()).getName();
		}
		entity.setParttimeTypeName(parttimePostTypeName);
		String parttimePostLevelName = null;
		if(entity.getParttimeType() != null && entity.getParttimeType() != 0){
			parttimePostLevelName = dictItemService.selectById(entity.getParttimeLevel().longValue()).getName();
		}
		entity.setParttimeLevelName(parttimePostLevelName);
		// 校级职务信息查询
		List<DictItem> schoolDutyTypes = null;
		if(tfs_type.intValue() == TeacherTypeEnum.KINDERGARTEN.getValue()){
			schoolDutyTypes = dictCatlogService.getSelectItems("KINDERGARTEN_DUTY_TYPE");
		}else{
			schoolDutyTypes = dictCatlogService.getSelectItems("SCHOOL_DUTY_TYPE");
		}
		String dutyTypes = entity.getDuty();
		String[] dutyTypesArr = dutyTypes.split(",");

		for (int i = 0; i < dutyTypesArr.length; i++) {
			for (DictItem dict : schoolDutyTypes) {
				if(Long.valueOf(dutyTypesArr[i]).longValue() == dict.getId().longValue()){
					dict.setChecked(true);
				}
			}
		}

		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("postTypes", postTypes);
		model.addAttribute("parttimeFlag", parttimeFlag);
		model.addAttribute("schoolDutyTypes", schoolDutyTypes);
		model.addAttribute("tfs_type", tfs_type);
		return "core/basic/teacher/postEngage/teacherPostEngageForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherPostEngage",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherPostEngage:update","TeacherPostEngage:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherPostEngage(TeacherPostEngage entity){
		System.out.println("entity.postType="+entity.getPostType());
		AjaxCallback ok = AjaxCallback.OK(teacherPostEngageService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherPostEngageByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherPostEngage:delete")
	@ResponseBody
	public String deleteTeacherPostEngageByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherPostEngageService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 岗位类别和岗位等级联动
	 * @param
	 * @return
	 */
	@RequestMapping(value="postLevel/{postType}",method = RequestMethod.POST)
	@ResponseBody
	public String postLevel(@PathVariable Long postType){
		
		System.out.println("postType="+postType);
		DictItem dictItem = dictItemService.selectById(postType);
		String postCode = dictItem.getName();
		// 岗位类别信息查询
		List<DictItem> postTypes = dictCatlogService.getSelectItems("POST_TYPE");
		// 岗位等级信息查询
		List<DictItem> postLevels = dictCatlogService.getSelectItems("POST_LEVEL");
		
		List<DictItem> dict = new ArrayList<DictItem>();
		
		for(DictItem post : postTypes){
			System.out.println("post="+post.getId()+",code="+ post.getCode());
			if(postCode.equals(post.getName())){
				if("1-教师岗位".equals(postCode) || "2-其他专业技术岗位".equals(postCode)){
					for(DictItem postlevel : postLevels){
						if(postlevel.getName().startsWith("1") || postlevel.getName().startsWith("9")){
							dict.add(postlevel);
						}
					}
				}
				if("3-管理岗位".equals(postCode)){
					for(DictItem postlevel : postLevels){
						if(postlevel.getName().startsWith("2") || postlevel.getName().startsWith("9")){
							dict.add(postlevel);
						}
					}
				}
				if("4-工勤技能岗位".equals(postCode)){
					for(DictItem postlevel : postLevels){
						if(postlevel.getName().startsWith("3") || postlevel.getName().startsWith("9")){
							dict.add(postlevel);
						}
					}
				}
			}
		}
		
		return JSON.toJSONString(dict);
	}
	
	/**
	 * 兼任岗位联动
	 * @param
	 * @return
	 */
	@RequestMapping(value="postType",method = RequestMethod.POST)
	@ResponseBody
	public String postType(){
		
		// 兼任岗位类别信息查询
		List<DictItem> postTypes = dictCatlogService.getSelectItems("PARTTIME_POST_TYPE");

		return JSON.toJSONString(postTypes);
	}
	
}
