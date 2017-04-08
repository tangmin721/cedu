<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherSubjectStudyTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_subject_study_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/subjectStudy/teacherSubjectStudyList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/subjectStudy/saveTeacherSubjectStudy" method="post" id="f_teacher_subject_study_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherSubjectStudyTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_subject_study_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="hidden" name="tid" id="f_teacher_subject_study_tid" value="${entity.tid }">
                        <input type="text" name="seq" id="f_teacher_subject_study_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_subject_study_year" class="control-label x100"><span class="red">*</span>年度：</label>
                        <select id="f_teacher_subject_study_year" name="year" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${years }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.year}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_subject_study_level" class="control-label x100"><span class="red">*</span>级别：</label>
                        <select id="f_teacher_subject_study_level" name="level" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${levels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.level}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_subject_study_name" class="control-label x100"><span class="red">*</span>课题名称：</label>
                        <input type="text" name="name" id="f_teacher_subject_study_name" value="${entity.name }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_subject_study_roleType" class="control-label x100"><span class="red">*</span>角色：</label>
                        <select id="f_teacher_subject_study_roleType" name="roleType" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${roleTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.roleType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_subject_study_memo" class="control-label x100">备注：</label>
                        <textarea  name="memo" id="f_teacher_subject_study_memo" data-toggle="autoheight" cols="20" rows="2"  maxlength="200">${entity.memo }</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherSubjectStudy:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>