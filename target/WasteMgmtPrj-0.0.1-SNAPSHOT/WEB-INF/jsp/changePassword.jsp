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
			&nbsp;<a href="${pageContext.request.contextPath}/admin/users">Users</a>
			&#187; &nbsp;Edit User
		</div>
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<form:form name='userForm' modelAttribute="userForm"
					action="${pageContext.request.contextPath}/admin/user/savepwd"
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
									<form:input id="oldpassword" path="password" type="password"
										name="oldpassword" value="${userForm.password}" />
									<label for="oldpassword" class="active">Old Password</label>
								</div>

							</div>

							<div class="row">
								<div class="input-field col s6">
									<form:input id="newpassword" path="changedPwd" type="password"
										name="newpassword" />
									<label for="newpassword" class="active">New Password</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="cfrmpassword" path="confirmPwd" type="password"
										name="cfrmpassword" />
									<label for="cfrmpassword" class="active">Confirm
										Password</label>
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
