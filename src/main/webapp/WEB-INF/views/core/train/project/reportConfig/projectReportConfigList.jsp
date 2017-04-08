<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#project_report_config_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_report_config_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_project_report_config_add').on('click', function(e) {
	$(this).dialog({id:'project_report_config_form', url:'${ctx}/core/train/project/reportConfig/projectReportConfigForm', type:'POST',title:'添加报名配置',mask:'true',width:'600',height:'520'});
});

$('#but_project_report_config_edit').on('click', function(e) {
	var list = projectReportConfig.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'project_report_config_form', url:'${ctx}/core/train/project/reportConfig/projectReportConfigForm/'+id, type:'POST',title:'编辑报名配置',mask:'true',width:'600',height:'520'});
	}
});

$('#but_project_report_config_delete').on('click', function(e) {
	var list = projectReportConfig.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/reportConfig/deleteProjectReportConfigByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProjectReportConfigTable"});
	}
});

function delRefushProjectReportConfigTable(json){
	$('.project_report_config_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/reportConfig/projectReportConfigList'});
}


var projectReportConfig = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_report_config_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="ProjectReportConfig:create">
			<button type="button" id="but_project_report_config_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectReportConfig:update">
			<button type="button" id="but_project_report_config_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectReportConfig:delete">
			<button type="button" id="but_project_report_config_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="project_report_config_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/reportConfig/projectReportConfigList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>pid</label><input type="text" value="${query.pid }" name="pid" class="form-control" size="10">&nbsp;
			<label>培训人数</label><input type="text" value="${query.trainCount }" name="trainCount" class="form-control" size="10">&nbsp;
			<label>报名方式</label><input type="text" value="${query.registerType }" name="registerType" class="form-control" size="10">&nbsp;
			<label>开始时间</label><input type="text" value="${query.startDate }" name="startDate" class="form-control" size="10">&nbsp;
			<label>结束时间</label><input type="text" value="${query.endDate }" name="endDate" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        </div>
    </form>
</div>

<div id="project_report_config_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="pid">pid</th>
			<th align="center" data-order-field="trainCount">培训人数</th>
			<th align="center" data-order-field="registerType">报名方式</th>
			<th align="center" data-order-field="startDate">开始时间</th>
			<th align="center" data-order-field="endDate">报名截止时间</th>
			<th align="center" data-order-field="memo">备注</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.pid }</td>
			<td>${item.trainCount }</td>
			<td>${item.registerType }</td>
			<td>${item.startDate }</td>
			<td>${item.endDate }</td>
			<td>${item.memo }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>