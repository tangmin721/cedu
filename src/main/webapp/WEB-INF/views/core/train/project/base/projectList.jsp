<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	/*
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
});*/

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
		$(this).dialog({id:'project_form', url:'${ctx}/core/train/project/base/projectForm/'+id, type:'POST',title:'编辑培训项目',mask:'true',width:'600',height:'300'});
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
	  $(this).dialog({id:'project-view-dialog', url:'${ctx}/core/train/project/base/projectViewForm/'+id, title:'项目详细信息',mask:'true',width:'760',height:'520'});
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
	
	$(this).alertmsg('confirm', '确认是否要变更项目状态为【'+strStatus+'】？如果是，请点确认！',{
		okCall:function(){
			$.ajax({ 
		        type: "POST", 
		        url: "${ctx}/core/train/project/base/updateStatus?id="+id+"&status="+status,
		        dataType : "json",
		        success: function(data) {
		        	$(this).alertmsg('ok', '操作成功');
		        	$('#project_search0').trigger('click');
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
		        	$('#project_search0').trigger('click');
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
		}
	});
}


function editProject(id){
	 $(this).dialog({id:'project-view-dialog', url:'${ctx}/core/train/project/base/projectForm/'+id, title:'编辑项目信息',mask:'true',width:'850',height:'520'});
}

//培训机构
function confTrainDept(id){
		$(this).navtab('closeTab', 'ntr-org');
	 $(this).dialog({id:'project-view-dialog', url:'${ctx}/core/train/project/trainorg/projectTrainOrgList/'+id, title:'分配培训机构',mask:'true',width:'1200',height:'620'});
}

function reportConf(id){
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/base/projectReportConf/'+id, type:'POST',title:'报名管理(名额分配)',mask:'true',width:'850',height:'600'});
}

function editReportApply(id){
	$(this).dialog({id:'project_roster_apply_form', url:'${ctx}/core/train/project/roster/applyForm/'+id,
		type:'POST',title:'编辑报名申请单',mask:'true',width:'600',height:'520'});
}

//查看申请单详情
function viweApply(id){
	  $(this).dialog({id:'teacher-view-dialog', url:'${ctx}/core/train/project/roster/applyViewReport/'+id, title:'申请单详细信息',mask:'true',width:'850',height:'600'});
}

function joinOrNot(id,status){
	var msg = '确定参加此次培训吗？';//=2的时候
	if(status==3){
		'确定不参加此次培训吗？'
	}
	$(this).alertmsg('confirm', msg,{
          okCall:function(){
        	  $.ajax({ 
  		        type: "POST", 
  		        url: "${ctx}/core/train/project/roster/joinOrNot/"+id+"/"+status,
  		        dataType : "json",
  		        success: function(data) {
  		        	$(this).alertmsg('ok', '操作成功');
  		        	$('#project_search1').trigger('click');
  		        	if(status==2){
  		        		$(this).navtab('reloadFlag','ntp-my-joined');
  		        	}else{
  		        		$(this).navtab('reloadFlag','ntp-my-unjoin');
  		        	}
  		        },
  		        error :  function(){
  		        	$(this).alertmsg('error', '系统错误！');
  		        }
  		    }); 
          }
      });
	
}

function reportCheck(id){
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/roster/checkList/'+id, type:'POST',title:'报名管理(审核)',mask:'true',width:'850',height:'600'});
}

//参培人明细
function trainTeachers(id){
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/roster/detail/trainTeachers?pid='+id, type:'POST',title:'参培人名单',mask:'true',width:'850',height:'600'});
}

//文件审核
function fileCheck(id){
	$(this).dialog({id:'np-file-check', url:'${ctx}/core/train/project/file/fileCheck/'+id, type:'POST',title:'材料审核',mask:'true',width:'850',height:'600'});
}

//文件查看
function fileView(id){
	$(this).dialog({id:'np-file-vied', url:'${ctx}/core/train/project/file/fileView/'+id, type:'POST',title:'材料查看',mask:'true',width:'850',height:'600'});
}

//机构上传培训材料
function orgFileCreate(pid){
	$(this).dialog({id:'np-org-file-create', url:'${ctx}/core/train/project/file/orgFileCreate/'+pid+'/0', type:'POST',title:'材料上传',mask:'true',width:'850',height:'350'});
}

//参培人员管理
function personManager(pid){
    $(this).dialog({id:'np-org-person-import', url:'${ctx}/core/train/project/person/projectPersonList/'+pid, type:'POST',title:'参培人员管理',mask:'true',width:'1330',height:'560'});
}



function orgFileView(pid){
	$(this).dialog({id:'np-org-file-view', url:'${ctx}/core/train/project/file/orgFileCreate/'+pid+'/1', type:'POST',title:'材料查看',mask:'true',width:'850',height:'350'});
}

//
function giveScore(id){
	$(this).dialog({id:'np-quota', url:'${ctx}/core/train/project/roster/detail/trainScores?pid='+id, type:'POST',title:'学时学分授予',mask:'true',width:'850',height:'600'});
}
</script>

<div class="bjui-pageHeader">
	<form id="pagerForm" class="project_pager_form"
		data-toggle="ajaxsearch"
		action="${ctx}/core/train/project/base/projectList/${menuType}"
		method="post" data-reload-navtab="true">
		<input type="hidden" name="pageSize" value="${page.pageSize}">
		<input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
		<input type="hidden" name="orderField" value="${param.orderField}">
		<input type="hidden" name="orderDirection"
			value="${param.orderDirection}">
		<div class="bjui-searchBar pull-right">
			<c:if test="${menuType != 1 && menuType != 0}">
			<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#school_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="school_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#school_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="school_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#school_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="school_list_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
            </c:if>
			<c:if test="${trainLevels.size()>0}">
				<label>培训等级</label>
				 <select name="trainLevel"  data-toggle="selectpicker" data-width="100px">
				   <option value="">--请选择--</option>
				   <c:forEach items="${trainLevels }" var="item">
				       <option  value="${item.id }" <c:if test="${item.id==query.trainLevel}">selected</c:if>>${item.name }</option>
				   </c:forEach>
				</select>&nbsp;
			</c:if>
				<label>培训类型</label> 
				 <select name="trainType"  data-toggle="selectpicker" data-width="100px">
				   <option value="">--请选择--</option>
				   <c:forEach items="${trainTypes }" var="item">
				       <option  value="${item.id }" <c:if test="${item.id==query.trainType}">selected</c:if>>${item.name }</option>
				   </c:forEach>
				</select>&nbsp;
			
			<button type="button" class="showMoreSearch" data-toggle="moresearch"
				data-name="custom2">
				<i class="fa fa-angle-double-down"></i>
			</button>
			<button type="submit" class="btn btn-blue"
				id="project_search${menuType }" data-icon="search">查询</button>
			&nbsp; <a class="btn btn-orange" href="javascript:;"
				data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
		</div>
		<div class="bjui-moreSearch pull-right">
			<label>项目名称</label><input type="text" value="${query.name }" name="name" class="form-control" size="10">&nbsp; 
			<label>项目编号</label><input type="text" value="${query.pno }" name="pno" class="form-control" size="10">&nbsp; 
			<label>学段</label>
            <select name="stage"  data-toggle="selectpicker" data-width="100px">
			   <option value="">--请选择--</option>
			   <c:forEach items="${stages }" var="item">
			       <option  value="${item.id }" <c:if test="${item.id==query.stage}">selected</c:if>>${item.name }</option>
			   </c:forEach>
			</select>&nbsp;
			<label>年度</label>
			<select name="schoolYear"  data-toggle="selectpicker" data-width="100px">
			   <option value="">--请选择--</option>
			   <c:forEach items="${schoolYears }" var="item">
			       <option  value="${item.id }" <c:if test="${item.id==query.schoolYear}">selected</c:if>>${item.name }</option>
			   </c:forEach>
			</select>&nbsp;
		    <label>实施方式</label>
		    <select name="implWay"  data-toggle="selectpicker" data-width="100px">
			   <option value="">--请选择--</option>
			   <c:forEach items="${implWays }" var="item">
			       <option  value="${item.id }" <c:if test="${item.id==query.implWay}">selected</c:if>>${item.name }</option>
			   </c:forEach>
			</select>&nbsp;
		</div>
	</form>
</div>

<div id="project_table" class="bjui-pageContent tableContent">
	<table data-toggle="tablefixed" data-width="100%">
		<thead>
			<tr class="trth">
				<!--<th width="26"><input type="checkbox" class="checkboxCtrl"
					data-group="ids" data-toggle="icheck"></th>-->
				<th width="26" title="序号">No.</th>
				
				<shiro:hasRole name="PROVINCE_ADMIN">
					<c:if test="${menuType == 2}">
						<th align="center" data-order-field="city">市</th>
						<th align="center" data-order-field="town">区县</th>
						<th align="center" data-order-field="school">学校</th>
					</c:if>
				</shiro:hasRole>
				<shiro:hasRole name="CITY_ADMIN">
					<c:if test="${menuType == 2}">
						<th align="center" data-order-field="town">区县</th>
					<th align="center" data-order-field="school">学校</th>
				</c:if>
				</shiro:hasRole>
				<shiro:hasRole name="TOWN_ADMIN">
					<c:if test="${menuType == 2}">
						<th align="center" data-order-field="school">学校</th>
					</c:if>
				</shiro:hasRole>
				<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,TRAINORG">
						<th align="center" data-order-field="province">省</th>
						<th align="center" data-order-field="city">市</th>
						<th align="center" data-order-field="town">区县</th>
						<th align="center" data-order-field="school">学校</th>
				</shiro:hasAnyRoles>
				<th align="center" data-order-field="name">项目名称</th>
				<th align="center" data-order-field="pno">项目编号</th>
				<th align="center" data-order-field="trainLevel">项目等级</th>
				<th align="center" data-order-field="trainType">项目类型</th>
				<th align="center" data-order-field="stage">学段</th>
				<th align="center" data-order-field="course">学科</th>
				<th align="center" data-order-field="schoolYear">年度</th>
				<th align="center" data-order-field="hour">学时</th>
				<th align="center" data-order-field="startDate">开始时间</th>
				<th align="center" data-order-field="endDate">结束时间</th>
				<th align="center" data-order-field="status">状态</th>
				<shiro:hasAnyRoles name="SCHOOL_ADMIN">
					<th align="center" data-order-field="cstatus">审批状态</th>
				</shiro:hasAnyRoles>
				<th align="center" data-order-field="status">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="#{page.list}" varStatus="index">
				<tr data-id="${item.id }">
					<!--
					<td><input type="checkbox" name="ids" data-toggle="icheck"
						value="${item.id }"></td>-->
					<td>${index.count+page.pageStart}</td>
					
					<shiro:hasRole name="PROVINCE_ADMIN">
						<c:if test="${menuType == 2}">
							<td>${item.cityName }</td>
							<td>${item.townName }</td>
							<td>${item.schoolName }</td>
						</c:if>
					</shiro:hasRole>
					<shiro:hasRole name="CITY_ADMIN">
						<c:if test="${menuType == 2}">
							<td>${item.townName }</td>
							<td>${item.schoolName }</td>
						</c:if>
					</shiro:hasRole>
					<shiro:hasRole name="TOWN_ADMIN">
						<c:if test="${menuType == 2}">
							<td>${item.schoolName }</td>
						</c:if>
					</shiro:hasRole>
					<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,TRAINORG">
							<td>${item.provinceName }</td>
							<td>${item.cityName }</td>
							<td>${item.townName }</td>
							<td>${item.schoolName }</td>
					</shiro:hasAnyRoles>
					<td><a href="javascript:;" onclick="projectView(${item.id })">${item.name }</a></td>
					<td>${item.pno }</td>
					<td>${item.trainLevelName }</td>
					<td>${item.trainTypeName }</td>
					<td>${item.stageName }</td>
					<td>${item.courseName }</td>
					<td>${item.schoolYearName }</td>
					<td><fmt:formatNumber value="${item.hour }" pattern="#.##" /></td>
					<td><fmt:formatDate value="${item.startDate }"
							pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${item.endDate }"
							pattern="yyyy-MM-dd" /></td>
					<td><c:if test="${item.status ==0}">
							<span class="label label-default">未发布</span>
						</c:if> <c:if test="${item.status ==1}">
							<span class="label label-info">已发布</span>
						</c:if> <c:if test="${item.status ==2}">
							<span class="label label-primary">培训中...</span>
						</c:if> <c:if test="${item.status ==3}">
							<span class="label label-success">培训已结束</span>
						</c:if>
					</td>
					<shiro:hasAnyRoles name="SCHOOL_ADMIN">
						<td>
							<c:if test="${item.cstatus ==11}">
								<span class="label label-default">待审</span>
							</c:if> <c:if test="${item.cstatus ==12}">
								<span class="label label-success" data-toggle="tooltip"  data-placement="right" title='${item.coption }'>审批通过</span>
							</c:if> <c:if test="${item.cstatus ==13}">
								<span class="label label-danger" data-toggle="tooltip"  data-placement="right" title='${item.coption }'>审批不通过</span>
							</c:if> 
						</td>
					</shiro:hasAnyRoles>
						<c:if test="${menuType ==0}">
							<td width="180">
								<%@ include file="/WEB-INF/views/core/train/project/base/projectMenu/menu0.jsp"%>
							</td>
						</c:if>
						<c:if test="${menuType ==1}">
							<td width="60">
								<%@ include file="/WEB-INF/views/core/train/project/base/projectMenu/menu1.jsp"%>
							</td>
						</c:if>
						<c:if test="${menuType ==2}">
							<td width="60">
								<%@ include file="/WEB-INF/views/core/train/project/base/projectMenu/menu2.jsp"%>
							</td>
						</c:if>
						<c:if test="${menuType ==3}">
							<td width="60">
								<%@ include file="/WEB-INF/views/core/train/project/base/projectMenu/menu3.jsp"%>
							</td>
						</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>