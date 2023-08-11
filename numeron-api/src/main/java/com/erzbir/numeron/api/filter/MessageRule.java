package com.erzbir.numeron.api.filter;

/**
 * @author Erzbir
 * @Date: 2022/11/18 14:36
 * <p>消息过滤类型</p>
 */
public enum MessageRule {
    /**
     * 匹配以 x 开头的消息
     */
    BEGIN_WITH,

    /**
     * 匹配以 x 结尾的消息
     */
    END_WITH,

    /**
     * 匹配包含 x 的消息
     */
    CONTAINS,

    /**
     * 匹配与 x 相等的消息
     */
    EQUAL,

    /**
     * 匹配满足正则表达式 x 的消息
     */
    REGEX,

    /**
     * 匹配在 x 数组中的消息
     */
    IN
}
