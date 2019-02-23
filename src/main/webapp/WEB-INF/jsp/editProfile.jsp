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
<title>Edit Profile</title>
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
				<form:form name='userForm' modelAttribute="entity"
					action="${pageContext.request.contextPath}/admin/profile/update"
					method="post">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Edit Profile</span>
							<c:if test="${not empty error}">
								<div class="error">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="firstName" path="firstName" type="text"
										name="firstName" value="${userForm.firstName}"
										required="required" />
									<label for="firstName" class="active">First Name</label>
								</div>
								<div class="input-field col s6">
									<form:input id="lastName" type="text" path="lastName"
										name="lastName" value="${userForm.lastName}"
										required="required" />
									<label for="lastName" class="active">Last Name</label>
								</div>
							</div>

							<div class="row">

								<div class="input-field col s6">
									<form:select id="gender" items="${genderList}" path="gender"
										value="${userForm.gender}">
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
										value="${userForm.email}" required="required" />
									<label for="email" class="active">Email</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s6">
									<form:input id="phone" type="tel" path="phone" name="phone"
										value="${userForm.phone}" required="required" />
									<label for="phone" class="active">Mobile Number</label>
								</div>

							</div>

							<div class="row">
								<c:if test="${enableEntity}">
									<div class="input-field col s6">
										<form:select id="selectedRole" path="selectedRole"
											onchange="displayEntityForm(this)" items="${entityTypes}">>
										</form:select>
										<label for="selectedRole" class="active">Role</label>
									</div>
								</c:if>
							</div>

							<div id="entityForm" style="display: none;">
								<div class="row">
									<div class="input-field col s6">
										<form:input id="entityName" type="text" path="entityName"
											required="required" />
										<label for="entityName" class="active">Entity Name</label>
									</div>
								</div>
								<div class="row">
									<div class="input-field col s6">
										<form:input id="desc" type="text" path="description" />
										<label for="description" class="active">Description</label>
									</div>
								</div>

								<div class="row">
									<div class="input-field col s10">
										<form:input id="address" type="text" path="address"
											required="required" />
										<label for="address" class="active">Address</label>
									</div>
								</div>

								<div class="row">
									<div class="input-field col s10">
										<form:select id="state" path="stateConfig" required="required">
											<c:forEach var="st" items="${stateList}">
												<form:option value="${st}">${st.state}, ${st.city}</form:option>
											</c:forEach>
										</form:select>
										<label for="state" class="active">State</label>
									</div>
								</div>
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
			var y = $('#entityName')[0];
			y.required = false;
			// 				y = document.getElementById("address");
			y = $('#address')[0];
			y.required = false;

			y = $('#state')[0];
			y.required = false;

			displayEntityForm($('#selectedRole')[0])
		});

		function displayEntityForm(ele) {
			var x = $('#entityForm')[0];
			if (ele.value === 'ENTITY') {
				if (x.style.display === "none") {
					x.style.display = "block";
				}
			} else {
				var y = $('#entityName')[0];
				y.required = false;
				// 				y = document.getElementById("address");
				y = $('#address')[0];
				y.required = false;

				y = $('#state')[0];
				y.required = false;
				x.style.display = "none";
			}
		}
	</script>
</body>
</html>
