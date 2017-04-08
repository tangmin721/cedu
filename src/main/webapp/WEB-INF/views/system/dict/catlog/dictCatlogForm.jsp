<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushDictCatlogTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.dict_catlog_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/dict/catlog/dictCatlogList'});
		$(this).dialog('closeCurrent');
	}
}
$('#f_dict_catlog_can').bootstrapSwitch();
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/dict/catlog/saveDictCatlog" id="f_dict_catlog_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushDictCatlogTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_dict_catlog_seq" class="control-label x100"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_dict_catlog_seq" value="${entity.seq }" data-rule="required;digits" size="15" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_dict_catlog_name" class="control-label x100"><span class="red">*</span>名称：</label>
                        <input type="text" name="name" id="f_dict_catlog_name" value="${entity.name }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_dict_catlog_code" class="control-label x100"><span class="red">*</span>编码：</label>
                        <input type="text" name="code" id="f_dict_catlog_code" value="${entity.code }" data-rule="required" size="15">
                    </td>
                </tr>
                 <tr>
                     <td>
                         <label for="f_dict_catlog_tree" class="control-label x100">是否是树：</label>
                         <input type="checkbox" name="isTree" id="f_dict_catlog_tree" data-toggle="icheck" data-label="是" <c:if test="${entity.isTree }">checked</c:if>>
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="f_dict_catlog_can" class="control-label x100">父节点可选中：</label>
                         <input id="f_dict_catlog_can" type="checkbox"  name="canParent"  data-size="mini" data-on-color="success" data-off-color="warning" data-on-text="可以"  data-off-text="不可以" <c:if test="${entity.canParent }">checked</c:if>>
                     </td>
                 </tr>
				 <tr>
                    <td>
                        <label for="f_dict_catlog_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_dict_catlog_memo" data-toggle="autoheight" cols="15" rows="2">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
    </ul>
</div>