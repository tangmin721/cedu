<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_academic_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_academic_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_academic_add').on('click', function(e) {
	$(this).dialog({id:'teacher_academic_form', url:'${ctx}/core/basic/teacher/academic/teacherAcademicForm?tid=${query.tid}', type:'POST',title:'添加学术论文',mask:'true',width:'600',height:'520'});
});

$('#but_teacher_academic_edit').on('click', function(e) {
	var list = teacherAcademic.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_academic_form', url:'${ctx}/core/basic/teacher/academic/teacherAcademicForm/'+id, type:'POST',title:'编辑学术论文',mask:'true',width:'600',height:'520'});
	}
});

$('#but_teacher_academic_delete').on('click', function(e) {
	var list = teacherAcademic.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/academic/deleteTeacherAcademicByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherAcademicTable"});
	}
});

function delRefushTeacherAcademicTable(json){
	$('.teacher_academic_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/academic/teacherAcademicList/${tid}'});
}


var teacherAcademic = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_academic_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherAcademic:create">
			<button type="button" id="but_teacher_academic_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherAcademic:update">
			<button type="button" id="but_teacher_academic_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherAcademic:delete">
			<button type="button" id="but_teacher_academic_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_academic_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/academic/teacherAcademicList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="100">
        <input type="hidden" name="pageCurrent" value="1">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" value="${query.tid }" name="tid">
        <!--  <div class="bjui-searchBar pull-right">
			<label>名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>发布年度</label><input type="text" value="${query.publishDate }" name="publishDate" class="form-control" size="10">&nbsp;
			<label>出版源</label><input type="text" value="${query.publishSource }" name="publishSource" class="form-control" size="10">&nbsp;
			<button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
		</div>
		<div class="bjui-moreSearch pull-right">
			<select name="roleType" data-toggle="selectpicker" data-width="100px">
				<option value="">--请选择--</option>
				<c:forEach items="${roleTypes }" var="item">
					<option value="${item.id }" <c:if test="${item.id==entity.roleType}">selected</c:if>>${item.name }</option>
				</c:forEach>
			</select>&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
			<label>序号</label><input type="text" value="${query.seq }" name="seq" class="form-control" size="10">&nbsp; 
        </div>
        -->
    </form>
</div>

<div id="teacher_academic_table" class="bjui-pageContent tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="name">名称</th>
			<th align="center" data-order-field="publishDate">发布年度</th>
			<th align="center" data-order-field="pnum">篇数</th>
			<th align="center" data-order-field="zsize">字数</th>
			<th align="center" data-order-field="publishSource">出版源</th>
			<th align="center" data-order-field="roleType">角色</th>
			<th align="center" data-order-field="memo">备注</th>
			<th align="center" data-order-field="seq">序号</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page}" varStatus="index">
    	<tr data-id="${item.id }">
    		<!-- 
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		 -->
    		<td>
			<c:choose>
				<c:when test="${item.flag == true}">
 					<input type="hidden" name="ids" value="${item.id }" >
 				</c:when>
				<c:otherwise><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }" ></c:otherwise>
			</c:choose>
			</td>
    		<td>${index.count}</td>
			<td>${item.name }</td>
			<td>
				<c:forEach items="${years }" var="items">
			        <c:if test="${items.id==item.publishDate}">${items.name }</c:if>
				</c:forEach>
			</td>
			<td>${item.pnum }</td>
			<td>${item.zsize }</td>
			<td>${item.publishSource }</td>
			<td><c:forEach items="${roleTypes }" var="items">
				<c:if test="${items.id==item.roleType}">${items.name }</c:if>
            </c:forEach></td>
			<td>${item.memo }</td>
			<td>${item.seq }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>