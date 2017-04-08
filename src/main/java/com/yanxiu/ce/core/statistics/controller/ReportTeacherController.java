package com.yanxiu.ce.core.statistics.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportTeacher;
import com.yanxiu.ce.core.statistics.entity.ReportTeacherQuery;
import com.yanxiu.ce.core.statistics.enums.ReportTeacherWayEnum;
import com.yanxiu.ce.core.statistics.service.ReportTeacherService;

/**
 * 教师信息统计
 * @author tangmin
 * @date 2016年8月15日
 */

@Controller
@RequestMapping("/core/statistics/teacher")
public class ReportTeacherController extends BasePctsController<ReportTeacherQuery>{
	
	@Autowired
	private ReportTeacherService reportTeacherService;
	
	@RequestMapping("main/{tchtype}")
	public String main(@PathVariable Integer tchtype,ReportTeacherQuery query,Model model){
		if(StringUtils.isBlank(query.getWay())){
			query.setWay(ReportTeacherWayEnum.NUMBERS.getValue()+"");
		}
		query = configReportPstsQuery(query);//配置省市区县查询条件
		query.setTchtype(tchtype+"");
		
		String headName = "";
		if(tchtype==TeacherTypeEnum.MIDDLE_SCHOOL.getValue()){
			headName = "教师";
		}else{
			headName = "校长";
		}
		model.addAttribute("headName", headName);
		
		List<?> ways = ReportTeacherWayEnum.toList();
		model.addAttribute("ways", ways);
		model.addAttribute("tchtype", tchtype);
		model.addAttribute("query", query);
		
		String wayName = "";//副标题
		if(StringUtils.isNotBlank(query.getWay())){
			wayName = ReportTeacherWayEnum.getDesc(Integer.parseInt(query.getWay()));
		}else{
			wayName = ReportTeacherWayEnum.NUMBERS.getDesc();
		}
		model.addAttribute("wayName",wayName);
		
		//获取 echarts的标题，数据
		ReportTeacher reportTeacher = null;
		List<ReportTeacher> rts = reportTeacherService.selectList(query);
		if(rts!=null && rts.size()>0){
			reportTeacher = rts.get(0);
		}
		String dataStr = reportTeacher==null?"[]":reportTeacher.getData();
		model.addAttribute("data", dataStr);
		model.addAttribute("title", reportTeacher==null?"[]":reportTeacher.getTitle());
		
		//计算总人数
		Integer total = 0;
		if(!dataStr.equals("[]")){
			List<NameValue> datas = JSONObject.parseArray(dataStr,NameValue.class);
			for(NameValue nameValue:datas){
				total += Integer.parseInt(nameValue.getValue());
			}
		}
		model.addAttribute("total", total);
		
		return "core/statistics/teacher/main";
	}
	
	
	@RequestMapping("mapView")
	public String mapView(){
	
		return "core/statistics/teacher/mapView";
	}
	
	/**
	 * 调用此方法 执行统计
	 * @return
	 */
	@RequestMapping("processTeacher")
	@ResponseBody
	public String process(){
		AjaxCallback ok = AjaxCallback.OK(reportTeacherService.processTeacherTotal());
		return JSON.toJSONString(ok);
	}
	
}
