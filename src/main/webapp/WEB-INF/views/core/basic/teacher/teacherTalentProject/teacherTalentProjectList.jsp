<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_talent_project_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_talent_project_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_talent_project_add').on('click', function(e) {
	$(this).dialog({id:'teacher_talent_project_form', url:'${ctx}/core/basic/teacher/teacherTalentProject/teacherTalentProjectForm?tid=${tid}', type:'POST',title:'添加入选人才项目',mask:'true',width:'400',height:'260'});
});

$('#but_teacher_talent_project_edit').on('click', function(e) {
	var list = teacherTalentProject.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_talent_project_form', url:'${ctx}/core/basic/teacher/teacherTalentProject/teacherTalentProjectForm/'+id, type:'POST',title:'编辑入选人才项目',mask:'true',width:'400',height:'260'});
	}
});

$('#but_teacher_talent_project_delete').on('click', function(e) {
	var list = teacherTalentProject.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/teacherTalentProject/deleteTeacherTalentProjectByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherTalentProjectTable"});
	}
});

function delRefushTeacherTalentProjectTable(json){
	$('.teacher_talent_project_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherTalentProject/teacherTalentProjectList/${tid}'});
}


var teacherTalentProject = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_talent_project_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherTalentProject:create">
			<button type="button" id="but_teacher_talent_project_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherTalentProject:update">
			<button type="button" id="but_teacher_talent_project_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherTalentProject:delete">
			<button type="button" id="but_teacher_talent_project_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_talent_project_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/teacherTalentProject/teacherTalentProjectList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
		<input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_talent_project_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="seq" title="序号">序号</th>
			<th align="center" data-order-field="projectNo" title="项目名称">项目名称</th>
			<th align="center" data-order-field="selectYear" title="入选年份">入选年份</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.seq }</td>
			<td>
			<c:forEach items="${projectNos }" var="items">
			<c:if test="${items.id == item.projectNo}">${items.name}</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${years }" var="items">
			<c:if test="${item.selectYear == items.id }">${items.name }</c:if>
			</c:forEach>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>