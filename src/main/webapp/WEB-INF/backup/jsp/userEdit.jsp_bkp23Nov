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
<title>Admin page</title>
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

		<div class="col s6">
			<a href="${pageContext.request.contextPath}/dashboard">Home</a>&#187;
			&nbsp;<a href="href="${pageContext.request.contextPath}/admin/users"">Users</a> &#187; &nbsp;Edit User
		</div>
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<form:form name='userForm' modelAttribute="userForm"
					action="${pageContext.request.contextPath}/admin/users/update"
					method="post">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Edit User</span>
							<c:if test="${not empty error}">
								<div class="materialize-red-text">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="firstName" path="firstName" type="text"
										name="firstName" readonly="true" value="${userForm.firstName}" />
									<label for="firstName" class="active">First Name</label>
								</div>
								<div class="input-field col s6">
									<form:input id="lastName" type="text" path="lastName"
										name="lastName" readonly="true" value="${userForm.lastName}" />
									<label for="lastName" class="active">Last Name</label>
								</div>
							</div>

							<div class="row">
								<div class="col s6">
									<form:checkbox id="userEnabled" path="userEnabled"
										label="Is User Active" value="${uerForm.userEnabled}" />
								</div>

								<div class="input-field col s6">
									<form:select id="gender" items="${genderList}" path="gender"
										readonly="true" value="${userForm.gender}">
									</form:select>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="loginId" path="loginId" readonly="true"
										value="${userForm.loginId}" />
									<label for="loginId" class="active">User Name</label>
								</div>

								<div class="input-field col s6">
									<form:input id="email" type="email" path="email"
										readonly="true" value="${userForm.email}" />
									<label for="email" class="active">Email</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s6">
									<form:input id="phone" type="tel" path="phone" name="phone"
										placeholder="123-456-7890"
										pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required="true"
										value="${userForm.phone}" />
									<label for="phone" class="active">Mobile Number</label>
								</div>
							</div>

							<c:if test="${not empty roles}">
								<div class="row">
									<div class=" col s6">
										<label for="userRoles" class="active">Roles<br /></label>
										<form:radiobuttons id="userRoles" name="userRoles"
											required="required" items="${roles}" path="role" value=""
											delimiter="<br/>" itemLabel="roleName"
											onchange="enableEntityForm(this)" />
									</div>
								</div>
							</c:if>

							<div id="entitySelectionForm" style="display: none;">

								<div class="row">
									<div class="input-field col s6">
										<form:select id="selectedRole" path="selectedRole"
											name="selectedRole" onchange="displayEntityForm(this)"
											items="${entityTypes}">
										</form:select>
									</div>
								</div>

								<div id="entityForm" style="display: none;">
									<div class="row">
										<div class="input-field col s6">
											<form:input id="entityName" path="entityName" type="text"
												name="entityName" required="required" />
											<label for="entityName" class="active">Entity Name</label>
										</div>
									</div>
									<div class="row">
										<div class="input-field col s6">
											<form:input id="description" path="description" type="text"
												name="description" />
											<label for="description" class="active">Description</label>
										</div>
									</div>

									<div class="row">
										<div class="input-field col s10">
											<form:input id="address" type="text" path="address"
												name="address" required="required" />
											<label for="address" class="active">Address</label>
										</div>
									</div>

									<div class="row">
										<div class="input-field col s10">
											<form:select id="state" path="stateConfig"
												required="required">
												<c:forEach var="st" items="${stateList}">
													<form:option value="${st}">${st.state}, ${st.city}</form:option>
												</c:forEach>
											</form:select>
											<label for="state" class="active">State</label>
										</div>
									</div>
								</div>
							</div>

							<div class="card-action">
								<input type="submit" class="btn" value="Update	" />
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
			var isTrue = $('#userRoles1')[0].checked == true;
			var comp = null;
			if (isTrue) {
				comp = $("label[for='userRoles1']")[0];
			}
			var isTrue = $('#userRoles2')[0].checked == true;
			if (isTrue) {
				comp = $("label[for='userRoles2']")[0];
			}
			enableEntityForm(comp);
			displayEntityForm($('#selectedRole')[0]);
		});

		function enableEntityForm(ele) {
			// 			var x = document.getElementById("entitySelectionForm");
			var x = $('#entitySelectionForm')[0];

			if (ele != undefined && null != ele) {

				if (ele.textContent === 'CUSTOMER'
						|| ele.labels[0].textContent === 'CUSTOMER') {
					if (x.style.display === "none") {
						x.style.display = "block";
					}
				}
			} else {
				x.style.display = "none";
			}
			var y = $('#entityName')[0];
			y.required = false;
			// 				y = document.getElementById("address");
			y = $('#address')[0];
			y.required = false;

			y = $('#state')[0];
			y.required = false;

		}

		function displayEntityForm(ele) {
			//var x = document.getElementById("entityForm");
			var x = $('#entityForm')[0];
			if (ele.value === 'ENTITY') {
				// 				var y = document.getElementById("entityName");
				var y = $('#entityName')[0];
				if (y.value === '') {
					y.required = true;
				} else {
					y.readOnly = true;
					$('#description')[0].readOnly = true;
				}
				// 				y = document.getElementById("address");
				y = $('#address')[0];
				if (y.value === '') {
					y.required = true;
				} else {
					y.readOnly = true;
				}

				y = $('#state')[0];
				if (y.value === '') {
					y.required = true;
				} else {
					y.readOnly = true;
				}

				if (x.style.display === "none") {
					x.style.display = "block";
				}
			} else {
				// 				var y = document.getElementById("entityName");
				var y = $('#entityName')[0];
				y.required = false;
				// 				y = document.getElementById("address");
				y = $('#address')[0];
				y.required = false;

				y = $('#state')[0];
				y.required = false;

			}
		}
	</script>
</body>
</html>
