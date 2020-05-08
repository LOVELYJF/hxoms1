
package com.hxoms.modules.dataCapture.log.annotation;


import java.lang.annotation.*;

/**
 * @authore:wjf
 * @data 2020/5/6 10:44
 * @Description:
 ***/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
