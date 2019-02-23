<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<jsp:include page="header.jsp"></jsp:include>
<html>
<head>
<link rel="icon" href="img/jtechy1.ico">
<title>Error</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />
</head>
<body>
	<main>
	<div class="container">
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<div class="card">
					<div class="card-content">
						<span class="card-title"><h5>Error Occurred</h5></span> <span
							class="materialize-red-text"> Something went wrong. Please
							contact Administrator!</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	</main>
</body>
<jsp:include page="footer.jsp"></jsp:include>
</html>