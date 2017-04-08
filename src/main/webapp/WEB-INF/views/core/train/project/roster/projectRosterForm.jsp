<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

	<form action="${ctx}/core/train/project/roster/saveProjectRoster" id="f_project_roster_apply_form" method="post">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
         <input type="hidden" name="pid" value="${pid }">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_project_roster_rosterNo" class="control-label x100">单号：</label>
                        <input type="text" name="rosterNo" id="f_project_roster_rosterNo" value="${entity.rosterNo }" disabled maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_roster_reportDate" class="control-label x100">上报时间：</label>
                        <input type="text" name="reportDate" id="f_project_roster_reportDate" value='<fmt:formatDate value="${entity.reportDate }" pattern="yyyy-MM-dd HH:mm:ss"/>' disabled   maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_roster_applyUserId" class="control-label x100">申请人：</label>
                        <input type="text" name="applyUserId" id="f_project_roster_applyUserId" value="${entity.applyUserName }" disabled data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_roster_memo" class="control-label x100"><span class="red">*</span>申请理由：</label>
                        <textarea name="memo" id="f_project_roster_memo" data-rule="required" data-toggle="autoheight" cols="30" rows="2" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
