<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="${pageContext.request.contextPath}/img/icon.ico">
<title>Customer Login for Waste Management System...</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/icon.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<main>
	<div class="container">
		<div class="row">
			<div class="col s12 m6 offset-m3">

				<form method="POST"
					action="${pageContext.request.contextPath}/login" class="col s12">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Sign In</span>
							<c:if test="${param.error != null}">
    							<div class="materialize-red-text">Either Provided
									Credentials are wrong or user is inactive</div>
							</c:if>
							<div class="row">
								<div class="input-field col s12">
									<input id="username" type="text" name="username"
										class="validate" required /> <label for="username"
										class="active">User Name</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12">
									<input id="password" type="password" name="password"
										class="validate" minlength="4" required /> <label
										id="password" class="active">Password</label>

								</div>
							</div>
						</div>
					</div>
					<div class="card-action">
						<input type="submit" class="btn" value="Sign In"></input> <a
							class="btn"
							href="${pageContext.request.contextPath}/registration">Register
							Here</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	</main>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>