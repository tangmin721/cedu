<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#register_msg_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#register_msg_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_register_msg_add').on('click', function(e) {
	$(this).dialog({id:'register_msg_form', url:'${ctx}/core/mq/registerMsgForm', type:'POST',title:'添加消息',mask:'true',width:'600',height:'520'});
});

$('#but_register_msg_edit').on('click', function(e) {
	var list = registerMsg.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'register_msg_form', url:'${ctx}/core/mq/registerMsgForm/'+id, type:'POST',title:'编辑消息',mask:'true',width:'600',height:'520'});
	}
});

$('#but_register_msg_delete').on('click', function(e) {
	var list = registerMsg.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/mq/deleteRegisterMsgByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushRegisterMsgTable"});
	}
});

function delRefushRegisterMsgTable(json){
	$('.register_msg_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/mq/registerMsgList'});
}


var registerMsg = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#register_msg_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}

$('#but_register_msg_produce').on('click',function(){
	
	$(this).alertmsg('confirm', '确认执行注册？',{
		okCall:function(){
			$.ajax({ 
		        type: "GET", 
		        url: "${ctx}/core/mq/producer",
		        dataType : "json",
		        success: function(data) {
		        	$(this).alertmsg('ok', data.message);
		        	$('#msg-search').trigger('click');
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
		}
	});
})
</script>

<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<shiro:hasPermission name="RegisterMsg:create">
			<button type="button" id="but_register_msg_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="RegisterMsg:update">
			<button type="button" id="but_register_msg_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="RegisterMsg:delete">
			<button type="button" id="but_register_msg_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
	<shiro:hasPermission name="RegisterMsg:create">
		<div class="btn-group pull-left" role="group">
			<button type="button" id="but_register_msg_produce" class="btn btn-success" data-icon="refresh" >执行注册</button>
		</div>
	</shiro:hasPermission>
    <form id="pagerForm" class="register_msg_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/mq/registerMsgList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>消息状态</label>
			<select name="status" data-toggle="selectpicker" data-width="150px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${msgStatuses }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.status}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;
			
			<label>消息类型</label>
			<select name="type" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${msgTypes }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.type}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;
			
			<label>发展网UID</label><input type="text" value="${query.uid }" name="uid" class="form-control" size="10">&nbsp;
			<label>passport</label><input type="text" value="${query.passport }" name="passport" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" id="msg-search" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        </div>
    </form>
</div>

<div id="register_msg_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="status" title="消息状态">消息状态</th>
			<th align="center" data-order-field="type" title="消息类型">消息类型</th>
			<th align="center" data-order-field="num" title="发送到队列次数">发送到队列次数</th>
			<th align="center" data-order-field="memo" title="描述">描述</th>
			<th align="center" data-order-field="createTime" title="创建时间">创建时间</th>
			<th align="center" data-order-field="producerTime" title="消息创建时间">消息创建时间</th>
			<th align="center" data-order-field="consumerTime" title="消息消费时间">消息消费时间</th>
			<th align="center" data-order-field="uid" title="发展网UID">发展网UID</th>
			<th align="center" data-order-field="passport" title="passport">passport</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>
				<c:if test="${item.status ==0}"><span class="label label-default">未发送到队列</span></c:if>
    			<c:if test="${item.status ==1}"><span class="label label-primary">已发送到队列待消费</span></c:if>
    			<c:if test="${item.status ==2}"><span class="label label-danger">系统错误</span></c:if>
    			<c:if test="${item.status ==3}"><span class="label label-warning">业务错误</span></c:if>
    			<c:if test="${item.status ==8}"><span class="label label-success">处理完成</span></c:if>
			</td>
			<td>
				<c:if test="${item.type ==0}"><span class="label label-default">注册用户</span></c:if>
    			<c:if test="${item.type ==1}"><span class="label label-primary">同步用户</span></c:if>
			</td>
			<td>${item.num }</td>
			<td>${item.memo }</td>
			<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${item.producerTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${item.consumerTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${item.uid }</td>
			<td>${item.passport }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>