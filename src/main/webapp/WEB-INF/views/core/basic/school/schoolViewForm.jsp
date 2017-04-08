<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="bjui-pageContent">
        <table class="table table-condensed table-hover table-bordered" style="width:100%">
            <tbody>
				 <tr>
				 <td colspan="2">
                        <label for="f_school_view_area" class="control-label x120">地区：</label>
                        <c:forEach items="${provinces }" var="item">
		                     <c:if test="${item.provinceNo==entity.province}">${item.provinceName }</c:if>
		                </c:forEach>
		                <c:forEach items="${citys }" var="item">
		                     <c:if test="${item.cityNo==entity.city}">${item.cityName }</c:if>
		                </c:forEach>
		                <c:forEach items="${towns }" var="item">
		                     <c:if test="${item.townNo==entity.town}">${item.townName }</c:if>
		                </c:forEach>
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_school_view_name" class="control-label x120">学校名称：</label>
                        ${entity.name }
                    </td>
                </tr>
				 <tr>
				 	<td>
                        <label for="f_school_view_schoolNo" class="control-label x120">学校编号(账号)：</label>
                        ${entity.schoolNo }
                    </td>
                    <td>
                        <label for="f_school_view_category" class="control-label x120">学校类别：</label>
                        <c:forEach items="${categorys }" var="item">
			               		<c:if test="${item.id==entity.category}">${item.name }</c:if>
			               	</c:forEach>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label for="f_school_view_stype" class="control-label x120">学校大类型：</label>
                        <c:forEach items="${schoolTypes }" var="item">
			               		<c:if test="${item.value==entity.schoolType}">${item.desc }</c:if>
			               	</c:forEach>
                    </td>
                </tr>
                <tr>
                	<td>
                        <label for="f_school_view_type" class="control-label x120">学校类型：</label>
                        <c:forEach items="${types }" var="item">
			               		<c:if test="${item.id==entity.type}">${item.name }</c:if>
			               	</c:forEach>
                    </td>
                    <td>
                        <label for="f_school_view_postCode" class="control-label x120">邮编：</label>
                        ${entity.postCode }
                    </td>
                
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_school_view_address" class="control-label x120">学校地址：</label>
                        ${entity.address }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_view_master" class="control-label x120">校长：</label>
                        ${entity.master }
                    </td>
                    <td>
                        <label for="f_school_view_tel" class="control-label x120">校办电话：</label>
                        ${entity.tel }
                    </td>
                </tr>
				 <tr>
				 	<td>
                        <label for="f_school_view_director" class="control-label x120">负责人：</label>
                        ${entity.director }
                    </td>
                    <td>
                        <label for="f_school_view_idCard" class="control-label x120">负责人身份证：</label>
                        ${entity.idCard }
                    </td>
                </tr>
                <tr>
                	<td>
                        <label for="f_school_view_theTel" class="control-label x120">负责人电话：</label>
                        ${entity.theTel }
                    </td>
                    <td>
                        <label for="f_school_view_phone" class="control-label x120">负责人手机：</label>
                        ${entity.phone }
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_school_view_email" class="control-label x120">负责人邮箱：</label>
                        ${entity.email }
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