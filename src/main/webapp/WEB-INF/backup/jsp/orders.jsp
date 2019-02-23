<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>

<title>Orders List Page</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<span class="card-title"><h5>Orders List</h5></span>
		<c:if test="${showOrderLinks}">
			<span class="card-title"><a class="btn"
				href="${pageContext.request.contextPath}/admin/orders/create">
					Add New Order</a></span>
		</c:if>
		<table class="striped">
			<thead>
				<tr>
					<th>Created By</th>
					<th>Status</th>
					<th>Description</th>
					<th></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ordersList}" var="p">
					<tr>
						<td><a
							href="${pageContext.request.contextPath}/admin/orders/detail?pId=${p.id}">${p.createdBy}</a></td>
						
							<td>${p.status}</td>
						
						<td>${p.description}</td>
						<c:choose>
						<c:when test="${p.status ne'ORDER_DELIVERED' }">						
							<td><a
							href="${pageContext.request.contextPath}/admin/orders/edit?pId=${p.id}">Edit</a></td>
						</c:when>
						<c:otherwise>
							<td> </td>
						</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
