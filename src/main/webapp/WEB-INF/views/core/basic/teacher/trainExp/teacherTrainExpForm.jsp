<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

//取消，刷新
$('#cancel_train_exp').off().on('click', function(e) {
	$('#div_tw_train_exp').bjuiajax('refreshLayout', {
		target:'#div_tw_train_exp',
		url:"${ctx}/core/basic/teacher/trainExp/teacherTrainExpList/"+wizard_tid
	});
})

//保存回调
function saveTrainExpCb(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_train_exp_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/trainExp/teacherTrainExpList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent tableContent">
	<form action="${ctx }/core/basic/teacher/trainExp/saveTeacherTrainExp"
		id="train_exp_form" class="pageForm" data-toggle="validate"
		method="post" data-callback="saveTrainExpCb">
		<input type="hidden" name="id" value="${entity.id}"> <input
			type="hidden" name="version" value="${entity.version}">
		<table id="study_exp_edit" class="table table-condensed table-hover"
			style="width: 100%; margin-top: -1px;">
			<tbody>
				<tr>
					<td><label for="f_teacher_train_exp_seq"
						class="control-label x120"><span class="red">*</span>排序：</label> <input
						type="hidden" name="tid" id="f_teacher_train_exp_tid"
						value="${entity.tid }"> <input type="text" name="seq"
						id="f_teacher_train_exp_seq" value="${entity.seq }"
						data-rule="required" size="20" data-toggle="spinner" data-min="0"
						data-max="999" data-step="1" data-rule="integer"></td>
				</tr>
				<!-- 
				<tr>
					<td><label for="f_teacher_train_exp_startDate"
						class="control-label x100"><span class="red">*</span>起始时间：</label>
						<input type="text" name="startDate"
						id="f_teacher_train_exp_startDate" value="<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>"
						size="20" data-toggle="datepicker" data-rule="required;date">
					</td>
				</tr>
				<tr>
					<td><label for="f_teacher_train_exp_endDate"
						class="control-label x100"><span class="red">*</span>结束时间：</label>
						<input type="text" name="endDate" id="f_teacher_train_exp_endDate"
						value="<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>" size="20" data-toggle="datepicker"
						data-rule="required;date"></td>
				</tr>
				-->
				<tr>
                    <td>
                        <label for="f_teacher_train_exp_year" class="control-label x120"><span class="red">*</span>培训年度：</label>
                        <select id="f_teacher_train_exp_year" name="year" data-toggle="selectpicker" data-rule="required" data-width="200px">
							<option value="">--请选择--</option>
							<c:forEach items="${years }" var="item">
								<option value="${item.id }"
									<c:if test="${item.id==entity.year}">selected</c:if>>${item.name }</option>
							</c:forEach>
						</select>
                    </td>
                </tr>
				<tr>
					<td><label for="f_teacher_train_exp_trainType" class="control-label x120"><span class="red">*</span>培训类别：</label>
						<select id="f_teacher_train_exp_trainType" name="trainType" data-toggle="selectpicker" data-rule="required" data-width="200px">
							<option value="">--请选择--</option>
							<c:forEach items="${trainTypes }" var="item">
								<option value="${item.id }"
									<c:if test="${item.id==entity.trainType}">selected</c:if>>${item.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label for="f_teacher_train_exp_projectNamer" class="control-label x120"><span class="red">*</span>培训项目名称：</label>
						<input type="text" name="projectName" id="f_teacher_train_exp_projectName" value="${entity.projectName }" data-rule="required" size="20">
					</td>
				</tr>
				 <tr>
                    <td>
                        <label for="f_teacher_train_exp_trainOrg" class="control-label x120">培训机构名称：</label>
                        <input type="text" name="trainOrg" id="f_teacher_train_exp_trainOrg" value="${entity.trainOrg }" maxlength="100" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_train_exp_trainMode" class="control-label x120"><span class="red">*</span>培训方式：</label>
                        <select id="f_teacher_train_exp_trainMode" name="trainMode" data-toggle="selectpicker" data-rule="required" data-width="200px">
							<option value="">--请选择--</option>
							<c:forEach items="${trainModes }" var="item">
								<option value="${item.id }"
									<c:if test="${item.id==entity.trainMode}">selected</c:if>>${item.name }</option>
							</c:forEach>
						</select>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_train_exp_hours" class="control-label x120"><span class="red">*</span>培训获得学时：</label>
                        <input type="text" name="hours" id="f_teacher_train_exp_hours" value="${entity.hours }" data-rule="required;digits" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_train_exp_score" class="control-label x120"><span class="red" <c:if test="${schoolType != 1}">style="display: none;"</c:if> >*</span>培训获得学分：</label>
                        <input type="text" name="score" id="f_teacher_train_exp_score" value="${entity.score }" <c:if test="${schoolType != 1}">data-rule="digits"</c:if> <c:if test="${schoolType == 1}">data-rule="required;digits"</c:if> maxlength="20" size="20">
                    </td>
                </tr>
				<!--  
				<tr>
					<td colspan="2"><label for="f_teacher_train_exp_memo"
						class="control-label x100">备注：</label> <textarea name="memo"
							id="f_teacher_train_exp_memo" data-toggle="autoheight" cols="20"
							rows="2" maxlength="100">${entity.memo }</textarea></td>
				</tr>
				-->
			</tbody>
		</table>
	</form>
</div>
<div class="bjui-pageFooter">
    <ul>
    	<!-- 
        <li><button id="cancel_train_exp" type="button" class="btn-red" data-icon="refresh">取消</button></li>
         -->
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
         <shiro:hasPermission name="TeacherTrainExp:create">
        <li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>
