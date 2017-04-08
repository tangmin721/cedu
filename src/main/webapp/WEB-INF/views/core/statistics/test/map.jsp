<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<script src="${ctx}/static/BJUI/plugins/echarts_3.0/echarts.min.js"></script>
<script type="text/javascript">
function randomData() {
    return Math.round(Math.random()*1000);
}

//显示标题，图例和空的坐标轴   先设置完其它的样式，显示一个空的直角坐标轴，然后获取数据后填入数据。
$.get('${ctx}/static/BJUI/plugins/echarts_3.0/json/hubei.json', function (hubeiJson) {
    echarts.registerMap('hubei', hubeiJson);
    var chart = echarts.init(document.getElementById('hubei-map'));
    chart.setOption({
   	    title: {
            text: '湖北省教师人口分布图 （2016）',
            subtext: '数据来自虚拟',
            sublink: 'http://www.baidu.com'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}<br/>{c} (人)'
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
        visualMap: {
            min: 0,
            max: 1000,
            text:['High','Low'],
            realtime: false,
            calculable: true,
            color: ['orangered','yellow','lightskyblue']
        },
        series: [{
		            type: 'map',
		            map: 'hubei',
		            itemStyle:{
			            normal:{label:{show:true}},
			            emphasis:{label:{show:true}}
	        		},
	        		data:[
	                       {name: '十堰市', value: randomData()},
	                       {name: '襄阳市', value: randomData()},
	                       {name: '神农架林区', value: randomData()},
	                       {name: '恩施土家族苗族自治州', value: randomData()},
	                       {name: '宜昌市', value: randomData()},
	                       {name: '荆门市', value: randomData()},
	                       {name: '随州市', value: randomData()},
	                       {name: '孝感市', value: randomData()},
	                       {name: '天门市', value: randomData()},
	                       {name: '潜江市', value: randomData()},
	                       {name: '仙桃市', value: randomData()},
	                       {name: '荆州市', value: randomData()},
	                       {name: '武汉市', value: randomData()},
	                       {name: '鄂州市', value: randomData()},
	                       {name: '黄冈市', value: randomData()},
	                       {name: '黄石市', value: randomData()},
	                       {name: '咸宁市', value: randomData()}
	                ]
        }]
    });
});

</script>

<div  class="bjui-pageContent tableContent">
	 <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="hubei-map" style="width: 100%;height:100%;"></div>
</div>
