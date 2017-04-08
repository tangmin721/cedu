<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherEduTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$(this).alertmsg('ok', json.message);
		$('#div_tw_edu').bjuiajax('refreshLayout', {
				target:'#div_tw_edu',
				url:"${ctx}/core/basic/teacher/edu/teacherEduForm/"+wizard_tid
		});
	}
}

//取消，刷新
$('#cancel_edu').off().on('click', function(e) {
	$('#div_tw_edu').bjuiajax('refreshLayout', {
		target:'#div_tw_edu',
		type:'post',
		url:"${ctx}/core/basic/teacher/edu/teacherEduForm/"+wizard_tid
	});
})

$("#f_teacher_edu_stage").change(function(){
         var stage = $("#f_teacher_edu_stage option:selected").val();
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
                     $("#f_teacher_edu_course").html("<option value=''>--请选择--</option>");
                     if(str!=""){
                         $("#f_teacher_edu_course").append(str);  
                     }
                     $('#f_teacher_edu_course').selectpicker('refresh');
                 },
                 error :  function(){
                     $(this).alertmsg('error', '系统错误！');
                 }
             }); 
        	 
        	//联动年级下拉框
        	 $.ajax({ 
                 type: "POST", 
                 url: "${ctx}/core/basic/conf/grade/grades/"+stage,
                 dataType : "json",
                 success: function(data) {
                     var str = "";
                     if(data){
                         $(data).each(function(n){
                             str += "<option value='"+this.id+"'>" + this.name+"</option>";
                         });
                     }
                     $("#f_teacher_edu_grade").html("<option value=''>--请选择--</option>");
                     if(str!=""){
                         $("#f_teacher_edu_grade").append(str);  
                     }
                     $('#f_teacher_edu_grade').selectpicker('refresh');
                 },
                 error :  function(){
                	 $(this).alertmsg('error', '系统错误！');
                 }
             }); 
         }else{
        	 $("#f_teacher_edu_course").html("<option value=''>--请选择--</option>");
        	 $('#f_teacher_edu_course').selectpicker('refresh');
        	 
        	 $("#f_teacher_edu_grade").html("<option value=''>--请选择--</option>");
        	 $('#f_teacher_edu_grade').selectpicker('refresh');
         }
         
         
        });
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/edu/saveTeacherEdu" method="post" id="f_teacher_edu_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherEduTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_edu_degree" class="control-label x100"><span class="red">*</span>学历：</label>
                         <input type="hidden" name="tid" id="f_teacher_edu_tid" value="${tid }">
                        <select id="f_teacher_edu_degree" name="degree"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${degrees }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.degree}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_edu_major" class="control-label x100"><span class="red">*</span>所学专业：</label>
                        <input type="text" name="major" id="f_teacher_edu_major" value="${entity.major }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_edu_univ" class="control-label x100"><span class="red">*</span>毕业学校：</label>
                        <input type="text" name="univ" id="f_teacher_edu_univ" value="${entity.univ }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_edu_teachedDay" class="control-label x100">从教时间：</label>
                        <input type="text" name="teachedDay" id="f_teacher_edu_teachedDay" value='<fmt:formatDate value="${entity.teachedDay }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_edu_workedDay" class="control-label x100">参加工作时间：</label>
                        <input type="text" name="workedDay" id="f_teacher_edu_workedDay" value='<fmt:formatDate value="${entity.workedDay }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_edu_stage" class="control-label x100"><span class="red">*</span>学段：</label>
                        <select id="f_teacher_edu_stage" name="stage"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${stages }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.stage}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_edu_course" class="control-label x100"><span class="red">*</span>学科：</label>
                        <select id="f_teacher_edu_course" name="course"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${courses }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.course}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_edu_grade" class="control-label x100">年级：</label>
                        <select id="f_teacher_edu_grade" name="grade"    data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${grades }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.grade}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				<tr>
                    <td>
                        <label for="f_teacher_edu_job_status" class="control-label x100"> 在岗状态：</label>
                        <select id="f_teacher_edu_job_status" name="jobStatus"    data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${jobStatuss }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.jobStatus}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
               	<tr>
                    <td>
                        <label for="f_teacher_edu_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_teacher_edu_memo" data-toggle="autoheight" cols="40" rows="5" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button id="cancel_edu" type="button" class="btn-red" data-icon="refresh">取消</button></li>
        <shiro:hasPermission name="TeacherEdu:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>