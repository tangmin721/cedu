<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_edu_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_edu_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_edu_add').on('click', function(e) {
	$(this).dialog({id:'teacher_edu_form', url:'${ctx}/core/basic/teacher/edu/teacherEduForm', type:'POST',title:'添加教学和学历',mask:'true',width:'600',height:'520'});
});

$('#but_teacher_edu_edit').on('click', function(e) {
	var list = teacherEdu.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_edu_form', url:'${ctx}/core/basic/teacher/edu/teacherEduForm/'+id, type:'POST',title:'编辑教学和学历',mask:'true',width:'600',height:'520'});
	}
});

$('#but_teacher_edu_delete').on('click', function(e) {
	var list = teacherEdu.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/edu/deleteTeacherEduByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherEduTable"});
	}
});

function delRefushTeacherEduTable(json){
	$('.teacher_edu_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/edu/teacherEduList'});
}


var teacherEdu = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_edu_table input[name="ids"]').each(function(){ 
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
    <form id="pagerForm" class="teacher_edu_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/edu/teacherEduList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>教师id</label><input type="text" value="${query.tid }" name="tid" class="form-control" size="10">&nbsp;
			<label>学历</label><input type="text" value="${query.degree }" name="degree" class="form-control" size="10">&nbsp;
			<label>专业</label><input type="text" value="${query.major }" name="major" class="form-control" size="10">&nbsp;
			<label>毕业学校</label><input type="text" value="${query.univ }" name="univ" class="form-control" size="10">&nbsp;
			<label>学段</label><input type="text" value="${query.stage }" name="stage" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>从教时间</label><input type="text" value="${query.teachedDay }" name="teachedDay" class="form-control" size="10">&nbsp;
			<label>任职时间</label><input type="text" value="${query.workedDay }" name="workedDay" class="form-control" size="10">&nbsp;
        	<label>学科</label><input type="text" value="${query.course }" name="course" class="form-control" size="10">&nbsp;
			<label>年级</label><input type="text" value="${query.grade }" name="grade" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="teacher_edu_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="tid">Tid</th>
			<th align="center" data-order-field="degree">学历</th>
			<th align="center" data-order-field="major">专业</th>
			<th align="center" data-order-field="univ">毕业学校</th>
			<th align="center" data-order-field="teachedDay">从教日期</th>
			<th align="center" data-order-field="workedDay">任职日期</th>
			<th align="center" data-order-field="stage">学段</th>
			<th align="center" data-order-field="course">学科</th>
			<th align="center" data-order-field="grade">年级</th>
			<th align="center" data-order-field="memo">备注</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.tid }</td>
			<td>${item.degree }</td>
			<td>${item.major }</td>
			<td>${item.univ }</td>
			<td><fmt:formatDate value="${item.teachedDay }" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${item.workedDay }" pattern="yyyy-MM-dd"/></td>
			<td>${item.stage }</td>
			<td>${item.course }</td>
			<td>${item.grade }</td>
			<td>${item.memo }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>