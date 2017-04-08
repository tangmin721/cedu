<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#teacher_study_exp_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#teacher_study_exp_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

$('#but_teacher_study_exp_add').on('click', function(e) {
	$(this).dialog({id:'teacher_study_exp_form', url:'${ctx}/core/basic/teacher/studyExp/teacherStudyExpForm?tid=${query.tid}', type:'POST',title:'添加学习经历',mask:'true',width:'520',height:'560'});
});

$('#but_teacher_study_exp_edit').on('click', function(e) {
	var list = teacherStudyExp.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要编辑的行！');
		
	}else if(list.length>1){
		$(this).alertmsg('warn', '只能选择一行进行编辑！');
	}else{
		var id = list[0];
		$(this).dialog({id:'teacher_study_exp_form', url:'${ctx}/core/basic/teacher/studyExp/teacherStudyExpForm/'+id, type:'POST',title:'编辑学习经历',mask:'true',width:'520',height:'560'});
	}
});

$('#but_teacher_study_exp_delete').on('click', function(e) {
	var list = teacherStudyExp.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要删除的行！');
	}else{
		$(this).bjuiajax('doAjaxChecked', {url:'${ctx}/core/basic/teacher/studyExp/deleteTeacherStudyExpByIds',idname:"ids",group:"ids",confirmMsg:"确定要删除选中项吗？",callback:"delRefushTeacherStudyExpTable"});
	}
});

function delRefushTeacherStudyExpTable(json){
	$('.teacher_study_exp_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/studyExp/teacherStudyExpList/${tid}'});
}


var teacherStudyExp = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#teacher_study_exp_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="TeacherStudyExp:create">
			<button type="button" id="but_teacher_study_exp_add" class="btn btn-blue" data-icon="plus-circle" >添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherStudyExp:update">
			<button type="button" id="but_teacher_study_exp_edit" class="btn btn-green" data-icon="edit" >编辑</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="TeacherStudyExp:delete">
			<button type="button" id="but_teacher_study_exp_delete" class="btn btn-red" data-icon="times-circle" >删除</button>
		</shiro:hasPermission>
	</div>
    <form id="pagerForm" class="teacher_study_exp_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/basic/teacher/studyExp/teacherStudyExpList/${tid}" method="POST" data-reload-navtab="true">
       	<input type="hidden" name="pageSize" value="200">
       	<input type="hidden" name="pageCurrent" value="1">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <input type="hidden" name="tid" value="${query.tid }">
       <!--  <div class="bjui-searchBar pull-right">
			<label>起始时间</label><input type="text" value="${query.startDate }" name="startDate" class="form-control" size="10">&nbsp;
			<label>结束时间</label><input type="text" value="${query.endDate }" name="endDate" class="form-control" size="10">&nbsp;
			<label>毕业学校</label><input type="text" value="${query.univ }" name="univ" class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search">查询</button>&nbsp;
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>学位类型</label><input type="text" value="${query.degreeType }" name="degreeType" class="form-control" size="10">&nbsp;
			<label>学历/学位</label><input type="text" value="${query.degree }" name="degree" class="form-control" size="10">&nbsp;
        	<label>专业</label><input type="text" value="${query.major }" name="major" class="form-control" size="10">&nbsp;
			<label>备注</label><input type="text" value="${query.memo }" name="memo" class="form-control" size="10">&nbsp;
			<label>序号</label><input type="text" value="${query.seq }" name="seq" class="form-control" size="10">&nbsp;
        </div> -->
    </form>
</div>

<div id="teacher_study_exp_table" class="tableContent">
    <table class="table table-hover table-condensed table-bordered table-top" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26">No.</th>
    		<th align="center" data-order-field="degree">获得学历</th>
    		<th align="center" data-order-field="degreeCountryType" title="获得学历的国家(地区)">获得学历的国家(地区)</th>
			<th align="center" data-order-field="univ">获得学历的院校或机构</th>
			<th align="center" data-order-field="major">专业</th>
			<th align="center" data-order-field="isNormalCollege" title="是否师范类专业">是否师范类专业</th>
			<th align="center" data-order-field="startDate">入学年月</th>
			<th align="center" data-order-field="endDate">毕业年月</th>
			<th align="center" data-order-field="academicDegree" title="学位层次">学位层次</th>
			<th align="center" data-order-field="academicDegreeName" title="学位名称">学位名称</th>
			<th align="center" data-order-field="academicCountryType" title="获得学位的国家(地区)">获得学位的国家(地区)</th>
			<th align="center" data-order-field="academicUnit" title="获得学位的院校或机构">获得学位的院校或机构</th>
			<th align="center" data-order-field="grantDate" title="学位授予年月">学位授予年月</th>
			<th align="center" data-order-field="studyMode" title="学习方式">学习方式</th>
			<th align="center" data-order-field="studyUnitType" title="在学单位类别">在学单位类别</th>
			<!-- 
			<th align="center" data-order-field="degreeType">学位类型</th>
			<th align="center" data-order-field="degreeFlag">最高学历</th>
			<th align="center" data-order-field="memo">备注</th>
			<th align="center" data-order-field="seq">序号</th>
			-->
			
			
			
    	</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="#{page}" varStatus="index">
    	<tr data-id="${item.id }">
    		<td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id }" ></td>
    		<td>${index.count}</td>
    		<td>
			<c:forEach items="${degrees }" var="items">
			  <c:if test="${items.id==item.degree}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${degreeCountryTypes }" var="items">
			  <c:if test="${items.id==item.degreeCountryType}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.univ }</td>
			<td>${item.major }</td>
			<td>
			<c:forEach items="${isNormalColleges }" var="items">
			  <c:if test="${items.id==item.isNormalCollege}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td><fmt:formatDate value="${item.startDate }" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${item.endDate }" pattern="yyyy-MM-dd"/></td>
			<td>
			<c:forEach items="${academicDegrees }" var="items">
			  <c:if test="${items.id==item.academicDegree}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${academicDegreeNames }" var="items">
			  <c:if test="${items.id==item.academicDegreeName}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${academicCountryTypes }" var="items">
			  <c:if test="${items.id==item.academicCountryType}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>${item.academicUnit }</td>
			<td><fmt:formatDate value="${item.grantDate }" pattern="yyyy-MM-dd"/></td>
			<td>
			<c:forEach items="${studyModes }" var="items">
			  <c:if test="${items.id==item.studyMode}">${items.name }</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${studyUnitTypes }" var="items">
			  <c:if test="${items.id==item.studyUnitType}">${items.name }</c:if>
			</c:forEach>
			</td>
			<!-- 
			<td>${item.degreeType }</td>
			<td>${item.degreeFlag }</td>
			<td>${item.memo }</td>
			<td>${item.seq }</td>
			-->
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>

