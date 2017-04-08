package com.yanxiu.ce.core.basic.controller;

import java.io.IOException;
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
import com.yanxiu.ce.core.basic.entity.TeacherCredent;
import com.yanxiu.ce.core.basic.entity.TeacherCredentQuery;
import com.yanxiu.ce.core.basic.service.TeacherCredentService;
import com.yanxiu.ce.system.dto.AttDto;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.AttachmentService;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 证书管理
 * @author tangmin
 * @date 2016-04-12 15:26:41
 */
@Controller
@RequestMapping("/core/basic/teacher/credent")
public class TeacherCredentController extends BaseController{
	
	/**
	 * 附件的table name
	 */
	private static final String  ATT_TABLE_NAME = "t_tch_credent_att";
	
	@Autowired
	private TeacherCredentService teacherCredentService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherCredentList")
	@RequiresPermissions("TeacherCredent:read")
	public String list(@RequestParam(value="tid",required=true) Long tid,TeacherCredentQuery query,Model model){
		query.setOrderField("seq");//默认是按id排
		query.setTid(tid.toString());
		query.setTidLike(false);
		List<DictItem> credents= dictCatlogService.getSelectItems("CREDENT_TYPE");
		
		Pagination<TeacherCredent> page = teacherCredentService.selectListPagination(query);
		
		//语种信息
		List<DictItem> languageTypes = dictCatlogService.getSelectItems("LANGUAGE_TYPE");
		//掌握程度信息
		List<DictItem> proficiencyLevels = dictCatlogService.getSelectItems("PROFICIENCY_LEVEL");
		//其他技能掌握程度信息
		List<DictItem> otherProficiencyLevels = proficiencyLevels;
		//语言证书名称
		List<DictItem> languageCredentTypes = dictCatlogService.getSelectItems("LANGUAGE_CREDENT_TYPE");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("credents", credents);
		model.addAttribute("languageTypes", languageTypes);
		model.addAttribute("proficiencyLevels", proficiencyLevels);
		model.addAttribute("otherProficiencyLevels", otherProficiencyLevels);
		model.addAttribute("languageCredentTypes", languageCredentTypes);
		return "core/basic/teacher/credent/teacherCredentList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherCredentForm")
	@RequiresPermissions("TeacherCredent:create")
	public String teacherCredentAddForm(@RequestParam(value="tid",required=true) Long tid,Model model){
		TeacherCredent entity = new TeacherCredent();
		entity.setSeq(teacherCredentService.selectMaxSeq(tid)+1);
		List<DictItem> credents= dictCatlogService.getSelectItems("CREDENT_TYPE");
		
		
		//语种信息
		List<DictItem> languageTypes = dictCatlogService.getSelectItems("LANGUAGE_TYPE");
		//掌握程度信息
		List<DictItem> proficiencyLevels = dictCatlogService.getSelectItems("PROFICIENCY_LEVEL");
		//其他技能掌握程度信息
		List<DictItem> otherProficiencyLevels = proficiencyLevels;
		//语言证书名称
		List<DictItem> languageCredentTypes = dictCatlogService.getSelectTreeItems("LANGUAGE_CREDENT_TYPE");
				
		//置空的附件列表
//		List<String> nullList = Lists.newArrayList();
//		model.addAttribute("previews",JSON.toJSONString(nullList));
//		model.addAttribute("previewDatas", JSON.toJSONString(nullList));
		
		entity.setTid(tid);
		
		model.addAttribute("entity", entity);
		model.addAttribute("credents", credents);
		model.addAttribute("languageTypes", languageTypes);
		model.addAttribute("proficiencyLevels", proficiencyLevels);
		model.addAttribute("otherProficiencyLevels", otherProficiencyLevels);
		model.addAttribute("languageCredentTypes", JSON.toJSONString(languageCredentTypes));
		return "core/basic/teacher/credent/teacherCredentForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherCredentForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherCredent:update")
	public String teacherCredentForm(@PathVariable Long id,Model model){
		TeacherCredent entity = teacherCredentService.selectById(id);
		
		List<DictItem> credents= dictCatlogService.getSelectItems("CREDENT_TYPE");
		
		//语种信息
		List<DictItem> languageTypes = dictCatlogService.getSelectItems("LANGUAGE_TYPE");
		//掌握程度信息
		List<DictItem> proficiencyLevels = dictCatlogService.getSelectItems("PROFICIENCY_LEVEL");
		//其他技能掌握程度信息
		List<DictItem> otherProficiencyLevels = proficiencyLevels;
		//语言证书名称
		List<DictItem> languageCredentTypes = dictCatlogService.getSelectTreeItems("LANGUAGE_CREDENT_TYPE");
		String languageCredentTypeName = "";
		if(entity.getLanguageCredentType() != null && entity.getLanguageCredentType().longValue() != 0){
			for(DictItem dict : languageCredentTypes){
				if(dict.getId().longValue() == entity.getLanguageCredentType().longValue()){
					dict.setChecked(true);
				}
			}
			
			languageCredentTypeName = dictItemService.selectById(entity.getLanguageCredentType()).getName();
		}
		
//		//获取附件到前端
//		AttDto attDto = attService.getAttDto(ATT_TABLE_NAME, id);
//		
//		model.addAttribute("previews", JSON.toJSONString(attDto.getPreviews()));
//		model.addAttribute("previewDatas", JSON.toJSONString(attDto.getPreviewDatas()));
		model.addAttribute("entity", entity);
		model.addAttribute("credents", credents);
		model.addAttribute("languageTypes", languageTypes);
		model.addAttribute("proficiencyLevels", proficiencyLevels);
		model.addAttribute("otherProficiencyLevels", otherProficiencyLevels);
		model.addAttribute("languageCredentTypes", JSON.toJSONString(languageCredentTypes));
		model.addAttribute("languageCredentTypeName", languageCredentTypeName);
		return "core/basic/teacher/credent/teacherCredentForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="saveTeacherCredent",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherCredent:update","TeacherCredent:create"},logical=Logical.AND)
	@ResponseBody
	@SystemControllerLog(description = "保存证书")
	public String saveTeacherCredent(TeacherCredent entity,@RequestParam(value="attIds",required=false) String attIds) throws IllegalStateException, IOException{
		User user = ShiroUtils.getCurrentUser();
		AjaxCallback ok = AjaxCallback.OK(teacherCredentService.saveOrUpdate(user.getId(),entity,attIds));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherCredentByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherCredent:delete")
	@ResponseBody
	@SystemControllerLog(description = "删除证书")
	public String deleteTeacherCredentByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherCredentService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}

}
