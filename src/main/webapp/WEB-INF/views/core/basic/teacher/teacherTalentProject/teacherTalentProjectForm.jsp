<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherTalentProjectTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_talent_project_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherTalentProject/teacherTalentProjectList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}

function f_teacher_project_type(){
	return ${projectNos};
}

function getSchoolType(){
	return ${schoolType};
}

//树下拉选择框：：选择事件
function pt_S_NodeCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true)
	var ids = '', names = ''

	for (var i = 0; i < nodes.length; i++) {
		ids   += ','+ nodes[i].id
		names += ','+ nodes[i].name
	}
	if (ids.length > 0) {
		ids = ids.substr(1), names = names.substr(1)
	}

	var $from = $('#'+ treeId).data('fromObj')

	if ($from && $from.length) {
		$from.val(names)
		if(names == "00-无"){
			$("#f_teacher_talent_project_selectYear option").removeAttr("selected");
			$("#f_teacher_talent_project_selectYear").selectpicker('refresh');
			$('#f_teacher_talent_project_form').validator("setField", "selectYear", "");
			$("#f_teacher_talent_project_selectYear").trigger('validate');
			$("#f_teacher_talent_project_selectYear").attr("disabled", true);
			if(getSchoolType() == 1){
				$("#f_teacher_talent_project_selectYear_span").hide();
			}
		}else{
			$("#f_teacher_talent_project_selectYear").attr("disabled", false);
			$("#f_teacher_talent_project_selectYear").removeAttr('disabled');
			$("#f_teacher_talent_project_selectYear").selectpicker('refresh');
			if(getSchoolType() == 1){
				$("#f_teacher_talent_project_selectYear_span").show();
				$("#f_teacher_talent_project_selectYear").attr("data-rule", "required");
			}
			$("#f_teacher_talent_project_selectYear").trigger('validate');
		}
	}
	$('#'+treeId+'_id').val(ids);
	$("#f_teacher_tchCourseType_ztree").trigger('validate');
}

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/teacherTalentProject/saveTeacherTalentProject" method="post" id="f_teacher_talent_project_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherTalentProjectTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_talent_project_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_talent_project_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_talent_project_projectNo" class="control-label x100"><span class="red" <c:if test="${schoolType != 1}">style="display: none;"</c:if>>*</span>项目名称：</label>
                        <input type="hidden" name="projectNo" value="${entity.projectNo}" id="f_teacher_talent_project_projectNo_id" >
                        <input type="text" id="f_teacher_tchCourseType_ztree" value="${projectName}" data-toggle="selectztree" size="20" data-tree="#f_teacher_talent_project_projectNo" readonly <c:if test="${schoolType == 1}">data-rule="required"</c:if>>
                        <ul style="height: 320px" id="f_teacher_talent_project_projectNo" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="pt_S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_project_type'}"
                            >
                        </ul>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_talent_project_selectYear" class="control-label x100"><span id="f_teacher_talent_project_selectYear_span" class="red" <c:if test="${schoolType != 1 or projectName == '00-无'}">style="display: none;"</c:if>>*</span>入选年份：</label>
                        <select id="f_teacher_talent_project_selectYear" name="selectYear" data-toggle="selectpicker" data-width="200px" <c:if test="${schoolType == 1}">data-rule="required"</c:if> <c:if test="${projectName == '00-无'}">disabled="true"</c:if>>
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${years }" var="item">
			               		<option  value="${item.id }" <c:if test="${empty entity.selectYear and item.name == currentYear}">selected</c:if><c:if test="${item.id==entity.selectYear}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherTalentProject:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>