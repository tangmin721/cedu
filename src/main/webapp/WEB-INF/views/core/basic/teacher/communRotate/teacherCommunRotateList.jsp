<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_commun_rotate_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_commun_rotate_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_commun_rotate_add').on('click', function(e) {
	$(this).dialog({id:'teacher_commun_rotate_form', url:'${ctx}/core/basic/teacher/communRotate/teacherCommunRotateForm?tid=${tid}&tfs_type=${tfs_type}', type:'POST',title:'添加交流轮岗',mask:'true',width:'480',height:'340'});
});

$('#but_teacher_commun_rotate_edit').on('click', function(e) {
	var list = teacherCommunRotate.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_commun_rotate_form', url:'${ctx}/core/basic/teacher/communRotate/teacherCommunRotateForm/'+id+'/${tfs_type}', type:'POST',title:'编辑交流轮岗',mask:'true',width:'480',height:'340'});
	}
});

$('#but_teacher_commun_rotate_delete').on('click', function(e) {
	var list = teacherCommunRotate.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/communRotate/deleteTeacherCommunRotateByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherCommunRotateTable"});
	}
});

function delRefushTeacherCommunRotateTable(json){
	$('.teacher_commun_rotate_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/communRotate/teacherCommunRotateList/${tid}/${tfs_type}'});
}


var teacherCommunRotate = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_commun_rotate_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherCommunRotate:create">
			<button type="button" id="but_teacher_commun_rotate_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherCommunRotate:update">
			<button type="button" id="but_teacher_commun_rotate_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherCommunRotate:delete">
			<button type="button" id="but_teacher_commun_rotate_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_commun_rotate_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/communRotate/teacherCommunRotateList/${tid}/${tfs_type}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_commun_rotate_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="seq" title="序号">序号</th>
			<th align="center" data-order-field="rotateType" title="交流轮岗类型">交流轮岗类型</th>
			<th align="center" data-order-field="isTranRelation" title="是否调动人事关系">是否调动人事关系</th>
			<th align="center" data-order-field="startDate" title="开始年月">开始年月</th>
			<th align="center" data-order-field="endDate" title="结束年月">结束年月</th>
			<th align="center" data-order-field="oldUnitName" title="原单位名称">原单位名称</th>
			<th align="center" data-order-field="rotateUnitName" title="交流轮岗单位名称">交流轮岗单位名称</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.seq }</td>
			<td>
			<c:forEach items="${rotateTypes }" var="items">
			<c:if test="${item.rotateType == items.id }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${isFlags }" var="items">
			<c:if test="${item.isTranRelation == items.id }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td><fmt:formatDate value="${item.startDate }" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${item.endDate }" pattern="yyyy-MM-dd"/></td>
			<td>${item.oldUnitName }</td>
			<td>${item.rotateUnitName }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>