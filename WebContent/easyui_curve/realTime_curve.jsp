<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<!-- easyUI -->
	<link href="<%=basePath %>/jquery-easyui-1.4.4/themes/bootstrap/easyui.css" rel="stylesheet">
	<link href="<%=basePath %>/jquery-easyui-1.4.4/themes/icon.css" rel="stylesheet">
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
	<script src="<%= basePath %>/jquery-easyui-1.4.4/jquery.min.js" charset="utf-8"></script>
	
	<!-- easyUI -->
	<script src="<%= basePath %>/jquery-easyui-1.4.4/jquery.easyui.min.js" charset="utf-8"></script>
	<script src="<%= basePath %>/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script src="<%= basePath %>/js/echarts-all.js" charset="utf-8"></script>
	
	<style type="text/css">
		* {margin: 0; padding: 0;}
	</style>



  </head>
  
  <body>
  	<form id="form" method="post">
		<label for="funSelect">选择风机：</label>
		<select id="funSelect" class="easyui-combobox" style="width:200px;" url="ajaxAllfun"  valueField="id" textField="name" required="required"></select>
		
        <label for="valSelect">选择数据：</label>   
        <select id="valSelect" class="easyui-combobox" style="width:200px;" required="required" >
			<option value="windSpeed">风速(m/s)</option>
			<option value="windDirection">风向(°)</option>
			<option value="power">有功功率(kW)</option>
			<option value="reactivePower">无功功率(kVar)</option>
			<option value="ambientTemperature">环境温度(℃)</option>
		</select>
		     
	</form>  
  	
	<div id="chart" style="height:500px"></div>
	
	<script type="text/javascript">
		var chart = echarts.init(document.getElementById('chart'));
		var option = {
			    title : {
			        text: '动态数据',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['风速(s/m)', '功率(kWh)']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    dataZoom : {
			        show : false,
			        start : 0,
			        end : 100
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : true,
			            data : (function (){
			                var now = new Date();
			                var res = [];
			                var len = 10;
			                while (len--) {
			                    res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
			                    now = new Date(now - 1000);
			                }
			                return res;
			            })()
			        },
			        {
			            type : 'category',
			            boundaryGap : true,
			            data : (function (){
			                var res = [];
			                var len = 10;
			                while (len--) {
			                    res.push(len + 1);
			                }
			                return res;
			            })()
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            scale: true,
			            name : '风速(s/m)',
			            boundaryGap: [0.2, 0.2]
			        },
			        {
			            type : 'value',
			            scale: true,
			            name : '功率(kWh)',
			            boundaryGap: [0.2, 0.2]
			        }
			    ],
			    series : [
			        {
			            name:'风速(s/m)',
			            type:'bar',
			            xAxisIndex: 1,
			            yAxisIndex: 1,
			            data:(function (){
			                var res = [];
			                var len = 10;
			                while (len--) {
			                    res.push(Math.round(Math.random() * 1000));
			                }
			                return res;
			            })()
			        },
			        {
			            name:'功率(kWh)',
			            type:'line',
			            data:(function (){
			                var res = [];
			                var len = 10;
			                while (len--) {
			                    res.push((Math.random()*10 + 5).toFixed(1) - 0);
			                }
			                return res;
			            })()
			        }
			    ]
			};
		
			chart.setOption(option);
			
			var lastData = 11;
			var axisData;
			var timeTicket;
			
			clearInterval(timeTicket);
			timeTicket = setInterval(function (){
			    lastData += Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1);
			    lastData = lastData.toFixed(1) - 0;
		 
			    axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,''); 
		
			    // 动态数据接口 addData
			
			    chart.addData([
			        [
			            0,        // 系列索引
			            Math.round(Math.random() * 1000), // 新增数据
			            true,     // 新增数据是否从队列头部插入
			            false     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
			        ],
			        [
			            1,        // 系列索引
			            lastData, // 新增数据
			            true,    // 新增数据是否从队列头部插入
			            false,    // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
			            axisData  // 坐标轴标签
			        ]
			    ]);
			}, 1000);
		                   
	</script>
  </body>
  
  

</html>
