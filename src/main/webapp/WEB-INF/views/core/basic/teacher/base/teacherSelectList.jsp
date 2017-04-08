<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_select_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_select_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_apply_add').on('click', function(e) {
	var list = select_teacher.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要添加的教师！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/roster/detail/saveProjectRosterDetail?applyId='+wizard_applyid,idname:"ids",group:"ids",confirmMsg:"确定要加入到名单中吗？",
				callback:function(json){
					$(this).alertmsg('ok',json.data,{title:"添加成功！"});
				}});
	}
});


var select_teacher = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_select_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}
</script>

<div class="bjui-pageHeader">
		<div class="btn-group pull-left" role="group">
			<shiro:hasPermission name="Teacher:create">
				<button type="button" id="but_teacher_apply_add" class="btn btn-blue" data-icon="check-square-o" >选择选中</button>
			</shiro:hasPermission>
		</div>
		<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
		</div>
    <form id="pagerForm" class="teacher_select_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/base/teacherSelectList/${type}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#teacher_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="teacher_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#teacher_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="teacher_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#teacher_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="teacher_list_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>姓名</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
        	<label>曾用名</label><input type="text" value="${query.usedName }" name="usedName" class="form-control" size="10">&nbsp;
			<label>性别</label>
			 	<input type="radio" name="sex" id="f_teacher_sex1" data-toggle="icheck" value="1" <c:if test="${query.sex==1}">checked</c:if> data-label="男&nbsp;&nbsp;">
                <input type="radio" name="sex" id="f_teacher_sex2" data-toggle="icheck" value="0" <c:if test="${query.sex==0}">checked</c:if> data-label="女">&nbsp;&nbsp;
                    
			<label>生日从</label>
			 <input type="text" name="startBirthday" id="f_teacher_birthday" value='<fmt:formatDate value="${query.startBirthday }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
			<label>到</label><input type="text" name="endBirthday" id="f_teacher_birthday" value='<fmt:formatDate value="${query.endBirthday }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
			
			<label>籍贯</label><input type="text" value="${query.nativer }" name="nativer" class="form-control" size="10">&nbsp;
			
			<label>民族</label>
			<select name="nation" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${nations }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.nation}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
			
			<label>身份证</label><input type="text" value="${query.idCard }" name="idCard" class="form-control" size="10">&nbsp;
			<label>办公电话</label><input type="text" value="${query.tel }" name="tel" class="form-control" size="10">&nbsp;
			<label>手机号</label><input type="text" value="${query.mobile }" name="mobile" class="form-control" size="10">&nbsp;
			<label>邮箱</label><input type="text" value="${query.email }" name="email" class="form-control" size="10">&nbsp;
			<label>标识码</label><input type="text" value="${query.tno }" name="tno" class="form-control" size="10">&nbsp;
			<label>状态</label>
			<select name="status" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${teacherStatus }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.status}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;
        </div>
    </form>
</div>

<div id="teacher_select_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<c:if test="${sessionScope.currentUser.province==0}">
    		<th align="center" data-order-field="province">省</th>
    		</c:if>
			<c:if test="${sessionScope.currentUser.city==0}">
    		<th align="center" data-order-field="city">市</th>
    		</c:if>
			<c:if test="${sessionScope.currentUser.town==0}">
    		<th align="center" data-order-field="town">区县</th>
    		</c:if>
			<c:if test="${sessionScope.currentUser.school==0}">
    		<th align="center" data-order-field="school">学校</th>
    		</c:if>
    		<th align="center" data-order-field="type">级别</th>
			<th align="center" data-order-field="name">姓名</th>
			<th align="center" data-order-field="usedName">曾用名</th>
			<th align="center" data-order-field="sex">性别</th>
			<th align="center" data-order-field="birthday">出生日期</th>
			<!--<th align="center" data-order-field="nativer">籍贯</th>
			<th align="center" data-order-field="nation">民族</th>
			<th align="center" data-order-field="idCard">身份证</th>
			<th align="center" data-order-field="tel">办公电话</th>
			<th align="center" data-order-field="mobile">手机号</th>
			<th align="center" data-order-field="email">Email</th>
			<th align="center" data-order-field="tno">标识码</th>
			<th align="center" data-order-field="status">状态</th>-->
    	</tr>
    	</thead>
    	<tbody>
    	<shiro:hasPermission name="Teacher:read">
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<c:if test="${sessionScope.currentUser.province==0}">
			<td>${item.provinceName }</td>
			</c:if>
			<c:if test="${sessionScope.currentUser.city==0}">
			<td>${item.cityName }</td>
			</c:if>
			<c:if test="${sessionScope.currentUser.town==0}">
			<td>${item.townName }</td>
			</c:if>
			<c:if test="${sessionScope.currentUser.school==0}">
			<td>${item.schoolName }</td>
			</c:if>
			<td>
				<c:if test="${item.type ==0}"><span class="label label-success">教师</span></c:if>
				<c:if test="${item.type ==1}"><span class="label label-primary">校长</span></c:if>
			</td>
			<td><a href="javascript:;" onclick="teacherView(${item.id })">${item.name }</a></td>
			<td>${item.usedName }</td>
			<td>
				<c:if test="${item.sex ==0}"><span class="label label-success">女</span></c:if>
				<c:if test="${item.sex ==1}"><span class="label label-primary">男</span></c:if>
			</td>
			<td><fmt:formatDate value="${item.birthday }" pattern="yyyy-MM-dd"/></td>
			<!--<td>${item.nativerName }</td>
			<td>${item.nationName }</td>
			<td>${item.idCard }</td>
			<td>${item.tel }</td>
			<td>${item.mobile }</td>
			<td>${item.email }</td>
			<td>${item.tno }</td>
			<td>
				<c:if test="${item.status ==1}"><span class="label label-default">未确认</span></c:if>
				<c:if test="${item.status ==2}"><span class="label label-danger">已确认</span></c:if>
				<c:if test="${item.status ==3}"><span class="label label-success">已审核</span></c:if>
			</td>
			-->
    	</tr>
    	</c:forEach>
    	</shiro:hasPermission>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>