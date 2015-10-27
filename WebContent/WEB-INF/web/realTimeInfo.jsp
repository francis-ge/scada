<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Real Time Info</title>

<!-- Bootstrap -->
<link href="<%= request.getContextPath() %>/css/bootstrap.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
	    body{
	    	padding-top:50px;
	    }
    </style>
</head>
<body>

<nav class="navbar navbar-fixed-top navbar-inverse navbar-default">   
<div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#inverseNavbar1">
      	<span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Sharpower</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
<div class="collapse navbar-collapse" id="inverseNavbar1">
<ul class="nav navbar-nav">
     <li ><a href="#">风场概览<span class="sr-only">(current)</span></a></li>
     <li><a href="#">瞬时信息</a></li>
     <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">风机曲线<span class="caret"></span></a>
       <ul class="dropdown-menu" role="menu">
         <li><a href="#">实时曲线</a></li>
         <li><a href="#">历史曲线</a></li>
         <li><a href="#">一年内统计曲线</a></li>
         <li class="divider"></li>
         <li><a href="#">风频图</a></li>
         <li><a href="#">风速功率图</a></li>
       </ul>
     	<li><a href="#">故障警告</a></li>
       	<li><a href="#">风场报表</a></li>
       	<li><a href="#">风场控制</a></li>
       	<li><a href="#">系统管理</a></li>
       	<li><a href="#"></a></li>
     </li>
</ul>
</div>
  </div>
  </nav>

 	<s:actionerror  theme="bootstrap"/>
	<s:actionmessage theme="bootstrap"/>
	<s:fielderror theme="bootstrap"/>
<s:debug></s:debug>	

  	<s:form id="form1" class="form-inline" method="post" action="realTimeInfo" label="选择风机"
  			enctype="multipart/form-data" theme="bootstrap" cssClass="form-horizontal">
 		<s:select label="风机编号：" name="fun.id" list="#request.funs" listKey="id" listValue="name" elementCssClass="col-sm-6"></s:select>
  		<s:textfield name="windFarm.id" type="hidden" value="1"></s:textfield>
  		<s:textfield name="variables" type="hidden" value="{,,}"></s:textfield>
  	</s:form>
  	
  	<div id="timer"></div>

   <div class="table-responsive">
		<table class="table table-striped table-hover table-bordered"  >
		<caption>风机实时信息</caption>
		  <tbody>
		
		    <tr>
		      <th scope="col">变量</th>
		      <th scope="col">数值</th>
		      <th scope="col">变量</th>
		      <th scope="col">数值</th>
		      <th scope="col">变量</th>
		      <th scope="col">数值</th>
		      <th scope="col">变量</th>
		      <th scope="col">数值</th>
		    </tr>
		     <s:iterator value="#request.funs" var="fun1">
                <tr>
                 
                </tr>
		    </s:iterator>
		    
		  </tbody>
		</table>
	  </div>
    
 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="<%= request.getContextPath() %>/js/jquery-1.11.3.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script src="<%= request.getContextPath() %>/js/bootstrap.js"></script>

<script src="<%= request.getContextPath() %>/js/timer.jquery.min.js"></script>

<script type="text/javascript">
	
	$(function(){
		$('#timer').timer(
			{
			    duration: '3s',
			    callback: function() {
			        $.post("AjaxRealTimeInfo", $("#form1").serializeArray(), function(data){console.log(data);}, "JSON");
			    }
			}	
		);
	})
</script>

</body>
</html>