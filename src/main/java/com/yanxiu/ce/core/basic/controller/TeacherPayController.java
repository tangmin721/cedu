package com.yanxiu.ce.core.basic.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.yanxiu.ce.core.basic.entity.TeacherPayQuery;
import com.yanxiu.ce.core.basic.service.TeacherPayService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 基本待遇管理
 * @author tangmin
 * @date 2016-12-19 11:26:56
 */
@Controller
@RequestMapping("/core/basic/teacher/teacherPay")
public class TeacherPayController extends BaseController{
	
	@Autowired
	private TeacherPayService teacherPayService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	/**
	 * @param tid
	 * @param query
	 * @param model
	 * @return
	 */
	/**
	 * @param tid
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("teacherPayList/{tid}/{tfs_type}")
	@RequiresPermissions("TeacherPay:read")
	public String list(@PathVariable Long tid, @PathVariable Integer tfs_type, TeacherPayQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		query.setTid(tid);
		Pagination<TeacherPay> page = teacherPayService.selectListPagination(query);
				
		for(TeacherPay pay : page.getList()){
			List<Long> ids = new ArrayList<Long>();
			String fundStr = pay.getInsuranceHousFund();
			String[] fundArr = fundStr.split(",");
			for(int i = 0; i < fundArr.length; i++){
				ids.add(Long.valueOf(fundArr[i]));
			}
			String fund = "";
			List<DictItem> dicts = dictItemService.selectByIds(ids);
			for(DictItem item : dicts){
				fund += item.getName() + ",";
			}
			pay.setInsuranceHousFund(fund.substring(0, fund.lastIndexOf(",")));
		}
		
		//学年
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("tid", tid);
		model.addAttribute("years", years);
		model.addAttribute("tfs_type", tfs_type);
		return "core/basic/teacher/teacherPay/teacherPayList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("teacherPayForm")
	@RequiresPermissions("TeacherPay:create")
	public String teacherPayFormAdd(@RequestParam(value="tid",required=true) Long tid, @RequestParam(value="tfs_type",required=true) Integer tfs_type, Model model){
		TeacherPay entity = new TeacherPay();
		entity.setSeq(teacherPayService.selectMaxSeq(tid)+1);
		entity.setTid(tid);
		
		//五险一金信息获取 字典表 INSURANCE_HOUS_FUND
		List<DictItem> insuranceHousFunds = dictCatlogService.getSelectItems("INSURANCE_HOUS_FUND");
		
		//学年
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");

		model.addAttribute("entity", entity);
		model.addAttribute("tid", tid);
		model.addAttribute("insuranceHousFunds",insuranceHousFunds);
		model.addAttribute("years", years);
		model.addAttribute("tfs_type", tfs_type);
		return "core/basic/teacher/teacherPay/teacherPayForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="teacherPayForm/{id}/{tfs_type}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherPay:update")
	public String teacherPayFormEdit(@PathVariable Long id,@PathVariable Integer tfs_type, Model model){
		TeacherPay entity = teacherPayService.selectById(id);
		
		String fundStr = entity.getInsuranceHousFund();
		String[] fundArr = fundStr.split(",");
		for(int i = 0; i < fundArr.length; i++){
			
		}

		List<DictItem> insuranceHousFunds = dictCatlogService.getSelectItems("INSURANCE_HOUS_FUND");
		for (int i = 0; i < fundArr.length; i++) {
			for (DictItem dict : insuranceHousFunds) {
				if(Long.valueOf(fundArr[i]).longValue() == dict.getId().longValue()){
					dict.setChecked(true);
				}
			}
		}
		
		//学年
		List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
		
		model.addAttribute("entity", entity);
		model.addAttribute("tid", entity.getTid());
		model.addAttribute("insuranceHousFunds",insuranceHousFunds);
		model.addAttribute("years", years);
		model.addAttribute("tfs_type", tfs_type);
		return "core/basic/teacher/teacherPay/teacherPayForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveTeacherPay",method = RequestMethod.POST)
	@RequiresPermissions(value={"TeacherPay:update","TeacherPay:create"},logical=Logical.AND)
	@ResponseBody
	public String saveTeacherPay(TeacherPay entity){
		if(entity.getYear() == null || entity.getYear().intValue() == 0){
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyy");
			Date time = new Date();
			String year = sdf.format(time);
			
			List<DictItem> years = dictCatlogService.getSelectItems("SCHOOL_YEAR");
			for(DictItem dict : years){
				if(Integer.valueOf(dict.getName()).intValue() == (Integer.valueOf(year).intValue()-1)){
					entity.setYear(dict.getId().intValue());
				}
			}
		}
		AjaxCallback ok = AjaxCallback.OK(teacherPayService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteTeacherPayByIds",method = RequestMethod.POST)
	@RequiresPermissions("TeacherPay:delete")
	@ResponseBody
	public String deleteTeacherPayByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		teacherPayService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
