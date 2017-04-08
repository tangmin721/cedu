<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">


$('#f_project_roster_check').bootstrapSwitch();

</script>

<div class="bjui-pageHeader">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-3px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_project_roster_rosterNo" class="control-label x100">单号：</label>
                        <input type="text" name="rosterNo" id="f_project_roster_rosterNo" value="${entity.rosterNo }" disabled maxlength="20" size="20">
                    </td>
                </tr>
                <tr>
                	<td>
                        <label for="f_project_roster_reportDate" class="control-label x100"><span class="red">*</span>上报时间：</label>
                        <input type="text" name="reportDate" id="f_project_roster_reportDate" value='<fmt:formatDate value="${entity.reportDate }" pattern="yyyy-MM-dd HH:mm:ss"/>' disabled   maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_roster_applyUserId" class="control-label x100"><span class="red">*</span>申请人：</label>
                        <input type="text" name="applyUserId" id="f_project_roster_applyUserId" value="${entity.applyUserName }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_roster_memo" class="control-label x100"><span class="red">*</span>备注：</label>
                        <input type="text" name="memo" id="f_project_roster_memo" value="${entity.memo }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    	 <form id="pagerForm" class="project_roster_detail_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/roster/checkView/${applyId}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<input type="hidden" value="${query.rosterId }" name="rosterId" class="form-control" size="10">&nbsp;
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
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td><a href="javascript:;" onclick="teacherView(${item.tid })">${item.tno }</a></td>
			<td><a href="javascript:;" onclick="teacherView(${item.tid })">${item.name }</a></td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>