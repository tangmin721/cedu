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
import com.yanxiu.ce.core.basic.entity.TeacherContactWay;
import com.yanxiu.ce.core.basic.entity.TeacherContactWayQuery;
import com.yanxiu.ce.core.basic.service.TeacherContactWayService;

/**
 * 联系方式管理
 * @author tangmin
 * @date 2016-12-29 17:58:06
 */
@Controller
@RequestMapping("/core/basic/teacher/contactWay")
public class TeacherContactWayController extends BaseController{
	
	@Autowired
	private TeacherContactWayService teacherContactWayService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("teacherContactWayList/{tid}/{tfs_type}")
	@RequiresPermissions("TeacherContactWay:read")
	public String list(@PathVariable Long tid, @PathVariable Integer tfs_type, TeacherContactWayQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		if(tfs_type.intValue() == 0 || tfs_type.intValue() == 3){
			TeacherContactWay entity = new TeacherContactWay();
			entity.setTid(tid);
		
			query.setTid(tid);
			query.setTidLike(false);
			List<TeacherContactWay> entityList = teacherContactWayService.selectList(query);
			if(entityList != null && entityList.size() > 0){
				entity = entityList.get(0);
			}
		
			model.addAttribute("query", query);
			model.addAttribute("entity", entity);
			model.addAttribute("tid", tid);
			return "core/basic/teacher/contactWay/teacherContactWayForm";
		}else{
			return "core/basic/teacher/teacherPrompt";
		}
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherContactWayForm")
	@RequiresPermissions("TeacherContactWay:create")
	public String teacherContactWayFormAdd(@RequestParam(value="tid", required=true) Long tid, Model model){
		TeacherContactWay entity = new TeacherContactWay();
		entity.setSeq(teacherContactWayService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		return "core/basic/teacher/contactWay/teacherContactWayForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherContactWayForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherContactWay:update")
	public String teacherContactWayFormEdit(@PathVariable Long id,Model model){
		TeacherContactWay entity = teacherContactWayService.selectById(id);
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		return "core/basic/teacher/contactWay/teacherContactWayForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherContactWay",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherContactWay:update","TeacherContactWay:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherContactWay(TeacherContactWay entity){
		AjaxCallback ok = AjaxCallback.OK(teacherContactWayService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherContactWayByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherContactWay:delete")
	@ResponseBody
	public String deleteTeacherContactWayByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherContactWayService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
