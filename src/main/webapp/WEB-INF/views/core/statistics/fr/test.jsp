<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div style="height:100%;width:100%;">
<iframe id="reportFrame" height="100%" width="100%" frameborder="0"
	src="${ctx }/ReportServer?reportlet=/teacher/teacher.cpt&id=${id}">
</iframe>  
</div>