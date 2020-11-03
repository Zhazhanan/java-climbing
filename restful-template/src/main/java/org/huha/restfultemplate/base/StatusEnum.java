package org.huha.restfultemplate.base;

/**
 * 服务状态
 *
 * @author: WangKun
 * @date: 2020/10/25
 */
public enum StatusEnum {
    /**
     * 操作成功
     */
    OK(200, "操作成功"),

    /**
     * 没有权限
     */
    UNAUTHORIZED(401, "没有权限"),
    /**
     * 业务异常
     */
    BUSINESS_EXCEPTION(500, "业务异常"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(700, "服务器出错啦");
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 内容
     */
    private String message;

    StatusEnum(Integer code, String message) {
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
