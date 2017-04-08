<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushSchoolTableEdit(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('#f_school_form_edit').bjuiajax('doSearch', {url:'${ctx}/core/basic/school/schoolProfileEdit/${entity.id}'});
		//$(this).dialog('closeCurrent');
	}
}

<c:if test="${sessionScope.currentUser.type ==4}">
	$('#f_school_form_td').css('display','none'); 
</c:if>
</script>
<div class="bjui-pageContent">
    <form action="${ctx}/core/basic/school/saveSchoolProfile" id="f_school_form_edit" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushSchoolTableEdit">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-bordered table-hover" style="width:40%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td id="f_school_form_td">
                    <!-- 
                        <label for="f_school_area" class="control-label x120"><span class="red">*</span>地区：</label>
                         -->
                        	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
			            	 <select id="school_form_edit_province" name="province" data-toggle="selectpicker" 
			             	data-nextselect="#school_form_edit_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
								<option value="">--省市--</option>
				               	<c:forEach items="${provinces }" var="item">
				                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==entity.province}">selected</c:if>>${item.provinceName }</option>
				                </c:forEach>
			                </select>
			                </div>
			                 <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
			        		<select name="city" id="school_form_edit_city" data-toggle="selectpicker" 
			        			 data-nextselect="#school_form_edit_town" 
			       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
			        			   data-size="20" data-width="140px">
			        			 <option value="">--城市--</option>
			        			 <c:forEach items="${citys }" var="item">
			                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==entity.city}">selected</c:if>>${item.cityName }</option>
			                	</c:forEach>
			            	</select>
			            	</div>
			            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
				            <select name="town" id="school_form_edit_town" data-toggle="selectpicker" data-rule="required"
				            	data-nextselect="#school_form_edit_school" 
				            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
				            	 data-size="20" data-width="140px">
				            	<option value="">--区县--</option>
			        			 <c:forEach items="${towns }" var="item">
			                     		<option value="${item.townNo }" <c:if test="${item.townNo==entity.town}">selected</c:if>>${item.townName }</option>
			                	</c:forEach>
				            </select>
				            </div>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_name" class="control-label x120"><span class="red">*</span>学校名称：</label>
                        <input type="text" name="name" id="f_school_edit_name" value="${entity.name }" data-rule="required" maxlength="100" size="43">
                    </td>
                </tr>
                <c:if test="${not empty entity.id}">
				 <tr>
                    <td>
                        <label for="f_school_edit_schoolNo" class="control-label x120">学校编号(账号)：</label>
                        <input type="text" name="schoolNo" id="f_school_edit_schoolNo" value="${entity.schoolNo }" disabled size="20">
                        <span style="color:red">系统自动生成并作为学校管理员登陆账号。</span>
                    </td>
                </tr>
                </c:if>
				 <tr>
                    <td>
                        <label for="f_school_edit_category" class="control-label x120"><span class="red">*</span>学校类别：</label>
                        <select id="f_school_edit_category" name="category" data-toggle="selectpicker" data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${categorys }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.category}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_school_edit_stype" class="control-label x120"><span class="red">*</span>学校大类型：</label>
                        <select id="f_school_edit_stype" name="schoolType" data-toggle="selectpicker" data-rule="required" data-width="200px">
                        	<option  value="">--请选择--</option>
                        	<c:forEach items="${schoolTypes }" var="item">
                        		<option value="${item.value }" <c:if test="${item.value==entity.schoolType}">selected</c:if>>${item.desc }</option>
                        	</c:forEach>
						</select>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_type" class="control-label x120"><span class="red">*</span>学校类型：</label>
                        <select id="f_school_edit_type" name="type" data-toggle="selectpicker"  data-rule="required" data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${types }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.type}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_address" class="control-label x120">学校地址：</label>
                        <input type="text" name="address" id="f_school_edit_address" value="${entity.address }"  maxlength="100" size="43">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_postCode" class="control-label x120">邮编：</label>
                        <input type="text" name="postCode" id="f_school_edit_postCode" value="${entity.postCode }" data-rule="postcode" maxlength="6" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_master" class="control-label x120"><span class="red">*</span>校长：</label>
                        <input type="text" name="master" id="f_school_edit_master" value="${entity.master }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_tel" class="control-label x120"><span class="red">*</span>校办电话：</label>
                        <input type="text" name="tel" id="f_school_edit_tel" value="${entity.tel }" data-rule="required;tel" maxlength="13" data-rule="required;tel" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_director" class="control-label x120">负责人：</label>
                        <input type="text" name="director" id="f_school_edit_director" value="${entity.director }" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_idCard" class="control-label x120">负责人身份证：</label>
                        <input type="text" name="idCard" id="f_school_edit_idCard" value="${entity.idCard }" data-rule="ID_card" maxlength="18" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_theTel" class="control-label x120">负责人电话：</label>
                        <input type="text" name="theTel" id="f_school_edit_theTel" value="${entity.theTel }" data-rule="tel" maxlength="13" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_phone" class="control-label x120">负责人手机：</label>
                        <input type="text" name="phone" id="f_school_edit_phone" value="${entity.phone }" data-rule="mobile" maxlength="11" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_edit_email" class="control-label x120">负责人邮箱：</label>
                        <input type="text" name="email" id="f_school_edit_email" value="${entity.email }" data-rule="email" maxlength="100" size="20">
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