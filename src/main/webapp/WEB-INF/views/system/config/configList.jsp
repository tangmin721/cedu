<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#config_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#config_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_config_add').on('click', function(e) {
	$(this).dialog({id:'config_form', url:'${ctx}/system/config/configForm', type:'POST',title:'添加系统配置',mask:'true',width:'480',height:'220'});
});

$('#but_config_edit').on('click', function(e) {
	var list = config.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'config_form', url:'${ctx}/system/config/configForm/'+id, type:'POST',title:'编辑系统配置',mask:'true',width:'480',height:'220'});
	}
});

$('#but_config_delete').on('click', function(e) {
	var list = config.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/config/deleteConfigByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushConfigTable"});
	}
});

function delRefushConfigTable(json){
	$('.config_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/config/configList'});
}


var config = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#config_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="Config:create">
			<button type="button" id="but_config_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="Config:update">
			<button type="button" id="but_config_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="Config:delete">
			<button type="button" id="but_config_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="config_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/config/configList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>用户id</label><input type="text" value="${query.userId }" name="userId" class="form-control" size="10">&nbsp;
			<label>配置名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>Key值</label><input type="text" value="${query.theKey }" name="theKey" class="form-control" size="10">&nbsp;
			<label>Value</label><input type="text" value="${query.theValue }" name="theValue" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        </div>
    </form>
</div>

<div id="config_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="userId">用户Id</th>
			<th align="center" data-order-field="name">配置名称</th>
			<th align="center" data-order-field="theKey">Key值</th>
			<th align="center" data-order-field="theValue">Value</th>
			<th align="center" data-order-field="memo">备注</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.userId }</td>
			<td>${item.name }</td>
			<td>${item.theKey }</td>
			<td>${item.theValue }</td>
			<td>${item.memo }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>