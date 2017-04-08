package com.yanxiu.ce.core.statistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/core/statistics/fr")
public class TestFineReport {
	/**
	 * 入门实例
	 * @return
	 */
	@RequestMapping("test/{id}")
	public String test(@PathVariable Long id,Model model){
		model.addAttribute("id", id);
		return "core/statistics/fr/test";
	}
	
	/**
	 * tab
	 * @return
	 */
	@RequestMapping("tab")
	public String tab(){
		return "core/statistics/fr/tab";
	}
}
