<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	
//基于准备好的dom，初始化echarts实例
var pieChart = echarts.init(document.getElementById('report_train_charts'));
//显示标题，图例和空的坐标轴   先设置完其它的样式，显示一个空的直角坐标轴，然后获取数据后填入数据。

pieChart.setOption({
	title : {
        text: '培训人员统计',
        subtext: '${wayName}'+'(总数:${total})',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        }
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ${title}
    },
    series : [
        {
            name: '人数',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:${data},
            itemStyle: {
            	normal:{ 
                    label:{ 
                      show: true, 
                      formatter: '{b} : {c} ({d}%)' 
                    }, 
                    labelLine :{show:true} 
              	}, 
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});
</script>
		
<div class="bjui-pageHeader">
    <form id="pagerForm" class="config_pager_form" data-toggle="ajaxsearch" action="${ctx}/core/statistics/train/main" method="post" data-reload-navtab="true">
        <input type="hidden" name="pageSize" value="1">
        <div class="bjui-searchBar pull-right">
        	<shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
            	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
            	 <select name="province" data-toggle="selectpicker" <c:if test="${sessionScope.currentUser.province!=0}">disabled</c:if>  
             	data-nextselect="#r_train_list_sform_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
					<option value="">--省市--</option>
	               	<c:forEach items="${query.queryProvinces }" var="item">
	                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==query.province}">selected</c:if>>${item.provinceName }</option>
	                </c:forEach>
                </select>
                </div>
                <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
        		<select name="city" id="r_train_list_sform_city" data-toggle="selectpicker" 
        			 data-nextselect="#r_train_list_sform_town" 
       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
        			 data-size="20" data-width="140px">
        			 <option value="">--城市--</option>
        			 <c:forEach items="${query.queryCitys }" var="item">
                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==query.city}">selected</c:if>>${item.cityName }</option>
                	</c:forEach>
            	</select>
            	</div>
            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
	            <select name="town" id="r_train_list_sform_town" data-toggle="selectpicker" 
	            	data-nextselect="#r_train_list_sform_school" 
	            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
	            	  data-size="20" data-width="140px">
	            	<option value="">--区县--</option>
        			 <c:forEach items="${query.queryTowns }" var="item">
                     		<option value="${item.townNo }" <c:if test="${item.townNo==query.town}">selected</c:if>>${item.townName }</option>
                	</c:forEach>
	            </select>
	            </div>
            </shiro:hasAnyRoles>
			<label>认证时间从</label>
			 <input type="text" name="startDate" id="f_score_checkdate_start" value="${query.startDate }"  data-toggle="datepicker" data-rule="date" size="11">
			<label>到</label><input type="text" name="endDate" id="f_score_checkdate_end"  value="${query.endDate }" data-toggle="datepicker" data-rule="date" size="11">
            
			<label>统计方式</label>
			<select name="way" data-toggle="selectpicker" data-width="100px">
               	<option value="">--请选择--</option>
               	<c:forEach items="${ways }" var="item">
               		<option  value="${item.value }" <c:if test="${item.value==query.way}">selected</c:if>>${item.desc }</option>
               	</c:forEach>
			</select>&nbsp;
            <button type="submit" class="btn btn-blue" id="r_train_search" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div class="wrapdiv4" style="height:100%;">
        <div id="report_train_charts" class="contentdiv4"></div>
    </div>
</div>

