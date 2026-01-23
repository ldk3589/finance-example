package com.example.financemanager.exception;

import com.example.financemanager.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j // 别忘了记录日志
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * 我们应该自定义一个 BusinessException
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("业务运行时异常: {}", e.getMessage());
        // 返回 code=1 代表业务错误
        return Result.error(1, e.getMessage());
    }

    /**
     * 捕获参数校验异常 (如果你用了 @Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(400, msg);
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统未分类异常: ", e); // 打印完整的堆栈信息
        return Result.error(500, "系统开小差了，请稍后再试");
    }
}