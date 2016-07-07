<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
  </head>
  
  <body style="text-align: center">
   	<br/><br/>
   	<table frame="border" cellpadding="0" cellspacing="0" width="90%">
   		<caption><h2>订单信息</h2></caption>
   		<tr>
   			<td>订单人</td>
   			<td>下单时间</td>
   			<td>订单状态</td>
 			<td>订单总价</td>
 			<td>操作</td>
   		</tr>

			<c:forEach var="order" items="${orders}">
				<tr>
					<td>
						${order.user.username }
					</td>
					<td>
						${order.ordertime }
					</td>
					<td>
						${order.state==false?'未发货':'已发货' }
					</td>
					<td>
						${order.price }
					</td>
					<td>
						<a
							href="${pageContext.request.contextPath }/manager/OrderServlet?method=find&id=${order.id }">查看明细</a>
						<a href="#">修改</a>
					</td>
				</tr>
			</c:forEach>
		</table>
  </body>
</html>
