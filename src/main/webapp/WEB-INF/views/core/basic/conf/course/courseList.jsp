<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#course_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#course_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_course_add').on('click', function(e) {
	$(this).dialog({id:'course_form', url:'${ctx}/core/basic/conf/course/courseForm', type:'POST',title:'添加学科',mask:'true',width:'460',height:'260'});
});

$('#but_course_edit').on('click', function(e) {
	var list = course.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'course_form', url:'${ctx}/core/basic/conf/course/courseForm/'+id, type:'POST',title:'编辑学科',mask:'true',width:'460',height:'260'});
	}
});

$('#but_course_delete').on('click', function(e) {
	var list = course.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/conf/course/deleteCourseByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushCourseTable"});
	}
});

function delRefushCourseTable(json){
	$('.course_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/conf/course/courseList'});
}


var course = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#course_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="Course:create">
			<button type="button" id="but_course_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="Course:update">
			<button type="button" id="but_course_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="Course:delete">
			<button type="button" id="but_course_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="course_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/conf/course/courseList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			
			<label>学段</label>
			<select name="stageId" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${stages }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.stageId}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>编码</label><input type="text" value="${query.code }" name="code" class="form-control" size="10">&nbsp;
			
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>序号</label><input type="text" value="${query.seq }" name="seq" class="form-control" size="10">&nbsp;
        	<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="course_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="seq">序号</th>
			<th align="center" data-order-field="stageId">学段</th>
			<th align="center" data-order-field="name">名称</th>
			<th align="center" data-order-field="code">编码</th>
			<th align="center" data-order-field="memo">备注</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.seq }</td>
			<td>${item.stageName }</td>
			<td>${item.name }</td>
			<td>${item.code }</td>
			<td>${item.memo }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>