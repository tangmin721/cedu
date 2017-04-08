<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_education_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_education_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_education_add').on('click', function(e) {
	$(this).dialog({id:'teacher_education_form', url:'${ctx}/core/basic/teacher/teacherEducation/teacherEducationForm?tid=${tid}&tfs_type=${tfs_type}', type:'POST',title:'添加教育教学',mask:'true',width:'600',height:'520'});
});

$('#but_teacher_education_edit').on('click', function(e) {
	var list = teacherEducation.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_education_form', url:'${ctx}/core/basic/teacher/teacherEducation/teacherEducationForm/'+id+'/${tfs_type}', type:'POST',title:'编辑教育教学',mask:'true',width:'600',height:'520'});
	}
});

$('#but_teacher_education_delete').on('click', function(e) {
	var list = teacherEducation.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/teacherEducation/deleteTeacherEducationByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherEducationTable"});
	}
});

function delRefushTeacherEducationTable(json){
	$('.teacher_education_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherEducation/teacherEducationList/${tid}/${tfs_type}'});
}


var teacherEducation = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_education_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherEducation:create">
			<button type="button" id="but_teacher_education_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherEducation:update">
			<button type="button" id="but_teacher_education_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherEducation:delete">
			<button type="button" id="but_teacher_education_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_education_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/teacherEducation/teacherEducationList/${tid}/${tfs_type}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_education_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="year" title="学年">学年</th>
			<th align="center" data-order-field="term" title="学期">学期</th>
			<c:if test="${tfs_type == 0 || tfs_type == 2 }">
			<th align="center" data-order-field="tchSegment" title="任教学段">任教学段</th>
			</c:if>
			<th align="center" data-order-field="tchStatus" title="任课状况">任课状况</th>
			<th align="center" data-order-field="tchStatusDesc" title="任课状况为其他情况的具体说明">任课状况为其他情况的具体说明</th>
			<c:if test="${tfs_type == 0 || tfs_type == 2 }">
			<th align="center" data-order-field="tchCourseType" title="任教课程">任教课程</th>
			</c:if>
			<c:if test="${tfs_type == 1 }">
			<th align="center" data-order-field="secondaryCourseType" title="任课课程类别">任课课程类别</th>
			<th align="center" data-order-field="secondarySubjectType" title="任课学科类别">任课学科类别</th>
			</c:if>
			<th align="center" data-order-field="classHour" title="平均每周课堂教学课时数">平均每周课堂教学课时数</th>
			<c:if test="${tfs_type == 2 }">
			<th align="center" data-order-field="otherJobType" title="承担的其他工作">承担的其他工作</th>
			</c:if>
			<th align="center" data-order-field="otherClassHour" title="平均每周其他工作折合教学课时数">平均每周其他工作折合教学课时数</th>
			<th align="center" data-order-field="partTimeJob" title="兼任工作">兼任工作</th>
			<th align="center" data-order-field="partTimeName" title="兼任其他工作名称">兼任其他工作名称</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>
			<c:forEach items="${years }" var="items">
			<c:if test="${item.year == items.id }">${items.name}</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${terms }" var="items">
			<c:if test="${item.term == items.id }">${items.name}</c:if>
			</c:forEach>
			</td>
			<c:if test="${tfs_type == 0 || tfs_type == 2 }">
			<td>${item.tchSegment }</td>
			</c:if>
			<td>
			<c:forEach items="${tchStatuss }" var="items">
			<c:if test="${item.tchStatus == items.id }">${items.name}</c:if>
			</c:forEach>
			</td>
			<td>${item.tchStatusDesc }</td>
			<c:if test="${tfs_type == 0 || tfs_type == 2 }">
			<td>${item.tchCourseType }</td>
			</c:if>
			<c:if test="${tfs_type == 1 }">
			<td>
			<c:forEach items="${secondaryCourseTypes }" var="items">
			<c:if test="${item.secondaryCourseType == items.id }">${items.name}</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${secondarySubjectTypes }" var="items">
			<c:if test="${item.secondarySubjectType == items.id }">${items.name}</c:if>
			</c:forEach>
			</td>
			</c:if>
			<td>${item.classHour }</td>
			<c:if test="${tfs_type == 2 }">
			<td>
			<c:forEach items="${otherJobTypes }" var="items">
			<c:if test="${item.otherJobType == items.id }">${items.name}</c:if>
			</c:forEach>
			</td>
			</c:if>
			<td>${item.otherClassHour }</td>
			<td>
			<c:forEach items="${partTimeJobs }" var="items">
			<c:if test="${item.partTimeJob == items.id }">${items.name}</c:if>
			</c:forEach>
			</td>
			<td>${item.partTimeName }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>