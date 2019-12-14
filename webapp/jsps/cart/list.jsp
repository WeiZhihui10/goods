<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>">
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/input.js'></c:url>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">
	<script type="text/javascript">
		$(function(){
			showTotal();//计算总计
			
			
			//给全选复选框添加click事件
			$("#selectAll").click(function(){
				//获取全选框状态
				var bool=$("#selectAll").attr("checked");
				//让所有条目的复选框与全选框同步
				setItemCheckBox(bool);
				//让结算按钮的状态与全选框同步
				setJieSuan(bool);
				//重新计算总计
				showTotal();
			});
			
			
			//通过输入框修改数量
			$(".quantity").blur(function(){
				//获取cartItem
				var id = $(this).attr("id").substring(0,32);
				//获取输入框中的数量
				var quantity=$("#"+id+"Quantity").val();
				//判断当前数量是否为0，如果为0，那就不修改数量，而是删除条目
				if(quantity==""||quantity==null){
					alert("请输入正确的数量");
					$("#"+id+"Quantity").val("1");
					return false;
				}
				if(quantity==0){
					if(confirm("您是否真要删除该条目")){
						location="/goods/CartItemServlet?oper=batchDeleteCartItem&cartItemIds="+id;
					}
				}else{
					sendUpdateQuantity(id,Number(quantity));
				}
				showTotal();
			});
			
			
			
			//给每个条目的复选框添加单击事件
			$(":checkbox[name=checkboxBtn]").click(function () {
				var all=$(":checkbox[name=checkboxBtn]").length;//所有条目的个数
				var select=$(":checkbox[name=checkboxBtn][checked=true]").length;
				if(all==select){
					$("#selectAll").attr("checked",true);//勾选全选复选框
					setJieSuan(true);//让结算按钮有效
					
				}else if(select==0){
					$("#selectAll").attr("checked",false);//取消全选
					setJieSuan(false);//让结算按钮有效
					
				}else{
					$("#selectAll").attr("checked",false);//取消全选
					setJieSuan(true);//让结算按钮有效
				}
				showTotal();//重新结算
			});
			
			
			
			//给减号添加click事件
			$(".jian").click(function(){
				//获取cartItem
				var id = $(this).attr("id").substring(0,32);
				//获取输入框中的数量
				var quantity=$("#"+id+"Quantity").val();
				//判断当前数量是否为1，如果为1，那就不修改数量，而是删除条目
				if(quantity==1){
					if(confirm("您是否真要删除该条目")){
						location="/goods/CartItemServlet?oper=batchDeleteCartItem&cartItemIds="+id;
					}
				}else{
					sendUpdateQuantity(id,Number(quantity)-1);
				}
				showTotal();
			});
			
			//给加号添加Click事件
			$(".jia").click(function(){
				//获取cartItem
				var id = $(this).attr("id").substring(0,32);
				//获取输入框中的数量
				var quantity=$("#"+id+"Quantity").val();
				sendUpdateQuantity(id,Number(quantity)+1);
			});
			
			//请求服务器，修改数量
			function sendUpdateQuantity(id,quantity){
				$.ajax({
					async:false,
					cache:false,
					url:"/goods/CartItemServlet",
					data:{oper:"updateCartItemQuantityByCartItemId",cartItemId:id,quantity:quantity},
					type:"POST",
					dataType:"json",
					success:function(result){
						//修改数量
						$("#"+id+"Quantity").val(result[0]);
						//修改小计
						$("#"+id+"Subtotal").text(result[1]);
						//计算总计
						showTotal();
					}
				});
			}
			
		});
		
		
		//计算总计
		function showTotal() {
			var total=0;
			//获取所有被勾选的复选框
			$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
				//获取复选框的值，即cartItemId
				var id=$(this).val();
				//再通过cartItemId找到小计，获取其文本
				var text=$("#"+id+"Subtotal").text();
				//累加计算
				total+=Number(text);
			});
			//显示总计
			$("#total").text(round(total, 2));
		}
		//统一设置所有条目的复选按钮的条目
		function setItemCheckBox(bool) {
			$(":checkbox[name=checkboxBtn]").attr("checked", bool);
		}
		
		//设置结算按钮样式
		function setJieSuan(bool) {
			if(bool){
				$("#jiesuan").removeClass("kill").addClass("jiesuan");
				$("#jiesuan").unbind("click");
			}else{
				$("#jiesuan").removeClass("jiesuan").addClass("kill");
				$("#jiesuan").click(function(){ return false;});
			}
		}
		
		//批量删除方法
		function batchDeleteCartItem() {
			var cartItemIdArray=new Array();
			//获取所有被选中的条目的复选框
			$(":checkbox[name=checkboxBtn][checked=true]").each(function () {
				cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
			});
			location="/goods/CartItemServlet?oper=batchDeleteCartItem&cartItemIds="+cartItemIdArray;
		}
		
		//jiesuan
		function jiesuan(){
			//获取所有选择的条目id，放到数组中
			var cartItemIdArray=new Array();
			//获取所有被选中的条目的复选框
			$(":checkbox[name=checkboxBtn][checked=true]").each(function () {
				cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
			});
			//把数组的值toString(),然后赋给表单的cartItemIds这个hidden
			$("#cartItemIds").val(cartItemIdArray.toString());
			//将总计传送到showitem.jsp
			$("#hiddenTotal").val($("#total").val());
			//提交这个表单
			$("#jieSuanForm").submit();
		}
		
		
	</script>
  </head>
  <body>
	<div style="color: red;text-align: center;font-size: 18px;">
		<span>${msg}</span>
	</div>
	<c:if test="${empty cartItemList }">
		<table width="95%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">
					<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
				</td>
				<td>
					<span class="spanEmpty">您的购物车中暂时没有商品</span>
				</td>
			</tr>
		</table>  
	</c:if>
<br/>
<br/>

<c:if test="${!empty cartItemList }">
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr align="center" bgcolor="#efeae5">
			<td align="left" width="50px">
				<input type="checkbox" id="selectAll" checked="checked" /><label for="selectAll">全选</label>
			</td>
			<td colspan="2">商品名称</td>
			<td>单价</td>
			<td>数量</td>
			<td>小计</td>
			<td>操作</td>
		</tr>

	
		<c:forEach items="${cartItemList }" var="cartItem">
			<tr align="center">
				<td align="left">
					<input value="${cartItem.cartItemId}" type="checkbox" name="checkboxBtn" checked="checked" />
				</td>
				<td align="left" width="70px">
					<a class="linkImage" href="<c:url value='/BookServlet?oper=findBookByBid&bid=${cartItem.book.bid }'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.image_b }'></c:url>"/></a>
				</td>
				<td align="left" width="400px">
				    <a href="<c:url value='/BookServlet?oper=findBookByBid&bid=${cartItem.book.bid }'/>"><span>${cartItem.book.bname }</span></a>
				</td>
				<td><span>&yen;<span class="currPrice" >${cartItem.book.currPrice }</span></span></td>
				<td>
					<a class="jian" id="${cartItem.cartItemId }Jian"></a>
					<input class="quantity" id="${cartItem.cartItemId }Quantity" type="text" value="${cartItem.quantity }"/>
					<a class="jia" id="${cartItem.cartItemId }Jia"></a>
				</td>
				<td width="100px">
					<span class="price_n">&yen;<span class="subTotal" id="${cartItem.cartItemId }Subtotal">${cartItem.subtotal}</span></span>
				</td>
				<td>
					<a href="<c:url value='/CartItemServlet?oper=batchDeleteCartItem&cartItemIds=${cartItem.cartItemId}'/>">删除</a>
				</td>
			</tr>
		</c:forEach>
	
	
	
	<tr>
		<td colspan="4" class="tdBatchDelete">
			<a href="javascript:batchDeleteCartItem();">批量删除</a>
		</td>
		<td colspan="3" align="right" class="tdTotal">
			<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
		</td>
	</tr>
	<tr>
		<td colspan="7" align="right">
			<a href="javascript:jiesuan()" id="jiesuan" class="jiesuan"></a>
		</td>
	</tr>
</table>
	<form id="jieSuanForm" action="<c:url value='/CartItemServlet'/>" method="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="oper" value="loadCartItems"/>
		<input type="hidden" name="total" id="hiddenTotal"/>
	</form>
</c:if>

  </body>
</html>
