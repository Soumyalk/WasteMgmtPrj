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
			&nbsp;<a href="${pageContext.request.contextPath}/admin/users">Users</a> &#187; &nbsp; New User
		</div>
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<form:form modelAttribute='userForm'
					action="${pageContext.request.contextPath}/admin/users/save"
					method="post">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Add User</span>
							<c:if test="${not empty error}">
								<div class="materialize-red-text">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="firstName" type="text" name="firstName"
										path="firstName" required="required" />
									<label for="firstName" class="active">First Name</label>
								</div>
								<div class="input-field col s6">
									<form:input id="lastName" type="text" path="lastName"
										required="required" />
									<label for="lastName" class="active">Last Name</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s6">
									<form:select id="gender" path="gender" required="required"
										name="gender" items="${genderList}">
										<option value="Male">Male</option>
										<option value="Female">Female</option>
									</form:select>
									<label for="gender" class="active">Gender</label>
								</div>
								<div class="row">
									<div class="input-field col s6">
										<form:input id="phone" type="tel" path="phone"
											required="required" placeholder="123-456-7890"
											pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" />
										<label for="phone" class="active">Mobile Number</label>
									</div>
									<div class="input-field col s10">
										<form:input id="email" type="email" path="email"
											required="required" />
										<label for="email" class="active">Email</label>
									</div>
								</div>

								<div class="row">
									<div class="input-field col s6">
										<form:input id="loginId" type="text" path="loginId"
											required="required" />
										<label for="loginId" class="active">LoginId</label>
									</div>
									<div class="input-field col s6">
										<form:input id="password" type="password" path="password"
											required="required" />
										<label for="password" class="active">password</label>
									</div>
								</div>
								<div class="card-action">
									<input type="submit" class="btn" value="Register" />
								</div>
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
	</script>
</body>
</html>
