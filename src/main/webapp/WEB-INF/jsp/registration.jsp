<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>

<title>Registration...</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	
	<div class="container">
		<div class="row">
			<div class="col s12 m6 offset-m3">
				<form:form action="${pageContext.request.contextPath}/register"
					modelAttribute="userForm" method="post">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">Registration</span>
							<c:if test="${not empty error}">
								<div class="materialize-red-text">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
							<c:if test="${empty msg}">
								<div class="row">
									<div class="input-field col s6">
										<form:input id="firstName" type="text" path="firstName"
											required="required" name="firstName" />
										<label for="firstName" class="active">First Name</label>
									</div>
									<div class="input-field col s6">
										<form:input id="lastName" type="text" path="lastName"
											required="required" name="lastName" />
										<label for="lastName" class="active">Last Name</label>
									</div>
								</div>

								<div class="row">
									<div class="input-field col s6">
										<form:select id="gender" path="gender" required="required"
											name="gender">
											<option value="Male">Male</option>
											<option value="Female">Female</option>
										</form:select>
										<label for="gender" class="active">Gender</label>
									</div>
									<div class="row">
										<div class="input-field col s6">
											<form:input id="phone" type="tel" name="phone" path="phone"
												placeholder="123-456-7890"
												pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required="required" />
											<label for="phone" class="active">Mobile Number</label>
										</div>
										<div class="input-field col s6">
											<form:input id="email" type="email" name="email"
												required="required" path="email" />
											<label for="email" class="active">Email</label>
										</div>
									</div>

									<div class="row">
										<div class="input-field col s6">
											<select id="selectedRole" name="selectedRole"
												onchange="displayEntityForm(this)">
												<c:forEach var="role" items="${roles}">
													<option value="${role}">${role}</option>
												</c:forEach>
											</select> <label for="selectedRole" class="active">Role</label>
										</div>
									</div>

									<div id="entityForm" style="display: none;">
										<div class="row">
											<div class="input-field col s6">
												<input id="entityName" type="text" name="entityName"
													required /> <label for="entityName" class="active">Entity
													Name</label>
											</div>
										</div>
										<div class="row">
											<div class="input-field col s6">
												<input id="desc" type="text" name="description" /> <label
													for="description" class="active">Description</label>
											</div>
										</div>

										<div class="row">
											<div class="input-field col s10">
												<input id="address" type="text" name="address" required />
												<label for="address" class="active">Address</label>
											</div>
										</div>

										<div class="row">
											<div class="input-field col s10">
												<form:select id="State" path="stateConfig"
													required="required">
													<c:forEach var="st" items="${stateList}">
														<form:option value="${st}">${st.state}, ${st.city}</form:option>
													</c:forEach>
												</form:select>
												<label for="state" class="active">State</label>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="input-field col s6">
											<form:input id="loginId" type="text" path="loginId" />
											<label for="loginId" class="active">LoginId</label>
										</div>
										<div class="input-field col s6">
											<form:input id="password" type="password" path="password" />
											<label for="password" class="active">password</label>
										</div>
									</div>
									<div class="card-action">
										<input type="submit" class="btn" value="Register" />
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>


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

			x.style.display = "none";
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
				y = $('#address')[0];
				y.required = false;


				x.style.display = "none";
			}
		}
	</script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>