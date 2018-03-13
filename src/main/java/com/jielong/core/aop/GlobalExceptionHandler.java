package com.jielong.core.aop;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.ResponseBean;

public class GlobalExceptionHandler {

	/**
	 * 处理文件上传异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MultipartException.class)
	public ResponseBean<String> handleError1(MultipartException e) {

		ResponseBean<String> responseBean=new ResponseBean<String>();
		responseBean.setErrorCode(ErrorCode.FILE_UPLOAD_EXCEPTION);
		responseBean.setErrorMessage(e.getCause().getMessage());
		
		return responseBean;

	}

}
