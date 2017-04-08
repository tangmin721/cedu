package com.yanxiu.ce.core.train.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.aop.SystemControllerLog;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.OrderField;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.utils.excel.ExcelFileGenerator;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.train.entity.Expert;
import com.yanxiu.ce.core.train.entity.ExpertQuery;
import com.yanxiu.ce.core.train.service.ExpertService;

/**
 * 专家信息管理
 * @author tangmin
 * @date 2016-07-29 15:49:04
 */
@Controller
@RequestMapping("/core/train/expert")
public class ExpertController extends BaseController{
	
	@Autowired
	private ExpertService expertService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("expertList")
	@RequiresPermissions("Expert:read")
	public String list(ExpertQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		List<OrderField> orderFields = new ArrayList<OrderField>();
		OrderField orderFiled1 = new OrderField("CONVERT(name using gbk)", "asc");//设置按中文排序
		OrderField orderFiled2 = new OrderField("CONVERT(dept using gbk)", "asc");
		orderFields.add(orderFiled1);
		orderFields.add(orderFiled2);
		query.setOrderFields(orderFields);
		Pagination<Expert> page = expertService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/train/expert/expertList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("expertForm")
	@RequiresPermissions("Expert:create")
	public String expertForm(Model model){
		Expert entity = new Expert();
		model.addAttribute("entity", entity);
		return "core/train/expert/expertForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="expertForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("Expert:update")
	public String expertForm(@PathVariable Long id,Model model){
		Expert entity = expertService.selectById(id);
		model.addAttribute("entity", entity);
		return "core/train/expert/expertForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveExpert",method = RequestMethod.POST)
	@RequiresPermissions(value={"Expert:update","Expert:create"},logical=Logical.AND)
	@ResponseBody
	public String saveExpert(Expert entity){
		AjaxCallback ok = AjaxCallback.OK(expertService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteExpertByIds",method = RequestMethod.POST)
	@RequiresPermissions("Expert:delete")
	@ResponseBody
	public String deleteExpertByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		expertService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 进入导入信息界面
	 * 
	 */
	@RequestMapping("expertImport")
	public String expertImportForm(Model model){
		
		return "core/train/expert/expertImport";
	}
	
	/**
	 * 导入excel验证
	 * 
	 * 
	 */
	@RequestMapping(value = "checkExcel", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkExcel(@RequestParam(value="file",required=false) MultipartFile file){
		logger.info("importExcel::");
		
		String msg = "";
		AjaxCallback ok = AjaxCallback.OK(msg);
				
		List<String> errorList = expertService.checkExcel(file);
		if(errorList.size()>0){
			msg = "验证失败！请对比错误信息修改Excel文件数据！";
			ok.setMessage(msg);
			ok.setStatusCode(AjaxCallback.CHECK_FAILD);
		}else{
			msg = "验证成功！";
			ok.setMessage(msg);
		}
		
		ok.setData(errorList);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * excel表格数据入表
	 * 
	 * 
	 */
	@RequestMapping(value = "importExcel", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "批量导入专家信息")
	public String importExcel(@RequestParam(value="file",required=false) MultipartFile file) throws ParseException {
		logger.info("importExcel::");
		
		AjaxCallback ok = AjaxCallback.OK("成功导入【"+expertService.importExcel(file)+"】条信息！");
	   // Thread.sleep(5000);模拟延时
		return JSON.toJSONString(ok);
	}
	
	@RequestMapping("expertViewForm/{id}")
	public String expertViewForm(@PathVariable Long id, Model model){
		
		Expert entity = expertService.selectById(id);
		
		model.addAttribute("entity", entity);
		return "core/train/expert/expertViewForm";
	}
	
	/**
	 * 
	 * 导出全部专家信息
	 * 
	 */
	@RequestMapping("exportAll")
    @SystemControllerLog(description = "导出全部")
	public void exportAll(HttpServletResponse response) throws Exception{
		//excel的标题数据（只有一条）
    	List<String> fieldName = expertService.getExcelFieldName();
    	
        //excel的内容数据（多条）
        ExpertQuery query = new ExpertQuery();	
        List<OrderField> orderFields = new ArrayList<OrderField>();
		OrderField orderFiled1 = new OrderField("CONVERT(name using gbk)", "asc");//设置按中文排序
		OrderField orderFiled2 = new OrderField("CONVERT(dept using gbk)", "asc");
		orderFields.add(orderFiled1);
		orderFields.add(orderFiled2);
		query.setOrderFields(orderFields);
        List<List<String>> fieldDatas = expertService.getExcelDatasByQuery(query);
        
		ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas, "专家信息表");
		
	}
	
	/**
	 * 
	 * 按查询条件导出专家信息
	 * 
	 */
	@RequestMapping("exportSearch")
    @SystemControllerLog(description = "导出部分")
	public void exportSearch(HttpServletResponse response,ExpertQuery query) throws Exception{
		//excel的标题数据（只有一条）
    	List<String> fieldName = expertService.getExcelFieldName();
    	
    	List<OrderField> orderFields = new ArrayList<OrderField>();
		OrderField orderFiled1 = new OrderField("CONVERT(name using gbk)", "asc");//设置按中文排序
		OrderField orderFiled2 = new OrderField("CONVERT(dept using gbk)", "asc");
		orderFields.add(orderFiled1);
		orderFields.add(orderFiled2);
		query.setOrderFields(orderFields);
		//url docode
		query.setName(java.net.URLDecoder.decode(query.getName(), "UTF-8"));
		query.setIdCard(java.net.URLDecoder.decode(query.getIdCard(), "UTF-8"));
		query.setDept(java.net.URLDecoder.decode(query.getDept(), "UTF-8"));
		query.setIdCard(java.net.URLDecoder.decode(query.getTitle(), "UTF-8"));
		if(query.getMobile() != null){
			query.setMobile(java.net.URLDecoder.decode(query.getMobile(), "UTF-8"));
		}
		if(query.getEmail() != null){
			query.setEmail(java.net.URLDecoder.decode(query.getEmail(), "UTF-8"));
		}
		query.setDirection(java.net.URLDecoder.decode(query.getDirection(), "UTF-8"));
		if(query.getStartTime() != null){
			query.setStartTime(query.getStartTime());
		}
		if(query.getEndTime() != null){
			query.setEndTime(query.getEndTime());
		}
		query.setGoodRate(java.net.URLDecoder.decode(query.getGoodRate(), "UTF-8"));
		query.setCourseName(java.net.URLDecoder.decode(query.getCourseName(), "UTF-8"));
		List<List<String>> fieldDatas = expertService.getExcelDatasByQuery(query);
		
		ExcelFileGenerator.excelExportDownload(response, fieldName, fieldDatas, "专家信息表");
	}

}
