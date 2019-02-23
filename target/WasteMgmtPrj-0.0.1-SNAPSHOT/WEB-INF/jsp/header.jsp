<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<header>
	<nav role="navigation">
		<div class="nav-wrapper container">
			<a id="logo-container" href="/" class="brand-logo">Food Wastage
				Management System </a>

			<ul class="right hide-on-med-and-down">
				<c:choose>
					<c:when test="${null == login or empty login}">
						<li><a href="${pageContext.request.contextPath}/startpage">Home</a></li>
						<li><a href="${pageContext.request.contextPath}/login">Sign
								In</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/dashboard">Home</a></li>
						<li><a href="${pageContext.request.contextPath}/logout">Sign
								Out</a></li>
								<li><a
							href="${pageContext.request.contextPath}/admin/user/changepwd">Change password</a></li>
						<li><a
							href="${pageContext.request.contextPath}/admin/profile">My
								Profile</a></li>
						<li><img
							src="${pageContext.request.contextPath}/img/profile.jpg"></img></li>
						<li><h4>${login}</h4></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</nav>
</header>