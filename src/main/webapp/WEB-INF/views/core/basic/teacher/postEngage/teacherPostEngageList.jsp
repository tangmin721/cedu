<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_post_engage_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_post_engage_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_post_engage_add').on('click', function(e) {
	$(this).dialog({id:'teacher_post_engage_form', url:'${ctx}/core/basic/teacher/postEngage/teacherPostEngageForm?tid=${tid}&tfs_type=${tfs_type}', type:'POST',title:'添加岗位聘任',mask:'true',width:'480',height:'360'});
});

$('#but_teacher_post_engage_edit').on('click', function(e) {
	var list = teacherPostEngage.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_post_engage_form', url:'${ctx}/core/basic/teacher/postEngage/teacherPostEngageForm/'+id+'/${tfs_type}', type:'POST',title:'编辑岗位聘任',mask:'true',width:'480',height:'360'});
	}
});

$('#but_teacher_post_engage_delete').on('click', function(e) {
	var list = teacherPostEngage.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/postEngage/deleteTeacherPostEngageByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherPostEngageTable"});
	}
});

function delRefushTeacherPostEngageTable(json){
	$('.teacher_post_engage_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/postEngage/teacherPostEngageList/${tid}/${tfs_type}'});
}


var teacherPostEngage = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_post_engage_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherPostEngage:create">
			<button type="button" id="but_teacher_post_engage_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherPostEngage:update">
			<button type="button" id="but_teacher_post_engage_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherPostEngage:delete">
			<button type="button" id="but_teacher_post_engage_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_post_engage_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/postEngage/teacherPostEngageList/${tid}/${tfs_type}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${query.tid}">
    </form>
</div>

<div id="teacher_post_engage_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="postType" title="岗位类别">岗位类别</th>
			<th align="center" data-order-field="postLevel" title="岗位等级">岗位等级</th>
			<th align="center" data-order-field="startDate" title="聘任开始年月">聘任开始年月</th>
			<th align="center" data-order-field="parttimeFlag" title="是否兼任其他岗位">是否兼任其他岗位</th>
			<th align="center" data-order-field="parttimeType" title="兼任岗位类别">兼任岗位类别</th>
			<th align="center" data-order-field="parttimeLevel" title="兼任岗位等级">兼任岗位等级</th>
			<th align="center" data-order-field="duty" title="校级职务">校级职务</th>
			<th align="center" data-order-field="dutyStartDate" title="任职开始年月">任职开始年月</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>
			<c:forEach items="${postTypes }" var="items">
			  <c:if test="${items.id==item.postType}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${postLevels }" var="items">
			  <c:if test="${items.id==item.postLevel}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td><fmt:formatDate value="${item.startDate }" pattern="yyyy-MM-dd"/></td>
			<td>
			<c:forEach items="${parttimeFlag }" var="items">
			  <c:if test="${items.id==item.parttimeFlag}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${parttimePostType }" var="items">
			  <c:if test="${items.id==item.parttimeType}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${postLevels }" var="items">
			  <c:if test="${items.id==item.parttimeLevel}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.duty }</td>
			<td><fmt:formatDate value="${item.dutyStartDate }" pattern="yyyy-MM-dd"/></td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>