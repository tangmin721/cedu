<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/teacher.css">

<script type="text/javascript">
function refushTeacherAssessTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_assess_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/assess/teacherAssessList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/assess/saveTeacherAssess" method="post" id="f_teacher_assess_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherAssessTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <div>
        	<span class="teacher_top_title_my"><span class="red">*</span>从本年度开始填写</span> 	
	    </div>
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				  <tr>
                    <td>
                        <label for="f_teacher_assess_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_assess_seq" value="${entity.seq }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_assess_cyear" class="control-label x100"><span class="red">*</span>考核年度：</label>
                        <select id="f_teacher_assess_cyear" name="cyear" data-toggle="selectpicker" data-width="200px" data-rule="required">
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${cyears }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.cyear}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_assess_checkResult" class="control-label x100"><span class="red">*</span>考核结果：</label>
                        <select id="f_teacher_assess_checkResult" name="checkResult" data-toggle="selectpicker" data-width="200px" data-rule="required">
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${checkResults }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.checkResult}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_assess_assessUnit" class="control-label x100"><span class="red" <c:if test="${schoolType != 1}">style="display: none;"</c:if> >*</span>考核单位名称：</label>
                        <input type="text" name="assessUnit" id="f_teacher_assess_assessUnit" value="<c:if test="${empty entity.assessUnit}">${defSchool }</c:if><c:if test="${not empty entity.assessUnit}">${entity.assessUnit }</c:if>"  <c:if test="${schoolType == 1}">data-rule="required"</c:if>  maxlength="200" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherAssess:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>