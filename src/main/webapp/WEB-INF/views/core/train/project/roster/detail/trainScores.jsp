<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#project_roster_detail_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_roster_detail_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_project_roster_detail_add').on('click', function(e) {
	$(this).dialog({id:'project_roster_detail_form', url:'${ctx}/core/basic/teacher/base/teacherSelectList/0', type:'POST',title:'选择教师',mask:'true',max:'true',width:'600',height:'520'});
});

$('#but_project_roster_detail_delete').on('click', function(e) {
	var list = projectRosterDetail.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/roster/detail/deleteProjectRosterDetailByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",
			callback:function(){
				$('#apply_detail_search').trigger('click');
			}});
	}
});


function delRefushProjectRosterDetailTable(json){
	$('.project_roster_detail_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/roster/detail/projectRosterDetailList'});
}

//学分授予 form
function giveScoreForm(id,pid){
	 $(this).dialog({id:'teacher-score-giveform', url:'${ctx}/core/basic/teacher/score/teacherScoreForm?tid='+id+'&pid='+pid, title:'学分授予',mask:'true',width:'450',height:'220'});
}

var projectRosterDetail = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_roster_detail_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="ProjectRosterDetail:create">
			<button type="button" id="but_project_roster_detail_fr" class="btn btn-blue" data-icon="plus-circle" >打印明细单</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="project_roster_detail_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/roster/detail/trainScores" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<input type="hidden" value="${query.pid }" name="pid" class="form-control" size="10">&nbsp;
			<label>教师编号</label><input type="text" value="${query.tno }" name="tno" class="form-control" size="10">&nbsp;
			<label>教师姓名</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
			<label>状态</label><input type="text" value="${query.status }" name="status" class="form-control" size="10">&nbsp;
            <button type="submit" class="btn btn-blue" id="score_detail_search" data-icon="search">查询</button>&nbsp;
        </div>
    </form>
</div>

<div id="project_roster_detail_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
			<th align="center" data-order-field="tno">教师编号</th>
			<th align="center" data-order-field="name">教师姓名</th>
			<th align="center">学分获取状态</th>
			<th align="center">操作</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td><a href="javascript:;" onclick="teacherView(${item.id })">${item.tno }</a></td>
			<td><a href="javascript:;" onclick="teacherView(${item.id })">${item.name }</a></td>
			<td>
				<c:if test="${item.status ==0}"><span class="label label-danger">未获取</span></c:if>
				<c:if test="${item.status ==1}"><span class="label label-success">已获取</span></c:if>
				<c:if test="${item.status ==null}"><span class="label label-default">未授予</span></c:if>
			</td>
			<td>
				<button type="button" class="btn btn-dark-orange" data-icon="life-bouy" onclick="giveScoreForm(${item.id},${pid})" data-toggle="tooltip"  data-placement="top" title="学分授予"></button>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>