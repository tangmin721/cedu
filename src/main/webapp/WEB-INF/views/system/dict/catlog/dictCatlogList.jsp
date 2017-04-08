<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#dict_catlog_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
    if(dictCatlog.selectedId()){
    	dictCatlog.onSelectChange();
    }
});

$('#dict_catlog_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
	if(dictCatlog.selectedId()){
	    dictCatlog.onSelectChange();
    }
});

$('#but_dict_catlog_edit').on('click', function(e) {
	var list = dictCatlog.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'dict_catlog_form', url:'${ctx}/system/dict/catlog/dictCatlogForm/'+id, type:'POST',title:'编辑字典目录管理',mask:'true',width:'400',height:'260'});
	}
});

$('#but_dict_catlog_delete').on('click', function(e) {
	var list = dictCatlog.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/dict/catlog/deleteDictCatlogByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushDictCatlogTable"});
	}
});

function delRefushDictCatlogTable(json){
	$('.dict_catlog_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/dict/catlog/dictCatlogList'});
}

$('#dict-remove-cache').on('click', function(e) {
	$(this).bjuiajax('doAjax', {url:'${ctx}/system/dict/removeDictCache'});
});

var dictCatlog = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#dict_catlog_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		},
		onSelectChange : function(){
			var id = this.selectedId();
			if(id){
				$("#div_sys_dict_item").bjuiajax('refreshLayout', {
					target:$('#div_sys_dict_item'),
					data: {catlogId:id}
				});
			}else{
				$("#div_sys_dict_item").bjuiajax('refreshLayout', {
					target:$('#div_sys_dict_item'),
					data: {catlogId:-1}
				});
			}
		}
}

</script>

<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<button type="button" class="btn btn-blue" data-icon="plus-circle" data-toggle="dialog"
			data-options="{id:'dict_catlog_form', url:'${ctx}/system/dict/catlog/dictCatlogForm',type:'POST',title:'添加字典目录管理',mask:'true',width:'400',height:'260'}"
			>添加</button>
		<button type="button" id="but_dict_catlog_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		<button type="button" id="but_dict_catlog_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
	</div>
    <form id="pagerForm" class="dict_catlog_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/dict/catlog/dictCatlogList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>名称 </label><input type="text" value="${query.name }" name="name" class="form-control" size="5">&nbsp;
			<label>编码</label><input type="text" value="${query.code }" name="code" class="form-control" size="5">&nbsp;
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

<div id="dict_catlog_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th width="26" align="center" data-order-field="seq">序号</th>
			<th align="center" data-order-field="name">名称</th>
			<th align="center" data-order-field="code">编码</th>
			<th align="center" data-order-field="isTree">是否是树</th>
			<th align="center" data-order-field="canParent">可选中父节点</th>
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
			<td><c:if test="${item.isTree }"><span class="label label-success">是</span></c:if><c:if test="${not item.isTree }">否</c:if></td>
			<td><c:if test="${item.canParent }"><span class="label label-success">可选中</span></c:if><c:if test="${not item.canParent }">不可选中</c:if></td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>