<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<script src="${ctx}/static/BJUI/plugins/echarts_3.0/echarts.min.js"></script>
<script src="${ctx}/static/BJUI/plugins/echarts_3.0/theme/shine.js"></script>
<script type="text/javascript">
// 基于准备好的dom，初始化echarts实例
var lineChart = echarts.init(document.getElementById('line-chart'));
//显示标题，图例和空的坐标轴   先设置完其它的样式，显示一个空的直角坐标轴，然后获取数据后填入数据。
lineChart.setOption({
	title: {
        text: '未来一周气温变化',
        subtext: '纯属虚构'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['最高气温','最低气温']
    },
    toolbox: {
        show: true,
        feature: {
            dataZoom: {},
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            restore: {},
            saveAsImage: {}
        }
    },
    xAxis:  {
        type: 'category',
        boundaryGap: false,
        data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    yAxis: {
        type: 'value',
        axisLabel: {
            formatter: '{value} °C'
        }
    },
    series: [
        {
            name:'最高气温',
            type:'line',
            data:[11, 11, 15, 13, 12, 13, 10],
            markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'最低气温',
            type:'line',
            data:[1, -2, 2, 5, 3, 2, 0],
            markPoint: {
                data: [
                    {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        }
    ]
});

//异步加载数据
//$.get('${ctx}/core/statistics/test/lineData.json').done(function(data) {
   
//	if(data.statusCode!='200'){
//		$(this).alertmsg('error', json.message);
//	}else{
		 // 填入数据
//		lineChart.setOption({
//	        xAxis: {
//	            data: data.data.categories
//	        },
//	        series: [{
	            // 根据名字对应到相应的系列
//	            name: '销量',
//	            data: data.data.data
//	        }]
//	    });
//	}
    
//});

</script>

<div  class="bjui-pageContent tableContent">
	 <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="line-chart" style="width: 100%;height:100%;"></div>
</div>
