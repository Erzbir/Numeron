package com.erzbir.numeron.enums;

import com.erzbir.numeron.api.filter.Matcher;
import lombok.Getter;

/**
 * 将 {@code StringMatchers} 委托给此枚举
 *
 * @author Erzbir
 * @Date 2023/9/27
 */
@Getter
public enum MatchType {
    TEXT_EQUALS(StringMatchers.EQUALS.getMatcher()),

    TEXT_EQUALS_IGNORE_CASE(StringMatchers.EQUALS_IGNORE_CASE.getMatcher()),

    TEXT_STARTS_WITH(StringMatchers.STARTS_WITH.getMatcher()),

    TEXT_ENDS_WITH(StringMatchers.ENDS_WITH.getMatcher()),

    TEXT_CONTAINS(StringMatchers.CONTAINS.getMatcher()),

    REGEX_MATCHES(StringMatchers.REGEX_MATCHES.getMatcher()),

    REGEX_CONTAINS(StringMatchers.REGEX_CONTAINS.getMatcher());

    private final Matcher<String, String> matcher;

    MatchType(Matcher<String, String> matcher) {
        this.matcher = matcher;
    }

}
