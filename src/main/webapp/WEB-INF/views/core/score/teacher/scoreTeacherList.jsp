<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<style type="text/css">
.score_top_title{ height:32px; line-height:32px; font-size:16px; font-weight:700;color:red;padding-left:10px;}
</style>
<script type="text/javascript">


function teacherScoreView(id,totalScore){
	  $(this).dialog({id:'teacherscore-view-dialog', url:'${ctx}/core/score/scoreTeacher/teacherScore/'+id+'?totalScore='+totalScore, title:'教师已获学时信息',mask:'true',width:'960',height:'600'});
}

$("#f_steacher_base_query_stage").change(function(){
    var stage = $("#f_steacher_base_query_stage option:selected").val();
    if(stage){
   	 //联动学科下拉框
   	 $.ajax({ 
            type: "POST", 
            url: "${ctx}/core/basic/conf/course/courses/"+stage,
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_steacher_base_query_course").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_steacher_base_query_course").append(str);  
                }
                $('#f_steacher_base_query_course').selectpicker('refresh');
            },
            error :  function(){
                $(this).alertmsg('error', '系统错误！');
            }
        }); 
    }else{
      	 $("#f_steacher_base_query_course").html("<option value=''>--请选择--</option>");
       	 $('#f_steacher_base_query_course').selectpicker('refresh');
    }
    });
    
 
$('#r_steacher_refresh').on('click',function(){
	
	$(this).alertmsg('confirm', '确认汇总每个老师的认证学时数据？',{
		okCall:function(){
			$.ajax({ 
		        type: "POST", 
		        url: "${ctx}/core/score/scoreTeacher/processTotalScore",
		        dataType : "json",
		        success: function(data) {
		        	$('#score_teacher_search').trigger('click');
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
		}
	});
})

</script>

<div class="bjui-pageHeader">
	<div class="btn-group pull-left" role="group">
		<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
			<button type="button" id="r_steacher_refresh" class="btn btn-success" data-icon="refresh" >实时刷新教师学时汇总</button>
			 <span class="score_top_title">最近数据更新时间：${summaryTime }</span>
		</shiro:hasAnyRoles>
	</div>
    <form id="pagerForm" class="steacher_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/score/scoreTeacher/list" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#steacher_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="steacher_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#steacher_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="steacher_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#steacher_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="steacher_list_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
            <label>姓名</label><input type="text" value="${query.name }" name="name" maxlength="10" class="form-control" size="10">&nbsp;
            <label>学时从</label><input type="text" value="${query.startScore }" name="startScore" data-rule="digits" maxlength="10" class="form-control" size="10">&nbsp;
            <label>到</label><input type="text" value="${query.endScore }" name="endScore"  data-rule="digits" maxlength="10"  class="form-control" size="10">&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" id="score_teacher_search" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
        	<label>学时获取时间从</label>
			 <input type="text" name="startCheckDay"  value='<fmt:formatDate value="${query.startCheckDay }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="11">
			<label>到</label><input type="text" name="endCheckDay"  value='<fmt:formatDate value="${query.endCheckDay }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="11">
			<label>生日从</label>
			 <input type="text" name="startBirthday" id="f_steacher_birthday" value='<fmt:formatDate value="${query.startBirthday }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="11">
			<label>到</label><input type="text" name="endBirthday" id="f_steacher_birthday" value='<fmt:formatDate value="${query.endBirthday }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="11">
			<br>
			<br>
			
			<label>标识码</label><input type="text" value="${query.tno }" name="tno" class="form-control" size="20">&nbsp;
			<label>身份证</label><input type="text" value="${query.idCard }" name="idCard" class="form-control" size="20">&nbsp;
			
        </div>
    </form>
</div>

<div id="steacher_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
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
    		<!--  <th align="center" data-order-field="type">级别</th> -->
    		<th align="center" data-order-field="tno">标识码</th>
			<th align="center" data-order-field="name">姓名</th>
			<th align="center" data-order-field="totalScore">已获学时</th>
			<th align="center" data-order-field="totalScore">已获学分</th>
			<th align="center" data-order-field="gender">性别</th>
			<c:if test="${type == 0}">
			
			</c:if>
			<c:if test="${type == 1}">
			<th align="center" data-order-field="joinCadresDay">任校级干部时间</th>
			<th align="center" data-order-field="duty">行政职务</th>
			</c:if>
			<th align="center" data-order-field="birthday">出生日期</th>
			
			<!--<th align="center" data-order-field="nativer">籍贯</th>
			<th align="center" data-order-field="nation">民族</th>
			<th align="center" data-order-field="idCard">身份证</th>
			<th align="center" data-order-field="tel">办公电话</th>
			<th align="center" data-order-field="mobile">手机号</th>
			<th align="center" data-order-field="email">Email</th>	
			<th align="center" data-order-field="status">状态</th>-->
    	</tr>
    	</thead>
    	<tbody>
    	<shiro:hasPermission name="Teacher:read">
    	<c:forEach var="item" items="#{page.list}" varStatus="index">
    	<tr data-id="${item.id }">
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
		<!-- 	<td>
				<c:if test="${item.type ==0}"><span class="label label-success">教师</span></c:if>
				<c:if test="${item.type ==1}"><span class="label label-primary">校长</span></c:if>
			</td> -->
			<td>${item.tno }</td>
			<td>
				<a href="javascript:;" onclick="teacherView(${item.id })">${item.name }</a>
			</td>
			<td> 
				<a href="javascript:;" onclick="teacherScoreView(${item.id },${item.totalScore })">${item.totalScore }</a>
			</td>
			<td> 
				<a href="javascript:;" onclick="teacherScoreView(${item.id },${item.totalScore })">${item.totalScoreNum }</a>
			</td>
			<td>
				<c:forEach items="${genders }" var="items">
					<c:if test="${items.id==item.gender}">${items.name }</c:if>
				</c:forEach>
			</td>
			<c:if test="${type == 0}">
			
			</c:if>
			<c:if test="${type == 1}">
			<td><fmt:formatDate value="${item.joinCadresDay }" pattern="yyyy-MM-dd"/></td>
			<td>
			<c:forEach items="${dutys }" var="items">
				<c:if test="${items.id==item.duty}">${items.name }</c:if>
			</c:forEach>
			</td>
			</c:if>
			<td><fmt:formatDate value="${item.birthday }" pattern="yyyy-MM-dd"/></td>
    	</tr>
    	</c:forEach>
    	</shiro:hasPermission>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>