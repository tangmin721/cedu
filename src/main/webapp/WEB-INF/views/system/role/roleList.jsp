<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#role_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#role_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#role_table').contextmenu('show', 
     {	 shadow: false,
         items:[
            {
                icon  : 'plus-circle',
                title : '添加',
                func  : function(parent, menu) {
                	alert(parent.context.id);
                    console.log(parent)
                    console.log('当前右键的jquery对象！')
                }
            },
            {
                icon  : 'edit',
                title : '编辑',
                func  : function(parent, menu) {
                    console.log(menu)
                    console.log('编辑右键li对象！')
                }
            },
            {
                icon  : 'times-circle',
                title : '删除',
                func  : function(parent, menu) {
                    console.log(menu)
                    console.log('删除右键li对象！')
                }
            },
            {
                title : 'diver'
            },
            {
            	icon  : 'sitemap',
                title : '分配权限',
                func  : function(parent, menu) {
                    console.log('分配权限！')
                }
            }
        ]
     }
 );

$('#but_role_add').on('click', function(e) {
	$(this).dialog({id:'role_form', url:'${ctx}/system/role/roleForm', type:'POST',title:'添加角色',mask:'true',width:'400',height:'260'});
});

$('#but_role_edit').on('click', function(e) {
	var list = role.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'role_form', url:'${ctx}/system/role/roleForm/'+id, type:'POST',title:'编辑角色',mask:'true',width:'400',height:'260'});
	}
});

$('#but_role_delete').on('click', function(e) {
	var list = role.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/role/deleteRoleByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushRoleTable"});
	}
});

$('#but_role_perm').on('click', function(e) {
	var list = role.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要分配的角色！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一个角色进行分配！');
	}else{
		var id = list[0];
		$(this).dialog({id:'role_perm_form', url:'${ctx}/system/role/rolePermTree/'+id, type:'POST',title:'角色权限分配',mask:'true',width:'520',height:'620'});
	}
});

function delRefushRoleTable(json){
	$('.role_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/role/roleList'});
}


var role = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#role_table input[name="ids"]').each(function(){ 
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
		<button type="button" id="but_role_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		<button type="button" id="but_role_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		<button type="button" id="but_role_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
	</div>
	<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
	</div>
	<div class="btn-group pull-left" role="group">
		<button type="button" id="but_role_perm" class="btn btn-orange" data-icon="eye" >分配权限</button>
	</div>
    <form id="pagerForm" class="role_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/role/roleList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>角色名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>角色编码</label><input type="text" value="${query.code }" name="code" class="form-control" size="10">&nbsp;
            
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>

<div id="role_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="seq">序号</th>
			<th align="center" data-order-field="name">角色名称</th>
			<th align="center" data-order-field="code">角色编码</th>
			<th align="center" data-order-field="active">激活状态</th>
			<th align="center" data-order-field="memo">备注</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.seq }</td>
			<td>${item.name }</td>
			<td>${item.code }</td>
			<td>
				<c:if test="${item.active }"><span class="label label-success">激活</span></c:if>
				<c:if test="${not item.active }"><span class="label label-warning">未激活</span></c:if>
			<td>${item.memo }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>