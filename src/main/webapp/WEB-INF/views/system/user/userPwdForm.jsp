<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushUserTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
        $(this).alertmsg('ok', json.message);
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/user/updatePwd" id="ff_user_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushUserTable">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="ff_user_loginPwd" class="control-label x120"><span class="red">*</span>旧密码：</label>
                        <input type="password" name="loginPwd" id="ff_user_loginPwd" data-rule="required;password"  maxlength="32" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="ff_user_newPwd1" class="control-label x120"><span class="red">*</span>新密码：</label>
                        <input type="password" name="newPwd1" id="ff_user_newPwd1"  data-rule="密码:required;password"  maxlength="32" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="ff_user_newPwd2" class="control-label x120"><span class="red">*</span>确认新密码：</label>
                        <input type="password" name="newPwd2" id="ff_user_newPwd2"  data-rule="确认密码:required;match(newPwd1);password"  maxlength="32" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
    </ul>
</div>