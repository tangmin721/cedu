<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	function refushFilePage(json){
		if(json.statusCode!='200'){
			$(this).alertmsg('error', json.message);
		}else{
			$(this).dialog('refresh','np-file-check');//刷新dialog
			$(this).dialog('closeCurrent');
		}
	}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/file/saveFileUnPassForm" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushFilePage"" id="f_file_unpass_form">
        <input type="hidden" name="pid" value="${pid}">
        <input type="hidden" name="orgId" value="${orgId}">
        <input type="hidden" name="type" value="${type}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_file_check_checkDesc" class="control-label x120"><span class="red">*</span>审核不同意说明：</label>
                        <textarea name="checkDesc" id="f_file_check_checkDesc" data-toggle="autoheight" cols="30" rows="2" maxlength="400" data-rule="required" >${checkDesc}</textarea>
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