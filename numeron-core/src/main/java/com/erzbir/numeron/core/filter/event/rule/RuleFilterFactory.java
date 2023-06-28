package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.filter.Filter;
import com.erzbir.numeron.core.filter.FilterFactory;
import com.erzbir.numeron.filter.FilterRule;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
 * <p>规则过滤器工厂</p>
 */
public class RuleFilterFactory implements FilterFactory {
    public final static RuleFilterFactory INSTANCE = new RuleFilterFactory();

    private RuleFilterFactory() {

    }

    /**
     * @param e 枚举子类
     * @return ChannelFilterInter
     */
    @Override
    public AbstractRuleFilter create(Enum<?> e, Filter filter) {
        if (e.equals(FilterRule.BLACK)) {
            return new BlackRuleFilter(filter);
        } else if (e.equals(FilterRule.NORMAL)) {
            return new NormalRuleFilter(filter);
        } else if (e.equals(FilterRule.NONE)) {
            return new NoneRuleFilter(filter);
        }
        return null;
    }
}
