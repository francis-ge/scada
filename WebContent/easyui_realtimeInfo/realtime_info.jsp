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
	<link href="<%= request.getContextPath() %>/jquery-easyui-1.4.4/themes/bootstrap/easyui.css" rel="stylesheet">
	<link href="<%= request.getContextPath() %>/jquery-easyui-1.4.4/themes/icon.css" rel="stylesheet">
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
	<script src="<%= request.getContextPath() %>/jquery-easyui-1.4.4/jquery.min.js" charset="utf-8"></script>
	
	<!-- easyUI -->
	<script src="<%= request.getContextPath() %>/jquery-easyui-1.4.4/jquery.easyui.min.js" charset="utf-8"></script>
	<script src="<%= request.getContextPath() %>/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	
	<style type="text/css">
		* {margin: 0; padding: 0;}
	</style>
	

	<script type="text/javascript">
		$(function(){
			$('#datagrid').datagrid({
				toolbar:'#toolBar',
				loadMsg:''
			});
			
			$('#funSelect').combobox('select',1);
			
			setInterval(function(){
					    	var funId = $('#funSelect').combobox('getValue');
									
					    	$('#datagrid').datagrid('load',{
					    					funId:funId
				    						} )
				    }, 1000 );
			
		})
		
	</script>

  </head>
  
  <body>

		<table id ="datagrid" class="easyui-datagrid" url="AjaxRealTimeInfo" idField="id" singleSelect="true" fit="true" >
			<thead>   
		        <tr>   
		            <th data-options="field:'name',width:200,align:'right'">名称</th>
		            <th data-options="field:'value',width:150">数值</th>  
		        </tr>   
		    </thead>   
		</table>
		
		<div id="toolBar" style="height:25px,overflow:hidden" >
				选择风机:
				<select id="funSelect" class="easyui-combobox" style="width:200px;" url="ajaxAllfun"  valueField="id" textField="name">
				</select>
		</div>	
  </body>
	<div id="timer"></div>
</html>
