package org.huha.restfultemplate.base.exception;

import org.huha.restfultemplate.base.StatusEnum;

/**
 * 权限异常
 *
 * @author: WangKun
 * @date: 2020/10/25
 */
public class BusinessException extends BaseException {
    public BusinessException() {
        super(StatusEnum.BUSINESS_EXCEPTION);
    }

    public BusinessException(StatusEnum status) {
        super(status);
    }

    public BusinessException(Integer code, String message) {
        super(code, message);
    }
}
