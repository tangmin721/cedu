<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProjectQuotaTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$(this).dialog('closeCurrent');
		$('#project_quota_search').trigger("click");//刷新查询
	}
}

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/quota/saveProjectQuota" id="f_project_quota_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushProjectQuotaTable">
        <input type="hidden" name="id" value="${entity.id}">
         <input type="hidden" name="pid" value="${pid }">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	 <tr>
                    <td>
                        <label for="f_project_quota_seq" class="control-label x100"><span class="red">*</span>排序：</label>
                        <input type="text" name="seq" id="f_project_quota_seq" value="${entity.seq }" data-rule="required;digits" maxlength="20" size="20">
                    </td>
                 </tr>
				 <tr>
                    <td  colspan="2" id="f_project_quota_area_td">
                        <label for="f_project_area" class="control-label x100">省市县校：</label>
                        <shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
                        	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
			            	 <select id="project_form_province" name="province" data-toggle="selectpicker" 
			             	data-nextselect="#project_form_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="10" data-width="140px">
								<option value="">--省市--</option>
				               	<c:forEach items="${provinces }" var="item">
				                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==entity.province}">selected</c:if>>${item.provinceName }</option>
				                </c:forEach>
			                </select>
			                </div>
			                 <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
			        		<select name="city" id="project_form_city" data-toggle="selectpicker" 
			        			 data-nextselect="#project_form_town" 
			       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
			        			   data-size="10" data-width="140px">
			        			 <option value="">--城市--</option>
			        			 <c:forEach items="${citys }" var="item">
			                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==entity.city}">selected</c:if>>${item.cityName }</option>
			                	</c:forEach>
			            	</select>
			            	</div>
			            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
				            <select name="town" id="project_form_town" data-toggle="selectpicker" 
				            	data-nextselect="#project_form_school" 
				            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
				            	 data-size="10" data-width="140px">
				            	<option value="">--区县--</option>
			        			 <c:forEach items="${towns }" var="item">
			                     		<option value="${item.townNo }" <c:if test="${item.townNo==entity.town}">selected</c:if>>${item.townName }</option>
			                	</c:forEach>
				            </select>
				            </div>
				             <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
				            <select name="school" id="project_form_school" data-toggle="selectpicker"  <c:if test="${sessionScope.currentUser.school!=0}">style="display: none;"</c:if>
				            	 data-size="10" data-width="140px" data-live-search="true">
				            	<option value="">--学校--</option>
			        			 <c:forEach items="${schools }" var="item">
			                     		<option value="${item.id }" <c:if test="${item.id==entity.school}">selected</c:if>>${item.name }</option>
			                	</c:forEach>
				            </select>
				            </div>
			            </shiro:hasAnyRoles>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_quota_num" class="control-label x100"><span class="red">*</span>名额人数：</label>
                        <input type="text" name="num" id="f_project_quota_num" value="${entity.num }" data-rule="required;digits" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_quota_memo" class="control-label x100">备注：</label>
                        <textarea name="memo" id="f_project_quota_memo" data-toggle="autoheight" cols="20" rows="2" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="ProjectQuota:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>