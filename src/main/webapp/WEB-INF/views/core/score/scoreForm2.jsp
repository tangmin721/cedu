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


$('#f_score_year').on('change', function(e) {
    $('#f_score_score').val("");
    $('#f_score_form').isValid(function(v) {
    });
});

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/score/saveScore" id="f_score_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushScoreTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="scoreType" value="2">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_score_type" class="control-label x100">学时类型：</label>
                        <input name="type" type="hidden" id="f_score_type" value="0">
                       	 集中培训
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_degree" class="control-label x100"><span class="red">*</span>学历提升：</label>
                        <select id="f_score_degree" name="degree"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" data-live-search="true">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${degrees }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.degree}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_startdate" class="control-label x100"><span class="red">*</span>开始时间：</label>
                        <input type="text" name="startDate" id="f_score_startdate" value='<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_score_endDate" class="control-label x100"><span class="red">*</span>结束时间：</label>
                        <input type="text" name="endDate" id="f_score_endDate" value='<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_univ" class="control-label x100"><span class="red">*</span>毕业院校：</label>
                        <input type="text" name="univ" id="f_score_univ" value="${entity.univ }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_major" class="control-label x100"><span class="red">*</span>所学专业：</label>
                        <input type="text" name="major" id="f_score_major" value="${entity.major }" data-rule="required" maxlength="20" size="20">
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
                        <label for="f_score_score" class="control-label x100"><span class="red">*</span>所获学时：</label>
                        <input type="text" name="score" id="f_score_score" value="${entity.score }" data-rule="required;digits;remote[post:${ctx}/core/score/checkScore, id, tid, year, scoreType, version, type, score]" maxlength="3" size="20">
                    </td>
                </tr>
                 <tr>
                     <td>
                         <label><span style="color:#ff3700;font-weight: normal;"><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申报说明：&nbsp;&nbsp;一个继续教育周期内学历提升累计登记不能超过120学时，超过部分登记为非集中培训学时。</span></label>
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