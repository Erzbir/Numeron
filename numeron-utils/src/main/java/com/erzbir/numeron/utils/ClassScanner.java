package com.erzbir.numeron.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
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
public class ClassScanner {

    private final String basePackage; // 主包
    private final String packageDirName;
    private final boolean recursive;  // 是否递归扫瞄
    @Deprecated
    private final Predicate<String> packagePredicate; // 用于过滤
    private final Predicate<Class<?>> classPredicate; // 用于过滤
    private ClassLoader classLoader;
    private Set<Class<?>> classes = new HashSet<>();  // 扫瞄后将字节码放到这个Set
    private Set<String> classesOfLoadError = new HashSet<>();
    private boolean initialize;
    private boolean ignoreLoadError;


    /**
     * @param basePackage    主包
     * @param recursive      是否递归
     * @param classPredicate 用于过滤
     * @param classLoader    类加载器, 在类加载器和线程类加载器的上下文不一致时会用到
     */
    public ClassScanner(String basePackage, ClassLoader classLoader, boolean recursive,
                        Predicate<Class<?>> classPredicate) {
        if (basePackage.endsWith(".")) {
            basePackage = basePackage.substring(0, basePackage.lastIndexOf('.'));
        }
        this.basePackage = basePackage;
        this.packageDirName = basePackage.replace('.', File.separatorChar);
        this.recursive = recursive;
        this.classPredicate = classPredicate;
        this.classLoader = classLoader;
        this.packagePredicate = null;
        this.initialize = false;
        this.ignoreLoadError = false;
    }

    public ClassScanner(String basePackage, ClassLoader classLoader, boolean recursive, Predicate<Class<?>> classPredicate, Predicate<?> packagePredicate) {
        this(basePackage, classLoader, recursive, classPredicate);
    }

    public ClassScanner(String basePackage, ClassLoader classLoader, boolean recursive) {
        this(basePackage, classLoader, recursive, null);
    }

    public ClassScanner(String basePackage, boolean recursive) {
        this(basePackage, Thread.currentThread().getContextClassLoader(), recursive, null);
    }

    public static Set<Class<?>> scanAllPackageByAnnotation(String packageName, Class<? extends Annotation> annotationClass) throws IOException {
        return scanAllPackage(packageName, clazz -> clazz.isAnnotationPresent(annotationClass));
    }

    public static Set<Class<?>> scanPackageByAnnotation(String packageName, Class<? extends Annotation> annotationClass) throws IOException, ClassNotFoundException {
        return scanPackage(packageName, clazz -> clazz.isAnnotationPresent(annotationClass));
    }

    public static Set<Class<?>> scanAllPackageBySuper(String packageName, Class<?> superClass) throws IOException {
        return scanAllPackage(packageName, clazz -> superClass.isAssignableFrom(clazz) && !superClass.equals(clazz));
    }

    public static Set<Class<?>> scanPackageBySuper(String packageName, Class<?> superClass) throws IOException, ClassNotFoundException {
        return scanPackage(packageName, clazz -> superClass.isAssignableFrom(clazz) && !superClass.equals(clazz));
    }

    public static Set<Class<?>> scanAllPackage() throws IOException {
        return scanAllPackage("", null);
    }

    public static Set<Class<?>> scanPackage() throws IOException, ClassNotFoundException {
        return scanPackage("", (Predicate<Class<?>>) null);
    }

    public static Set<Class<?>> scanPackage(String packageName) throws IOException, ClassNotFoundException {
        return scanPackage(packageName, (Predicate<Class<?>>) null);
    }

    public static Set<Class<?>> scanPackage(String packageName, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        return new ClassScanner(packageName, classLoader, true, null).scanAllClasses();
    }

    public static Set<Class<?>> scanPackage(String packageName, Predicate<Class<?>> classFilter) throws IOException, ClassNotFoundException {
        return new ClassScanner(packageName, Thread.currentThread().getContextClassLoader(), true, classFilter).scanAllClasses();
    }

    public static Set<Class<?>> scanPackage(String packageName, ClassLoader classLoader, Predicate<Class<?>> classFilter) throws IOException, ClassNotFoundException {
        return new ClassScanner(packageName, classLoader, true, classFilter).scanAllClasses();
    }

    public static Set<Class<?>> scanAllPackage(String packageName) throws IOException {
        return new ClassScanner(packageName, Thread.currentThread().getContextClassLoader(), true, null).scanAllClasses(true);
    }

    public static Set<Class<?>> scanAllPackage(String packageName, Predicate<Class<?>> classFilter) throws IOException {
        return new ClassScanner(packageName, Thread.currentThread().getContextClassLoader(), true, classFilter).scanAllClasses(true);
    }

    public static Set<Class<?>> scanAllPackage(String packageName, ClassLoader classLoader, Predicate<Class<?>> classFilter) throws IOException, ClassNotFoundException {
        return new ClassScanner(packageName, classLoader, true, classFilter).scanAllClasses(true);
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

    public Set<Class<?>> getClasses() {
        return classes;
    }

    public Set<String> getClassesOfLoadError() {
        return classesOfLoadError;
    }

    public boolean isInitialize() {
        return initialize;
    }

    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }

    public boolean isIgnoreLoadError() {
        return ignoreLoadError;
    }

    public void setIgnoreLoadError(boolean ignoreLoadError) {
        this.ignoreLoadError = ignoreLoadError;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String getPackageDirName() {
        return packageDirName;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public Predicate<String> getPackagePredicate() {
        return packagePredicate;
    }

    public Predicate<Class<?>> getClassPredicate() {
        return classPredicate;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * @return 包含所有class的Set
     * <p>扫瞄所有class</p>
     */
    public Set<Class<?>> scanAllClasses() throws IOException, ClassNotFoundException {
        return scanAllClasses(false);
    }

    public Set<Class<?>> scanAllClasses(boolean forceScanJavaClassPaths) throws IOException {
        clear();
        String basePackageFilePath = basePackage.replace('.', File.separatorChar);
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
        if (forceScanJavaClassPaths) {
            scanJavaClassPaths();
        }
        return classes;
    }

    /**
     * @param type 注解字节码
     * @return 返回Set
     * <p>扫瞄有{@param type}注解的类</p>
     */
    public Set<Class<?>> scanWithAnnotation(Class<? extends Annotation> type) throws ClassNotFoundException, IOException {
        // 如果为空就先执行扫瞄再用Stream流过滤, 不为空就注解过滤
        if (classes.size() == 0) {
            return scanWithAnnotation(type, true);
        }
        return scanWithAnnotation(type, false);
    }

    public Set<Class<?>> scanWithAnnotation(Class<? extends Annotation> type, boolean isRenew) throws IOException, ClassNotFoundException {
        if (isRenew) {
            scanAllClasses();
        }
        return classes.stream().filter(t -> {
            boolean flag = !t.isAnnotation(); // 排除注解, 因为注解也会有注解
            Annotation[] annotations = t.getAnnotations();  // 取出t上的所有类注解
            for (Annotation annotation : annotations) {
                // if (getAnnotationFromAnnotation(annotation, type) != null)
                if (annotation.annotationType().getAnnotation(type) != null) {
                    return flag;
                }
            }
            return false;
        }).collect(Collectors.toSet());
    }

    /**
     * @param interfaceType 接口的字节码
     * @return 返回一个Set
     * <p>此方法扫瞄实现了{@param interfaceType}的类</p>
     */
    public Set<Class<?>> scanWithInterface(Class<?> interfaceType) throws IOException, ClassNotFoundException {
        if (classes.size() == 0) {
            return scanWithInterface(interfaceType, true);
        }
        return scanWithInterface(interfaceType, false);
    }

    public Set<Class<?>> scanWithInterface(Class<?> interfaceType, boolean isRenew) throws IOException, ClassNotFoundException {
        if (isRenew) {
            scanAllClasses();
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
            classPath = URLDecoder.decode(classPath, File.separatorChar == '\\' ? Charset.forName("GBK") : StandardCharsets.UTF_8);
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
                        .replace(File.separatorChar, '.');//
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
            int i = packageDirName.lastIndexOf(File.separatorChar);
            filePath = filePath.substring(0, i);
        }
        return filePath.endsWith(File.separator) ? filePath : filePath.concat(File.separator);
    }

    private void scanJar(JarFile jar) {
        String name;
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            name = entry.getName().replace(File.separatorChar, '.');
            if (basePackage == null || basePackage.isEmpty() || name.startsWith(this.basePackage)) {
                if (name.endsWith(".class") && !entry.isDirectory()) {
                    final String className = name//
                            .substring(0, name.length() - 6)//
                            .replace(File.separatorChar, '.');
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