<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#${entity.underLineName}_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#${entity.underLineName}_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_${entity.underLineName}_add').on('click', function(e) {
	$(this).dialog({id:'${entity.underLineName}_form', url:'${r"${"}${"ctx"}${r"}"}/${CONFIG.requestMapPath}/${entity.firstLowName}Form', type:'POST',title:'添加${CONFIG.modelName}',mask:'true',width:'600',height:'520'});
});

$('#but_${entity.underLineName}_edit').on('click', function(e) {
	var list = ${entity.firstLowName}.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'${entity.underLineName}_form', url:'${r"${"}${"ctx"}${r"}"}/${CONFIG.requestMapPath}/${entity.firstLowName}Form/'+id, type:'POST',title:'编辑${CONFIG.modelName}',mask:'true',width:'600',height:'520'});
	}
});

$('#but_${entity.underLineName}_delete').on('click', function(e) {
	var list = ${entity.firstLowName}.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${r"${"}${"ctx"}${r"}"}/${CONFIG.requestMapPath}/delete${entity.className}ByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefush${entity.className}Table"});
	}
});

function delRefush${entity.className}Table(json){
	$('.${entity.underLineName}_pager_form').bjuiajax('doSearch', {url:'${r"${"}${"ctx"}${r"}"}/${CONFIG.requestMapPath}/${entity.firstLowName}List'});
}


var ${entity.firstLowName} = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#${entity.underLineName}_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="${entity.className}:create">
			<button type="button" id="but_${entity.underLineName}_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="${entity.className}:update">
			<button type="button" id="but_${entity.underLineName}_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="${entity.className}:delete">
			<button type="button" id="but_${entity.underLineName}_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="${entity.underLineName}_pager_form" data-toggle="ajaxsearch" action="${r"${"}${"ctx"}${r"}"}/${CONFIG.requestMapPath}/${entity.firstLowName}List" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${r"${"}${"page.pageSize"}${r"}"}">
        <input type="hidden" name="pageCurrent" value="${r"${"}${"page.pageCurrent"}${r"}"}">
        <input type="hidden" name="orderField" value="${r"${"}${"param.orderField"}${r"}"}">
        <input type="hidden" name="orderDirection" value="${r"${"}${"param.orderDirection"}${r"}"}">
        <div class="bjui-searchBar pull-right">
<#list entity.myfieldList as e>			<label>字段${e_index}</label><input type="text" value="${r"${"}${"query.${e.fieldName} "}${r"}"}" name="${e.fieldName}" class="form-control" size="10">&nbsp;
        	</#list>
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        </div>
    </form>
</div>

<div id="${entity.underLineName}_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
<#list entity.myfieldList as e>			<th align="center" data-order-field="${e.fieldName}" title="字段${e_index}">字段${e_index}</th>
        	</#list>   
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="${r"#{"}${"page.list"}${r"}"}" varStatus="index">
    	<tr data-id="${r"${"}${"item.id "}${r"}"}">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${r"${"}${"item.id "}${r"}"}"></td>
    		<td>${r"${"}${"index.count+page.pageStart"}${r"}"}</td>
<#list entity.myfieldList as e>			<td>${r"${"}${"item.${e.fieldName} "}${r"}"}</td>
        	</#list>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>