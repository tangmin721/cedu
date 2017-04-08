package com.yanxiu.ce.test.tool.generation.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.yanxiu.ce.test.tool.generation.bean.EntityInfo;
import com.yanxiu.ce.test.tool.generation.bean.GenConfig;
import com.yanxiu.ce.test.tool.generation.tmplate.ControllerGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.DaoGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.JspFormGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.JspListGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.JunitGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.MybatisXmlGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.MybatisXmlJoinGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.MysqlDDLGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.QueryGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.ServiceGenerator;
import com.yanxiu.ce.test.tool.generation.tmplate.ServiceImplGenerator;

/**
 * 代码生成器
 * @author tangmin
 * @date 2016年2月26日
 */
public class CodeGenerator
{

	List<Class<?>> clazzList = new ArrayList<Class<?>>();
	
	/**
	 * 包名
	 */
	private String packagePath;
	
	/**
	 * 实体对应的表名
	 */
	private String tableName;
	
	
	/**
	 * requestMapping路径
	 */
	private String requestMapPath;
	
	/**
	 * 模块名
	 */
	private String modelName;

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	

	public String getRequestMapPath() {
		return requestMapPath;
	}

	public void setRequestMapPath(String requestMapPath) {
		this.requestMapPath = requestMapPath;
	}
	
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public CodeGenerator addClass(Class<?> clazz)
	{
		this.clazzList.add(clazz);
		return this;
	}

	public void writeFile(File dir, String fileName, String content)
			throws Exception
	{
		File file = new File(dir, fileName);
		if (!file.exists())
		{
			file.createNewFile();
		}
		Writer writer = new FileWriter(file);
		writer.write(content);
		writer.flush();
		writer.close();
	}

	/**
	 * 配置生成文件存放位置，生成的表名
	 * @param path
	 * @param tableName
	 * @throws Exception
	 */
	public void outPut(String path) throws Exception
	{
		File dir = new File(path);
		if (!dir.exists())
		{
			dir.mkdir();
		}
		GenConfig genConfig = new GenConfig();
		//加载配置
		genConfig.setPackagePath(packagePath);
		genConfig.setTableName(tableName);
		genConfig.setRequestMapPath(requestMapPath);
		genConfig.setModelName(modelName);
		
		//根据模板生成代码
		MysqlDDLGenerator mysql = new MysqlDDLGenerator();
		MybatisXmlGenerator xmlGenerator = new MybatisXmlGenerator();
		MybatisXmlJoinGenerator xmlJoinGenerator = new MybatisXmlJoinGenerator();
		QueryGenerator query = new QueryGenerator();
		DaoGenerator dao = new DaoGenerator();
		ServiceGenerator service = new ServiceGenerator();
		ServiceImplGenerator serviceImpl = new ServiceImplGenerator();
		ControllerGenerator controller = new ControllerGenerator();
		JunitGenerator junit = new JunitGenerator();
		JspListGenerator jspList = new JspListGenerator();
		JspFormGenerator jspForm = new JspFormGenerator();
		for (Class<?> clazz : this.clazzList)
		{
			EntityInfo entity = new EntityInfo(clazz,tableName);
			writeFile(dir,tableName+".sql",
					mysql.generateCode(entity, genConfig));
			writeFile(dir, entity.getClassName() + "Query.java",
					query.generateCode(entity, genConfig));
			writeFile(dir, entity.getClassName() + "Dao.xml",
					xmlGenerator.generateCode(entity, genConfig));
			writeFile(dir, "JOIN_"+entity.getClassName() + "Dao.xml",
					xmlJoinGenerator.generateCode(entity, genConfig));
			writeFile(dir, entity.getClassName() + "Dao.java",
					dao.generateCode(entity, genConfig));
			writeFile(dir, entity.getClassName() + "Service.java",
					service.generateCode(entity, genConfig));
			writeFile(dir, entity.getClassName() + "ServiceImpl.java",
					serviceImpl.generateCode(entity, genConfig));
			writeFile(dir, entity.getClassName() + "Controller.java",
					controller.generateCode(entity, genConfig));
			writeFile(dir, "Test"+entity.getClassName() + ".java",
					junit.generateCode(entity, genConfig));
			writeFile(dir, entity.getFirstLowName() + "List.jsp",
					jspList.generateCode(entity, genConfig));
			writeFile(dir, entity.getFirstLowName() + "Form.jsp",
					jspForm.generateCode(entity, genConfig));
			
		}
	}


	
}
