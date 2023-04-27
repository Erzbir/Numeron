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

    private final String basePackage; // 主包
    private final String packageDirName;
    private final boolean recursive;  // 是否递归扫瞄
    private final Predicate<String> packagePredicate; // 用于过滤
    private final Predicate<Class<?>> classPredicate; // 用于过滤
    private ClassLoader classLoader;
    private Set<Class<?>> classes = new HashSet<>();  // 扫瞄后将字节码放到这个Set
    private Set<String> classesOfLoadError = new HashSet<>();
    private boolean initialize;
    private boolean ignoreLoadError;


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
        this.packageDirName = basePackage.replace('.', File.separatorChar);
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
            if (annotation1.annotationType().getPackage().getName().startsWith("java")) {
                continue;
            }
            return getAnnotationFromAnnotation(annotation1, supAnnotationType);
        }
        return null;
    }

//    private void scanJavaClassPaths() {
//        final String[] javaClassPaths = ClassUtil.getJavaClassPaths();
//        for (String classPath : javaClassPaths) {
//            // bug修复，由于路径中空格和中文导致的Jar找不到
//            classPath = URLUtil.decode(classPath, CharsetUtil.systemCharsetName());
//
//            scanFile(new File(classPath), null);
//        }
//    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

//    /**
//     * 扫描文件或目录中的类
//     *
//     * @param file    文件或目录
//     * @param rootDir 包名对应classpath绝对路径
//     */
//    private void scanFile(File file, String rootDir) {
//        if (file.isFile()) {
//            final String fileName = file.getAbsolutePath();
//            if (fileName.endsWith(".class")) {
//                final String className = fileName//
//                        // 8为classes长度，fileName.length() - 6为".class"的长度
//                        .substring(rootDir.length(), fileName.length() - 6)//
//                        .replace(File.separatorChar, '.');//
//                //加入满足条件的类
//                addIfAccept(className);
//            } else if (fileName.endsWith(".jar")) {
//                try {
//                    scanJar(new JarFile(file));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        } else if (file.isDirectory()) {
//            final File[] files = file.listFiles();
//            if (null != files) {
//                for (File subFile : files) {
//                    scanFile(subFile, (null == rootDir) ? subPathBeforePackage(file) : rootDir);
//                }
//            }
//        }
//    }

    //    private String subPathBeforePackage(File file) {
//        String filePath = file.getAbsolutePath();
//        if (packageDirName != null && !packageDirName.isEmpty() && packageDirName.isBlank()) {
//            int i = packageDirName.lastIndexOf(File.separatorChar);
//            filePath = StrUtil.subBefore(filePath, this.packageDirName, true);
//        }
//        return StrUtil.addSuffixIfNot(filePath, File.separator);
//    }
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
            //类名和包名长度一致，用户可能传入的包名是类名
            if (className.equals(this.basePackage)) {
                addIfAccept(loadClass(className));
            }
        } else if (classLen > packageLen) {
            addIfAccept(loadClass(className));
        }
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
        } catch (NoClassDefFoundError | ClassNotFoundException e) {
            // 由于依赖库导致的类无法加载，直接跳过此类
            classesOfLoadError.add(className);
        } catch (UnsupportedClassVersionError e) {
            // 版本导致的不兼容的类，跳过
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

    /**
     * @return 包含所有class的Set
     * <p>扫瞄所有class</p>
     */
    public Set<Class<?>> scanAllClasses() throws IOException, ClassNotFoundException {
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
                scanPackageClassesByFile(packageName, filePath);
            } else if ("jar".equals(protocol)) {
                scanPackageClassesByJar(packageName, resource);
            }
        }
        return classes;
    }

    public Set<Class<?>> scanPackageClassesByJar(String basePackage, URL url)
            throws IOException, ClassNotFoundException {
        Set<Class<?>> ret = new HashSet<>();
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
                ret.add(loadClass);
            }
        }
        classes.addAll(ret);
        return ret;
    }

    /**
     * 在文件夹中扫描包和类
     */
    public Set<Class<?>> scanPackageClassesByFile(String packageName, String packagePath)
            throws ClassNotFoundException {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        Set<Class<?>> ret = new HashSet<>();
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
            return null;
        }
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                scanPackageClassesByFile(packageName + "." + file.getName(), file.getAbsolutePath());
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class<?> loadClass = classLoader.loadClass(packageName + '.' + className);
                if (classPredicate == null || classPredicate.test(loadClass)) {
                    ret.add(loadClass);
                }
            }
        }
        classes.addAll(ret);
        return ret;
    }

    /**
     * @param type 注解字节码
     * @return 返回Set
     * <p>扫瞄有{@param type}注解的类</p>
     */
    public Set<Class<?>> scanWithAnnotation(Class<? extends Annotation> type) throws ClassNotFoundException, IOException {
        // 如果为空就先执行扫瞄再用Stream流过滤, 不为空就注解过滤
        if (classes.size() == 0) {
            scanAllClasses();
        }
        return classes.stream().filter(t -> {
            boolean flag = !t.isAnnotation(); // 排除注解, 因为注解也会有注解
            Annotation[] annotations = t.getAnnotations();  // 取出t上的所有类注解
            for (Annotation annotation : annotations) {
                if (getAnnotationFromAnnotation(annotation, type) != null) {
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

    private void clear() {
        this.classes = new HashSet<>();
        this.classesOfLoadError = new HashSet<>();
    }
}