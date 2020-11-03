package org.huha.restfultemplate.base.exception;

import org.huha.restfultemplate.base.Result;
import org.huha.restfultemplate.base.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常拦截，统一处理
 *
 * @author: WangKun
 * @date: 2020/10/25
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截参数校验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Result businessExceptionHandler(MethodArgumentNotValidException ex) {
        String msg = "";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            msg += error.getField() + error.getDefaultMessage() + ";";
        }
        return Result.error(msg);
    }

    /**
     * 拦截一般业务异常，直接返回
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({BaseException.class})
    @ResponseBody
    public Result businessExceptionHandler(BaseException ex) {
        return Result.error(ex.getCode(), ex.getMessage());
    }


    /**
     * 拦截异常，直接返回
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exceptionHandler(Exception ex) {
        log.error("", ex);
        return Result.error(StatusEnum.UNKNOWN_ERROR);
    }
}
