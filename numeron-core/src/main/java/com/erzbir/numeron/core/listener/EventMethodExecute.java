package com.erzbir.numeron.core.listener;

import com.erzbir.numeron.annotation.RunAfter;
import com.erzbir.numeron.annotation.RunBefore;
import com.erzbir.numeron.core.util.SimpleAnnotationParser;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:32
 * <p>消息处理方法</p>
 */
public class EventMethodExecute implements MethodExecute {
    public static final EventMethodExecute INSTANCE = new EventMethodExecute();

    private EventMethodExecute() {
    }

    @Override
    public void execute(Method method, Object bean, Object... args) {
        try {
            method.setAccessible(true);
            getRunBeforeFunc(method).run();
            method.invoke(bean, args);
            getRunAfterFunc(method).run();
        } catch (Exception e) {
            NumeronLogUtil.logger.error(e.getMessage(), e);
        }
    }

    private Runnable getRunBeforeFunc(Method method) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        RunBefore runBeforeAnnotation = method.getAnnotation(RunBefore.class);
        if (runBeforeAnnotation == null) {
            return () -> {
            };
        }
        return getRunnableFunc(method, runBeforeAnnotation);
    }

    private Runnable getRunAfterFunc(Method method) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        RunAfter runAfterAnnotation = method.getAnnotation(RunAfter.class);
        if (runAfterAnnotation == null) {
            return () -> {
            };
        }
        return getRunnableFunc(method, runAfterAnnotation);
    }

    private Runnable getRunnableFunc(Method method, Annotation annotation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SimpleAnnotationParser value = new SimpleAnnotationParser(annotation, "value");
        value.inject(method);
        Object retObj = value.getResultMap().get("value");
        String classDotMethod = (String) retObj;
        String[] classMethod = splitMethodName(classDotMethod);
        String simpleMethodName = removeParameters(classMethod[1]);
        try {
            Class<?> aClass = Class.forName(classMethod[0]);
            Method[] methods = aClass.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equals(simpleMethodName)) {
                    Object object = aClass.getConstructor().newInstance();
                    return () -> {
                        try {
                            m.invoke(object);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            NumeronLogUtil.logger.error(e.getMessage(), e);
                        }
                    };
                }
            }
        } catch (Exception e) {
            NumeronLogUtil.err(e.getMessage());
            return () -> {
            };
        }
        return () -> {
        };

    }

    private boolean validateMethod(String[] types1, String[] types2) {
        if (types1 == types2) {
            throw new IllegalArgumentException("same array");
        }
        if (types1.length != types2.length) {
            return false;
        }
        for (int i = 0, j = types1.length - 1; i < j; i++, j--) {
            if (!types1[i].equals(types2[i]) || !types1[j].equals(types2[j])) {
                return false;
            }
        }
        return true;
    }

    public String removeParameters(String methodName) {
        return methodName.replaceFirst("\\(.*", "");
    }

    public String[] splitMethodName(String s) {
        String[] ret = new String[]{"", ""};
        int i = s.length() - 1;
        StringBuilder builder = new StringBuilder();
        while (i > 0) {
            char c = s.charAt(i--);
            if (c == '.') {
                ret[1] = builder.toString();
                break;
            } else {
                builder.insert(0, c);
            }
        }
        ret[0] = s.substring(0, i + 1);
        return ret;
    }
}
