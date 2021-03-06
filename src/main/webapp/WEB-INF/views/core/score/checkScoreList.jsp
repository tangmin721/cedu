<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
$('#check_score_table').on('ifChanged','input[name="ids"]', function(e) {
	e.stopPropagation();
    var checked = $(this).is(':checked'), val = $(this).val();
    if (checked){
        $(this).parent().parent().parent().addClass('selected'); 
    }else{
        $(this).parent().parent().parent().removeClass('selected'); 
	}
});


$('#check_score_table').on('click','tr:not(.trth)', function(e) {
	e.stopPropagation();
	$(this).find(':checkbox').iCheck('check');
	$(this).addClass('selected'); 
	$(this).siblings().find(':checkbox').iCheck('uncheck');
});

//批量通过
$('#but_score_pass').on('click',function(e) {
	var list = score.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要审核的行！');
	}else{
		$(this).alertmsg('confirm', '确定批量审核通过选中项吗？',{
            okCall:function(){
            	$.ajax({ 
                    type: "POST", 
                    url: '${ctx}/core/score/checkMultiByIds?ids='+list+'&status=1',
                    dataType : "json",
                    success: function(data) {
                    	$(this).alertmsg('ok', '批量审核通过操作成功！');
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
	}
});

$('#but_score_unpass').on('click',function(e){
	var list = score.selectedIds();
	if(list.length==0){
		$(this).alertmsg('warn', '请选择需要审核的行！');
	}else{
		$(this).alertmsg('confirm', '确定批量审核不通过选中项吗？',{
            okCall:function(){
            	$(this).dialog({id:'np-score-unpasses-form', url:'${ctx}/core/score/scoreUnPassesForm?ids='+list, type:'POST',title:'审核不通过，请输入为什么不通过',mask:'true',width:'550',height:'240'});
            }
        });
	}
})

$('#but_score_allpass_school').on('click',function(e){
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



function viewCheckScore(id,type){
	switch (type)
	{
	case 1:
	    $(this).dialog({id:'check_score_view', url:'${ctx}/core/score/scoreView1/'+id, type:'POST',title:'【项目学时】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 2:
		$(this).dialog({id:'check_score_view', url:'${ctx}/core/score/scoreView2/'+id, type:'POST',title:'【学历提升】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 3:
		$(this).dialog({id:'check_score_view', url:'${ctx}/core/score/scoreView3/'+id, type:'POST',title:'【校本研修】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 4:
		$(this).dialog({id:'check_score_view', url:'${ctx}/core/score/scoreView4/'+id, type:'POST',title:'【论文/专著】详细信息',mask:'true',width:'420',height:'420'});
	  break;
	case 5:
		$(this).dialog({id:'check_score_view', url:'${ctx}/core/score/scoreView5/'+id, type:'POST',title:'【学术交流】详细信息',mask:'true',width:'420',height:'410'});
	  break;
	case 6:
		$(this).dialog({id:'check_score_view', url:'${ctx}/core/score/scoreView6/'+id, type:'POST',title:'【课题研究】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 7:
		$(this).dialog({id:'check_score_view', url:'${ctx}/core/score/scoreView7/'+id, type:'POST',title:'【其他】详细信息',mask:'true',width:'420',height:'260'});
	  break;
	case 8:
		$(this).dialog({id:'check_score_view', url:'${ctx}/core/score/scoreView8/'+id, type:'POST',title:'【自主研修】详细信息',mask:'true',width:'760',height:'510'});
	  break;
	}
}

function adminEditScore(id,type){
	switch (type)
	{
		case 1:
			$(this).dialog({id:'admin_edit_form', url:'${ctx}/core/score/scoreForm1/'+id+'?adminUpdate=true', type:'POST',title:'管理员 修改【项目学时】学时',mask:'true',width:'760',height:'340'});
			break;
		case 2:
			$(this).dialog({id:'admin_edit_form', url:'${ctx}/core/score/scoreForm2/'+id+'?adminUpdate=true', type:'POST',title:'管理员 修改【学历提升】学时',mask:'true',width:'760',height:'360'});
			break;
		case 3:
			$(this).dialog({id:'admin_edit_form', url:'${ctx}/core/score/scoreForm3/'+id+'?adminUpdate=true', type:'POST',title:'管理员 修改【校本研修】学时',mask:'true',width:'760',height:'220'});
			break;
		case 4:
			$(this).dialog({id:'admin_edit_form', url:'${ctx}/core/score/scoreForm4/'+id+'?adminUpdate=true', type:'POST',title:'管理员 修改【论文/专著】学时',mask:'true',width:'760',height:'460'});
			break;
		case 5:
			$(this).dialog({id:'admin_edit_form', url:'${ctx}/core/score/scoreForm5/'+id+'?adminUpdate=true', type:'POST',title:'管理员 修改【学术交流】学时',mask:'true',width:'760',height:'360'});
			break;
		case 6:
			$(this).dialog({id:'admin_edit_form', url:'${ctx}/core/score/scoreForm6/'+id+'?adminUpdate=true', type:'POST',title:'管理员 修改【课题研究】学时',mask:'true',width:'760',height:'350'});
			break;
		case 7:
			$(this).dialog({id:'admin_edit_form', url:'${ctx}/core/score/scoreForm7/'+id+'?adminUpdate=true', type:'POST',title:'管理员 修改【其他】学时',mask:'true',width:'760',height:'230'});
			break;
		case 8:
			$(this).dialog({id:'admin_edit_form', url:'${ctx}/core/score/scoreForm8/'+id+'?adminUpdate=true', type:'POST',title:'管理员 修改【自主研修】学时',mask:'true',width:'760',height:'510'});
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
        	$('#town_check_score_list_search').trigger('click');
        	$('#province_check_score_list_search').trigger('click');
        },
        error :  function(){
        	$(this).alertmsg('error', '系统错误！');
        }
    }); 
}

function unpassScore(id){
	$(this).dialog({id:'np-score-unpass-form', url:'${ctx}/core/score/scoreUnPassForm/'+id, type:'POST',title:'审核不通过，请输入为什么不通过',mask:'true',width:'550',height:'240'});
}

var score = {
		selectedId: function(){
			var list = this.selectedIds();
			if(list.length==1)
				return list[0];
		},
		selectedIds: function(){
			var list = [];
			$('#check_score_table input[name="ids"]').each(function(){ 
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
		<shiro:hasPermission name="Score:check">
			<button type="button" id="but_score_pass" class="btn btn-success" data-icon="check-circle" >批量通过</button>
			<button type="button" id="but_score_unpass" class="btn btn-danger" data-icon="times-circle" >批量不通过</button>
		</shiro:hasPermission>
		<shiro:hasRole name="SCHOOL_ADMIN">
			<button type="button" id="but_score_allpass_school" class="btn btn-success" data-icon="keyboard-o" >一键全部通过</button>
		</shiro:hasRole>
	</div>
    <form id="pagerForm" class="score_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/score/checkScoreList/${pagetype}" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageCurrent}">
        <input type="hidden" name="orderField" value="${param.orderField}">
        <input type="hidden" name="orderDirection" value="${param.orderDirection}">
        <div class="bjui-searchBar pull-right">
        	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#check_score_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="check_score_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#check_score_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="check_score_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#check_score_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
	            <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
	            <select name="school" id="check_score_sform_school" data-toggle="selectpicker" 
	            	 data-size="20" data-width="140px" data-live-search="true" >
	            	<option value="">--学校--</option>
        			 <c:forEach items="${query.querySchools }" var="item">
                     		<option value="${item.id }" <c:if test="${item.id==query.school}">selected</c:if>>${item.name }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
        	<label>培训类型：</label>
			 	<input type="radio" name="type" id="f_score_type1" data-toggle="icheck" value="0" <c:if test="${query.type==0}">checked</c:if> data-label="集中培训&nbsp;&nbsp;">
                <input type="radio" name="type" id="f_score_type2" data-toggle="icheck" value="1" <c:if test="${query.type==1}">checked</c:if> data-label="非集中培训">&nbsp;&nbsp;&nbsp;&nbsp;
            
            <label>学时种类：</label>
			<select name="scoreType" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${scoreTypes }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.scoreType}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;&nbsp;   
             
			<label>年度：</label>
			<select name="year" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${years }" var="item">
               		<option  value="${item.id }" <c:if test="${item.id==query.year}">selected</c:if>>${item.name }</option>
               	</c:forEach>
			</select>&nbsp;
            <button type="button" class="showMoreSearch" data-toggle="moresearch" data-name="custom2"><i class="fa fa-angle-double-down"></i></button>
            <button type="submit" class="btn btn-blue" data-icon="search" id="check_score_list_search">查询</button>&nbsp;
        </div>
        <div class="bjui-moreSearch pull-right">
			
			
			<label>教师姓名：</label><input type="text" value="${query.tname }" name="tname" class="form-control" size="10">&nbsp;
			<label>标识码：</label><input type="text" value="${query.tno }" name="tno" class="form-control" size="10">&nbsp;
			<label>项目编号：</label><input type="text" value="${query.pno }" name="pno" class="form-control" size="10">&nbsp;
        </div>
    </form>
</div>

<div id="check_score_table" class="bjui-pageContent tableContent">
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
    		<th align="center" data-order-field="tname" title="培训类型">姓名</th>
    		<th align="center" data-order-field="tno" title="tno">标识码</th>
    		<th align="center" data-order-field="type" title="培训类型">培训类型</th>
    		<th align="center" data-order-field="scoreType" title="学时种类">学时种类</th>
    		<th align="center" data-order-field="name" title="名称">名称</th>
    		<!--  <th align="center" data-order-field="pno" title="项目编号">项目编号</th>-->
    		<th align="center" data-order-field="year" title="年度">年度</th>
			<th align="center" data-order-field="score" title="学时">申报学时</th>
			<th align="center" data-order-field="createTime" title="申报时间">申报时间</th>
			<th align="center" data-order-field="status" title="状态">状态</th>
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
			<td><a href="javascript:;" onclick="teacherView(${item.tid })">${item.tname }</a></td>
    		<td>${item.tno }</td>
    		<td>
    			<c:if test="${item.type ==0}"><span class="label label-success">集中培训</span></c:if>
				<c:if test="${item.type ==1}"><span class="label label-primary">非集中培训</span></c:if>
    		</td>
    		<td>
    			<c:if test="${item.scoreType ==1}"><span class="label label-info">项目学时</span></c:if>
    			<c:if test="${item.scoreType ==2}"><span class="label label-info">学历提升</span></c:if>
    			<c:if test="${item.scoreType ==3}"><span class="label label-info">校本研修</span></c:if>
    			<c:if test="${item.scoreType ==4}"><span class="label label-info">论文/专著</span></c:if>
    			<c:if test="${item.scoreType ==5}"><span class="label label-info">学术交流</span></c:if>
    			<c:if test="${item.scoreType ==6}"><span class="label label-info">课题研究</span></c:if>
    			<c:if test="${item.scoreType ==7}"><span class="label label-info">其他</span></c:if>
    			<c:if test="${item.scoreType ==8}"><span class="label label-info">自主研修</span></c:if>
    		</td>
    		<td>
    			<a href="javascript:;" onclick="viewCheckScore(${item.id },${item.scoreType})">${item.name }</a>
    		</td>
    		<!--<td>${item.pno }</td>-->
    		<td>${item.yearName }</td>
			<td>
				<c:if test="${not empty item.schoolUpdateMemo or not empty item.townUpdateMemo}">
					<a href="javascript:;" data-toggle="tooltip"  data-placement="right"
					   title='管理员修改说明：<c:if test="${not empty item.schoolUpdateMemo}">【学校管理员修改原因:${item.schoolUpdateMemo}】</c:if>
					   		  <c:if test="${not empty item.townUpdateMemo}">【区县管理员管理员修改原因:${item.townUpdateMemo}】</c:if>' >${item.score }</a>
				</c:if>
				<c:if test="${empty item.schoolUpdateMemo and  empty item.townUpdateMemo}">
					${item.score }
				</c:if>
			</td>
			<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd"/></td>
			<td>
				<c:if test="${item.status ==10}"><span class="label label-warning">校级待审</span></c:if>
    			<c:if test="${item.status ==11}"><span class="label label-danger" data-toggle="tooltip"  data-placement="right" title="不通过原因:${item.checkDesc}" >校级不通过</span></c:if>
    			<c:if test="${item.status ==20}"><span class="label label-warning">区县待审</span></c:if>
    			<c:if test="${item.status ==21}"><span class="label label-danger" data-toggle="tooltip"  data-placement="right" title="不通过原因:${item.checkDesc}" >区县不通过</span></c:if>
    			<c:if test="${item.status ==30}"><span class="label label-warning">省级待审</span></c:if>
    			<c:if test="${item.status ==31}"><span class="label label-danger" data-toggle="tooltip"  data-placement="right" title="不通过原因:${item.checkDesc}" >省级不通过</span></c:if>
    			<c:if test="${item.status ==40}"><span class="label label-success">学时认证完成</span></c:if>
			</td>
			<td>
				<shiro:hasPermission name="Score:check">
						<button type="button" class="btn btn-success" data-icon="check-circle" onclick="saveStatus(${item.id },1)"  data-toggle="tooltip"  data-placement="top" title="通过"></button>
						<button type="button" class="btn btn-danger" data-icon="times-circle" onclick="unpassScore(${item.id })" data-toggle="tooltip"  data-placement="top" title="不通过"></button>
				</shiro:hasPermission>
				<shiro:hasAnyRoles name="TOWN_ADMIN,SCHOOL_ADMIN">
					<button type="button" class="btn btn-warning" data-icon="edit"
							onclick="adminEditScore(${item.id },${item.scoreType})" data-toggle="tooltip"
							data-placement="top" title="修改"></button>
				</shiro:hasAnyRoles>
			</td>
    	</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
<%@ include file="/WEB-INF/common/page.jsp"%>