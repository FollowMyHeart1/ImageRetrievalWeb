package team.culturecomputing.bupt.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import team.culturecomputing.bupt.dao.ExceptionResponse;
import team.culturecomputing.bupt.exception.ApiException;


@ControllerAdvice
public class GlobalExceptionHandler {
 
	private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
 
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ExceptionResponse handleException(HttpServletRequest request, Exception ex) {
		if (ex instanceof ApiException) {
			log.warn(ex.getMessage(), ex);
			ApiException apiException = (ApiException) ex;
			return ExceptionResponse.create(apiException.getCode(), apiException.getMessage());
		} else {
			log.error(ex.getMessage(), ex);
			return ExceptionResponse.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		}
	}
 
}
