<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});


$('#but_teacher_wizard_add').on('click', function(e) {
	$(this).dialog({id:'teacher_wizard_form', url:'${ctx}/core/basic/teacher/base/wizardForm?type=${type}', type:'POST',title:'添加教师',
			//onClose:function(){
			//	$('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
			//},
			mask:'true',width:'1120',height:'680',resizable:'false'});
});

$('#but_teacher_wizard_edit').on('click', function(e) {
	var list = teacher.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_wizard_form', url:'${ctx}/core/basic/teacher/base/wizardForm/'+id, type:'POST',title:'编辑教师',
			//onClose:function(){
			//	$('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
			//},
		mask:'true',width:'1120',height:'680',resizable:'false'});
	}
});

$('#but_teacher_delete').on('click', function(e) {
	var list = teacher.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/base/deleteTeacherByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherTable"});
	}
});


$('#but_teacher_import').on('click',function(e){
	$(this).dialog({id:'school_form', url:'${ctx}/core/basic/teacher/base/teacherImport/${type}', type:'POST',title:'导入教师信息',mask:'true',width:'660',height:'560'});
});

$('#teacher_export_all').on('click',function(e){
	$(this).alertmsg('confirm', '确定要导出全部教师信息吗？',{
		okCall:function(){
			window.location.href='${ctx }/core/basic/teacher/base/exportAll/${type}';
		}
	});
});

$('#teacher_export_search').on('click',function(e){
	$(this).alertmsg('confirm', '确定要根据查询条件，导出教师信息吗？',{
		okCall:function(){
			var params = $('.teacher_pager_form').serialize(); // http request parameters. 
			params = decodeURIComponent(params,true);
			params = encodeURI(encodeURI(params));
			window.location.href='${ctx }/core/basic/teacher/base/exportSearch/${type}?'+params;
		}
	});
});

$('#teacher_export_check').on('click',function(e){
	var list = teacher.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要导出的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行导出！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_form', url:'${ctx}/core/basic/teacher/base/teacherForm/'+id, type:'POST',title:'导出教师',mask:'true',width:'820',height:'520'});
	}
});

function delRefushTeacherTable(json){
	$('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
}

var teacher = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}

$("#f_teacher_base_query_stage").change(function(){
    var stage = $("#f_teacher_base_query_stage option:selected").val();
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
                $("#f_teacher_base_query_course").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_teacher_base_query_course").append(str);  
                }
                $('#f_teacher_base_query_course').selectpicker('refresh');
            },
            error :  function(){
                $(this).alertmsg('error', '系统错误！');
            }
        }); 
    }else{
      	 $("#f_teacher_base_query_course").html("<option value=''>--请选择--</option>");
       	 $('#f_teacher_base_query_course').selectpicker('refresh');
    }
    });
</script>

<div class="bjui-pageHeader">
		<div class="btn-group pull-left" role="group">
			<shiro:hasPermission name="Teacher:create">
				<button type="button" id="but_teacher_wizard_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="Teacher:update">
				<button type="button" id="but_teacher_wizard_edit" class="btn btn-green" data-icon="edit" >编辑</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="Teacher:delete">
				<button type="button" id="but_teacher_delete" class="btn btn-red" data-icon="times-circle">删除</button>
			</shiro:hasPermission>
		</div>
		<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
		</div>
		<div class="pull-left">
	        <div class="btn-group">
				<shiro:hasPermission name="Teacher:import">
	        	<button type="button" id="but_teacher_import" class="btn btn-dark-blue" data-icon="sign-in" >导入</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="Teacher:export">
	            <button type="button" class="btn-purple dropdown-toggle" data-toggle="dropdown" data-icon="sign-out">导出<span class="caret"></span></button>
	            <ul class="dropdown-menu right" role="menu">
	                <li><a href="javascript:;" id="teacher_export_all">&nbsp;<span class="fa fa-asterisk"></span>&nbsp; 导出<span style="color: green;">全部</span></a></li>
	                <li class="divider"></li>
	                <li><a href="javascript:;" id="teacher_export_search" >&nbsp;<span class="fa fa-search"></span>&nbsp; 按<span style="color: blue;">查询条件</span>导出</a></li>
	            </ul>
				</shiro:hasPermission>
	        </div>
	    </div>
    <form id="pagerForm" class="teacher_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/base/teacherList/${type}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#teacher_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="teacher_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#teacher_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="teacher_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#teacher_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="teacher_list_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
            <label>姓名</label><input type="text" value="${query.name }" name="name" class="form-control" size="10" maxlength="8">&nbsp;
            <label>标识码</label><input type="text" value="${query.tno }" name="tno" class="form-control" size="19" maxlength="19">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	
        	
            <%-- <label>生日从</label>
			 <input type="text" name="startBirthday" id="f_teacher_birthday" value='<fmt:formatDate value="${query.startBirthday }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="11">
			<label>到</label><input type="text" name="endBirthday" id="f_teacher_birthday" value='<fmt:formatDate value="${query.endBirthday }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="11">
			 --%>
			<br>
			<br>
			 <label>民族</label>
			<select name="nation" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${nations }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.nation}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>身份证</label><input type="text" value="${query.idCard }" name="idCard" class="form-control" maxlength="18" size="18">&nbsp;
			
        </div>
    </form>
</div>

<div id="teacher_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<c:if test="${sessionScope.currentUser.province==0}">
    		<th align="center" data-order-field="province">省</th>
    		</c:if>
			<c:if test="${sessionScope.currentUser.city==0}">
    		<th align="center" data-order-field="city">市</th>
    		</c:if>
			<c:if test="${sessionScope.currentUser.town==0}">
    		<th align="center" data-order-field="town">区县</th>
    		</c:if>
			<c:if test="${sessionScope.currentUser.school==0}">
    		<th align="center" data-order-field="school">学校</th>
    		</c:if>
    		<!--  <th align="center" data-order-field="type">级别</th> -->
    		<th align="center" data-order-field="tno">标识码</th>
			<th align="center" data-order-field="name">姓名</th>
			<th align="center" data-order-field="gender">性别</th>
			<th align="center" data-order-field="birthday">出生日期</th>
			
			<!--<th align="center" data-order-field="nativer">籍贯</th>
			<th align="center" data-order-field="nation">民族</th>
			<th align="center" data-order-field="idCard">身份证</th>
			<th align="center" data-order-field="tel">办公电话</th>
			<th align="center" data-order-field="mobile">手机号</th>
			<th align="center" data-order-field="email">Email</th>	
			<th align="center" data-order-field="status">状态</th>-->
    	</tr>
    	</thead>
    	<tbody>
    	<shiro:hasPermission name="Teacher:read">
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<c:if test="${sessionScope.currentUser.province==0}">
			<td>${item.provinceName }</td>
			</c:if>
			<c:if test="${sessionScope.currentUser.city==0}">
			<td>${item.cityName }</td>
			</c:if>
			<c:if test="${sessionScope.currentUser.town==0}">
			<td>${item.townName }</td>
			</c:if>
			<c:if test="${sessionScope.currentUser.school==0}">
			<td>${item.schoolName }</td>
			</c:if>
			<td>${item.tno }</td>
			<td>
				<a href="javascript:;" onclick="teacherView(${item.id })">${item.name }</a>
			</td>
			<td>${item.genderName }</td>
			<td><fmt:formatDate value="${item.birthday }" pattern="yyyy-MM-dd"/></td>
    	</tr>
    	</c:forEach>
    	</shiro:hasPermission>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>