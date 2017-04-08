<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#dict_item_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#dict_item_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_dict_item_add').on('click', function(e) {
	var list = dictCatlog.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择左侧字典目录的行！');
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行字典目录进行！');
	}else{
		var catlogId = list[0];
		$(this).dialog({id:'dict_item_form', url:'${ctx}/system/dict/item/dictItemForm', data:{catlogId:catlogId},type:'POST',title:'添加字典项',mask:'true',width:'400',height:'260'});
	}
});

$('#but_dict_item_edit').on('click', function(e) {
	var list = dictItem.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'dict_item_form', url:'${ctx}/system/dict/item/dictItemForm/'+id, type:'POST',title:'编辑字典项',mask:'true',width:'400',height:'260'});
	}
});

$('#but_dict_item_delete').on('click', function(e) {
	var list = dictItem.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/dict/item/deleteDictItemByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushDictItemTable"});
	}
});

function delRefushDictItemTable(json){
	$('.dict_item_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/dict/item/dictItemList'});
}

var dictItem = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#dict_item_table input[name="ids"]').each(function(){ 
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
		<button type="button" id="but_dict_item_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		<button type="button" id="but_dict_item_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		<button type="button" id="but_dict_item_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
	</div>
    <form id="pagerForm" class="dict_item_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/dict/item/dictItemList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<input type="hidden" value="${param.catlogId }" name="catlogId" class="form-control" size="5">
			<label>名称</label><input type="text" value="${param.name }" name="name" class="form-control" size="5">&nbsp;
			<label>编码</label><input type="text" value="${param.code }" name="code" class="form-control" size="5">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>序号</label><input type="text" value="${query.seq }" name="seq" class="form-control" size="5">&nbsp;
            <label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="5">&nbsp;
        </div>
    </form>
</div>

<div id="dict_item_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="seq">序号</th>
			<th align="center" data-order-field="name">名称</th>
			<th align="center" data-order-field="code">编码</th>
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
			<td>${item.memo }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>