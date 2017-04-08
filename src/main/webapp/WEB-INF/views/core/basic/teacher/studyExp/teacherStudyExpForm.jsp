<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/teacher.css">
<script type="text/javascript">

//保存回调
function saveStudyExpCb(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_study_exp_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/studyExp/teacherStudyExpList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}

function  f_teacher_studyexp_unittype(){
	return ${studyUnitTypes};
}

function f_teacher_studyexp_academicdegree(){
	return ${academicDegreeNames};
}

$("#f_teacher_study_exp_degree").change(function(){
	var degreeName = $("#f_teacher_study_exp_degree option:selected").text();	
	if(degreeName == "0-无"){
		$("#f_teacher_study_exp_degreeCountryType option").removeAttr("selected");
		$("#f_teacher_study_exp_degreeCountryType").selectpicker('refresh');
		$("#f_teacher_study_exp_degreeCountryType").attr("disabled", true);
		$("#f_teacher_study_exp_degreeCountryType_span").hide();
		$('#study_exp_form').validator("setField", "degreeCountryType", "");
		
		$("#f_teacher_study_exp_univ").val("");
		$("#f_teacher_study_exp_univ").attr("disabled", true);
		$("#f_teacher_study_exp_univ_span").hide();
		$('#study_exp_form').validator("setField", "univ", "");
		
		$("#f_teacher_study_exp_major").val("");
		$("#f_teacher_study_exp_major").attr("disabled", true);
		$("#f_teacher_study_exp_major_span").hide();
		$('#study_exp_form').validator("setField", "major", "");
		
		$("#f_teacher_study_exp_isNormalCollege option").removeAttr("selected");
		$("#f_teacher_study_exp_isNormalCollege").selectpicker('refresh');
		$("#f_teacher_study_exp_isNormalCollege").attr("disabled", true);
		$("#f_teacher_study_exp_isNormalCollege_span").hide();
		$('#study_exp_form').validator("setField", "isNormalCollege", "");
		
		$("#f_teacher_study_exp_startDate").val("");
		$("#f_teacher_study_exp_startDate").attr("disabled", true);
		$("#f_teacher_study_exp_startDate_span").hide();
		$('#study_exp_form').validator("setField", "startDate", "");
		
		$("#f_teacher_study_exp_endDate").val("");
		$("#f_teacher_study_exp_endDate").attr("disabled", true);
	}else{
		$("#f_teacher_study_exp_degreeCountryType").attr("disabled", false);
		$("#f_teacher_study_exp_degreeCountryType_span").show();
		$('#f_teacher_study_exp_degreeCountryType').attr("data-rule", "required");
		
		$("#f_teacher_study_exp_univ").attr("disabled", false);
		$("#f_teacher_study_exp_univ_span").show();
		$('#f_teacher_study_exp_univ').attr("data-rule", "required");
		
		$("#f_teacher_study_exp_major").attr("disabled", false);
		$("#f_teacher_study_exp_major_span").show();
		$('#f_teacher_study_exp_major').attr("data-rule", "required");
		
		$("#f_teacher_study_exp_isNormalCollege").attr("disabled", false);
		$("#f_teacher_study_exp_isNormalCollege_span").show();
		$('#f_teacher_study_exp_isNormalCollege').attr("data-rule", "required");
		
		$("#f_teacher_study_exp_startDate").attr("disabled", false);
		$("#f_teacher_study_exp_startDate_span").show();
		$('#f_teacher_study_exp_startDate').attr("data-rule", "required;date");
		
		$("#f_teacher_study_exp_endDate").attr("disabled", false);
	}
	
});


$("#f_teacher_study_exp_academicDegree").change(function(){
	var academicDegreeName = $("#f_teacher_study_exp_academicDegree option:selected").text();	
	if(academicDegreeName == "0-无"){
		$("#f_study_exp_academicDegreeName_ztree").attr("disabled", true);
		$("#f_study_exp_academicDegreeName_select_tree_id").val("");
		$("#f_teacher_study_exp_academicDegreeName_span").hide();
		$('#study_exp_form').validator("setField", "f_teacher_study_exp_academicDegree_name", "");

		$("#f_teacher_study_exp_academicCountryType option").removeAttr("selected");
		$("#f_teacher_study_exp_academicCountryType").selectpicker('refresh');
		$("#f_teacher_study_exp_academicCountryType").attr("disabled", true);
		$("#f_teacher_study_exp_academicCountryType_span").hide();
		$('#study_exp_form').validator("setField", "academicCountryType", "");
		
		$("#f_teacher_study_exp_academicUnit").val("");
		$("#f_teacher_study_exp_academicUnit").attr("disabled", true);
		$("#f_teacher_study_exp_academicUnit_span").hide();
		$('#study_exp_form').validator("setField", "academicUnit", "");
		
		$("#f_teacher_study_exp_grantDate").val("");
		$("#f_teacher_study_exp_grantDate").attr("disabled", true);
		$("#f_teacher_study_exp_grantDate_span").hide();
		$('#study_exp_form').validator("setField", "grantDate", "");
	}else{
		$("#f_study_exp_academicDegreeName_ztree").attr("disabled", false);
		$("#f_teacher_study_exp_academicDegreeName_span").show();
		$('#f_study_exp_academicDegreeName_ztree').attr("data-rule", "required");
		
		$("#f_teacher_study_exp_academicCountryType").attr("disabled", false);
		$("#f_teacher_study_exp_academicCountryType_span").show();
		$('#f_teacher_study_exp_academicCountryType').attr("data-rule", "required");
		
		$("#f_teacher_study_exp_academicUnit").attr("disabled", false);
		$("#f_teacher_study_exp_academicUnit_span").show();
		$('#f_teacher_study_exp_academicUnit').attr("data-rule", "required");
		
		$("#f_teacher_study_exp_grantDate").attr("disabled", false);
		$("#f_teacher_study_exp_grantDate_span").show();
		$('#f_teacher_study_exp_grantDate').attr("data-rule", "required;date");
	}
	
});

</script>

<div class="bjui-pageContent">
    <form action="${ctx }/core/basic/teacher/studyExp/saveTeacherStudyExp" id="study_exp_form" class="pageForm" data-toggle="validate" method="post" data-callback="saveStudyExpCb">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <div>
        	<span class="teacher_top_title_my"><span class="red">*</span>从第一学历(高中或中专)填写至当前学历，学历低于高中或中专学历的，只填写最高学历</span> 	
	    </div>
        <table id="study_exp_edit"  class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_study_exp_seq" class="control-label x160"><span class="red">*</span>序号：</label>
                        <input type="hidden" name="tid" id="f_teacher_study_exp_tid" value="${entity.tid }">
                        <input type="text" name="seq" id="f_teacher_study_exp_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
                <tr>
                	<td>
                		<label for="f_teacher_study_exp_degree" class="control-label x160"><span class="red">*</span>获得学历：</label>
                        <select id="f_teacher_study_exp_degree" name="degree" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${degrees }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.degree}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                	</td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_study_exp_degreeCountryType" class="control-label x160"><span id="f_teacher_study_exp_degreeCountryType_span" class="red">*</span>获得学历的国家(地区)：</label>
                        <select id="f_teacher_study_exp_degreeCountryType" name="degreeCountryType" data-toggle="selectpicker" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${degreeCountryTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${empty entity.degreeCountryType and item.id==81297}">selected</c:if><c:if test="${item.id==entity.degreeCountryType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_study_exp_univ" class="control-label x160"><span id="f_teacher_study_exp_univ_span"  class="red">*</span>获得学历的院校或机构：</label>
                        <input type="text" name="univ" id="f_teacher_study_exp_univ" value="${entity.univ }"  size="20">
                    </td>
                 </tr>
                 <tr>
                    <td>
                        <label for="f_teacher_study_exp_major" class="control-label x160"><span id="f_teacher_study_exp_major_span" class="red">*</span>所学专业：</label>
                        <input type="text" name="major" id="f_teacher_study_exp_major" value="${entity.major }" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_study_exp_isNormalCollege" class="control-label x160"><span id="f_teacher_study_exp_isNormalCollege_span" class="red">*</span>是否师范类专业：</label>
                        <select id="f_teacher_study_exp_isNormalCollege" name="isNormalCollege" data-toggle="selectpicker" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${isNormalColleges }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.isNormalCollege}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_study_exp_startDate" class="control-label x160"><span id="f_teacher_study_exp_startDate_span" class="red">*</span>入学年月：</label>
                        <input type="text" name="startDate" id="f_teacher_study_exp_startDate" value="<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>" data-toggle="datepicker" size="20">
                    </td>
                </tr>
                <tr>
                	<td>
                        <label for="f_teacher_study_exp_endDate" class="control-label x160">毕业年月：</label>
                        <input type="text" name="endDate" id="f_teacher_study_exp_endDate" value="<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>" data-rule="date" data-toggle="datepicker" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_study_exp_academicDegree" class="control-label x160"><span class="red">*</span>学位层次：</label>
                        <select id="f_teacher_study_exp_academicDegree" name="academicDegree" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${academicDegrees }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.academicDegree}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_study_exp_academicDegreeName_ztree" class="control-label x160"><span id="f_teacher_study_exp_academicDegreeName_span" class="red">*</span>学位名称：</label>
                        <input type="hidden" name="academicDegreeName" value="${entity.academicDegreeName}" id="f_study_exp_academicDegreeName_select_tree_id" >
                        <input type="text" id="f_study_exp_academicDegreeName_ztree" name="f_teacher_study_exp_academicDegree_name" value="${academicDegreeName}" data-toggle="selectztree" size="20" data-tree="#f_study_exp_academicDegreeName_select_tree" readonly>
                        <ul style="height: 160px" id="f_study_exp_academicDegreeName_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_studyexp_academicdegree'}"
                            >
                        </ul>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_study_exp_academicCountryType" class="control-label x160"><span id="f_teacher_study_exp_academicCountryType_span" class="red">*</span>获得学位的国家(地区)：</label>
                        <select id="f_teacher_study_exp_academicCountryType" name="academicCountryType" data-toggle="selectpicker" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${academicCountryTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${empty entity.academicCountryType and item.name=='156-中国'}">selected</c:if><c:if test="${item.id==entity.academicCountryType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_study_exp_academicUnit" class="control-label x160"><span id="f_teacher_study_exp_academicUnit_span" class="red">*</span>获得学位的院校或机构：</label>
                        <input type="text" name="academicUnit" id="f_teacher_study_exp_academicUnit" value="${entity.academicUnit }" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_study_exp_grantDate" class="control-label x160"><span id="f_teacher_study_exp_grantDate_span" class="red">*</span>学位授予年月：</label>
                        <input type="text" name="grantDate" id="f_teacher_study_exp_grantDate" value="<fmt:formatDate value="${entity.grantDate }" pattern="yyyy-MM-dd"/>" data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_study_exp_studyMode" class="control-label x160"><span class="red">*</span>学习方式：</label>
                        <select id="f_teacher_study_exp_studyMode" name="studyMode" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${studyModes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.studyMode}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_study_exp_studyUnitType" class="control-label x160">在学单位类别：</label>
                        <input type="hidden" name="studyUnitType" value="${entity.studyUnitType}" id="f_teacher_study_exp_studyUnitType_id" >
                        <input type="text" id="f_teacher_study_exp_type_ztree" value="${studyUnitTypeName}" data-toggle="selectztree" size="20" data-tree="#f_teacher_study_exp_studyUnitType" readonly>
                        <ul style="height: 160px" id="f_teacher_study_exp_studyUnitType" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_studyexp_unittype'}"
                            >
                        </ul>
                    </td>
                </tr>
                 
                <!-- 
                <tr>
                   <td>
                	<label for="f_teacher_study_exp_degreeType" class="control-label x100"><span class="red">*</span>学历类型：</label>
                        <select id="f_teacher_study_exp_degreeType" name="degreeType" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${degreeTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.degreeType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                	</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label for="f_teacher_study_exp_memo" class="control-label x100">备注：</label>
                        <textarea  name="memo" id="f_teacher_study_exp_memo" data-toggle="autoheight" cols="20" rows="2"  maxlength="100">${entity.memo }</textarea>
                    </td>
                </tr>
                -->
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <!-- 
        <li><button id="cancel_study_exp" type="button" class="btn-red" data-icon="refresh">取消</button></li>
         -->
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
         <shiro:hasPermission name="TeacherStudyExp:create">
        <li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>
