<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_move_pojo_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_move_pojo_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});


$('#but_teacher_move_pojo_move').on('click', function(e) {
	var list = teacherMovePojo.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要进行人员调动的人！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一个人进行调动！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_move_pojo_form', url:'${ctx}/core/basic/teacher/move/recode/recodeForm/'+id, type:'POST',title:'教师调动',mask:'true',width:'856',height:'250'});
	}
});

//查看teacher详情
function teacherMoveView(id,type){
	$(this).dialog({id:'teacher-view-dialog', url:'${ctx}/core/basic/teacher/base/teacherView/'+id+'?type='+type, title:'教师详细信息',mask:'true',width:'850',height:'600'});
}

var teacherMovePojo = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_move_pojo_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherMovePojo:move">
			<button type="button" id="but_teacher_move_pojo_move" class="btn btn-primary" data-icon="edit" >调动</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_move_pojo_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/move/list" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<input type="text" value="${query.name }" placeholder="姓名" name="name" class="form-control" size="10">&nbsp;
			<input type="text" value="${query.idCard }" placeholder="身份证" name="idCard" maxlength="18" class="form-control" size="18">&nbsp;
			<input type="text" value="${query.tno }" name="tno" placeholder="标识码"  maxlength="16" class="form-control" size="16">&nbsp;
			<label>状态：</label>
				<input type="checkbox" name="moveStatus" data-toggle="icheck" data-label="调动中" <c:if test="${query.moveStatus }">checked</c:if>>
            <button type="submit" class="btn btn-blue" data-icon="search" id="pojo-search-btn">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>

<div id="teacher_move_pojo_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
    		<th align="center" data-order-field="tno" title="标识码">标识码</th>
			<th align="center" data-order-field="name" title="姓名">姓名</th>
			<th align="center" data-order-field="gender" title="性别">性别</th>
			<th align="center" data-order-field="birthday" title="出生日期">出生日期</th>
			<th width="80" align="center" data-order-field="moveStatus" title="调动状态">调动状态</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td>
				<c:if test="${item.moveStatus !=1}">
					<input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }">
				</c:if>
			</td>
    		<td>${index.count+page.pageStart}</td>
    		<td>${item.tno }</td>
			<td><a href="javascript:;" onclick="teacherView(${item.id })">${item.name }</a></td>
			<td>${item.genderName }</td>
			<td><fmt:formatDate value="${item.birthday }" pattern="yyyy-MM-dd"/></td>
			
			<td>
				<c:if test="${item.moveStatus ==1}"><span class="label label-danger">调动中</span></c:if>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>