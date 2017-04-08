<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="bjui-pageContent">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="scoreType" value="6">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_score_type" class="control-label x100">学时类型：</label>
                       	 非集中培训
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
                        <label for="f_score_level" class="control-label x100">级别：</label>
			               	<c:forEach items="${levels }" var="item">
			               		<c:if test="${item.id==entity.level}">${item.name }</c:if>
			               	</c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_scoreRoleType" class="control-label x100">角色：</label>
			               	<c:forEach items="${scoreRoleTypes }" var="item">
			               		<c:if test="${item.id==entity.scoreRoleType}">${item.name }</c:if>
			               	</c:forEach>
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_name" class="control-label x100">课题名称：</label>
                        ${entity.name }
                    </td>
                </tr>
                 <tr>
                     <td>
                         <label for="f_score_pnum" class="control-label x100">交流时长：</label>
                         ${entity.pnum }(天)
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