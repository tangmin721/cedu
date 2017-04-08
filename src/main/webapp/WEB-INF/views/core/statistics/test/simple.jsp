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
    toolbox: {
        show: true,
        feature: {
            dataZoom: {},
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar', 'stack', 'tiled']},
            restore: {},
            saveAsImage: {}
        }
    },
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
	            name: '销量',
	            data: data.data.data
	        }]
	    });
	}
    
});


//处理点击事件并且条状到相应的百度搜索页面
myChartSimple.on('click', function (params) {
    window.open('https://www.baidu.com/s?wd=' + encodeURIComponent(params.name));
});
</script>

<div  class="bjui-pageContent tableContent">
	 <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="simple" style="width: 100%;height:100%;"></div>
</div>
