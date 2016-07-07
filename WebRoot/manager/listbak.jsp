<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>数据库备份信息列表页面</title>
  </head>
  
  <body style="text-align: center;">
	<br/>
	<table width="80%" frame="border">
		<tr>
			<td>备份文件名</td>
			<td>备份时间</td>
			<td>备份原因</td>
			<td>操作</td>
		</tr>
		
		<c:forEach var="bak" items="${baks}">
			<tr>
				<td>${bak.filename }</td>
				<td>${bak.baktime }</td>
				<td>${bak.description }</td>
				<td>
					<a href="${pageContext.request.contextPath }/manager/DbServlet?method=restore&id=${bak.id}">恢复</a>
					<a href="${pageContext.request.contextPath }/manager/DbServlet?method=remove&id=${bak.id}">删除</a>
				</td>
			</tr>
		</c:forEach>
	
	</table>
	  
  </body>
</html>
