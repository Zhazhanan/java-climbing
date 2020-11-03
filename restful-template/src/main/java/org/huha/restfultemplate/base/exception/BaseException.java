package org.huha.restfultemplate.base.exception;


import org.huha.restfultemplate.base.StatusEnum;

/**
 * 基础异常类
 *
 * @author: WangKun
 * @date: 2020/10/25
 */
public class BaseException extends RuntimeException {
    private Integer code;
    private String message;

    public BaseException(StatusEnum status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
