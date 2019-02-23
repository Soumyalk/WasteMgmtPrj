<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="img/jtechy1.ico">
<title>FoodNames List Page</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<main>
	<div class="container">
		<span class="card-title"><h5>FoodName List</h5></span> <span
			class="card-title"><a class="btn"
			href="${pageContext.request.contextPath}/admin/foodnames/create">
				Add New FoodItem</a></span>
		<table class="striped">
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Food Type</th>
					<th></th>
					<th></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${foodNames}" var="p">
					<tr>
						<td>${p.id}</td>
						<td>${p.foodName}</td>
						<td>${p.foodType.name}</td>
						<td><a
							href="${pageContext.request.contextPath}/admin/foodnames/edit?pId=${p.id}">Edit</a></td>
							<td><a
							href="${pageContext.request.contextPath}/admin/foodnames/delete?pId=${p.id}">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</main>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
