<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
		$('.easyui-accordion a, .accordionMenu a').click(
			function(){
				var src = $(this).attr('title');
				var title = $(this).html();
				
				if (src=="") return false;
				
				if($('.easyui-tabs').tabs('exists', title)==true){
					$('.easyui-tabs').tabs('select',title);
				}else{
					$('#tabs').tabs('add',{
						title : title,
						content : '<iframe frameborder=0 style=height:100%;width:100% src=' + src + '></iframe>',
						closable : true
						})		
				}
			})
	})
</script>

<title></title>
</head>

<body class="easyui-layout" fit="true">   
    <div data-options="region:'north',title:'',split:false" style="height:50px;"></div>   
    <div  data-options="region:'west',title:'导航栏',split:true" style="width:150px;">
	    <div  class="easyui-accordion" fit="true">
	    	<div title="风场概览" style="overflow:auto;">
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="" href="#">风场概览</a>
	    	</div>
	    	
	    	<div title="瞬时信息" style="overflow:auto;">
    			<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_realtimeInfo/realtime_info.jsp" href="#">瞬时信息</a>
	    	</div>
	    	
	    	<div title="风机曲线" style="overflow:auto;">
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_curve/realTime_curve.jsp" href="#">实时曲线</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="" href="#">历史曲线</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="" href="#">一年内日统计曲线</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_curve/windFrequency_curve.jsp" href="#">风频图</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_curve/power_curve.jsp" href="#">风速功率图</a>
	    	</div>
	    	
	    	<div title="故障报告" style="overflow:auto;">
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_error_worning/report_errorForOne.jsp" href="#">单台故障统计</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_error_worning/report_worningForOne.jsp" href="#">单台警告统计</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_error_worning/report_error.jsp" href="#">风场故障统计</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_error_worning/report_worning.jsp" href="#">风场警告统计</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_error_worning/report_errorForOneQuery.jsp" href="#">故障查询</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_error_worning/report_worningForOneQuery.jsp" href="#">警告查询</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_error_worning/report_currentError.jsp" href="#">当前故障</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_error_worning/report_currentWorning.jsp" href="#">当前警告</a>
	    	</div>
	    	
	    	<div title="风场报表" style="overflow:auto;">
		    	<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_report/report_hourForOneFun.jsp" href="#">单台小时报表</a>
		    	<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_report/report_funState.jsp" href="#">机组状态变化记录</a>
		    	<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="<%= basePath %>/easyui_report/report_hour.jsp" href="#">风场小时报表</a>
		    	<a class="easyui-menubutton" plain="true" style="display:block;width:100%;" title="" data-options="menu:'#dayReportMenu'" href="javascript:void(0)">风场日报表</a>   
					<div class="accordionMenu" id="dayReportMenu" style="width:150px;">   
					    <div><a title="<%= basePath %>/easyui_report/report_day_main.jsp" href="#">主要信息（日报）</a></div>   
					    <div><a title="<%= basePath %>/easyui_report/report_day_time.jsp" href="#">时间统计（日报）</a></div>   
					</div>
		    	<a class="easyui-menubutton" plain="true" style="display:block;width:100%;" title="" data-options="menu:'#monthReportMenu'" href="javascript:void(0)">风场月报表</a>   
					<div class="accordionMenu" id="monthReportMenu" style="width:150px;">   
					   	<div><a title="<%= basePath %>/easyui_report/report_month_main.jsp" href="#">主要信息（月报）</a></div>   
					    <div><a title="<%= basePath %>/easyui_report/report_month_time.jsp" href="#">时间统计（月报）</a></div> 
					</div> 
		    	<a class="easyui-menubutton" plain="true" style="display:block;width:100%;" title="" data-options="menu:'#yearReportMenu'" href="javascript:void(0)">风场年报表</a>   
					<div class="accordionMenu" id="yearReportMenu" style="width:150px;">   
					   	<div><a title="<%= basePath %>/easyui_report/report_year_main.jsp" href="#">主要信息（年报）</a></div>   
					    <div><a title="<%= basePath %>/easyui_report/report_year_time.jsp" href="#">时间统计（年报）</a></div> 
					</div>  

	    	</div>
	    	
	    	<div title="风场控制" style="overflow:auto;">
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="" href="#">功率限制</a>
	    	</div>
	    	
	    	<div title="系统管理" style="overflow:auto;">
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="" href="#">用户管理</a>
	    		<a class="easyui-linkbutton" plain="true" style="display:block;width:100%;" title="" href="#">退出系统</a>
	    	</div>
	    </div>
    </div>   
    <div data-options="region:'center',title:''" style="overflow:hidden;background:#eee;" border="false" >
	    <div id="tabs" class="easyui-tabs" fit="true" border="false">
		</div>
    </div>   
</body>  



</html>