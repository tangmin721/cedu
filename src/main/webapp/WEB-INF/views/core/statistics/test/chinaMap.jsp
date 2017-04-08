<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script src="${ctx}/static/BJUI/plugins/echarts_3.0/echarts.min.js"></script>
<script src="${ctx}/static/BJUI/plugins/echarts_3.0/theme/shine.js"></script>
<script type="text/javascript">
// 基于准备好的dom，初始化echarts实例
var myChartSimple = echarts.init(document.getElementById('simple'));
//显示标题，图例和空的坐标轴   先设置完其它的样式，显示一个空的直角坐标轴，然后获取数据后填入数据。
myChartSimple.setOption({
    title: {
        text: '异步数据加载示例,柱状图'
    },
    tooltip: {},
    legend: {
        data:['销量']
    },
    xAxis: {
        data: []
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: []
    }]
});

//异步加载数据
$.get('${ctx}/core/statistics/test/data.json').done(function(data) {
   
	if(data.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		 // 填入数据
		myChartSimple.setOption({
	        xAxis: {
	            data: data.data.categories
	        },
	        series: [{
	            // 根据名字对应到相应的系列
	            name: '销量222',
	            data: data.data.data
	        }]
	    });
	}
    
});


</script>

<div class="bjui-pageContent">
    <div style="margin:15px auto 0; width:96%;">
        <div class="row" style="padding: 0 8px;">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading"><h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i>柱状图 <a href="static/doc/chart/echarts.html" data-toggle="navtab" data-id="doc-echarts" data-title="ECharts图表说明">使用说明</a></h3></div>
                    <div class="panel-body">
                        <div id="simple" style="mini-width:400px;height:350px"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading"><h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i>中国地图 <a href="static/doc/chart/echarts.html" data-toggle="navtab" data-id="doc-echarts" data-title="ECharts图表说明">使用说明</a></h3></div>
                    <div class="panel-body">
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading"><h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i>湖北教师分布 <a href="doc/chart/echarts.html" data-toggle="navtab" data-id="doc-echarts" data-title="ECharts图表说明">使用说明</a></h3></div>
                    <div class="panel-body">
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading"><h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i>柱状/拆线图 <a href="doc/chart/echarts.html" data-toggle="navtab" data-id="doc-echarts" data-title="ECharts图表说明">使用说明</a></h3></div>
                    <div class="panel-body">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
    </ul>
</div>
