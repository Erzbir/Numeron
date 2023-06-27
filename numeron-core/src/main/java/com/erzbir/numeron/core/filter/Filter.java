package com.erzbir.numeron.core.filter;

/**
 * 过滤器采用 策略模式 + 装饰者模式 + 工厂模式的设计
 * <p></p>
 * 实现此接口的抽象方法, 用于返回过滤的是否成立
 *
 * @author Erzbir
 * @Date: 2023/6/27 11:35
 */
@FunctionalInterface
public interface Filter {
    boolean filter();
}