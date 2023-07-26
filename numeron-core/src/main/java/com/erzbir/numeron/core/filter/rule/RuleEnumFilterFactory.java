package com.erzbir.numeron.core.filter.rule;

import com.erzbir.numeron.core.exception.FilterNotFoundException;
import com.erzbir.numeron.core.filter.EnumFilterFactory;
import com.erzbir.numeron.filter.FilterRule;

/**
 * @author Erzbir
 * @Data 2023/7/26
 */
public class RuleEnumFilterFactory implements EnumFilterFactory {
    public final static RuleEnumFilterFactory INSTANCE = new RuleEnumFilterFactory();

    @Override
    public AbstractRuleChannelFilter create(Enum<?> e) {
        if (e.equals(FilterRule.BLACK)) {
            return new BlackRuleChannelFilter();
        } else if (e.equals(FilterRule.NORMAL)) {
            return new NormalRuleChannelFilter();
        } else if (e.equals(FilterRule.NONE)) {
            return new NoneRuleChannelFilter();
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
