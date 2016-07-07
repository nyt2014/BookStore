<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>前台首页</title>

<style type="text/css">
body {
	margin: 0px;
	padding: 0px;
	text-align: center;
}

#container {
	width: 980px;
	height: 300px;
	text-align: left;
}

#main {
	margin-top: 20px;
}

#head {
	text-align: center;
}

#categories {
	border: solid 1px blue;
	width: 250px;
	padding-left: 20px;
	height: 400px;
	line-height: 40px;
	float: left;
}

#books {
	float: left;
	margin-left: 40px;
}

#image {
	float: left;
}

#info {
	float: left;
}

#book {
	float: left;
	width: 230px;
}
</style>
</head>

<body>

	<div id="container">
		<div id="head">
			<%@include file="/client/head.jsp"%>
		</div>

		<div id="main">

			<div id="categories">
				书籍列表：
				<c:forEach var="c" items="${categories}">
					<li><a
						href='${pageContext.request.contextPath }/client/IndexServlet?category_id=${c.id }'>${c.name
							}</a></li>
				</c:forEach>
			</div>

			<div id="books">
				<c:forEach var="book" items="${pagebean.list}" varStatus="status">
					<div id="book">
						<div id="image">
							<img
								src="${pageContext.request.contextPath }/images/${book.image }" />
						</div>
						<div id="info">
							<li>${book.name }</li>
							<li>${book.author }</li>
							<li>${book.price }</li>
							<li><a
								href="${pageContext.request.contextPath }/client/BuyServlet?id=${book.id }">购买</a>
							</li>
						</div>
						<div style="clear: both"></div>
					</div>
					<c:if test="${status.count%3==0}">
						<div style="clear: both"></div>
						<br />
					</c:if>
				</c:forEach>
				<div style="clear: both"></div>
				<br />
				
				<div id="pagebar">
					总共${pagebean.totalpage }页 当前${pagebean.currentpage }页
					<c:forEach var="pagenum" items="${pagebean.pagebar}">
						<a
							href="${pageContext.request.contextPath }/client/IndexServlet?currentpage=${pagenum }&category_id=${param.category_id }">${pagenum
							}</a>
					</c:forEach>

				</div>
			</div>

		</div>
	</div>
</body>
</html>
