<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#project_roster_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_roster_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_project_roster_add').on('click', function(e) {
	$(this).dialog({id:'project_roster_form', url:'${ctx}/core/train/project/roster/projectRosterForm', type:'POST',title:'添加报名业务',mask:'true',width:'600',height:'520'});
});

$('#but_project_roster_edit').on('click', function(e) {
	var list = projectRoster.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'project_roster_form', url:'${ctx}/core/train/project/roster/projectRosterForm/'+id, type:'POST',title:'编辑报名业务',mask:'true',width:'600',height:'520'});
	}
});

$('#but_project_roster_delete').on('click', function(e) {
	var list = projectRoster.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/roster/deleteProjectRosterByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProjectRosterTable"});
	}
});

function delRefushProjectRosterTable(json){
	$('.project_roster_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/roster/projectRosterList'});
}


var projectRoster = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_roster_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="ProjectRoster:create">
			<button type="button" id="but_project_roster_add" class="btn btn-blue" data-icon="plus-circle" >添加申请单</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectRoster:update">
			<button type="button" id="but_project_roster_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectRoster:delete">
			<button type="button" id="but_project_roster_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="project_roster_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/roster/projectRosterList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>PID</label><input type="text" value="${query.pid }" name="pid" class="form-control" size="10">&nbsp;
			<label>申请单编号</label><input type="text" value="${query.rosterNo }" name="rosterNo" class="form-control" size="10">&nbsp;
			<label>上报时间</label><input type="text" value="${query.reportDate }" name="reportDate" class="form-control" size="10">&nbsp;
			<label>上报人</label><input type="text" value="${query.applyUserId }" name="applyUserId" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
			<label>审批人</label><input type="text" value="${query.checkUserId }" name="checkUserId" class="form-control" size="10">&nbsp;
			<label>审批时间</label><input type="text" value="${query.checkDate }" name="checkDate" class="form-control" size="10">&nbsp;
			<label>审批意见</label><input type="text" value="${query.checkDesc }" name="checkDesc" class="form-control" size="10">&nbsp;
			<label>状态</label><input type="text" value="${query.status }" name="status" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="project_roster_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="pid">PID</th>
			<th align="center" data-order-field="rosterNo">申请单编号</th>
			<th align="center" data-order-field="reportDate">上报时间</th>
			<th align="center" data-order-field="applyUserId">上报人</th>
			<th align="center" data-order-field="memo">备注</th>
			<th align="center" data-order-field="checkUserId">审批人</th>
			<th align="center" data-order-field="checkDate">审批时间</th>
			<th align="center" data-order-field="checkDesc">审批意见</th>
			<th align="center" data-order-field="status">状态</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.pid }</td>
			<td>${item.rosterNo }</td>
			<td>${item.reportDate }</td>
			<td>${item.applyUserId }</td>
			<td>${item.memo }</td>
			<td>${item.checkUserId }</td>
			<td>${item.checkDate }</td>
			<td>${item.checkDesc }</td>
			<td>${item.status }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>