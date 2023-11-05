package com.erzbir.numeron.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author Erzbir
 * @Date: 2022/12/12 01:17
 */
@Getter
public class ClassScanner {

    @Setter
    private String basePackage; // 主包
    @Setter
    private String packageDirName;
    @Setter
    private Predicate<Class<?>> classPredicate; // 用于过滤
    @Setter
    private ClassLoader classLoader;
    private Set<Class<?>> classes = new HashSet<>();  // 扫瞄后将字节码放到这个Set
    private Set<String> classesOfLoadError = new HashSet<>();
    @Setter
    private boolean initialize;
    @Setter
    private boolean ignoreLoadError;

    /**
     * @param basePackage    主包
     * @param classPredicate 用于过滤
     * @param classLoader    类加载器
     */
    public ClassScanner(String basePackage, ClassLoader classLoader,
                        Predicate<Class<?>> classPredicate) {
        if (basePackage.endsWith(".")) {
            basePackage = basePackage.substring(0, basePackage.lastIndexOf('.'));
        }
        this.basePackage = basePackage;
        this.packageDirName = basePackage.replace('.', '/');
        this.classPredicate = classPredicate;
        this.classLoader = classLoader;
        this.initialize = false;
        this.ignoreLoadError = false;
    }

    public ClassScanner(String basePackage, ClassLoader classLoader) {
        this(basePackage, classLoader, null);
    }

    public ClassScanner(String basePackage) {
        this(basePackage, Thread.currentThread().getContextClassLoader(), null);
    }

    public ClassScanner(String basePackage, Predicate<Class<?>> classPredicate) {
        this(basePackage, Thread.currentThread().getContextClassLoader(), classPredicate);
    }

    public static Set<Class<?>> scanAllPackageByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        return scanAllPackage(packageName, clazz -> clazz.isAnnotationPresent(annotationClass));
    }

    public static Set<Class<?>> scanPackageByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        return scanPackage(packageName, clazz -> clazz.isAnnotationPresent(annotationClass));
    }

    public static Set<Class<?>> scanAllPackageBySuper(String packageName, Class<?> superClass) {
        return scanAllPackage(packageName, clazz -> superClass.isAssignableFrom(clazz) && !superClass.equals(clazz));
    }

    public static Set<Class<?>> scanPackageBySuper(String packageName, Class<?> superClass) {
        return scanPackage(packageName, clazz -> superClass.isAssignableFrom(clazz) && !superClass.equals(clazz));
    }

    public static Set<Class<?>> scanPackage() {
        return scanPackage("", (Predicate<Class<?>>) null);
    }

    public static Set<Class<?>> scanPackage(String packageName) {
        return scanPackage(packageName, (Predicate<Class<?>>) null);
    }

    public static Set<Class<?>> scanPackage(String packageName, ClassLoader classLoader, boolean force) {
        return new ClassScanner(packageName, classLoader, null).scan(force);
    }

    public static Set<Class<?>> scanPackage(String packageName, boolean force) {
        return new ClassScanner(packageName).scan(force);
    }

    public static Set<Class<?>> scanPackage(String packageName, ClassLoader classLoader) {
        return new ClassScanner(packageName, classLoader, null).scan();
    }

    public static Set<Class<?>> scanPackage(String packageName, Predicate<Class<?>> classFilter) {
        return new ClassScanner(packageName, Thread.currentThread().getContextClassLoader(), classFilter).scan();
    }

    public static Set<Class<?>> scanPackage(String packageName, ClassLoader classLoader, Predicate<Class<?>> classFilter) {
        return new ClassScanner(packageName, classLoader, classFilter).scan();
    }

    public static Set<Class<?>> scanAllPackage() {
        return scanAllPackage("");
    }

    public static Set<Class<?>> scanAllPackage(String packageName) {
        return new ClassScanner(packageName).scan(true);
    }

    public static Set<Class<?>> scanAllPackage(String packageName, Predicate<Class<?>> classFilter) {
        return new ClassScanner(packageName, Thread.currentThread().getContextClassLoader(), classFilter).scan(true);
    }

    public static Set<Class<?>> scanAllPackage(String packageName, ClassLoader classLoader) {
        return new ClassScanner(packageName, classLoader).scan(true);
    }

    public static Set<Class<?>> scanAllPackage(String packageName, ClassLoader classLoader, Predicate<Class<?>> classFilter) {
        return new ClassScanner(packageName, classLoader, classFilter).scan(true);
    }

    /**
     * 获取注解的注解, 用于解决注解传递的问题
     *
     * @param subAnnotation     子注解
     * @param supAnnotationType 要获取的注解的字节码
     * @return subAnnotation上的字节码为annotationType的注解
     */
    private static Annotation getAnnotationFromAnnotation(Annotation subAnnotation, Class<? extends Annotation> supAnnotationType) {
        if (subAnnotation == null || supAnnotationType == null) {
            return null;
        }
        if (subAnnotation.annotationType() == supAnnotationType) {
            return subAnnotation;
        }
        for (Annotation annotation1 : subAnnotation.annotationType().getAnnotations()) {
            String name = annotation1.annotationType().getPackage().getName();
            if (name.startsWith("java") || name.startsWith("kot")) {
                continue;
            }
            return getAnnotationFromAnnotation(annotation1, supAnnotationType);
        }
        return null;
    }

    public Set<Class<?>> scan() {
        return scan(false);
    }

    public Set<Class<?>> scan(boolean forceScanJavaClassPaths) {
        clear();
        String basePackageFilePath = basePackage.replace('.', '/');
        try {
            Enumeration<URL> resources = classLoader.getResources(basePackageFilePath);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
                    scanFile(new File(filePath), null);
                } else if ("jar".equals(protocol)) {
                    scanJar(((JarURLConnection) resource.openConnection()).getJarFile());
                }
            }
        } catch (IOException e) {
            classesOfLoadError.add(basePackageFilePath);
            NumeronLogUtil.err(e.getMessage());
        }
        if (forceScanJavaClassPaths || classes.isEmpty()) {
            scanJavaClassPaths();
        }
        return classes;
    }

    public Set<Class<?>> scanWithAnnotation(Class<? extends Annotation> type) {
        // 如果为空就先执行扫瞄再用Stream流过滤, 不为空就注解过滤
        if (classes.isEmpty()) {
            return scanWithAnnotation(type, true);
        }
        return scanWithAnnotation(type, false);
    }

    public Set<Class<?>> scanWithAnnotation(Class<? extends Annotation> type, boolean isRenew) {
        if (isRenew) {
            scan();
        }
        return classes.stream().filter(t -> {
            if (t.isAnnotation()) {
                return false;
            }
            if (t.getAnnotation(type) != null) {
                return true;
            }
            Annotation[] annotations = t.getAnnotations();  // 取出 t 上的所有注解, 向上一层搜索
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getAnnotation(type) != null) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toSet());
    }

    public Set<Class<?>> scanWithSupperClass(Class<?> supperClass) {
        if (classes.isEmpty()) {
            return scanWithSupperClass(supperClass, true);
        }
        return scanWithSupperClass(supperClass, false);
    }

    public Set<Class<?>> scanWithSupperClass(Class<?> supperClass, boolean isRenew) {
        if (isRenew) {
            scan();
        }
        return classes.stream().filter(t -> {
            Class<?>[] interfaces = t.getInterfaces();
            for (Class<?> inter : interfaces) {
                if (inter == supperClass) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toSet());
    }

    protected Class<?> loadClass(String className) {
        ClassLoader loader = this.classLoader;
        if (null == loader) {
            loader = Thread.currentThread().getContextClassLoader();
            this.classLoader = loader;
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, this.initialize, loader);
        } catch (NoClassDefFoundError | ClassNotFoundException | UnsupportedClassVersionError e) {
            classesOfLoadError.add(className);
        } catch (Throwable e) {
            if (!this.ignoreLoadError) {
                throw new RuntimeException(e);
            } else {
                classesOfLoadError.add(className);
            }
        }
        return clazz;
    }

    private void scanJavaClassPaths() {
        final String[] javaClassPaths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
        for (String classPath : javaClassPaths) {
            classPath = URLDecoder.decode(classPath, StandardCharsets.UTF_8);
            scanFile(new File(classPath), null);
        }
    }

    /**
     * 扫描文件或目录中的类
     *
     * @param file    文件或目录
     * @param rootDir 包名对应classpath绝对路径
     */
    private void scanFile(File file, String rootDir) {
        if (file.isFile()) {
            final String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".class")) {
                final String className = fileName//
                        .substring(rootDir.length(), fileName.length() - 6)//
                        .replace('/', '.');//
                addIfAccept(className);
            } else if (fileName.endsWith(".jar")) {
                try {
                    scanJar(new JarFile(file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (file.isDirectory()) {
            final File[] files = file.listFiles();
            if (null != files) {
                for (File subFile : files) {
                    scanFile(subFile, (null == rootDir) ? subPathBeforePackage(file) : rootDir);
                }
            }
        }
    }

    private String subPathBeforePackage(File file) {
        String filePath = file.getAbsolutePath();
        if (packageDirName != null && !packageDirName.isEmpty() && packageDirName.isBlank()) {
            int i = packageDirName.lastIndexOf('/');
            filePath = filePath.substring(0, i);
        }
        return filePath.endsWith("/") ? filePath : filePath.concat("/");
    }

    private void scanJar(JarFile jar) {
        String name;
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            name = entry.getName().replace('/', '.');
            if (basePackage == null || basePackage.isEmpty() || name.startsWith(this.basePackage)) {
                if (name.endsWith(".class") && !entry.isDirectory()) {
                    final String className = name//
                            .substring(0, name.length() - 6)//
                            .replace('/', '.');
                    addIfAccept(loadClass(className));
                }
            }
        }
    }

    private void addIfAccept(Class<?> clazz) {
        if (null != clazz) {
            if (classPredicate == null || classPredicate.test(clazz)) {
                this.classes.add(clazz);
            }
        }
    }

    private void addIfAccept(String className) {
        if (className == null || className.isEmpty() || className.isBlank()) {
            return;
        }
        int classLen = className.length();
        int packageLen = this.basePackage.length();
        if (classLen == packageLen) {
            if (className.equals(this.basePackage)) {
                addIfAccept(loadClass(className));
            }
        } else if (classLen > packageLen) {
            addIfAccept(loadClass(className));
        }
    }

    private void clear() {
        this.classes = new HashSet<>();
        this.classesOfLoadError = new HashSet<>();
    }
}