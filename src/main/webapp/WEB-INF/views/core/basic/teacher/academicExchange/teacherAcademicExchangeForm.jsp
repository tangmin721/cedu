<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherAcademicExchangeTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_academic_exchange_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/academicExchange/teacherAcademicExchangeList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/academicExchange/saveTeacherAcademicExchange" method="post" id="f_teacher_academic_exchange_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherAcademicExchangeTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	<tr>
                    <td>
                        <label for="f_teacher_academic_exchange_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="hidden" name="tid" id="f_teacher_academic_exchange_tid" value="${entity.tid }">
                        <input type="text" name="seq" id="f_teacher_academic_exchange_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
            	 <tr>
                    <td>
                        <label for="f_teacher_academic_exchange_year" class="control-label x100"><span class="red">*</span>年度：</label>
                        <select id="f_teacher_academic_exchange_year" name="year" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${years }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.year}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_academic_exchange_level" class="control-label x100"><span class="red">*</span>级别：</label>
                        <select id="f_teacher_academic_exchange_level" name="level" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${levels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.level}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>
                    </td>
                </tr>

				 <tr>
                    <td>
                        <label for="f_teacher_academic_exchange_exchName" class="control-label x100"><span class="red">*</span>交流议题：</label>
                        <input type="text" name="exchName" id="f_teacher_academic_exchange_exchName" value="${entity.exchName }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_academic_exchange_hours" class="control-label x100"><span class="red">*</span>主讲时长：</label>
                        <input type="text" name="hours" id="f_teacher_academic_exchange_hours" value="${entity.hours }" data-rule="required;digits" maxlength="20" size="20">(小时)
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_academic_exchange_memo" class="control-label x100">备注：</label>
                        <textarea  name="memo" id="f_teacher_academic_exchange_memo" data-toggle="autoheight" cols="20" rows="2"  maxlength="200">${entity.memo }</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherAcademicExchange:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>