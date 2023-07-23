package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.exception.FilterNotFoundException;
import com.erzbir.numeron.core.filter.AbstractCacheFilterFactory;
import com.erzbir.numeron.core.filter.CacheFilterFactory;
import com.erzbir.numeron.core.filter.FilterFactory;
import com.erzbir.numeron.filter.FilterRule;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
 * <p>规则过滤器工厂</p>
 */
public class RuleCacheFilterFactory extends AbstractCacheFilterFactory implements FilterFactory, CacheFilterFactory {
    public final static RuleCacheFilterFactory INSTANCE = new RuleCacheFilterFactory();

    private RuleCacheFilterFactory() {

    }

    /**
     * @param e 枚举子类
     * @return ChannelFilterInter
     */
    @Override
    public AbstractRuleFilter create(Enum<?> e) {
        if (e.equals(FilterRule.BLACK)) {
            return (AbstractRuleFilter) getFilter(BlackRuleFilter.class);
        } else if (e.equals(FilterRule.NORMAL)) {
            return (AbstractRuleFilter) getFilter(NormalRuleFilter.class);
        } else if (e.equals(FilterRule.NONE)) {
            return (AbstractRuleFilter) getFilter(NoneRuleFilter.class);
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
