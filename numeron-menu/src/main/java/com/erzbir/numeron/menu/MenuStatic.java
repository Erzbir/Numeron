package com.erzbir.numeron.menu;


import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2023/3/15 19:53
 */
public class MenuStatic {
    public static final Set<String> menuList = new HashSet<>();
    public static final Map<String, Set<Long>> closeMenuGroups = new HashMap<>();
    public static final Map<String, List<Command>> menuMap = new HashMap<>();
    public static Map<String, BufferedImage> bufferedImageMap = new HashMap<>();

    static {
        ExecutorService poolExecutor = Executors.newFixedThreadPool(8);
        poolExecutor.execute(() -> {
            try {
                bufferedImageMap.put("close", IOUtils.asyncDrawingImgByPath("/Images/Menu/close.png"));
            } catch (IOException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        });
        poolExecutor.execute(() -> {
            try {
                bufferedImageMap.put("content", IOUtils.asyncDrawingImgByPath("/Images/Menu/content.png"));
            } catch (IOException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        });
        poolExecutor.execute(() -> {
            try {
                bufferedImageMap.put("describe", IOUtils.asyncDrawingImgByPath("/Images/Menu/describe.png"));
            } catch (IOException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        });
        poolExecutor.execute(() -> {
            try {
                bufferedImageMap.put("help", IOUtils.asyncDrawingImgByPath("/Images/Menu/help.png"));
            } catch (IOException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        });
        poolExecutor.execute(() -> {
            try {
                bufferedImageMap.put("open", IOUtils.asyncDrawingImgByPath("/Images/Menu/open.png"));

            } catch (IOException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        });
        poolExecutor.execute(() -> {
            try {
                bufferedImageMap.put("author", IOUtils.asyncDrawingImgByPath("/Images/Menu/author.png"));
            } catch (IOException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        });
        poolExecutor.execute(() -> {
            try {
                bufferedImageMap.put("show", IOUtils.asyncDrawingImgByPath("/Images/Menu/show.png"));
            } catch (IOException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        });
        poolExecutor.execute(() -> {
            try {
                bufferedImageMap.put("function", IOUtils.asyncDrawingImgByPath("/Images/Menu/function.png"));
            } catch (IOException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new RuntimeException(e);
            }
        });
        poolExecutor.shutdown();
    }
}