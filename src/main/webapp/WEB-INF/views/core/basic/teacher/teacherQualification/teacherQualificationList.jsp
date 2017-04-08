<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_qualification_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_qualification_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_qualification_add').on('click', function(e) {
	$(this).dialog({id:'teacher_qualification_form', url:'${ctx}/core/basic/teacher/teacherQualification/teacherQualificationForm?tid=${tid}', type:'POST',title:'添加教师资格',mask:'true',width:'500',height:'420'});
});

$('#but_teacher_qualification_edit').on('click', function(e) {
	var list = teacherQualification.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_qualification_form', url:'${ctx}/core/basic/teacher/teacherQualification/teacherQualificationForm/'+id, type:'POST',title:'编辑教师资格',mask:'true',width:'500',height:'420'});
	}
});

$('#but_teacher_qualification_delete').on('click', function(e) {
	var list = teacherQualification.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/teacherQualification/deleteTeacherQualificationByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherQualificationTable"});
	}
});

function delRefushTeacherQualificationTable(json){
	$('.teacher_qualification_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherQualification/teacherQualificationList/${tid}'});
}


var teacherQualification = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_qualification_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherQualification:create">
			<button type="button" id="but_teacher_qualification_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherQualification:update">
			<button type="button" id="but_teacher_qualification_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherQualification:delete">
			<button type="button" id="but_teacher_qualification_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_qualification_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/teacherQualification/teacherQualificationList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_qualification_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="seq" title="序号">序号</th>
			<th align="center" data-order-field="qualifyType" title="教师资格证种类">教师资格证种类</th>
			<th align="center" data-order-field="qualifyNo" title="教师资格证号码">教师资格证号码</th>
			<th align="center" data-order-field="sbuject" title="任教学科">任教学科</th>
			<th align="center" data-order-field="certificateDate" title="证书颁发日期">证书颁发日期</th>
			<th align="center" data-order-field="issueAgency" title="字颁发机构">颁发机构</th>
			<th align="center" data-order-field="firstRegistDate" title="首次注册日期">首次注册日期</th>
			<th align="center" data-order-field="firstRegistResult" title="首次注册结论">首次注册结论</th>
			<th align="center" data-order-field="fixRegistDate" title="定期注册日期">定期注册日期</th>
			<th align="center" data-order-field="fixRegistResult" title="定期注册结论">定期注册结论</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.seq }</td>
			<td>
			<c:forEach items="${qualifys }" var="items">
				<c:if test="${items.id == item.qualifyType}">${items.name} </c:if>
			</c:forEach>
			</td>
			<td>${item.qualifyNo }</td>
			<td>${item.sbuject }</td>
			<td>
			<fmt:formatDate value="${item.certificateDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td>${item.issueAgency }</td>
			<td>
			<fmt:formatDate value="${item.firstRegistDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td>${item.firstRegistResult }</td>
			<td>
			<fmt:formatDate value="${item.fixRegistDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td>${item.fixRegistResult }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>