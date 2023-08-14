package com.erzbir.numeron.api.filter.factory.rule;

import com.erzbir.numeron.api.filter.AbstractCacheFilter;
import com.erzbir.numeron.api.filter.factory.CacheFilterFactory;
import com.erzbir.numeron.api.filter.factory.EnumFilterFactory;
import com.erzbir.numeron.api.filter.rule.AbstractRuleChannelFilter;
import com.erzbir.numeron.api.filter.rule.BlackRuleChannelFilter;
import com.erzbir.numeron.api.filter.rule.NoneRuleChannelFilter;
import com.erzbir.numeron.api.filter.rule.NormalRuleChannelFilter;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.exception.FilterNotFoundException;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
 * <p>规则过滤器工厂</p>
 */
public class RuleEnumCacheFilterFactory extends AbstractCacheFilter implements EnumFilterFactory, CacheFilterFactory {
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
