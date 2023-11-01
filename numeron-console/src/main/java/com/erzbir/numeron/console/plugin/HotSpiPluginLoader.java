package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.utils.NumeronLogUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;

/**
 * @author Erzbir
 * @Date: 2023/4/26 19:13
 */
public class HotSpiPluginLoader extends URLClassLoader implements HotJarLoad, SpiJarLoad {
    public final String SPI_CONFIG_FILE = "META-INF/services/com.erzbir.numeron.console.plugin.Plugin";

    private final Map<String, Class<?>> classCache = new ConcurrentHashMap<>();

    //所有需要我们自己加载的类
    private final Map<String, File> fileCache = new ConcurrentHashMap<>();

    public HotSpiPluginLoader() {
        super(new URL[0]);
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        Object lock = super.isRegisteredAsParallelCapable() ? getClassLoadingLock(className) : this;
        synchronized (lock) {
            Class<?> c = classCache.get(className);
            if (c == null) {
                c = findLoadedClass(className);
            }
            if (c == null) {
                if (fileCache.containsKey(className)) {
                    throw new ClassNotFoundException();
                } else {
                    return getSystemClassLoader().loadClass(className);
                }
            }
            return c;
        }
    }

    public String getSPI_CONFIG_FILE() {
        return SPI_CONFIG_FILE;
    }

    public Map<String, Class<?>> getClassCache() {
        return classCache;
    }

    public Map<String, File> getFileCache() {
        return fileCache;
    }

    @Override
    public List<Object> loadNewJar(File jar) throws IOException {
        JarFile jarFile = new JarFile(jar);
        jarFile.entries().asIterator().forEachRemaining(jarEntry -> {
            String name = jarEntry.getName();
            if (name.endsWith(".class") && !name.endsWith("module-info.class") && !name.endsWith("package-info.class")) {
                InputStream inputStream;
                String className = name.replaceAll("/", ".")
                        .replace(".class", "");
                try {
                    inputStream = jarFile.getInputStream(jarEntry);
                    if (findLoadedClass(className) == null && classCache.get(className) == null) {
                        load(inputStream, className);
                    }
                } catch (IOException e) {
                    NumeronLogUtil.logger.error("ERROR", e);
                }
            }
        });
        return loadNewJar(jarFile);
    }

    private List<Object> loadNewJar(JarFile jarFile) throws IOException {
        List<Object> objects = new ArrayList<>();
        try (jarFile; BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(jarFile.getEntry(SPI_CONFIG_FILE))))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Class<?> clazz = Class.forName(line, false, this);
                Field instanceField = clazz.getField("INSTANCE");
                instanceField.setAccessible(true);
                Object instance = instanceField.get(null);
                if (instance != null) {
                    objects.add(instance);
                }
            }
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            NumeronLogUtil.logger.error("ERROR", e);
        }
        return objects;
    }

    @Override
    public List<Object> loadJarFromSpi(@NotNull File plugin, @NotNull Class<?> type) throws IOException {
        List<Object> objects = new ArrayList<>();
        ServiceLoader<?> load = ServiceLoader.load(type, this);
        load.stream().forEach(objects::add);
        return objects;
    }

    public void addURLs(String directory) throws IOException {
        Collection<File> files = FileUtils.listFiles(new File(directory), new String[]{"class"}, true);
        for (File file : files) {
            String className = fileToClassName(file);
            fileCache.putIfAbsent(className, file);
        }
    }

    public void load() throws IOException {
        for (Map.Entry<String, File> fileEntry : fileCache.entrySet()) {
            File file = fileEntry.getValue();
            String name = file.getName();
            if (name.endsWith(".class")) {
                this.load(file);
            } else if (name.endsWith(".jar")) {
                this.loadNewJar(file);
            }
        }
    }

    private Class<?> load(File file) throws IOException {
        String className = fileToClassName(file);
        return load(file, className);
    }

    private Class<?> load(InputStream inputStream, String className) throws IOException {
        final byte[] bytes = inputStream.readAllBytes();
        return load(className, bytes);
    }

    private Class<?> load(File file, String className) {
        byte[] bytes = fileToBytes(file);
        return load(className, bytes);
    }

    private Class<?> load(String className, byte[] bytes) {
        Class<?> defineClass = defineClass(className, bytes, 0, bytes.length);
        if (defineClass != null) {
            classCache.put(className, defineClass);
        }
        return defineClass;
    }


    private byte[] fileToBytes(File file) {
        try {
            return IOUtils.toByteArray(file.toURI());
        } catch (IOException e) {
            NumeronLogUtil.logger.error("ERROR", e);
            return new byte[0];
        }
    }

    private String fileToClassName(File file) throws IOException {
        String path = file.getCanonicalPath();
        String className = path.substring(path.lastIndexOf("/classes") + 9);
        return className.replaceAll("/", ".").replace(".class", "");
    }
}
