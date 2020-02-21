<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QR Code</title>
</head>
<body>

	<h3>Product List</h3>
	<table border="1" cellpadding="2" cellspacing="2">
		<tr>
			<th>Id</th>
			<th>company</th>
			<th>points</th>
			<th>quantity</th>
			<th>BarCode</th>
		</tr>
		<c:forEach var="product" items="${products }">
			<tr>
				<td>${product.id }</td>
				<td>${product.company }</td>
				<td>${product.points }</td>
				<td>${product.quantity }</td>
				<td>
					<img src="${pageContext.request.contextPath }/product/qrcode/${product.id }" width="100" height="100">
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>
