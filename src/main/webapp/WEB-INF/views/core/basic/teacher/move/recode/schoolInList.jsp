<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_move_recode_in_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_move_recode_in_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_move_recode_in_add').on('click', function(e) {
	$(this).dialog({id:'teacher_move_recode_in_form', url:'${ctx}/core/basic/teacher/move/recode/teacherMoveRecodeForm', type:'POST',title:'添加教师调动',mask:'true',width:'600',height:'520'});
});

$('#but_teacher_move_recode_in_edit').on('click', function(e) {
	var list = teacherMoveRecode.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_move_recode_in_form', url:'${ctx}/core/basic/teacher/move/recode/teacherMoveRecodeForm/'+id, type:'POST',title:'编辑教师调动',mask:'true',width:'600',height:'520'});
	}
});

$('#but_teacher_move_recode_in_delete').on('click', function(e) {
	var list = teacherMoveRecode.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/move/recode/deleteTeacherMoveRecodeByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherMoveRecodeTable"});
	}
});


function acceptMove(id){
	$.ajax({
		type: "POST",
		url: '${ctx}/core/basic/teacher/move/recode/acceptMove/'+id,
		dataType : "json",
		success: function(data) {
			$('#out-search').trigger("click");
			$(this).navtab('reloadFlag','ntv-tmove-list');
			$(this).navtab('reloadFlag','ntv-move-in');
			$(this).alertmsg('ok', '操作成功！');
		},
		error :  function(){
			$(this).alertmsg('error', '系统错误！');
		}
	});
}

function refuseMove(id){
	$(this).dialog({id:'refuse_form', url:'${ctx}/core/basic/teacher/move/recode/refuseMove/'+id, type:'POST',title:'拒绝调动理由',mask:'true',width:'600',height:'180'});
}

function delRefushTeacherMoveRecodeTable(json){
	$('.teacher_move_recode_in_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/move/recode/teacherMoveRecodeList'});
}


var teacherMoveRecode = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_move_recode_in_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherMoveRecode:create">
			<button type="button" id="but_teacher_move_recode_in_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherMoveRecode:update">
			<button type="button" id="but_teacher_move_recode_in_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherMoveRecode:delete">
			<button type="button" id="but_teacher_move_recode_in_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_move_recode_in_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/move/recode/schoolInList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>姓名</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>标识码</label><input type="text" value="${query.tno }" name="tno" class="form-control" size="16" maxlength="16">&nbsp;
			<label>身份证</label><input type="text" value="${query.idCard }" name="idCard" class="form-control" size="18" maxlength="18">&nbsp;
			<label>调动状态</label>
			<select name="moveStatus"  data-toggle="selectpicker"  data-width="100px">
				<option value="">--请选择--</option>
				<c:forEach items="${moveStatuses }" var="item">
					<option  value="${item.value }"  <c:if test="${item.value==query.moveStatus}">selected</c:if>>${item.desc }</option>
				</c:forEach>
			</select>&nbsp;
            <button type="submit" class="btn btn-blue" data-icon="search" id="out-search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>

<div id="teacher_move_recode_in_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" title="姓名">姓名</th>
			<th align="center" title="标识码">标识码</th>
			<th align="center" data-order-field="sendDate" title="申请时间">申请时间</th>
			<th align="center" title="调出校">调出校</th>
			<th align="center" title="调入状态">调入状态</th>
			<th align="center" data-order-field="sendDate" title="处理时间">处理时间</th>
			<th align="center" title="操作">操作</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>
				<a href="javascript:;" onclick="teacherView(${item.tid })">${item.name }</a>
			</td>
			<td>${item.tno }</td>
			<td>
				<fmt:formatDate value="${item.sendDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td>${item.provinceName }${item.cityName }${item.townName }${item.schoolName }</td>
			<td>
				<c:if test="${item.moveStatus ==1}"><span class="label label-info" data-toggle="tooltip"  data-placement="right" title="申请原因:${item.sendMemo}">调动中</span></c:if>
				<c:if test="${item.moveStatus ==2}"><span class="label label-success">调动完成</span></c:if>
				<c:if test="${item.moveStatus ==3}"><span class="label label-danger"  data-toggle="tooltip"  data-placement="right" title="拒绝理由:${item.takeMemo}">调动被拒绝</span></c:if>
			</td>
			<td>
				<fmt:formatDate value="${item.takeDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td>
				<c:if test="${item.moveStatus ==1}">
					<button type="button" class="btn btn-success" data-icon="check-circle" onclick="acceptMove(${item.id })" >接收</button>
					<button type="button" class="btn btn-danger" data-icon="times-circle" onclick="refuseMove(${item.id })" >拒绝</button>
				</c:if>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>