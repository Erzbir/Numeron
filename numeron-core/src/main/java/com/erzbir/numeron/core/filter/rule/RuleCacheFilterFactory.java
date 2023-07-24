package com.erzbir.numeron.core.filter.rule;

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
    public AbstractRuleChannelFilter create(Enum<?> e) {
        if (e.equals(FilterRule.BLACK)) {
            return (AbstractRuleChannelFilter) getFilter(BlackRuleChannelFilter.class);
        } else if (e.equals(FilterRule.NORMAL)) {
            return (AbstractRuleChannelFilter) getFilter(NormalRuleChannelFilter.class);
        } else if (e.equals(FilterRule.NONE)) {
            return (AbstractRuleChannelFilter) getFilter(NoneRuleChannelFilter.class);
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
