<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushUserLogTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.user_log_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/userlog/userLogList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/userlog/saveUserLog" id="f_user_log_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushUserLogTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_user_log_loginName" class="control-label x100"><span class="red">*</span>登录名：</label>
                        <input type="text" name="loginName" id="f_user_log_loginName" value="${entity.loginName }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_log_operateStatus" class="control-label x100"><span class="red">*</span>操作状态：</label>
                        <input type="text" name="operateStatus" id="f_user_log_operateStatus" value="${entity.operateStatus }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_log_ip" class="control-label x100"><span class="red">*</span>Ip：</label>
                        <input type="text" name="ip" id="f_user_log_ip" value="${entity.ip }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_log_description" class="control-label x100"><span class="red">*</span>操作描述：</label>
                        <input type="text" name="description" id="f_user_log_description" value="${entity.description }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_log_content" class="control-label x100"><span class="red">*</span>详细方法：</label>
                        <input type="text" name="content" id="f_user_log_content" value="${entity.content }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_log_operType" class="control-label x100"><span class="red">*</span>操作类型：</label>
                        <input type="text" name="operType" id="f_user_log_operType" value="${entity.operType }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="UserLog:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>