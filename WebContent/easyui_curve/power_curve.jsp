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
		
        <label for="date1">选择日期：</label>   
     	<input id="date1" type="text" class="easyui-datebox" required="required"></input>
	    <input id="date2" type="text" class="easyui-datebox" required="required"></input>
    	<a class="easyui-linkbutton" title="" href="javascript:void(0)">查询</a>
	</form>  
  	
	<div id="chart" style="height:500px"></div>
	
	<script type="text/javascript">
		var chart = echarts.init(document.getElementById('chart'));
		var option = {
			    title : {
			        text: '风速功率图',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['功率(kWh)']
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

			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : true,
			            data : [1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,
			                    8.5,9,9.5,10,10.5,11,11.5,12,12.5,13,13.5,
			                    14,14.5,15,15.5,16,16.5,17,17.5,18,18.5,19,
			                    19.5,20,20.5,21,21.5,22,22.5,23,23.5,24,24.5,25]
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            scale: true,
			            name : '功率(kWh)',
			            boundaryGap: [0.2, 0.2]
			        }
			    ],
			    series : [
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
	</script>
  </body>
  
  

</html>
