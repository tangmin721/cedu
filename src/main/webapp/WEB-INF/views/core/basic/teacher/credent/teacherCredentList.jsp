<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_credent_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_credent_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_credent_add').on('click', function(e) {
	$(this).dialog({id:'teacher_credent_form', url:'${ctx}/core/basic/teacher/credent/teacherCredentForm?tid=${query.tid}', type:'POST',title:'添加技能及证书',mask:'true',width:'460',height:'420'});
});

$('#but_teacher_credent_edit').on('click', function(e) {
	var list = teacherCredent.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_credent_form', url:'${ctx}/core/basic/teacher/credent/teacherCredentForm/'+id, type:'POST',title:'编辑技能及证书',mask:'true',width:'460',height:'420'});
	}
});

$('#but_teacher_credent_delete').on('click', function(e) {
	var list = teacherCredent.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/credent/deleteTeacherCredentByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherCredentTable"});
	}
});

function delRefushTeacherCredentTable(json){
	$('.teacher_credent_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/credent/teacherCredentList'});
}


var teacherCredent = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_credent_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherCredent:create">
			<button type="button" id="but_teacher_credent_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherCredent:update">
			<button type="button" id="but_teacher_credent_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherCredent:delete">
			<button type="button" id="but_teacher_credent_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_credent_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/credent/teacherCredentList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" value="${query.tid }" name="tid" >
       <!--   <div class="bjui-searchBar pull-right">
			<label>证书类型</label>
			<select name="type" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${credents }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.type}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			<label>名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>编号</label><input type="text" value="${query.credentNo }" name="credentNo" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>获取时间</label><input type="text" value="${query.takeDate }" name="takeDate" class="form-control" size="10">&nbsp;
			<label>颁发单位</label><input type="text" value="${query.dept }" name="dept" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
			<label>序号</label><input type="text" value="${query.seq }" name="seq" class="form-control" size="10">&nbsp;
        </div>-->
    </form>
</div>

<div id="teacher_credent_table" class="tableContent">

	<table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<th align="center" data-order-field="languageType" title="语种">语种</th>
			<th align="center" data-order-field="proficiencyLevel" title="掌握程度">掌握程度</th>
			<th align="center" data-order-field="otherSkillName" title="其他技能名称">其他技能名称</th>
			<th align="center" data-order-field="otherProficiencyLevel" title="其他技能掌握程度">其他技能掌握程度</th>
    		<th align="center" data-order-field="type">证书类型</th>
    		<th align="center" data-order-field="languageCredentType" title="语言证书名称">语言证书名称</th>
			<th align="center" data-order-field="name">证书名称</th>
			<th align="center" data-order-field="takeDate">发证年月</th>
			<th align="center" data-order-field="dept">颁发单位</th>
			<th align="center" data-order-field="credentNo">发证单位</th>
			<!--  
			<th align="center" data-order-field="memo">备注</th>
			<th align="center" data-order-field="seq">排序</th>
			-->
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>
    		<c:forEach items="${languageTypes }" var="items">
    		<c:if test="${items.id == item.languageType }">${items.name }</c:if>
    		</c:forEach>
    		</td>
			<td>
			<c:forEach items="${proficiencyLevels }" var="items">
    		<c:if test="${items.id == item.proficiencyLevel }">${items.name }</c:if>
    		</c:forEach>
			</td>
			<td>${item.otherSkillName }</td>
			<td>
			<c:forEach items="${otherProficiencyLevels }" var="items">
    		<c:if test="${items.id == item.otherProficiencyLevel }">${items.name }</c:if>
    		</c:forEach>
			</td>
    		<td>${item.typeName }</td>
    		<td>
    		<c:forEach items="${languageCredentTypes }" var="items">
    		<c:if test="${items.id == item.languageCredentType }">${items.name }</c:if>
    		</c:forEach>
    		</td>
			<td>${item.name }</td>
			<td><fmt:formatDate value="${item.takeDate }" pattern="yyyy-MM-dd"/></td>
			<td>${item.dept }</td>
			<td>${item.credentNo }</td>
			<!--
			<td>${item.memo }</td>
			<td>${item.seq }</td>
			-->
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>