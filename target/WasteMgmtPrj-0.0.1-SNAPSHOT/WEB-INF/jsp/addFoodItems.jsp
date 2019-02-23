<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn1" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<div class="col s12 m6 offset-m3">

	<div class="row">
		<div class="input-field col s6">
			<div class="row">
				<form:select id="fNames" items="${fNames}" path="foodType"
					itemLabel="name">
				</form:select>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="input-field col s6">
			<form:input id="val" path="value" type="text" name="name" />
			<label for="val" class="active">Quantity</label>
		</div>
	</div>
</div>
