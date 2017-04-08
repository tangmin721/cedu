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
import com.yanxiu.ce.core.basic.entity.TeacherProfessionTech;
import com.yanxiu.ce.core.basic.entity.TeacherProfessionTechQuery;
import com.yanxiu.ce.core.basic.service.TeacherProfessionTechService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 专业技术职务聘任管理
 * @author tangmin
 * @date 2016-12-16 11:34:37
 */
@Controller
@RequestMapping("/core/basic/teacher/professionTechnical")
public class TeacherProfessionTechController extends BaseController{
	
	@Autowired
	private TeacherProfessionTechService professionTechnicalService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("professionTechnicalList/{tid}")
	@RequiresPermissions("ProfessionTechnical:read")
	public String list(@PathVariable Long tid, TeacherProfessionTechQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		query.setTid(tid);
		Pagination<TeacherProfessionTech> page = professionTechnicalService.selectListPagination(query);
		
		//专业技术聘任职务信息查询 对应字典表 PROFESSION_DUTY
		List<DictItem> professionDutys = dictCatlogService.getSelectItems("PROFESSION_DUTY");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("professionDutys", professionDutys);
		return "core/basic/teacher/professionTechnical/professionTechnicalList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("professionTechnicalForm")
	@RequiresPermissions("ProfessionTechnical:create")
	public String professionTechnicalFormAdd(@RequestParam(value="tid",required=true) Long tid,Model model){
		TeacherProfessionTech entity = new TeacherProfessionTech();
		entity.setSeq(professionTechnicalService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		//专业技术聘任职务信息查询 对应字典表 PROFESSION_DUTY MARRY
		List<DictItem> professionDutys= dictCatlogService.getSelectTreeItems("PROFESSION_DUTY");
		//List<DictItem> professionDutys= dictCatlogService.getSelectTreeItems("MARRY");
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("professionDutys", JSON.toJSONString(professionDutys));
		return "core/basic/teacher/professionTechnical/professionTechnicalForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="professionTechnicalForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("ProfessionTechnical:update")
	public String professionTechnicalFormEdit(@PathVariable Long id,Model model){
		TeacherProfessionTech entity = professionTechnicalService.selectById(id);
		
		String professionDutyName = "";
		List<DictItem> professionDutys= dictCatlogService.getSelectTreeItems("PROFESSION_DUTY");
		
		if(entity.getProfessionDuty() != null && entity.getProfessionDuty().longValue() != 0){
			for (DictItem ent : professionDutys) {
				//单选的情况，多选还需要split
				if (ent.getId().longValue() == entity.getProfessionDuty()){
					ent.setChecked(true);
				}
			}
			DictItem item = dictItemService.selectById(entity.getProfessionDuty());
			if (item != null) {
				professionDutyName = item.getName();
			}
		}
		
		model.addAttribute("professionDutys", JSON.toJSONString(professionDutys));
		model.addAttribute("professionDutyName", professionDutyName);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("entity", entity);
		return "core/basic/teacher/professionTechnical/professionTechnicalForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveProfessionTechnical",method = RequestMethod.POST)
	@RequiresPermissions(value={"ProfessionTechnical:update","ProfessionTechnical:create"},logical=Logical.AND)
	@ResponseBody
	public String saveProfessionTechnical(TeacherProfessionTech entity){
		AjaxCallback ok = AjaxCallback.OK(professionTechnicalService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteProfessionTechnicalByIds",method = RequestMethod.POST)
	@RequiresPermissions("ProfessionTechnical:delete")
	@ResponseBody
	public String deleteProfessionTechnicalByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		professionTechnicalService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
