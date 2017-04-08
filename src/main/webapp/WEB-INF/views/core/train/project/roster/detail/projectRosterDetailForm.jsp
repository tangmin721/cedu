<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProjectRosterDetailTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.project_roster_detail_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/roster/detail/projectRosterDetailList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/roster/detail/saveProjectRosterDetail" id="f_project_roster_detail_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushProjectRosterDetailTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_project_roster_detail_rosterId" class="control-label x100"><span class="red">*</span>名单id：</label>
                        <input type="text" name="rosterId" id="f_project_roster_detail_rosterId" value="${entity.rosterId }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_roster_detail_tid" class="control-label x100"><span class="red">*</span>教师id：</label>
                        <input type="text" name="tid" id="f_project_roster_detail_tid" value="${entity.tid }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="ProjectRosterDetail:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>