<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>pwd.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/pwd.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/user/pwd.js'></c:url>"></script>
	<script type="text/javascript" src="<c:url value='/js/input.js'></c:url>"></script>
	<script src="<c:url value='/js/common.js'/>"></script>
  </head>
  
  <body>
  <c:if test="${empty sessionUser }">
  	您还没有登录，请先<a href="<c:url value='/jsps/user/login.jsp'></c:url>" target="_top">登录</a>
  </c:if>
  <c:if test="${!(empty sessionUser) }">
    <div class="div0">
    	<span>修改密码</span>
    </div>

	<div class="div1">
		<form action="<c:url value='/UserServlet'/>" method="post" target="_top" id="updatePwdForm">
			<input type="hidden" name="oper" value="updateUserPwd"/>
		<table>
			<tr>
				<td><label class="error">${msg}</label></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td align="right">原密码:</td>
				<td><input class="input" type="password" name="oldPwd" id="loginpass" value=""/></td>
				<td><label id="loginpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right">新密码:</td>
				<td><input class="input" type="password" name="newPwd" id="newpass" value=""/></td>
				<td><label id="newpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right">确认密码:</td>
				<td><input class="input" type="password" name="newPwd2" id="reloginpass" value=""/></td>
				<td><label id="reloginpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
				  <img id="vCode" src="<c:url value='/VerifyCodeServlet'></c:url>" border="1"/>
		    	  <a href="javascript:_change();">看不清，换一张</a>
				</td>
			</tr>
			<tr>
				<td align="right">验证码:</td>
				<td>
				  <input class="input" type="text" name="verifyCode" id="verifyCode" value=""/>
				</td>
				<td><label id="verifyCodeError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td><input id="submit" type="image" src="<c:url value="/images/btn9.jpg"></c:url>" value="修改密码"/></td>
			</tr>
		</table>
		</form>
	</div>
	</c:if>
  </body>
</html>
