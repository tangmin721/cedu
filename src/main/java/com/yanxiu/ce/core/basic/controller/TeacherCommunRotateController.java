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
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.TeacherCommunRotate;
import com.yanxiu.ce.core.basic.entity.TeacherCommunRotateQuery;
import com.yanxiu.ce.core.basic.service.TeacherCommunRotateService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 交流轮岗管理
 * @author tangmin
 * @date 2016-12-29 10:35:05
 */
@Controller
@RequestMapping("/core/basic/teacher/communRotate")
public class TeacherCommunRotateController extends BaseController{
	
	@Autowired
	private TeacherCommunRotateService teacherCommunRotateService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherCommunRotateList/{tid}/{tfs_type}")
	@RequiresPermissions("TeacherCommunRotate:read")
	public String list(@PathVariable Long tid, @PathVariable Integer tfs_type, TeacherCommunRotateQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		if(tfs_type.intValue() == 0){
			query.setTid(tid);
			Pagination<TeacherCommunRotate> page = teacherCommunRotateService.selectListPagination(query);
		
			//轮岗类型信息查询
			List<DictItem> rotateTypes = dictCatlogService.getSelectItems("ROTATE_TYPE");
			//是否调动人事关系信息查询
			List<DictItem> isFlags = dictCatlogService.getSelectItems("IS_FLAG");
				
			model.addAttribute("query", query);
			model.addAttribute("page", page);
			model.addAttribute("tid", tid);
			model.addAttribute("rotateTypes", rotateTypes);
			model.addAttribute("isFlags", isFlags);
		
			return "core/basic/teacher/communRotate/teacherCommunRotateList";
		}else{
			return "core/basic/teacher/teacherPrompt";
		}
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherCommunRotateForm")
	@RequiresPermissions("TeacherCommunRotate:create")
	public String teacherCommunRotateFormAdd(@RequestParam(value="tid", required=true) Long tid,@RequestParam(value="tfs_type", required=true) Integer tfs_type, Model model){
		TeacherCommunRotate entity = new TeacherCommunRotate();
		entity.setSeq(teacherCommunRotateService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		//轮岗类型信息查询
		List<DictItem> rotateTypes = dictCatlogService.getSelectTreeItems("ROTATE_TYPE");
		//是否调动人事关系信息查询
		List<DictItem> isFlags = dictCatlogService.getSelectItems("IS_FLAG");
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("rotateTypes", JSON.toJSONString(rotateTypes));
		model.addAttribute("isFlags", isFlags);
		model.addAttribute("tfs_type", tfs_type);
		return "core/basic/teacher/communRotate/teacherCommunRotateForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherCommunRotateForm/{id}/{tfs_type}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherCommunRotate:update")
	public String teacherCommunRotateFormEdit(@PathVariable Long id,@PathVariable Integer tfs_type,Model model){
		TeacherCommunRotate entity = teacherCommunRotateService.selectById(id);
		
		//轮岗类型信息查询
		List<DictItem> rotateTypes = dictCatlogService.getSelectTreeItems("ROTATE_TYPE");
		
		String rotateName = "";
		if(entity.getRotateType() != null && entity.getRotateType().longValue() != 0){
			for (DictItem ent : rotateTypes) {
				// 单选的情况，多选还需要split
				if (ent.getId().longValue() == entity.getRotateType().longValue()) {
					ent.setChecked(true);
				}
			}
			
			DictItem dict = dictItemService.selectById(entity.getRotateType());
			rotateName = dict.getName();
		}
		
		//是否调动人事关系信息查询
		List<DictItem> isFlags = dictCatlogService.getSelectItems("IS_FLAG");
				
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("rotateTypes", JSON.toJSONString(rotateTypes));
		model.addAttribute("isFlags", isFlags);
		model.addAttribute("rotateName",rotateName);
		model.addAttribute("tfs_type",tfs_type);
		return "core/basic/teacher/communRotate/teacherCommunRotateForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherCommunRotate",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherCommunRotate:update","TeacherCommunRotate:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherCommunRotate(TeacherCommunRotate entity){
		AjaxCallback ok = AjaxCallback.OK(teacherCommunRotateService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherCommunRotateByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherCommunRotate:delete")
	@ResponseBody
	public String deleteTeacherCommunRotateByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherCommunRotateService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
