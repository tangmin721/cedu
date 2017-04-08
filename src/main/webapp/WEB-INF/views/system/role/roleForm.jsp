<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushRoleTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.role_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/role/roleList'});
		$(this).dialog('closeCurrent');
	}
}

$('#f_role_active').bootstrapSwitch();
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/role/saveRole" id="f_role_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushRoleTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_role_seq" class="control-label x85">序号：</label>
                        <input type="text" name="seq" id="f_role_seq" value="${entity.seq }" data-rule="required;digits" size="15" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_role_name" class="control-label x85"><span class="red">*</span>角色名称：</label>
                        <input type="text" name="name" id="f_role_name" value="${entity.name }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_role_code" class="control-label x85"><span class="red">*</span>角色编码：</label>
                        <input type="text" name="code" id="f_role_code" value="${entity.code }" data-rule="required" size="15">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_role_active" class="control-label x85"><span class="red">*</span>是否激活：</label>
						<input id="f_role_active" type="checkbox"  name="active"  data-size="mini" data-on-color="success" data-off-color="warning" data-on-text="激活"  data-off-text="未激活" <c:if test="${entity.active }">checked</c:if>>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_role_memo" class="control-label x85">备注：</label>
                        <textarea name="memo" id="f_role_memo" data-toggle="autoheight" cols="15" rows="2">${entity.memo}</textarea>
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