<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>

<title>Metrics List Page</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css"
	media="screen,projection" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	
	<div class="container">
		<span class="card-title"><h5>Metrics List</h5></span>
		<form:form modelAttribute="type"
			action="${pageContext.request.contextPath}/admin/metrics"
			method="get">
			<form:select id="filterTypes" cssClass="filterTypes"
				items="${filterType}" value="${type}" onchange="this.form.submit();"
				path="type">
			</form:select>
			<table class="striped">
				<thead>
					<tr>
						<th>Food Name</th>
						<th>Food Type</th>
						<th>Value</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${metricsList}" var="p">
						<tr>
							<td>${p.foodName.foodName}</td>
							<td>${p.foodName.foodType.name}</td>
							<td>${p.value}&nbsp;${p.foodName.foodType.qType}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form:form>
	</div>
	</main>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
	<script>
		$(document).ready(function() {
			$('select').material_select();
		});
	</script>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
