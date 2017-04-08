<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#expert_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#expert_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_expert_add').on('click', function(e) {
	$(this).dialog({id:'expert_form', url:'${ctx}/core/train/expert/expertForm', type:'POST',title:'添加专家信息',mask:'true',width:'600',height:'520'});
});

$('#but_expert_edit').on('click', function(e) {
	var list = expert.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'expert_form', url:'${ctx}/core/train/expert/expertForm/'+id, type:'POST',title:'编辑专家信息',mask:'true',width:'600',height:'520'});
	}
});

$('#but_expert_delete').on('click', function(e) {
	var list = expert.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/expert/deleteExpertByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushExpertTable"});
	}
});

function delRefushExpertTable(json){
	$('.expert_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/expert/expertList'});
}


var expert = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#expert_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}

$('#but_expert_import').on('click',function(e){
	$(this).dialog({id:'expert_import_form', url:'${ctx}/core/train/expert/expertImport', type:'POST',title:'导入专家信息',mask:'true',width:'660',height:'560'});
});

function expertView(id){
	  $(this).dialog({id:'expert-view-dialog', url:'${ctx}/core/train/expert/expertViewForm/'+id, title:'专家详细信息',mask:'true',width:'650',height:'240'});
}

$('#expert_export_all').on('click',function(e){
	$(this).alertmsg('confirm', '确定要导出全部专家信息吗？',{
		okCall:function(){
			window.location.href='${ctx }/core/train/expert/exportAll';
		}
	});
});

$('#expert_export_search').on('click',function(e){
	$(this).alertmsg('confirm', '确定要根据查询条件，导出专家信息吗？',{
		okCall:function(){
			var params = $('.expert_pager_form').serialize(); // http request parameters. 
			params = decodeURIComponent(params,true);
			params = encodeURI(encodeURI(params));
			window.location.href='${ctx }/core/train/expert/exportSearch?'+params;
		}
	});
});

</script>

<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<shiro:hasPermission name="Expert:create">
			<button type="button" id="but_expert_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="Expert:update">
			<button type="button" id="but_expert_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="Expert:delete">
			<button type="button" id="but_expert_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
	<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
	</div>
	
		<div class="pull-left">
		        <div class="btn-group">
		        	<shiro:hasPermission name="Expert:import">
		        		<button type="button" id="but_expert_import" class="btn btn-dark-blue" data-icon="sign-in" >导入</button>
		        	</shiro:hasPermission>
		        	<shiro:hasPermission name="Expert:export">
		        	<button type="button" class="btn-purple dropdown-toggle" data-toggle="dropdown" data-icon="sign-out">导出<span class="caret"></span></button>
		            <ul class="dropdown-menu right" role="menu">
		                <li><a href="javascript:;" id="expert_export_all">&nbsp;<span class="fa fa-asterisk"></span>&nbsp; 导出<span style="color: green;">全部</span></a></li>
		                <li class="divider"></li>
		                <li><a href="javascript:;" id="expert_export_search" >&nbsp;<span class="fa fa-search"></span>&nbsp; 按<span style="color: blue;">查询条件</span>导出</a></li>
		            </ul>
		            </shiro:hasPermission>
		        </div>
		</div>
    <form id="pagerForm" class="expert_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/expert/expertList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>姓名</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>身份证</label><input type="text" value="${query.idCard }" name="idCard" class="form-control" size="10">&nbsp;
			<label>单位</label><input type="text" value="${query.dept }" name="dept" class="form-control" size="10">&nbsp;
			<label>职称</label><input type="text" value="${query.title }" name="title" class="form-control" size="10">&nbsp;
			<shiro:hasRole name="PROVINCE_ADMIN">
			<label>联系电话</label><input type="text" value="${query.mobile }" name="mobile" class="form-control" size="10">&nbsp;
			<label>电子邮箱</label><input type="text" value="${query.email }" name="email" class="form-control" size="10">&nbsp;
			</shiro:hasRole>
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
			<label>研究方向</label><input type="text" value="${query.direction }" name="direction" class="form-control" size="10">&nbsp;
			<label>授课时间从</label>
			 <input type="text" name="startTime"  value='<fmt:formatDate value="${query.startTime }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
			<label>到</label><input type="text" name="endTime"  value='<fmt:formatDate value="${query.endTime }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
			<label>优良率</label><input type="text" value="${query.goodRate }" name="goodRate" class="form-control" size="10">&nbsp;
			<label>课程名称</label><input type="text" value="${query.courseName }" name="courseName" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="expert_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="name" title="姓名">姓名</th>
			<!--  
			<th align="center" data-order-field="idCard" title="身份证">身份证</th>
			-->
			<th align="center" data-order-field="dept" title="单位">单位</th>
			<th align="center" data-order-field="courseName" title="课程名称">课程名称</th>
			<!-- 
			<th align="center" data-order-field="title" title="职称">职称</th>
			 -->
			<shiro:hasRole name="PROVINCE_ADMIN">
			<th align="center" data-order-field="mobile" title="联系电话">联系电话</th>
			<th align="center" data-order-field="email" title="电子邮件"  >电子邮件</th>
			</shiro:hasRole>
			<th align="center" data-order-field="direction" title="研究方向">研究方向</th>
			<th align="center" data-order-field="time" title="授课时间">授课时间</th>
			<th align="center" data-order-field="goodRate" title="优良率">优良率</th>
			
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td><a href="javascript:;" onclick="expertView(${item.id })">${item.name }</a></td>
			<!--  
			<td>${item.idCard }</td>
			-->
			<td>${item.dept }</td>
			<td >${item.courseName }</td>
			<!-- 
			<td>${item.title }</td>
			 -->
			<shiro:hasRole name="PROVINCE_ADMIN">
			<td> ${item.mobile }</td>
			<td>${item.email }</td>
			</shiro:hasRole>
			<td>${item.direction }</td>
			<td><fmt:formatDate value="${item.time }" pattern="yyyy-MM-dd"/></td>
			<td>${item.goodRate }</td>
			
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
  	
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>