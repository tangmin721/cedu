<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>


<div class="bjui-pageContent">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	<tr>
                    <td colspan=2>
                        <label for="f_expert_view_courseName" class="control-label x100">课程名称：</label>
                        ${entity.courseName }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_view_name" class="control-label x100">姓名：</label>
                        ${entity.name }
                    </td>
                    <td>
                        <label for="f_expert_view_idCard" class="control-label x100">身份证：</label>
                        ${entity.idCard }
                    </td>
                </tr>
				 <tr>
                    <td colspan=2>
                        <label for="f_expert_view_dept" class="control-label x100">单位：</label>
                        ${entity.dept }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_expert_view_title" class="control-label x100">职称：</label>
                        ${entity.title }
                    </td>
                    <td>
                        <label for="f_expert_view_direction" class="control-label x100">研究方向：</label>
                        ${entity.direction }
                    </td>
                </tr>
                <shiro:hasRole name="PROVINCE_ADMIN">
				 <tr>
				 	<td>
                        <label for="f_expert_view_mobile" class="control-label x100">联系电话：</label>
                        ${entity.mobile }
                    </td>
                    <td>
                        <label for="f_expert_view_email" class="control-label x100">电子邮箱：</label>
                       ${entity.email }
                    </td>
                </tr>
                </shiro:hasRole>
				 <tr>
                    <td>
                        <label for="f_expert_view_time" class="control-label x100">授课时间：</label>
                        <fmt:formatDate value="${entity.time }" pattern="yyyy-MM-dd"/>
                    </td>
                    <td>
                        <label for="f_expert_view_goodRate" class="control-label x100">优良率：</label>
                        ${entity.goodRate }
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