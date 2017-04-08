package com.yanxiu.ce.test.tool.generation;

import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherCredent;
import com.yanxiu.ce.core.basic.entity.TeacherStudyExp;
import com.yanxiu.ce.core.basic.entity.TeacherTrainExp;
import com.yanxiu.ce.test.tool.generation.generator.CodeGenerator;

/**
 * 运行此方法生成代码
 * @author tangmin
 * @date 2016年2月26日
 */
public class CodeCreateGeneratorMain {

	/**
	 * 生成代码的main方法，运行此方法即可
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		CodeGenerator codeGen = new CodeGenerator();
		//配置需要生成的类
		codeGen.addClass(TeacherTrainExp.class);
		//配置包名
		//codeGen.setPackagePath("com.yanxiu.ce.core.train");
		codeGen.setPackagePath("com.yanxiu.ce.core.basic");
		//配置requestMap
		//codeGen.setRequestMapPath("core/basic/teacher/trainExp");
		codeGen.setRequestMapPath("core/basic/teacher/trainExp");
		//配置对应的数据库表名
		codeGen.setTableName("t_tch_train_exp");
		//配置模块名称  
		codeGen.setModelName("国内培训");
		//代码生成存放位置  
		codeGen.outPut("d:\\Code\\trainExp");
		System.out.println("代码生成成功");
	}

}
