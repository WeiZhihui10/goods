<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>book_desc.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/book/desc.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datepick.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/input.js'></c:url>"></script>
	<script type="text/javascript" src="<c:url value='/adminjsps/admin/js/book/desc.js'/>"></script>
	
	<script type="text/javascript">
		
		$(function() {
			$("#box").attr("checked", false);
			$("#formDiv").css("display", "none");
			$("#show").css("display", "");	
			
			// 操作和显示切换
			$("#box").click(function() {
				if($(this).attr("checked")) {
					$("#show").css("display", "none");
					$("#formDiv").css("display", "");
				} else {
					$("#formDiv").css("display", "none");
					$("#show").css("display", "");		
				}
			});
		});
		
		function loadChildren(){
			//获取pid
			var pid=$("#pid").val();
			$.ajax({
				async:true,
				cache:false,
				url:"/goods/admin/AdminBookServlet",
				data:{oper:"ajaxFindChildren", pid:pid},
				type:"POST",
				dataType:"json",
				success:function(result){
					$("#cid").empty();
					$("#cid").append($("<option>==请选择2级分类==</option>"));
					for(var i=0;i<result.length;i++){
						var option=$("<option>").val(result[i].cid).text(result[i].cname);
						$("#cid").append(option);
					}
				}
			});
		}
		
		/**
		 * 点击编辑按钮时
		 */
		function editForm() {
			$("#oper").val("updateBookByBid");
			$("#form").submit();
		}
		
		function deleteForm() {
			$("#oper").val("deleteBook");
			$("#form").submit();
		}
	</script>
  </head>
  
  <body>
    <input type="checkbox" id="box"><label for="box">编辑或删除</label>
    <br/>
    <br/>
  <div id="show">
    <div class="sm">${bookByBid.bname}</div>
    <img align="top" src="<c:url value='/${bookByBid.image_w }'/>" class="tp"/>
    <div id="book" style="float:left;">
	    <ul>
	    	<li>商品编号：${bookByBid.bid }</li>
	    	<li>当前价：<span class="price_n">&yen;${bookByBid.currPrice }</span></li>
	    	<li>定价：<span style="text-decoration:line-through;">&yen;${bookByBid.price }
	    	</span>　折扣：<span style="color: #c30;">${bookByBid.discount }</span>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<tr>
				<td colspan="3">
					作者：${bookByBid.author }
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：${bookByBid.press }
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：${bookByBid.publishtime }</td>
			</tr>
			<tr>
				<td>版次：${bookByBid.edition }</td>
				<td>页数：${bookByBid.pageNum }</td>
				<td>字数：${bookByBid.wordNum }</td>
			</tr>
			<tr>
				<td width="180">${bookByBid.printtime }</td>
				<td>开本：${bookByBid.booksize } 开</td>
				<td>纸张：${bookByBid.paper }</td>
			</tr>
		</table>
	</div>
  </div>
  
  
  <div id='formDiv'>
   <div class="sm">&nbsp;</div>
   <form action="<c:url value='/admin/AdminBookServlet'></c:url>" method="post" id="form">
    <input type="hidden" name="oper" id="oper"/>
   	<input type="hidden" name="bid" value="${bookByBid.bid }"/>
    <img align="top" src="<c:url value='/${bookByBid.image_w }'/>" class="tp"/>
    <div style="float:left;">
	    <ul>
	    	<li>商品编号：${bookByBid.bid }</li>
	    	<li>书名：　<input id="bname" type="text" name="bname" value="${bookByBid.bname }" style="width:500px;"/></li>
	    	<li>当前价：<input id="currPrice" type="text" name="currPrice" value="${bookByBid.currPrice }" style="width:50px;"/></li>
	    	<li>定价：　<input id="price" type="text" name="price" value="${bookByBid.price }" style="width:50px;"/>
	    	折扣：<input id="discount" type="text" name="discount" value="${bookByBid.discount }" style="width:30px;"/>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<tr>
				<td colspan="3">
					作者：　　<input id="author" type="text" name="author" value="${bookByBid.author }" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：　<input id="press" type="text" name="press" value="${bookByBid.press }" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：<input id="publishtime" type="text" name="publishtime" value="${bookByBid.publishtime }" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>版次：　　<input id="edition" type="text" name="edition" value="${bookByBid.edition }" style="width:40px;"/></td>
				<td>页数：　　<input id="pageNum" type="text" name="pageNum" value="${bookByBid.pageNum }" style="width:50px;"/></td>
				<td>字数：　　<input id="wordNum" type="text" name="wordNum" value="${bookByBid.wordNum }" style="width:80px;"/></td>
			</tr>
			<tr>
				<td width="250px">印刷时间：<input id="printtime" type="text" name="printtime" value="${bookByBid.printtime }" style="width:100px;"/></td>
				<td width="250px">开本：　　<input id="booksize" type="text" name="booksize" value="${bookByBid.booksize }" style="width:30px;"/></td>
				<td>纸张：　　<input id="paper" type="text" name="paper" value="${bookByBid.paper }" style="width:80px;"/></td>
			</tr>
			<tr>
				<td>
					一级分类：<select name="pid" id="pid" onchange="loadChildren()">
						<option value="">==请选择1级分类==</option>
						<c:forEach items="${parents }" var="parent">
			    			<option value="${parent.cid }"<c:if test="${pid eq parent.cid  }">selected='selected'</c:if> >${parent.cname }</option>
			    		</c:forEach>
					</select>
				</td>
				<td>
					二级分类：<select name="cid" id="cid">
						<option value="">==请选择2级分类==</option>
			    		<c:forEach items="${childrens }" var="children">
			    			<option value="${children.cid }"<c:if test="${children.cid eq bookByBid.category.cid }">selected='selected'</c:if> >${children.cname }</option>
			    		</c:forEach>
					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2">
					<input onclick="editForm()" type="button" name="method" id="editBtn" class="btn" value="编　　辑">
					<input onclick="deleteForm()" type="button" name="method" id="delBtn" class="btn" value="删　　除">
				</td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>
