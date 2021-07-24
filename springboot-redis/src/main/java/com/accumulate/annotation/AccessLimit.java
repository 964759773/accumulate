package com.accumulate.annotation;

import java.lang.annotation.*;

@Documented //  表明该注解标记的元素可以被Javadoc 或类似的工具文档化
@Inherited // 表明使用了@Inherited注解的注解，所标记的类的子类也会拥有这个注解
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD}) // 该注解可以应用的java元素类型
@Retention(RetentionPolicy.RUNTIME) // 该注解的生命周期
public @interface AccessLimit {
    // 最大请求次数
    int maxCount() default 5;

    // 请求次数的指定时间范围  秒数(redis数据过期时间)
    int seconds() default 60;

}
