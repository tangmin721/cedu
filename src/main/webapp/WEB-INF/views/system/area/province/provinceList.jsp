<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#province_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
    if(province.selectedId()){
    	province.onSelectChange();
    }
});

$('#province_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
	if(province.selectedId()){
    	province.onSelectChange();
    }
});

$('#but_province_add').on('click', function(e) {
	$(this).dialog({id:'province_form', url:'${ctx}/system/area/province/provinceForm', type:'POST',title:'添加省',mask:'true',width:'400',height:'260'});
});

$('#but_province_edit').on('click', function(e) {
	var list = province.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'province_form', url:'${ctx}/system/area/province/provinceForm/'+id, type:'POST',title:'编辑省',mask:'true',width:'400',height:'260'});
	}
});

$('#but_province_delete').on('click', function(e) {
	var list = province.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/area/province/deleteProvinceByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProvinceTable"});
	}
});

function delRefushProvinceTable(json){
	$('.province_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/area/province/provinceList'});
}


var province = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#province_table input[name="ids"]').each(function(){ 
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
				$("#div_sys_area_city").bjuiajax('refreshLayout', {
					target:$('#div_sys_area_city'),
					data: {provinceNo:id}
				});
			}else{
				$("#div_sys_area_city").bjuiajax('refreshLayout', {
					target:$('#div_sys_area_city'),
					data: {provinceNo:-1}
				});
			}
		}
}
</script>

<div class="bjui-pageHeader">
    <form id="pagerForm" class="province_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/area/province/provinceList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>P</label><input type="text" value="${query.provinceNo }" name="provinceNo" class="form-control" size="8" maxlength="6">&nbsp;
			<label>省</label><input type="text" value="${query.provinceName }" name="provinceName" class="form-control" size="8" maxlength="6">&nbsp;
            
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>

<div id="province_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<th align="center" data-order-field="seq">序号</th>
			<th align="center" data-order-field="provinceNo">provinceNo</th>
			<th align="center" data-order-field="provinceName">省</th>
			
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.provinceNo }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.provinceNo }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>${item.seq }</td>
			<td>${item.provinceNo }</td>
			<td>${item.provinceName }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page-area.jsp"%>