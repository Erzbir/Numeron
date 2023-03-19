package com.erzbir.numeron.core.filter.rule;

import com.erzbir.numeron.core.filter.ChannelFilterInter;
import com.erzbir.numeron.core.filter.FilterFactory;

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
    public ChannelFilterInter create(Enum<?> e) {
        if (e.equals(FilterRule.BLACK)) {
            return new BlackRuleFilter();
        } else if (e.equals(FilterRule.NORMAL)) {
            return new NormalRuleFilter();
        } else if (e.equals(FilterRule.NONE)) {
            return new NoneRuleFilter();
        }
        return null;
    }
}
