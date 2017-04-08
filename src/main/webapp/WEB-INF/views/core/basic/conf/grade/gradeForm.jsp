<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushGradeTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.grade_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/conf/grade/gradeList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/conf/grade/saveGrade" id="f_grade_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushGradeTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_grade_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_grade_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_grade_stageId" class="control-label x100"><span class="red">*</span>学段：</label>
                        <select id="f_grade_stageId" name="stageId" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${stages }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.stageId}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_grade_name" class="control-label x100"><span class="red">*</span>名称：</label>
                        <input type="text" name="name" id="f_grade_name" value="${entity.name }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_grade_code" class="control-label x100"><span class="red">*</span>编码：</label>
                        <input type="text" name="code" id="f_grade_code" value="${entity.code }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_grade_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_grade_memo" data-toggle="autoheight" cols="20" rows="2">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="Grade:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>