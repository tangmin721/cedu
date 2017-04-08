<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div style="height:100%;width:100%;">
<iframe id="reportFrame" height="100%" width="100%" frameborder="0"
	src="${fr_url }/ReportServer?reportlet=/teacher/checkStatistics.cpt&pid=${pid}">
</iframe>  
</div>