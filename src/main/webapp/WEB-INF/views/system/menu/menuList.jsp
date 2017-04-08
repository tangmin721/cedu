<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#menu_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#menu_table').on('click','tr:not(.trth)',function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_menu_add').on('click', function(e) {
	if(objMenu.selectedId!=undefined){
		$(this).dialog({id:'menu_form', url:'${ctx}/system/menu/menuForm?pid='+objMenu.selectedId, type:'POST',title:'添加菜单',mask:'true',width:'600',height:'420'});
	}else{
		$(this).alertmsg('confirm', '您未选择左侧节点，是否想要添加根节点，如果是，请点确认！',{
			okCall:function(){
				$(this).dialog({id:'menu_form', url:'${ctx}/system/menu/menuForm?pid=1', type:'POST',title:'添加菜单',mask:'true',width:'600',height:'420'});
			}
		});
		
	}
});

$('#but_menu_edit').on('click', function(e) {
	var list = menu.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'menu_form', url:'${ctx}/system/menu/menuForm/'+id, type:'POST',title:'编辑菜单',mask:'true',width:'600',height:'420'});
	}
});

$('#but_menu_delete').on('click', function(e) {
	var list = menu.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/menu/deleteMenuByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushMenuTable"});
	}
});

$('#but_menu_perm_list').on('click', function(e) {
	var list = menu.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要分配权限资源的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行分配权限资源！');
	}else{
		var id = list[0];
		$(this).dialog({id:'menu_perm_list', url:'${ctx}/system/permission/permissionList/'+id, type:'POST',title:'权限资源列表',mask:'true',width:'600',height:'420'});
	}
});

function delRefushMenuTable(json){
	$('.menu_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/menu/menuList'});
	$("#div_sys_menu_tree").bjuiajax('refreshLayout', {
		target:$('#div_sys_menu_tree')
	});
}


var menu = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#menu_table input[name="ids"]').each(function(){ 
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
		<button type="button" id="but_menu_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		<button type="button" id="but_menu_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		<button type="button" id="but_menu_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
	</div>
	<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
	</div>
	<div class="btn-group pull-left" role="group">
		<button type="button" id="but_menu_perm_list" class="btn btn-orange" data-icon="eye" >权限资源</button>
	</div>
    <form id="pagerForm" class="menu_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/menu/menuList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<input type="hidden" value="${query.pid }" name="pid">&nbsp;
			<label>名称&nbsp;</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>NAVID&nbsp;</label><input type="text" value="${query.tabid }" name="tabid" class="form-control" size="10">&nbsp;
             <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
         <div class="bjui-moreSearch pull-right">
			<label>类名&nbsp;</label><input type="text" value="${query.modelClzName }" name="modelClzName" class="form-control" size="10">&nbsp;
			<label>图标&nbsp;</label><input type="text" value="${query.faicon }" name="faicon" class="form-control" size="10">&nbsp;
			<label>URL&nbsp;</label><input type="text" value="${query.url }" name="url" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="menu_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
    		<th align="center" data-order-field="seq" title="序号">排序</th>
			<th align="center" data-order-field="name" title="模块名称">模块名称</th>
			<th align="center" data-order-field="modelClzName" title="对应模块的类名">对应模块的类名</th>
			<th align="center" data-order-field="faicon" title="图标">图标</th>
			<th align="center" data-order-field="theLast" title="最下级">最下级</th>
			<th align="center" data-order-field="shower" title="显示菜单">显示菜单</th>
			<th align="center" data-order-field="active" title="激活状态">激活状态</th>
			<th align="center" data-order-field="tabid" title="NAVID">NAVID</th>
			<th align="center" data-order-field="url" title="URL">URL</th>
			<th align="center" data-order-field="memo" title="备注">备注</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>${item.seq }</td>
			<td>${item.name }</td>
			<td>${item.modelClzName }</td>
			<td><i class="fa fa-${item.faicon}"></i>&nbsp;&nbsp;${item.faicon }</td>
			<td>
				<c:if test="${item.theLast }"><span class="label label-info">是</span></c:if>
				<c:if test="${not item.theLast }"><span class="label label-default">否</span></c:if>
			</td>
			<td>
				<c:if test="${item.shower }"><span class="label label-success">显示</span></c:if>
				<c:if test="${not item.shower }"><span class="label label-default">不显示</span></c:if>
			</td>
			<td>
				<c:if test="${item.active }"><span class="label label-success">已激活</span></c:if>
				<c:if test="${not item.active }"><span class="label label-default">未激活</span></c:if>
			</td>
			<td>${item.tabid }</td>
			<td>${item.url }</td>
			
			<td>${item.memo }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>