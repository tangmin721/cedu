<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

//取消，刷新
$('#cancel_academic').off().on('click', function(e) {
	$('.teacher_academic_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/academic/teacherAcademicList/'+wizard_tid});
	$(this).dialog('closeCurrent');
})

//保存回调
function saveAcademicCb(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_academic_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/academic/teacherAcademicList/'+wizard_tid});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent tableContent">
    <form action="${ctx }/core/basic/teacher/academic/saveTeacherAcademic" id="academic_form" class="pageForm" data-toggle="validate" method="post" data-callback="saveAcademicCb">
       	<input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table id="teacher_academic_edit"  class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	<tr>
                    <td>
                        <label for="f_teacher_academic_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="hidden" name="tid" id="f_teacher_academic_tid" value="${entity.tid }">
                        <input type="text" name="seq" id="f_teacher_academic_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
                <tr>
                <tr>
                    <td>
                        <label for="f_teacher_academic_name" class="control-label x100"><span class="red">*</span>名称：</label>
                        <input type="text" name="name" id="f_teacher_academic_name" value="${entity.name }" data-rule="required"  size="20">
                    </td>
                 </tr>
                 <tr>
                	<td>
                        <label for="f_teacher_academic_publish_date" class="control-label x100"><span class="red">*</span>发布年度：</label>
                        <select id="f_teacher_academic_publish_date" name="publishDate" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${years }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.publishDate}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_academic_pnum" class="control-label x100"><span class="red">*</span>篇数：</label>
                        <input type="text" name="pnum" id="f_teacher_academic_pnum" value="${entity.pnum }"  data-rule="required;digits"  size="20">
                    </td>
                 </tr>
                 <tr>
                    <td>
                        <label for="f_teacher_academic_zsize" class="control-label x100"><span class="red">*</span>字数：</label>
                        <input type="text" name="zsize" id="f_teacher_academic_zsize" value="${entity.zsize }"   data-rule="required;digits" size="20">
                    </td>
                 </tr>
                 <tr>
                    <td>
                        <label for="f_teacher_academic_publish_source" class="control-label x100"><span class="red">*</span>出版源：</label>
                        <input type="text" name="publishSource" id="f_teacher_academic_publish_source" value="${entity.publishSource }"   data-rule="required" size="20">
                    </td>
                 </tr>
                 <tr>
                	<td>
                		<label for="f_teacher_academic_role_type" class="control-label x100"><span class="red">*</span>角色：</label>
                        <select id="f_teacher_academic_role_type" name="roleType" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${roleTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.roleType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                	</td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_teacher_academic_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_teacher_academic_memo" data-toggle="autoheight" rows="1" maxlength="100">${entity.memo }</textarea>
                    </td>
                 </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
    	<!-- 
       <li><button id="cancel_academic" type="button" class="btn-red" data-icon="refresh">取消</button></li>
        -->
       	<li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherAcademic:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>
