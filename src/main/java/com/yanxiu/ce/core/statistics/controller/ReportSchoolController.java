package com.yanxiu.ce.core.statistics.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportSchool;
import com.yanxiu.ce.core.statistics.entity.ReportSchoolQuery;
import com.yanxiu.ce.core.statistics.enums.ReportSchoolWayEnum;
import com.yanxiu.ce.core.statistics.service.ReportSchoolService;

/**
 * 教师信息统计
 * @author tangmin
 * @date 2016年8月15日
 */

@Controller
@RequestMapping("/core/statistics/school")
public class ReportSchoolController extends BasePctsController<ReportSchoolQuery>{
	
	@Autowired
	private ReportSchoolService reportSchoolService;
	
	@RequestMapping("main")
	public String main(ReportSchoolQuery query,Model model){
		if(StringUtils.isBlank(query.getWay())){
			query.setWay(ReportSchoolWayEnum.NUMBERS.getValue()+"");
		}
		query = configReportPstsQuery(query);//配置省市区县查询条件
		
		List<?> ways = ReportSchoolWayEnum.toList();
		model.addAttribute("ways", ways);
		model.addAttribute("query", query);
		
		String wayName = "";//副标题
		if(StringUtils.isNotBlank(query.getWay())){
			wayName = ReportSchoolWayEnum.getDesc(Integer.parseInt(query.getWay()));
		}else{
			wayName = ReportSchoolWayEnum.NUMBERS.getDesc();
		}
		model.addAttribute("wayName",wayName);
		
		//获取 echarts的标题，数据
		ReportSchool reportSchool = null;
		List<ReportSchool> rts = reportSchoolService.selectList(query);
		if(rts!=null && rts.size()>0){
			reportSchool = rts.get(0);
		}
		String dataStr = reportSchool==null?"[]":reportSchool.getData();
		model.addAttribute("data", dataStr);
		model.addAttribute("title", reportSchool==null?"[]":reportSchool.getTitle());
		
		//计算总数
		Integer total = 0;
		if(!dataStr.equals("[]")){
			List<NameValue> datas = JSONObject.parseArray(dataStr,NameValue.class);
			for(NameValue nameValue:datas){
				total += Integer.parseInt(nameValue.getValue());
			}
		}
		model.addAttribute("total", total);
		
		return "core/statistics/school/main";
	}
	
	/**
	 * 调用此方法 执行统计
	 * @return
	 */
	@RequestMapping("processSchool")
	@ResponseBody
	public String process(){
		AjaxCallback ok = AjaxCallback.OK(reportSchoolService.processTotal());
		return JSON.toJSONString("成功");
	}
	
}
