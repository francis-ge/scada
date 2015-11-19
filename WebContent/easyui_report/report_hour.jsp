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
	
	<style type="text/css">
		* {margin: 0; padding: 0;}
	</style>

	<script type="text/javascript">
		$(function(){
			$('#datagrid').datagrid({
				toolbar:'#toolBar'
			});
			
		})
		
	</script>

  </head>
  
  <body>

		<table id ="datagrid" class="easyui-datagrid" url="" idField="id" singleSelect="true" fit="true" striped="true" fitColumns="true">
			<thead>   
		        <tr>   
		            <th data-options="field:'data',width:30,align:'center'">日期</th>
		            <th data-options="field:'name',width:20,align:'center'">风机名称</th>  
		            <th data-options="field:'a',width:20">风速(m/s)</th>  
		            <th data-options="field:'b',width:20">功率(kW)</th>  
		            <th data-options="field:'c',width:20">发电量(kWh)</th>  
		            <th data-options="field:'d',width:20">电机转速(rpm)</th>  
		            <th data-options="field:'e',width:20">电机温度(℃)</th>  
		            <th data-options="field:'f',width:20">机舱温度(℃)</th>  
		            <th data-options="field:'g',width:20">温控柜温度(℃)</th>  
		            <th data-options="field:'h',width:20">系统压力(Bar)</th>  
		        </tr>   
		    </thead>   
		</table>
		
		<div id="toolBar" style="height:25px,overflow:hidden" >
				<form id="from1" method="post">   
					选择日期:<input id="date" type="text" class="easyui-datebox" required="required"></input>
					小时段：<select id="hourSelect" class="easyui-combobox" style="width:100px;" required="required"
					data-options="valueField:'value', textField:'label', 
								  data: [{label:'0-1',value:'0'},
										{label:'1-2',value:'1'},
										{label:'2-3',value:'2'},
										{label:'3-4',value:'3'},
										{label:'4-5',value:'4'},
										{label:'5-6',value:'5'},
										{label:'6-7',value:'6'},
										{label:'7-8',value:'7'},
										{label:'8-9',value:'8'},
										{label:'9-10',value:'9'},
										{label:'10-11',value:'10'},
										{label:'11-12',value:'11'},
										{label:'12-13',value:'12'},
										{label:'13-14',value:'13'},
										{label:'14-15',value:'14'},
										{label:'15-16',value:'15'},
										{label:'16-17',value:'16'},
										{label:'17-18',value:'17'},
										{label:'18-19',value:'18'},
										{label:'19-20',value:'19'},
										{label:'20-21',value:'20'},
										{label:'21-22',value:'21'},
										{label:'22-23',value:'22'},
										{label:'23-24',value:'23'}]">
					</select>
					<a class="easyui-linkbutton" title="" href="javascript:void(0)">查询</a> 
				</form>
		</div>	
  </body>
	<div id="timer"></div>
</html>
