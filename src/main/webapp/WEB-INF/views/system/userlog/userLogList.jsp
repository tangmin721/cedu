<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#user_log_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#user_log_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_user_log_add').on('click', function(e) {
	$(this).dialog({id:'user_log_form', url:'${ctx}/system/userlog/userLogForm', type:'POST',title:'添加用户操作日志',mask:'true',width:'600',height:'520'});
});

$('#but_user_log_edit').on('click', function(e) {
	var list = userLog.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'user_log_form', url:'${ctx}/system/userlog/userLogForm/'+id, type:'POST',title:'编辑用户操作日志',mask:'true',width:'600',height:'520'});
	}
});

$('#but_user_log_delete').on('click', function(e) {
	var list = userLog.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/system/userlog/deleteUserLogByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushUserLogTable"});
	}
});

function delRefushUserLogTable(json){
	$('.user_log_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/userlog/userLogList'});
}


var userLog = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#user_log_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="UserLog:create">
			<button type="button" id="but_user_log_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="UserLog:update">
			<button type="button" id="but_user_log_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="UserLog:delete">
			<button type="button" id="but_user_log_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="user_log_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/userlog/userLogList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>登录名</label><input type="text" value="${query.loginName }" name="loginName" class="form-control" size="10">&nbsp;
			<label>操作状态</label><input type="text" value="${query.operateStatus }" name="operateStatus" class="form-control" size="10">&nbsp;
			<label>访问IP</label><input type="text" value="${query.ip }" name="ip" class="form-control" size="10">&nbsp;
			<label>操作描述</label><input type="text" value="${query.description }" name="description" class="form-control" size="10">&nbsp;
			<label>具体类方法</label><input type="text" value="${query.content }" name="content" class="form-control" size="10">&nbsp;
			<label>操作类型</label><input type="text" value="${query.operType }" name="operType" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        </div>
    </form>
</div>

<div id="user_log_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="loginName" title="登录名">登录名</th>
			<th align="center" data-order-field="ip" title="访问IP">访问IP</th>
			<th align="center" data-order-field="description" title="操作描述">操作描述</th>
			<th align="center" data-order-field="content" title="具体类方法">具体类方法</th>
			<th align="center" data-order-field="operType" title="操作类型">操作类型</th>
			<th align="center" data-order-field="operateStatus" title="操作状态">操作状态</th>
			<th align="center" data-order-field="createTime" title="操作时间">操作时间</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.loginName }</td>
			<td>${item.ip }</td>
			<td>${item.description }</td>
			<td>${item.content }</td>
			<td>
				<c:if test="${item.operType ==0}">系统操作</span></c:if>
				<c:if test="${item.operType ==1}">系统异常</span></c:if>
			</td>
			<td>
				<c:if test="${item.operateStatus ==1}"><span class="label label-success">操作成功</span></c:if>
				<c:if test="${item.operateStatus ==0}"><span class="label label-danger">操作失败</span></c:if>
			</td>
			<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>