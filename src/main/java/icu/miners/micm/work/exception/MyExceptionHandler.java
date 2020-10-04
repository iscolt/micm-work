package icu.miners.micm.work.exception;

import icu.miners.micm.work.model.base.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	private ResponseResult<Object> handlerErrorInfo(HttpServletRequest request, Exception e) {
		log.error(e.getMessage());
		return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器异常");
	}
}
