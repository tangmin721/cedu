<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushScoreTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.score_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/score/scoreList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/score/saveScore" id="f_score_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushScoreTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_score_tid" class="control-label x100"><span class="red">*</span>字段0：</label>
                        <input type="text" name="tid" id="f_score_tid" value="${entity.tid }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_tname" class="control-label x100"><span class="red">*</span>字段1：</label>
                        <input type="text" name="tname" id="f_score_tname" value="${entity.tname }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_tno" class="control-label x100"><span class="red">*</span>字段2：</label>
                        <input type="text" name="tno" id="f_score_tno" value="${entity.tno }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_province" class="control-label x100"><span class="red">*</span>字段3：</label>
                        <input type="text" name="province" id="f_score_province" value="${entity.province }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_city" class="control-label x100"><span class="red">*</span>字段4：</label>
                        <input type="text" name="city" id="f_score_city" value="${entity.city }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_town" class="control-label x100"><span class="red">*</span>字段5：</label>
                        <input type="text" name="town" id="f_score_town" value="${entity.town }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_school" class="control-label x100"><span class="red">*</span>字段6：</label>
                        <input type="text" name="school" id="f_score_school" value="${entity.school }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_provinceName" class="control-label x100"><span class="red">*</span>字段7：</label>
                        <input type="text" name="provinceName" id="f_score_provinceName" value="${entity.provinceName }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_cityName" class="control-label x100"><span class="red">*</span>字段8：</label>
                        <input type="text" name="cityName" id="f_score_cityName" value="${entity.cityName }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_townName" class="control-label x100"><span class="red">*</span>字段9：</label>
                        <input type="text" name="townName" id="f_score_townName" value="${entity.townName }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_schoolName" class="control-label x100"><span class="red">*</span>字段10：</label>
                        <input type="text" name="schoolName" id="f_score_schoolName" value="${entity.schoolName }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_pid" class="control-label x100"><span class="red">*</span>字段11：</label>
                        <input type="text" name="pid" id="f_score_pid" value="${entity.pid }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_pno" class="control-label x100"><span class="red">*</span>字段12：</label>
                        <input type="text" name="pno" id="f_score_pno" value="${entity.pno }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_name" class="control-label x100"><span class="red">*</span>字段13：</label>
                        <input type="text" name="name" id="f_score_name" value="${entity.name }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_score" class="control-label x100"><span class="red">*</span>字段14：</label>
                        <input type="text" name="score" id="f_score_score" value="${entity.score }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_type" class="control-label x100"><span class="red">*</span>字段15：</label>
                        <input type="text" name="type" id="f_score_type" value="${entity.type }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_scoreType" class="control-label x100"><span class="red">*</span>字段16：</label>
                        <input type="text" name="scoreType" id="f_score_scoreType" value="${entity.scoreType }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_year" class="control-label x100"><span class="red">*</span>字段17：</label>
                        <input type="text" name="year" id="f_score_year" value="${entity.year }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_degree" class="control-label x100"><span class="red">*</span>字段18：</label>
                        <input type="text" name="degree" id="f_score_degree" value="${entity.degree }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_startDate" class="control-label x100"><span class="red">*</span>字段19：</label>
                        <input type="text" name="startDate" id="f_score_startDate" value="${entity.startDate }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_endDate" class="control-label x100"><span class="red">*</span>字段20：</label>
                        <input type="text" name="endDate" id="f_score_endDate" value="${entity.endDate }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_major" class="control-label x100"><span class="red">*</span>字段21：</label>
                        <input type="text" name="major" id="f_score_major" value="${entity.major }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_univ" class="control-label x100"><span class="red">*</span>字段22：</label>
                        <input type="text" name="univ" id="f_score_univ" value="${entity.univ }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_pnum" class="control-label x100"><span class="red">*</span>字段23：</label>
                        <input type="text" name="pnum" id="f_score_pnum" value="${entity.pnum }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_zsize" class="control-label x100"><span class="red">*</span>字段24：</label>
                        <input type="text" name="zsize" id="f_score_zsize" value="${entity.zsize }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_csource" class="control-label x100"><span class="red">*</span>字段25：</label>
                        <input type="text" name="csource" id="f_score_csource" value="${entity.csource }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_scoreRoleType" class="control-label x100"><span class="red">*</span>字段26：</label>
                        <input type="text" name="scoreRoleType" id="f_score_scoreRoleType" value="${entity.scoreRoleType }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_level" class="control-label x100"><span class="red">*</span>字段27：</label>
                        <input type="text" name="level" id="f_score_level" value="${entity.level }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_memo" class="control-label x100"><span class="red">*</span>字段28：</label>
                        <input type="text" name="memo" id="f_score_memo" value="${entity.memo }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_status" class="control-label x100"><span class="red">*</span>字段29：</label>
                        <input type="text" name="status" id="f_score_status" value="${entity.status }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_checkDesc" class="control-label x100"><span class="red">*</span>字段30：</label>
                        <input type="text" name="checkDesc" id="f_score_checkDesc" value="${entity.checkDesc }" data-rule="required" maxlength="20" size="20">
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