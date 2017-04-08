<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushDictItemTreeTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
        $("#div_sys_dict_item").bjuiajax('refreshLayout', {
            target:$('#div_sys_dict_item'),
            data: {catlogId:${catlogId}}
        });
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/dict/item/saveDictItem" id="f_dict_item_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushDictItemTreeTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="catlogId" value="${entity.catlogId}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_dict_item_seq" class="control-label x85"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_dict_item_seq" value="${entity.seq }" data-rule="required;digits" size="15" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_dict_item_name" class="control-label x85"><span class="red">*</span>名称：</label>
                        <input type="text" name="name" id="f_dict_item_name" value="${entity.name }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_dict_item_code" class="control-label x85"><span class="red">*</span>编码：</label>
                        <input type="text" name="code" id="f_dict_item_code" value="${entity.code }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_dict_item_memo" class="control-label x85">备注：</label>
                        <textarea name="memo" id="f_dict_item_memo" data-toggle="autoheight" cols="15" rows="2">${entity.memo}</textarea>
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