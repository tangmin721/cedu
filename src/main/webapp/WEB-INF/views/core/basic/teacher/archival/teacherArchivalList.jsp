<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_archival_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_archival_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_archival_add').on('click', function(e) {
	$(this).dialog({id:'teacher_archival_form', url:'${ctx}/core/basic/teacher/archival/teacherArchivalForm', type:'POST',title:'添加学籍档案',mask:'true',width:'600',height:'520'});
});

$('#but_teacher_archival_edit').on('click', function(e) {
	var list = teacherArchival.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_archival_form', url:'${ctx}/core/basic/teacher/archival/teacherArchivalForm/'+id, type:'POST',title:'编辑学籍档案',mask:'true',width:'600',height:'520'});
	}
});

$('#but_teacher_archival_delete').on('click', function(e) {
	var list = teacherArchival.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/archival/deleteTeacherArchivalByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherArchivalTable"});
	}
});

function delRefushTeacherArchivalTable(json){
	$('.teacher_archival_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/archival/teacherArchivalList'});
}


var teacherArchival = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_archival_table input[name="ids"]').each(function(){ 
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
    <form id="pagerForm" class="teacher_archival_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/archival/teacherArchivalList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>教师id</label><input type="text" value="${query.tid }" name="tid" class="form-control" size="10">&nbsp;
			<label>人员类型</label><input type="text" value="${query.personType }" name="personType" class="form-control" size="10">&nbsp;
			<label>行政职务</label><input type="text" value="${query.duty }" name="duty" class="form-control" size="10">&nbsp;
			<label>职称</label><input type="text" value="${query.title }" name="title" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>教师资格</label><input type="text" value="${query.qualify }" name="qualify" class="form-control" size="10">&nbsp;
			<label>骨干类型</label><input type="text" value="${query.boneType }" name="boneType" class="form-control" size="10">&nbsp;
			<label>岗位等级</label><input type="text" value="${query.jobLevel }" name="jobLevel" class="form-control" size="10">&nbsp;
			<label>政治面貌</label><input type="text" value="${query.politic }" name="politic" class="form-control" size="10">&nbsp;
			<label>入党时间</label><input type="text" value="${query.joinDay }" name="joinDay" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="teacher_archival_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="tid">教师姓名</th>
			<th align="center" data-order-field="personType">人员类型</th>
			<th align="center" data-order-field="duty">行政职务</th>
			<th align="center" data-order-field="title">职称</th>
			<th align="center" data-order-field="qualify">教师资格</th>
			<th align="center" data-order-field="boneType">骨干类型</th>
			<th align="center" data-order-field="jobLevel">岗位等级</th>
			<th align="center" data-order-field="politic">政治面貌</th>
			<th align="center" data-order-field="joinDay">入党时间</th>
			<th align="center" data-order-field="memo">备注</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.tid }</td>
			<td>${item.personType }</td>
			<td>${item.duty }</td>
			<td>${item.title }</td>
			<td>${item.qualify }</td>
			<td>${item.boneType }</td>
			<td>${item.jobLevel }</td>
			<td>${item.politic }</td>
			<td><fmt:formatDate value="${item.joinDay }" pattern="yyyy-MM-dd"/></td>
			<td>${item.memo }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>