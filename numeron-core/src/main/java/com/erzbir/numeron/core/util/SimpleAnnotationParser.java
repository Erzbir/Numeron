package com.erzbir.numeron.core.util;

import com.erzbir.numeron.utils.NumeronLogUtil;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public class SimpleAnnotationParser implements AnnotationParser {
    private final String[] keys;
    private final Annotation annotation;
    @Getter
    private final Map<String, Object> resultMap = new HashMap<>();

    public SimpleAnnotationParser(Annotation annotation, String... keys) {
        this.annotation = annotation;
        this.keys = keys;
    }


    @Override
    public void inject(Object object) {
        if (object == null || annotation == null) {
            return;
        }
        Class<? extends Annotation> aClass = annotation.getClass();
        for (String key : keys) {
            try {
                Method declaredMethod = aClass.getDeclaredMethod(key);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(annotation);
                resultMap.put(key, invoke);
            } catch (Exception e) {
                NumeronLogUtil.err(e.getMessage());
            }
        }
    }

    @Override
    public Object get(String key) {
        return resultMap.get(key);
    }
}
