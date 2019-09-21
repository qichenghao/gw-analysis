package com.qtone.gy.exception;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.qtone.gy.dto.ResponseDto;
import com.qtone.gy.enums.CommonResultEnum;
import com.qtone.gy.enums.StatusCodeEnum;
import com.qtone.gy.utils.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class RestExceptionHandler {
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * 文件不存在异常
     * @author renfei
     * @date 2017年11月9日 下午12:25:29
     * @param e
     * @return
     */
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseBody
    public ResponseDto fileNotFoundExceptionHandler(FileNotFoundException e) {
    	logger.error(e.getMessage());
    	return ResponseUtil.error(CommonResultEnum.COMMON_ERROR_642);
    }

    /**
     * 获取ip地址异常
     * @author guohaibing
     * @date 2017年11月1日 上午9:37:15
     * @param e
     * @return
     */
    @ExceptionHandler(UnknownHostException.class)
    @ResponseBody
    public ResponseDto UnknownHostException(UnknownHostException e) {
    	logger.error(e.getMessage());
    	return ResponseUtil.error(CommonResultEnum.UNKNOWNHOST_EXCEPTION);
    }
    /**
     * 外键约束异常
     * @author renfei
     * @date 2017年10月31日 上午9:37:15
     * @param e
     * @return
     */
    @ExceptionHandler(MySQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResponseDto request632(MySQLIntegrityConstraintViolationException e) {
    	logger.error(e.getMessage());
    	return ResponseUtil.error(CommonResultEnum.COMMON_ERROR_604, "");
    }

    /**
     * 文件大小超出限制异常
     * @author renfei
     * @date 2017年11月1日 下午5:30:48
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseDto maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
    	logger.error(e.getMessage());
    	return ResponseUtil.error(CommonResultEnum.COMMON_ERROR_620);
    }

    /**
     * 文件上传异常
     * @author renfei
     * @date 2017年10月31日 下午3:05:33
     * @param e
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public ResponseDto multipartExceptionHandler(MultipartException e) {
    	logger.error(e.getMessage());
    	return ResponseUtil.error(CommonResultEnum.COMMON_ERROR_602);
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseDto runtimeExceptionHandler(RuntimeException ex, HttpServletResponse response) {
        logger.error(ex.getMessage());
        response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
        return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseDto nullPointerExceptionHandler(NullPointerException ex, HttpServletResponse response) {
        logger.error(ex.getMessage());
        response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
        return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public ResponseDto classCastExceptionHandler(ClassCastException ex, HttpServletResponse response) {
       logger.error(ex.getMessage());
       response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
       return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
    }
    
    /**
     * 提交添加参数绑定异常
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public ResponseDto bindExceptionHandler(BindException ex, HttpServletResponse response) {
       logger.error(ex.getMessage());
        return ResponseUtil.error(CommonResultEnum.COMMON_ERROR_602);
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResponseDto iOExceptionHandler(IOException ex, HttpServletResponse response) {
       logger.error(ex.getMessage());
       response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
       return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
    }

    /**
     * 未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public ResponseDto noSuchMethodExceptionHandler(NoSuchMethodException ex, HttpServletResponse response) {
       logger.error(ex.getMessage());
       response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
       return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public ResponseDto indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex, HttpServletResponse response) {
       logger.error(ex.getMessage());
       response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
       return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
    }

    /**
     * 400错误
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseDto requestNotReadable(HttpMessageNotReadableException ex) {
       logger.error(ex.getMessage());
       return ResponseUtil.error(CommonResultEnum.COMMON_ERROR_602);
    }

    /**
     * 602错误
     */
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResponseDto requestTypeMismatch(TypeMismatchException ex) {
       logger.error(ex.getMessage());
        return ResponseUtil.error(CommonResultEnum.COMMON_ERROR_602);
    }

    /**
     * 400错误
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseDto requestMissingServletRequest(MissingServletRequestParameterException ex, HttpServletResponse response) {
       logger.error(ex.getMessage());
       response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
       return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
    }

    /**
     * 405错误
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResponseDto request405(HttpServletResponse response) {
    	response.setStatus(StatusCodeEnum.METHOD_NOT_ALLOWED.getCode());
        return ResponseUtil.error(StatusCodeEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 406错误
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ResponseDto request406(HttpServletResponse response) {
    	response.setStatus(StatusCodeEnum.NOT_ACCEPTABLE.getCode());
        return ResponseUtil.error(StatusCodeEnum.NOT_ACCEPTABLE);
    }

    /**
     * 500错误
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public ResponseDto server500(RuntimeException ex, HttpServletResponse response) {
       logger.error(ex.getMessage());
       response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
       return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
    }
    /**
     * 业务异常全局统一处理
     * @author huangguangxi
     * @date:   2017年9月29日 下午5:58:47    
     * @param ex
     * @return          
     * @throws
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseDto handleBusinessException(BusinessException ex, HttpServletResponse response) {
        logger.error(ex.getMessage());  // 记录业务异常错误信息
        if (null == ex.getParam()) {
        	if (StatusCodeEnum.SERVER_ERROR.getCode() == ex.getResultEnum().getCode()) {
        		response.setStatus(StatusCodeEnum.SERVER_ERROR.getCode());
        	} else if(StatusCodeEnum.NOTAUTHORIZATION.getCode() == ex.getResultEnum().getCode()) {
        		response.setStatus(StatusCodeEnum.NOTAUTHORIZATION.getCode());
        	}
        	return ResponseUtil.error(ex.getResultEnum());
        } else {
        	String[] tips=ex.getParam().split(",");
        	return ResponseUtil.error(ex.getResultEnum(), tips);
        }
    }

    /**
     * 404错误
     * @author renfei
     * @date 2017年10月25日 下午4:49:46
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
	public ResponseDto defaultErrorHandler(Exception e,HttpServletResponse response) throws Exception {
		logger.error(e.getMessage());
		if (e instanceof NoHandlerFoundException) {
			response.setStatus(CommonResultEnum.NO_ERROR.getCode());
			return ResponseUtil.error(CommonResultEnum.NO_ERROR);
		} else {
			response.setStatus(CommonResultEnum.SERVER_ERROR.getCode());
			return ResponseUtil.error(StatusCodeEnum.SERVER_ERROR);
		}
	}

}
