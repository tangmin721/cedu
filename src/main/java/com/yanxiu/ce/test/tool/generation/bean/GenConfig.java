package com.yanxiu.ce.test.tool.generation.bean;

/**
 * 配置
 * @author tangmin
 * @date 2016年2月26日
 */
public class GenConfig {

	private String dbType="mysql";
	
	/**
	 * 包名
	 */
	private String packagePath;
	
	/**
	 * requestMapping路径
	 */
	private String requestMapPath;
	
	/**
	 * tableName
	 */
	private String tableName;
	
	/**
	 * 模块名称
	 */
	private String modelName;

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
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
	
}
