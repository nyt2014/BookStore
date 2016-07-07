<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>网上书店</h1>
<br />
<br />
<div>
	<div>
		<a href="${pageContext.request.contextPath }/index.jsp">首页</a> <a
			href="${pageContext.request.contextPath }/client/listcart.jsp">查看购物车</a>
		<a href="#">查看自己的订单</a>
	</div>
	<div style="float: right;">
		<c:if test="${user==null }">
			<form action="${pageContext.request.contextPath }/client/LoginServlet" method="post">
				用户名：<input type="text" name="username" style="width: 50px">
				密码：<input type="password" name="password" style="width: 50px">
				<input type="submit" value="登陆"> 
				<input type="button"
					value="注册"
					onclick="javascript:window.location.href='/bookstore/client/register.jsp'">
			</form>
		</c:if>
		<c:if test="${user!=null }">
			 欢迎您：${user.username }
		</c:if>
	</div>
	<div style="clear: both"></div>
</div>
<hr>