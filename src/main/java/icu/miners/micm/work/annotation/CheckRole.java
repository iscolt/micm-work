package icu.miners.micm.work.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查角色，默认判断是否是管理员
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/29
 * @see: icu.miners.micm.work.annotation
 * @version: v1.0.0
 */
//注解信息会被添加到Java文档中
@Documented
//注解的生命周期，表示注解会被保留到什么阶段，可以选择编译阶段、类加载阶段，或运行阶段
@Retention(RetentionPolicy.RUNTIME)
//注解作用的位置，ElementType.METHOD表示该注解仅能作用于方法上
@Target(ElementType.METHOD)
public @interface CheckRole {
    short value() default 1;   // 注解参数
}
