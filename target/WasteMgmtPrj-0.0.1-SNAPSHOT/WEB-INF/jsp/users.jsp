<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>

<title>Users List page</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">

		<div class="row">
			<div class="col s6">
				<a href="${pageContext.request.contextPath}/dashboard">Home</a>	&#187;
				&nbsp; Users
			</div>
		</div>
		<span class="card-title"><h5>Users List</h5></span> <span
			class="card-title"><a class="btn"
			href="${pageContext.request.contextPath}/admin/users/create"> Add
				New User</a></span>

		<c:if test="${not empty error}">
			<div class="materialize-red-text">${error}</div>
		</c:if>
		<table class="striped">
			<thead>
				<tr>
					<th>login Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Is user Active</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usersList}" var="user">
					<tr>
						<td>${user.loginId}</td>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.email}</td>
						<td>${user.userEnabled}</td>
						<td><a
							href="${pageContext.request.contextPath}/admin/users/edit?loginId=${user.loginId}">Edit</a></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/users/delete?loginId=${user.loginId}">Delete</a></td>
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
