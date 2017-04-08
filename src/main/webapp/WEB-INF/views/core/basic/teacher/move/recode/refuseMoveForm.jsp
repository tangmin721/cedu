<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushRecodeTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
        $(this).navtab('reloadFlag','ntv-move-in');
        $(this).navtab('reloadFlag','mtv-move-out');
        $(this).navtab('reloadFlag','ntv-tmove-list');
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/move/recode/refuseMove" id="f_dict_catlog_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushRecodeTable">
        <input type="hidden" name="id" value="${id}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="ftakeMemo" class="control-label x85"><span class="red">*</span>拒绝原因:</label>
                        <textarea name="takeMemo" id="ftakeMemo" data-toggle="autoheight" data-rule="required" cols="36" rows="4" maxlength="400"></textarea>
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