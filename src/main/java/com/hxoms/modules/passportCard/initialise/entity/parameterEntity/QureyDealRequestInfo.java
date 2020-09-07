package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/30
 */
@ApiModel(value = "存疑处理请求信息")
public class QureyDealRequestInfo {

    @NotBlank(message = "主键不能为空")
    @ApiModelProperty(value="主键",required = true)
    private String id;

    @NotBlank(message = "异常处理结论不能为空")
    @ApiModelProperty(value="异常处理结论",required = true)
    private String exceptionConclusion;

    @NotBlank(message = "异常处理人不能为空")
    @ApiModelProperty(value="异常处理人",required = true)
    private String exceptionHandler;

    @NotNull(message = "异常处理时间不能为空")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="异常处理时间",required = true)
    private Date exceptionSolvedate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExceptionConclusion() {
        return exceptionConclusion;
    }

    public void setExceptionConclusion(String exceptionConclusion) {
        this.exceptionConclusion = exceptionConclusion;
    }

    public String getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(String exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public Date getExceptionSolvedate() {
        return exceptionSolvedate;
    }

    public void setExceptionSolvedate(Date exceptionSolvedate) {
        this.exceptionSolvedate = exceptionSolvedate;
    }
}
