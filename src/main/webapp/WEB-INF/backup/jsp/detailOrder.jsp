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

<title>Detail Order</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />

<link type="text/css" rel="stylesheet"
	href="https://api.mqcdn.com/sdk/mapquest-js/v1.3.2/mapquest.css" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	
	<div class="container">
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<form:form name='permissionFrm' modelAttribute="entity"
					action="${pageContext.request.contextPath}/admin/orders/update"
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
							<div class="row">
								<div class="input-field col s6">
									<form:input id="loc" path="createdBy" readonly="true"
										value="${entity.createdBy}" type="text" name="loc" />
									<label for="loc" class="active">Created By</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s10">
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
								<div class="input-field col s10">
									<form:input id="desc" path="description" readonly="true"
										value="${entity.description}" type="text" name="desc" />
									<label for="description" class="active">description</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s10" ue>
									<form:input id="address" path="address.address" readonly="true"
										value="${entity.address.address}" type="text" name="address" />
									<label for="description" class="active">Depot Address</label>
								</div>
							</div>
							<div id="map" style="width: 580px; height: 400px;"></div>


							<div class="row">
								<div class="input-field col s6">
									<form:input id="status" path="status" readonly="true"
										value="${entity.status}" type="text" name="desc" />
									<label for="description" class="active">Status</label>
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
												<td>${p.value}&nbsp;${p.foodName.foodType.qType}</td>
												<c:if test="${showOrderLinks}">
													<td><a
														href="${pageContext.request.contextPath}/admin/orders/deleteFI?pId=${p.id}&dId=${entity.id}">Delete</a>
													</td>
												</c:if>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>


						<div class="card-action">
							<c:if test="${showOrderLinks}">
								<a class="btn" id="addFD" onclick="displayFDForm(this)">Add
									Food Item </a>
							</c:if>
						</div>
						<div class="card" id="fdDiv" style="display: none;">
							<c:if test="${showOrderLinks}">
								<div class="card-content">
									<span class="card-title black-text">Add Food Items</span>
									<c:if test="${not empty msg}">
										<div class="msg">${msg}</div>
									</c:if>
									<div class="row">
										<form:select id="foodItems" items="${fTypes}"
											onchange="updateHintText(this)" required="required"
											path="orderFoodItem.foodName" itemLabel="foodName">
										</form:select>
									</div>

									<div class="row">
										<div class="input-field col s6">
											<form:input id="value" path="orderFoodItem.value"
												type="number" required="required" name="value" />
											<label for="value" class="active">Value</label>
										</div>

										<label id="staticTxt" class="materialize-red-text" value=""
											name="staticTxt" />
									</div>

									<div class="card-action">
										<input type="submit" class="btn" value="Create" />
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	

	<jsp:include page="footer.jsp"></jsp:include>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
	<script src="https://api.mqcdn.com/sdk/mapquest-js/v1.3.2/mapquest.js"></script>

	<script>
		$(document).ready(function() {
			$('select').material_select();
			var fromAddress = '${fromMapAddres}';
			var toAddress = '${toMapAddres}';
			L.mapquest.key = 'ocVLKLVda83av1QbS3MZqJiGz3hR4v95';

			var map = L.mapquest.map('map', {
				layers : L.mapquest.tileLayer('map'),
			      center: [40.7128, -74.0059],	
				zoom : 13
			});

			L.mapquest.directions().route({
				start : fromAddress,
				end : toAddress,
				optimizeWaypoints : true
			});
		});
		
		function displayFDForm(ele) {
			var x = document.getElementById("fdDiv");
			if (x.style.display === "none") {
				x.style.display = "block";
				var y = $("#staticTxt")[0];
				var obj2 = '${map}';
				y.textContent = obj2.split(",")[0].split("=")[1];
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

		function updateHintText(ele) {
			var x = document.getElementById("staticTxt");
			var obj2 = '${map}';
			var a = obj2.split(",");
			for (i = 0; i < a.length; i++) {
				var val = a[i].split("=")[0].replace("{", "").replace("}", "");
				if (val.trim() === ele.selectedOptions[0].label) {
					x.textContent = a[i].split("=")[1];
					break;
				}
			}

		}
	</script>
</body>
</html>
