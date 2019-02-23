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
		
		<div class="col s6">
			<a href="${pageContext.request.contextPath}/dashboard">Home</a>&#187;
			&nbsp;<a href="href="${pageContext.request.contextPath}/admin/permissions"">Permissions</a> &#187; &nbsp;Edit Permission
		</div>
	
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<form:form name='permissionFrm' modelAttribute="entity"
					action="${pageContext.request.contextPath}/admin/permissions/update"
					method="post">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Edit Permission</span>
							<c:if test="${not empty error}">
								<div class="materialize-red-text">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="id" path="id" type="text" name="id"
										readonly="true" value="${entity.id}" />
									<label for="id" class="active">Id</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="permission" path="name" type="text" name="name"
										value="${entity.name}" required="required" />
									<label for="permission" class="active">Permission Name</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s10">
									<form:input id="description" path="desc" name="description"
										value="${entity.desc}" />
									<label for="description" class="active">Description</label>
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


	<jsp:include page="footer.jsp"></jsp:include>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

</body>
</html>
