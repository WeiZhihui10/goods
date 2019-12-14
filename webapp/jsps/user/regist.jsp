<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册页面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/regist.css'></c:url>" />
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'></c:url>"></script>
<script type="text/javascript" src="<c:url value='/jsps/js/user/regist.js'></c:url>"></script>
<script type="text/javascript" src="<c:url value='/js/input.js'></c:url>"></script>

</head>
<body>
	<div id="divMain">
		<div id="divTitle">
			<span id="spanTitle">新用户注册</span>
		</div>
		<div id="divBody">
		<form action="<c:url value='/UserServlet'></c:url>" method="post" id="registForm">
			<input type="hidden" name="oper" value="regist" />
			<table id="tableForm">
			  <tr>
			    <td class="tdText">用户名：</td>
			    <td class="tdInput"><input class="inputClass" type="text" name="loginname" id="loginname" autocomplete="off" value="${form.getName()}"></td>
			    <td class="tdError"><label class="errorClass" id="loginnameError">${errors.loginname}</label></td>
			  </tr>
			  <tr>
			    <td class="tdText">登录密码：</td>
			    <td class="tdInput"><input class="inputClass" type="password" name="loginpwd"  id="loginpwd" value="${form.getPwd()}"/></td>
			    <td class="tdError"><label class="errorClass" id="loginpwdError">${errors.pwd}</label></td>
			  </tr>
			  <tr>
			    <td class="tdText">确认密码：</td>
			    <td class="tdInput"><input class="inputClass" type="password" name="loginpwd2" id="loginpwd2" value="${form.getLoginPwd2()}"/></td>
			    <td class="tdError"><label class="errorClass" id="loginpwd2Error">${errors.pwd2}</label></td>
			  </tr>
			  <tr>
			    <td class="tdText">Email：</td>
			    <td class="tdInput"><input class="inputClass" type="text" name="email" id="email" autocomplete="off" value="${form.getEmail()}" /></td>
			    <td class="tdError"><label class="errorClass" id="emailError">${errors.email}</label></td>
			  </tr>
			  <tr>
			    <td class="tdText">验证码：</td>
			    <td class="tdInput"><input class="inputClass" type="text" name="verifyCode" id="verifyCode" autocomplete="off" /></td>
			    <td class="tdError"><label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label></td>
			  </tr>
			  <tr>
			    <td class="tdText"></td>
			    <td class="tdInput"><div  id="divVerifyCode"><img id="imgVerifyCode" alt="验证码" src="<c:url value='/VerifyCodeServlet'></c:url>"></div></td>
			    <td class="tdError"><label><a href="javascript:_hyz()">换一张</a></label></td>
			  </tr>
			  <tr>
			    <td class="tdText"></td>
			    <td class="tdInput"><input type="image" alt="注册" src="<c:url value='/images/regist1.jpg'></c:url>" id="submitBtn" /></td>
			    <td class="tdError"><a href="<c:url value='/jsps/user/login.jsp'></c:url>">已有账号？立马登录！</a></td>
			  </tr>
			</table>
		</form>
		</div>
	</div>
</body>
</html>