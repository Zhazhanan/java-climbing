package org.huha.restfultemplate.base.exception;


import org.huha.restfultemplate.base.StatusEnum;

/**
 * 权限异常
 *
 * @author: WangKun
 * @date: 2020/10/25
 */
public class AuthException extends BaseException {
    public AuthException() {
        super(StatusEnum.UNAUTHORIZED);
    }

    public AuthException(StatusEnum status) {
        super(status);
    }

    public AuthException(Integer code, String message) {
        super(code, message);
    }
}
