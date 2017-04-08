<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

		<button type="button" class="btn btn-dark-blue" data-icon="file-word-o" onclick="fileView(${item.id})" data-toggle="tooltip"  data-placement="top" title="机构材料查看"></button>
		<button type="button" class="btn btn-dark-orange" data-icon="users" onclick="personManager(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人员与成绩管理"></button>