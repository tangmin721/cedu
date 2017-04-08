<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherContactWayTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		//$('.teacher_contact_way_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/contactWay/teacherContactWayList'});
		$(this).alertmsg("ok", "联系方式信息保存成功");
	}
}
</script>

    <form action="${ctx}/core/basic/teacher/contactWay/saveTeacherContactWay" method="post" id="f_teacher_contact_way_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherContactWayTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="seq" value="1">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_contact_way_address" class="control-label x100">通讯地址：</label>
                        <input type="text" name="address" id="f_teacher_contact_way_address" value="${entity.address }" maxlength="200" size="20">
                    </td>
                    <td>
                        <label for="f_teacher_contact_way_telphone" class="control-label x100">联系电话：</label>
                        <input type="text" name="telphone" id="f_teacher_contact_way_telphone" value="${entity.telphone }" data-rule="tel" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_contact_way_phoneNo" class="control-label x100">手机：</label>
                        <input type="text" name="phoneNo" id="f_teacher_contact_way_phoneNo" value="${entity.phoneNo }" data-rule="mobile" maxlength="11" size="20">
                    </td>
                    <td>
                        <label for="f_teacher_contact_way_email" class="control-label x100">Email：</label>
                        <input type="text" name="email" id="f_teacher_contact_way_email" value="${entity.email }" data-rule="email" maxlength="100" size="20">
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_teacher_contact_way_otherContact" class="control-label x100">其他联系方：</label>
                        <input type="text" name="otherContact" id="f_teacher_contact_way_otherContact" value="${entity.otherContact }" maxlength="200" size="40">
                    </td>
                </tr>
            </tbody>
        </table>
    <div class="bjui-pageFooter">
    <ul>
    	<!--
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        -->
        <shiro:hasPermission name="TeacherContactWay:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
	</div>
    </form>
