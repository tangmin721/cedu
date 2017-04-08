package com.yanxiu.ce.core.statistics.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.core.statistics.dto.ChartData;

/**
 * echart3.0测试类
 * @author tangmin
 * @date 2016年4月25日
 */
@Controller
@RequestMapping("/core/statistics/test")
public class TestEchartsThreeController {
	
	/**
	 * 入门实例
	 * @return
	 */
	@RequestMapping("simple")
	public String simple(){
		return "core/statistics/test/simple";
	}
	
	/**
	 * 测试中国地图
	 * @return
	 */
	@RequestMapping("chinaMap")
	public String chinaMap(){
		return "core/statistics/test/chinaMap";
	}
	
	/**
	 * 简单柱状图测试数据
	 * @return
	 */
	@RequestMapping(value="data.json")
	@ResponseBody
	public String data(){
		List<String> cs = Lists.newArrayList("衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子");
		List<Integer> ds = Lists.newArrayList(5, 20, 36, 10, 10, 20);
		ChartData data = new ChartData();
		data.setCategories(cs);
		data.setData(ds);
		AjaxCallback ok = AjaxCallback.OK("ok");
		ok.setData(data);
		return JSON.toJSONString(ok);
	}

	
	/**
	 * *************测试   折线（面积）图Line  start ***********************
	 */
	@RequestMapping("line")
	public String line(){
		return "core/statistics/test/line";
	}
	
	/**
	 * 简单柱状图测试数据
	 * @return
	 */
	@RequestMapping(value="lineData.json")
	@ResponseBody
	public String lineData(){
		List<String> cs = Lists.newArrayList("周一","周二","周三","周四","周五","周六","周日");
		List<Integer> ds = Lists.newArrayList(11, 11, 15, 13, 12, 13, 10);
		ChartData data = new ChartData();
		data.setCategories(cs);
		data.setData(ds);
		AjaxCallback ok = AjaxCallback.OK("ok");
		ok.setData(data);
		return JSON.toJSONString(ok);
	}
	/**************测试   折线（面积）图Line  end ************************/
	
	
	/**
	 * *************测试   柱状（条形）图Bar  start ***********************
	 */
	@RequestMapping("bar")
	public String bar(){
		return "core/statistics/test/bar";
	}
	
	/**
	 * 简单柱状图测试数据
	 * @return
	 */
	@RequestMapping(value="barData.json")
	@ResponseBody
	public String barData(){
		List<String> cs = Lists.newArrayList("衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子");
		List<Integer> ds = Lists.newArrayList(5, 20, 36, 10, 10, 20);
		ChartData data = new ChartData();
		data.setCategories(cs);
		data.setData(ds);
		AjaxCallback ok = AjaxCallback.OK("ok");
		ok.setData(data);
		return JSON.toJSONString(ok);
	}
	/**************测试   折线（面积）图Line  end ************************/
	
	
	/**
	 * *************测试  饼（圆环）图 Pie  start ***********************
	 */
	@RequestMapping("pie")
	public String pie(){
		return "core/statistics/test/pie";
	}
	
	/**
	 * 饼（圆环）图 Pie测试数据
	 * @return
	 */
	@RequestMapping(value="pieData.json")
	@ResponseBody
	public String pieData(){
		List<String> cs = Lists.newArrayList("衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子");
		List<Integer> ds = Lists.newArrayList(5, 20, 36, 10, 10, 20);
		ChartData data = new ChartData();
		data.setCategories(cs);
		data.setData(ds);
		AjaxCallback ok = AjaxCallback.OK("ok");
		ok.setData(data);
		return JSON.toJSONString(ok);
	}
	/**************测试  测试  饼（圆环）图 Pie  end ************************/
	
	
	/**
	 * *************测试    	地图 Maps  start ***********************
	 */
	@RequestMapping("map")
	public String map(){
		return "core/statistics/test/map";
	}
	
	/**
	 *  	地图 Maps测试数据
	 * @return
	 */
	@RequestMapping(value="mapData.json")
	@ResponseBody
	public String mapData(){
		List<String> cs = Lists.newArrayList("衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子");
		List<Integer> ds = Lists.newArrayList(5, 20, 36, 10, 10, 20);
		ChartData data = new ChartData();
		data.setCategories(cs);
		data.setData(ds);
		AjaxCallback ok = AjaxCallback.OK("ok");
		ok.setData(data);
		return JSON.toJSONString(ok);
	}
	/**************测试   	地图 Maps  end ************************/
}
