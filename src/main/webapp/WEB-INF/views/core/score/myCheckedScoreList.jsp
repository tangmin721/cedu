<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#my_scoretable').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#my_scoretable').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

function viewMyCheckedScore(id, scoreType){
	switch (scoreType)
	{
	case 1:
	    $(this).dialog({id:'checked_score_view', url:'${ctx}/core/score/scoreView1/'+id, type:'POST',title:'【项目学时】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 2:
		$(this).dialog({id:'checked_score_view', url:'${ctx}/core/score/scoreView2/'+id, type:'POST',title:'【学历提升】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 3:
		$(this).dialog({id:'checked_score_view', url:'${ctx}/core/score/scoreView3/'+id, type:'POST',title:'【校本研修】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 4:
		$(this).dialog({id:'checked_score_view', url:'${ctx}/core/score/scoreView4/'+id, type:'POST',title:'【论文/专著】详细信息',mask:'true',width:'420',height:'420'});
	  break;
	case 5:
		$(this).dialog({id:'checked_score_view', url:'${ctx}/core/score/scoreView5/'+id, type:'POST',title:'【学术交流】详细信息',mask:'true',width:'420',height:'300'});
	  break;
	case 6:
		$(this).dialog({id:'checked_score_view', url:'${ctx}/core/score/scoreView6/'+id, type:'POST',title:'【课题研究】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 7:
		$(this).dialog({id:'checked_score_view', url:'${ctx}/core/score/scoreView7/'+id, type:'POST',title:'【其他】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 8:
		$(this).dialog({id:'checked_score_view', url:'${ctx}/core/score/scoreView8/'+id, type:'POST',title:'【自主研修】详细信息',mask:'true',width:'560',height:'510'});
	  break;
	}
}

</script>

<div class="bjui-pageHeader">
    <form id="pagerForm" class="my_scorepager_form" data-toggle="ajaxsearch" action="${ctx}/core/score/myCheckedScoreList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<label>培训类型：</label>
			 	<input type="radio" name="type" id="f_my_scoretype1" data-toggle="icheck" value="0" <c:if test="${query.type==0}">checked</c:if> data-label="集中培训&nbsp;&nbsp;">
                <input type="radio" name="type" id="f_my_scoretype2" data-toggle="icheck" value="1" <c:if test="${query.type==1}">checked</c:if> data-label="非集中培训">&nbsp;&nbsp;&nbsp;&nbsp;
            
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
             
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        </div>
    </form>
</div>

<div id="my_scoretable" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
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
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
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
    			<a href="javascript:;" onclick="viewMyCheckedScore(${item.id },${item.scoreType})">${item.name }</a>
    		</td>
    		<td>${item.yearName }</td>
			<td>${item.score }</td>
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
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>