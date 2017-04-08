<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_contact_way_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_contact_way_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_contact_way_add').on('click', function(e) {
	$(this).dialog({id:'teacher_contact_way_form', url:'${ctx}/core/basic/teacher/contactWay/teacherContactWayForm?tid=${tid}', type:'POST',title:'添加联系方式',mask:'true',width:'600',height:'520'});
});

$('#but_teacher_contact_way_edit').on('click', function(e) {
	var list = teacherContactWay.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_contact_way_form', url:'${ctx}/core/basic/teacher/contactWay/teacherContactWayForm/'+id, type:'POST',title:'编辑联系方式',mask:'true',width:'600',height:'520'});
	}
});

$('#but_teacher_contact_way_delete').on('click', function(e) {
	var list = teacherContactWay.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/contactWay/deleteTeacherContactWayByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherContactWayTable"});
	}
});

function delRefushTeacherContactWayTable(json){
	$('.teacher_contact_way_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/contactWay/teacherContactWayList/${tid}'});
}


var teacherContactWay = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_contact_way_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherContactWay:create">
			<button type="button" id="but_teacher_contact_way_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherContactWay:update">
			<button type="button" id="but_teacher_contact_way_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherContactWay:delete">
			<button type="button" id="but_teacher_contact_way_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_contact_way_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/contactWay/teacherContactWayList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_contact_way_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="seq" title="序号">序号</th>
			<th align="center" data-order-field="address" title="通讯地址">通讯地址</th>
			<th align="center" data-order-field="telphone" title="联系电话">联系电话</th>
			<th align="center" data-order-field="phoneNo" title="手机">手机</th>
			<th align="center" data-order-field="email" title="Email">Email</th>
			<th align="center" data-order-field="otherContact" title="其他联系方">其他联系方</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.tid }</td>
			<td>${item.seq }</td>
			<td>${item.address }</td>
			<td>${item.telphone }</td>
			<td>${item.phoneNo }</td>
			<td>${item.email }</td>
			<td>${item.otherContact }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>