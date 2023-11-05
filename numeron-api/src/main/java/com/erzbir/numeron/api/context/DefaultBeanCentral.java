package com.erzbir.numeron.api.context;

import com.erzbir.numeron.annotation.Lazy;
import com.erzbir.numeron.utils.ClassScanner;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Erzbir
 * @Date 2023/11/2
 */
@Getter
public class DefaultBeanCentral implements BeanFactory, BeanContext {
    public final static DefaultBeanCentral INSTANCE = new DefaultBeanCentral();
    private final Map<String, Object> singletonMap = new HashMap<>();
    private final Map<String, Class<?>> lazyMap = new HashMap<>();

    @Override
    public void addBean(Object bean) {
        singletonMap.put(bean.getClass().getName(), bean);
    }

    @Override
    public void addBean(String name, Object bean) {
        singletonMap.put(name, bean);
    }

    @Override
    public Object remove(Object bean) {
        for (Map.Entry<String, Object> entry : singletonMap.entrySet()) {
            if (entry.getValue().equals(bean)) {
                return singletonMap.remove(entry.getKey());
            }
        }
        return null;
    }

    @Override
    public Object remove(String name) {
        return singletonMap.remove(name);
    }

    private boolean isConstructClass(Class<?> bean) {
        int modifiers = bean.getModifiers();
        return !bean.isAnnotation() && !bean.isEnum() && !bean.isInterface() && !Modifier.isAbstract(modifiers);
    }

    @Override
    public Object getBean(Class<?> requiredType) {
        return getBean(requiredType.getName());
    }

    @Override
    public Object getBean(String name) {
        Object o = singletonMap.get(name);
        if (o == null) {
            o = getLazyBean(name);
        }
        return o;
    }

    private Object getLazyBean(Class<?> type) {
        return getLazyBean(type.getName());
    }

    private Object getLazyBean(String name) {
        Object o;
        Class<?> clazz = lazyMap.get(name);
        o = create(clazz);
        if (o != null) {
            singletonMap.put(name, o);
        }
        return o;
    }

    /**
     * @param interfaceType 接口的字节码
     * @return 返回类名为键, 类的字节码为值的Map
     * <p>取出所有实现了{@param interfaceType}接口的类实现</p>
     */
    public Map<String, Object> getBeansWithInter(Class<?> interfaceType) {
        Map<String, Object> beans = new HashMap<>();
        singletonMap.keySet().forEach(k -> {
            Object bean = getBean(k);
            if (interfaceType.isAssignableFrom(bean.getClass())) {
                beans.put(k, bean);
            }
        });
        return beans;
    }

    /**
     * @param annotationType 注解的字节码
     * @return 返回类名为键, 类的字节码为值的Map
     * <p>取出所有带有{@param annotationType}注解的类<p/>
     */
    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        HashMap<String, Object> beans = new HashMap<>();
        singletonMap.forEach((k, v) -> {
            Object bean = getBean(k);
            if (bean.getClass().isAnnotationPresent(annotationType)) {
                beans.put(k, bean);
            }
        });
        lazyMap.forEach((k, v) -> {
            if (v.isAnnotationPresent(annotationType)) {
                beans.put(k, getBean(k));
            }
        });
        return beans;
    }

    @Override
    public boolean contains(Object object) {
        if (object instanceof Class<?>) {
            return lazyMap.containsValue(object);
        }
        return singletonMap.containsValue(object);
    }

    @Override
    public void plus(String packageName, ClassLoader classLoader) {
        plus(packageName, classLoader, null);
    }

    @Override
    public void plus(String packageName, ClassLoader classLoader, Predicate<Class<?>> filter) {
        ClassScanner scanner = new ClassScanner(packageName, classLoader, filter);
        Set<Class<?>> classes = scanner.scan();
        plus(classes);
    }

    @Override
    public void plus(String name, Class<?> c) {
        if (!isConstructClass(c)) {
            return;
        }
        Lazy annotation = c.getAnnotation(Lazy.class);
        if (annotation != null) {
            lazyMap.put(name, c);
        } else {
            addBean(name, create(c));
        }
    }

    @Override
    public void plusByAnnotation(String packageName, ClassLoader classLoader, Class<? extends Annotation> annotation) {
        ClassScanner scanner = new ClassScanner(packageName, classLoader, null);
        Set<Class<?>> classes = scanner.scanWithAnnotation(annotation); // 扫瞄带有@Component注解的class
        plus(classes);
    }

    @Override
    public void plusBySupperClass(String packageName, ClassLoader classLoader, Class<?> supperClass) {
        ClassScanner scanner = new ClassScanner(packageName, classLoader, null);
        Set<Class<?>> classes = scanner.scanWithSupperClass(supperClass); // 扫瞄带有@Component注解的class
        plus(classes);
    }

    @Override
    public Class<?> reduce(String name) {
        return lazyMap.remove(name);
    }

    @Override
    public Class<?> reduce(Class<?> c) {
        for (Map.Entry<String, Class<?>> entry : lazyMap.entrySet()) {
            if (entry.getValue().equals(c)) {
                return lazyMap.remove(entry.getKey());
            }
        }
        return null;
    }

    @Override
    public Class<?> get(String name) {
        return lazyMap.get(name);
    }

    @Override
    public void plus(Set<Class<?>> classes) {
        classes.forEach(this::plus);
    }

    @Override
    public void plus(Set<Class<?>> classes, Predicate<Class<?>> filter) {
        plus(classes.stream().filter(filter).collect(Collectors.toSet()));
    }

    @Override
    public void plus(Class<?> beanClass) {
        plus(beanClass.getName(), beanClass);
    }

}
