<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushScoreTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.score_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/score/myScoreList'});
		$(this).dialog('closeCurrent');
	}
}

$('#f_score_pnum').on('change',function(){
    var pum = $('#f_score_pnum').val();
    if(!isNaN(pum)){
        $('#f_score_score').val(3*pum);
    }
});
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/score/saveScore" id="f_score_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushScoreTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="scoreType" value="5">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_score_type" class="control-label x100">学时类型：</label>
                        <input name="type" type="hidden" id="f_score_type" value="1">
                       	 非集中培训
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_year" class="control-label x100"><span class="red">*</span>年度：</label>
                        <select id="f_score_year" name="year"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" data-live-search="true">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${years }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.year}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_level" class="control-label x100"><span class="red">*</span>级别：</label>
                        <select id="f_score_level" name="level"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" data-live-search="true">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${levels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.level}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_name" class="control-label x100"><span class="red">*</span>交流议题：</label>
                        <input type="text" name="name" id="f_score_name" value="${entity.name }" data-rule="required" maxlength="50" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_memo" class="control-label x100"><span class="red">*</span>交流提纲：</label>
                        <textarea name="memo" id="f_score_memo" data-toggle="autoheight" cols="40" rows="2" maxlength="400" data-rule="required">${entity.memo}</textarea>
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_pnum" class="control-label x100"><span class="red">*</span>交流时长：</label>
                        <input type="text" name="pnum" id="f_score_pnum" value="${entity.pnum }" data-rule="required;digits" maxlength="10" size="20">(天)
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_score" class="control-label x100"><span class="red">*</span>所获学时：</label>
                        <input type="text" name="score" id="f_score_score" value="${entity.score }" data-rule="required;digits;range[3~]" readonly maxlength="5" size="20">
                    </td>
                </tr>
                 <tr>
                     <td>
                         <label><span style="color:#ff3700;font-weight: normal;"><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申报说明：&nbsp;&nbsp;参加市级以上学术交流、课题研究的，按3学时/天登记非集中培训学时。</span></label>
                     </td>
                 </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="Score:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>