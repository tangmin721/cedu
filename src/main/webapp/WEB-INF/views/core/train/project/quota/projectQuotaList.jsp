<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#project_quota_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_quota_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_project_quota_add').on('click', function(e) {
	$(this).dialog({id:'project_quota_form', url:'${ctx}/core/train/project/quota/projectQuotaAddForm/${pid}', type:'POST',title:'添加名额分配',mask:'true',width:'820',height:'240'});
});

$('#but_project_quota_edit').on('click', function(e) {
	var list = projectQuota.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'project_quota_form', url:'${ctx}/core/train/project/quota/projectQuotaForm/'+id, type:'POST',title:'编辑名额分配',mask:'true',width:'820',height:'240'});
	}
});

$('#but_project_quota_delete').on('click', function(e) {
	var list = projectQuota.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/quota/deleteProjectQuotaByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProjectQuotaTable"});
	}
});

function delRefushProjectQuotaTable(json){
	$('.project_quota_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/quota/projectQuotaList/${pid}'});
}


var projectQuota = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_quota_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="ProjectQuota:create">
			<button type="button" id="but_project_quota_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectQuota:update">
			<button type="button" id="but_project_quota_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectQuota:delete">
			<button type="button" id="but_project_quota_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="project_quota_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/quota/projectQuotaList/${pid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>学校</label><input type="text" value="${query.school }" name="school" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" id="project_quota_search" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>pid</label><input type="text" value="${query.pid }" name="pid" class="form-control" size="10">&nbsp;
			<label>省</label><input type="text" value="${query.province }" name="province" class="form-control" size="10">&nbsp;
			<label>市</label><input type="text" value="${query.city }" name="city" class="form-control" size="10">&nbsp;
			<label>区县</label><input type="text" value="${query.town }" name="town" class="form-control" size="10">&nbsp;
			<label>名额人数</label><input type="text" value="${query.num }" name="num" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
			<label>排序</label><input type="text" value="${query.seq }" name="seq" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="project_quota_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="pid">pid</th>
			<th align="center" data-order-field="province">省</th>
			<th align="center" data-order-field="city">市</th>
			<th align="center" data-order-field="town">区县</th>
			<th align="center" data-order-field="school">学校</th>
			<th align="center" data-order-field="school">学校类别</th>
			<th align="center" data-order-field="num">名额人数</th>
			<th align="center" data-order-field="memo">备注</th>
			<th align="center" data-order-field="seq">序号</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.pid }</td>
			<td>${item.provinceName }</td>
			<td>${item.cityName }</td>
			<td>${item.townName }</td>
			<td>${item.schoolName }</td>
			<td>${item.shoolCategoryName }</td>
			<td>${item.num }</td>
			<td>${item.memo }</td>
			<td>${item.seq }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>