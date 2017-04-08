<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProjectTrainOrgTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.project_train_org_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/trainorg/projectTrainOrgList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/trainorg/saveProjectTrainOrg" id="f_project_train_org_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushProjectTrainOrgTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_project_train_org_pid" class="control-label x100"><span class="red">*</span>字段0：</label>
                        <input type="text" name="pid" id="f_project_train_org_pid" value="${entity.pid }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_train_org_orgid" class="control-label x100"><span class="red">*</span>字段1：</label>
                        <input type="text" name="orgid" id="f_project_train_org_orgid" value="${entity.orgid }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="ProjectTrainOrg:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>