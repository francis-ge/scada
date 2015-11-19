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
	<link href="<%= basePath %>/jquery-easyui-1.4.4/themes/bootstrap/easyui.css" rel="stylesheet">
	<link href="<%= basePath %>/jquery-easyui-1.4.4/themes/icon.css" rel="stylesheet">
	
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
		            <th data-options="field:'data',width:150,align:'center'">日期</th>
		            <th data-options="field:'name',width:150,align:'center'">风机名称</th>  
		            <th data-options="field:'a',width:120">环境温度(℃)</th>  
		            <th data-options="field:'b',width:120">发电小时数(h)</th>  
		            <th data-options="field:'c',width:120">维护小时数(h)</th>  
		            <th data-options="field:'d',width:120">故障小时数(h)</th>  
		            <th data-options="field:'e',width:120">风机正常小时数(h)</th>  
		            <th data-options="field:'f',width:120">总运行小时数(h)</th>  
		        </tr>   
		    </thead>   
		</table>
		
		<div id="toolBar" style="height:25px,overflow:hidden" >
			
				<form id="from1" method="post">   
					选择日期:<input id="date" type="text" class="easyui-datebox" required="required"></input>
					<a class="easyui-linkbutton" title="" href="javascript:void(0)">查询</a> 
				</form>
				
		</div>	
  </body>
	<div id="timer"></div>
</html>
