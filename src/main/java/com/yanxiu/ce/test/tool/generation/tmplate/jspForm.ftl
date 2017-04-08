<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refush${entity.className}Table(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.${entity.underLineName}_pager_form').bjuiajax('doSearch', {url:'${r"${"}${"ctx"}${r"}"}/${CONFIG.requestMapPath}/${entity.firstLowName}List'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${r"${"}${"ctx"}${r"}"}/${CONFIG.requestMapPath}/save${entity.className}" method="post" id="f_${entity.underLineName}_form" data-toggle="validate" data-alertmsg="false" data-callback="refush${entity.className}Table">
        <input type="hidden" name="id" value="${r"${"}${"entity.id"}${r"}"}">
        <input type="hidden" name="version" value="${r"${"}${"entity.version"}${r"}"}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
<#list entity.myfieldList as e>				 <tr>
                    <td>
                        <label for="f_${entity.underLineName}_${e.fieldName}" class="control-label x100"><span class="red">*</span>字段${e_index}：</label>
                        <input type="text" name="${e.fieldName}" id="f_${entity.underLineName}_${e.fieldName}" value="${r"${"}${"entity.${e.fieldName} "}${r"}"}" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
</#list>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="${entity.className}:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>