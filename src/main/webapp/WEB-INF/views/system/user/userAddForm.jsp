<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushUserTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.user_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/user/userList'});
		$(this).dialog('closeCurrent');
	}
}

$('#f_user_type').on('change', function (e) {
	  var id = $('#f_user_type').val();
	  
	  if(id==0 || id==1){
		  $('#f_user_area_td').css('display','none'); 
		  $('#f_train_org_td').css('display','none');
		  $('#user_addform_province').selectpicker('hide');
		  $('#user_addform_city').selectpicker('hide');
		  $('#user_addform_town').selectpicker('hide');
		  $('#user_addform_school').selectpicker('hide');
	  }
	  
	  if(id==2){
		  $('#f_user_area_td').css('display','block'); 
		  $('#f_train_org_td').css('display','none');
		  $('#user_addform_province').selectpicker('show');
		  $('#user_addform_city').selectpicker('hide');
		  $('#user_addform_town').selectpicker('hide');
		  $('#user_addform_school').selectpicker('hide');
	  }
	  if(id==3){
		  $('#f_user_area_td').css('display','block'); 
		  $('#f_train_org_td').css('display','none');
		  $('#user_addform_province').selectpicker('show');
		  $('#user_addform_city').selectpicker('show');
		  $('#user_addform_town').selectpicker('hide');
		  $('#user_addform_school').selectpicker('hide');
	  }
	  if(id==4){
		  $('#f_user_area_td').css('display','block'); 
		  $('#f_train_org_td').css('display','none');
		  $('#user_addform_province').selectpicker('show');
		  $('#user_addform_city').selectpicker('show');
		  $('#user_addform_town').selectpicker('show');
		  $('#user_addform_school').selectpicker('hide');
	  }
	  if(id!=0 && id!=1 && id!=2 &&id!=3 &&id!=4){
		  $('#f_train_org_td').css('display','none');
		  $('#f_user_area_td').css('display','block'); 
		  $('#user_addform_province').selectpicker('show');
		  $('#user_addform_city').selectpicker('show');
		  $('#user_addform_town').selectpicker('show');
		  $('#user_addform_school').selectpicker('show');
		  
	  }
	  if(id==9){
		  $('#f_train_org_td').css('display','block');
		  $('#f_user_area_td').css('display','none');
		  $('#user_addform_province').selectpicker('hide');
		  $('#user_addform_city').selectpicker('hide');
		  $('#user_addform_town').selectpicker('hide');
		  $('#user_addform_school').selectpicker('hide');
	  }
});

$('#f_user_reflag').bootstrapSwitch();
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/user/saveUser" id="f_user_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushUserTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	<tr>
                    <td>
                        <label for="f_user_type" class="control-label x100" ><span class="red">*</span>用户类型：</label>
                        <select id="f_user_type" name="type" data-toggle="selectpicker" data-rule="required" data-width="200px" >
                        	<option value="">--请选择--</option>
                        	<c:forEach items="${userTypes }" var="item">
                        		<c:if test="${item.value != 6 }">
								 <option  value="${item.value }">${item.desc }</option>
								</c:if>
                        	</c:forEach>
                        	</select>
                    </td>
                </tr>
                <tr>
                    <td id="f_user_area_td">
                        <label for="f_user_type" class="control-label x100"><span class="red">*</span>地区：</label>
                        <shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
                        	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
			            	 <select id="user_addform_province" name="province" data-toggle="selectpicker" 
			             	data-nextselect="#user_addform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
								<option value="">--省市--</option>
				               	<c:forEach items="${query.queryProvinces }" var="item">
				                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
				                </c:forEach>
			                </select>
			                </div>
			                 <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
			        		<select name="city" id="user_addform_city" data-toggle="selectpicker" 
			        			 data-nextselect="#user_addform_town" 
			       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
			        			   data-size="20" data-width="140px">
			        			 <option value="">--城市--</option>
			        			 <c:forEach items="${query.queryCitys }" var="item">
			                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
			                	</c:forEach>
			            	</select>
			            	</div>
			            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
				            <select name="town" id="user_addform_town" data-toggle="selectpicker" 
				            	data-nextselect="#user_addform_school" 
				            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
				            	 data-size="20" data-width="140px">
				            	<option value="">--区县--</option>
			        			 <c:forEach items="${query.queryTowns }" var="item">
			                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
			                	</c:forEach>
				            </select>
				            </div>
				             <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
				            <select name="school" id="user_addform_school" data-toggle="selectpicker"  <c:if test="${sessionScope.currentUser.school!=0}">style="display: none;"</c:if>
				            	 data-size="20" data-width="140px" data-live-search="true">
				            	<option value="">--学校--</option>
			        			 <c:forEach items="${query.querySchools }" var="item">
			                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
			                	</c:forEach>
				            </select>
				            </div>
			            </shiro:hasAnyRoles>
                    </td>
                </tr>
                <tr id="f_train_org_td" style="display:none">
                    <td>
                        <label for="f_user_org" class="control-label x100">培训机构：</label>
                         <select id="f_user_org" name="org"    data-toggle="selectpicker"  data-width="200px" data-live-search="true">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${trainOrgs }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.org}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_user_unit" class="control-label x100"><span class="red">*</span>单位：</label>
                        <input type="text" name="unit" id="f_user_unit" value="${entity.unit }" data-rule="required"  maxlength="50" size="20" >
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_loginName" class="control-label x100"><span class="red">*</span>登录名：</label>
                        <input type="text" name="loginName" id="f_user_loginName" value="${entity.loginName }" data-rule="required:username"  maxlength="20" size="20" >
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_user_newPwd1" class="control-label x100"><span class="red">*</span>密码：</label>
                        <input type="password" name="newPwd1" id="f_user_newPwd1"  data-rule="密码:required;password" maxlength="32"  size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_user_newPwd2" class="control-label x100"><span class="red">*</span>确认密码：</label>
                        <input type="password" name="newPwd2" id="f_user_newPwd2"  data-rule="确认密码:required;match(newPwd1);password"  maxlength="32"  size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_realName" class="control-label x100"><span class="red">*</span>姓名：</label>
                        <input type="text" name="realName" id="f_user_realName" value="${entity.realName }" data-rule="required" maxlength="18" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_mobileNo" class="control-label x100"><span class="red">*</span>手机号：</label>
                        <input type="text" name="mobileNo" id="f_user_mobileNo" value="${entity.mobileNo }" maxlength="11" data-rule="required;mobile" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_user_email" class="control-label x100"><span class="red">*</span>Email：</label>
                        <input type="text" name="email" id="f_user_email" value="${entity.email }" data-rule="required;email" maxlength="100" size="20">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_user_status" class="control-label x100"><span class="red">*</span>状态：</label>
                        <select name="status" data-toggle="selectpicker" data-rule="required" data-width="200px">
                        	<option  value="">--请选择--</option>
                        	<c:forEach items="${userStatus }" var="item">
                        		<option value="${item.value }" <c:if test="${item.value==entity.status}">selected</c:if>>${item.desc }</option>
                        	</c:forEach>
						</select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_user_reflag" class="control-label x100"><span class="red">*</span>是否开通发展网账号：</label>
						<input id="f_user_reflag" type="checkbox"  name="reflag"  data-size="mini" data-on-color="success" data-off-color="warning" data-on-text="开通"  data-off-text="不开通" <c:if test="${entity.reflag }">checked</c:if>>
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