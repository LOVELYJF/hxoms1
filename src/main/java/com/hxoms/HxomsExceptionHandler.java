package com.hxoms;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HxomsExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(HxomsExceptionHandler.class);

    @ExceptionHandler
    public Result handleException(Exception e) {
        String message = e.getMessage();
        logger.error("异常信息：", e);
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
