<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushConfigTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.config_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/config/configList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/config/saveConfig" id="f_config_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushConfigTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_config_name" class="control-label x100"><span class="red">*</span>配置名称：</label>
                        <input type="text" name="name" id="f_config_name" value="${entity.name }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_config_theKey" class="control-label x100"><span class="red">*</span>KEY：</label>
                        <input type="text" name="theKey" id="f_config_theKey" value="${entity.theKey }" data-rule="required" maxlength="200" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_config_theValue" class="control-label x100"><span class="red">*</span>VALUE：</label>
                        <input type="text" name="theValue" id="f_config_theValue" value="${entity.theValue }" data-rule="required" maxlength="200" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_config_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_config_memo" data-toggle="autoheight" cols="20" rows="2">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="Config:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>