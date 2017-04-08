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
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label class="control-label x100"><span class="red">*</span>名称：</label>
                        ${entity.name }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="control-label x100"><span class="red">*</span>机构账号：</label>
                        ${entity.orgNo }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label class="control-label x100"><span class="red">*</span>类型：</label>
			               	<c:forEach items="${orgTypes }" var="item">
			               		 <c:if test="${item.id==entity.type}">${item.name }</c:if>
			               	</c:forEach>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label  class="control-label x100">级别：</label>

			               	<c:forEach items="${orgLevels }" var="item">
			               		<c:if test="${item.id==entity.level}">${item.name }</c:if>
			               	</c:forEach>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label class="control-label x100">地址：</label>
                        ${entity.address }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label  class="control-label x100">邮编：</label>
                        ${entity.postCode }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label  class="control-label x100"><span class="red">*</span>联系人：</label>
                        ${entity.linkman }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label class="control-label x100"><span class="red">*</span>联系电话：</label>
                        ${entity.tel }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label class="control-label x100">联系人手机：</label>
                        ${entity.phone }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label class="control-label x100">Email：</label>
                        ${entity.email }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label  class="control-label x100">备注：</label>
                        ${entity.memo}
                    </td>
                </tr>
            </tbody>
        </table>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
    </ul>
</div>