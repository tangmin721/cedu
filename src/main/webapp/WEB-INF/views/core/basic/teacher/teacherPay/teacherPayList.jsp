<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_pay_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_pay_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_pay_add').on('click', function(e) {
	$(this).dialog({id:'teacher_pay_form', url:'${ctx}/core/basic/teacher/teacherPay/teacherPayForm?tid=${tid}&tfs_type=${tfs_type}', type:'POST',title:'添加基本待遇',mask:'true',width:'625',height:'420'});
});

$('#but_teacher_pay_edit').on('click', function(e) {
	var list = teacherPay.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_pay_form', url:'${ctx}/core/basic/teacher/teacherPay/teacherPayForm/'+id+'/${tfs_type}', type:'POST',title:'编辑基本待遇',mask:'true',width:'625',height:'420'});
	}
});

$('#but_teacher_pay_delete').on('click', function(e) {
	var list = teacherPay.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/teacherPay/deleteTeacherPayByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherPayTable"});
	}
});

function delRefushTeacherPayTable(json){
	$('.teacher_pay_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherPay/teacherPayList/${tid}/${tfs_type}'});
}


var teacherPay = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_pay_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherPay:create">
			<button type="button" id="but_teacher_pay_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherPay:update">
			<button type="button" id="but_teacher_pay_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherPay:delete">
			<button type="button" id="but_teacher_pay_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_pay_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/teacherPay/teacherPayList/${tid}/${tfs_type}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="teacher_pay_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="seq" title="序号">序号</th>
			<th align="center" data-order-field="year" title="年度">年度</th>
			<th align="center" data-order-field="yearPay" title="年工资收入(元)">年工资收入(元)</th>
			<th align="center" data-order-field="basicPay" title="基本工资(元/月)">基本工资(元/月)</th>
			<th align="center" data-order-field="meritPay" title="绩效工资(元/月)">绩效工资(元/月)</th>
			<c:if test="${tfs_type == 0 }">
			<th align="center" data-order-field="countrySubsidy" title="乡村教师生活补助(元/月)">乡村教师生活补助(元/月)</th>
			</c:if>
			<c:if test="${tfs_type == 1 || tfs_type == 3 || tfs_type == 2}">
			<th align="center" data-order-field="subsidy" title="津贴补贴(元/月)">
			<c:choose>
       			<c:when test="${tfs_type == 2 }">
					特教津贴补贴(元/月)
       			</c:when>
       			<c:otherwise>
					津贴补贴(元/月)
       			</c:otherwise>
			</c:choose>
			</th>
			</c:if>
			<c:if test="${tfs_type == 0 || tfs_type == 2}">
			<th align="center" data-order-field="otherSubsidy" title="其他津贴补贴(元/月)">其他津贴补贴(元/月)</th>
			</c:if>
			<th align="center" data-order-field="otherPay" title="其他(元/月)">其他(元/月)</th>
			<th align="center" data-order-field="insuranceHousFund" title="五险一金">五险一金</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.seq }</td>
			<td>
			<c:forEach items="${years }" var="items">
			<c:if test="${item.year == items.id }">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.yearPay }</td>
			<td>${item.basicPay }</td>
			<td>${item.meritPay }</td>
			<c:if test="${tfs_type == 0 }">
			<td>${item.countrySubsidy }</td>
			</c:if>
			<c:if test="${tfs_type == 1 || tfs_type == 3 || tfs_type == 2}">
			<td>${item.subsidy }</td>
			</c:if>
			<c:if test="${tfs_type == 0 || tfs_type == 2}">
			<td>${item.otherSubsidy }</td>
			</c:if>
			<td>${item.otherPay }</td>
			<td>${item.insuranceHousFund }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>