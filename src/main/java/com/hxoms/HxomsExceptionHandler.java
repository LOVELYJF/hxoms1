package com.hxoms;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HxomsExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(HxomsExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandle(Exception e) {
        String message = e.getMessage();
        logger.error("异常信息：", e);
        message = "服务器问题，联系开发人员解决。";
        return Result.error(message);
    }
    @ExceptionHandler(CustomMessageException.class)
    @ResponseBody
    public Result customMessageExceptionHandle(CustomMessageException e) {
        String message = e.getMessage();
        logger.error("异常信息：", e);
        if (StringUtils.isBlank(message))
            message = "检查参数传递问题...";
        return Result.error(message);
    }
    /**
     * @Desc: 处理Get请求中实体参数验证异常
     * @Author: wangyunquan
     * @Date: 2020/9/6
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindExceptionHandler(BindException  e) {
        return Result.error(e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining()));
    }

    /**
     * @Desc: 处理请求中@RequestParam参数验证异常
     * @Author: wangyunquan
     * @Date: 2020/9/6
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result constraintViolationExceptionHandler(ConstraintViolationException e) {
        return Result.error(e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining()));
    }

    /**
     * @Desc: 处理Post请求中@RequestBody参数验证异常
     * @Author: wangyunquan
     * @Date: 2020/9/6
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return Result.error(e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining()));
    }
}
