<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProjectTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$(this).navtab({id:'ntp-my-create', url:'${ctx}/core/train/project/base/projectList/0', title:'管理我创建的项目',fresh:true});
		if('${formtype}'=='dialog'){//dialog
			$(this).dialog('closeCurrent');
		}else{// navtab
			$(this).alertmsg('ok', json.message);
			$(this).navtab('closeTab','create-project-form');
		}
		
	}
}

function ratio(){
	var ratioScore = $("#f_project_ratioScore").val();
	var ratioHour = $("#f_project_ratioHour").val();
	var score = $("#f_project_score").val();
	var hour = $("#f_project_hour").val();
	//alert("ratioScore="+ratioScore+",ratioHour="+ratioHour+",score="+score+",hour="+hour);
	if(ratioScore && ratioHour){
		if(score){
			$("#f_project_hour").val(score*ratioHour/ratioScore);
		}else{
			if(hour){
				$("#f_project_score").val(hour*ratioScore/ratioScore);
			}
		}
	}
}

$("#f_project_ratioScore").change(function(){
	ratio();
});

$("#f_project_ratioHour").change(function(){
	ratio();
});

$("#f_project_score").change(function(){
	ratio();
});

$("#f_project_hour").change(function(){
	ratio();
});


$("#f_project_stage").change(function(){
    var stage = $("#f_project_stage option:selected").val();
    if(stage){
   	 //联动学科下拉框
   	 $.ajax({ 
            type: "POST", 
            url: "${ctx}/core/basic/conf/course/courses/"+stage,
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_project_course").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_project_course").append(str);  
                }
                $('#f_project_course').selectpicker('refresh');
            },
            error :  function(){
            	  $(this).alertmsg('error', '系统错误！');
            }
        }); 
    }else{
   	 $("#f_project_course").html("<option value=''>--请选择--</option>");
   	 $('#f_project_course').selectpicker('refresh');
   	 
   	 $("#f_project_grade").html("<option value=''>--请选择--</option>");
   	 $('#f_project_grade').selectpicker('refresh');
    }
   	 
});

$('#f_project_confirmFlag').bootstrapSwitch();
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/base/saveProject" id="f_project_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushProjectTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="optType" value="${optType}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                     <td colspan="2">
                        <label for="f_project_name" class="control-label x100"><span class="red">*</span>项目名称：</label>
                        <input type="text" name="name" id="f_project_name" value="${entity.name }" data-rule="required" maxlength="62" size="62">
                    </td>
                </tr>
                <tr>
                     <td colspan="2">
                        <label for="f_project_pno" class="control-label x100">项目编号：</label>
                        <input type="text" name="pno" id="f_project_pno" value="${entity.pno }" disabled maxlength="62" size="62">
                    </td>
                </tr>
                <tr>
                	<shiro:hasAnyRoles name="PROVINCE_ADMIN">
                	 <td>
                        <label for="f_project_trainLevel" class="control-label x100"><span class="red">*</span>项目类别：</label>
                        <select id="f_project_trainLevel" name="trainLevel"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${trainLevels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.trainLevel}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                    </shiro:hasAnyRoles>
                    <shiro:hasAnyRoles name="CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
                	 <td>
                        <label for="f_project_trainLevel" class="control-label x100"><span class="red">*</span>项目类别：</label>
                        <select id="f_project_trainLevel" name="trainLevel"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" disabled>
			               	<c:forEach items="${trainLevels }" var="item">
			               		<option  value="${item.id }" selected>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                    </shiro:hasAnyRoles>
                    <td>
                        <label for="f_project_trainType" class="control-label x100"><span class="red">*</span>项目类型：</label>
                        <select id="f_project_trainType" name="trainType"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${trainTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.trainType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_stage" class="control-label x100"><span class="red">*</span>学段：</label>
                        <select id="f_project_stage" name="stage"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${stages }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.stage}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                     <td>
                        <label for="f_project_course" class="control-label x100"><span class="red">*</span>学科：</label>
                        <select id="f_project_course" name="course"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${courses }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.course}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_schoolYear" class="control-label x100"><span class="red">*</span>年度：</label>
                        <select id="f_project_schoolYear" name="schoolYear"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${schoolYears }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.schoolYear}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                     <td>
                        <label for="f_project_hour" class="control-label x100"><span class="red">*</span>学时：</label>
                        <input type="text" name="hour" id="f_project_hour" value='<fmt:formatNumber value="${entity.hour }" pattern="#" type="number"/>' data-rule="required;digits" maxlength="6" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                    	<label for="f_project_goodScore" class="control-label x100"><span class="red">*</span>优秀所获学时：</label>
                        <input type="text" name="goodScore" id="f_project_goodScore" value='<fmt:formatNumber value="${entity.goodScore }" pattern="#" type="number"/>' data-rule="required;digits" maxlength="6" size="20">
                      </td>
                     <td>
                        <label for="f_project_passScore" class="control-label x100"><span class="red">*</span>合格所获学时：</label>
                        <input type="text" name="passScore" id="f_project_passScore" value='<fmt:formatNumber value="${entity.passScore }" pattern="#" type="number"/>' data-rule="required;digits" maxlength="6" size="20">
                    
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_startDate" class="control-label x100"><span class="red">*</span>开始时间：</label>
                        <input type="text" name="startDate" id="f_project_startDate" value='<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20">
                    </td>
                    <td>
                        <label for="f_project_endDate" class="control-label x100"><span class="red">*</span>结束时间：</label>
                        <input type="text" name="endDate" id="f_project_endDate" value='<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20">
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_description" class="control-label x100">项目内容：</label>
                        <textarea name="description" id="f_project_description" data-toggle="autoheight" cols="62" rows="2" maxlength="400">${entity.description}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_project_faceDepts" class="control-label x100"><span class="red">*</span>面向岗位(可多选)：</label>
                         <select id="f_project_faceDepts" name="faceDepts"  data-rule="required"  data-toggle="selectpicker" multiple data-width="200px">
			               	<c:forEach items="${faceDeptses }" var="item">
			               		<option  value="${item.id }" <c:if test="${fn:contains(entity.faceDepts, item.id)}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                    <td>
                        <label for="f_project_teacherTypes" class="control-label x100"><span class="red">*</span>面向学段(可多选)：</label>
                         <select id="f_project_teacherTypes" name="teacherTypes"  data-rule="required"  data-toggle="selectpicker" multiple data-width="200px">
			               	<c:forEach items="${teacherTypeses }" var="item">
			               		<option  value="${item.value }" <c:if test="${fn:contains(entity.teacherTypes, item.value)}">selected</c:if>>${item.desc }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_implWay" class="control-label x100"><span class="red">*</span>培训方式：</label>
                         <select id="f_project_implWay" name="implWay"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${implWays }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.implWay}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                    <td>
                        <label for="f_project_personNum" class="control-label x100"><span class="red">*</span>人数：</label>
                        <input type="text" name="personNum" id="f_project_personNum" value='<fmt:formatNumber value="${entity.personNum }" pattern="#" type="number"/>' data-rule="required;digits" maxlength="6" size="20">
                    </td>
                </tr>
                 <tr>
                     <td colspan="2">
                         <label for="f_project_createType0" class="control-label x100"><span class="red">*</span>学时生成方式：</label><input type="radio" name="scoreCreateType" id="f_project_createType0" data-toggle="icheck" value="0" data-rule="checked" <c:if test="${entity.scoreCreateType==0}">checked</c:if> data-label="自动生成学时：由培训机构直接导入学时。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"><br>
                         <label for="f_project_createType1" class="control-label x100"></label><input type="radio" name="scoreCreateType" id="f_project_createType1" data-toggle="icheck" value="1" <c:if test="${entity.scoreCreateType==1}">checked</c:if> data-label="管理员确认生成学时：培训结束后，管理员通过【生成学时】按钮生成学时。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"><br>
                         <label for="f_project_createType2" class="control-label x100"></label><input type="radio" name="scoreCreateType" id="f_project_createType2" data-toggle="icheck" value="2" <c:if test="${entity.scoreCreateType==2}">checked</c:if> data-label="个人申报学时：培训机构直接导入的参培人名单，不做关联，只做参考。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"><br>
                     </td>
                 </tr>
				 <tr> 
                    <td colspan="2">
                        <label for="f_project_memo" class="control-label x100">备注：</label>
                         <textarea name="memo" id="f_project_memo" data-toggle="autoheight" cols="62" rows="2" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="Project:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>