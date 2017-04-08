<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/teacher.css">
<script type="text/javascript">
function refushTeacherOverseasStudyTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_overseas_study_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/overseasStudy/teacherOverseasStudyList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}

$("#f_teacher_overseas_study_isOverseasExp").change(function(){
	var overseasFlag = $("#f_teacher_overseas_study_isOverseasExp option:selected").text();	
	if(overseasFlag == "0-否"){
		$("#f_teacher_overseas_study_nation option").removeAttr("selected");
		$("#f_teacher_overseas_study_nation").selectpicker('refresh');
		$("#f_teacher_overseas_study_nation").attr("disabled", true);
		$("#f_teacher_overseas_study_startDate").val("");
		$("#f_teacher_overseas_study_startDate").attr("disabled", true);
		$("#f_teacher_overseas_study_endDate").val("");
		$("#f_teacher_overseas_study_endDate").attr("disabled", true);
		$("#f_teacher_overseas_study_orgName").val("");
		$("#f_teacher_overseas_study_orgName").attr("disabled", true);
		$("#f_teacher_overseas_study_projectName").val("");
		$("#f_teacher_overseas_study_projectName").attr("disabled", true);
		$("#f_teacher_overseas_study_unitName").val("");
		$("#f_teacher_overseas_study_unitName").attr("disabled", true);
		
		$("#f_teacher_overseas_study_nation_span").hide();
		$("#f_teacher_overseas_study_startDate_span").hide();
		$("#f_teacher_overseas_study_endDate_span").hide();
		$("#f_teacher_overseas_study_orgName_span").hide();
		$("#f_teacher_overseas_study_projectName_span").hide();
		$("#f_teacher_overseas_study_unitName_span").hide();
		
		$('#f_teacher_overseas_study_form').validator("setField", "nation", "");
		$('#f_teacher_overseas_study_form').validator("setField", "startDate", "");
		$('#f_teacher_overseas_study_form').validator("setField", "endDate", "");
		$('#f_teacher_overseas_study_form').validator("setField", "orgName", "");
		$('#f_teacher_overseas_study_form').validator("setField", "projectName", "");
		$('#f_teacher_overseas_study_form').validator("setField", "unitName", "");
	}else{
		$("#f_teacher_overseas_study_nation").attr("disabled", false);
		$("#f_teacher_overseas_study_nation option").removeAttr("disabled");
		$("#f_teacher_overseas_study_nation").selectpicker('refresh');
		$("#f_teacher_overseas_study_startDate").attr("disabled", false);
		$("#f_teacher_overseas_study_endDate").attr("disabled", false);
		$("#f_teacher_overseas_study_orgName").attr("disabled", false);
		$("#f_teacher_overseas_study_projectName").attr("disabled", false);
		$("#f_teacher_overseas_study_unitName").attr("disabled", false);
		
		$("#f_teacher_overseas_study_nation_span").show();
		$("#f_teacher_overseas_study_startDate_span").show();
		$("#f_teacher_overseas_study_endDate_span").show();
		$("#f_teacher_overseas_study_orgName_span").show();
		$("#f_teacher_overseas_study_projectName_span").show();
		$("#f_teacher_overseas_study_unitName_span").show();
		
		$("#f_teacher_overseas_study_nation").attr("data-rule", "required");
		$("#f_teacher_overseas_study_startDate").attr("data-rule", "开始日期: required;date");
		$("#f_teacher_overseas_study_endDate").attr("data-rule", "结束日期: required;date;match[gt, startDate, date]");
		$("#f_teacher_overseas_study_orgName").attr("data-rule", "required");
		$("#f_teacher_overseas_study_projectName").attr("data-rule", "required");
		$("#f_teacher_overseas_study_unitName").attr("data-rule", "required");
	}
});

$("#f_teacher_overseas_study_nation").change(function(){
	var nationName = $("#f_teacher_overseas_study_nation option:selected").text();	
	if(nationName == "156-中国"){
		$(this).alertmsg("warn","不能选择【156-中国】");
		$("#f_teacher_overseas_study_nation option").removeAttr("selected");
		$("#f_teacher_overseas_study_nation").selectpicker('refresh');
	}
});

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/overseasStudy/saveTeacherOverseasStudy" method="post" id="f_teacher_overseas_study_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherOverseasStudyTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <div>
        	<span class="teacher_top_title_my"><span class="red">*</span>海外学历不在此填写，时长三个月以上的</span> 	
	    </div>
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_overseas_study_seq" class="control-label x190"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_overseas_study_seq" value="${entity.seq }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_overseas_study_isOverseasExp" class="control-label x190"><span class="red">*</span>是否有海外研修（访学）经历：</label>
                    	<select id="f_teacher_overseas_study_isOverseasExp" name="isOverseasExp" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<c:forEach items="${overseasStudys }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.isOverseasExp}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_overseas_study_startDate" class="control-label x190"><span id="f_teacher_overseas_study_startDate_span" <c:if test="${not empty entity.isOverseasExp and empty entity.startDate}">style="display: none;"</c:if>  class="red">*</span>开始日期：</label>
                        <input type="text" name="startDate" id="f_teacher_overseas_study_startDate" value="<fmt:formatDate value="${entity.startDate }" pattern="yyy-MM-dd"/>" data-rule="开始日期: required;date" <c:if test="${not empty entity.isOverseasExp and empty entity.startDate}">disabled="true"</c:if> data-toggle="datepicker" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_overseas_study_endDate" class="control-label x190"><span id="f_teacher_overseas_study_endDate_span" <c:if test="${not empty entity.isOverseasExp and empty entity.endDate}">style="display: none;"</c:if> class="red">*</span>结束日期：</label>
                        <input type="text" name="endDate" id="f_teacher_overseas_study_endDate" value="<fmt:formatDate value="${entity.endDate }" pattern="yyy-MM-dd"/>" data-rule="结束日期: required;date;match[gt, startDate, date]" <c:if test="${not empty entity.isOverseasExp and empty entity.endDate}">disabled="true"</c:if> data-toggle="datepicker" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_overseas_study_nation" class="control-label x190"><span id="f_teacher_overseas_study_nation_span" <c:if test="${not empty entity.isOverseasExp and empty entity.nation}">style="display: none;"</c:if> class="red">*</span>国家(地区)：</label>
                        <select id="f_teacher_overseas_study_nation" name="nation" data-toggle="selectpicker" data-width="200px" data-rule="required" <c:if test="${not empty entity.isOverseasExp and empty entity.nation}">disabled="true"</c:if> >
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${nations }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.nation}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_overseas_study_orgName" class="control-label x190"><span id="f_teacher_overseas_study_orgName_span" <c:if test="${not empty entity.isOverseasExp and empty entity.orgName}">style="display: none;"</c:if> class="red">*</span>研修（访学）机构名称：</label>
                        <input type="text" name="orgName" id="f_teacher_overseas_study_orgName" value="${entity.orgName }" maxlength="20" size="20" data-rule="required" <c:if test="${not empty entity.isOverseasExp and empty entity.orgName}">disabled="true"</c:if> >
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_overseas_study_projectName" class="control-label x190"><span id="f_teacher_overseas_study_projectName_span" <c:if test="${not empty entity.isOverseasExp and empty entity.projectName}">style="display: none;"</c:if> class="red">*</span>项目名称：</label>
                        <input type="text" name="projectName" id="f_teacher_overseas_study_projectName" value="${entity.projectName }" maxlength="20" size="20" data-rule="required" <c:if test="${not empty entity.isOverseasExp and empty entity.projectName}">disabled="true"</c:if> >
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_overseas_study_unitName" class="control-label x190"><span id="f_teacher_overseas_study_unitName_span" <c:if test="${not empty entity.isOverseasExp and empty entity.unitName}">style="display: none;"</c:if> class="red">*</span>项目组织单位名称：</label>
                        <input type="text" name="unitName" id="f_teacher_overseas_study_unitName" value="${entity.unitName }" maxlength="20" size="20" <c:if test="${not empty entity.isOverseasExp and empty entity.unitName}">disabled="true"</c:if> data-rule="required" >
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherOverseasStudy:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>