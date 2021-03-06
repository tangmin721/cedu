<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>

<script type="text/javascript">
    function adminRefushScoreTable(json) {
        if (json.statusCode != '200') {
            $(this).alertmsg('error', json.message);
        } else {
            $('#check_score_list_search').trigger('click');
            $(this).dialog('close', 'admin_edit_form');
        }
    }

    $('#f_score_year').on('change', function(e) {
        $('#f_score_score').val("");
        $('#f_score_form').isValid(function(v) {
        });
    });

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/score/adminUpdateScore" id="f_score_form" method="post" data-toggle="validate"
          data-alertmsg="false" data-callback="adminRefushScoreTable">
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
                    <label class="control-label x100">学历提升：</label>
                    <c:forEach items="${degrees }" var="item">
                        <c:if test="${item.id==entity.degree}">${item.name }</c:if>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x100">开始时间：</label>
                    <fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x100">结束时间：</label>
                    <fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x100">毕业院校：</label>
                    ${entity.univ }
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x100">所学专业：</label>
                    ${entity.major }
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
                    <label for="f_score_score" class="control-label x100"><span class="red">*</span>所获学时：</label>
                    <input type="text" name="score" id="f_score_score" value="${entity.score }"
                           data-rule="required;digits;remote[post:${ctx}/core/score/checkScore, id, tid, year, scoreType, version, type, score]"
                           maxlength="3" size="20">
                </td>
            </tr>
            <shiro:hasRole name="SCHOOL_ADMIN">
                <tr>
                    <td colspan="2">
                        <label for="f_score_schoolmemo" class="control-label x100"><span
                                class="red">*</span>修改说明：</label>
                        <textarea name="schoolUpdateMemo" id="f_score_schoolmemo" data-toggle="autoheight" data-rule="required" cols="40"
                                  rows="2" maxlength="400">${entity.schoolUpdateMemo}</textarea>
                    </td>
                </tr>
            </shiro:hasRole>
            <shiro:hasRole name="TOWN_ADMIN">
                <tr>
                    <td colspan="2">
                        <label for="f_score_townmemo" class="control-label x100"><span class="red">*</span>修改说明：</label>
                        <textarea name="townUpdateMemo" id="f_score_townmemo" data-toggle="autoheight" data-rule="required"  cols="40"
                                  rows="2" maxlength="400">${entity.townUpdateMemo}</textarea>
                    </td>
                </tr>
            </shiro:hasRole>
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
        <li>
            <button type="button" class="btn-close" data-icon="close">取消</button>
        </li>
        <li>
            <button type="submit" class="btn-blue" data-icon="save">保存</button>
        </li>
    </ul>
</div>