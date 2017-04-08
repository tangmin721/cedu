<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherWorkExpTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_work_exp_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/workExp/teacherWorkExpList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}

function f_teacher_workexp_depttype(){
	return ${deptTypes};
}

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/workExp/saveTeacherWorkExp" method="post" id="f_teacher_work_exp_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherWorkExpTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_work_exp_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_dept" class="control-label x100"><span class="red">*</span>任职单位名称：</label>
                        <input type="text" name="dept" id="f_teacher_work_exp_dept" value="${entity.dept }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_startDate" class="control-label x100"><span class="red">*</span>任职开始年月：</label>
                        <input type="text" name="startDate" id="f_teacher_work_exp_startDate" value="<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>" data-rule="任职开始年月: required; date;" data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_endDate" class="control-label x100"><span class="red">*</span>任职结束年月：</label>
                        <input type="text" name="endDate" id="f_teacher_work_exp_endDate" value="<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>" data-rule="任职结束年月: required; date; match[gte, startDate, date]" data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_deptType_ztree" class="control-label x100"><span class="red">*</span>单位性质类别：</label>
                        <input type="hidden" name="deptType" value="${entity.deptType}" id="f_teacher_work_exp_deptType_select_tree_id" >
                        <input type="text" id="f_teacher_work_exp_deptType_ztree" value="${deptTypeName}" data-toggle="selectztree" size="20" data-rule="required" data-tree="#f_teacher_work_exp_deptType_select_tree" readonly>
                        <ul id="f_teacher_work_exp_deptType_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_workexp_depttype'}"
                            >
                        </ul>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_duty" class="control-label x100"><span class="red">*</span>任职岗位：</label>
                        <input type="text" name="duty" id="f_teacher_work_exp_duty" value="${entity.duty }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
                <!--  
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_voucher" class="control-label x100">证明人：</label>
                        <input type="text" name="voucher" id="f_teacher_work_exp_voucher" value="${entity.voucher }" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_mobile" class="control-label x100">字段8：</label>
                        <input type="text" name="mobile" id="f_teacher_work_exp_mobile" value="${entity.mobile }" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_work_exp_memo" class="control-label x100">备注：</label>
                        <input type="text" name="memo" id="f_teacher_work_exp_memo" value="${entity.memo }"  maxlength="20" size="20">
                    </td>
                </tr>
                -->
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherWorkExp:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>