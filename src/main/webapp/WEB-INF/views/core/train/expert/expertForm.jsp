<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushExpertTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.expert_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/expert/expertList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/expert/saveExpert" id="f_expert_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushExpertTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	<tr>
                    <td>
                        <label for="f_expert_courseName" class="control-label x100">课程名称：</label>
                        <textarea name="courseName" id="f_expert_courseName" data-toggle="autoheight" cols="20" rows="2" maxlength="100" >${entity.courseName }</textarea>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_name" class="control-label x100"><span class="red">*</span>姓名：</label>
                        <input type="text" name="name" id="f_expert_name" value="${entity.name }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_idCard" class="control-label x100">身份证：</label>
                        <input type="text" name="idCard" id="f_expert_idCard" value="${entity.idCard }" data-rule="ID_card" maxlength="18" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_dept" class="control-label x100">单位：</label>
                        <input type="text" name="dept" id="f_expert_dept" value="${entity.dept }"  maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_title" class="control-label x100">职称：</label>
                        <input type="text" name="title" id="f_expert_title" value="${entity.title }" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_mobile" class="control-label x100">联系电话：</label>
                        <input type="text" name="mobile" id="f_expert_mobile" value="${entity.mobile }"  maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_email" class="control-label x100">电子邮箱：</label>
                        <input type="text" name="email" id="f_expert_email" value="${entity.email }" data-rule="email" maxlength="100" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_direction" class="control-label x100">研究方向：</label>
                        <input type="text" name="direction" id="f_expert_direction" value="${entity.direction }" maxlength="50" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_time" class="control-label x100">授课时间：</label>
                        <input type="text" name="time" id="f_expert_time" value='<fmt:formatDate value="${entity.time }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_goodRate" class="control-label x100">优良率：</label>
                        <input type="text" name="goodRate" id="f_expert_goodRate" value="${entity.goodRate }" maxlength="20" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="Expert:update">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>