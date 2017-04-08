<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_score_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_score_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_score_add').on('click', function(e) {
	$(this).dialog({id:'teacher_score_form', url:'${ctx}/core/basic/teacher/score/teacherScoreForm', type:'POST',title:'添加学时学分',mask:'true',width:'600',height:'520'});
});

$('#but_teacher_score_edit').on('click', function(e) {
	var list = teacherScore.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_score_form', url:'${ctx}/core/basic/teacher/score/teacherScoreForm/'+id, type:'POST',title:'编辑学时学分',mask:'true',width:'600',height:'520'});
	}
});

$('#but_teacher_score_delete').on('click', function(e) {
	var list = teacherScore.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/score/deleteTeacherScoreByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherScoreTable"});
	}
});

function delRefushTeacherScoreTable(json){
	$('.teacher_score_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/score/teacherScoreList'});
}


var teacherScore = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_score_table input[name="ids"]').each(function(){ 
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
	<!-- <div class="btn-group pull-left" role="group">
		<shiro:hasPermission name="TeacherScore:create">
			<button type="button" id="but_teacher_score_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherScore:update">
			<button type="button" id="but_teacher_score_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherScore:delete">
			<button type="button" id="but_teacher_score_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div> -->
    <form id="pagerForm" class="teacher_score_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/score/teacherScoreList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" value="${query.tid }" name="tid" >
        <!-- 
        <div class="bjui-searchBar pull-right">
			<label>获得状态</label><input type="text" value="${query.status }" name="status" class="form-control" size="10">&nbsp;
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
        </div>
         -->
    </form>
</div>

<div id="teacher_score_table" class="tableContent">
     <table class="table table-bordered table-hover table-striped table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th align="center" data-order-field="type" title="培训类型">培训类型</th>
    		<th align="center" data-order-field="scoreType" title="学时种类">学时种类</th>
    		<th align="center" data-order-field="name" title="名称">名称</th>
    		<th align="center" data-order-field="year" title="年度">年度</th>
			<th align="center" data-order-field="score" title="学时">申报学时</th>
			<th align="center" data-order-field="createTime" title="申报时间">申报时间</th>
			<th align="center" data-order-field="status" title="状态">状态</th>
			<th align="center" data-order-field="checkDesc" title="审核时间">审核时间</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr>
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
    		</td>
    		<td>${item.name }</td>
    		<td>${item.yearName }</td>
			<td>${item.score }</td>
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