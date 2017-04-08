<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#project_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#project_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_project_add').on('click', function(e) {
	$(this).dialog({id:'project_form', url:'${ctx}/core/train/project/base/createProjectForm/dialog', type:'POST',title:'添加培训项目',mask:'true',width:'850',height:'620'});
});

$('#but_project_edit').on('click', function(e) {
	var list = project.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'project_form', url:'${ctx}/core/train/project/base/projectForm/'+id, type:'POST',title:'编辑培训项目',mask:'true',width:'850',height:'620'});
	}
});

$('#but_project_delete').on('click', function(e) {
	var list = project.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/train/project/base/deleteProjectByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushProjectTable"});
	}
});

$('#but_project_operator').on('click', function(e) {
	var list = project.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要管理项目执行人的行！');
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行管理！');
	}else{
		var id = list[0];
		$(this).navtab('closeTab', 'ntr-project-exer');
		$(this).dialog({id:'ntr-project-exer', url:'${ctx}/core/train/project/operator/projectOperatorList', type:'POST',title:'管理项目执行人',mask:'true',width:'850',height:'620'});
	}
});

$('#but_project_conf').on('click', function(e) {
	var list = project.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要管理报名的行！');
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行管理！');
	}else{
		var id = list[0];
		$(this).navtab('closeTab', 'np-quota');
		$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/base/projectReportConf/'+id, type:'POST',title:'报名管理(名额分配)',mask:'true',width:'850',height:'600'});
	}
});

$('#but_project_apply').on('click', function(e) {
	var list = project.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要管理报名的行！');
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行管理！');
	}else{
		var id = list[0];
		$(this).navtab('closeTab', 'np-quota');
		$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/base/projectReportApply/'+id, type:'POST',title:'报名管理(申请)',mask:'true',width:'850',height:'600'});
	}
});

$('#but_project_check').on('click', function(e) {
	var list = project.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要管理报名的行！');
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行管理！');
	}else{
		var id = list[0];
		$(this).navtab('closeTab', 'np-quota');
		$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/base/projectReportCheck/'+id, type:'POST',title:'报名管理(审核)',mask:'true',width:'850',height:'600'});
	}
});



function delRefushProjectTable(json){
	$('.project_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/base/projectList'});
}


var project = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#project_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}


function projectView(id){
	  $(this).dialog({id:'project-view-dialog', url:'${ctx}/core/train/project/base/projectView/'+id, title:'项目详细信息',mask:'true',width:'850',height:'600'});
}

function optProject(id,status){
	var strStatus = '';
	if(status==0){
		strStatus = '未发布';
	}else if(status==1){
		strStatus = '已发布';
	}else if(status==2){
		strStatus = '培训中...';
	}else if(status==3){
		strStatus = '培训已结束';
	}
	
	$(this).alertmsg('confirm', '确认是否要变更为项目状态为【'+strStatus+'】？如果是，请点确认！',{
		okCall:function(){
			$.ajax({ 
		        type: "POST", 
		        url: "${ctx}/core/train/project/base/updateStatus?id="+id+"&status="+status,
		        dataType : "json",
		        success: function(data) {
		        	$(this).alertmsg('ok', '操作成功');
		        	$('#project_search').trigger('click');
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
		}
	});
}

function deleteProject(id){
	$(this).alertmsg('confirm', '确认是否要删除该项目，如果是，请点确认！',{
		okCall:function(){
			$.ajax({ 
		        type: "POST", 
		        url: "${ctx}/core/train/project/base/deleteProjectByIds?ids="+id,
		        dataType : "json",
		        success: function(data) {
		        	$('#project_search').trigger('click');
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
		}
	});
}


function editProject(id){
	 $(this).dialog({id:'project-view-dialog', url:'${ctx}/core/train/project/base/projectForm/'+id, title:'编辑项目信息',mask:'true',width:'850',height:'620'});
}

function confTrainDept(id){
	 $(this).dialog({id:'project-view-dialog', url:'${ctx}/core/train/project/trainorg/projectTrainOrgList/'+id, title:'分配培训机构',mask:'true',width:'950',height:'620'});
}

function reportConf(id){
	$(this).navtab('closeTab', 'np-quota');
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/base/projectReportConf/'+id, type:'POST',title:'报名管理(名额分配)',mask:'true',width:'850',height:'600'});
}

function reportApply(id){
	$(this).navtab('closeTab', 'np-quota');
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/roster/applyList/'+id, type:'POST',title:'报名管理(申请)',mask:'true',width:'850',height:'600'});
}

function reportCheck(id){
	$(this).navtab('closeTab', 'np-quota');
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/roster/checkList/'+id, type:'POST',title:'报名管理(审核)',mask:'true',width:'850',height:'600'});
}

//参培人明细
function trainTeachers(id){
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/roster/detail/trainTeachers?pid='+id, type:'POST',title:'参培人名单',mask:'true',width:'850',height:'600'});
}

//
function giveScore(id){
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/roster/detail/trainScores?pid='+id, type:'POST',title:'学时学分授予',mask:'true',width:'850',height:'600'});
}
</script>

<div class="bjui-pageHeader">
    <form id="pagerForm" class="project_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/train/project/base/projectList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
            	 <select name="province" data-toggle="selectpicker"   
             		data-nextselect="#project_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
        		<select name="city" id="project_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#project_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
	            <select name="town" id="project_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#project_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            <select name="school" id="project_list_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" id="project_search" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>项目名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp;
        	<label>项目编号</label><input type="text" value="${query.pno }" name="pno" class="form-control" size="10">&nbsp;
			<label>学段</label><input type="text" value="${query.stage }" name="stage" class="form-control" size="10">&nbsp;
			<label>学科</label><input type="text" value="${query.course }" name="course" class="form-control" size="10">&nbsp;
        	<label>培训类型</label><input type="text" value="${query.trainType }" name="trainType" class="form-control" size="10">&nbsp;
			<label>学年</label><input type="text" value="${query.schoolYear }" name="schoolYear" class="form-control" size="10">&nbsp;
			<label>学期</label><input type="text" value="${query.term }" name="term" class="form-control" size="10">&nbsp;
        	<label>学分比</label><input type="text" value="${query.ratioScore }" name="ratioScore" class="form-control" size="10">&nbsp;
			<label>学时比</label><input type="text" value="${query.ratioHour }" name="ratioHour" class="form-control" size="10">&nbsp;
			<label>学分</label><input type="text" value="${query.score }" name="score" class="form-control" size="10">&nbsp;
			<label>学时</label><input type="text" value="${query.hour }" name="hour" class="form-control" size="10">&nbsp;
			<label>开始日期</label><input type="text" value="${query.startDate }" name="startDate" class="form-control" size="10">&nbsp;
			<label>结束日期</label><input type="text" value="${query.endDate }" name="endDate" class="form-control" size="10">&nbsp;
			<label>培训目标</label><input type="text" value="${query.trainTageter }" name="trainTageter" class="form-control" size="10">&nbsp;
			<label>考核类型</label><input type="text" value="${query.checkTypes }" name="checkTypes" class="form-control" size="10">&nbsp;
			<label>描述</label><input type="text" value="${query.description }" name="description" class="form-control" size="10">&nbsp;
			<label>项目目标	</label><input type="text" value="${query.taget }" name="taget" class="form-control" size="10">&nbsp;
			<label>项目内容</label><input type="text" value="${query.content }" name="content" class="form-control" size="10">&nbsp;
			<label>实施方式</label><input type="text" value="${query.implWay }" name="implWay" class="form-control" size="10">&nbsp;
			<label>确认开关</label><input type="text" value="${query.confirmFlag }" name="confirmFlag" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
			<label>用户类型</label><input type="text" value="${query.userType }" name="userType" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="project_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
    		<th align="center" data-order-field="province">省</th>
    		<th align="center" data-order-field="city">市</th>
    		<th align="center" data-order-field="town">区县</th>
    		<th align="center" data-order-field="school">学校</th>
			<th align="center" data-order-field="name">项目名称</th>
			<th align="center" data-order-field="pno">项目编号</th>
			<th align="center" data-order-field="stage">学段</th>
			<th align="center" data-order-field="course">学科</th>
			<th align="center" data-order-field="trainType">培训类型</th>
			<!--<th align="center" data-order-field="schoolYear">学年</th>
			<th align="center" data-order-field="term">学期</th>
			<th align="center" data-order-field="ratioScore">学分比</th>
			<th align="center" data-order-field="ratioHour">学时比</th>
			<th align="center" data-order-field="score">学分</th>
			<th align="center" data-order-field="hour">学时</th>-->
			<th align="center" data-order-field="startDate">开始时间</th>
			<th align="center" data-order-field="endDate">结束时间</th>
			<!--<th align="center" data-order-field="trainTageter">培训目标</th>
			<th align="center" data-order-field="checkTypes">考核类型</th>
			<th align="center" data-order-field="description">描述</th>
			<th align="center" data-order-field="taget">项目目标</th>
			<th align="center" data-order-field="content">项目内容</th>
			<th align="center" data-order-field="implWay">实施方式</th>
			<th align="center" data-order-field="confirmFlag">确认开关</th>
			<th align="center" data-order-field="memo">备注</th>
			<th align="center" data-order-field="userType">用户类型</th>-->
			<th align="center" data-order-field="status">状态</th>
			<th align="center" data-order-field="status">操作</th>
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }"></td>
    		<td>${index.count+page.pageStart}</td>
			<td>${item.provinceName }</td>
			<td>${item.cityName }</td>
			<td>${item.townName }</td>
			<td>${item.schoolName }</td>
			<td><a href="javascript:;" onclick="projectView(${item.id })">${item.name }</a></td>
			<td>${item.pno }</td>
			<td>${item.stageName }</td>
			<td>${item.courseName }</td>
			<td>${item.trainTypeName }</td>
			<!--<td>${item.schoolYearName }</td>
			<td>${item.termName }</td>
			<td><fmt:formatNumber value="${item.ratioScore }" pattern="#.##"/></td>
			<td><fmt:formatNumber value="${item.ratioHour }" pattern="#.##"/></td>
			<td><fmt:formatNumber value="${item.score }" pattern="#.##"/></td>
			<td><fmt:formatNumber value="${item.hour }" pattern="#.##"/></td>-->
			<td><fmt:formatDate value="${item.startDate }" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${item.endDate }" pattern="yyyy-MM-dd"/></td>
			<!--<td>${item.trainTageter }</td>
			<td>${item.checkTypes }</td>
			<td>${item.description }</td>
			<td>${item.taget }</td>
			<td>${item.content }</td>
			<td>${item.implWay }</td>
			<td>${item.confirmFlag }</td>
			<td>${item.memo }</td>
			<td>${item.userType }</td>-->
			<td>
				<c:if test="${item.status ==0}"><span class="label label-default">未发布</span></c:if>
				<c:if test="${item.status ==1}"><span class="label label-info">已发布</span></c:if>
				<c:if test="${item.status ==2}"><span class="label label-primary">培训中...</span></c:if>
				<c:if test="${item.status ==3}"><span class="label label-success">培训已结束</span></c:if>
			</td>
			<td width="230">
				<c:if test="${item.status ==0}">
					<shiro:hasPermission name="Project:changeStatus">
						<button type="button" class="btn btn-primary" data-icon="share-square-o" onclick="optProject(${item.id},1)" data-toggle="tooltip"  data-placement="top" title="发布"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:delete">
						<button type="button" class="btn btn-danger" data-icon="trash-o" onclick="deleteProject(${item.id})"  data-toggle="tooltip"  data-placement="top" title="删除"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:update">
						<button type="button" class="btn btn-green" data-icon="edit" onclick="editProject(${item.id})" data-toggle="tooltip"  data-placement="top" title="编辑"></button>
					</shiro:hasPermission>
				</c:if>
				<c:if test="${item.status ==1}">
					<shiro:hasPermission name="Project:changeStatus">
						<button type="button" class="btn btn-info" data-icon="ban" onclick="optProject(${item.id},0)" data-toggle="tooltip"  data-placement="top" title="取消发布"></button>
						<button type="button" class="btn btn-primary" data-icon="ge" onclick="optProject(${item.id},2)" data-toggle="tooltip"  data-placement="top" title="变更状态为【培训中】"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:update">
						<button type="button" class="btn btn-green" data-icon="edit" onclick="editProject(${item.id})" data-toggle="tooltip"  data-placement="top" title="编辑"></button>
						<button type="button" class="btn btn-dark-blue" data-icon="cog" onclick="confTrainDept(${item.id})" data-toggle="tooltip"  data-placement="top" title="设置培训单位"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:conf">
						<button type="button" class="btn btn-purple" data-icon="cogs" onclick="reportConf(${item.id})" data-toggle="tooltip"  data-placement="top" title="报名配置"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:apply">
						<button type="button" class="btn btn-purple" data-icon="send" onclick="reportApply(${item.id})" data-toggle="tooltip"  data-placement="top" title="报名申请"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:check">
						<button type="button" class="btn btn-purple" data-icon="check-square-o" onclick="reportCheck(${item.id})" data-toggle="tooltip"  data-placement="top" title="报名审核"></button>
					</shiro:hasPermission>
				</c:if>
				<c:if test="${item.status ==2}">
					<shiro:hasPermission name="Project:changeStatus">
						<button type="button" class="btn btn-primary" data-icon="ra" onclick="optProject(${item.id},3)" data-toggle="tooltip"  data-placement="top" title="变更状态为【培训结束】"></button>
					</shiro:hasPermission>
					<!-- <button type="button" class="btn btn-dark-blue" data-icon="street-view" onclick="openMydialog(this)" data-toggle="tooltip"  data-placement="top" title="考勤记录"></button> -->
					<shiro:hasPermission name="ProjectRosterDetail:read">
						<button type="button" class="btn btn-dark-blue" data-icon="users" onclick="trainTeachers(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人名单"></button>
					</shiro:hasPermission>
				</c:if>
				<c:if test="${item.status ==3}">
					<!--<button type="button" class="btn btn-dark-blue" data-icon="street-view" onclick="openMydialog(this)" data-toggle="tooltip"  data-placement="top" title="考勤记录"></button>-->
					<shiro:hasPermission name="ProjectRosterDetail:read">
						<button type="button" class="btn btn-dark-blue" data-icon="users" onclick="trainTeachers(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人名单"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:score">
						<button type="button" class="btn btn-dark-orange" data-icon="life-bouy" onclick="giveScore(${item.id})" data-toggle="tooltip"  data-placement="top" title="学分授予"></button>
					</shiro:hasPermission>
				</c:if>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>