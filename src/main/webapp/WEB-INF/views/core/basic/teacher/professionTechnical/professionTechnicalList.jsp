<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#profession_technical_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#profession_technical_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_profession_technical_add').on('click', function(e) {
	$(this).dialog({id:'profession_technical_form', url:'${ctx}/core/basic/teacher/professionTechnical/professionTechnicalForm?tid=${tid}', type:'POST',title:'添加专业技术职务聘任',mask:'true',width:'450',height:'300'});
});

$('#but_profession_technical_edit').on('click', function(e) {
	var list = professionTechnical.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'profession_technical_form', url:'${ctx}/core/basic/teacher/professionTechnical/professionTechnicalForm/'+id, type:'POST',title:'编辑专业技术职务聘任',mask:'true',width:'450',height:'300'});
	}
});

$('#but_profession_technical_delete').on('click', function(e) {
	var list = professionTechnical.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/professionTechnical/deleteProfessionTechnicalByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProfessionTechnicalTable"});
	}
});

function delRefushProfessionTechnicalTable(json){
	$('.profession_technical_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/professionTechnical/professionTechnicalList/${tid}'});
}


var professionTechnical = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#profession_technical_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="ProfessionTechnical:create">
			<button type="button" id="but_profession_technical_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProfessionTechnical:update">
			<button type="button" id="but_profession_technical_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProfessionTechnical:delete">
			<button type="button" id="but_profession_technical_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="profession_technical_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/professionTechnical/professionTechnicalList/${tid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${tid}">
    </form>
</div>

<div id="profession_technical_table" class="tableContent">
     <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="professionDuty" title="聘任专业技术职务">聘任专业技术职务</th>
			<th align="center" data-order-field="startDate" title="聘任开始年月">聘任开始年月</th>
			<th align="center" data-order-field="endDate" title="聘任结束年月">聘任结束年月</th>
			<th align="center" data-order-field="unitName" title="聘任单位名称">聘任单位名称</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>
			<c:forEach items="${professionDutys }" var="items">
			  <c:if test="${items.id==item.professionDuty}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td><fmt:formatDate value="${item.startDate }" pattern="yyyy-MM-dd" /> </td>
			<td><fmt:formatDate value="${item.endDate }" pattern="yyyy-MM-dd" /></td>
			<td>${item.unitName }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>