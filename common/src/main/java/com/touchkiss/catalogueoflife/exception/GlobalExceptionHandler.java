package com.touchkiss.catalogueoflife.exception;

import com.touchkiss.catalogueoflife.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理程序
 *
 * @author necksas.liu
 * @date 2026/03/30 19:34:35
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("Global Exception Captured: ", e);
        return Result.error("Internal Server Error: " + e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Illegal Argument: ", e);
        return Result.error(400, "Bad Request: " + e.getMessage());
    }
}
