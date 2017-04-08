<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherScoreTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('#score_detail_search').trigger('click');
		$(this).dialog('close','teacher-score-giveform');
	}
}

$('#f_teacher_score_checkStatus').bootstrapSwitch();

$('#f_teacher_score_status').bootstrapSwitch();
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/score/saveTeacherScore" method="post" id="f_teacher_score_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherScoreTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid }">
        <input type="hidden" name="pid" value="${entity.pid }">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-13px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_score_score" class="control-label x100"><span class="red">*</span>学分：</label>
                        <input type="text" name="score" id="f_teacher_score_score" value="${entity.score }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_score_hour" class="control-label x100"><span class="red">*</span>学时：</label>
                        <input type="text" name="hour" id="f_teacher_score_hour" value="${entity.hour }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_score_checkStatus" class="control-label x100"><span class="red">*</span>考核状态：</label>
                        <input id="f_teacher_score_checkStatus" type="checkbox"  name="status"  data-size="mini" data-on-color="success" 
                        	data-off-color="danger" data-on-text="合格"  data-off-text="不及格" <c:if test="${entity.status }">checked</c:if>>
                    </td>
                </tr>
                、 		<tr>
                    <td>
                        <label for="f_teacher_score_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_teacher_score_memo" data-toggle="autoheight" cols="20" rows="2" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherScore:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>