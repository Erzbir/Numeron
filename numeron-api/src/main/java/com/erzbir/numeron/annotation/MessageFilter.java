package com.erzbir.numeron.annotation;

import com.erzbir.numeron.api.filter.CustomFilter;
import com.erzbir.numeron.api.filter.DefaultFilter;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 标记在 {@link Handler} 注解生效的方法上, 指定过滤
 * </p>
 *
 * <p>
 * 可以指定一个自定义的过滤规则, 如果加上过滤逻辑则为: customFilter && messageRule && permission
 * </p>
 *
 * <p>
 * 当监听到一个消息时, 首先调用 customFilter, 再调用 messageRule 进行消息匹配, 最后调用 permission 判断是否有权限, 过滤出一个期望的消息
 * </p>
 *
 * @author Erzbir
 * @Date 2023/8/13
 * @see CommonFilter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageFilter {
    String value() default "";

    /**
     * 默认无指定过滤规则
     *
     * @return 过滤规则
     */
    FilterRule filterRule() default FilterRule.NONE;

    /**
     * 默认规则是 str1.equals(str2)
     *
     * @return 消息匹配规则
     */
    MessageRule messageRule() default MessageRule.EQUAL;

    /**
     * @return 权限规则
     */
    PermissionType permission();

    /**
     * @return 需要匹配的文本
     */
    String text();

    /**
     * @return 指定监听对象的 id (User), 0 代表不指定
     */
    long id() default 0;

    /**
     * <p>
     * 如果 filterRule 为 CUSTOM 且值不为 {@link DefaultFilter}, 会调用自定义的过滤器
     * </p>
     *
     * @return 指定过滤器的字节码
     */
    Class<? extends CustomFilter<?>> customFilter() default DefaultFilter.class;
}
