package com.erzbir.numeron.core.filter;

import com.erzbir.numeron.annotation.Permission;
import com.erzbir.numeron.api.filter.annotation.PermissionAnnotationFilter;
import com.erzbir.numeron.api.filter.factory.AnnotationFilterFactory;
import net.mamoe.mirai.event.Event;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public class PermissionAnnotationProcessor implements EventAnnotationProcessor {
    public static final PermissionAnnotationProcessor INSTANCE = new PermissionAnnotationProcessor();

    @Override
    public boolean process(AnnotatedElement annotatedElement, Event event) {
        Permission permission = annotatedElement.getAnnotation(Permission.class);
        if (permission == null) {
            return true;
        }
        PermissionAnnotationFilter permissionFilter = (PermissionAnnotationFilter) AnnotationFilterFactory.INSTANCE.create(permission);
        return permissionFilter.filter(event);
    }
}
