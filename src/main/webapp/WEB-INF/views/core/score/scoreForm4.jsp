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

$('#f_score_zsize').on('change',function(){
    var zsize = $('#f_score_zsize').val();
    if(!isNaN(zsize)){
        if(zsize<1000){
            $('#f_score_score').val(0);
        }else {
            $('#f_score_score').val(2*parseInt(zsize/1000));
        }

    }
});

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/score/saveScore" id="f_score_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushScoreTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="scoreType" value="4">
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
                        <label for="f_score_name" class="control-label x100"><span class="red">*</span>名称：</label>
                        <input type="text" name="name" id="f_score_name" value="${entity.name }" data-rule="required" maxlength="50" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_pnum" class="control-label x100"><span class="red">*</span>篇数：</label>
                        <input type="text" name="pnum" id="f_score_pnum" value="${entity.pnum }" data-rule="required;digits" maxlength="10" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_zsize" class="control-label x100"><span class="red">*</span>字数：</label>
                        <input type="text" name="zsize" id="f_score_zsize" value="${entity.zsize }" data-rule="required;digits" maxlength="10" size="20">
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_csource" class="control-label x100"><span class="red">*</span>出版源：</label>
                        <input type="text" name="csource" id="f_score_csource" value="${entity.csource }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_scoreRoleType" class="control-label x100"><span class="red">*</span>角色：</label>
                        <select id="f_score_scoreRoleType" name="scoreRoleType"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" data-live-search="true">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${scoreRoleTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.scoreRoleType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_memo" class="control-label x100"><span class="red">*</span>摘要：</label>
                        <textarea name="memo" id="f_score_memo" data-toggle="autoheight" cols="40" rows="2" maxlength="400" data-rule="required">${entity.memo}</textarea>
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_score" class="control-label x100"><span class="red">*</span>所获学时：</label>
                        <input type="text" name="score" id="f_score_score" value="${entity.score }" data-rule="required;digits;range[2~]" readonly maxlength="11" size="20">
                    </td>
                </tr>
                 <tr>
                     <td>
                         <label><span style="color:#ff3700;font-weight: normal;"><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申报说明：&nbsp;&nbsp;在公开发行的报刊杂志发表论文或公开出版专著的，按2学时/千字登记非集中培训学时。
                                            <br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;满1000字最多填写2学时，不满1000，不得申报学时。
                         </span></label>
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