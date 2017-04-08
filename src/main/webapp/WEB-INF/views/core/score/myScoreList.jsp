<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#score_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#score_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#butscore_add_1').on('click', function(e) {
	$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm', type:'POST',title:'添加项目学时申报',mask:'true',width:'760',height:'520'});
});

$('#butscore_edit').on('click', function(e) {
	var list = score.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm/'+id, type:'POST',title:'编辑学时申报',mask:'true',width:'760',height:'520'});
	}
});

$('#butscore_delete').on('click', function(e) {
	var list = score.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/score/deleteScoreByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushScoreTable"});
	}
});

function delRefushScoreTable(json){
	$('.score_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/score/scoreList'});
}

//学时申报
function addScore(tid,type){
	switch (type)
	{
	case 1:
	    $(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm1', type:'POST',title:'添加【项目学时】申报',mask:'true',width:'760',height:'340'});
	  break;
	case 2:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm2', type:'POST',title:'添加【学历提升】申报',mask:'true',width:'760',height:'340'});
	  break;
	case 3:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm3', type:'POST',title:'添加【校本研修】申报',mask:'true',width:'760',height:'200'});
	  break;
	case 4:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm4', type:'POST',title:'添加【论文/专著】申报',mask:'true',width:'760',height:'420'});
	  break;
	case 5:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm5', type:'POST',title:'添加【学术交流】申报',mask:'true',width:'760',height:'340'});
	  break;
	case 6:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm6', type:'POST',title:'添加【课题研究】申报',mask:'true',width:'760',height:'330'});
	  break;
	case 7:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm7', type:'POST',title:'添加【其他】申报',mask:'true',width:'760',height:'210'});
	  break;
	case 8:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm8', type:'POST',title:'添加【自主研修】申报',mask:'true',width:'760',height:'510'});
	  break;
	}
}

function editScore(id,type){
	switch (type)
	{
	case 1:
	    $(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm1/'+id, type:'POST',title:'编辑【项目学时】申报',mask:'true',width:'760',height:'340'});
	  break;
	case 2:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm2/'+id, type:'POST',title:'编辑【学历提升】申报',mask:'true',width:'760',height:'340'});
	  break;
	case 3:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm3/'+id, type:'POST',title:'编辑【校本研修】申报',mask:'true',width:'760',height:'200'});
	  break;
	case 4:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm4/'+id, type:'POST',title:'编辑【论文/专著】申报',mask:'true',width:'760',height:'420'});
	  break;
	case 5:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm5/'+id, type:'POST',title:'编辑【学术交流】申报',mask:'true',width:'760',height:'340'});
	  break;
	case 6:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm6/'+id, type:'POST',title:'编辑【课题研究】申报',mask:'true',width:'760',height:'330'});
	  break;
	case 7:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm7/'+id, type:'POST',title:'编辑【其他】申报',mask:'true',width:'760',height:'210'});
	  break;
	case 8:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm8/'+id, type:'POST',title:'编辑【自主研修】申报',mask:'true',width:'760',height:'510'});
	  break;
	}
}

var score = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#score_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}

function deleteScore(id){
	$(this).bjuiajax('doAjax', {url:'${ctx}/core/score/deleteScoreById/'+id,confirmMsg:"确定要删除吗？",callback:"delRefushScoreTable"});
}

function viewMyScore(id, scoreType){
	switch (scoreType)
	{
	case 1:
	    $(this).dialog({id:'score_view', url:'${ctx}/core/score/scoreView1/'+id, type:'POST',title:'【项目学时】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 2:
		$(this).dialog({id:'score_view', url:'${ctx}/core/score/scoreView2/'+id, type:'POST',title:'【学历提升】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 3:
		$(this).dialog({id:'score_view', url:'${ctx}/core/score/scoreView3/'+id, type:'POST',title:'【校本研修】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 4:
		$(this).dialog({id:'score_view', url:'${ctx}/core/score/scoreView4/'+id, type:'POST',title:'【论文/专著】详细信息',mask:'true',width:'420',height:'420'});
	  break;
	case 5:
		$(this).dialog({id:'score_view', url:'${ctx}/core/score/scoreView5/'+id, type:'POST',title:'【学术交流】详细信息',mask:'true',width:'420',height:'300'});
	  break;
	case 6:
		$(this).dialog({id:'score_view', url:'${ctx}/core/score/scoreView6/'+id, type:'POST',title:'【课题研究】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 7:
		$(this).dialog({id:'score_view', url:'${ctx}/core/score/scoreView7/'+id, type:'POST',title:'【其他】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 8:
		$(this).dialog({id:'score_view', url:'${ctx}/core/score/scoreView8/'+id, type:'POST',title:'【自主研修】详细信息',mask:'true',width:'760',height:'510'});
	  break;
	}
}

</script>

<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<shiro:hasPermission name="Score:create">
	            <button type="button" class="btn-dark-blue dropdown-toggle" data-toggle="dropdown" data-icon="plus-square">学时申报<span class="caret"></span></button>
	            <ul class="dropdown-menu left" role="menu">
	            	<li class="dropdown-header">集中培训</li>
	                <li><a href="javascript:addScore(${tid },1);">&nbsp;<span style="color: green;" class="fa fa-plus-square"></span>&nbsp; 申报<span style="color: red;">项目学时</span></a></li>
	                <li><a href="javascript:addScore(${tid },2);">&nbsp;<span style="color: green;" class="fa fa-plus-square"></span>&nbsp; 申报<span style="color: red;">学历提升</span></a></li>
	                <li class="divider"></li>
	                <li class="dropdown-header">非集中培训</li>
	                <li><a href="javascript:addScore(${tid },3);">&nbsp;<span style="color: green;" class="fa fa-plus-square"></span>&nbsp; 申报<span style="color: blue;">校本研修</span></a></li>
	           		<li><a href="javascript:addScore(${tid },4);">&nbsp;<span style="color: green;" class="fa fa-plus-square"></span>&nbsp; 申报<span style="color: blue;">论文/专著</span></a></li>
	           		<li><a href="javascript:addScore(${tid },5);">&nbsp;<span style="color: green;" class="fa fa-plus-square"></span>&nbsp; 申报<span style="color: blue;">学术交流</span></a></li>
	           		<li><a href="javascript:addScore(${tid },6);">&nbsp;<span style="color: green;" class="fa fa-plus-square"></span>&nbsp; 申报<span style="color: blue;">课题研究</span></a></li>
	           		<li><a href="javascript:addScore(${tid },8);">&nbsp;<span style="color: green;" class="fa fa-plus-square"></span>&nbsp; 申报<span style="color: blue;">自主研修</span></a></li>
	           		<li><a href="javascript:addScore(${tid },7);">&nbsp;<span style="color: green;" class="fa fa-plus-square"></span>&nbsp; 申报<span style="color: blue;">其他</span></a></li>	
	            </ul>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="score_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/score/myScoreList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<label>培训类型：</label>
			 	<input type="radio" name="type" id="f_score_type1" data-toggle="icheck" value="0" <c:if test="${query.type==0}">checked</c:if> data-label="集中培训&nbsp;&nbsp;">
                <input type="radio" name="type" id="f_score_type2" data-toggle="icheck" value="1" <c:if test="${query.type==1}">checked</c:if> data-label="非集中培训">&nbsp;&nbsp;&nbsp;&nbsp;
            
            <label>学时种类：</label>
			<select name="scoreType" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${scoreTypes }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.scoreType}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;&nbsp;   
			
			<label>年度：</label>
			<select name="year" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${years }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.year}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
             
			<label>状态：</label>
			<select name="status" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${scoreStatus }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.status}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;&nbsp;
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        </div>
    </form>
</div>

<div id="score_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
    		<th width="66" align="center" data-order-field="type" title="培训类型">培训类型</th>
    		<th width="66" align="center" data-order-field="scoreType" title="学时种类">学时种类</th>
    		<th align="center" data-order-field="name" title="名称">名称</th>
    		<th align="center" data-order-field="year" title="年度">年度</th>
			<th align="center" data-order-field="score" title="学时">申报学时</th>
			<th align="center" data-order-field="score" title="学分">申报学分</th>
			<th width="66" align="center" data-order-field="createTime" title="申报时间">申报时间</th>
			<th width="66" align="center" data-order-field="status" title="状态">状态</th>
			<th width="66" align="center" data-order-field="checkDesc" title="审核时间">审核时间</th>
			<th align="center" title="操作">操作</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
    		<td>
    			<c:if test="${item.type ==0}"><span class="label label-success">集中培训</span></c:if>
				<c:if test="${item.type ==1}"><span class="label label-primary">非集中培训</span></c:if>
    		</td>
    		<td>
    			<c:if test="${item.scoreType ==1}"><span class="label label-info">项目学时</span></c:if>
    			<c:if test="${item.scoreType ==2}"><span class="label label-info">学历提升</span></c:if>
    			<c:if test="${item.scoreType ==3}"><span class="label label-info">校本研修</span></c:if>
    			<c:if test="${item.scoreType ==4}"><span class="label label-info">论文/专著</span></c:if>
    			<c:if test="${item.scoreType ==5}"><span class="label label-info">学术交流</span></c:if>
    			<c:if test="${item.scoreType ==6}"><span class="label label-info">课题研究</span></c:if>
    			<c:if test="${item.scoreType ==7}"><span class="label label-info">其他</span></c:if>
    			<c:if test="${item.scoreType ==8}"><span class="label label-info">自主研修</span></c:if>
    		</td>
    		<td>
    		<a href="javascript:;" onclick="viewMyScore(${item.id },${item.scoreType})">${item.name }</a>
    		</td>
    		<td>${item.yearName }</td>
			<td>
				<c:if test="${not empty item.schoolUpdateMemo or not empty item.townUpdateMemo}">
					<a href="javascript:;" data-toggle="tooltip"  data-placement="right"
					   title='管理员修改说明：<c:if test="${not empty item.schoolUpdateMemo}">【学校管理员修改原因:${item.schoolUpdateMemo}】</c:if>
					   		  <c:if test="${not empty item.townUpdateMemo}">【区县管理员管理员修改原因:${item.townUpdateMemo}】</c:if>' >${item.score }</a>
				</c:if>
				<c:if test="${empty item.schoolUpdateMemo and  empty item.townUpdateMemo}">
					${item.score }
				</c:if>
			</td>
			<td>${item.scoreNum }</td>
			<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd"/></td>
			<td>
				<c:if test="${item.status ==10}"><span class="label label-warning">校级待审</span></c:if>
    			<c:if test="${item.status ==11}"><span class="label label-danger" data-toggle="tooltip"  data-placement="right" title="不通过原因:${item.checkDesc}" >校级不通过</span></c:if>
    			<c:if test="${item.status ==20}"><span class="label label-warning">区县待审</span></c:if>
    			<c:if test="${item.status ==21}"><span class="label label-danger" data-toggle="tooltip"  data-placement="right" title="不通过原因:${item.checkDesc}" >区县不通过</span></c:if>
    			<c:if test="${item.status ==30}"><span class="label label-warning">省级待审</span></c:if>
    			<c:if test="${item.status ==31}"><span class="label label-danger" data-toggle="tooltip"  data-placement="right" title="不通过原因:${item.checkDesc}" >省级不通过</span></c:if>
    			<c:if test="${item.status ==40}"><span class="label label-success">学时认证完成</span></c:if>
			</td>
			<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
			<td>
				<shiro:hasPermission name="Score:update">
					<c:if test="${item.status ==10 or item.status ==11 or item.status ==21 or item.status ==31}">
						<button type="button" class="btn btn-green" data-icon="edit" onclick="editScore(${item.id },${item.scoreType})" data-toggle="tooltip"  data-placement="top" title="编辑">编辑</button>
					</c:if>
				</shiro:hasPermission>
				<shiro:hasPermission name="Score:delete">
					<c:if test="${item.status ==10 or item.status ==11 or item.status ==21 or item.status ==31}">
						<button type="button"  class="btn btn-red" data-icon="times-circle" onclick="deleteScore(${item.id })" >删除</button>
					</c:if>
				</shiro:hasPermission>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>