<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherCommunRotateTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_commun_rotate_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/communRotate/teacherCommunRotateList/${tid}/${tfs_type}'});
		$(this).dialog('closeCurrent');
	}
}

function f_teacher_rotate_type(){
	return ${rotateTypes};
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
	    if(names == "0-无"){
	    	$("#f_teacher_commun_rotate_isTranRelation option").removeAttr("selected");
			$("#f_teacher_commun_rotate_isTranRelation").selectpicker('refresh');
			$("#f_teacher_commun_rotate_isTranRelation").attr("disabled", true)
			$("#f_teacher_commun_rotate_startDate").val("");
			$("#f_teacher_commun_rotate_endDate").val("");
			$("#f_teacher_commun_rotate_oldUnitName").val("");
			$("#f_teacher_commun_rotate_rotateUnitName").val("");
			$("#f_teacher_commun_rotate_startDate").attr("disabled", true);
			$("#f_teacher_commun_rotate_endDate").attr("disabled", true);
			$("#f_teacher_commun_rotate_oldUnitName").attr("disabled", true);
			$("#f_teacher_commun_rotate_rotateUnitName").attr("disabled", true);
			
			$("#f_teacher_commun_rotate_isTranRelation_span").hide();
			$("#f_teacher_commun_rotate_startDate_span").hide();
			$("#f_teacher_commun_rotate_oldUnitName_span").hide();
			$("#f_teacher_commun_rotate_rotateUnitName_span").hide();
	
			$('#f_teacher_commun_rotate_form').validator("setField", "isTranRelation", "");
			$('#f_teacher_commun_rotate_form').validator("setField", "startDate", "");
			$('#f_teacher_commun_rotate_form').validator("setField", "oldUnitName", "");
			$('#f_teacher_commun_rotate_form').validator("setField", "rotateUnitName", "");
		}else{
			$("#f_teacher_commun_rotate_isTranRelation").attr("disabled", false)
			$("#f_teacher_commun_rotate_isTranRelation option").removeAttr("disabled");
			$("#f_teacher_commun_rotate_isTranRelation").selectpicker('refresh');
			$("#f_teacher_commun_rotate_startDate").attr("disabled", false);
			$("#f_teacher_commun_rotate_endDate").attr("disabled", false);
			$("#f_teacher_commun_rotate_oldUnitName").attr("disabled", false);
			$("#f_teacher_commun_rotate_rotateUnitName").attr("disabled", false);
			
			$("#f_teacher_commun_rotate_isTranRelation_span").show();
			$("#f_teacher_commun_rotate_startDate_span").show();
			$("#f_teacher_commun_rotate_oldUnitName_span").show();
			$("#f_teacher_commun_rotate_rotateUnitName_span").show();
			
			$("#f_teacher_commun_rotate_isTranRelation").attr("data-rule", "required");
			$("#f_teacher_commun_rotate_startDate").attr("data-rule", "开始年月: required;date;match[lt, endDate, date]");
			$("#f_teacher_commun_rotate_oldUnitName").attr("data-rule", "required");
			$("#f_teacher_commun_rotate_rotateUnitName").attr("data-rule", "required");
		}
	}
	$('#'+treeId+'_id').val(ids)
	$('#f_teacher_rotate_ztree').trigger('validate');
}

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/communRotate/saveTeacherCommunRotate" method="post" id="f_teacher_commun_rotate_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherCommunRotateTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_commun_rotate_seq" class="control-label x140"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_commun_rotate_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_rotate_ztree" class="control-label x140"><span class="red">*</span>交流轮岗类型：</label>
                        <input type="hidden" name="rotateType" value="${entity.rotateType}" id="f_teacher_commun_rotate_rotateType_id" >
                        <input type="text" id="f_teacher_rotate_ztree" value="${rotateName}" data-toggle="selectztree" size="20" data-tree="#f_teacher_commun_rotate_rotateType" data-rule="required" readonly>
                        <ul style="height: 300px" id="f_teacher_commun_rotate_rotateType" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="pt_S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_rotate_type'}"
                            >
                        </ul>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_commun_rotate_isTranRelation" class="control-label x140"><span id="f_teacher_commun_rotate_isTranRelation_span" <c:if test="${not empty entity.rotateType and empty entity.isTranRelation}">style="display: none;"</c:if> class="red">*</span>是否调动人事关系：</label>
                        <select id="f_teacher_commun_rotate_isTranRelation" name="isTranRelation" data-toggle="selectpicker" data-width="200px" data-rule="required" <c:if test="${not empty entity.rotateType and empty entity.isTranRelation}">disabled="true"</c:if>>
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${isFlags }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.isTranRelation}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_commun_rotate_startDate" class="control-label x140"><span id="f_teacher_commun_rotate_startDate_span" <c:if test="${not empty entity.rotateType and empty entity.startDate}">style="display: none;"</c:if>  class="red">*</span>开始年月：</label>
                        <input type="text" name="startDate" id="f_teacher_commun_rotate_startDate" value="<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>" <c:if test="${not empty entity.rotateType and empty entity.startDate}">disabled="true"</c:if> data-rule="开始年月: required;date;match[lt, endDate, date]" data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_commun_rotate_endDate" class="control-label x140">结束年月：</label>
                        <input type="text" name="endDate" id="f_teacher_commun_rotate_endDate" value="<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>" data-rule="结束年月: date" data-toggle="datepicker" size="20" <c:if test="${not empty entity.rotateType and empty entity.endDate}">disabled="true"</c:if>>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_commun_rotate_oldUnitName" class="control-label x140"><span id="f_teacher_commun_rotate_oldUnitName_span" <c:if test="${not empty entity.rotateType and empty entity.oldUnitName}">style="display: none;"</c:if> class="red">*</span>原单位名称：</label>
                        <input type="text" name="oldUnitName" id="f_teacher_commun_rotate_oldUnitName" value="${entity.oldUnitName }" maxlength="20" size="20" data-rule="required" <c:if test="${not empty entity.rotateType and empty entity.oldUnitName}">disabled="true"</c:if>>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_commun_rotate_rotateUnitName" class="control-label x140"><span id="f_teacher_commun_rotate_rotateUnitName_span" <c:if test="${not empty entity.rotateType and empty entity.rotateUnitName}">style="display: none;"</c:if> class="red">*</span>交流轮岗单位名称：</label>
                        <input type="text" name="rotateUnitName" id="f_teacher_commun_rotate_rotateUnitName" value="${entity.rotateUnitName }" maxlength="20" size="20" data-rule="required" <c:if test="${not empty entity.rotateType and empty entity.rotateUnitName}">disabled="true"</c:if>>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherCommunRotate:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>