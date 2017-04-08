<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
</script>

	<form action="${ctx}/core/train/project/reportConfig/saveProjectReportConfig" method="post" id="f_project_report_config_form" method="post">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_project_report_config_trainCount" class="control-label x100"><span class="red">*</span>培训人数：</label>
                        <input type="hidden" name="pid" id="f_project_report_config_pid" value="${pid }">
                        <input type="text" name="trainCount" id="f_project_report_config_trainCount" value="${entity.trainCount }" data-rule="required;digits" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_report_config_registerType" class="control-label x100"><span class="red">*</span>报名方式：</label>
                        <select name="registerType" data-toggle="selectpicker" id="f_project_report_config_registerType" data-rule="required" data-width="200px">
                        	<option  value="">--请选择--</option>
                        	<c:forEach items="${registerTypes }" var="item">
                        		<option value="${item.value }" <c:if test="${item.value==entity.registerType}">selected</c:if>>${item.desc }</option>
                        	</c:forEach>
						</select>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_report_config_startDate" class="control-label x100"><span class="red">*</span>报名开始时间：</label>
                    	<input type="text" name="startDate" id="f_project_report_config_startDate" value='<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_report_config_endDate" class="control-label x100"><span class="red">*</span>报名截止时间：</label>
                        <input type="text" name="endDate" id="f_project_report_config_endDate" value='<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_report_config_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_project_report_config_memo" data-toggle="autoheight" cols="20" rows="3" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
