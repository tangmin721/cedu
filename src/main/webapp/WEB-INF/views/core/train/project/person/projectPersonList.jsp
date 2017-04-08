<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
var person_timer = null;
<shiro:hasRole name="TRAINORG">
$('#project_person_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_person_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});
	</shiro:hasRole>

$('#but_project_person_add').on('click', function(e) {
	$(this).dialog({id:'project_person_form', url:'${ctx}/core/train/project/person/projectPersonForm', type:'POST',title:'添加参培人名单',mask:'true',width:'600',height:'520'});
});

$('#but_project_person_edit').on('click', function(e) {
	var list = projectPerson.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'project_person_form', url:'${ctx}/core/train/project/person/projectPersonForm/'+id, type:'POST',title:'修改参培人培训成绩',mask:'true',width:'600',height:'240'});
	}
});

$('#but_project_person_delete').on('click', function(e) {
	var list = projectPerson.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/person/deleteProjectPersonByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProjectPersonTable"});
	}
});

function delRefushProjectPersonTable(json){
	$('.project_person_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/person/projectPersonList'});
}
//参培人员成绩导入
function personImport(pid){
	$(this).dialog({id:'np-org-person-import-form', url:'${ctx}/core/train/project/person/personImport/'+pid, type:'POST',title:'参培人员成绩导入',mask:'true',width:'760',height:'560'});
}

//生成认证学时进度条
function personCreateScorePage(pid){
	$(this).dialog({id:'np-org-person-create-page', url:'${ctx}/core/train/project/person/personCreateScorePage/'+pid, onClose:'person_page_dialog_onClose',type:'POST',title:'生成认证学时',mask:'true',width:'660',height:'160'});
}

function person_page_dialog_onClose(){
	if(person_timer){
		clearTimeout(person_timer);
	}
}


//取消学时认证
$('#person-cancel-score-btn2').on('click',function () {
	$(this).alertmsg('confirm', '确认删除本项目下所有老师的已认证学时吗？',{
		okCall:function(){

			if(person_timer){
				clearTimeout(person_timer);
			}
			$.ajax({
				type: "POST",
				async: false,
				url: "${ctx}/core/train/project/person/cancelMakeScore/"+${pid},
				dataType : "json",
				success: function() {
					$(this).alertmsg('ok', '删除成功！');
					$('#project_person_search_btn').trigger('click');
				},
				error :  function(){
					$(this).alertmsg('error', '系统错误！');
				}
			});
		}
	})
});


$('#person_export_all').on('click',function(e){
	$(this).alertmsg('confirm', '确定要导出全部参培人员信息吗？',{
		okCall:function(){
			window.location.href='${ctx }/core/train/project/person/exportAll/${pid}';
		}
	});
});

$('#person_export_search').on('click',function(e){
	$(this).alertmsg('confirm', '确定要根据查询条件，导出参培人员信息吗？',{
		okCall:function(){
			var params = $('.project_person_pager_form').serialize(); // http request parameters.
			params = decodeURIComponent(params,true);
			params = encodeURI(encodeURI(params));
			window.location.href='${ctx }/core/train/project/person/exportSearch/${pid}?'+params;
		}
	});
});


var projectPerson = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_person_table input[name="ids"]').each(function(){ 
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
		<c:if test="${projectStatus==2 or projectStatus==1}">
		<shiro:hasPermission name="ProjectPerson:update">
			<button type="button" id="but_project_person_edit" class="btn btn-green" data-icon="edit" >修改成绩</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="ProjectPerson:delete">
			<button type="button" id="but_project_person_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
		</c:if>
	</div>
	<div class="btn-group pull-left" role="group">
		&nbsp;&nbsp;
	</div>
	<div class="pull-left">
		<div class="btn-group">
<c:if test="${projectStatus==3 and scoreCreateType==1}">
			<shiro:hasPermission name="ProjectPerson:makeScore">
				<button type="button" class="btn btn-dark-blue" data-icon="sign-in" onclick="personCreateScorePage(${pid})">生成学时</button>
				<button type="button" id="person-cancel-score-btn2" class="btn btn-red" data-icon="remove" >删除学时</button>
			</shiro:hasPermission>
</c:if>
<c:if test="${projectStatus==2 or projectStatus==1}">
			<shiro:hasPermission name="ProjectPerson:import">
				<button type="button" class="btn btn-dark-blue" data-icon="sign-in" onclick="personImport(${pid})">参培人员导入</button>
			</shiro:hasPermission>
</c:if>
			<shiro:hasPermission name="ProjectPerson:export">
				<button type="button" class="btn-purple dropdown-toggle" data-toggle="dropdown" data-icon="sign-out">导出<span class="caret"></span></button>
				<ul class="dropdown-menu right" role="menu">
					<li><a href="javascript:;" id="person_export_all">&nbsp;<span class="fa fa-asterisk"></span>&nbsp; 导出<span style="color: green;">全部</span></a></li>
					<li class="divider"></li>
					<li><a href="javascript:;" id="person_export_search" >&nbsp;<span class="fa fa-search"></span>&nbsp; 按<span style="color: blue;">查询条件</span>导出</a></li>
				</ul>
			</shiro:hasPermission>
		</div>
	</div>
    <form id="pagerForm" class="project_person_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/person/projectPersonList/${pid}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
			<shiro:lacksRole name="TRAINORG">
			<select id="person_sform_org" name="oid" data-toggle="selectpicker" data-size="20" data-width="140px">
				<option value="">--请选择培训机构--</option>
				<c:forEach items="${projectOrgMaps }" var="item">
					<option value="${item.id }" <c:if test="${item.id==query.oid}">selected</c:if>>${item.name }</option>
				</c:forEach>
			</select>
			</shiro:lacksRole>
			<label>标识码</label><input type="text" value="${query.tno }" name="tno" class="form-control" size="14" maxlength="14">&nbsp;
			<label>身份证</label><input type="text" value="${query.idCard }" name="idCard" class="form-control" size="18" maxlength="18">&nbsp;
			<label>姓名</label><input type="text" value="${query.name }" name="name" class="form-control" size="10" maxlength="4">&nbsp;
			<label>成绩</label>
			<select name="pass" id="person_sform_pass" data-toggle="selectpicker"
					data-size="20" data-width="80px">
				<option value="">--请选择--</option>
				<option value="1" <c:if test='${query.pass=="1"}'>selected</c:if>>合格</option>
				<option value="0" <c:if test='${query.pass=="0"}'>selected</c:if>>不合格</option>
				<option value="2" <c:if test='${query.pass=="2"}'>selected</c:if>>未填</option>
			</select>
			&nbsp;
			<button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" id="project_person_search_btn" data-icon="search">查询</button>&nbsp;
        </div>
		<div class="bjui-moreSearch pull-right">
			<select id="person_form_province" name="province" data-toggle="selectpicker"
					data-nextselect="#person_form_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
				<option value="">--省市--</option>
				<c:forEach items="${query.queryProvinces }" var="item">
					<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
				</c:forEach>
			</select>
			<select name="city" id="person_form_city" data-toggle="selectpicker"
					data-nextselect="#person_form_town"
					data-refurl="${ctx}/system/area/town/towns?city={value}"
					data-size="20" data-width="140px">
				<option value="">--城市--</option>
				<c:forEach items="${query.queryCitys }" var="item">
					<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
				</c:forEach>
			</select>
			<select name="town" id="person_form_town" data-toggle="selectpicker"
					data-nextselect="#person_form_school"
					data-refurl="${ctx}/core/basic/school/schools?town={value}"
					data-size="20" data-width="140px">
				<option value="">--区县--</option>
				<c:forEach items="${query.queryTowns }" var="item">
					<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
				</c:forEach>
			</select>
			<select name="school" id="person_form_school" data-toggle="selectpicker"
					data-size="20" data-width="140px" data-live-search="true">
				<option value="">--学校--</option>
				<c:forEach items="${query.querySchools }" var="item">
					<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
				</c:forEach>
			</select>
		</div>
    </form>
</div>

<div id="project_person_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
			<shiro:hasRole name="TRAINORG">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
			</shiro:hasRole>
    		<th width="26" title="序号">No.</th>
			<shiro:lacksRole name="TRAINORG">
			<th align="center" title="培训机构">培训机构</th>
			<th align="center" title="培训机构编号">机构No.</th>
			</shiro:lacksRole>
			<th align="center" data-order-field="province" title="省">省</th>
			<th align="center" data-order-field="city" title="市">市</th>
			<th align="center" data-order-field="town" title="区县">区县</th>
			<th align="center" data-order-field="school" title="学校">学校</th>
			<th align="center" data-order-field="name" title="姓名">姓名</th>
			<th align="center" data-order-field="tno" title="标识码">标识码</th>
			<th align="center" data-order-field="idCard" title="身份证">身份证</th>
			<th align="center" title="成绩">成绩</th>
			<th align="center" title="可获学时">可获学时</th>
			<th align="center" title="已认证/未认证">认证状态</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
			<shiro:hasRole name="TRAINORG">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
			</shiro:hasRole>
    		<td>${index.count+page.pageStart}</td>
			<shiro:lacksRole name="TRAINORG">
			<td>${item.orgName }</td>
			<td>${item.orgNo }</td>
			</shiro:lacksRole>
			<td>${item.provinceName }</td>
			<td>${item.cityName }</td>
			<td>${item.townName }</td>
			<td>${item.schoolName }</td>
			<td><a href="javascript:;" onclick="teacherView(${item.tid })">${item.name }</a></td>
			<td>${item.tno }</td>
			<td>${item.idCard }</td>
			<td>
				<c:if test="${item.pass==1}"><span style="color:green;">优秀</span></c:if>
				<c:if test="${item.pass==2}"><span style="color:blue;">合格</span></c:if>
				<c:if test="${item.pass==0}"><span style="color:red;">不合格</span></c:if>
				<c:if test="${empty item.pass}">--</c:if>
			</td>
			<td>
				<c:if test="${item.pass==1}"><span style="color:green;">${item.goodScore}</span></c:if>
				<c:if test="${item.pass==2}"><span style="color:blue;">${item.passScore}</span></c:if>
				<c:if test="${item.pass==0}"><span style="color:red;">0</span></c:if>
				<c:if test="${empty item.pass}">--</c:if>
			</td>
			<td>
				<c:if test="${not empty item.scid}"><span style="color:green;">已认证</span></c:if>
				<c:if test="${empty item.scid}"><span style="color:red;">未认证</span></c:if>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>