package com.example.financemanager.exception;

import lombok.Getter;

/**
 * 自定义业务异常，用于处理业务层可以预见的错误
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message) {
        super(message);
        this.code = 1; // 默认业务错误码
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}