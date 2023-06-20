package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.utils.NumeronLogUtil;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * @author Erzbir
 * @Date: 2023/6/19 13:34
 */
public class ClassMonitor {
    public static final String HOT_DIRECTORY = "numeron_plugin";
    public static final long HOT_INTERVAL = 5000;

    public static void start() throws Exception {
        IOFileFilter filter = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".class"));
        FileAlterationObserver observer = new FileAlterationObserver(new File(HOT_DIRECTORY), filter);
        observer.addListener(new ClassFileAlterationListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(HOT_INTERVAL, observer);
        monitor.start();
    }

    private static class ClassFileAlterationListener extends FileAlterationListenerAdaptor {

        @Override
        public void onFileChange(File file) {
            try (HotSpiPluginLoader classLoader = new HotSpiPluginLoader()) {
                classLoader.addURLs(HOT_DIRECTORY);
                classLoader.load();
            } catch (Exception e) {
                NumeronLogUtil.logger.error("ERROR", e);
            } finally {
                System.gc();
            }
        }
    }
}
