<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>后台书籍列表</title>
  </head>
	<body style="text-align: center">
   	<br/><br/>
   	
   	<table frame="border" cellpadding="0" cellspacing="0" width="90%">
   		<caption><h2>图书信息</h2></caption>
   		<tr>
   			<td>书名</td>
   			<td>作者</td>
   			<td>描述</td>
   			<td>图片</td>
 			<td>操作</td>
   		</tr>
   		
   		<c:forEach var="book" items="${books}">
   			<tr>
   				<td>${book.name }</td>
   				<td>${book.author }</td>
   				<td>${book.description }</td>
   				<td><a href="${pageContext.request.contextPath }/images/${book.image }">查看图片</a></td>
	   			<td>
	   				<a href="#">修改</a>
	   				<a href="#">删除</a>
	   			</td>
   			</tr>
   		</c:forEach>
   	</table>
   	<br/><br/>
 
    
  </body>
</html>

