package org.hoon.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler
{
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public String handler (Exception exception)
	{
		return exception.getMessage();
	}
}
