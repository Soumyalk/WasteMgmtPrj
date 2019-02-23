<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>

<title>Pantry Food Item List </title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<span class="card-title"><h5>Pantry Food Item List</h5></span> <span
			class="card-title"><a class="btn"
			href="${pageContext.request.contextPath}/admin/address/fooditemlimit/new">
				Add New Config</a></span>
		<table class="striped">
			<thead>
				<tr>
					<th>Id</th>
					<th>Address</th>
					<th>Food Item</th>
					<th>Max Allowed Value</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${entities}" var="p">
					<tr>
						<td>${p.id}</td>
						<td>${p.address}</td>
						<td>${p.foodName}</td>
						<td>${p.maxAllowed}</td>
						<td><a
							href="${pageContext.request.contextPath}/admin/address/fooditemlimit/edit?id=${p.id}">Edit</a></td>
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
