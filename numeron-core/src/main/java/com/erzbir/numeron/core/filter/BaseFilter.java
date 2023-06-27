package com.erzbir.numeron.core.filter;

/**
 * 装饰者基类
 *
 * @author Erzbir
 * @Date: 2023/6/25 16:02
 */
public class BaseFilter implements Filter {
    @Override
    public boolean filter() {
        return true;
    }
}
