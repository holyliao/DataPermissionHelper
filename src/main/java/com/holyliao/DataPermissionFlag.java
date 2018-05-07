package com.holyliao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 加在controller接口上表明当前接口需要进行数据权限控制，可以通过ControllerAdvice做切面处理
 * 加在其他方法上可以使用AOP做切面处理
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataPermissionFlag {
    String[] tables();
}
