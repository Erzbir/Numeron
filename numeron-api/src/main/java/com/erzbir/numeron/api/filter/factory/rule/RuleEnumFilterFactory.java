package com.erzbir.numeron.api.filter.factory.rule;

import com.erzbir.numeron.api.exception.FilterNotFoundException;
import com.erzbir.numeron.api.filter.FilterRule;
import com.erzbir.numeron.api.filter.factory.EnumFilterFactory;
import com.erzbir.numeron.api.filter.rule.AbstractRuleChannelFilter;
import com.erzbir.numeron.api.filter.rule.BlackRuleChannelFilter;
import com.erzbir.numeron.api.filter.rule.NoneRuleChannelFilter;
import com.erzbir.numeron.api.filter.rule.NormalRuleChannelFilter;

/**
 * @author Erzbir
 * @Date 2023/7/26
 */
public class RuleEnumFilterFactory implements EnumFilterFactory {
    public final static RuleEnumFilterFactory INSTANCE = new RuleEnumFilterFactory();

    private RuleEnumFilterFactory() {

    }

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
