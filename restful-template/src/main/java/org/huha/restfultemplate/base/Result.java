package org.huha.restfultemplate.base;

import java.io.Serializable;

/**
 * @author: WangKun
 * @date: 2020/10/25
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 3844594284474824174L;
    private Integer code;
    private String message;
    private T data;

    public static Result error() {
        return new Result(StatusEnum.UNKNOWN_ERROR.getCode(), StatusEnum.UNKNOWN_ERROR.getMessage());
    }

    public static Result error(StatusEnum statusEnum) {
        return new Result(statusEnum.getCode(), statusEnum.getMessage());
    }

    public static Result error(String msg) {
        return new Result(StatusEnum.UNKNOWN_ERROR.getCode(), msg);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg);
    }

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(T data) {
        this.code = StatusEnum.OK.getCode();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
