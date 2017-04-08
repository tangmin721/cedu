<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherQualificationTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_qualification_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherQualification/teacherQualificationList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}

$("#f_teacher_qualification_qualifyType").change(function(){
	var qualifyName = $("#f_teacher_qualification_qualifyType option:selected").text();
	if(qualifyName == "0-无"){
		$("#f_teacher_qualification_qualifyNo").val("");
		$("#f_teacher_qualification_sbuject").val("");
		$("#f_teacher_qualification_certificateDate").val("");
		$("#f_teacher_qualification_issueAgency").val("");
		$("#f_teacher_qualification_qualifyNo").attr("disabled",true);
		$("#f_teacher_qualification_sbuject").attr("disabled",true);
		$("#f_teacher_qualification_certificateDate").attr("disabled",true);
		$("#f_teacher_qualification_issueAgency").attr("disabled",true);
		
		$("#f_teacher_qualification_qualifyNo_span").hide();
		$("#f_teacher_qualification_sbuject_span").hide();
		$("#f_teacher_qualification_certificateDate_span").hide();
		
		$('#f_teacher_qualification_form').validator("setField", "qualifyNo", "");
		$('#f_teacher_qualification_form').validator("setField", "sbuject", "");
		$('#f_teacher_qualification_form').validator("setField", "certificateDate", "");
	}else{
		$("#f_teacher_qualification_qualifyNo").attr("disabled",false);
		$("#f_teacher_qualification_sbuject").attr("disabled",false);
		$("#f_teacher_qualification_certificateDate").attr("disabled",false);
		$("#f_teacher_qualification_issueAgency").attr("disabled",false);
		
		$("#f_teacher_qualification_qualifyNo_span").show();
		$("#f_teacher_qualification_sbuject_span").show();
		$("#f_teacher_qualification_certificateDate_span").show();
		
		$('#f_teacher_qualification_qualifyNo').attr("data-rule", "required");
		$('#f_teacher_qualification_sbuject').attr("data-rule", "required");
		$('#f_teacher_qualification_certificateDate').attr("data-rule", "required;date");
	}
});

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/teacherQualification/saveTeacherQualification" method="post" id="f_teacher_qualification_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherQualificationTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_seq" class="control-label x130"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_qualification_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_qualifyType" class="control-label x130"><span class="red">*</span>教师资格证种类：</label>
                    	<select id="f_teacher_qualification_qualifyType" name="qualifyType" data-toggle="selectpicker" data-rule="required" data-width="200px">
                    		<option value="">--请选择--</option>
			               	<c:forEach items="${qualifys }" var="item">
			               		<option value="${item.id}" <c:if test="${empty entity.qualifyType and item.name=='0-无'}">selected</c:if><c:if test="${item.id == entity.qualifyType}">selected</c:if>>${item.name}</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_qualifyNo" class="control-label x130"><span id="f_teacher_qualification_qualifyNo_span" <c:if test="${empty entity.qualifyNo}">style="display: none;"</c:if> class="red">*</span>教师资格证号码：</label>
                        <input type="text" name="qualifyNo" id="f_teacher_qualification_qualifyNo" <c:if test="${empty entity.qualifyNo}">disabled="true"</c:if> value="${entity.qualifyNo }"  maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_sbuject" class="control-label x130"><span id="f_teacher_qualification_sbuject_span" <c:if test="${empty entity.sbuject}">style="display: none;"</c:if> class="red">*</span>任教学科：</label>
                        <input type="text" name="sbuject" id="f_teacher_qualification_sbuject" <c:if test="${empty entity.sbuject}">disabled="true"</c:if> value="${entity.sbuject }"  maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_certificateDate" class="control-label x130"><span id="f_teacher_qualification_certificateDate_span" <c:if test="${empty entity.certificateDate}">style="display: none;"</c:if> class="red">*</span>证书颁发日期：</label>
                        <input type="text" name="certificateDate" id="f_teacher_qualification_certificateDate" <c:if test="${empty entity.certificateDate}">disabled="true"</c:if> value="<fmt:formatDate value="${entity.certificateDate }" pattern="yyyy-MM-dd"/>" data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_issueAgency" class="control-label x130">颁发机构：</label>
                        <input type="text" name="issueAgency" id="f_teacher_qualification_issueAgency" <c:if test="${empty entity.certificateDate}">disabled="true"</c:if> value="${entity.issueAgency }" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_firstRegistDate" class="control-label x130">首次注册日期：</label>
                        <input type="text" name="firstRegistDate" id="f_teacher_qualification_firstRegistDate" value="${entity.firstRegistDate }" disabled="disabled" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_firstRegistResult" class="control-label x130">首次注册结论：</label>
                        <input type="text" name="firstRegistResult" id="f_teacher_qualification_firstRegistResult" value="${entity.firstRegistResult }" disabled="disabled" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_fixRegistDate" class="control-label x130">定期注册日期：</label>
                        <input type="text" name="fixRegistDate" id="f_teacher_qualification_fixRegistDate" value="${entity.fixRegistDate }" disabled="disabled" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_qualification_fixRegistResult" class="control-label x130">定期注册结论：</label>
                        <input type="text" name="fixRegistResult" id="f_teacher_qualification_fixRegistResult" value="${entity.fixRegistResult }" disabled="disabled" maxlength="20" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherQualification:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>