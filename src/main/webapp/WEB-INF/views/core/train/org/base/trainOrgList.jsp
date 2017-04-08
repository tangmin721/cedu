<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#train_org_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#train_org_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_train_org_add').on('click', function(e) {
	$(this).dialog({id:'train_org_form', url:'${ctx}/core/train/org/base/trainOrgForm', type:'POST',title:'添加培训机构',mask:'true',width:'580',height:'430'});
});

$('#but_train_org_edit').on('click', function(e) {
	var list = trainOrg.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'train_org_form', url:'${ctx}/core/train/org/base/trainOrgForm/'+id, type:'POST',title:'编辑培训机构',mask:'true',width:'580',height:'430'});
	}
});

function trainOrgView(id){
	  $(this).dialog({id:'org-view-dialog-view', url:'${ctx}/core/train/org/base/trainOrgView/'+id, title:'培训机构详细',mask:'true',width:'400',height:'420'});
}

$('#but_train_org_delete').on('click', function(e) {
	var list = trainOrg.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/org/base/deleteTrainOrgByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTrainOrgTable"});
	}
});

function delRefushTrainOrgTable(json){
	$('.train_org_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/org/base/trainOrgList'});
}


var trainOrg = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#train_org_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TrainOrg:create">
			<button type="button" id="but_train_org_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TrainOrg:update">
			<button type="button" id="but_train_org_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TrainOrg:delete">
			<button type="button" id="but_train_org_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="train_org_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/org/base/trainOrgList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#steacher_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="steacher_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#steacher_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="steacher_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#steacher_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="steacher_list_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
			<label>名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>类型</label>
			<select name="type" data-toggle="selectpicker"  data-width="100px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${orgTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==query.type}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
			<!--  
			<label>级别</label>
			 <select name="level" data-toggle="selectpicker"  data-width="100px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${orgLevels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==query.level}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
			-->
			 <label>机构来源</label>
			 <select name="userType" data-toggle="selectpicker"  data-width="100px">
			 	<option value="">--请选择--</option>
			 	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN">
			    <option  value="2" <c:if test="${query.userType == 2}">selected</c:if>>省级</option>
			    </shiro:hasAnyRoles>
			    <shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN">
			    <option  value="3" <c:if test="${query.userType == 3}">selected</c:if>>市级</option>
			    </shiro:hasAnyRoles>
			    <shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN">
			    <option  value="4" <c:if test="${query.userType == 4}">selected</c:if>>区县级</option>
			    </shiro:hasAnyRoles>
			    <shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
			    <option  value="5" <c:if test="${query.userType == 5}">selected</c:if>>校级</option>
			    </shiro:hasAnyRoles>
			 </select>&nbsp;
			 <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
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

<div id="train_org_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
			<th align="center" data-order-field="name">名称</th>
			<th align="center" data-order-field="orgNo">登录账号</th>
			<th align="center" data-order-field="type">类型</th>
			<th align="center" data-order-field="userType">机构来源</th>
			<th align="center" data-order-field="level">级别</th>
			<th align="center" data-order-field="postCode">邮编</th>
			<th align="center" data-order-field="linkman">联系人</th>
			<th align="center" data-order-field="tel">电话</th>
			<th align="center" data-order-field="phone">手机</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td><a href="javascript:;" onclick="trainOrgView(${item.id })">${item.name }</a></td>
			<td>${item.orgNo }</td>
			<td>${item.typeName }</td>
			<td>
				<c:if test="${item.userType ==2}"><span class="label label-danger">省级</span></c:if>
    			<c:if test="${item.userType ==3}"><span class="label label-info">市级</span></c:if>
    			<c:if test="${item.userType ==4}"><span class="label label-primary">区县级</span></c:if>
    			<c:if test="${item.userType ==5}"><span class="label label-warning">校级</span></c:if>
			</td>
			<td>${item.levelName }</td>
			<td>${item.postCode }</td>
			<td>${item.linkman }</td>
			<td>${item.tel }</td>
			<td>${item.phone }</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>