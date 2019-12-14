<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function _go() {
		var pc = $("#pageCode").val();//获取文本框中的当前页码
		if(!/^[1-9]\d*$/.test(pc)) {//对当前页码进行整数校验
			alert('请输入正确的页码！');
			return;
		}
		if(pc > ${pageBean.tp}) {//判断当前页码是否大于最大页
			alert('请输入正确的页码！');
			return;
		}
		location = "${pageBean.url}&pc="+pc;
	}
</script>


<div class="divBody">
  <div class="divContent">
    <%--上一页 --%>
   	<a class="aBtn bold" href="${pageBean.url}&pc=1">首页</a>
	<c:choose>
		<c:when test="${pageBean.pc eq 1}"><span class="spanBtnDisabled">上一页</span></c:when>
		<c:otherwise><a href="${pageBean.url}&pc=${pageBean.pc-1}" class="aBtn bold">上一页</a></c:otherwise>
	</c:choose>
       
       

   
   <%-- 计算begin和end --%>
     <%-- 如果总页数<=6，那么显示所有页码，即begin=1 end=${pb.tp} --%>
       <%-- 设置begin=当前页码-2，end=当前页码+3 --%>
         <%-- 如果begin<1，那么让begin=1 end=6 --%>
         <%-- 如果end>最大页，那么begin=最大页-5 end=最大页 --%>

   
   <%-- 显示页码列表 --%>
    <c:choose>
    	<c:when test="${pageBean.tp <=6}">
    		<c:set var="begin" value="1"></c:set>
    		<c:set var="end" value="${pageBean.tp }"></c:set>
    	</c:when>
    	<c:otherwise>
    		<c:set var="begin" value="${pageBean.pc-2 }"></c:set>
    		<c:set var="end" value="${pageBean.pc+3 }"></c:set>
    		<c:if test="${begin<1 }">
    			<c:set var="begin" value="1"></c:set>
    			<c:set var="end" value="6"></c:set>
    		</c:if>
    		<c:if test="${end>pageBean.tp }">
    			<c:set var="begin" value="${pageBean.tp-5 }"></c:set>
    			<c:set var="end" value="${pageBean.tp }"></c:set>
    		</c:if>
    	</c:otherwise>
    </c:choose>
    
    <c:forEach begin="${begin}" end="${end}" var="i">
    	<c:choose>
    		<c:when test="${i eq pageBean.pc }">
    			<span class="spanBtnSelect">${i}</span>
    		</c:when>
    		<c:otherwise>
    			<a href="${pageBean.url }&pc=${i}" class="aBtn">${i}</a>
    		</c:otherwise>
    	</c:choose>
    </c:forEach>
    
    <%-- 显示点点点 --%>
    <c:if test="${end<pageBean.tp}">
    	 <span class="spanApostrophe">...</span> 
    </c:if>
    

   
     <%--下一页 --%>
     <c:choose>
     	<c:when test="${pageBean.pc eq pageBean.tp }"><span class="spanBtnDisabled">下一页</span></c:when>
     	<c:otherwise> <a href="${pageBean.url }&pc=${pageBean.pc+1}" class="aBtn bold">下一页</a> </c:otherwise>
     </c:choose>
      <a class="aBtn bold" href="${pageBean.url}&pc=${pageBean.tp}">尾页</a>  
       
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    
    <%-- 共N页 到M页 --%>
    <span>共${pageBean.tp}页</span>
    <span>到</span>
    <input type="text" class="inputPageCode" id="pageCode" value="${pageBean.pc}"/>
    <span>页</span>
    <a href="javascript:_go();" class="aSubmit">确定</a>
  </div>
</div>