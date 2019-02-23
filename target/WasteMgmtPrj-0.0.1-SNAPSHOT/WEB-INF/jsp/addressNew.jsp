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
<title>New Address</title>
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
					action="${pageContext.request.contextPath}/admin/address/save"
					method="post">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Create Address</span>
							<c:if test="${not empty error}">
								<div class="error">${error}</div>
							</c:if>
							<div class="row">
								<div class="input-field col s6">
									<form:input id="name" path="name" type="text" name="name"
										required="required" />
									<label for="name" class="active">Address Name</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s10">
									<form:input id="address" path="address" name="address"
										required="required" />
									<label for="address" class="active">Address</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s10">
									<form:select id="selectedState" path="stateConfig"
										required="required">
										<c:forEach var="st" items="${stList}">
											<form:option value="${st}">${st.state}, ${st.city}</form:option>
										</c:forEach>
									</form:select>
									<label for=selectedState class="active">State</label>
								</div>
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
