<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	function refushScorePage(json){
		if(json.statusCode!='200'){
			$(this).alertmsg('error', json.message);
		}else{
			$('#check_score_list_search').trigger('click');
			$('#town_check_score_list_search').trigger('click');
			$('#province_check_score_list_search').trigger('click');
			$(this).dialog('close','np-score-unpass-form');
		}
	}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/score/saveStatus" data-toggle="validate" method="post" data-alertmsg="false" data-callback="refushScorePage" id="f_score_unpass_form">
        <input type="hidden" name="id" value="${id}">
        <input type="hidden" name="status" value="0">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_score_check_checkDesc" class="control-label x120"><span class="red">*</span>审核不同意说明：</label>
                        <textarea name="checkDesc" id="f_score_check_checkDesc" data-toggle="autoheight" cols="30" rows="2" maxlength="400" data-rule="required" >${checkDesc}</textarea>
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