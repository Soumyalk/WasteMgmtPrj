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

<title>Create Order</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	
	<div class="container">
	
	<div class="col s6">
				<a href="${pageContext.request.contextPath}/admin/orders">Orders </a> &#187;
				&nbsp; Create Order
			</div>
	
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<form:form name='permissionFrm' modelAttribute="entity"
					action="${pageContext.request.contextPath}/admin/orders/save"
					method="post">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Create Order</span>
							<c:if test="${not empty error}">
								<div class="materialize-red-text">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
							<div class="row">
								<div class="input-field col s10">
									<form:input id="loc" path="pickupLocation" type="" name="loc"
										required="required" />
									<label for="loc" class="active">Pickup Location</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s10">
									<form:select id="selectedState" path="stateConfig"
										required="required">
										<c:forEach var="st" items="${stateList}">
											<form:option value="${st}">${st.state}, ${st.city}</form:option>
										</c:forEach>
									</form:select>
									<label for="state" class="active">State</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s10">
									<form:input id="description" path="description" type="text"
										name="name" />
									<label for="description" class="active">description</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s6">
									<div class="row">
										<form:select id="fNames" items="${fNames}" required="required"
											onchange="updateHintText(this)" path="orderFoodItem.foodName"
											itemLabel="foodName" itemValue="id">
										</form:select>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s6">
									<form:input id="val" required="required"
										path="orderFoodItem.value" type="number" name="name" />
									<label for="val" class="active">Quantity</label>
								</div>
								<label id="staticTxt" class="materialize-red-text" value=""
									name="staticTxt" />
							</div>

							<div class="card-action">
								<input type="submit" class="btn" value="Create" />
							</div>
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


	<script>
		$(document).ready(function() {
			$('select').material_select();
			var x = document.getElementById("staticTxt");
			var obj2 = '${map}';
			x.textContent = obj2.split(",")[0].split("=")[1];
		});

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
