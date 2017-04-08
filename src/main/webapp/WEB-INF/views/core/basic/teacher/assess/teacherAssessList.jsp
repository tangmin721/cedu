<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_assess_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_assess_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_assess_add').on('click', function(e) {
	$(this).dialog({id:'teacher_assess_form', url:'${ctx}/core/basic/teacher/assess/teacherAssessForm?tid=${tid}', type:'POST',title:'添加考核情况',mask:'true',width:'450',height:'230'});
});

$('#but_teacher_assess_edit').on('click', function(e) {
	var list = teacherAssess.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_assess_form', url:'${ctx}/core/basic/teacher/assess/teacherAssessForm/'+id, type:'POST',title:'编辑考核情况',mask:'true',width:'450',height:'230'});
	}
});

$('#but_teacher_assess_delete').on('click', function(e) {
	var list = teacherAssess.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/assess/deleteTeacherAssessByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherAssessTable"});
	}
});

function delRefushTeacherAssessTable(json){
	$('.teacher_assess_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/assess/teacherAssessList/${tid}'});
}


var teacherAssess = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_assess_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherAssess:create">
			<button type="button" id="but_teacher_assess_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherAssess:update">
			<button type="button" id="but_teacher_assess_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherAssess:delete">
			<button type="button" id="but_teacher_assess_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_assess_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/assess/teacherAssessList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
        <!--  
        <div class="bjui-searchBar pull-right">
			<label>教师id</label><input type="text" value="${query.tid }" name="tid" class="form-control" size="10">&nbsp;
			<label>考核年度</label><input type="text" value="${query.cyear }" name="cyear" class="form-control" size="10">&nbsp;
			<label>考核结果</label><input type="text" value="${query.checkResult }" name="checkResult" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
			<label>排序</label><input type="text" value="${query.seq }" name="seq" class="form-control" size="10">&nbsp;
        </div>
        -->
    </form>
</div>

<div id="teacher_assess_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="cyear" title="考核年度">考核年度</th>
			<th align="center" data-order-field="checkResult" title="考核结果">考核结果</th>
			<th align="center" data-order-field="assessUnit" title="考核单位名称">考核单位名称</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>
			<c:forEach items="${cyears }" var="items">
				<c:if test="${items.id == item.cyear }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${checkResults }" var="items">
				<c:if test="${items.id == item.checkResult }">${items.name }</c:if>			               		
			</c:forEach>
			</td>
			<td>${item.assessUnit }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>