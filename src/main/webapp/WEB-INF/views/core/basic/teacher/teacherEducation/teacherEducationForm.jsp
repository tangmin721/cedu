<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherEducationTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_education_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherEducation/teacherEducationList/${tid}/${tfs_type}'});
		$(this).dialog('closeCurrent');
	}
}

function f_teacher_course_type(){
	return ${tchCourseTypes};
}

function f_teacher_secondarySubjectType(){
	return ${secondarySubjectTypes};
}

function getSchoolType(){
	return ${tfs_type};
}
//树下拉选择框：：选择事件
function pt_S_NodeCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true)
	var ids = '', names = ''

	for (var i = 0; i < nodes.length; i++) {
		ids   += ','+ nodes[i].id
		names += ','+ nodes[i].name
	}
	if (ids.length > 0) {
		ids = ids.substr(1), names = names.substr(1)
	}

	var $from = $('#'+ treeId).data('fromObj')

	if ($from && $from.length) {
		$from.val(names)
		/* if(names == "无"){
			$("#f_profession_technical_startDate").val("");
			$("#f_profession_technical_endDate").val("");
			$("#f_profession_technical_unitName").val("");
			$("#f_profession_technical_startDate").attr("disabled", true);
			$("#f_profession_technical_endDate").attr("disabled", true);
			$("#f_profession_technical_unitName").attr("disabled", true);
		}else{
			$("#f_profession_technical_startDate").attr("disabled", false);
			$("#f_profession_technical_endDate").attr("disabled", false);
			$("#f_profession_technical_unitName").attr("disabled", false);
		} */
	}
	$('#'+treeId+'_id').val(ids);
	$('#f_teacher_secondarySubjectType_ztree').trigger('validate');

}

$("#f_teacher_education_tchSegment").change(function (){
	var segments = $("#f_teacher_education_tchSegment option:selected").text();
	var str = segments.split(" ");
	for(var i = 0; i < str.length; i++){
		if(str[i] == "0-无" && str.length > 2){
			$(this).alertmsg('warn', '选择【0-无】时，不能进行其他选项的选择！')
			$("#f_teacher_education_tchSegment option").removeAttr("selected");
			$("#f_teacher_education_tchSegment").selectpicker('refresh');
			return;
		}
		if(str[i] == "0-无" && str.length == 2){
			$("#f_teacher_education_tchStatus option").removeAttr("selected");
			$("#f_teacher_education_tchStatus").selectpicker('refresh');
			$("#f_teacher_education_tchStatus option[value=81103]").attr("selected",true);
			$('#f_teacher_education_tchStatus').selectpicker('refresh');
		}
	}
});

$("#f_teacher_education_tchStatus").change(function(){
	var status = $("#f_teacher_education_tchStatus option:selected").text();
	if(status == "99-其他"){
		$("#f_teacher_education_tchStatusDesc").val("");
		$("#f_teacher_education_tchStatusDesc").attr("disabled", false);
	}else{
		$("#f_teacher_education_tchStatusDesc").val("");
		$("#f_teacher_education_tchStatusDesc").attr("disabled", true);
	}
	if(status == "20-任课"){
		$("#f_teacher_tchCourseType_ztree").attr("disabled", false);
		$("#f_teacher_education_classHour").val("");
		$("#f_teacher_education_classHour").attr("disabled", false);
		$("#f_teacher_education_otherClassHour").val("");
		$("#f_teacher_education_otherClassHour").attr("disabled", false);
		$("#f_teacher_education_secondaryCourseType").attr("disabled", false);
		$("#f_teacher_education_secondaryCourseType option").removeAttr("disabled");
		$("#f_teacher_education_secondaryCourseType").selectpicker('refresh');
		$("#f_teacher_secondarySubjectType_ztree").attr("disabled", false);
		$("#f_teacher_education_otherJobType").attr("disabled", false);
		$("#f_teacher_education_otherJobType").selectpicker('refresh');
		if(getSchoolType() == 1){
			$("#f_teacher_education_secondaryCourseType_span").show();
			$("#f_teacher_education_secondarySubjectType_span").show();
			
			$("#f_teacher_education_secondaryCourseType").attr("data-rule", "required");
			$("#f_teacher_secondarySubjectType_ztree").attr("data-rule", "required");
		}
		$("#f_teacher_education_classHour_span").show();
		$("#f_teacher_education_classHour").attr("data-rule", "required;digits");
		
	}else{
		$("#f_teacher_education_tchCourseType_id").val("");
		$("#f_teacher_tchCourseType_ztree").val("");
		$("#f_teacher_tchCourseType_ztree").attr("disabled", true);
		
		$("#f_teacher_education_classHour").val("");
		$("#f_teacher_education_classHour_span").hide();
		$('#f_teacher_education_form').validator("setField", "classHour", "");
		$('#f_teacher_education_classHour').trigger('validate');
		$("#f_teacher_education_classHour").attr("disabled", true);
		
		$("#f_teacher_education_otherClassHour").val("");
		$("#f_teacher_education_otherClassHour").attr("disabled", true);
		$("#f_teacher_education_secondaryCourseType option").removeAttr("selected");
		$("#f_teacher_education_secondaryCourseType").selectpicker('refresh');
		
		$("#f_teacher_education_secondarySubjectType_id").val("");
		$("#f_teacher_secondarySubjectType_ztree").val("");
		
		$("#f_teacher_education_otherJobType option").removeAttr("selected");
		$("#f_teacher_education_otherJobType").selectpicker('refresh');
		$("#f_teacher_education_otherJobType").attr("disabled", true);
		
		if(getSchoolType() == 1){
			$("#f_teacher_education_secondaryCourseType_span").hide();
			$("#f_teacher_education_secondarySubjectType_span").hide();
			
			$('#f_teacher_education_form').validator("setField", "secondaryCourseType", "");
			$('#f_teacher_education_form').validator("setField", "f_teacher_secondarySubjectType_ztree_name", "");
			$("#f_teacher_education_secondaryCourseType").trigger('validate');
			$("#f_teacher_secondarySubjectType_ztree").trigger('validate');
		}		
		$("#f_teacher_education_secondaryCourseType").attr("disabled", true);
		$("#f_teacher_secondarySubjectType_ztree").attr("disabled", true);
	}
	
});

$("#f_teacher_education_partTimeJob").change(function(){
	var partJob = $("#f_teacher_education_partTimeJob option:selected").text();
	if(partJob == "99-其他"){
		$("#f_teacher_education_partTimeName").val("");
		$("#f_teacher_education_partTimeName").attr("disabled", false);
	}else{
		$("#f_teacher_education_partTimeName").val("");
		$("#f_teacher_education_partTimeName").attr("disabled", true);
	}
	
});
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/teacherEducation/saveTeacherEducation" method="post" id="f_teacher_education_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherEducationTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_education_seq" class="control-label x130"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_education_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_education_year" class="control-label x130"><span class="red">*</span>学年：</label>
                        <select id="f_teacher_education_year" name="year" data-toggle="selectpicker" data-rule="required" data-width="200px">
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${years }" var="item">
			               		<option  value="${item.id }" <c:if test="${empty entity.year and item.name == currentYear}">selected</c:if><c:if test="${item.id==entity.year}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_education_term" class="control-label x130"><span class="red">*</span>学期：</label>
                        <select id="f_teacher_education_term" name="term" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<c:forEach items="${terms }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.term}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                <c:if test="${tfs_type == 0 || tfs_type == 2 }">
				 <tr>
                    <td>
                        <label for="f_teacher_education_tchSegment" class="control-label x130"><span class="red">*</span>任教学段：</label>
                        <select id="f_teacher_education_tchSegment" name="tchSegment" data-toggle="selectpicker" data-rule="required" multiple data-width="200px">
			               	<c:forEach items="${tchSegments }" var="item">         		
			               		<option  value="${item.id }" <c:if test="${item.checked}">selected</c:if>>${item.name } </option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                </c:if>
				 <tr>
                    <td>
                        <label for="f_teacher_education_tchStatus" class="control-label x130"><span class="red">*</span>任课状况：</label>
                        <select id="f_teacher_education_tchStatus" name="tchStatus" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<c:forEach items="${tchStatuss }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.tchStatus}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_education_tchStatusDesc" class="control-label x130">任课状况为其他情况的具体说明：</label>
                        <input type="text" name="tchStatusDesc" id="f_teacher_education_tchStatusDesc" value="${entity.tchStatusDesc }" maxlength="20" size="20">
                    </td>
                </tr>
                <c:if test="${tfs_type == 0 }">
				<tr>
                    <td>
                        <label for="f_teacher_education_tchCourseType" class="control-label x130">任教课程：</label>
						<input type="hidden" name="tchCourseType" value="${entity.tchCourseType}" id="f_teacher_education_tchCourseType_id" >
                        <input type="text" id="f_teacher_tchCourseType_ztree" value="${tchCourseTypeName}" data-toggle="selectztree" size="20" data-tree="#f_teacher_education_tchCourseType" readonly>
                        <ul style="height: 278px" id="f_teacher_education_tchCourseType" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="checkbox" data-radio-type="all" data-on-check="pt_S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_course_type'}"
                            >
                        </ul>
                    </td>
                </tr>	 
                </c:if>
                <c:if test="${tfs_type == 2 }">
				<tr>
                    <td>
                        <label for="f_teacher_education_spetchCourseType" class="control-label x130"><span class="red">*</span>任教课程：</label>
						<select id="f_teacher_education_spetchCourseType" name="specialTchCourseType" data-toggle="selectpicker" data-rule="required" data-width="200px">
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${specialTchCourseTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.specialTchCourseType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>	 
                </c:if>
                <c:if test="${tfs_type == 1 }">
				 <tr>
                    <td>
                        <label for="f_teacher_education_secondaryCourseType" class="control-label x130"><span class="red" <c:if test="${entity.tchStatus != 81102 and not empty entity.tchStatus}">style="display: none;"</c:if> id="f_teacher_education_secondaryCourseType_span">*</span>任课课程类别：</label>
                        <select id="f_teacher_education_secondaryCourseType" name="secondaryCourseType" data-toggle="selectpicker" data-width="200px" data-rule="required" <c:if test="${not empty entity.tchStatus and empty entity.secondaryCourseType}">disabled="true"</c:if>>
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${secondaryCourseTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.secondaryCourseType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_education_secondarySubjectType" class="control-label x130"><span class="red" <c:if test="${entity.tchStatus != 81102 and not empty entity.tchStatus}">style="display: none;"</c:if> id="f_teacher_education_secondarySubjectType_span">*</span>任课学科类别：</label>
						<input type="hidden" name="secondarySubjectType" value="${entity.secondarySubjectType}" id="f_teacher_education_secondarySubjectType_id" >
                        <input type="text" id="f_teacher_secondarySubjectType_ztree" name="f_teacher_secondarySubjectType_ztree_name" value="${secondarySubjectTypeName}" data-toggle="selectztree" size="20" <c:if test="${not empty entity.tchStatus and empty secondarySubjectTypeName}">disabled="true"</c:if> data-tree="#f_teacher_education_secondarySubjectType" data-rule="required" readonly>
                        <ul style="height: 220px" id="f_teacher_education_secondarySubjectType" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="pt_S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_secondarySubjectType'}"
                            >
                        </ul>
                    </td>
                </tr>
                </c:if>
				 <tr>
                    <td>
                        <label for="f_teacher_education_classHour" class="control-label x130"><span class="red" id="f_teacher_education_classHour_span" <c:if test="${entity.tchStatus != 81102 and not empty entity.tchStatus}">style="display: none;"</c:if>>*</span>平均每周课堂教学课时数：</label>
                        <input type="text" name="classHour" id="f_teacher_education_classHour" value="<c:if test="${empty entity.classHour}">0</c:if><c:if test="${not empty entity.classHour}">${entity.classHour }</c:if>" data-rule="required;digits" <c:if test="${not empty entity.tchStatus and empty entity.classHour}">disabled="true"</c:if> maxlength="20" size="20">
                    </td>
                </tr>
                <c:if test="${tfs_type == 2 }">
				 <tr>
                    <td>
                        <label for="f_teacher_education_otherJobType" class="control-label x130">承担的其他工作：</label>
                        <select id="f_teacher_education_otherJobType" name="otherJobType" data-toggle="selectpicker" data-width="200px">
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${otherJobTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.otherJobType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                </c:if>
				 <tr>
                    <td>
                        <label for="f_teacher_education_otherClassHour" class="control-label x130">平均每周其他工作折合教学课时数：</label>
                        <input type="text" name="otherClassHour" id="f_teacher_education_otherClassHour" value="<c:if test="${empty entity.otherClassHour}">0</c:if><c:if test="${empty entity.otherClassHour}">${entity.otherClassHour }</c:if>" data-rule="digits" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_education_partTimeJob" class="control-label x130"><span class="red">*</span>兼任工作：</label>
                        <select id="f_teacher_education_partTimeJob" name="partTimeJob" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<c:forEach items="${partTimeJobs }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.partTimeJob}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_education_partTimeName" class="control-label x130">兼任其他工作名称：</label>
                        <input type="text" name="partTimeName" id="f_teacher_education_partTimeName" value="${entity.partTimeName }" maxlength="20" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherEducation:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>