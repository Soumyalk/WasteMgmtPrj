<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>

<title>Admin page</title>
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
			<c:if test="${showUsrMgnt eq true}">
				<div class="col s12 m4">
					<div class="card">
						<div class="card-content">
							<span class="card-title"><h5>User Management</h5></span>
							<div class="row" style="padding-left: 10px">
								<a href="${pageContext.request.contextPath}/admin/users">Users</a>
								
							</div>
							<div class="row" style="padding-left: 10px">
								<a href="${pageContext.request.contextPath}/admin/permissions">User
									Permissions</a>
								
							</div>
							<div class="row" style="padding-left: 10px">
								<a href="${pageContext.request.contextPath}/admin/userroles">User
									Roles</a>
								
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<div class="col s12 m4">
				<div class="card">
					<div class="card-content">
						<span class="card-title"><h5>Food Wastage ordering</h5></span>
						<div class="row" style="padding-left: 10px">
							<a href="${pageContext.request.contextPath}/admin/metrics">Food
								Wastage Metrics</a>
							
						</div>
						<div class="row" style="padding-left: 10px">
							<a href="${pageContext.request.contextPath}/admin/orders">Orders</a>
							
						</div>
						<div class="row" style="padding-left: 10px">
							<a href="${pageContext.request.contextPath}/admin/deliveredList">Order Delivered List</a>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${showFoodConfig eq true}">
			<div class="row">
				<div class="col s12 m4">
					<div class="card">
						<div class="card-content">
							<span class="card-title"><h5>Food Type Configuration</h5></span>
							<div class="row" style="padding-left: 10px">
								<a href="${pageContext.request.contextPath}/admin/foodtypes">Food
									Types</a>
								
							</div>
							<div class="row" style="padding-left: 10px">
								<a href="${pageContext.request.contextPath}/admin/foodnames">Food
									Items</a>
								
							</div>
						</div>
					</div>
				</div>

				<div class="col s12 m4">
					<div class="card">
						<div class="card-content">
							<span class="card-title"><h5>Food Pantry Location
									Maintenance</h5></span>
							<div class="row" style="padding-left: 10px">
								<a href="${pageContext.request.contextPath}/admin/address/list">Food
									Pantry Location</a>
								
							</div>
							<div class="row" style="padding-left: 10px">
								<a
									href="${pageContext.request.contextPath}/admin/address/state/list">State
									Config</a>
								
							</div>

							<div class="row" style="padding-left: 10px">
								<a href="${pageContext.request.contextPath}/admin/address/fooditemlimit/list">Food Limit Config</a>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
