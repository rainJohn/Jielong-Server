package com.jielong.core.aop;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.ResponseBean;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	/**
	 * 处理文件上传异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MultipartException.class)
	public ResponseBean<String> handleUploadException(MultipartException e) {

		ResponseBean<String> responseBean=new ResponseBean<String>();
		responseBean.setErrorCode(ErrorCode.FILE_UPLOAD_EXCEPTION);
		responseBean.setErrorMessage(e.getCause().getMessage());
		
		return responseBean;

	}
	
	@ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }


}
