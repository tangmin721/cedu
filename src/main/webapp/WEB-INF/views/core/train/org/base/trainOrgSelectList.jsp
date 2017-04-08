<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#train_org_select_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#train_org_select_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});


function orgView(id){
	  $(this).dialog({id:'org-view-dialog', url:'${ctx}/core/train/org/base/trainOrgView/'+id, title:'培训机构详细',mask:'true',width:'400',height:'420'});
}


$('#but_train_org_add').on('click', function(e) {
	var list = train_select_org.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要添加的培训机构！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/trainorg/saveProjectTrainOrg?pid=${pid}',idname:"ids",group:"ids",confirmMsg:"确定要加入到项目的培训机构名单中吗？",
				callback:function(){
					$(this).dialog('close','project_train_org-dialog');
					$('#train-org-addlist-search').trigger('click');
				}});
	}
});


var train_select_org = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#train_org_select_table input[name="ids"]').each(function(){ 
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
				<button type="button" id="but_train_org_add" class="btn btn-blue" data-icon="check-square-o" >选择选中</button>
			</shiro:hasPermission>
		</div>
		<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
		</div>
    <form id="pagerForm" class="train_org_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/org/base/trainOrgSelectList/${pid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>类型</label>
			<select name="type" data-toggle="selectpicker"  data-width="100px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${orgTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==query.type}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
			
			<label>级别</label>
			 <select name="level" data-toggle="selectpicker"  data-width="100px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${orgLevels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==query.level}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
			<label>机构来源</label>
			<select name="userType" data-toggle="selectpicker"  data-width="100px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${orgEnums }" var="item">
			               		<option  value="${item.value }" <c:if test="${item.value==query.userType}">selected</c:if>>${item.desc }</option>
			               	</c:forEach>
						</select>&nbsp;
			 <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
        </div>
        <div class="bjui-moreSearch pull-right">
       		<label>地址</label><input type="text" value="${query.address }" name="address" class="form-control" size="10">&nbsp;
        	<label>邮编</label><input type="text" value="${query.postCode }" name="postCode" class="form-control" size="10">&nbsp;
			<label>联系人</label><input type="text" value="${query.linkman }" name="linkman" class="form-control" size="10">&nbsp;
			<label>联系人身份证</label><input type="text" value="${query.idCard }" name="idCard" class="form-control" size="10">&nbsp;
			<label>电话</label><input type="text" value="${query.tel }" name="tel" class="form-control" size="10">&nbsp;
			<label>手机</label><input type="text" value="${query.phone }" name="phone" class="form-control" size="10">&nbsp;
			<label>Email</label><input type="text" value="${query.email }" name="email" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="train_org_select_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="name">名称</th>
			<th align="center" data-order-field="type">类型</th>
			<th align="center" data-order-field="userType">机构来源</th>
			<th align="center" data-order-field="level">级别</th>
			<th align="center" data-order-field="address">地址</th>
			<th align="center" data-order-field="linkman">联系人</th>
			<th align="center" data-order-field="tel">电话</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td><a href="javascript:;" onclick="orgView(${item.id })">${item.name }</td>
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