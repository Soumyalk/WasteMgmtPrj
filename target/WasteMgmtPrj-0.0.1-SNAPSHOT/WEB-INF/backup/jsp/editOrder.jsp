<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn1" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="img/jtechy1.ico">
<title>Edit Order</title>
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
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<form:form name='permissionFrm' modelAttribute="entity"
					action="${pageContext.request.contextPath}/admin/orders/updateOrderDetails"
					method="post">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Order Details</span>
							<c:if test="${not empty error}">
								<div class="materialize-red-text">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="loc" path="id" readonly="true"
										value="${entity.id}" type="text" name="loc" />
									<label for="loc" class="active">Id</label>
								</div>
							</div>

							<c:if test="${not empty users}">
								<div class="row">
									<div class="input-field col s6">
										<form:label for="users" path="assignee" class="active">Assign User</form:label>
										<form:select id="users" items="${users}" required="required"
											value="${entity.assignee}" path="assignee"
											itemLabel="loginId">
										</form:select>
									</div>
								</div>

								<div class="row">
									<div class="input-field col s6">
										<form:select id="loc" path="address" items="${address}"
											value="${entity.address}" itemLabel="name" />
										<label for="loc" class="active">Depot Address</label>
									</div>
								</div>
							</c:if>

							<c:choose>
								<c:when test="${null == statusList or empty statusList}">
									<div class="row">
										<div class="input-field col s6">
											<form:input id="status" path="status" readonly="true"
												value="${entity.status}" type="text" name="desc" />
											<label for="description" class="active">Status</label>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="row">
										<div class="input-field col s6">
											<form:select id="status" path="status" items="${statusList}"
												value="${entity.status}" type="text" name="desc" />
											<label for="description" class="active">Status</label>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="loc" path="pickupLocation" readonly="true"
										value="${entity.pickupLocation}" type="text" name="loc" />
									<label for="loc" class="active">Pickup Location</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s10">
									<form:input id="state" path="stateConfig.state" type="text"
										name="state" readonly="true" required="required" />
									<label for="state" class="active">State</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s10">
									<form:input id="city" path="stateConfig.city" type="text"
										name="city" readonly="true" required="required" />
									<label for="city" class="active">City</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s6">
									<form:input id="desc" path="description" readonly="true"
										value="${entity.description}" type="text" name="desc" />
									<label for="description" class="active">description</label>
								</div>
							</div>

							<div class="row">
								<table class="striped">
									<thead>
										<tr>
											<th>Id</th>
											<th>Food Name</th>
											<th>value</th>
											<c:if test="${showOrderLinks}">
												<th></th>
											</c:if>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${entity.foodItems}" var="p">
											<tr>
												<td>${p.id}</td>
												<td>${p.foodName.foodName}</td>
												<td>${p.value}${p.foodName.foodType.qType}</td>
												<c:if test="${showOrderLinks}">
													<td><a
														href="${pageContext.request.contextPath}/admin/orders/deleteFI?pId=${p.id}&dId=${entity.id}">Delete</a></td>
												</c:if>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="card-action">
								<input type="submit" class="btn" value="Update" />
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	</main>

	<jsp:include page="footer.jsp"></jsp:include>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>


	<script>
		$(document).ready(function() {
			$('select').material_select();
		});
		function displayFDForm(ele) {
			var x = document.getElementById("fdDiv");
			if (x.style.display === "none") {
				x.style.display = "block";
			} else {
				x.style.display = "none";
			}
			x = document.getElementById("addFD");
			if (x.style.display === "none") {
				x.style.display = "block";
			} else {
				x.style.display = "none";
			}
		}
	</script>
</body>
</html>
