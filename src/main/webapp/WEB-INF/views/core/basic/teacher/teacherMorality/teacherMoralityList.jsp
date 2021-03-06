<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_morality_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_morality_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_morality_add').on('click', function(e) {
	$(this).dialog({id:'teacher_morality_form', url:'${ctx}/core/basic/teacher/teacherMorality/teacherMoralityForm?tid=${tid}', type:'POST',title:'添加师德信息',mask:'true',width:'600',height:'580'});
});

$('#but_teacher_morality_edit').on('click', function(e) {
	var list = teacherMorality.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_morality_form', url:'${ctx}/core/basic/teacher/teacherMorality/teacherMoralityForm/'+id, type:'POST',title:'编辑师德信息',mask:'true',width:'600',height:'580'});
	}
});

$('#but_teacher_morality_delete').on('click', function(e) {
	var list = teacherMorality.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/teacherMorality/deleteTeacherMoralityByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherMoralityTable"});
	}
});

function delRefushTeacherMoralityTable(json){
	$('.teacher_morality_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherMorality/teacherMoralityList/${tid}'});
}


var teacherMorality = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_morality_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherMorality:create">
			<button type="button" id="but_teacher_morality_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherMorality:update">
			<button type="button" id="but_teacher_morality_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherMorality:delete">
			<button type="button" id="but_teacher_morality_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_morality_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/teacherMorality/teacherMoralityList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_morality_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="assessDate" title="师德考核时间">师德考核时间</th>
			<th align="center" data-order-field="assessResult" title="师德考核结论">师德考核结论</th>
			<th align="center" data-order-field="unitName" title="师德考核单位名称">师德考核单位名称</th>
			<th align="center" data-order-field="honorLevel" title="荣誉级别">荣誉级别</th>
			<th align="center" data-order-field="honorName" title="获得荣誉称号">获得荣誉称号</th>
			<th align="center" data-order-field="honorStartDate" title="荣誉发生日期">荣誉发生日期</th>
			<th align="center" data-order-field="honorDesc" title="荣誉记录描述">荣誉记录描述</th>
			<th align="center" data-order-field="honorAwardUnit" title="荣誉授予单位名称">荣誉授予单位名称</th>
			<th align="center" data-order-field="punishType" title="处分类别">处分类别</th>
			<th align="center" data-order-field="punishReason" title="处分原因">处分原因</th>
			<th align="center" data-order-field="punishStartDate" title="处分发生日期">处分发生日期</th>
			<th align="center" data-order-field="punishDec" title="处分记录描述">处分记录描述</th>
			<th align="center" data-order-field="punishUnit" title="处分单位名称">处分单位名称</th>
			<th align="center" data-order-field="punishDate" title="处分日期">处分日期</th>
			<th align="center" data-order-field="punishCancelDate" title="处分撤销日期">处分撤销日期</th>
			<th align="center" data-order-field="punishCancelReason" title="处分撤销原因">处分撤销原因</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>
			<fmt:formatDate value="${item.assessDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td>
			<c:forEach items="${assessResults }" var="items">
			<c:if test="${items.id == item.assessResult }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.unitName }</td>
			<td>
			<c:forEach items="${honorLevels }" var="items">
			<c:if test="${items.id == item.honorLevel }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${honorNames }" var="items">
			<c:if test="${items.id == item.honorName }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<fmt:formatDate value="${item.honorStartDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td>${item.honorDesc }</td>
			<td>${item.honorAwardUnit }</td>
			<td>
			<c:forEach items="${punishTypes }" var="items">
			<c:if test="${items.id == item.punishType }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${punishReasons }" var="items">
			<c:if test="${items.id == item.punishReason }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<fmt:formatDate value="${item.punishStartDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td>${item.punishDec }</td>
			<td>${item.punishUnit }</td>
			<td>
			<fmt:formatDate value="${item.punishDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td>
			<fmt:formatDate value="${item.punishCancelDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td>${item.punishCancelReason }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>