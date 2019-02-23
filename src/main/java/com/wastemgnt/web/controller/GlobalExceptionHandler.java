package com.wastemgnt.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	protected String handleConflict(Exception exception, WebRequest request) {
		return "error";
	}
}
