<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProjectTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.project_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/base/projectList'});
		$(this).dialog('closeCurrent');
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
                alertMsg.error("系统错误！");
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
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                     <td colspan="2">
                        <label for="f_project_name" class="control-label x100"><span class="red">*</span>项目名称：</label>
                        <input type="text" name="name" id="f_project_name" value="${entity.name }" data-rule="required" maxlength="62" size="62" disabled>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_stage" class="control-label x100"><span class="red">*</span>学段：</label>
                        <select id="f_project_stage" name="stage"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" disabled>
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${stages }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.stage}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                     <td>
                        <label for="f_project_course" class="control-label x100"><span class="red">*</span>学科：</label>
                        <select id="f_project_course" name="course"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" disabled>
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${courses }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.course}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_trainType" class="control-label x100"><span class="red">*</span>培训类型：</label>
                        <select id="f_project_trainType" name="trainType"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" disabled>
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${trainTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.trainType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_schoolYear" class="control-label x100"><span class="red">*</span>年度：</label>
                        <select id="f_project_schoolYear" name="schoolYear"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" disabled>
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${schoolYears }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.schoolYear}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                     <td>
                        <label for="f_project_term" class="control-label x100"><span class="red">*</span>学期：</label>
                        <select id="f_project_term" name="term"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" disabled>
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${terms }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.term}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                     <td colspan="2">
                        <label for="f_project_ratioScore" class="control-label x100"><span class="red">*</span>学分：学时</label>
                        <input type="text" name="ratioScore" id="f_project_ratioScore" value='<fmt:formatNumber value="${entity.ratioScore }" pattern="#.##"/>' data-rule="required" maxlength="9" size="9" disabled> : 
                         <input type="text" name="ratioHour" id="f_project_ratioHour" value='<fmt:formatNumber value="${entity.ratioHour }" pattern="#.##"/>' data-rule="required" maxlength="9" size="9" disabled>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_score" class="control-label x100"><span class="red">*</span>学分：</label>
                        <input type="text" name="score" id="f_project_score" value='<fmt:formatNumber value="${entity.score }" pattern="#.##"/>' data-rule="required" maxlength="20" size="20" disabled>
                    </td>
                     <td>
                        <label for="f_project_hour" class="control-label x100"><span class="red">*</span>学时：</label>
                        <input type="text" name="hour" id="f_project_hour" value='<fmt:formatNumber value="${entity.hour }" pattern="#.##"/>' data-rule="required" maxlength="20" size="20" disabled>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_startDate" class="control-label x100"><span class="red">*</span>开始时间：</label>
                        <input type="text" name="startDate" id="f_project_startDate" value='<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20" disabled>
                    </td>
                    <td>
                        <label for="f_project_endDate" class="control-label x100"><span class="red">*</span>结束时间：</label>
                        <input type="text" name="endDate" id="f_project_endDate" value='<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20" disabled>
                    </td>
                </tr>
				 <tr>
                      <td colspan="2">
                        <label for="f_project_trainTageter" class="control-label x100"><span class="red">*</span>培训对象：</label>
                        <input type="text" name="trainTageter" id="f_project_trainTageter" value="${entity.trainTageter }" data-rule="required" maxlength="20" size="20" disabled>
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_checkTypes" class="control-label x100">考核方式：</label>
                        <c:forEach items="${checkTypes }" var="item" varStatus="index">
                       		 <input type="checkbox" name="checkType" value="${item.id }" id="f_project_checkTypes" data-toggle="icheck" data-label="${item.name }" <c:if test="${checkTypeIdList.contains(item.id) }">checked</c:if> disabled>
			             </c:forEach>
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_description" class="control-label x100">项目内容：</label>
                        <textarea name="description" id="f_project_description" data-toggle="autoheight" cols="62" rows="2" maxlength="400" disabled>${entity.description}</textarea>
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_taget" class="control-label x100">项目目标：</label>
                        <textarea name="taget" id="f_project_taget" data-toggle="autoheight" cols="62" rows="2" maxlength="400" disabled>${entity.taget}</textarea>
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_content" class="control-label x100">内容简介：</label>
                        <textarea name="content" id="f_project_content" data-toggle="autoheight" cols="62" rows="2" maxlength="400" disabled>${entity.content}</textarea>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_implWay" class="control-label x100"><span class="red">*</span>培训方式：</label>
                        <input type="radio" name="implWay" id="f_project_implWay1" data-toggle="icheck" value="1" data-rule="checked" disabled <c:if test="${entity.implWay==1}">checked</c:if> data-label="面授培训&nbsp;&nbsp;">
                        <input type="radio" name="implWay" id="f_project_implWay2" data-toggle="icheck" value="0" disabled <c:if test="${entity.implWay==0}">checked</c:if> data-label="远程培训">
                    </td>
                    <td>
                        <label for="f_project_confirmFlag" class="control-label x100"><span class="red">*</span>确认开关：</label>
                        <input id="f_project_confirmFlag" type="checkbox"  name="confirmFlag"  data-size="mini" data-on-color="success" 
                        	data-off-color="warning" data-on-text="需要确认"  data-off-text="不需要确认" <c:if test="${entity.confirmFlag }">checked</c:if> disabled>
                    </td>
                </tr>
				 <tr> 
                    <td colspan="2">
                        <label for="f_project_memo" class="control-label x100">备注：</label>
                         <textarea name="memo" id="f_project_memo" data-toggle="autoheight" cols="62" rows="2" maxlength="400" disabled>${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
</div>
