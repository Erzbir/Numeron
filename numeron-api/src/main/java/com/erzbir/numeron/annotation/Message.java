package com.erzbir.numeron.annotation;


import com.erzbir.numeron.api.filter.CustomFilter;
import com.erzbir.numeron.api.filter.DefaultFilter;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>标记消息事件处理方法, 要生效类上必须要有 {@link Listener} 注解</p>
 *
 * <p>
 * 此注解标注的类会作为一个消息事件监听注册进 mirai 的监听中, 并将标记的方法作为回调方法, 注册消息监听时会根据方法上的此注解的方法返回值进行注册
 * </p>
 *
 * @author Erzbir
 * @Date: 2022/11/18 14:32
 * @see Event
 * @see FilterRule
 * @see MessageRule
 * @see PermissionType
 * @see Handler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Deprecated
public @interface Message {
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
     * <p>
     * 如果 filterRule 为 CUSTOM 且值不为 {@link DefaultFilter}, 会调用自定义的过滤器
     * </p>
     *
     * @return 指定过滤器的字节码
     */
    Class<? extends CustomFilter<?>> filter() default DefaultFilter.class;

    /**
     * @return 监听事件的优先级
     */
    EventPriority priority() default EventPriority.NORMAL;

    /**
     * @return 协程并发类型
     */
    ConcurrencyKind concurrency() default ConcurrencyKind.CONCURRENT;
}
