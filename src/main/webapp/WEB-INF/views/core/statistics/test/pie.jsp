<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<script src="${ctx}/static/BJUI/plugins/echarts_3.0/echarts.min.js"></script>
<script src="${ctx}/static/BJUI/plugins/echarts_3.0/theme/shine.js"></script>
<script type="text/javascript">
// 基于准备好的dom，初始化echarts实例
var pieChart = echarts.init(document.getElementById('pie-chart'));
//显示标题，图例和空的坐标轴   先设置完其它的样式，显示一个空的直角坐标轴，然后获取数据后填入数据。
pieChart.setOption({
	title : {
        text: '某站点用户访问来源',
        subtext: '纯属虚构',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
    },
    series : [
        {
            name: '访问来源',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'直接访问'},
                {value:310, name:'邮件营销'},
                {value:234, name:'联盟广告'},
                {value:135, name:'视频广告'},
                {value:1548, name:'搜索引擎'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});


//处理点击事件并且条状到相应的百度搜索页面
pieChart.on('click', function (params) {
    window.open('https://www.baidu.com/s?wd=' + encodeURIComponent(params.name));
});
</script>

<div  class="bjui-pageContent tableContent">
	 <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="pie-chart" style="width: 100%;height:100%;"></div>
</div>
