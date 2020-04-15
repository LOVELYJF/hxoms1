package com.hxoms;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HxomsExceptionHandler {

    @ExceptionHandler
    public Result handleException(Exception e) {
        String message = e.getMessage();
        if (e instanceof CustomMessageException) {
            if (StringUtils.isBlank(message)) {
                message = "检查参数传递问题...";
            }
        } else {
            message = "服务器问题，联系开发人员解决。";
        }
        return Result.error(message);
    }

}
