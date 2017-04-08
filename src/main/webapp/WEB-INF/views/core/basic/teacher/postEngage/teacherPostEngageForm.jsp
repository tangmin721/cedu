<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/teacher.css">
<script type="text/javascript">
function refushTeacherPostEngageTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_post_engage_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/postEngage/teacherPostEngageList/${tid}/${tfs_type}'});
		$(this).dialog('closeCurrent');
	}
}

$("#f_teacher_post_engage_postType").change(function(){
    var postType = $("#f_teacher_post_engage_postType option:selected").val();
    if(postType){
   	 //联动岗位等级下拉框
   	 $.ajax({ 
            type: "POST", 
            url: "${ctx}/core/basic/teacher/postEngage/postLevel/"+postType,
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_teacher_post_engage_postLevel").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_teacher_post_engage_postLevel").append(str);  
                }
                $('#f_teacher_post_engage_postLevel').selectpicker('refresh');
            },
            error :  function(){
                $(this).alertmsg('error', '系统错误！');
            }
        }); 
   	 
    }else{
   	 $("#f_teacher_post_engage_postLevel").html("<option value=''>--请选择--</option>");
   	 $('#f_teacher_post_engage_postLevel').selectpicker('refresh');
    }
});

$("#f_teacher_post_engage_parttimeFlag").change(function(){
    var partTimeFlag = $("#f_teacher_post_engage_parttimeFlag option:selected").text();
    if(partTimeFlag == "1-是"){
    	$.ajax({ 
            type: "POST", 
            url: "${ctx}/core/basic/teacher/postEngage/postType",
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_teacher_post_engage_parttimeType").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_teacher_post_engage_parttimeType").append(str);  
                }
                $('#f_teacher_post_engage_parttimeType').selectpicker('refresh');
            },
            error :  function(){
                $(this).alertmsg('error', '系统错误！');
            }
        }); 
    }else{
    	$("#f_teacher_post_engage_parttimeType").html("<option value=''>--请选择--</option>");
      	$('#f_teacher_post_engage_parttimeType').selectpicker('refresh');
      	$("#f_teacher_post_engage_parttimeLevel").html("<option value=''>--请选择--</option>");
      	$('#f_teacher_post_engage_parttimeLevel').selectpicker('refresh');
    }
});

$('#f_teacher_post_engage_parttimeType_td').on('click',function(){
	var partTimeFlag = $("#f_teacher_post_engage_parttimeFlag option:selected").text();
    if(partTimeFlag == "1-是"){
	$.ajax({ 
        type: "POST", 
        url: "${ctx}/core/basic/teacher/postEngage/postType",
        dataType : "json",
        success: function(data) {
            var str = "";
            if(data){
                $(data).each(function(n){
                    str += "<option value='"+this.id+"'>" + this.name+"</option>";
                });
            }
            $("#f_teacher_post_engage_parttimeType").html("<option value=''>--请选择--</option>");
            if(str!=""){
                $("#f_teacher_post_engage_parttimeType").append(str);  
            }
            $('#f_teacher_post_engage_parttimeType').selectpicker('refresh');
        },
        error :  function(){
            $(this).alertmsg('error', '系统错误！');
        }
    }); 
	}
})

$("#f_teacher_post_engage_parttimeType").change(function(){
    var postpartTypeName = $("#f_teacher_post_engage_parttimeType option:selected").text();
    var postpartType = $("#f_teacher_post_engage_parttimeType option:selected").val();
    var psotType = $("#f_teacher_post_engage_postType option:selected").text();
    
    if(postpartTypeName == psotType){
    	$(this).alertmsg('warn', '兼任岗位类别不能和岗位类别相同！')

    	$("#f_teacher_post_engage_parttimeType").html("<option value='' >--请选择--</option>");
    	$('#f_teacher_post_engage_parttimeType').selectpicker('refresh');
    	$("#f_teacher_post_engage_parttimeLevel").html("<option value=''>--请选择--</option>");
      	$('#f_teacher_post_engage_parttimeLevel').selectpicker('refresh');
    	return;
    }
    
    if(postpartTypeName == "0-无其他岗位类别"){
    	$("#f_teacher_post_engage_parttimeLevel").html("<option value=''></option>");
      	$('#f_teacher_post_engage_parttimeLevel').selectpicker('refresh');
    }else if(postpartType){
   	 //联动岗位等级下拉框
   	 $.ajax({ 
            type: "POST", 
            url: "${ctx}/core/basic/teacher/postEngage/postLevel/"+postpartType,
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_teacher_post_engage_parttimeLevel").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_teacher_post_engage_parttimeLevel").append(str);  
                }
                $('#f_teacher_post_engage_parttimeLevel').selectpicker('refresh');
            },
            error :  function(){
                $(this).alertmsg('error', '系统错误！');
            }
        }); 
   	 
    }else{
   	 $("#f_teacher_post_engage_parttimeLevel").html("<option value=''>--请选择--</option>");
   	 $('#f_teacher_post_engage_parttimeLevel').selectpicker('refresh');
    }
});

$("#f_teacher_post_engage_duty").change(function(){
	var dutyName = $("#f_teacher_post_engage_duty option:selected").text();
	if(dutyName == "00-无"){
		$("#f_teacher_post_engage_dutyStartDate").val("");
		$("#f_teacher_post_engage_dutyStartDate").attr("disabled", true);
	}else{
		$("#f_teacher_post_engage_dutyStartDate").attr("disabled", false);
	}
});

//校级职务多选限定
$("#f_teacher_post_engage_duty").change(function (){
	var dutyName = $("#f_teacher_post_engage_duty option:selected").text();
	var str = dutyName.split(" ");
	for(var i = 0; i < str.length; i++){
		if(str[i] == "00-无" && str.length > 2){
			$(this).alertmsg('warn', '选择【00-无】时，不能多选！')
			$("#f_teacher_post_engage_duty option").removeAttr("selected");
			$("#f_teacher_post_engage_duty").selectpicker('refresh');
			return;
		}
	}
});
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/postEngage/saveTeacherPostEngage" method="post" id="f_teacher_post_engage_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherPostEngageTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <div>
        	<span class="teacher_top_title_my"><span class="red">*</span>从当前状态开始录入</span> 	
	    </div>
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_study_exp_seq" class="control-label x140"><span class="red">*</span>序号：</label>
                        <input type="hidden" name="tid" id="f_teacher_study_exp_tid" value="${entity.tid }">
                        <input type="text" name="seq" id="f_teacher_study_exp_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_post_engage_postType" class="control-label x140"><span class="red">*</span>岗位类别：</label>
                        <select id="f_teacher_post_engage_postType" name="postType" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${postTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.postType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_post_engage_postLevel" class="control-label x140"><span class="red">*</span>岗位等级：</label>
                        <select id="f_teacher_post_engage_postLevel" name="postLevel" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option  value="${entity.postLevel }" >${entity.postLevelName }</option>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_post_engage_startDate" class="control-label x140"><span class="red">*</span>聘任开始年月：</label>
                    	<input type="text" name="startDate" id="f_teacher_post_engage_startDate" value="<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>" data-rule="required;date" data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_post_engage_parttimeFlag" class="control-label x140"></span>是否兼任其他岗位：</label>
                        <select id="f_teacher_post_engage_parttimeFlag" name="parttimeFlag" data-toggle="selectpicker"  data-width="200px">
			               	<c:forEach items="${parttimeFlag }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.parttimeFlag}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td id="f_teacher_post_engage_parttimeType_td">
                        <label for="f_teacher_post_engage_parttimeType" class="control-label x140"></span>兼任岗位类别：</label>
                        <select id="f_teacher_post_engage_parttimeType" name="parttimeType" data-toggle="selectpicker"  data-width="200px" >
			               	<option  value="${entity.parttimeType }" >${entity.parttimeTypeName }</option>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_post_engage_parttimeLevel" class="control-label x140"></span>兼任岗位等级：</label>
                        <select id="f_teacher_post_engage_parttimeLevel" name="parttimeLevel" data-toggle="selectpicker"  data-width="200px">
			               	<option  value="${entity.parttimeLevel }" >${entity.parttimeLevelName }</option>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_post_engage_duty" class="control-label x140"><span class="red">*</span>校级职务：</label>
                        <select id="f_teacher_post_engage_duty" name="duty" data-toggle="selectpicker" data-rule="required" multiple data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${schoolDutyTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${empty entity.duty and item.name=='00-无'}">selected</c:if><c:if test="${item.checked}">selected</c:if>>${item.name } </option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_post_engage_dutyStartDate" class="control-label x140"></span>任职开始年月：</label>
                        <input type="text" name="dutyStartDate" id="f_teacher_post_engage_dutyStartDate" value="<fmt:formatDate value="${entity.dutyStartDate }" pattern="yyyy-MM-dd"/>" data-rule="date" data-toggle="datepicker" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherPostEngage:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>