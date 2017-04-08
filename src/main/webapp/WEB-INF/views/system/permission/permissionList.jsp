<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#menu_permission_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#menu_permission_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_menu_permission_add').on('click', function(e) {
	$(this).dialog({id:'menu_permission_form_dialog',fresh:true,url:'${ctx}/system/permission/permissionForm?menuId=${menuId}', type:'POST',title:'添加权限',mask:'true',width:'400',height:'196'});
});

$('#but_menu_permission_edit').on('click', function(e) {
	var list = permission.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'menu_permission_form_dialog',fresh:true,url:'${ctx}/system/permission/permissionForm/'+id+'?menuId=${menuId}', type:'POST',title:'编辑权限',mask:'true',width:'400',height:'196'});
	}
});

$('#but_menu_permission_delete').on('click', function(e) {
	var list = permission.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/permission/deletePermissionByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushPermissionTable"});
	}
});

function delRefushPermissionTable(json){
	$('.menu_permission_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/permission/permissionList'});
}

function refushPermissionTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$(this).dialog('closeCurrent');
		$('#menu_clear').trigger("click");//刷新查询
	}
}

var permission = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#menu_permission_table input[name="ids"]').each(function(){ 
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
		<button type="button" id="but_menu_permission_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		<button type="button" id="but_menu_permission_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		<button type="button" id="but_menu_permission_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
	</div>
    <form id="pagerForm" class="menu_permission_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/permission/permissionList/${menuId }" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<input type="hidden" value="${query.menuId }" name="menuId" class="form-control" size="10">
			<label>名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="5">&nbsp;
			<label>编码</label><input type="text" value="${query.code }" name="code" class="form-control" size="5">&nbsp;
            <button type="submit" id="menu_clear" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>

<div id="menu_permission_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<th align="center" data-order-field="clzName">类名</th>
			<th align="center" data-order-field="name">名称</th>
			<th align="center" data-order-field="code">编码</th>
			<th align="center" data-order-field="faicon">图标</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>${item.clzName }</td>
			<td>${item.name }</td>
			<td>${item.code }</td>
			<td><i class="fa fa-${item.faicon}"></i>&nbsp;&nbsp;${item.faicon }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>