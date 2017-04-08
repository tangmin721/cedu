<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
//基于准备好的dom，初始化echarts实例
var pieChart = echarts.init(document.getElementById('report_teacher_charts'));
//显示标题，图例和空的坐标轴   先设置完其它的样式，显示一个空的直角坐标轴，然后获取数据后填入数据。

pieChart.setOption({
	title : {
        text: '${headName}信息统计',
        subtext: '${wayName}'+'(总人数:${total})',
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
            name: '教师人数',
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