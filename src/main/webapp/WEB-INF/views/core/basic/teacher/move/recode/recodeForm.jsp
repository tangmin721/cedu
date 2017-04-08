<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherMoveRecodeTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
	    $('#pojo-search-btn').trigger("click");
        $(this).navtab('reloadFlag','ntv-move-in');
        $(this).navtab('reloadFlag','mtv-move-out');
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/move/recode/saveRecode" method="post" id="f_teacher_move_recode_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherMoveRecodeTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                     <td id="f_teacher_area_td" colspan="2">
                         <label  class="control-label x100"><span class="red">*</span>调往学校：</label>
                                 <div style="display: none;">
                                     <select id="recode_form_province" name="tprovince" data-toggle="selectpicker"
                                             data-nextselect="#recode_form_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
                                         <option value="">--省市--</option>
                                         <c:forEach items="${tprovinces }" var="item">
                                             <option value="${item.provinceNo }" <c:if test="${item.provinceNo==entity.province}">selected</c:if>>${item.provinceName }</option>
                                         </c:forEach>
                                     </select>
				 </div>
                                     <select name="tcity" id="recode_form_city" data-toggle="selectpicker"
                                             data-nextselect="#recode_form_town"
                                             data-refurl="${ctx}/system/area/town/towns?city={value}"
                                             data-size="20" data-width="140px">
                                         <option value="">--城市--</option>
                                         <c:forEach items="${tcitys }" var="item">
                                             <option value="${item.cityNo }" <c:if test="${item.cityNo==entity.city}">selected</c:if>>${item.cityName }</option>
                                         </c:forEach>
                                     </select>
                                 
                                 <select name="ttown" id="recode_form_town" data-toggle="selectpicker"
                                         data-nextselect="#recode_form_school"
                                         data-refurl="${ctx}/core/basic/school/schools?town={value}"
                                         data-size="20" data-width="140px">
                                     <option value="">--区县--</option>
                                     <c:forEach items="${ttowns }" var="item">
                                         <option value="${item.townNo }" <c:if test="${item.townNo==entity.town}">selected</c:if>>${item.townName }</option>
                                     </c:forEach>
                                 </select>
                                 <select name="tschool" id="recode_form_school" data-toggle="selectpicker"
                                         data-size="20" data-width="140px" data-live-search="true" data-rule="required">
                                     <option value="">--学校--</option>
                                     <c:forEach items="${tschools }" var="item">
                                         <option value="${item.id }" >${item.name }</option>
                                     </c:forEach>
                                 </select>
                     </td>
                </tr>
                 <tr>
                     <td>
                         <label for="f_teacher_move_recode_sendMemo" class="control-label x100"><span class="red">*</span>调动原因：</label>
                         <textarea name="sendMemo" id="f_teacher_move_recode_sendMemo" data-toggle="autoheight" data-rule="required" cols="57" rows="4" maxlength="400">${entity.sendMemo }</textarea>
                     </td>
                 </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherMovePojo:move">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>