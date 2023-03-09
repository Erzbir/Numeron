package com.erzbir.mirai.numeron.classloader;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author Erzbir
 * @Date: 2022/12/12 01:17
 */
public class ClassScanner {

    private final String basePackage;
    private final boolean recursive;
    private final Predicate<String> packagePredicate;
    private final Predicate<Class<?>> classPredicate;
    private Set<Class<?>> classes;

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

    public Set<Class<?>> scanAllClasses() throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new LinkedHashSet<>();
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
                // 扫描文件夹中的包和类
                scanPackageClassesByFile(classes, packageName, filePath);
            } else if ("jar".equals(protocol)) {
                scanPackageClassesByJar(packageName, resource, classes);
            }
        }
        return classes;
    }

    private void scanPackageClassesByJar(String basePackage, URL url, Set<Class<?>> classes)
            throws IOException, ClassNotFoundException {
        String basePackageFilePath = basePackage.replace('.', '/');
        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (!name.startsWith(basePackageFilePath) || entry.isDirectory()) {
                continue;
            }
            if (!recursive && name.lastIndexOf('/') != basePackageFilePath.length()) {
                continue;
            }

            if (packagePredicate != null) {
                String jarPackageName = name.substring(0, name.lastIndexOf('/')).replace("/", ".");
                if (!packagePredicate.test(jarPackageName)) {
                    continue;
                }
            }
            String className = name.replace('/', '.');
            className = className.substring(0, className.length() - 6);
            Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(className);
            if (classPredicate == null || classPredicate.test(loadClass)) {
                classes.add(loadClass);
            }

        }
    }

    /**
     * 在文件夹中扫描包和类
     */
    private void scanPackageClassesByFile(Set<Class<?>> classes, String packageName, String packagePath)
            throws ClassNotFoundException {
        // 转为文件
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirFiles = dir.listFiles(file -> {
            String filename = file.getName();

            if (file.isDirectory()) {
                if (!recursive) {
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
                scanPackageClassesByFile(classes, packageName + "." + file.getName(), file.getAbsolutePath());
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                if (classPredicate == null || classPredicate.test(loadClass)) {
                    classes.add(loadClass);
                }
            }
        }
    }

    public Set<Class<?>> scanWithAnnotation(Class<? extends Annotation> type) throws ClassNotFoundException, IOException {
        if (classes == null) {
            classes = scanAllClasses();
        }
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

    public Set<Class<?>> scanWithInterface(Class<?> interfaceType) throws IOException, ClassNotFoundException {
        if (classes == null) {
            classes = scanAllClasses();
        }
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
}