package com.erzbir.mirai.numeron.boot.classloader;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Erzbir
 * @Date: 2022/12/12 01:17
 */
@SuppressWarnings("unchecked")
public class ClassScanner {

    private final String basePackage;
    private final boolean recursive;
    private final Predicate<String> packagePredicate;
    private final Predicate<Class<?>> classPredicate;

    /**
     * Instantiates a new Class scanner.
     *
     * @param basePackage 主包
     * @param recursive   是否递归扫描
     */
    public ClassScanner(String basePackage, boolean recursive, Predicate<String> packagePredicate,
                        Predicate<Class<?>> classPredicate) {
        this.basePackage = basePackage;
        this.recursive = recursive;
        this.packagePredicate = packagePredicate;
        this.classPredicate = classPredicate;
    }

    public <T> Set<Class<T>> scanAll() throws IOException, ClassNotFoundException {
        Set<Class<T>> classes = new LinkedHashSet<>();
        String packageName = basePackage;
        if (packageName.endsWith(".")) {
            packageName = packageName.substring(0, packageName.lastIndexOf('.'));
        }
        String basePackageFilePath = packageName.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(basePackageFilePath);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
                scanByFile(classes, packageName, filePath, recursive);
            }
        }

        return classes;
    }

    public <T> Set<Class<T>> scanWithAnnotation(Class<? extends Annotation> type) throws ClassNotFoundException, IOException {
        Set<Class<T>> classes = scanAll();
        return classes.stream().filter(t -> {
            boolean flag = !t.isAnnotation();
            Annotation[] annotations = t.getAnnotations();
            for (Annotation annotation : annotations) {
                return getAnnotationFromAnnotation(annotation, type) != null && flag;
            }
            return false;
        }).collect(Collectors.toSet());
    }

    public Annotation getAnnotationFromAnnotation(Annotation annotation, Class<? extends Annotation> clazz) {
        return annotation.annotationType().getAnnotation(clazz);
    }

    public <T> Set<Class<T>> scanWithInterface(Class<T> interfaceType) throws IOException, ClassNotFoundException {
        Set<Class<T>> classes = scanAll();
        return classes.stream().filter(t -> {
            Class<?>[] interfaces = t.getInterfaces();
            for (Class<?> inter : interfaces) {
                if (inter == interfaceType) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toSet());
    }

    private <T> void scanByFile(Set<Class<T>> classes, String packageName, String packagePath, boolean recursive) throws ClassNotFoundException {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        final boolean fileRecursive = recursive;
        File[] dirFiles = dir.listFiles(file -> {
            String filename = file.getName();
            if (file.isDirectory()) {
                if (!fileRecursive) {
                    return false;
                }
                if (packagePredicate != null) {
                    return packagePredicate.test(packageName + "." + filename);
                }
                return true;
            }
            return filename.endsWith(".class");
        });
        if (null == dirFiles) {
            return;
        }
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                scanByFile(classes, packageName + "." + file.getName(), file.getAbsolutePath(), recursive);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                if (classPredicate == null || classPredicate.test(loadClass)) {
                    classes.add((Class<T>) loadClass);
                }
            }
        }
    }
}