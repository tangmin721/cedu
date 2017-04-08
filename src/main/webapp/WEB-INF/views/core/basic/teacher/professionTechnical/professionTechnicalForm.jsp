<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProfessionTechnicalTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.profession_technical_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/professionTechnical/professionTechnicalList/${tid}'});
		$(this).dialog('closeCurrent');
	}
}

function f_teacher_profession_duty(){
	return ${professionDutys};
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
		if(names == "无"){
			$("#f_profession_technical_startDate").val("");
			$("#f_profession_technical_endDate").val("");
			$("#f_profession_technical_unitName").val("");
			$("#f_profession_technical_startDate_spn").hide();
			$('#f_profession_technical_form').validator("setField", "startDate", "");
			$('#f_profession_technical_startDate').trigger('validate');
			$("#f_profession_technical_startDate").attr("disabled", true);
			$("#f_profession_technical_endDate").attr("disabled", true);
			$("#f_profession_technical_unitName").attr("disabled", true);
			
		}else{
			$("#f_profession_technical_startDate").attr("disabled", false);
			$("#f_profession_technical_endDate").attr("disabled", false);
			$("#f_profession_technical_unitName").attr("disabled", false);
			$("#f_profession_technical_startDate_spn").show();
			$('#f_profession_technical_startDate').attr("data-rule", "聘任开始年月: required;date;match[lt, endDate, date]");
			$('#f_profession_technical_startDate').trigger('validate');
		}
	}
	$('#'+treeId+'_id').val(ids);
	var tmpid = (treeId+'_id').replace('select_tree_id','ztree');
	$('#'+tmpid).trigger('validate');
}

//监听时间变化信息
$("#f_profession_technical_startDate").on('afterchange.bjui.datepicker',function (e,data){
	var proFessName = $("#f_teacher_professionDuty_ztree").val();
	if(proFessName == "无"){
		
	}else{
		$('#f_profession_technical_startDate').attr("data-rule", "聘任开始年月: required;date;match[lt, endDate, date]");
	}
});

$("#f_profession_technical_endDate").on('afterchange.bjui.datepicker',function (e,data){
	var proFessName = $("#f_teacher_professionDuty_ztree").val();
	if(proFessName == "无"){
		
	}else{
		$('#f_profession_technical_startDate').attr("data-rule", "聘任开始年月: required;date;match[lt, endDate, date]");
	}
});

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/professionTechnical/saveProfessionTechnical" method="post" id="f_profession_technical_form" data-toggle="validate" data-alertmsg="false" data-callback="refushProfessionTechnicalTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}" >
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_profession_technical_seq" class="control-label x130"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_profession_technical_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_professionDuty_ztree" class="control-label x130"><span class="red">*</span>聘任专业技术职务：</label>
                        <input type="hidden" name="professionDuty" value="${entity.professionDuty}" id="f_teacher_professionDuty_select_tree_id" >
                        <input type="text" id="f_teacher_professionDuty_ztree" value="${professionDutyName}" data-toggle="selectztree" size="20" data-tree="#f_teacher_professionDuty_select_tree" data-rule="required" readonly>
                        <ul id="f_teacher_professionDuty_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="pt_S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_profession_duty'}"
                            >
                        </ul>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_profession_technical_startDate" class="control-label x130"><span id="f_profession_technical_startDate_spn" <c:if test="${professionDutyName == '无'}"> style="display: none;"</c:if> class="red">*</span></span>聘任开始年月：</label>
                        <input type="text" name="startDate" id="f_profession_technical_startDate" value="<fmt:formatDate value="${entity.startDate }"  pattern="yyyy-MM-dd"/>" <c:if test="${professionDutyName == '无'}">disabled="true"</c:if> data-rule="聘任开始年月: required;date;match[lt, endDate, date]" data-toggle="datepicker" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_profession_technical_endDate" class="control-label x130">聘任结束年月：</label>
                        <input type="text" name="endDate" id="f_profession_technical_endDate" value="<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>" data-rule="聘任结束年月: date;" data-toggle="datepicker" size="20" <c:if test="${professionDutyName == '无'}">disabled="true"</c:if>>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_profession_technical_unitName" class="control-label x130">聘任单位名称：</label> 
                        <textarea name="unitName" id="f_profession_technical_unitName" data-toggle="autoheight" cols="20" rows="2" maxlength="150" <c:if test="${professionDutyName == '无'}">disabled="true"</c:if>>${entity.unitName}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="ProfessionTechnical:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>