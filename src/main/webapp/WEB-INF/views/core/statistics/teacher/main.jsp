<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<c:if test="${query.way ==0 or query.way=='' or query.way==null}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way0.jsp"%></c:if>
<c:if test="${query.way ==1}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way1.jsp"%></c:if>
<c:if test="${query.way ==2}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way2.jsp"%></c:if>
<c:if test="${query.way ==3}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way3.jsp"%></c:if>
<c:if test="${query.way ==4}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way4.jsp"%></c:if>
<c:if test="${query.way ==5}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way5.jsp"%></c:if>
<c:if test="${query.way ==6}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way6.jsp"%></c:if>
<c:if test="${query.way ==7}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way7.jsp"%></c:if>
<c:if test="${query.way ==8}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way8.jsp"%></c:if>
<c:if test="${query.way ==9}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way9.jsp"%></c:if>
<c:if test="${query.way ==10}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way10.jsp"%></c:if>
<c:if test="${query.way ==11}"><%@ include file="/WEB-INF/views/core/statistics/teacher/way/way11.jsp"%></c:if>

<script type="text/javascript">
	$('#r_teacher_refresh').on('click',function(){
		
		$(this).alertmsg('confirm', '确认刷新数据？',{
			okCall:function(){
				$.ajax({ 
			        type: "POST", 
			        url: "${ctx}/core/statistics/teacher/processTeacher",
			        dataType : "json",
			        success: function(data) {
			        	$('#r_teacher_search').trigger('click');
			        },
			        error :  function(){
			        	$(this).alertmsg('error', '系统错误！');
			        }
			    }); 
			}
		});
	})
	
	
	$('#mk_add').on('click', function(e) {
		$(this).dialog({id:'mk_tch_form', url:'${ctx}/core/statistics/report/createTeacherForm', type:'POST',title:'大量生成教师信息数据',width:'420',height:'160'});
	});
</script>
		
<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<shiro:hasRole name="SUP_ADMIN">
			<button type="button" id="mk_add" class="btn btn-danger" data-icon="list" >大量生成教师信息数据</button>
		</shiro:hasRole>
		<button type="button" id="r_teacher_refresh" class="btn btn-success" data-icon="refresh" >实时刷新${headName}信息统计</button>
	</div>
    <form id="pagerForm" class="config_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/statistics/teacher/main/${tchtype}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="1">
        <div class="bjui-searchBar pull-right">
        	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#r_teacher_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="r_teacher_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#r_teacher_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="r_teacher_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#r_teacher_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="r_teacher_list_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
			<label>统计方式</label>
			<select name="way" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${ways }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.way}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;
            <button type="submit" class="btn btn-blue" id="r_teacher_search" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div class="wrapdiv4" style="height:100%;">
        <div id="report_teacher_charts" class="contentdiv4"></div>
    </div>
</div>

