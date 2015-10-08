<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Input Fun</title>

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<s:debug></s:debug>
<nav class="navbar navbar-inverse">   
<div class="container-fluid">
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
  <s:if test="id!=0&name!=null">Input Success!</s:if>
  <s:elseif test="id==0&name!=null">Input Fail!</s:elseif>
  <s:else></s:else>

<div class="container">
<form class="form-horizontal" method="post" action="saveFun">
  <div class="form-group">
    <label for="inputName" class="col-sm-2 control-label">风机名称</label>
    <div class="col-sm-5">
      <input type="text" class="form-control" id="inputName" placeholder="Name" name="name">
    </div>
  </div>
<div class="form-group">
    <label for="inputType" class="col-sm-2 control-label">主控类型</label>
    <div class="col-sm-5">
     <select class="form-control" id="inputType" name="type">
      <s:iterator value="#request.plcTypes" var="type">
      	<option>${name}</option>
      </s:iterator>
    </select>
    </div>
  </div>
  
  <div class="form-group">
    <label for="inputAddress" class="col-sm-2 control-label">风机地址</label>
    <div class="col-sm-5">
      <input type="text" class="form-control" id="inputAddress" placeholder="Addres" name="address">
    </div>
  </div>
  
    <div class="form-group">
    <label for="inputGroup" class="col-sm-2 control-label">分组</label>
    <div class="col-sm-5">
      <input type="text" class="form-control" id="inputGroup" placeholder="Group" name="line">
    </div>
  </div>
  
    <input name="windFarm.id" type="hidden" value="1">
    
    <div class="col-sm-offset-2 col-sm-5" >
      <button type="submit" class="btn btn-default pull-right col-sm-3" >提交</button>
    </div>
   
    </form>
    
    </div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="js/jquery-1.11.3.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script src="js/bootstrap.js"></script>
</body>
</html>