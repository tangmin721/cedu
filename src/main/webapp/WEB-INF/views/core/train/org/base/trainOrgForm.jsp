<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTrainOrgTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.train_org_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/org/base/trainOrgList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/org/base/saveTrainOrg" id="f_train_org_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushTrainOrgTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_train_org_name" class="control-label x100"><span class="red">*</span>名称：</label>
                        <input type="text" name="name" id="f_train_org_name" value="${entity.name }" data-rule="required" maxlength="25" size="25">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_type" class="control-label x100"><span class="red">*</span>类型：</label>
                        <select id="f_train_org_type" name="type" data-toggle="selectpicker" data-rule="required" data-width="250px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${orgTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.type}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_level" class="control-label x100">级别：</label>
                        <select id="f_train_org_level" name="level" data-toggle="selectpicker"  data-width="250px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${orgLevels }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.level}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_address" class="control-label x100">地址：</label>
                        <input type="text" name="address" id="f_train_org_address" value="${entity.address }"  maxlength="100" size="25">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_postCode" class="control-label x100">邮编：</label>
                        <input type="text" name="postCode" id="f_train_org_postCode" value="${entity.postCode }" data-rule="postcode" maxlength="6" size="25">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_linkman" class="control-label x100"><span class="red">*</span>联系人：</label>
                        <input type="text" name="linkman" id="f_train_org_linkman" value="${entity.linkman }" data-rule="required" maxlength="25" size="25">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_idCard" class="control-label x100">联系人身份证：</label>
                        <input type="text" name="idCard" id="f_train_org_idCard" value="${entity.idCard }" data-rule="ID_card" maxlength="18"  size="25">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_tel" class="control-label x100"><span class="red">*</span>联系电话：</label>
                        <input type="text" name="tel" id="f_train_org_tel" value="${entity.tel }" data-rule="required;tel" maxlength="25" size="25">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_phone" class="control-label x100">联系人手机：</label>
                        <input type="text" name="phone" id="f_train_org_phone" value="${entity.phone }" data-rule="mobile" maxlength="11" size="25">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_email" class="control-label x100">Email：</label>
                        <input type="text" name="email" id="f_train_org_email" value="${entity.email }"  data-rule="email" maxlength="100" size="25">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_train_org_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_train_org_memo" data-toggle="autoheight" cols="25" rows="2" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="TrainOrg:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>