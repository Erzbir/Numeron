package com.erzbir.numeron.core.classloader;

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
public class ClassScanner {

    private static Set<Class<?>> classes = new HashSet<>();  // 扫瞄后将字节码放到这个Set
    private final String basePackage; // 主包
    private final boolean recursive;  // 是否递归扫瞄
    private final Predicate<String> packagePredicate; // 用于过滤
    private final Predicate<Class<?>> classPredicate; // 用于过滤
    private final ClassLoader classLoader;


    /**
     * @param basePackage      主包
     * @param recursive        是否递归
     * @param packagePredicate 用于过滤
     * @param classPredicate   用于过滤
     * @param classLoader      类加载器, 在类加载器和线程类加载器的上下文不一致时会用到
     */
    public ClassScanner(String basePackage, ClassLoader classLoader, boolean recursive, Predicate<String> packagePredicate,
                        Predicate<Class<?>> classPredicate) {
        this.basePackage = basePackage;
        this.recursive = recursive;
        this.packagePredicate = packagePredicate;
        this.classPredicate = classPredicate;
        this.classLoader = classLoader;
    }

    public ClassScanner(String basePackage, ClassLoader classLoader, boolean recursive) {
        this(basePackage, classLoader, recursive, null, null);
    }

    public ClassScanner(String basePackage, boolean recursive) {
        this(basePackage, Thread.currentThread().getContextClassLoader(), recursive, null, null);
    }

    public static Set<Class<?>> getAllClasses() {
        return classes;
    }

    /**
     * @return 包含所有class的Set
     * <p>扫瞄所有class</p>
     */
    public Set<Class<?>> scanAllClasses() throws IOException, ClassNotFoundException {
        if (classes.size() > 0) {
            return classes;
        }
        String packageName = basePackage;
        if (packageName.endsWith(".")) {
            packageName = packageName.substring(0, packageName.lastIndexOf('.'));
        }
        String basePackageFilePath = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(basePackageFilePath);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
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
            Class<?> loadClass = classLoader.loadClass(className);
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
                Class<?> loadClass = classLoader.loadClass(packageName + '.' + className);
                if (classPredicate == null || classPredicate.test(loadClass)) {
                    classes.add(loadClass);
                }
            }
        }
    }

    /**
     * @param type 注解字节码
     * @return 返回Set
     * <p>扫瞄有{@param type}注解的类</p>
     */
    public Set<Class<?>> scanWithAnnotation(Class<? extends Annotation> type) throws ClassNotFoundException, IOException {
        // 如果为空就先执行扫瞄再用Stream流过滤, 不为空就注解过滤
        if (classes.size() == 0) {
            classes = scanAllClasses();
        }
        return classes.stream().filter(t -> {
            boolean flag = !t.isAnnotation(); // 排除注解, 因为注解也会有注解
            boolean flag2 = false;
            Annotation[] annotations = t.getAnnotations();  // 取出t上的所有类注解
            for (Annotation annotation : annotations) {
                flag2 = getAnnotationFromAnnotation(annotation, type) != null;
            }
            return flag && flag2;
        }).collect(Collectors.toSet());
    }

    /**
     * @param annotation 要扫瞄的注解
     * @param clazz      要获取的注解的字节码
     * @return 返回注解类型, 如果获取到就不为空反之空,
     * 用于判断{@param annotation}注解上是否有{@param clazz注解}
     */
    public Annotation getAnnotationFromAnnotation(Annotation annotation, Class<? extends Annotation> clazz) {
        return annotation.annotationType().getAnnotation(clazz);
    }

    /**
     * @param interfaceType 接口的字节码
     * @return 返回一个Set
     * <p>此方法扫瞄实现了{@param interfaceType}的类</p>
     */
    public Set<Class<?>> scanWithInterface(Class<?> interfaceType) throws IOException, ClassNotFoundException {
        if (classes.size() == 0) {
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