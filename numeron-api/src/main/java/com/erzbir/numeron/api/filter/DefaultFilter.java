package com.erzbir.numeron.api.filter;

/**
 * @author Erzbir
 * @Date 2023/8/11
 */
public class DefaultFilter implements CustomFilter {
    @Override
    public <E> boolean filter(E event) {
        return true;
    }
}
