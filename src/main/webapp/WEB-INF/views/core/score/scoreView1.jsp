<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>


<div class="bjui-pageContent">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="scoreType" value="1">
         <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
         	<tbody>
        		<tr>
                    <td>
                        <label for="f_score_pno" class="control-label x100">项目编号：</label>
                        ${entity.pno }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_year" class="control-label x100">项目年度：</label>
                        ${entity.yearName }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_level" class="control-label x100">培训等级：</label>
			               	<c:forEach items="${trainLevels }" var="item">
			               		<c:if test="${item.id==entity.level}">${item.name }</c:if>
			               	</c:forEach>
                    </td>
                </tr>
             <tbody>
        </table>
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
                        <label for="f_score_pid" class="control-label x100">项目名称：</label>
			            ${projectName }
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