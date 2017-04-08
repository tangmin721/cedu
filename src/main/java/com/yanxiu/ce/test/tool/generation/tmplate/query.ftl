package ${CONFIG.packagePath}.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * ${CONFIG.modelName}管理
 * @author tangmin
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
public class ${entity.className}Query extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
<#list entity.myfieldList as e>		
	private String ${e.fieldName};
	private Boolean ${e.fieldName}Like = true;
	
</#list>

<#list entity.myfieldList as e>		
	public String get${e.supFiledName}() {
		return ${e.fieldName};
	}
	public void set${e.supFiledName}(String ${e.fieldName}) {
		this.${e.fieldName} = ${e.fieldName};
	}
	public Boolean get${e.supFiledName}Like() {
		return ${e.fieldName}Like;
	}
	public void set${e.supFiledName}Like(Boolean ${e.fieldName}Like) {
		this.${e.fieldName}Like = ${e.fieldName}Like;
	}
</#list>
	
}