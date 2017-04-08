<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTeacherPayTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.teacher_pay_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/teacherPay/teacherPayList/${tid}/${tfs_type}'});
		$(this).dialog('closeCurrent');
	}
}

$("#f_teacher_pay_insuranceHousFund").change(function (){
	var fund = $("#f_teacher_pay_insuranceHousFund option:selected").text();
	var str = fund.split(" ");
	for(var i = 0; i < str.length; i++){
		if(str[i] == "0-无" && str.length > 2){
			$(this).alertmsg('warn', '选择【0-无】时，不能进行其他选项的选择！')
			//$("#f_teacher_pay_insuranceHousFund option").selectpicker('deselectAll');
			$("#f_teacher_pay_insuranceHousFund option").removeAttr("selected");
			$("#f_teacher_pay_insuranceHousFund").selectpicker('refresh');
			return;
		}
	}
});

function getYear(){
    var date = new Date();
    var currentdate = date.getFullYear();
    return currentdate;
}

//监听年度变化信息
$("#f_teacher_pay_year").on('change',function (){
	var year = $("#f_teacher_pay_year option:selected").text();
	if(year != ''){
		if(getYear() <= year){
			$("#f_teacher_pay_year option").removeAttr("selected");
			$("#f_teacher_pay_year").selectpicker('refresh');
			$(this).alertmsg('warn', '不能选择本年度及未来年度，为空时默认为上一年度！');
		}else{
			
		}
	}else{
		
	}
});
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/teacher/teacherPay/saveTeacherPay" method="post" id="f_teacher_pay_form" data-toggle="validate" data-alertmsg="false" data-callback="refushTeacherPayTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_teacher_pay_seq" class="control-label x170"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_teacher_pay_seq" value="${entity.seq }" data-rule="required;digits" size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_pay_year" class="control-label x170">年度：</label>
                        <select id="f_teacher_pay_year" name="year" data-toggle="selectpicker" data-width="200px">
                        	<option value="">--请选择--</option>
			               	<c:forEach items="${years }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.year}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select><span class="red">&nbsp;未满一年请下年度填报；默认为上一年度</span>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_pay_yearPay" class="control-label x170"><span class="red">*</span>年工资收入(元)：</label>
                        <input type="text" name="yearPay" id="f_teacher_pay_yearPay" value="<c:if test="${empty entity.yearPay}">0</c:if><c:if test="${not empty entity.yearPay}">${entity.yearPay }</c:if>" data-rule="required;number" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_pay_basicPay" class="control-label x170"><span class="red">*</span>基本工资(元/月)：</label>
                        <input type="text" name="basicPay" id="f_teacher_pay_basicPay" value="<c:if test="${empty entity.basicPay}">0</c:if><c:if test="${not empty entity.basicPay}">${entity.basicPay }</c:if>" data-rule="required;number" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_pay_meritPay" class="control-label x170"><span class="red">*</span>绩效工资(元/月)：</label>
                        <input type="text" name="meritPay" id="f_teacher_pay_meritPay" value="<c:if test="${empty entity.meritPay}">0</c:if><c:if test="${not empty entity.meritPay}">${entity.meritPay }</c:if>" data-rule="required;number" maxlength="20" size="20">
                    </td>
                </tr>
                <c:if test="${tfs_type == 0 }">
				 <tr>
                    <td>
                        <label for="f_teacher_pay_countrySubsidy" class="control-label x170"><span class="red">*</span>乡村教师生活补助(元/月)：</label>
                        <input type="text" name="countrySubsidy" id="f_teacher_pay_countrySubsidy" value="<c:if test="${empty entity.countrySubsidy}">0</c:if><c:if test="${not empty entity.countrySubsidy}">${entity.countrySubsidy }</c:if>" data-rule="required;number" maxlength="20" size="20">
                    </td>
                </tr>
                </c:if>
                <c:if test="${tfs_type == 1 || tfs_type == 3 || tfs_type == 2}">
				 <tr>
                    <td>
                        <label for="f_teacher_pay_subsidy" class="control-label x170"><span class="red">*</span>
                        <c:choose>
       						<c:when test="${tfs_type == 2 }">
								特教津贴补贴(元/月)：
       						</c:when>
       						<c:otherwise>
								津贴补贴(元/月)：
       						</c:otherwise>
						</c:choose>
                        </label>
                        <input type="text" name="subsidy" id="f_teacher_pay_subsidy" value="<c:if test="${empty entity.subsidy}">0</c:if><c:if test="${not empty entity.subsidy}">${entity.subsidy }</c:if>" data-rule="required;number" maxlength="20" size="20">
                    </td>
                </tr>
                </c:if>
                <c:if test="${tfs_type == 0 || tfs_type == 2}">
				 <tr>
                    <td>
                        <label for="f_teacher_pay_otherSubsidy" class="control-label x170"><span class="red">*</span>其他津贴补贴(元/月)：</label>
                        <input type="text" name="otherSubsidy" id="f_teacher_pay_otherSubsidy" value="<c:if test="${empty entity.otherSubsidy}">0</c:if><c:if test="${not empty entity.otherSubsidy}">${entity.otherSubsidy }</c:if>" data-rule="required;number" maxlength="20" size="20">
                    </td>
                </tr>
                </c:if>
				 <tr>
                    <td>
                        <label for="f_teacher_pay_otherPay" class="control-label x170"><span class="red">*</span>其他(元/月)：</label>
                        <input type="text" name="otherPay" id="f_teacher_pay_otherPay" value="<c:if test="${empty entity.otherPay}">0</c:if><c:if test="${not empty entity.otherPay}">${entity.otherPay }</c:if>" data-rule="required;number" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_pay_insuranceHousFund" class="control-label x170"><span class="red">*</span>五险一金：</label>
                        <select id="f_teacher_pay_insuranceHousFund" name="insuranceHousFund" data-toggle="selectpicker" multiple data-rule="required" data-width="200px">
			               	<c:forEach items="${insuranceHousFunds }" var="item">
			               		<option value="${item.id}" <c:if test="${item.checked}">selected</c:if>> ${item.name}</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TeacherPay:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>