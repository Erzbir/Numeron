package com.erzbir.numeron.enums;

/**
 * @author Erzbir
 * @Date: 2022/11/19 10:54
 * <p>规则过滤类型</p>
 */
public enum FilterRule {
    /**
     * 过滤掉黑名单
     */
    BLACK,

    /**
     * 过滤掉未注册的群
     */
    NORMAL,

    /**
     * 不做任何过滤
     */
    NONE,

    /**
     * 自定义模式
     */
    CUSTOM
}
