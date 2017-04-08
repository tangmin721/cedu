<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushOrgTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.org_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/org/orgList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/org/saveOrg" id="f_org_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushOrgTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_org_pid" class="control-label x85"><span class="red">*</span>字段0：</label>
                        <input type="text" name="pid" id="f_org_pid" value="${entity.pid }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_org_name" class="control-label x85"><span class="red">*</span>字段1：</label>
                        <input type="text" name="name" id="f_org_name" value="${entity.name }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_org_code" class="control-label x85"><span class="red">*</span>字段2：</label>
                        <input type="text" name="code" id="f_org_code" value="${entity.code }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_org_seq" class="control-label x85"><span class="red">*</span>字段3：</label>
                        <input type="text" name="seq" id="f_org_seq" value="${entity.seq }" data-rule="required;digits" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_org_memo" class="control-label x85"><span class="red">*</span>字段4：</label>
                        <input type="text" name="memo" id="f_org_memo" value="${entity.memo }" data-rule="required" size="15">
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