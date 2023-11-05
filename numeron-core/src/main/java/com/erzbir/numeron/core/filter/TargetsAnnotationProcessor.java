package com.erzbir.numeron.core.filter;

import com.erzbir.numeron.annotation.Targets;
import com.erzbir.numeron.api.filter.factory.AnnotationFilterFactory;
import net.mamoe.mirai.event.Event;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public class TargetsAnnotationProcessor implements EventAnnotationProcessor {
    public static final TargetsAnnotationProcessor INSTANCE = new TargetsAnnotationProcessor();

    @Override
    public boolean process(AnnotatedElement annotatedElement, Event event) {
        Targets targets = annotatedElement.getAnnotation(Targets.class);
        if (targets == null) {
            return true;
        }
        return AnnotationFilterFactory.INSTANCE.create(targets).filter(event);
    }
}
