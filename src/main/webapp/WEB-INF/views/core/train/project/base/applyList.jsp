<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#project_roster_apply_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_roster_apply_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_project_roster_apply_add').on('click', function(e) {
	$(this).dialog({id:'project_roster_apply_form', url:'${ctx}/core/train/project/roster/applyForm/${pid}', type:'POST',title:'添加报名申请单',mask:'true',width:'600',height:'520'});
});

$('#but_project_roster_apply_edit').on('click', function(e) {
	var list = projectRoster.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'project_roster_apply_form', url:'${ctx}/core/train/project/roster/applyFormEdit/'+id, type:'POST',title:'编辑报名业务',mask:'true',width:'600',height:'520'});
	}
});

$('#but_project_roster_apply_delete').on('click', function(e) {
	var list = projectRoster.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/roster/deleteProjectRosterByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProjectRosterTable"});
	}
});

function delRefushProjectRosterTable(json){
	$('.project_roster_apply_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/roster/projectRosterList'});
}

function editApply(id){
	$(this).dialog({id:'project_roster_apply_form', url:'${ctx}/core/train/project/roster/applyFormEdit/'+id, type:'POST',title:'编辑报名申请',mask:'true',width:'600',height:'520'});
}

//查看teacher详情
function viweApply(id){
	  $(this).dialog({id:'teacher-view-dialog', url:'${ctx}/core/train/project/roster/applyViewReport/'+id, title:'申请单详细信息',mask:'true',width:'850',height:'600'});
}

function optApply(id,status){
	$.ajax({ 
        type: "POST", 
        url: "${ctx}/core/train/project/roster/updateStatus?id="+id+"&status="+status,
        dataType : "json",
        success: function(data) {
        	$(this).alertmsg('ok', '操作成功');
        	$('#apply_search').trigger('click');
        },
        error :  function(){
        	$(this).alertmsg('error', '系统错误！');
        }
    }); 
}

//上报
function reportTheApply(id){
	$.ajax({ 
        type: "POST", 
        url: "${ctx}/core/train/project/roster/reportRoster/"+id,
        dataType : "json",
        success: function(data) {
        	$(this).alertmsg('ok', '操作成功');
        	$('#apply_search').trigger('click');
        },
        error :  function(){
        	$(this).alertmsg('error', '系统错误！');
        }
    }); 
}

function deleteApply(id){
	$(this).alertmsg('confirm', '确认是否要删除该项目，如果是，请点确认！',{
		okCall:function(){
			$.ajax({ 
		        type: "POST", 
		        url: "${ctx}/core/train/project/roster/deleteProjectRosterByIds?ids="+id,
		        dataType : "json",
		        success: function(data) {
		        	$('#apply_search').trigger('click');
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
		}
	});
}

var projectRoster = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_roster_apply_table input[name="ids"]').each(function(){ 
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
			<button type="button" id="but_project_roster_apply_add" class="btn btn-blue" data-icon="plus-circle" >添加申请单</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="project_roster_apply_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/roster/applyList/${pid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<input type="hidden" value="${query.pid }" name="pid" class="form-control" size="10">&nbsp;
			<label>申请单编号</label><input type="text" value="${query.rosterNo }" name="rosterNo" class="form-control" size="10">&nbsp;
			<label>上报时间</label><input type="text" value="${query.reportDate }" name="reportDate" class="form-control" size="10">&nbsp;
			<label>上报人</label><input type="text" value="${query.applyUserId }" name="applyUserId" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" id="apply_search" data-icon="search">查询</button>&nbsp;
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

<div id="project_roster_apply_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<th align="center" data-order-field="rosterNo">申请单编号</th>
			<th align="center" data-order-field="reportDate">上报时间</th>
			<th align="center" data-order-field="applyUserId">申请人</th>
			<th align="center" data-order-field="memo">申请理由</th>
			<th align="center" data-order-field="checkUserId">审批人</th>
			<th align="center" data-order-field="checkDate">审批时间</th>
			<th align="center" data-order-field="checkDesc">审批意见</th>
			<th align="center" data-order-field="status">状态</th>
			<th width="150" align="center" >操作</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>${item.rosterNo }</td>
			<td><fmt:formatDate value="${item.reportDate }" pattern="yyyy-MM-dd"/></td>
			<td>${item.applyUserName }</td>
			<td>${item.memo }</td>
			<td>${item.checkUserName }</td>
			<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
			<td>${item.checkDesc }</td>
			<td>
				<c:if test="${item.status ==0}"><span class="label label-default">未上报</span></c:if>
				<c:if test="${item.status ==1}"><span class="label label-info">已上报</span></c:if>
				<c:if test="${item.status ==2}"><span class="label label-success">审核通过</span></c:if>
				<c:if test="${item.status ==3}"><span class="label label-danger">审核不通过</span></c:if>
			</td>
			<td>
				<c:if test="${item.status ==0 || item.status==3}">
					<button type="button" class="btn btn-danger" data-icon="trash-o" onclick="deleteApply(${item.id})"  data-toggle="tooltip"  data-placement="top" title="删除"></button>
					<button type="button" class="btn btn-green" data-icon="edit" onclick="editApply(${item.id})" data-toggle="tooltip"  data-placement="top" title="编辑"></button>
					<button type="button" class="btn btn-dark-blue" data-icon="user-secret" onclick="viweApply(${item.id})" data-toggle="tooltip"  data-placement="top" title="查看详情"></button>
					<button type="button" class="btn btn-primary" data-icon="share-square-o" onclick="reportTheApply(${item.id})" data-toggle="tooltip"  data-placement="top" title="上报"></button>
				</c:if>
				<c:if test="${item.status ==1}">
					<button type="button" class="btn btn-dark-blue" data-icon="user-secret" onclick="viweApply(${item.id})" data-toggle="tooltip"  data-placement="top" title="查看详情"></button>
				</c:if>
				<c:if test="${item.status ==2}">
					<button type="button" class="btn btn-dark-blue" data-icon="user-secret" onclick="viweApply(${item.id})" data-toggle="tooltip"  data-placement="top" title="查看详情"></button>
				</c:if>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>