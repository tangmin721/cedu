package com.yanxiu.ce.test.tool.generation.tmplate;

/**
 * mybatis的xml模板
 * @author tangmin
 * @date 2016年2月26日
 */
public class DaoGenerator extends TemplateCodeGenerator{

	
	@Override
	public String getTemplateFile() {
		/**
		 * 模板文件
		 */
		return "dao.ftl";
	}
	
}
