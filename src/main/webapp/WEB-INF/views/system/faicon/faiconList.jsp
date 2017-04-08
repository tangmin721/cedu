<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="bjui-pageHeader">
    <form id="pagerForm" class="faicon_pager_form" data-toggle="ajaxsearch" action="${ctx}/system/faicon/faiconList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<label>名称：</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
            
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>

<div id="faicon_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26">No.</th>
			<th align="center" data-order-field="name">图标名称</th>
			<th align="center" data-order-field="name">图标样式</th>
			<th width="74">操作</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td>${index.count+page.pageStart}</td>
			<td>${item.name }</td>
			<td><i class="fa fa-${item.name}"></i></td>
			<td>
                <a href="javascript:;" data-toggle="lookupback" data-args="{faicon:'${item.name}'}" class="btn btn-blue" title="选择本项" data-icon="check">选择</a>
            </td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page-lookup.jsp"%>