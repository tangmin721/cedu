<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<style type="text/css">
.project_view_style {
font-size:12px;
font-weight:bold;
width:15%;
}
</style>

<script type="text/javascript">
$('#f_project_confirmFlag').bootstrapSwitch();
</script>

<div class="bjui-pageContent">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="optType" value="${optType}">
        <table class="table table-condensed table-hover table-bordered " style="width:100%;margin-top:-1px;">
            <tbody>
				<tr style="height: 30px">
				 	<td align="center" class="project_view_style">项目名称</td>
                    <td colspan="3" style="width:75%;word-wrap:break-word;word-break:break-all;">${entity.name }</td>
                </tr>
                <tr style="height: 30px">
                	<td align="center" class="project_view_style">项目编号</td>
                    <td colspan="3" style="width:75%;word-wrap:break-word;word-break:break-all;">${entity.pno }</td>
                </tr>
                <tr style="height: 30px">
                	<td align="center" class="project_view_style">项目类别</td>
                	 <td>
			              <c:forEach items="${trainLevels }" var="item">
			               		<c:if test="${item.id==entity.trainLevel}">${item.name }</c:if>
			              </c:forEach>
                    </td>
                    <td align="center" class="project_view_style">项目类型</td>
                    <td>
			        	<c:forEach items="${trainTypes }" var="item">
			               	<c:if test="${item.id==entity.trainType}">${item.name }</c:if>
			            </c:forEach>
                    </td>
                </tr>
				 <tr style="height: 30px">
				 <td align="center" class="project_view_style">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;段</td>
                    <td>
			            <c:forEach items="${stages }" var="item">
			               	<c:if test="${item.id==entity.stage}">${item.name }</c:if>
			            </c:forEach>
                    </td>
                    <td align="center" class="project_view_style">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科</td>
                     <td>
			            <c:forEach items="${courses }" var="item">
			               <c:if test="${item.id==entity.course}">${item.name }</c:if>
			            </c:forEach>
                    </td>
                </tr>
				 <tr style="height: 30px">
				 <td align="center" class="project_view_style">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度</td>
                    <td>
			            <c:forEach items="${schoolYears }" var="item">
			               	<c:if test="${item.id==entity.schoolYear}">${item.name }</c:if>
			            </c:forEach>
                    </td>
                    <td align="center" class="project_view_style">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时</td>
                     <td>
                        <fmt:formatNumber value="${entity.hour }" pattern="#.##"/>
                    </td>
                </tr>
                <tr style="height: 30px">
                  	<td align="center" class="project_view_style">优秀所获学时</td>
                     <td>
                        <fmt:formatNumber value="${entity.goodScore }" pattern="#.##"/>
                    </td>
                    <td align="center" class="project_view_style">合格所获学时</td>
                     <td>
                        <fmt:formatNumber value="${entity.passScore }" pattern="#.##"/>
                    </td>
                </tr>
				 <tr style="height: 30px">
				 <td align="center" class="project_view_style">开始时间</td>
                    <td>
                        <fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>
                    </td>
                    <td align="center" class="project_view_style">结束时间</td>
                    <td>
                        <fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>
                    </td>
                </tr>
				 <tr style="height: 30px">
				 	<td align="center" class="project_view_style">项目内容</td>
                    <td colspan="3" style="width:75%;word-wrap:break-word;word-break:break-all;">${entity.description}</td>
                </tr>
                <tr style="height: 30px">
				 	<td align="center"  class="project_view_style">面向岗位</td>
                    <td colspan="3">
                   		<c:forEach items="${faceDeptses }" var="item">
		               		<c:if test="${fn:contains(entity.faceDepts, item.id)}">${item.name }</c:if>
		               	</c:forEach>
                    </td>
                </tr>
                <tr style="height: 30px">
				 	<td align="center"  class="project_view_style">面向学段</td>
                    <td>
                    	<c:forEach items="${teacherTypeses }" var="item">
		               		<c:if test="${fn:contains(entity.teacherTypes, item.value)}">${item.desc }</c:if>
		               	</c:forEach>
                    </td>
                </tr>
				 <tr style="height: 30px">
				 	<td align="center"  class="project_view_style">培训方式</td>
                    <td>
			        	<c:forEach items="${implWays }" var="item">
			               	<c:if test="${item.id==entity.implWay}">${item.name }</c:if>
			            </c:forEach>
                    </td>
                     <td align="center" class="project_view_style">人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数</td>
                     <td>
                        <fmt:formatNumber value="${entity.personNum }" pattern="#.##"/>
                    </td>
                </tr>
                <tr style="height: 30px">
                    <td align="center"  class="project_view_style">学时生成方式</td>
                    <td colspan="3">
                        <c:forEach items="${scoreCreateTypes }" var="item">
                            <c:if test="${item.value==entity.scoreCreateType}">${item.desc }</c:if>
                        </c:forEach>
                    </td>
                </tr>
				 <tr style="height: 30px"> 
				 	<td align="center"  class="project_view_style">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</td>
                    <td colspan="3" style="width:75%;word-wrap:break-word;word-break:break-all;">${entity.memo}
                    </td>
                </tr>
            </tbody>
        </table>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
    </ul>
</div>