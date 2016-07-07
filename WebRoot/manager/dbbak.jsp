<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'dbbak.jsp' starting page</title>
  </head>
  
  <body>
  	<form action="${pageContext.request.contextPath }/manager/DbServlet?method=bakup" method="post">
    请描述备份的原因：<br/>
    <textarea rows="5" cols="80" name="description"></textarea>
    <input type="submit" value="备份">
    </form>
  </body>
</html>
