package com.hxoms.modules.dataCapture.datasources.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @authore:wjf
 * @data 2020/4/14 9:44
 * @Description:
 ***/

/**
 * 注解生命周期作用范围
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 *注解作用在参数上
 */
@Target({
        ElementType.METHOD,ElementType.PARAMETER
})
public @interface DBChange {


}
