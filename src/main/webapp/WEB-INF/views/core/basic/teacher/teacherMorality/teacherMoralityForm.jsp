<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherMoralityTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_morality_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherMorality/teacherMoralityList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}

function getSchoolType(){
	return ${schoolType};
}

$("#f_teacher_morality_honorLevel").change(function(){
	var honorLevelName = $("#f_teacher_morality_honorLevel option:selected").text();
	if(honorLevelName == "0-无"){
		$("#f_teacher_morality_honorName option").removeAttr("selected");
		$("#f_teacher_morality_honorName").selectpicker('refresh');
		$("#f_teacher_morality_honorStartDate").val("");
		$("#f_teacher_morality_honorDesc").val("");
		$("#f_teacher_morality_honorAwardUnit").val("");
		
		if(getSchoolType() == 1){
		$("#f_teacher_morality_honorName_span").hide();
		$("#f_teacher_morality_honorStartDate_span").hide();
		$("#f_teacher_morality_honorDesc_span").hide();
		$("#f_teacher_morality_honorAwardUnit_span").hide();
		
		$('#f_teacher_morality_form').validator("setField", "honorName", "");
		$('#f_teacher_morality_form').validator("setField", "honorStartDate", "");
		$('#f_teacher_morality_form').validator("setField", "honorDesc", "");
		$('#f_teacher_morality_form').validator("setField", "honorAwardUnit", "");
		
		$("#f_teacher_morality_honorName").trigger('validate');
		$("#f_teacher_morality_honorStartDate").trigger('validate');
		$("#f_teacher_morality_honorDesc").trigger('validate');
		$("#f_teacher_morality_honorAwardUnit").trigger('validate');
		}
		
		$("#f_teacher_morality_honorName").attr("disabled",true);
		$("#f_teacher_morality_honorStartDate").attr("disabled",true);
		$("#f_teacher_morality_honorDesc").attr("disabled",true);
		$("#f_teacher_morality_honorAwardUnit").attr("disabled",true);
	}else{
		$("#f_teacher_morality_honorName").attr("disabled",false);
		$("#f_teacher_morality_honorStartDate").attr("disabled",false);
		$("#f_teacher_morality_honorDesc").attr("disabled",false);
		$("#f_teacher_morality_honorAwardUnit").attr("disabled",false);
		
		$("#f_teacher_morality_honorName option").removeAttr("disabled");
		$("#f_teacher_morality_honorName").selectpicker('refresh');
		
		if(getSchoolType() == 1){
		$("#f_teacher_morality_honorName_span").show();
		$("#f_teacher_morality_honorStartDate_span").show();
		$("#f_teacher_morality_honorDesc_span").show();
		$("#f_teacher_morality_honorAwardUnit_span").show();
		
		$('#f_teacher_morality_honorName').attr("data-rule", "required");
		$('#f_teacher_morality_honorStartDate').attr("data-rule", "required;date");
		$('#f_teacher_morality_honorDesc').attr("data-rule", "required");
		$('#f_teacher_morality_honorAwardUnit').attr("data-rule", "required");
		}
	}
});

$("#f_teacher_morality_punishType").change(function(){
	var punishTypeName = $("#f_teacher_morality_punishType option:selected").text();
	if(punishTypeName == "0-无"){
		$("#f_teacher_morality_punishReason option").removeAttr("selected");
		$("#f_teacher_morality_punishReason").selectpicker('refresh');
		$("#f_teacher_morality_punishStartDate").val("");
		$("#f_teacher_morality_punishDec").val("");
		$("#f_teacher_morality_punishUnit").val("");
		$("#f_teacher_morality_punishDate").val("");
		$("#f_teacher_morality_punishCancelDate").val("");
		$("#f_teacher_morality_punishCancelReason").val("");
		
		$("#f_teacher_morality_punishReason_span").hide();
		$("#f_teacher_morality_punishStartDate_span").hide();
		$('#f_teacher_morality_form').validator("setField", "punishReason", "");
		$('#f_teacher_morality_form').validator("setField", "punishStartDate", "");
		$("#f_teacher_morality_punishReason").trigger('validate');
		$("#f_teacher_morality_punishStartDate").trigger('validate');
		
		if(getSchoolType() == 1){
			$("#f_teacher_morality_punishDec_span").hide();
			$("#f_teacher_morality_punishUnit_span").hide();
			$("#f_teacher_morality_punishDate_span").hide();
			$("#f_teacher_morality_punishCancelDate_span").hide();
			$("#f_teacher_morality_punishCancelReason_span").hide();
			
			$('#f_teacher_morality_form').validator("setField", "punishDec", "");
			$('#f_teacher_morality_form').validator("setField", "punishUnit", "");
			$('#f_teacher_morality_form').validator("setField", "punishDate", "");
			$('#f_teacher_morality_form').validator("setField", "punishCancelDate", "");
			$('#f_teacher_morality_form').validator("setField", "punishCancelReason", "");
			
			$("#f_teacher_morality_punishDec").trigger('validate');
			$("#f_teacher_morality_punishUnit").trigger('validate');
			$("#f_teacher_morality_punishDate").trigger('validate');
			$("#f_teacher_morality_punishCancelDate").trigger('validate');
			$("#f_teacher_morality_punishCancelReason").trigger('validate');
		}
		
		$("#f_teacher_morality_punishReason").attr("disabled", true);
		$("#f_teacher_morality_punishStartDate").attr("disabled", true);
		$("#f_teacher_morality_punishDec").attr("disabled", true);
		$("#f_teacher_morality_punishUnit").attr("disabled", true);
		$("#f_teacher_morality_punishDate").attr("disabled", true);
		$("#f_teacher_morality_punishCancelDate").attr("disabled", true);
		$("#f_teacher_morality_punishCancelReason").attr("disabled", true);
		
	}else{
		$("#f_teacher_morality_punishReason").attr("disabled", false);
		$("#f_teacher_morality_punishReason option").removeAttr("disabled");
		$("#f_teacher_morality_punishReason").selectpicker('refresh');
		$("#f_teacher_morality_punishStartDate").attr("disabled", false);
		$("#f_teacher_morality_punishStartDate").val(getNowYYYYMMDD());
		$("#f_teacher_morality_punishDec").attr("disabled", false);
		$("#f_teacher_morality_punishUnit").attr("disabled", false);
		$("#f_teacher_morality_punishDate").attr("disabled", false);
		$("#f_teacher_morality_punishCancelDate").attr("disabled", false);
		$("#f_teacher_morality_punishCancelReason").attr("disabled", false);
		
		$("#f_teacher_morality_punishReason_span").show();
		$("#f_teacher_morality_punishStartDate_span").show();
		$('#f_teacher_morality_punishReason').attr("data-rule", "required");
		$('#f_teacher_morality_punishStartDate').attr("data-rule", "required;date");
		
		if(getSchoolType() == 1){
			$("#f_teacher_morality_punishDec_span").show();
			$("#f_teacher_morality_punishUnit_span").show();
			$("#f_teacher_morality_punishDate_span").show();
			$("#f_teacher_morality_punishCancelDate_span").show();
			$("#f_teacher_morality_punishCancelReason_span").show();
			
			$("#f_teacher_morality_punishDec").attr("data-rule", "required");
			$("#f_teacher_morality_punishUnit").attr("data-rule", "required");
			$("#f_teacher_morality_punishDate").attr("data-rule", "required;date");
			$("#f_teacher_morality_punishCancelDate").attr("data-rule", "required;date");
			$("#f_teacher_morality_punishCancelReason").attr("data-rule", "required");
		}
	}
})
if($("#f_teacher_morality_assessDate").val() == ""){
	$("#f_teacher_morality_assessDate").val(getNowYYYYMMDD());
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/teacherMorality/saveTeacherMorality" method="post" id="f_teacher_morality_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherMoralityTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_seq" class="control-label x130"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_morality_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_assessDate" class="control-label x130"><span class="red">*</span>师德考核时间：</label>
                        <input type="text" name="assessDate" id="f_teacher_morality_assessDate" value='<fmt:formatDate value="${entity.assessDate }" pattern="yyyy-MM-dd"/>' data-rule="date;required" data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_assessResult" class="control-label x130"><span class="red">*</span>师德考核结论：</label>
                        <select id="f_teacher_morality_assessResult" name="assessResult" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<c:forEach items="${assessResults }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.assessResult}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_unitName" class="control-label x130"><span class="red" <c:if test="${schoolType != 1}">style="display: none;"</c:if>>*</span>师德考核单位名称：</label>
                        <input type="text" name="unitName" id="f_teacher_morality_unitName" value="${entity.unitName }" <c:if test="${schoolType == 1}">data-rule="required"</c:if> maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_honorLevel" class="control-label x130"><span class="red" <c:if test="${schoolType != 1}">style="display: none;"</c:if>>*</span>荣誉级别：</label>
                        <select id="f_teacher_morality_honorLevel" name="honorLevel" data-toggle="selectpicker"  data-width="200px" <c:if test="${schoolType == 1}">data-rule="required"</c:if>>
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${honorLevels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.honorLevel}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_honorName" class="control-label x130"><span class="red" id="f_teacher_morality_honorName_span" <c:if test="${schoolType != 1 or entity.honorLevel == 81014}">style="display: none;"</c:if>>*</span>获得荣誉称号：</label>
                        <select id="f_teacher_morality_honorName" name="honorName" data-toggle="selectpicker"  data-width="200px" <c:if test="${entity.honorLevel == 81014}">disabled="true"</c:if> <c:if test="${schoolType == 1}">data-rule="required"</c:if>>
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${honorNames }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.honorName}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_honorStartDate" class="control-label x130"><span class="red" id="f_teacher_morality_honorStartDate_span" <c:if test="${schoolType != 1 or entity.honorLevel == 81014}">style="display: none;"</c:if>>*</span>荣誉发生日期：</label>
                        <input type="text" name="honorStartDate" id="f_teacher_morality_honorStartDate" value='<fmt:formatDate value="${entity.honorStartDate }" pattern="yyyy-MM-dd"/>' <c:if test="${entity.honorLevel == 81014}">disabled="true"</c:if> <c:if test="${schoolType == 1}">data-rule="required;date"</c:if> data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_honorDesc" class="control-label x130"><span class="red" id="f_teacher_morality_honorDesc_span" <c:if test="${schoolType != 1 or entity.honorLevel == 81014}">style="display: none;"</c:if>>*</span>荣誉记录描述：</label>
                        <input type="text" name="honorDesc" id="f_teacher_morality_honorDesc" value="${entity.honorDesc }" maxlength="20" size="20" <c:if test="${entity.honorLevel == 81014}">disabled="true"</c:if> <c:if test="${schoolType == 1}">data-rule="required"</c:if>>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_honorAwardUnit" class="control-label x130"><span class="red" id="f_teacher_morality_honorAwardUnit_span" <c:if test="${schoolType != 1 or entity.honorLevel == 81014}">style="display: none;"</c:if>>*</span>荣誉授予单位名称：</label>
                        <input type="text" name="honorAwardUnit" id="f_teacher_morality_honorAwardUnit" value="${entity.honorAwardUnit }" maxlength="20" size="20" <c:if test="${entity.honorLevel == 81014}">disabled="true"</c:if> <c:if test="${schoolType == 1}">data-rule="required"</c:if>>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_punishType" class="control-label x130"><span class="red">*</span>处分类别：</label>
                        <select id="f_teacher_morality_punishType" name="punishType" data-toggle="selectpicker" data-rule="required" data-width="200px">
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${punishTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${empty entity.punishType and item.name=='0-无'}">selected</c:if><c:if test="${item.id==entity.punishType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_punishReason" class="control-label x130"><span id="f_teacher_morality_punishReason_span" <c:if test="${empty entity.punishReason}">style="display: none;"</c:if> class="red">*</span>处分原因：</label>
                        <select id="f_teacher_morality_punishReason" name="punishReason" data-toggle="selectpicker" data-width="200px" <c:if test="${empty entity.punishReason}">disabled="true"</c:if> <c:if test="${not empty entity.punishReason}">data-rule="required"</c:if>>
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${punishReasons }" var="item">
			               			<option  value="${item.id }" <c:if test="${item.id==entity.punishReason}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_punishStartDate" class="control-label x130"><span id="f_teacher_morality_punishStartDate_span" <c:if test="${empty entity.punishStartDate}">style="display: none;"</c:if> class="red">*</span>处分发生日期：</label>
                        <input type="text" name="punishStartDate" id="f_teacher_morality_punishStartDate" <c:if test="${empty entity.punishStartDate}">disabled="true"</c:if> <c:if test="${not empty entity.punishStartDate}">data-rule="required"</c:if> value='<fmt:formatDate value="${entity.punishStartDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_punishDec" class="control-label x130"><span id="f_teacher_morality_punishDec_span" <c:if test="${schoolType != 1 or (empty entity.punishDec and schoolType == 1)}">style="display: none;"</c:if> class="red">*</span>处分记录描述：</label>
                        <input type="text" name="punishDec" id="f_teacher_morality_punishDec" <c:if test="${empty entity.punishStartDate}">disabled="true"</c:if> value="${entity.punishDec }" maxlength="20" size="20" <c:if test="${schoolType == 1 and not empty entity.punishDec}">data-rule="required"</c:if>>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_punishUnit" class="control-label x130"><span id="f_teacher_morality_punishUnit_span" <c:if test="${schoolType != 1 or (empty entity.punishUnit and schoolType == 1)}">style="display: none;"</c:if> class="red">*</span>处分单位名称：</label>
                        <input type="text" name="punishUnit" id="f_teacher_morality_punishUnit" <c:if test="${empty entity.punishStartDate}">disabled="true"</c:if> value="${entity.punishUnit }" maxlength="20" size="20" <c:if test="${schoolType == 1 and not empty entity.punishUnit}">data-rule="required"</c:if>>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_punishDate" class="control-label x130"><span id="f_teacher_morality_punishDate_span" <c:if test="${schoolType != 1 or (empty entity.punishDate and schoolType == 1)}">style="display: none;"</c:if> class="red">*</span>处分日期：</label>
                        <input type="text" name="punishDate" id="f_teacher_morality_punishDate" <c:if test="${empty entity.punishStartDate}">disabled="true"</c:if> value='<fmt:formatDate value="${entity.punishDate }" pattern="yyyy-MM-dd"/>' <c:if test="${schoolType == 1 and not empty entity.punishDate}">data-rule="required"</c:if> data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_punishCancelDate" class="control-label x130"><span id="f_teacher_morality_punishCancelDate_span" <c:if test="${schoolType != 1 or (empty entity.punishCancelDate and schoolType == 1)}">style="display: none;"</c:if> class="red">*</span>处分撤销日期：</label>
                        <input type="text" name="punishCancelDate" id="f_teacher_morality_punishCancelDate" <c:if test="${empty entity.punishStartDate}">disabled="true"</c:if> value='<fmt:formatDate value="${entity.punishCancelDate }" pattern="yyyy-MM-dd"/>' <c:if test="${schoolType == 1 and not empty entity.punishCancelDate}">data-rule="required"</c:if> data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_morality_punishCancelReason" class="control-label x130"><span id="f_teacher_morality_punishCancelReason_span" <c:if test="${schoolType != 1 or (empty entity.punishCancelReason and schoolType == 1)}">style="display: none;"</c:if> class="red">*</span>处分撤销原因：</label>
                        <input type="text" name="punishCancelReason" id="f_teacher_morality_punishCancelReason" <c:if test="${empty entity.punishStartDate}">disabled="true"</c:if> value="${entity.punishCancelReason }" maxlength="20" size="20" <c:if test="${schoolType == 1 and not empty entity.punishCancelReason}">data-rule="required"</c:if>>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherMorality:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>