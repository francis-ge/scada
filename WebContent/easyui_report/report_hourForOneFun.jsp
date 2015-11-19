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
		            <th data-options="field:'name',width:'10%',align:'center'">风机名称</th>  
		            <th data-options="field:'date',width:'5%',align:'center'">小时</th>
		            <th data-options="field:'b',width:'10%'">平均风速(m/s)</th>  
		            <th data-options="field:'c',width:'10%'">平均功率(kW)</th>  
		            <th data-options="field:'d',width:'10%'">平均无功功率(KVar)</th>  
		            <th data-options="field:'e',width:'10%'">最大风速(m/s)</th>  
		            <th data-options="field:'f',width:'10%'">最大功率(kW)</th>  
		            <th data-options="field:'g',width:'10%'">环境温度</th>  
		            <th data-options="field:'a',width:'10%'">发电量(kWh)</th>  
		        </tr>   
		    </thead>   
		</table>
		
		<div id="toolBar" style="height:25px,overflow:hidden" >
				<form id="from1" method="post"> 
					<label for="funSelect">选择风机 ：</label>  
					<select id="funSelect" class="easyui-combobox" style="width:200px;" url="ajaxAllfun"  valueField="id" textField="name" required="required">
					</select>
					<label for="date1">选择时间段:</label>
					<input id="date1" type="text" class="easyui-datebox" required="required"></input>
					<a class="easyui-linkbutton" title="" href="javascript:void(0)">查询</a> 
				</form>
				
		</div>	
  </body>
	<div id="timer"></div>
</html>
