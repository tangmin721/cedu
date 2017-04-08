<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#town_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#town_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_town_add').on('click', function(e) {
	$(this).dialog({id:'town_form', url:'${ctx}/system/area/town/townForm', type:'POST',title:'添加区/县',mask:'true',width:'400',height:'260'});
});

$('#but_town_edit').on('click', function(e) {
	var list = town.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'town_form', url:'${ctx}/system/area/town/townForm/'+id, type:'POST',title:'编辑区/县',mask:'true',width:'400',height:'260'});
	}
});

$('#but_town_delete').on('click', function(e) {
	var list = town.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/area/town/deleteTownByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTownTable"});
	}
});

function delRefushTownTable(json){
	$('.town_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/area/town/townList'});
}


var town = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#town_table input[name="ids"]').each(function(){ 
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
    <form id="pagerForm" class="town_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/area/town/townList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>C</label><input type="text" value="${query.cityNo }" name="cityNo" class="form-control" size="8" maxlength="6">&nbsp;
			<label>T</label><input type="text" value="${query.townNo }" name="townNo" class="form-control" size="8" maxlength="6">&nbsp;
			<label>县</label><input type="text" value="${query.townName }" name="townName" class="form-control" size="8" maxlength="6">&nbsp;
            
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>

<div id="town_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<th align="center" data-order-field="seq">序号</th>
			<th align="center" data-order-field="cityNo">cityNo</th>
			<th align="center" data-order-field="townNo">townNo</th>
			<th align="center" data-order-field="townName">县/区</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>${item.seq }</td>
			<td>${item.cityNo }</td>
			<td>${item.townNo }</td>
			<td>${item.townName }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page-area.jsp"%>