<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    <base target="body">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>">
<style type="text/css">
	body {font-size: 10pt;}
</style>
  </head>
  
  <body>
<h1 style="text-align: center; line-height: 30px;"><a target="_top" href="<c:url value='/adminjsps/admin/index.jsp'></c:url>">网上书城系统后台管理</a></h1>
<div style="line-height: 15px; padding-left:50px;">
	<span>管理员：${admin.adminName}</span>
	<a target="_top" href="<c:url value='/AdminServlet?oper=adminQuit'/>">退出</a>
	<span style="padding-left:50px;">
		<a href="<c:url value='/admin/AdminCategoryServlet?oper=findAllCategory'/>">分类管理</a>
		<a href="<c:url value='/adminjsps/admin/book/main.jsp'/>">图书管理</a>
		<a href="<c:url value='/admin/AdminOrderServlet?oper=findAllOrder'/>">订单管理</a>
	</span>
</div>
  </body>
</html>
