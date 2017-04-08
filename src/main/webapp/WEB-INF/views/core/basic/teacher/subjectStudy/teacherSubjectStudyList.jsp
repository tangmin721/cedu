<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_subject_study_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_subject_study_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_subject_study_add').on('click', function(e) {
	$(this).dialog({id:'teacher_subject_study_form', url:'${ctx}/core/basic/teacher/subjectStudy/teacherSubjectStudyForm?tid=${tid}', type:'POST',title:'添加课题研究',mask:'true',width:'500',height:'360'});
});

$('#but_teacher_subject_study_edit').on('click', function(e) {
	var list = teacherSubjectStudy.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_subject_study_form', url:'${ctx}/core/basic/teacher/subjectStudy/teacherSubjectStudyForm/'+id, type:'POST',title:'编辑课题研究',mask:'true',width:'500',height:'360'});
	}
});

$('#but_teacher_subject_study_delete').on('click', function(e) {
	var list = teacherSubjectStudy.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/subjectStudy/deleteTeacherSubjectStudyByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherSubjectStudyTable"});
	}
});

function delRefushTeacherSubjectStudyTable(json){
	$('.teacher_subject_study_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/subjectStudy/teacherSubjectStudyList/${tid}'});
}


var teacherSubjectStudy = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_subject_study_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherSubjectStudy:create">
			<button type="button" id="but_teacher_subject_study_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherSubjectStudy:update">
			<button type="button" id="but_teacher_subject_study_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherSubjectStudy:delete">
			<button type="button" id="but_teacher_subject_study_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_subject_study_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/subjectStudy/teacherSubjectStudyList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="200">
        <input type="hidden" name="pageCurrent" value="1">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${query.tid}">
        <!--  <div class="bjui-searchBar pull-right">
			<label>年度</label>
			<select name="year" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${years }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.year}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>级别</label>
			<select name="level" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${levels }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.level}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>课题名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>角色</label>
        	<select name="roleType" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${roleTypes }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.roleType}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
        </div>-->
    </form>
</div>

<div id="teacher_subject_study_table" class="bjui-pageContent tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="level" title="级别">级别</th>
			<th align="center" data-order-field="year" title="年度">年度</th>
			<th align="center" data-order-field="roleType" title="角色">角色</th>
			<th align="center" data-order-field="name" title="课题名称">课题名称</th>
			<th align="center" data-order-field="memo" title="备注">备注</th>
			<th align="center" data-order-field="seq" title="序号">序号</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td>
    		<c:choose>
				<c:when test="${item.flag == true}">
 					<input type="hidden" name="ids" value="${item.id }" >
 				</c:when>
				<c:otherwise><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }" ></c:otherwise>
			</c:choose>
    		</td>
    		<td>${index.count}</td>
			<td>
			<c:forEach items="${levels }" var="items">
			  <c:if test="${items.id==item.level}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${years }" var="items">
			  <c:if test="${items.id==item.year}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${roleTypes }" var="items">
				<c:if test="${items.id==item.roleType}">${items.name }</c:if>
            </c:forEach>
			</td>
			<td>${item.name }</td>
			<td>${item.memo }</td>
			<td>${item.seq }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>