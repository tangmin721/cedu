<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_train_exp_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_train_exp_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_train_exp_add').on('click', function(e) {
	$(this).dialog({id:'teacher_train_exp_form', url:'${ctx}/core/basic/teacher/trainExp/teacherTrainExpForm?tid=${query.tid}', type:'POST',title:'添加国内培训情况',mask:'true',width:'450',height:'350'});
});

$('#but_teacher_train_exp_edit').on('click', function(e) {
	var list = teacherTrainExp.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_train_exp_form', url:'${ctx}/core/basic/teacher/trainExp/teacherTrainExpForm/'+id, type:'POST',title:'编辑国内培训情况',mask:'true',width:'600',height:'520'});
	}
});

$('#but_teacher_train_exp_delete').on('click', function(e) {
	var list = teacherTrainExp.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/trainExp/deleteTeacherTrainExpByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherTrainExpTable"});
	}
});

function delRefushTeacherTrainExpTable(json){
	$('.teacher_train_exp_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/trainExp/teacherTrainExpList/${tid}'});
}


var teacherTrainExp = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_train_exp_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherTrainExp:create">
			<button type="button" id="but_teacher_train_exp_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherTrainExp:update">
			<button type="button" id="but_teacher_train_exp_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherTrainExp:delete">
			<button type="button" id="but_teacher_train_exp_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_train_exp_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/trainExp/teacherTrainExpList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="200">
        <input type="hidden" name="pageCurrent" value="1">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
       <!--   <div class="bjui-searchBar pull-right">
			<label>起始时间</label><input type="text" value="${query.startDate }" name="startDate" class="form-control" size="10">&nbsp;
			<label>结束时间</label><input type="text" value="${query.endDate }" name="endDate" class="form-control" size="10">&nbsp;
			<label>培训类型</label> 
			<select name="trainType" data-toggle="selectpicker" data-width="100px">
				<option value="">--请选择--</option>
				<c:forEach items="${trainTypes }" var="item">
					<option value="${item.id }" <c:if test="${item.id==entity.trainType}">selected</c:if>>${item.name }</option>
				</c:forEach>
			</select>&nbsp;
			<label>培训项目</label><input type="text" value="${query.projectName }" name="projectName" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
			<label>序号</label><input type="text" value="${query.seq }" name="seq" class="form-control" size="10">&nbsp;
        </div>-->
    </form>
</div>

<div id="teacher_train_exp_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
    		<!--  
			<th align="center" data-order-field="startDate" title="起始时间">起始时间</th>
			<th align="center" data-order-field="endDate" title="结束时间">结束时间</th>
			-->
			<th align="center" data-order-field="year" title="培训年度">培训年度</th>
			<th align="center" data-order-field="trainType" title="培训类别">培训类别</th>
			<th align="center" data-order-field="projectName" title="培训项目名称">培训项目名称</th>
			<th align="center" data-order-field="trainOrg" title="培训机构名称">培训机构名称</th>
			<th align="center" data-order-field="trainMode" title="培训方式">培训方式</th>
			<th align="center" data-order-field="hours" title="培训获得学时">培训获得学时</th>
			<th align="center" data-order-field="score" title="培训获得学分">培训获得学分</th>
			<!--  
			<th align="center" data-order-field="memo" title="备注">备注</th>
			<th align="center" data-order-field="seq" title="排序">排序</th>
			-->
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
			<!-- 
			<td><fmt:formatDate value="${item.startDate }" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${item.endDate }" pattern="yyyy-MM-dd"/></td>
			 -->
			<td>
			<c:forEach items="${years }" var="items">
			  <c:if test="${items.id==item.year}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${trainTypes }" var="items">
			  <c:if test="${items.id==item.trainType}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.projectName }</td>
			<td>${item.trainOrg }</td>
			<td>
			<c:forEach items="${trainModes }" var="items">
			  <c:if test="${items.id==item.trainMode}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.hours }</td>
			<td>${item.score }</td>
			<!-- 
			<td>${item.memo }</td>
			<td>${item.seq }</td>
			 -->
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>