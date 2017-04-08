<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
    function adminRefushScoreTable(json){
        if(json.statusCode!='200'){
            $(this).alertmsg('error', json.message);
        }else{
            $('#check_score_list_search').trigger('click');
            $(this).dialog('close','admin_edit_form');
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
    <form action="${ctx}/core/score/adminUpdateScore" id="f_score_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="adminRefushScoreTable">
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
                        <label class="control-label x100">年度：</label>
                        <c:forEach items="${years }" var="item">
                            <c:if test="${item.id==entity.year}">${item.name }</c:if>
                        </c:forEach>
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label class="control-label x100">名称：</label>
                        ${entity.name }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="control-label x100">篇数：</label>
                        ${entity.pnum }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="control-label x100">字数：</label>
                        ${entity.zsize }
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label class="control-label x100">出版源：</label>
                        ${entity.csource }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label class="control-label x100">角色：</label>
                        <c:forEach items="${scoreRoleTypes }" var="item">
                            <c:if test="${item.id==entity.scoreRoleType}">${item.name }</c:if>
                        </c:forEach>
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_memo" class="control-label x100">摘要：</label>
                        ${entity.memo }
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_score" class="control-label x100"><span class="red">*</span>所获学时：</label>
                        <input type="text" name="score" id="f_score_score" value="${entity.score }" data-rule="required;digits;range[2~]"  maxlength="3" size="20">
                    </td>
                </tr>
                 <shiro:hasRole name="SCHOOL_ADMIN">
                     <tr>
                         <td colspan="2">
                             <label for="f_score_schoolmemo" class="control-label x100"><span class="red">*</span>修改说明：</label>
                             <textarea name="schoolUpdateMemo" id="f_score_schoolmemo" data-toggle="autoheight"  data-rule="required" cols="40" rows="2" maxlength="400">${entity.schoolUpdateMemo}</textarea>
                         </td>
                     </tr>
                 </shiro:hasRole>
                 <shiro:hasRole name="TOWN_ADMIN">
                     <tr>
                         <td colspan="2">
                             <label for="f_score_townmemo" class="control-label x100"><span class="red">*</span>修改说明：</label>
                             <textarea name="townUpdateMemo" id="f_score_townmemo" data-toggle="autoheight"  data-rule="required" cols="40" rows="2" maxlength="400">${entity.townUpdateMemo}</textarea>
                         </td>
                     </tr>
                 </shiro:hasRole>
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
        <li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
    </ul>
</div>