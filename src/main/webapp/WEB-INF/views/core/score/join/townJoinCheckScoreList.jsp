<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#town_check_score_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});

$('#town_check_score_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});


function viewScore(id,type){
	switch (type)
	{
	case 1:
	    $(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm1/'+id, type:'POST',title:'查看【项目学时】申报',mask:'true',width:'600',height:'320'});
	  break;
	case 2:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm2/'+id, type:'POST',title:'查看【学历提升】申报',mask:'true',width:'600',height:'320'});
	  break;
	case 3:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm3/'+id, type:'POST',title:'查看【校本研修】申报',mask:'true',width:'600',height:'320'});
	  break;
	case 4:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm4/'+id, type:'POST',title:'查看【论文/专著】申报',mask:'true',width:'600',height:'320'});
	  break;
	case 5:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm5/'+id, type:'POST',title:'查看【学术交流】申报',mask:'true',width:'600',height:'320'});
	  break;
	case 6:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm6/'+id, type:'POST',title:'查看【课题研究】申报',mask:'true',width:'600',height:'320'});
	  break;
	case 7:
		$(this).dialog({id:'score_form', url:'${ctx}/core/score/scoreForm7/'+id, type:'POST',title:'查看【其他】申报',mask:'true',width:'600',height:'320'});
	  break;
	}
}

function saveStatus(id,status){

	$.ajax({ 
        type: "POST", 
        url: '${ctx}/core/score/saveStatus?id='+id+'&status='+status,
        dataType : "json",
        success: function(data) {
        	$(this).alertmsg('ok', '审核通过！');
        	$('#check_score_list_search').trigger('click');
        },
        error :  function(){
        	$(this).alertmsg('error', '系统错误！');
        }
    }); 
}

function unpassScore(id){
	$(this).dialog({id:'np-score-unpass-create', url:'${ctx}/core/score/scoreUnPassForm/'+id, type:'POST',title:'审核不通过，请输入为什么不通过',mask:'true',width:'550',height:'240'});
}

function checkSchool(school,schoolName){
	$(this).dialog({id:'np-check-school', url:'${ctx}/core/score/checkScoreList/0?pschool='+school, type:'POST',title:'学时申报明细审核——'+schoolName,mask:'true',width:'1500',height:'640'});
}

var score = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#town_check_score_table input[name="ids"]').each(function(){ 
				 var checked = $(this).is(':checked'), val = $(this).val();
				 if(checked){
					 list.push(val);
				 }
			}) 
			return list;
		}
}

$('#but_score_allpass_town').on('click',function(e){
	$(this).alertmsg('confirm', '确定一键审核通过全部项吗？',{
		okCall:function(){
			$.ajax({ 
		        type: "POST", 
		        url: '${ctx}/core/score/oneKeyPass',
		        dataType : "json",
		        success: function(data) {
		        	$(this).alertmsg('ok', '一键审核通过操作成功！');
		        	$('#check_score_list_search').trigger('click');
		        	$('#town_check_score_list_search').trigger('click');
		        	$('#province_check_score_list_search').trigger('click');
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
		<button type="button" id="but_score_allpass_town" class="btn btn-success" data-icon="keyboard-o" >一键全部通过</button>
	</div>
    <form id="pagerForm" class="score_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/scoreJoin/townJoinCheckScoreList" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#town_join__sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="town_join__sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#town_join__sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="town_join__sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#town_join__sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="town_join__sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true">
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
        	
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search" id="town_check_score_list_search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
        <div class="bjui-moreSearch pull-right">
            
        </div>
    </form>
</div>

<div id="town_check_score_table" class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
    	<thead>
    	<tr class="trth">
    		<th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
    		<th width="26" title="序号">No.</th>
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
    		<th align="center" title="schoolNo">学校编号</th>
			<th align="center" title="审核状态">审核状态</th>
			<th align="center" title="审核">审核</th>
    	</tr>
    	</thead>
    	<tbody>
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
			<td>${item.schoolNo }</td>
    		<td>
    			待审（${item.dnum }）
    		</td>
    		<td>
    			<button type="button" class="btn btn-success" data-icon="check-circle" onclick="checkSchool(${item.school },'${item.schoolName }')" >明细审核</button>
    		</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>