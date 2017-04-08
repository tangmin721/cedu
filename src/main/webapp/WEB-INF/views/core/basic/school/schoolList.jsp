<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#school_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#school_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_school_add').on('click', function(e) {
	$(this).dialog({id:'school_form', url:'${ctx}/core/basic/school/schoolForm', type:'POST',title:'添加学校',mask:'true',width:'660',height:'450'});
});

$('#but_school_edit').on('click', function(e) {
	var list = school.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'school_form', url:'${ctx}/core/basic/school/schoolForm/'+id, type:'POST',title:'编辑学校',mask:'true',width:'660',height:'450'});
	}
});

$('#but_school_delete').on('click', function(e) {
	var list = school.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/school/deleteSchoolByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:function(json){
			if(json.statusCode=='350'){
				var trMsg = '';
    			$(json.data).each(function(){
    				trMsg += '<div><span class="warning"><i class="glyphicon glyphicon-info-sign"></i></span> &nbsp;&nbsp;'
        			+ this
        			+'</br>'
        			+'</div>'
        			;
        		});
    			$(this).alertmsg('error', trMsg);
	    	}else{
	    		$(this).alertmsg('ok', '删除成功！');
	    		$('.school_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/school/schoolList'});
	    	}
		}
		});	
	}
});

$('#but_school_import').on('click',function(e){
	$(this).dialog({id:'school_form', url:'${ctx}/core/basic/school/schoolImport', type:'POST',title:'导入学校信息',mask:'true',width:'660',height:'560'});
});

$('#school_export_all').on('click',function(e){
	$(this).alertmsg('confirm', '确定要导出全部学校信息吗？',{
		okCall:function(){
			window.location.href='${ctx }/core/basic/school/exportAll';
		}
	});
});

$('#school_export_search').on('click',function(e){
	$(this).alertmsg('confirm', '确定要根据查询条件，导出学校信息吗？',{
		okCall:function(){
			var params = $('.school_pager_form').serialize(); // http request parameters. 
			params = decodeURIComponent(params,true);
			params = encodeURI(encodeURI(params));
			window.location.href='${ctx }/core/basic/school/exportSearch?'+params;
		}
	});
});


function delRefushSchoolTable(json){
	$('.school_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/school/schoolList'});
}


var school = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#school_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}

function schoolView(id){
	  $(this).dialog({id:'school-view-dialog', url:'${ctx}/core/basic/school/schoolViewForm/'+id, title:'学校详细信息',mask:'true',width:'650',height:'300'});
}

</script>

<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<button type="button" id="but_school_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		<button type="button" id="but_school_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		<button type="button" id="but_school_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
	</div>
	<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
	</div>
	<div class="pull-left">
        <div class="btn-group">
        	<button type="button" id="but_school_import" class="btn btn-dark-blue" data-icon="sign-in" >导入</button>
            <button type="button" class="btn-purple dropdown-toggle" data-toggle="dropdown" data-icon="sign-out">导出<span class="caret"></span></button>
            <ul class="dropdown-menu right" role="menu">
                <li><a href="javascript:;" id="school_export_all">&nbsp;<span class="fa fa-asterisk"></span>&nbsp; 导出<span style="color: green;">全部</span></a></li>
                <li class="divider"></li>
                <li><a href="javascript:;" id="school_export_search" >&nbsp;<span class="fa fa-search"></span>&nbsp; 按<span style="color: blue;">查询条件</span>导出</a></li>
            </ul>
        </div>
    </div>
    <form id="pagerForm" class="school_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/school/schoolList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#school_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="school_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#school_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="school_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#school_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
			<label>校名</label><input type="text" value="${query.name }" name="name" maxlength="20" class="form-control" size="10">&nbsp;
			
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>学校类别</label>
			<select name="category" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${categorys }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.category}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>学校大类型</label>
			<select name="schoolType" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${schoolTypes }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.schoolType}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>学校类型</label>
			<select name="type" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${types }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.type}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>学校编号</label><input type="text" value="${query.schoolNo }" maxlength="10" name="schoolNo" class="form-control" size="10">&nbsp;
			<label>学校地址</label><input type="text" value="${query.address }" maxlength="10" name="address" class="form-control" size="10">&nbsp;
			<label>邮编</label><input type="text" value="${query.postCode }" maxlength="6" name="postCode" class="form-control" size="10">&nbsp;
			<label>校长</label><input type="text" value="${query.master }" maxlength="10" name="master" class="form-control" size="10">&nbsp;
			<label>校办电话</label><input type="text" value="${query.tel }" maxlength="12" name="tel" class="form-control" size="10">&nbsp;
			<label>负责人</label><input type="text" value="${query.director }" maxlength="10" name="director" class="form-control" size="10">&nbsp;
			<label>负责人身份证</label><input type="text" value="${query.idCard }" maxlength="18" name="idCard" class="form-control" size="10">&nbsp;
			<label>负责人电话</label><input type="text" value="${query.theTel }" maxlength="12" name="theTel" class="form-control" size="10">&nbsp;
			<label>负责人手机</label><input type="text" value="${query.phone }" maxlength="11" name="phone" class="form-control" size="10">&nbsp;
			<label>负责人Email</label><input type="text" value="${query.email }" maxlength="20" name="email" class="form-control" size="10">&nbsp;
			<!-- 
			<label>状态</label>
			<select name="status" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${schoolStatus }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.status}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;
			-->
        </div>
    </form>
</div>

<div id="school_table" class="bjui-pageContent tableContent">
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
			<th align="center" data-order-field="name">学校名称</th>
			<th align="center" data-order-field="schoolNo">编号</th>
			<th align="center" data-order-field="category">类别</th>
			<th align="center" data-order-field="type">大类型</th>
			<th align="center" data-order-field="type">类型</th>
			<th align="center" data-order-field="address">地址</th>
			<th align="center" data-order-field="postCode">邮编</th>
			<th align="center" data-order-field="master">校长</th>
			<th align="center" data-order-field="tel">校办电话</th>
			<!--  
			<th align="center" data-order-field="director">负责人</th>
			<th align="center" data-order-field="idCard">负责人身份证</th>
			<th align="center" data-order-field="theTel">负责人电话</th>
			<th align="center" data-order-field="phone">负责人手机</th>
			<th align="center" data-order-field="email">负责人邮箱</th>
			<th align="center" data-order-field="status">状态</th>-->
    	</tr>
    	</thead>
    	<tbody>
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
			<td><a href="javascript:;" onclick="schoolView(${item.id })">${item.name }</a></td>
			<td>${item.schoolNo }</td>
			<td>${item.categoryName }</td>
			<td>
				<c:if test="${item.schoolType ==0}"><span class="label label-info">中小学校</span></c:if>
				<c:if test="${item.schoolType ==1}"><span class="label label-primary">中等职业学校</span></c:if>
				<c:if test="${item.schoolType ==2}"><span class="label label-warning">特殊教育学校</span></c:if>
				<c:if test="${item.schoolType ==3}"><span class="label label-success">幼儿园</span></c:if>
			</td>
			<td>${item.typeName }</td>
			<td>${item.address }</td>
			<td>${item.postCode }</td>
			<td>${item.master }</td>
			<td>${item.tel }</td>
			<!--
			<td>${item.director }</td>
			<td>${item.idCard }</td>
			<td>${item.theTel }</td>
			<td>${item.phone }</td>
			<td>${item.email }</td>
			<td>
				<c:if test="${item.status ==1}"><span class="label label-default">未确认</span></c:if>
				<c:if test="${item.status ==2}"><span class="label label-danger">已确认</span></c:if>
				<c:if test="${item.status ==3}"><span class="label label-success">已审核</span></c:if>
			</td>-->
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>