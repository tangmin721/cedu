<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherArchivalTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$(this).alertmsg('ok', json.message);
		$('#div_tw_archival').bjuiajax('refreshLayout', {
				target:'#div_tw_archival',
				url:"${ctx}/core/basic/teacher/archival/teacherArchivalForm/"+wizard_tid
		});
	}
}

//取消，刷新
$('#cancel_archival').off().on('click', function(e) {
	$('#div_tw_archival').bjuiajax('refreshLayout', {
		target:'#div_tw_archival',
		type:'post',
		url:"${ctx}/core/basic/teacher/archival/teacherArchivalForm/"+wizard_tid
	});
})
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/archival/saveTeacherArchival" method="post" id="f_teacher_archival_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherArchivalTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_archival_personType" class="control-label x100"><span class="red">*</span>人员类型：</label>
                        <input type="hidden" name="tid" id="f_teacher_archival_tid" value="${tid }" >
                        <select id="f_teacher_archival_personType" name="personType" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${personTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.personType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_archival_duty" class="control-label x100">行政职务：</label>
                         <select id="f_teacher_archival_duty" name="duty" data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${dutys }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.duty}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_archival_title" class="control-label x100">职称：</label>
                        <select id="f_teacher_archival_title" name="title" data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${titles }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.title}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_archival_qualify" class="control-label x100"><span class="red">*</span>教师资格：</label>
                         <select id="f_teacher_archival_qualify" name="qualify" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${qualifys }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.qualify}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_archival_boneType" class="control-label x100">骨干类型：</label>
                        <select id="f_teacher_archival_jobLevel" name="boneType" data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${boneTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.boneType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                
				 <tr>
                    <td>
                        <label for="f_teacher_archival_jobLevel" class="control-label x100">非专任岗位：</label>
                         <select id="f_teacher_archival_jobLevel" name="jobLevel" data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${jobLevels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.jobLevel}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                
				 <tr>
                    <td>
                        <label for="f_teacher_archival_politic" class="control-label x100"><span class="red">*</span>政治面貌：</label>
                        <select id="f_teacher_archival_politic" name="politic" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${politics }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.politic}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_archival_joinDay" class="control-label x100">参加党派时间：</label>
                        <input type="text" name="joinDay" id="f_teacher_archival_joinDay" value='<fmt:formatDate value="${entity.joinDay }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_archival_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_teacher_archival_memo" data-toggle="autoheight" cols="40" rows="5" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button id="cancel_archival" type="button" class="btn-red" data-icon="refresh">取消</button></li>
        <shiro:hasPermission name="TeacherArchival:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>