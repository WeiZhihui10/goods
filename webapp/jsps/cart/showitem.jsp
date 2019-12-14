<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>showitem.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/showitem.css'/>">
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="http://www.jq22.com/jquery/1.11.1/jquery.min.js"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/input.js'></c:url>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/distpicker.data.js'></c:url>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/distpicker.js'></c:url>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/main.js'></c:url>"></script>
	<style type="text/css">
	#addr{width: 400px; height: 32px;border: 1px solid #7f9db9; padding-left: 10px; line-height: 32px;}
	select{height: 32px;border: 1px solid #7f9db9;line-height: 32px;}
	</style>

	<script type="text/javascript">
		//计算合计
		$(function() {
			var total = 0;
			$(".subtotal").each(function() {
				total += Number($(this).text());
			});
			$("#total").text(round(total, 2));
			
			
		});
		//获得地址
		function loadAddress() {
			var province=$("select[name='province']").val();
			var city=$("select[name='city']").val();
			var county=$("select[name='county']").val();
			var addr=$("#addr").val();
			var address=province+city+county+addr;
			if(address==""||address==null){
				alert("地址不能为空");
			}else if(addr==""||addr==null){
				alert("请填写详细地址");
			}else if(county==""||county==null){
				alert("请选择区");
			}else if(city==""){
				alert("请选择市");
			}else if(province==""){
				alert("请填写地址");
			}
			return address;
		}
		//添加地址
		function addAddress(){
			var address=loadAddress();
			$.ajax({
				cache: false,
				async: true,
				type: "POST",
				dataType: "text",
				data: {oper: "addAddress", address: address},
				url: "/goods/UserServlet",
				success: function(result) {
					alert("添加成功");
					window.location.reload();
					
				}
			});
		}
		//修改sessionUser中addressList的值为null
		function turnNull(){
			$.ajax({
				cache: false,
				async: true,
				type: "POST",
				dataType: "text",
				data: {oper: "turnNull"},
				url: "/goods/UserServlet",
				success: function(result) {
					window.location.reload();
				}
			});
		}
		
		//提交订单
		function submit(){
			var addr=$("#address").val();
			if(addr==""||addr==null){
				alert("请选择收货地址");
				return;
			}
			$("#form1").submit();
		}
	</script>
  </head>
  
  <body>
<form id="form1" action="<c:url value='/OrderServlet'/>" method="post">
	<input type="hidden" name="cartItemIds" value="${cartItemIds }"/>
	<input type="hidden" name="oper" value="addOrderAndOrderItem"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr bgcolor="#efeae5">
		<td width="400px" colspan="5"><span style="font-weight: 900;">生成订单</span></td>
	</tr>
	<tr align="center">
		<td width="10%">&nbsp;</td>
		<td width="50%">图书名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
	</tr>


	<c:forEach items="${cartItemsList}" var="cartItem">
		<tr align="center">
			<td align="right">
				<a class="linkImage" href="<c:url value='/BookServlet?oper=findBookByBid&bid=${cartItem.book.bid }'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.image_b }'/>"/></a>
			</td>
			<td align="left">
				<a href="<c:url value='/BookServlet?oper=findBookByBid&bid=${cartItem.book.bid }'/>"><span>${cartItem.book.bname }</span></a>
			</td>
			<td>&yen;${cartItem.book.currPrice }</td>
			<td>${cartItem.quantity }</td>
			<td>
				<span class="price_n">&yen;<span class="subtotal">${cartItem.subtotal }</span></span>
			</td>
		</tr>
	</c:forEach>



	<tr>
		<td colspan="6" align="right">
			<span>总计：</span><span class="price_t">&yen;<span id="total">${total }</span></span>
		</td>
	</tr>
	<tr>
		<td colspan="5" bgcolor="#efeae5"><span style="font-weight: 900">收货地址</span></td>
	</tr>
	<tr id="loadAddr">
	<c:choose>
		<c:when test="${empty sessionUser.addresseList}">
			<td colspan="6" id="distpicker5">
				<input type="hidden" name="address" id="address"/>
				<select  name="province" id="province10"></select>
				<select name="city" id="city10"></select>
				<select name="county" id="district10"></select>
				<input id="addr" type="text" placeholder="请填写详细地址及联系人姓名、电话" />
				<a href="javascript:addAddress();">添加地址</a>
			</td>
		</c:when>
		<c:otherwise>
			<td colspan="6">
				<select name="address" id="address"> 
					<c:forEach items="${sessionUser.addresseList}" var="address">
						<option value="${address.address }">${address.address }</option>
					</c:forEach>
				</select>
				<a href="javascript:turnNull();">添加新地址</a>
			</td>
		</c:otherwise>
	</c:choose>
	</tr>
	<tr>
		<td style="border-top-width: 4px;" colspan="5" align="right">
			<a id="linkSubmit" href="javascript:submit();">提交订单</a>
		</td>
	</tr>
</table>
</form>
  </body>
</html>
