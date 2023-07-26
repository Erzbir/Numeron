package com.erzbir.numeron.core.filter.rule;

import com.erzbir.numeron.core.exception.FilterNotFoundException;
import com.erzbir.numeron.core.filter.AbstractEnumCacheFilterFactory;
import com.erzbir.numeron.core.filter.CacheFilterFactory;
import com.erzbir.numeron.core.filter.EnumFilterFactory;
import com.erzbir.numeron.filter.FilterRule;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
 * <p>规则过滤器工厂</p>
 */
public class RuleEnumCacheFilterFactory extends AbstractEnumCacheFilterFactory implements EnumFilterFactory, CacheFilterFactory {
    public final static RuleEnumCacheFilterFactory INSTANCE = new RuleEnumCacheFilterFactory();

    private RuleEnumCacheFilterFactory() {

    }

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
