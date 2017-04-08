<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="bjui-pageContent">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="scoreType" value="2">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_score_type" class="control-label x100">学时类型：</label>
                       	 集中培训
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_degree" class="control-label x100">学历提升：</label>
			               	<c:forEach items="${degrees }" var="item">
			               		<c:if test="${item.id==entity.degree}">${item.name }</c:if>
			               	</c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_startdate" class="control-label x100">开始时间：</label>
                        <fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_endDate" class="control-label x100">结束时间：</label>
                        <fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_univ" class="control-label x100">毕业院校：</label>
                        ${entity.univ }
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_major" class="control-label x100">所学专业：</label>
                        ${entity.major }
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_year" class="control-label x100">年度：</label>
			            ${entity.yearName }
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_score" class="control-label x100">所获学时：</label>
                        ${entity.score }
                    </td>
                </tr>
            </tbody>
        </table>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
    </ul>
</div>