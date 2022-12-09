package com.erzbir.mirai.numeron.filter.rule;

import com.erzbir.mirai.numeron.filter.ChannelFilterInter;
import com.erzbir.mirai.numeron.filter.FilterFactory;

import static com.erzbir.mirai.numeron.enums.FilterRule.*;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
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
        if (e.equals(BLACK)) {
            return new BlackRuleFilter();
        } else if (e.equals(NORMAL)) {
            return new NormalRuleFilter();
        } else if (e.equals(NONE)) {
            return new NoneRuleFilter();
        }
        return null;
    }
}
