<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#user_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#user_table').on('click','tr:not(.trth)',function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_user_add').on('click', function(e) {
	$(this).dialog({id:'user_form', url:'${ctx}/system/user/userAddForm', type:'POST',title:'添加用户',mask:'true',width:'800',height:'400'});
});

$('#but_user_edit').on('click', function(e) {
	var list = user.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'user_form', url:'${ctx}/system/user/userUpdateForm/'+id, type:'POST',title:'编辑用户',mask:'true',width:'800',height:'400'});
	}
});

$('#but_user_role').on('click', function(e) {
	var list = user.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要分配的人！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一个人进行分配！');
	}else{
		var id = list[0];
		$(this).dialog({id:'user_form', url:'${ctx}/system/user/userRoleManage/'+id, type:'POST',title:'用户角色分配',mask:'true',width:'520',height:'520'});
	}
});


$('#but_user_resetpwd').on('click', function(e) {
	var list = user.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要重置密码的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行重置密码！');
	}else{
		var id = list[0];
		$.ajax({ 
	        type: "POST", 
	        url: '${ctx}/system/user/resetPwd/'+id,
	        dataType : "json",
	        success: function(data) {
	        	$(this).alertmsg('ok', '重置密码成功！');
	        },
	        error :  function(){
	        	$(this).alertmsg('error', '系统错误！');
	        }
	    }); 
	}
});

$('#but_user_delete').on('click', function(e) {
	var list = user.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/user/deleteUserByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushUserTable"});
	}
});

function delRefushUserTable(json){
	$('.user_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/user/userList'});
}


var user = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#user_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}

$('#user_export_all').on('click',function(e){
	$(this).alertmsg('confirm', '确定要导出全部用户信息吗？',{
		okCall:function(){
			window.location.href='${ctx }/system/user/exportAll';
		}
	});
});

$('#user_export_search').on('click',function(e){
	$(this).alertmsg('confirm', '确定要根据查询条件，导出用户信息吗？',{
		okCall:function(){
			var params = $('.user_pager_form').serialize(); // http request parameters. 
			params = decodeURIComponent(params,true);
			params = encodeURI(encodeURI(params));
			window.location.href='${ctx }/system/user/exportSearch?'+params;
		}
	});
});

</script>

<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<button type="button" id="but_user_add" class="btn btn-blue" data-icon="user-plus" >添加</button>
		<button type="button" id="but_user_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		<button type="button" id="but_user_delete" class="btn btn-red" data-icon="user-times" >删除</button>
	</div>
	<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
	</div>
	<div class="btn-group pull-left" role="group">
		<shiro:hasPermission name="User:export">
	    	<button type="button" class="btn-purple dropdown-toggle" data-toggle="dropdown" data-icon="sign-out">导出<span class="caret"></span></button>
	        <ul class="dropdown-menu right" role="menu">
	        	<li><a href="javascript:;" id="user_export_all">&nbsp;<span class="fa fa-asterisk"></span>&nbsp; 导出<span style="color: green;">全部</span></a></li>
	            <li class="divider"></li>
	            <li><a href="javascript:;" id="user_export_search" >&nbsp;<span class="fa fa-search"></span>&nbsp; 按<span style="color: blue;">查询条件</span>导出</a></li>
	    	</ul>
		</shiro:hasPermission>
	</div>
	<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
	</div>
	<shiro:hasPermission name="Role:update">
		<div class="btn-group pull-left" role="group">
			<button type="button" id="but_user_role" class="btn btn-orange" data-icon="users" >分配角色</button>
		</div>
	</shiro:hasPermission>
	<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
	</div>
	<div class="btn-group pull-left" role="group">
		<button type="button" id="but_user_resetpwd" class="btn btn-danger" data-icon="refresh" >重置密码</button>
	</div>
	
    <form id="pagerForm" class="user_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/user/userList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        
        <div class="bjui-searchBar pull-right">
            <shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#user_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="120px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="user_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#user_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="120px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="user_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#user_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="120px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="user_list_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="120px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
			<label>用户类型</label>
				<select name="type" data-toggle="selectpicker" data-width="100px">
                      		<option value="">--请选择--</option>
                      	<c:forEach items="${userTypes }" var="item">
                      		<option  value="${item.value }" <c:if test="${item.value==query.type}">selected</c:if>>${item.desc }</option>
                      	</c:forEach>
				</select>
			<button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>单位</label><input type="text" value="${query.unit }" name="unit" class="form-control" size="10" >&nbsp;
        	<label>登录名</label><input type="text" value="${query.loginName }" name="loginName" class="form-control" size="10" >&nbsp;
			<label>状态</label>
				<select name="status" data-toggle="selectpicker" data-width="100px">
                      	<option value="">--请选择--</option>
                      	<c:forEach items="${userStatus }" var="item">
                      		<option  value="${item.value }" <c:if test="${item.value==query.status}">selected</c:if>>${item.desc }</option>
                      	</c:forEach>
				</select>
			<!--  
            <label>用户编号</label><input type="text" value="${query.userNo }" name="userNo" class="form-control" size="10">&nbsp;
            -->
            <label>姓名</label><input type="text" value="${query.realName }" name="realName" class="form-control" size="10">&nbsp;
            <!-- 
			<label>手机</label><input type="text" value="${query.mobileNo }" name="mobileNo" class="form-control" size="10">&nbsp;
			<label>Email</label><input type="text" value="${query.email }" name="email" class="form-control" size="10">&nbsp;
			-->
        </div>
    </form>
</div>

<div id="user_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="36" title="序号">No.</th>
    		<!--  <th align="center" data-order-field="userNo">用户编号</th>-->
    		<th align="center" data-order-field="province" title="省">省</th>
    		<th align="center" data-order-field="city">市</th>
    		<th align="center" data-order-field="town">区县</th>
    		<th align="center" data-order-field="shcool">学校</th>
    		<th align="center" data-order-field="unit">单位</th>
			<th align="center" data-order-field="loginName">登录名</th>
			<th align="center" data-order-field="realName">姓名</th>
			<!--  <th align="center" data-order-field="mobileNo">手机号</th>
			<th align="center" data-order-field="email">Email</th>-->
			<th align="center" data-order-field="status">状态</th>
			<th align="center" data-order-field="type">用户类型</th>
			
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<!--<td>${item.userNo }</td>-->
			
			<td>${item.provinceName }</td>
			<td>${item.cityName }</td>
			<td>${item.townName }</td>
			<td>${item.schoolName }</td>
			<td>${item.unit }</td>
			<td>${item.loginName }</td>
			<td>${item.realName }</td>
			<!--<td>${item.mobileNo }</td>
			<td>${item.email }</td>-->
			<td>
				<c:if test="${item.status ==1}"><span class="label label-success">已激活</span></c:if>
				<c:if test="${item.status ==2}"><span class="label label-danger">未激活</span></c:if>
				<c:if test="${item.status ==3}"><span class="label label-default">已锁定</span></c:if>
			</td>
			<td>
				<c:if test="${item.type ==0}"><span class="label label-danger">超级管理员</span></c:if>
				<c:if test="${item.type ==1}"><span class="label label-danger">系统管理员</span></c:if>
				<c:if test="${item.type ==2}"><span class="label label-primary">省级管理员</span></c:if>
				<c:if test="${item.type ==3}"><span class="label label-primary">市级管理员</span></c:if>
				<c:if test="${item.type ==4}"><span class="label label-primary">区县管理员</span></c:if>
				<c:if test="${item.type ==5}"><span class="label label-primary">校级管理员</span></c:if>
				<c:if test="${item.type ==6}"><span class="label label-info">教师个人用户</span></c:if>
				<c:if test="${item.type ==7}"><span class="label label-info">校长个人用户</span></c:if>
				<c:if test="${item.type ==8}"><span class="label label-warning">其他</span></c:if>
				<c:if test="${item.type ==9}"><span class="label label-warning">培训机构管理员</span></c:if>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>