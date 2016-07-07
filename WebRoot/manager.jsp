<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'manager.jsp' starting page</title>
</head>

<frameset rows="18%,*">
	<frame src="${pageContext.request.contextPath}/manager/head.jsp"
		name="head">
	<frameset cols="15%,*">
		<frame src="${pageContext.request.contextPath}/manager/left.jsp"
			name="left">
		<frame src="#" name="right">
	</frameset>
</frameset>

</html>
