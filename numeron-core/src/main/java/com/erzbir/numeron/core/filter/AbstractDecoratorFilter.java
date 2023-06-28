package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;

/**
 * 抽象装饰者基类
 * <p></p>
 * 此抽象类为装饰者模式的体现, 继承此类的类都会继承一个 {@code Filter} 且通过唯一的有参构造初始化
 * <p></p>
 * 继承这个类的所有子类都会有一个 {@code super.filter()} 来调用此父类方法, 完成过滤方法的组装
 * <p></p>
 * 在子类构造时会包含另一个实现 {@code Filter} 接口的子类, 通过不断构造上一个子类把功能组装起来
 *
 * @author Erzbir
 * @Date: 2023/6/25 18:45
 * @see AbstractFilter
 * @see EventFilter
 * @see com.erzbir.numeron.core.processor.MessageAnnotationProcessor 此为具体的使用案例
 * @see Event
 */
public abstract class AbstractDecoratorFilter extends AbstractFilter implements Filter {
    protected Filter decoratorFilter;

    public AbstractDecoratorFilter(Filter decoratorFilter) {
        this.decoratorFilter = decoratorFilter;
    }

    @Override
    public boolean filter() {
        if (filterRule == null) {
            throw new RuntimeException("have to set filter rule");
        }
        return decoratorFilter.filter();
    }
}
