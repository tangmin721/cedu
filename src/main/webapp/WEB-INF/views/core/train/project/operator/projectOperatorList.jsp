<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#project_operator_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_operator_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_project_operator_add').on('click', function(e) {
	$(this).dialog({id:'project_operator_form', url:'${ctx}/core/train/project/operator/projectOperatorForm', type:'POST',title:'添加项目执行人',mask:'true',width:'600',height:'520'});
});

$('#but_project_operator_edit').on('click', function(e) {
	var list = projectOperator.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'project_operator_form', url:'${ctx}/core/train/project/operator/projectOperatorForm/'+id, type:'POST',title:'编辑项目执行人',mask:'true',width:'600',height:'520'});
	}
});

$('#but_project_operator_delete').on('click', function(e) {
	var list = projectOperator.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/operator/deleteProjectOperatorByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProjectOperatorTable"});
	}
});

function delRefushProjectOperatorTable(json){
	$('.project_operator_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/operator/projectOperatorList'});
}


var projectOperator = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_operator_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}
</script>

<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<shiro:hasPermission name="ProjectOperator:create">
			<button type="button" id="but_project_operator_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectOperator:update">
			<button type="button" id="but_project_operator_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectOperator:delete">
			<button type="button" id="but_project_operator_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="project_operator_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/operator/projectOperatorList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>登录名</label><input type="text" value="${query.loginName }" name="loginName" class="form-control" size="10">&nbsp;
			<label>执行人</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>省</label><input type="text" value="${query.province }" name="province" class="form-control" size="10">&nbsp;
			<label>市</label><input type="text" value="${query.city }" name="city" class="form-control" size="10">&nbsp;
			<label>县</label><input type="text" value="${query.town }" name="town" class="form-control" size="10">&nbsp;
			<label>学校</label><input type="text" value="${query.school }" name="school" class="form-control" size="10">&nbsp;
        	<label>办公电话</label><input type="text" value="${query.tel }" name="tel" class="form-control" size="10">&nbsp;
			<label>手机</label><input type="text" value="${query.mobile }" name="mobile" class="form-control" size="10">&nbsp;
			<label>Email</label><input type="text" value="${query.email }" name="email" class="form-control" size="10">&nbsp;
			<label>所在部门</label><input type="text" value="${query.dept }" name="dept" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="project_operator_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="province">省</th>
			<th align="center" data-order-field="city">市</th>
			<th align="center" data-order-field="town">区县</th>
			<th align="center" data-order-field="school">学校</th>
			<th align="center" data-order-field="loginName">登录名</th>
			<th align="center" data-order-field="name">执行人</th>
			<th align="center" data-order-field="tel">办公电话</th>
			<th align="center" data-order-field="mobile">手机</th>
			<th align="center" data-order-field="email">Email</th>
			<th align="center" data-order-field="dept">所在部门</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.province }</td>
			<td>${item.city }</td>
			<td>${item.town }</td>
			<td>${item.school }</td>
			<td>${item.loginName }</td>
			<td>${item.name }</td>
			<td>${item.tel }</td>
			<td>${item.mobile }</td>
			<td>${item.email }</td>
			<td>${item.dept }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>