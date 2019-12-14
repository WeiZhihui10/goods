<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/login.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/user/login.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/input.js'></c:url>"></script>
	<script src="<c:url value='/js/common.js'/>"></script>
	<script type="text/javascript">
		$(function() {/*Map<String(Cookie名称),Cookie(Cookie本身)>*/
			// 获取cookie中的用户名
			var loginname = window.decodeURI("${cookie.username.value}");
			if("${requestScope.name}") {
				loginname = "${requestScope.name}";
			}
			$("#loginname").val(loginname);
		});
	</script>
  </head>
  
  <body>
	<div class="main">
	  <div><a href="<c:url value='/index.jsp'></c:url>"><img src="<c:url value='/images/logoko.png'/>" /></a></div>
	  <div>
	    <div class="imageDiv"><img class="img" src="<c:url value='/images/zj.png'/>"/></div>
        <div class="login1">
	      <div class="login2">
            <div class="loginTopDiv">
              <span class="loginTop">登录</span>
              <span>
                <a href="<c:url value='/jsps/user/regist.jsp'/>" class="registBtn"></a>
              </span>
            </div>
            <div>
              <form target="_top" action="<c:url value='/UserServlet'/>" method="post" id="loginForm">
                <input type="hidden" name="oper" value="login" />
                  <table>
                    <tr>
                      <td width="50"></td>
                      <td><label class="error" id="msg">${msg}</label></td>
                      <c:remove var="msg"/>
                    </tr>
                    <tr>
                      <td width="50">用户名</td>
                      <td><input class="input" type="text" name="loginname"  id="loginname"  value="${name}"/></td>
                    </tr>
                    <tr>
                      <td height="20">&nbsp;</td>
                      <td><label id="loginnameError" class="error">${errors.loginname}</label></td>
                    </tr>
                    <tr>
                      <td>密　码</td>
                      <td><input class="input" type="password" name="loginpass" id="loginpass" value="${pwd}"/></td>
                    </tr>
                    <tr>
                      <td height="20">&nbsp;</td>
                      <td><label id="loginpassError" class="error">${errors.loginpwd}</label></td>
                    </tr>
                    <tr>
                      <td>验证码</td>
                      <td>
                        <input class="input yzm" type="text" name="verifyCode" id="verifyCode"  value=""/>
                        <img id="vCode" src="<c:url value='/VerifyCodeServlet'></c:url>"/>
                        <a id="hyz" href="javascript:_hyz()">换一张</a>
                      </td>
                    </tr>
                    <tr>
                      <td height="20px">&nbsp;</td>
                      <td><label id="verifyCodeError" class="error">${errors.verifyCode}</label></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td align="left">
                        <input type="image" id="submit" src="<c:url value='/images/login1.jpg'/>" class="loginBtn"/>
                      </td>
                    </tr>																				
                 </table>
              </form>
            </div>
          </div>
        </div>
      </div>
	</div>
  </body>
</html>
	