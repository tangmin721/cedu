<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProjectOperatorTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.project_operator_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/operator/projectOperatorList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/operator/saveProjectOperator" id="f_project_operator_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushProjectOperatorTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_project_operator_province" class="control-label x100"><span class="red">*</span>省：</label>
                        <input type="text" name="province" id="f_project_operator_province" value="${entity.province }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_operator_city" class="control-label x100"><span class="red">*</span>市：</label>
                        <input type="text" name="city" id="f_project_operator_city" value="${entity.city }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_operator_town" class="control-label x100"><span class="red">*</span>区县：</label>
                        <input type="text" name="town" id="f_project_operator_town" value="${entity.town }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_operator_school" class="control-label x100"><span class="red">*</span>字段3：</label>
                        <input type="text" name="school" id="f_project_operator_school" value="${entity.school }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_operator_name" class="control-label x100"><span class="red">*</span>执行人：</label>
                        <input type="text" name="name" id="f_project_operator_name" value="${entity.name }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_operator_tel" class="control-label x100">办公电话：</label>
                        <input type="text" name="tel" id="f_project_operator_tel" value="${entity.tel }" data-rule="tel" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_operator_mobile" class="control-label x100"><span class="red">*</span>手机：</label>
                        <input type="text" name="mobile" id="f_project_operator_mobile" value="${entity.mobile }" data-rule="mobile" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_operator_email" class="control-label x100">Eamil：</label>
                        <input type="text" name="email" id="f_project_operator_email" value="${entity.email }" data-rule="email" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_operator_dept" class="control-label x100">所在部门：</label>
                        <input type="text" name="dept" id="f_project_operator_dept" value="${entity.dept }"  maxlength="20" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="ProjectOperator:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>