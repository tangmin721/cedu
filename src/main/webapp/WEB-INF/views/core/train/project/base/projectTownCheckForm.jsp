<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	function refushTCPage(json){
		if(json.statusCode!='200'){
			$(this).alertmsg('error', json.message);
		}else{
			$('#project_search2').trigger('click');
			$(this).dialog('close','np-town-check-form');
		}
	}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/base/saveTownCheckProject" data-toggle="validate" method="post" data-alertmsg="false" data-callback="refushTCPage" id="f_score_unpass_form">
        <input type="hidden" name="pid" value="${pid }"/>
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	 <tr>
                    <td>
                        <label class="control-label x120"><span class="red">*</span>审批：</label>
                        <input type="radio" name="pass"  data-toggle="icheck" value="12" data-rule="checked" <c:if test="${pass==12}">checked</c:if> data-label="通过&nbsp;&nbsp;">
                        <input type="radio" name="pass"  data-toggle="icheck" value="13" <c:if test="${pass==13}">checked</c:if> data-label="不通过">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_check_coption" class="control-label x120"><span class="red">*</span>审核意见说明：</label>
                        <textarea name="coption" id="f_check_coption" data-toggle="autoheight" cols="30" rows="2" maxlength="400" data-rule="required" >${coption}</textarea>
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