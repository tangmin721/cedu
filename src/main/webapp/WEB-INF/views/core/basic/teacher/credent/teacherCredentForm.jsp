<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<link href="${ctx }/static/BJUI/plugins/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
<script src="${ctx }/static/BJUI/plugins/bootstrap-fileinput/js/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
<script src="${ctx }/static/BJUI/plugins/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
<script src="${ctx }/static/BJUI/plugins/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>
<script type="text/javascript">

var cre_previews = "";
var cre_previewDatas = "";

function refushTeacherCredentTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_credent_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/credent/teacherCredentList'});
		$(this).dialog('closeCurrent');
	}
}

$('#f_teacher_credent_file').fileinput({
    language: 'zh',
    previewFileIcon: '<i class="fa fa-file"></i>',
    
    uploadUrl: '${ctx}/system/att/upload',
    uploadAsync: false,
    showUpload: false, // hide upload button
    showRemove: false, // hide remove button
    maxFileSize: 1024000000,
    maxFilesNum: 10,
    
    overwriteInitial:false,
    initialPreview: cre_previews,
    initialPreviewConfig: cre_previewDatas,
    
   // allowedFileExtensions : ['jpg', 'png','gif','avi','rar'],
    allowedPreviewTypes: null, // set to empty, null or false to disable preview for all types
    previewFileIconSettings: {//自定义这些类型的文件预览图标
        'doc': '<i class="fa fa-file-word-o text-primary"></i>',
        'xls': '<i class="fa fa-file-excel-o text-success"></i>',
        'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
        'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
        'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
        'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
    },
    previewFileExtSettings: {
        'doc': function(ext) {
            return ext.match(/(doc|docx)$/i);
        },
        'xls': function(ext) {
            return ext.match(/(xls|xlsx)$/i);
        },
        'ppt': function(ext) {
            return ext.match(/(ppt|pptx)$/i);
        }
    },
    previewSettings:    {
        image: {width: "160px", height: "100px"},
        other: {width: "160px", height: "100px"}
    },
    slugCallback: function(filename) {
        return filename;
    }
}).on("filebatchselected", function(event, files) {//file变化，触发事件
    $('#f_teacher_credent_file').fileinput("upload");//自动上传
}).on('fileclear', function(event) {//右上角 close按钮，清空
	$('#f_teacher_credentattIds').val('');
	credent_att_ids = [];
});

$('#f_teacher_credent_file').on('filedeleted', function(event, key) {
  //  alert('delete:'+key);
});

$('#f_teacher_credent_file').on('filesuccessremove', function(event, id) {
//	alert('filesuccessremove'+id);
//    if (some_processing_function(id)) {
//       console.log('Uploaded thumbnail successfully removed');
//    } else {
//        return false; // abort the thumbnail removal
//    }
});

//附件赋值
var credent_att_ids = [];


$('#f_teacher_credent_file').on('filebatchpreupload', function(event, data, previewId, index) {
	console.log("上传前"+previewId);
});

$('#f_teacher_credent_file').on('filebatchuploadsuccess', function(event, data, previewId, index) {
	
	var form = data.form, 
	files = data.files, 
	extra = data.extra,
    response = data.response, reader = data.reader;
	
	console.log(event);
	console.log(data);
	console.log(previewId);
	console.log(index);
    var response = data.response;
    var ids = response.data;
    credent_att_ids = credent_att_ids.concat(ids);
    var attIdsStr = credent_att_ids.join(',');
    $('#f_teacher_credentattIds').val(attIdsStr);
});

function f_teacher_credent_language_type(){
	return ${languageCredentTypes};
}

$("#f_teacher_credent_type").change(function(){
	var typeName = $("#f_teacher_credent_type option:selected").text();	
	if(typeName == "0-无"){
		$('#f_teacher_credent_form').validator("setField", "f_teacher_credent_languageCredentType_ztree_name", "");
		$('#f_teacher_credent_form').validator("setField", "name", "");
		$('#f_teacher_credent_languageCredentType_ztree').trigger('validate');
		$('#f_teacher_credent_name').trigger('validate');
	
		$("#f_teacher_credent_languageCredentType_select_tree_id").val("");
		$("#f_teacher_credent_languageCredentType_ztree").val("");
		$("#f_teacher_credent_languageCredentType_ztree").attr("disabled", true);
		
		$("#f_teacher_credent_name").val("");
		$("#f_teacher_credent_name").attr("disabled", true);
		
		$("#f_teacher_credent_takeDate").val("");
		$("#f_teacher_credent_takeDate").attr("disabled", true);
		
		$("#f_teacher_credent_dept").val("");
		$("#f_teacher_credent_dept").attr("disabled", true);
		
		$("#f_teacher_credent_credentNo").val("");
		$("#f_teacher_credent_credentNo").attr("disabled", true);
	}else if(typeName == "2-语言证书"){
		
		$("#f_teacher_credent_takeDate").attr("disabled", false);
		$("#f_teacher_credent_dept").attr("disabled", false);
		$("#f_teacher_credent_credentNo").attr("disabled", false);
		
		$('#f_teacher_credent_form').validator("setField", "name", "");
		$('#f_teacher_credent_name').trigger('validate');
		
		$("#f_teacher_credent_name").attr("disabled", true);
		$("#f_teacher_credent_languageCredentType_ztree").attr("disabled", false);
		$('#f_teacher_credent_form').validator("setField", "name", "");
		$("#f_teacher_credent_languageCredentType_span").show();
		$("#f_teacher_credent_languageCredentType_ztree").attr("data-rule", "required");
	}else{
		$('#f_teacher_credent_form').validator("setField", "f_teacher_credent_languageCredentType_ztree_name", "");
		$('#f_teacher_credent_languageCredentType_ztree').trigger('validate');
		$('#f_teacher_credent_name').trigger('validate');
		
		$("#f_teacher_credent_languageCredentType_select_tree_id").val("");
		$("#f_teacher_credent_languageCredentType_ztree").val("");
		$("#f_teacher_credent_languageCredentType_ztree").attr("disabled", true);
		$("#f_teacher_credent_languageCredentType_span").hide();
		$("#f_teacher_credent_name").attr("data-rule", "required");
		
		$("#f_teacher_credent_name").attr("disabled", false);
		$("#f_teacher_credent_takeDate").attr("disabled", false);
		$("#f_teacher_credent_dept").attr("disabled", false);
		$("#f_teacher_credent_credentNo").attr("disabled", false);
	}
});

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
		if(names == "9-其他语言证书"){
			$("#f_teacher_credent_name").attr("disabled", false);
			$("#f_teacher_credent_name").attr("data-rule", "required");
		}else{
			$('#f_teacher_credent_form').validator("setField", "name", "");
			$('#f_teacher_credent_name').trigger('validate');
			$("#f_teacher_credent_name").attr("disabled", true);
		}
	}
	$('#'+treeId+'_id').val(ids)
	var tmpid = (treeId+'_id').replace('select_tree_id','ztree');
	$('#'+tmpid).trigger('validate');
}

</script>



<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/credent/saveTeacherCredent" id="f_teacher_credent_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherCredentTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_credent_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="hidden" name="tid" id="f_teacher_credent_tid" value="${entity.tid }">
                        <input type="text" name="seq" id="f_teacher_credent_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_credent_languageType" class="control-label x100"><span class="red">*</span>语种：</label>
                        <select id="f_teacher_credent_languageType" name="languageType" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${languageTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.languageType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_credent_proficiencyLevel" class="control-label x100"><span class="red">*</span>掌握程度：</label>
                        <select id="f_teacher_credent_proficiencyLevel" name="proficiencyLevel" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${proficiencyLevels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.proficiencyLevel}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_credent_otherSkillName" class="control-label x100">其他技能名称：</label>
                        <input type="text" name="otherSkillName" id="f_teacher_credent_otherSkillName" value="${entity.otherSkillName }" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_credent_otherProficiencyLevel" class="control-label x100">其他技能掌握程度：</label>
                        <select id="f_teacher_credent_otherProficiencyLevel" name="otherProficiencyLevel" data-toggle="selectpicker" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${otherProficiencyLevels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.otherProficiencyLevel}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                <tr>
                	<td>
                		<label for="f_teacher_credent_type" class="control-label x100">证书类型：</label>
                        <select id="f_teacher_credent_type" name="type" data-toggle="selectpicker" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${credents }" var="item">
			               		<option  value="${item.id }" <c:if test='${empty entity.type && item.name=="2-语言证书"}'>selected</c:if><c:if test="${item.id==entity.type}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                	</td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_credent_languageCredentType_ztree" class="control-label x100"><span id="f_teacher_credent_languageCredentType_span" class="red">*</span>语言证书名称：</label>
                        <input type="hidden" name="languageCredentType" value="${entity.languageCredentType}" id="f_teacher_credent_languageCredentType_select_tree_id" >
                        <input type="text" id="f_teacher_credent_languageCredentType_ztree" name="f_teacher_credent_languageCredentType_ztree_name" value="${languageCredentTypeName}" data-toggle="selectztree" size="20" data-tree="#f_teacher_credent_languageCredentType_select_tree" data-rule="required" readonly>
                        <ul style="height: 160px" id="f_teacher_credent_languageCredentType_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="pt_S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_credent_language_type'}"
                            >
                        </ul>
                    </td>
                </tr>
                <tr>
                	 <td>
                        <label for="f_teacher_credent_name" class="control-label x100">证书名称：</label>
                        <input type="text" name="name" id="f_teacher_credent_name" value="${entity.name }" disabled="true" maxlength="20" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_credent_takeDate" class="control-label x100">发证年月：</label>
                        <input type="text" name="takeDate" id="f_teacher_credent_takeDate" value='<fmt:formatDate value="${entity.takeDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_teacher_credent_dept" class="control-label x100">发证单位：</label>
                        <input type="text" name="dept" id="f_teacher_credent_dept" value="${entity.dept }" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_credent_credentNo" class="control-label x100">证书编号：</label>
                        <input type="text" name="credentNo" id="f_teacher_credent_credentNo" value="${entity.credentNo }" maxlength="20" size="20">
                    </td>
                </tr>
                
				 
                <!--  
				 <tr>
                    <td colspan="2">
                        <label for="f_teacher_credent_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_teacher_credent_memo" data-toggle="autoheight" cols="20" rows="2" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                       <label for="f_teacher_credent_file" class="control-label x100">附件：</label>
                       <input type="hidden" name="attIds" id="f_teacher_credentattIds">
                    </td>
                </tr>
                -->
            </tbody>
        </table>
    </form>
    <!--  
    <div style="padding:10px;100px;">
    	<input id="f_teacher_credent_file" name="file" type="file" multiple>
    </div>
    -->
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherCredent:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>