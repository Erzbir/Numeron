package com.erzbir.numeron.menu;


import com.erzbir.numeron.annotation.Command;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

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
        try {
            bufferedImageMap.put("close", IOUtils.drawingImgByPath("/Images/Menu/close.png"));
            bufferedImageMap.put("content", IOUtils.drawingImgByPath("/Images/Menu/content.png"));
            bufferedImageMap.put("help", IOUtils.drawingImgByPath("/Images/Menu/help.png"));
            bufferedImageMap.put("open", IOUtils.drawingImgByPath("/Images/Menu/open.png"));
            bufferedImageMap.put("author", IOUtils.drawingImgByPath("/Images/Menu/author.png"));
            bufferedImageMap.put("describe", IOUtils.drawingImgByPath("/Images/Menu/describe.png"));
            bufferedImageMap.put("show", IOUtils.drawingImgByPath("/Images/Menu/show.png"));
            bufferedImageMap.put("function", IOUtils.drawingImgByPath("/Images/Menu/function.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}