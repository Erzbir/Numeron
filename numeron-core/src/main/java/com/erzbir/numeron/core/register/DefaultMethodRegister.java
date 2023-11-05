package com.erzbir.numeron.core.register;

import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.core.util.AnnotationParser;
import com.erzbir.numeron.core.util.SimpleAnnotationParser;
import kotlin.coroutines.CoroutineContext;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date 2023/11/3
 */
@Setter
@Getter
public class DefaultMethodRegister implements MethodRegister {
    private EventHandlerRegister eventHandlerRegister = new EventHandlerRegister();

    @Override
    public void registerMethod(Object bean, Method method, EventChannel<? extends net.mamoe.mirai.event.Event> eventChannel, CoroutineContext coroutineContext) {
        Annotation handlerAnnotation = method.getAnnotation(Handler.class);
        if (handlerAnnotation == null) {
            handlerAnnotation = method.getAnnotation(EventHandler.class);
        }
        AnnotationParser annotationParser = new SimpleAnnotationParser(handlerAnnotation, "priority", "concurrency");
        annotationParser.inject(method);
        EventPriority priority = (EventPriority) annotationParser.get("priority");
        ConcurrencyKind concurrency = (ConcurrencyKind) annotationParser.get("concurrency");
        eventHandlerRegister.register(bean, method, eventChannel, priority, concurrency, coroutineContext);
    }
}
