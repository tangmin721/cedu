<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_overseas_study_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_overseas_study_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_overseas_study_add').on('click', function(e) {
	$(this).dialog({id:'teacher_overseas_study_form', url:'${ctx}/core/basic/teacher/overseasStudy/teacherOverseasStudyForm?tid=${tid}', type:'POST',title:'添加海外研修(访学)',mask:'true',width:'520',height:'320'});
});

$('#but_teacher_overseas_study_edit').on('click', function(e) {
	var list = teacherOverseasStudy.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_overseas_study_form', url:'${ctx}/core/basic/teacher/overseasStudy/teacherOverseasStudyForm/'+id, type:'POST',title:'编辑海外研修(访学)',mask:'true',width:'520',height:'320'});
	}
});

$('#but_teacher_overseas_study_delete').on('click', function(e) {
	var list = teacherOverseasStudy.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/overseasStudy/deleteTeacherOverseasStudyByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherOverseasStudyTable"});
	}
});

function delRefushTeacherOverseasStudyTable(json){
	$('.teacher_overseas_study_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/overseasStudy/teacherOverseasStudyList/${tid}'});
}


var teacherOverseasStudy = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_overseas_study_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherOverseasStudy:create">
			<button type="button" id="but_teacher_overseas_study_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherOverseasStudy:update">
			<button type="button" id="but_teacher_overseas_study_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherOverseasStudy:delete">
			<button type="button" id="but_teacher_overseas_study_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_overseas_study_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/overseasStudy/teacherOverseasStudyList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_overseas_study_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="seq" title="序号">序号</th>
			<th align="center" data-order-field="isOverseasExp" title="是否有海外研修（访学）经历">是否有海外研修（访学）经历</th>
			<th align="center" data-order-field="startDate" title="开始日期">开始日期</th>
			<th align="center" data-order-field="endDate" title="结束日期">结束日期</th>
			<th align="center" data-order-field="nation" title="国家(地区)">国家(地区)</th>
			<th align="center" data-order-field="orgName" title="研修（访学）机构名称">研修（访学）机构名称</th>
			<th align="center" data-order-field="projectName" title="项目名称">项目名称</th>
			<th align="center" data-order-field="unitName" title="项目组织单位名称">项目组织单位名称</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.seq }</td>
			<td>
			<c:forEach items="${overseasStudys }" var="items">
			<c:if test="${items.id == item.isOverseasExp }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td><fmt:formatDate value="${item.startDate }" pattern="yyy-MM-dd"/></td>
			<td><fmt:formatDate value="${item.endDate }" pattern="yyy-MM-dd"/></td>
			<td>
			<c:forEach items="${nations }" var="items">
			<c:if test="${items.id == item.nation }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.orgName }</td>
			<td>${item.projectName }</td>
			<td>${item.unitName }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>