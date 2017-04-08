<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#project_train_org_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_train_org_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_project_train_org_add').on('click', function(e) {
	 $(this).dialog({id:'project_train_org-dialog', url:'${ctx}/core/train/org/base/trainOrgSelectList/${pid}', 
		 title:'选择选中培训机构',mask:'true',width:'1000',height:'600'});
	 
});

$('#but_project_train_org_delete').on('click', function(e) {
	var list = projectTrainOrg.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/trainorg/deleteProjectTrainOrgByIds',
			idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProjectTrainOrgTable"});
	}
});

function delRefushProjectTrainOrgTable(json){
	$('.project_train_org_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/trainorg/projectTrainOrgList'});
}

function orgView(id){
	  $(this).dialog({id:'org-view-dialog', url:'${ctx}/core/train/org/base/trainOrgView/'+id, title:'培训机构详细',mask:'true',width:'400',height:'480'});
}

var projectTrainOrg = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_train_org_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="Project:conf">
			<button type="button" id="but_project_train_org_add" class="btn btn-blue" data-icon="plus-circle" >增加培训机构</button>
			<button type="button" id="but_project_train_org_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="project_train_org_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/trainorg/projectTrainOrgList/${pid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>机构名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>机构来源</label>
			<select name="userType" data-toggle="selectpicker"  data-width="100px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${orgEnums }" var="item">
			               		<option  value="${item.value }" <c:if test="${item.value==query.userType}">selected</c:if>>${item.desc }</option>
			               	</c:forEach>
						</select>&nbsp;
            <button type="submit" class="btn btn-blue" id="train-org-addlist-search" data-icon="search">查询</button>&nbsp;
        </div>
        <div class="bjui-moreSearch pull-right">
        <!-- 
        	<label>pid</label><input type="text" value="${query.pid }" name="pid" class="form-control" size="10">&nbsp;
			<label>orgid</label><input type="text" value="${query.orgid }" name="orgid" class="form-control" size="10">&nbsp;
		-->
        </div>
    </form>
</div>

<div id="project_train_org_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="orgid">机构名称</th>
			<th align="center" >机构账号</th>
			<th align="center" >类型</th>
			<th align="center" >机构来源</th>
			<th align="center" >级别</th>
			<th align="center" >地址</th>
			<th align="center" >联系人</th>
			<th align="center" >电话</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td><a href="javascript:;" onclick="orgView(${item.orgid })">${item.name }</a></td>
			<td>${item.orgNo }</td>
			<td>${item.typeName }</td>
			<td>
				<c:if test="${item.userType ==2}"><span class="label label-danger">省级</span></c:if>
    			<c:if test="${item.userType ==3}"><span class="label label-info">市级</span></c:if>
    			<c:if test="${item.userType ==4}"><span class="label label-primary">区县级</span></c:if>
    			<c:if test="${item.userType ==5}"><span class="label label-warning">校级</span></c:if>
			</td>
			<td>${item.levelName }</td>
			<td>${item.address }</td>
			<td>${item.linkman }</td>
			<td>${item.tel }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>