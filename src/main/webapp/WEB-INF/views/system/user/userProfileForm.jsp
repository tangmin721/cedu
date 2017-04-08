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

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/user/saveUser" id="f_user_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushUserTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	<tr>
                    <td>
                        <label for="ff_user_type" class="control-label x100">用户类型：</label>
                        <c:forEach items="${userTypes }" var="item">
                        	<c:if test="${item.value==entity.type}">${item.desc }</c:if>	
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td id="ff_user_area_td">
                       <label for="user_updateform_province" class="control-label x100">地区：</label>
                       			 <c:forEach items="${provinces }" var="item">
				               		<c:if test="${item.provinceNo==entity.province}">${item.provinceName }</c:if>
				                </c:forEach>
				                 <c:forEach items="${citys }" var="item">
				               		<c:if test="${item.cityNo==entity.city}">${item.cityName }</c:if>
				                </c:forEach>
				                 <c:forEach items="${towns }" var="item">
				               		<c:if test="${item.townNo==entity.town}">${item.townName }</c:if>
				                </c:forEach>
				                 <c:forEach items="${schools }" var="item">
				               		<c:if test="${item.id==entity.school}">${item.name }</c:if>
				                </c:forEach>
                    </td>
                </tr>
                 <tr id="ff_train_org_td" style="display:none">
                    <td>
                        <label for="ff_user_org" class="control-label x100">培训机构：</label>
                         <c:forEach items="${trainOrgs }" var="item">
                        	<c:if test="${item.id==entity.org}">${item.name }</c:if>	
                        </c:forEach>
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_user_unit" class="control-label x100">单位：</label>
                        ${entity.unit }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_loginName" class="control-label x100">登录名：</label>
                        ${entity.loginName }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_realName" class="control-label x100">姓名：</label>
                        ${entity.realName }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_user_mobileNo" class="control-label x100">手机号：</label>
                        ${entity.mobileNo }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_user_email" class="control-label x100">Email：</label>
                        ${entity.email }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_user_status" class="control-label x100">状态：</label>
                        <c:forEach items="${userStatus }" var="item">
                        	<c:if test="${item.value==entity.status}">${item.desc }</c:if>	
                        </c:forEach>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
    </ul>
</div>