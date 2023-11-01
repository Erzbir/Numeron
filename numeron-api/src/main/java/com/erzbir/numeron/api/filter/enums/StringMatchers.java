package com.erzbir.numeron.api.filter.enums;

import com.erzbir.numeron.api.filter.StringMatcher;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类实际上是一个是 {@code StringMatcher} 的单例实现, 例如: {@code EQUALS},
 * 使用函数式接口的方式构造一个匹配两个字符串是否相同的 {@code StringMatcher},
 * 而 {@code EQUALS} 就是这个 {@code StringMatcher} 的单例模式 (枚举特性)
 *
 * @author Erzbir
 * @Date 2023/9/27
 */
@Getter
public enum StringMatchers {
    EQUALS(String::equals),

    EQUALS_IGNORE_CASE(String::equalsIgnoreCase),

    STARTS_WITH((target, rule) -> rule.startsWith(target)),

    ENDS_WITH((target, rule) -> rule.endsWith(target)),

    CONTAINS((target, rule) -> rule.contains(target)),

    REGEX_MATCHES((target, rule) -> {
        Pattern pattern = Pattern.compile(rule);
        Matcher regMatcher = pattern.matcher(target);
        return regMatcher.matches();
    }),

    REGEX_CONTAINS((target, rule) -> {
        Pattern pattern = Pattern.compile(rule);
        Matcher regMatcher = pattern.matcher(target);
        while (regMatcher.hitEnd()) {
            if (regMatcher.find()) {
                return true;
            }
        }
        return false;
    });

    private final StringMatcher matcher;

    StringMatchers(StringMatcher matcher) {
        this.matcher = matcher;
    }

}
