<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_work_exp_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_work_exp_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_work_exp_add').on('click', function(e) {
	$(this).dialog({id:'teacher_work_exp_form', url:'${ctx}/core/basic/teacher/workExp/teacherWorkExpForm?tid=${tid}', type:'POST',title:'添加工作经历',mask:'true',width:'460',height:'280'});
});

$('#but_teacher_work_exp_edit').on('click', function(e) {
	var list = teacherWorkExp.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_work_exp_form', url:'${ctx}/core/basic/teacher/workExp/teacherWorkExpForm/'+id, type:'POST',title:'编辑工作经历',mask:'true',width:'460',height:'280'});
	}
});

$('#but_teacher_work_exp_delete').on('click', function(e) {
	var list = teacherWorkExp.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/workExp/deleteTeacherWorkExpByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherWorkExpTable"});
	}
});

function delRefushTeacherWorkExpTable(json){
	$('.teacher_work_exp_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/workExp/teacherWorkExpList/${tid}'});
}


var teacherWorkExp = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_work_exp_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherWorkExp:create">
			<button type="button" id="but_teacher_work_exp_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherWorkExp:update">
			<button type="button" id="but_teacher_work_exp_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherWorkExp:delete">
			<button type="button" id="but_teacher_work_exp_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_work_exp_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/workExp/teacherWorkExpList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_work_exp_table" class="tableContent">
    <table class="table table-hover table-bordered" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<th align="center" data-order-field="seq">序号</th>
    		<th align="center" data-order-field="dept">任职单位名称</th>
			<th align="center" data-order-field="startDate">任职开始年月</th>
			<th align="center" data-order-field="endDate">任职结束年月</th>
			<th align="center" data-order-field="deptType">单位性质类别</th>
			<th align="center" data-order-field="duty">任职岗位</th>
			<!--  
			<th align="center" data-order-field="voucher">证明人</th>
			<th align="center" data-order-field="mobile">证明人手机</th>
			<th align="center" data-order-field="memo">备注</th>
			-->
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>${item.seq }</td>
    		<td>${item.dept }</td>
			<td><fmt:formatDate value="${item.startDate }" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${item.endDate }" pattern="yyyy-MM-dd"/></td>
			<td>
			<c:forEach items="${deptTypes }" var="items">
			<c:if test="${item.deptType == items.id }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.duty }</td>
			<!--  
			<td>${item.voucher }</td>
			<td>${item.mobile }</td>
			<td>${item.memo }</td>
			-->
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>