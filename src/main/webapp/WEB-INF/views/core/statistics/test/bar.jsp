<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<script src="${ctx}/static/BJUI/plugins/echarts_3.0/echarts.min.js"></script>
<script src="${ctx}/static/BJUI/plugins/echarts_3.0/theme/shine.js"></script>
<script type="text/javascript">
// 基于准备好的dom，初始化echarts实例
var barChart = echarts.init(document.getElementById('bar-chart'));
//显示标题，图例和空的坐标轴   先设置完其它的样式，显示一个空的直角坐标轴，然后获取数据后填入数据。
barChart.setOption({
	tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data: ['直接访问', '邮件营销','联盟广告','视频广告','搜索引擎']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis:  {
        type: 'value'
    },
    yAxis: {
        type: 'category',
        data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    series: [
        {
            name: '直接访问',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: [320, 302, 301, 334, 390, 330, 320]
        },
        {
            name: '邮件营销',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
            name: '联盟广告',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: [220, 182, 191, 234, 290, 330, 310]
        },
        {
            name: '视频广告',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: [150, 212, 201, 154, 190, 330, 410]
        },
        {
            name: '搜索引擎',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: [820, 832, 901, 934, 1290, 1330, 1320]
        }
    ]
});

//异步加载数据
//$.get('${ctx}/core/statistics/test/barData.json').done(function(data) {
   
//	if(data.statusCode!='200'){
//		$(this).alertmsg('error', json.message);
//	}else{
		 // 填入数据
//		barChart.setOption({
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
    <div id="bar-chart" style="width: 100%;height:100%;"></div>
</div>
